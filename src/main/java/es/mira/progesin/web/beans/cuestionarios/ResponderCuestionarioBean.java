package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.Documento;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionarioId;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IDatosTablaGenericaRepository;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IRespuestaCuestionarioService;
import es.mira.progesin.util.DataTableView;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean que contiene los métodos necesarios para que los usuarios puedan responder las preguntas contenidas en los
 * cuestionarios
 * @author EZENTIS
 *
 */

@Setter
@Getter
@Controller("responderCuestionarioBean")
@Scope("session")
public class ResponderCuestionarioBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private VisualizarCuestionario visualizarCuestionario;
    
    @Autowired
    private ICuestionarioEnvioService cuestionarioEnvioService;
    
    private CuestionarioEnvio cuestionarioEnviado;
    
    @Autowired
    private transient IRespuestaCuestionarioRepository respuestaRepository;
    
    @Autowired
    private transient IRespuestaCuestionarioService respuestaService;
    
    @Autowired
    private transient IDatosTablaGenericaRepository datosTablaRepository;
    
    @Autowired
    private transient IDocumentoService documentoService;
    
    @Autowired
    transient IRegistroActividadService regActividadService;
    
    @Autowired
    private transient INotificacionService notificacionService;
    
    @Autowired
    transient IAlertaService alertaService;
    
    /**
     * Guarda las respuestas introducidas por el usuario en BBDD, incluidos los documentos subidos
     */
    public void guardarBorrador() {
        try {
            List<RespuestaCuestionario> listaRespuestas = new ArrayList<>();
            guardarRespuestasTipoTexto(listaRespuestas);
            List<DatosTablaGenerica> listaDatosTablaSave = new ArrayList<>();
            guardarRespuestasTipoTablaMatriz(listaRespuestas, listaDatosTablaSave);
            
            cuestionarioEnvioService.transaccSaveConRespuestas(cuestionarioEnviado, listaRespuestas,
                    listaDatosTablaSave);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Borrador",
                    "El borrador se ha guardado con éxito");
        } catch (Exception e) {
            e.printStackTrace();
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al guardar las respuestas. ");
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.name(), e);
        }
    }
    
    /**
     * Guarda la fecha de cumplimentación y las respuestas introducidas por el usuario en BBDD, incluidos los documentos
     * subidos
     * 
     * @author EZENTIS
     */
    public void enviarCuestionario() {
        try {
            List<RespuestaCuestionario> listaRespuestas = new ArrayList<>();
            guardarRespuestasTipoTexto(listaRespuestas);
            List<DatosTablaGenerica> listaDatosTablaSave = new ArrayList<>();
            guardarRespuestasTipoTablaMatriz(listaRespuestas, listaDatosTablaSave);
            
            cuestionarioEnviado.setFechaCumplimentacion(new Date());
            cuestionarioEnvioService.transaccSaveConRespuestasInactivaUsuariosProv(cuestionarioEnviado, listaRespuestas,
                    listaDatosTablaSave);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Cumplimentación",
                    "Cuestionario cumplimentado y enviado con éxito.");
            
            String textoNotReg = "Cuestionario para la inspección " + cuestionarioEnviado.getInspeccion().getNumero()
                    + " respondido";
            
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(textoNotReg, TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.CUESTIONARIO.name());
            // Notificamos a los miembros del equipo
            notificacionService.crearNotificacionEquipo(textoNotReg, SeccionesEnum.CUESTIONARIO.name(),
                    cuestionarioEnviado.getInspeccion());
            
        } catch (Exception e) {
            e.printStackTrace();
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al enviar el cuestionario cumplimentado. ");
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.name(), e);
        }
    }
    
    /**
     * Guarda las respuestas de las preguntas que no son de tipo TABLA o MATRIZ
     * @see guardarRespuestas
     *
     */
    private void guardarRespuestasTipoTexto(List<RespuestaCuestionario> listaRespuestas) {
        Map<PreguntasCuestionario, String> mapaRespuestas = visualizarCuestionario.getMapaRespuestas();
        mapaRespuestas.forEach((pregunta, respuesta) -> {
            if (respuesta != null && respuesta.isEmpty() == Boolean.FALSE) {
                RespuestaCuestionario respuestaCuestionario = new RespuestaCuestionario();
                RespuestaCuestionarioId idRespuesta = new RespuestaCuestionarioId();
                idRespuesta.setCuestionarioEnviado(cuestionarioEnviado);
                idRespuesta.setPregunta(pregunta);
                respuestaCuestionario.setRespuestaId(idRespuesta);
                respuestaCuestionario.setRespuestaTexto(respuesta);
                if (pregunta.getTipoRespuesta() != null && pregunta.getTipoRespuesta().startsWith("ADJUNTO")) {
                    respuestaCuestionario.setDocumentos(visualizarCuestionario.getMapaDocumentos().get(pregunta));
                }
                listaRespuestas.add(respuestaCuestionario);
            }
        });
    }
    
    /**
     * Guarda en BBDD las respuestas de tipo TABLA o MATRIZ
     * @param listaRespuestas
     * @see guardarRespuestas
     * 
     */
    private void guardarRespuestasTipoTablaMatriz(List<RespuestaCuestionario> listaRespuestas,
            List<DatosTablaGenerica> listaDatosTablaSave) {
        Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla = visualizarCuestionario.getMapaRespuestasTabla();
        
        mapaRespuestasTabla.forEach((pregunta, respuesta) -> {
            if (respuesta != null) {
                List<DatosTablaGenerica> listaDatosTabla = respuesta.getListaDatosTabla();
                RespuestaCuestionario rtaCuestionario = new RespuestaCuestionario();
                RespuestaCuestionarioId idRespuesta = new RespuestaCuestionarioId();
                idRespuesta.setCuestionarioEnviado(cuestionarioEnviado);
                idRespuesta.setPregunta(pregunta);
                rtaCuestionario.setRespuestaId(idRespuesta);
                for (int i = 0; i < listaDatosTabla.size(); i++) {
                    DatosTablaGenerica datosTablaGenerica = listaDatosTabla.get(i);
                    // Si no estaba ya en la respuesta
                    if (datosTablaGenerica.getId() == null) {
                        RespuestaCuestionario respuestaCuestionarioTabla = new RespuestaCuestionario();
                        respuestaCuestionarioTabla.setRespuestaId(idRespuesta);
                        datosTablaGenerica.setRespuesta(respuestaCuestionarioTabla);
                        listaDatosTabla.set(i, datosTablaGenerica);
                    }
                }
                rtaCuestionario.setRespuestaTablaMatriz(listaDatosTabla);
                listaRespuestas.add(rtaCuestionario);
                listaDatosTablaSave.addAll(listaDatosTabla);
            }
        });
    }
    
    /**
     * Elimina una fila nueva a la pregunta pasada como parámetro. El tipo de respuesta de esta pregunta siempre deberá
     * empezar por TABLA
     * @param pregunta Pregunta de un cuestionario
     */
    public void eliminarFilaRespuestaTabla(PreguntasCuestionario pregunta) {
        Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla = visualizarCuestionario.getMapaRespuestasTabla();
        if (mapaRespuestasTabla.get(pregunta) != null) {
            DataTableView dataTableView = mapaRespuestasTabla.get(pregunta);
            DatosTablaGenerica datoTablaEliminar = dataTableView.eliminarFila();
            if (datoTablaEliminar.getId() != null) {
                datosTablaRepository.delete(datoTablaEliminar);
            }
            mapaRespuestasTabla.put(pregunta, dataTableView);
            visualizarCuestionario.setMapaRespuestasTabla(mapaRespuestasTabla);
        }
    }
    
    /**
     * Se crea un objeto Documento a partir del fichero que sube el usuario y se añade al mapa de documentos que se
     * visualiza en pantalla
     * 
     * @param event Evento que contiene el fichero que sube el usuario
     */
    public void subirFichero(FileUploadEvent event) {
        UploadedFile archivoSubido = event.getFile();
        List<Documento> listaDocumentos;
        if (documentoService.extensionCorrecta(archivoSubido)) {
            
            try {
                PreguntasCuestionario pregunta = (PreguntasCuestionario) event.getComponent().getAttributes()
                        .get("pregunta");
                
                // Grabamos la respuesta con el documento subido
                RespuestaCuestionario respuestaCuestionario = new RespuestaCuestionario();
                RespuestaCuestionarioId idRespuesta = new RespuestaCuestionarioId();
                idRespuesta.setCuestionarioEnviado(cuestionarioEnviado);
                idRespuesta.setPregunta(pregunta);
                respuestaCuestionario.setRespuestaId(idRespuesta);
                respuestaCuestionario.setRespuestaTexto(visualizarCuestionario.getMapaRespuestas().get(pregunta));
                
                Map<PreguntasCuestionario, List<Documento>> mapaDocumentos = visualizarCuestionario.getMapaDocumentos();
                listaDocumentos = mapaDocumentos.get(pregunta) != null ? mapaDocumentos.get(pregunta)
                        : new ArrayList<>();
                
                respuestaService.saveConDocumento(respuestaCuestionario, archivoSubido, listaDocumentos);
                
                mapaDocumentos.put(pregunta, listaDocumentos);
                visualizarCuestionario.setMapaDocumentos(mapaDocumentos);
                
            } catch (Exception e) {
                e.printStackTrace();
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                        "Se ha producido un error al subir el fichero. Inténtelo de nuevo más tarde.");
                regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.name(), e);
            }
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "La extensión del documento no corresponde con el documento subido");
        }
    }
    
    /**
     * Elimina el documento pasado como parámetro de la pregunta
     * @param pregunta
     * @param documento
     */
    public void eliminarDocumento(PreguntasCuestionario pregunta, Documento documento) {
        
        RespuestaCuestionario respuestaCuestionario = new RespuestaCuestionario();
        RespuestaCuestionarioId idRespuesta = new RespuestaCuestionarioId();
        idRespuesta.setCuestionarioEnviado(cuestionarioEnviado);
        idRespuesta.setPregunta(pregunta);
        respuestaCuestionario.setRespuestaId(idRespuesta);
        List<Documento> listaDocumentos = visualizarCuestionario.getMapaDocumentos().get(pregunta);
        listaDocumentos.remove(documento);
        respuestaCuestionario.setDocumentos(listaDocumentos);
        respuestaRepository.save(respuestaCuestionario);
        respuestaRepository.flush();
        documentoService.delete(documento);
        
        visualizarCuestionario.getMapaDocumentos().put(pregunta, listaDocumentos);
    }
    
    /**
     * Obtiene el cuestionario a mostrar en función del usuario que se loguea
     */
    @PostConstruct
    public void init() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (RoleEnum.PROV_CUESTIONARIO.equals(user.getRole())) {
            cuestionarioEnviado = cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(user.getCorreo());
            visualizarCuestionario.visualizarRespuestasCuestionario(cuestionarioEnviado);
        }
    }
    
}
