package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionarioId;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.IAreaCuestionarioService;
import es.mira.progesin.services.IAreaUsuarioCuestEnvService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IRespuestaCuestionarioService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.DataTableView;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean que contiene los métodos necesarios para que los usuarios puedan responder las preguntas contenidas en los
 * cuestionarios.
 * 
 * @author EZENTIS
 *
 */

@Setter
@Getter
@Controller("responderCuestionarioBean")
@Scope(FacesViewScope.NAME)
public class ResponderCuestionarioBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Lista de usuarios provisionales.
     */
    private List<User> usuariosProv;
    
    /**
     * Lista de áreas/usuarios.
     */
    private List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv;
    
    /**
     * Mapa de áreas/usuarios.
     */
    private Map<Long, String> mapaAreaUsuarioCuestEnv;
    
    /**
     * Cuestionario enviado.
     */
    private CuestionarioEnvio cuestionarioEnviado;
    
    /**
     * Stream que permite la descarga de la plantilla adjunta al cuestionario.
     */
    
    private transient StreamedContent file;
    
    /**
     * Visualizar cuestionario.
     */
    @Autowired
    private VisualizarCuestionario visualizarCuestionario;
    
    /**
     * Servicio de respuestas.
     */
    @Autowired
    private transient IRespuestaCuestionarioService respuestaService;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Servicio de notificaciones.
     */
    @Autowired
    private transient INotificacionService notificacionService;
    
    /**
     * Servicio de alertas.
     */
    @Autowired
    private transient IAlertaService alertaService;
    
    /**
     * Servicio de areas/usuario de cuestionarios enviados.
     */
    @Autowired
    private transient IAreaUsuarioCuestEnvService areaUsuarioCuestEnvService;
    
    /**
     * Servicio de areas cuestionario.
     */
    @Autowired
    private transient IAreaCuestionarioService areaCuestionarioService;
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private transient IUserService userService;
    
    /**
     * Guarda las respuestas introducidas por el usuario en BBDD.
     */
    public void guardarBorrador() {
        try {
            List<RespuestaCuestionario> listaRespuestas = new ArrayList<>();
            guardarRespuestasTipoTexto(listaRespuestas);
            guardarRespuestasTipoTablaMatriz(listaRespuestas);
            
            listaRespuestas = respuestaService.transaccSaveConRespuestas(listaRespuestas);
            
            // Para que cuando guardemos las respuestas tipo tabla/matriz tengan id, sino da problemas el mapeo con las
            // respuestas de tipo tabla, ya que no encuentra el id cuando añaden filas y siguen con la misma sesión
            actualizarIdRespuestasTabla(listaRespuestas);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Borrador",
                    "El borrador se ha guardado con éxito");
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al guardar las respuestas. ");
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
        }
    }
    
    /**
     * Actualiza el mapa de respuestas de tipo tabla/matriz con los id obtenidos al guardar en BBDD.
     * 
     * @param listaRespuestas Lista de respuestas
     */
    private void actualizarIdRespuestasTabla(List<RespuestaCuestionario> listaRespuestas) {
        listaRespuestas.forEach(respuesta -> {
            String tipoRespuesta = respuesta.getRespuestaId().getPregunta().getTipoRespuesta();
            if ((tipoRespuesta.startsWith(Constantes.TIPORESPUESTATABLA)
                    || tipoRespuesta.startsWith(Constantes.TIPORESPUESTAMATRIZ))
                    && respuesta.getRespuestaTablaMatriz() != null) {
                visualizarCuestionario.getMapaRespuestasTablaAux().put(respuesta.getRespuestaId().getPregunta(),
                        respuesta.getRespuestaTablaMatriz());
            }
        });
        visualizarCuestionario.construirTipoRespuestaTablaMatrizConDatos();
    }
    
    /**
     * Guarda la fecha de cumplimentación y las respuestas introducidas por el usuario en BBDD.
     * 
     */
    public void enviarCuestionario() {
        try {
            boolean principalControlaTodasAreas = comprobarAsignaciones();
            if (principalControlaTodasAreas) {
                List<RespuestaCuestionario> listaRespuestas = new ArrayList<>();
                guardarRespuestasTipoTexto(listaRespuestas);
                guardarRespuestasTipoTablaMatriz(listaRespuestas);
                
                cuestionarioEnviado.setFechaCumplimentacion(new Date());
                respuestaService.transaccSaveConRespuestasInactivaUsuariosProv(cuestionarioEnviado, listaRespuestas);
                
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Cumplimentación",
                        "Cuestionario cumplimentado y enviado con éxito.");
                
                String textoNotReg = "Cuestionario para la inspección "
                        + cuestionarioEnviado.getInspeccion().getNumero() + " respondido";
                
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(textoNotReg, TipoRegistroEnum.ALTA.name(),
                        SeccionesEnum.CUESTIONARIO.getDescripcion());
                // Creamos alertas a los miembros del equipo y al rol de Apoyo
                alertaService.crearAlertaRol(SeccionesEnum.CUESTIONARIO.getDescripcion(), textoNotReg,
                        RoleEnum.ROLE_SERVICIO_APOYO);
                alertaService.crearAlertaEquipo(SeccionesEnum.CUESTIONARIO.getDescripcion(), textoNotReg,
                        cuestionarioEnviado.getInspeccion());
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Cumplimentación abortada",
                        "Aún hay areas asignadas a otros usuarios. ", null);
            }
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al enviar el cuestionario cumplimentado. ");
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
        }
    }
    
    /**
     * Comprueba las asignaciones realizadas.
     * @return boolean
     */
    private boolean comprobarAsignaciones() {
        String nombreUsuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean principalControlaTodasAreas = true;
        Iterator<AreaUsuarioCuestEnv> iterator = listaAreasUsuarioCuestEnv.iterator();
        while (principalControlaTodasAreas && iterator.hasNext()) {
            principalControlaTodasAreas = nombreUsuarioActual.equals(iterator.next().getUsernameProv());
        }
        return principalControlaTodasAreas;
    }
    
    /**
     * Crea una nueva respuesta a partir de una pregunta.
     * 
     * @param pregunta Pregunta que se desea usar como referencia.
     * @return Respuesta creada.
     */
    private RespuestaCuestionario crearRespuesta(PreguntasCuestionario pregunta) {
        RespuestaCuestionario respuestaCuestionario = new RespuestaCuestionario();
        RespuestaCuestionarioId idRespuesta = new RespuestaCuestionarioId();
        idRespuesta.setCuestionarioEnviado(cuestionarioEnviado);
        idRespuesta.setPregunta(pregunta);
        respuestaCuestionario.setRespuestaId(idRespuesta);
        return respuestaCuestionario;
    }
    
    /**
     * Guarda las respuestas de las preguntas que no son de tipo TABLA o MATRIZ.
     * 
     * @param listaRespuestas Lista de respuestas
     * @see guardarRespuestas
     *
     */
    private void guardarRespuestasTipoTexto(List<RespuestaCuestionario> listaRespuestas) {
        String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<PreguntasCuestionario, String> mapaRespuestas = visualizarCuestionario.getMapaRespuestas();
        mapaRespuestas.forEach((pregunta, respuesta) -> {
            // Guardar sólo las preguntas asignadas al usuario actual
            if (mapaAreaUsuarioCuestEnv.get(pregunta.getArea().getId()).equals(usuarioActual) && respuesta != null
                    && respuesta.isEmpty() == Boolean.FALSE) {
                RespuestaCuestionario respuestaCuestionario = crearRespuesta(pregunta);
                respuestaCuestionario.setRespuestaTexto(respuesta);
                if (pregunta.getTipoRespuesta() != null && pregunta.getTipoRespuesta().startsWith("ADJUNTO")) {
                    respuestaCuestionario.setDocumentos(visualizarCuestionario.getMapaDocumentos().get(pregunta));
                }
                listaRespuestas.add(respuestaCuestionario);
            }
        });
    }
    
    /**
     * Guarda en BBDD las respuestas de tipo TABLA o MATRIZ.
     * 
     * @param listaRespuestas Lista de respuestas
     * @see guardarRespuestas
     * 
     */
    private void guardarRespuestasTipoTablaMatriz(List<RespuestaCuestionario> listaRespuestas) {
        String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla = visualizarCuestionario.getMapaRespuestasTabla();
        
        mapaRespuestasTabla.forEach((pregunta, dataTableView) -> {
            // Guardar sólo las preguntas asignadas al usuario actual
            if (mapaAreaUsuarioCuestEnv.get(pregunta.getArea().getId()).equals(usuarioActual)
                    && dataTableView != null) {
                List<DatosTablaGenerica> listaDatosTabla = dataTableView.getListaDatosTabla();
                RespuestaCuestionario rtaCuestionario = crearRespuesta(pregunta);
                rtaCuestionario.setRespuestaTablaMatriz(listaDatosTabla);
                listaRespuestas.add(rtaCuestionario);
            }
        });
    }
    
    /**
     * Elimina la última fila de una respuesta tipo TABLA.
     * 
     * @param pregunta Pregunta de un cuestionario
     */
    public void eliminarFilaRespuestaTabla(PreguntasCuestionario pregunta) {
        Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla = visualizarCuestionario.getMapaRespuestasTabla();
        if (mapaRespuestasTabla.get(pregunta) != null) {
            DataTableView dataTableView = mapaRespuestasTabla.get(pregunta);
            dataTableView.eliminarFila();
            mapaRespuestasTabla.put(pregunta, dataTableView);
            visualizarCuestionario.setMapaRespuestasTabla(mapaRespuestasTabla);
        }
    }
    
    /**
     * Se crea un objeto Documento a partir del fichero que sube el usuario y se añade al mapa de documentos que se
     * visualiza en pantalla.
     * 
     * @param event Evento que contiene el fichero que sube el usuario
     */
    public void subirFichero(FileUploadEvent event) {
        UploadedFile archivoSubido = event.getFile();
        if (respuestaService.esExtensionCorrecta(archivoSubido)) {
            
            try {
                PreguntasCuestionario pregunta = (PreguntasCuestionario) event.getComponent().getAttributes()
                        .get("pregunta");
                
                RespuestaCuestionario respuestaCuestionario = crearRespuesta(pregunta);
                respuestaCuestionario.setRespuestaTexto(visualizarCuestionario.getMapaRespuestas().get(pregunta));
                
                Map<PreguntasCuestionario, List<Documento>> mapaDocumentos = visualizarCuestionario.getMapaDocumentos();
                if (mapaDocumentos.get(pregunta) != null) {
                    respuestaCuestionario.setDocumentos(mapaDocumentos.get(pregunta));
                } else {
                    respuestaCuestionario.setDocumentos(new ArrayList<>());
                }
                respuestaCuestionario = respuestaService.saveConDocumento(respuestaCuestionario, archivoSubido);
                
                visualizarCuestionario.getMapaDocumentos().put(pregunta, respuestaCuestionario.getDocumentos());
                
            } catch (DataAccessException | ProgesinException e) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                        "Se ha producido un error al subir el fichero. Inténtelo de nuevo más tarde.");
                regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
            }
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "La extensión del documento no corresponde con el documento subido");
        }
    }
    
    /**
     * Elimina el documento pasado como parámetro de la pregunta.
     * 
     * @param pregunta Pregunta a la que pertenece
     * @param documento Documento a eliminar
     */
    public void eliminarDocumento(PreguntasCuestionario pregunta, Documento documento) {
        try {
            RespuestaCuestionario respuestaCuestionario = crearRespuesta(pregunta);
            List<Documento> listaDocumentos = visualizarCuestionario.getMapaDocumentos().get(pregunta);
            listaDocumentos.remove(documento);
            respuestaCuestionario.setDocumentos(listaDocumentos);
            respuestaCuestionario = respuestaService.eliminarDocumentoRespuesta(respuestaCuestionario, documento);
            visualizarCuestionario.getMapaDocumentos().put(pregunta, respuestaCuestionario.getDocumentos());
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al eliminar el fichero. Inténtelo de nuevo más tarde.");
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
        }
    }
    
    /**
     * Obtiene el cuestionario a mostrar en función del usuario que se loguea.
     */
    @PostConstruct
    public void init() {
        User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (RoleEnum.ROLE_PROV_CUESTIONARIO.equals(usuarioActual.getRole())) {
            cuestionarioEnviado = respuestaService.buscaCuestionarioAResponder(usuarioActual.getCorreo());
            
            setUsuariosProv(userService.listaUsuariosProvisionalesCorreo(cuestionarioEnviado.getCorreoEnvio()));
            
            // Dependiendo de si es el usuario principal o no recuperamos todas las asociaciones o sólo las del usuario
            // actual
            if (usuarioActual.getUsername().equals(usuarioActual.getCorreo())) {
                listaAreasUsuarioCuestEnv = areaUsuarioCuestEnvService
                        .findByIdCuestionarioEnviado(cuestionarioEnviado.getId());
            } else {
                listaAreasUsuarioCuestEnv = areaUsuarioCuestEnvService.findByIdCuestionarioEnviadoAndUsuarioProv(
                        cuestionarioEnviado.getId(), usuarioActual.getUsername());
            }
            
            generarMapaAreaUsuarioCuestEnv();
            
            visualizarCuestionario.setListaAreasVisualizarUsuario(
                    areaCuestionarioService.findByIdIn(new ArrayList<>(mapaAreaUsuarioCuestEnv.keySet())));
            visualizarCuestionario.generarMapaAreasVisualizarUsuario();
            
            visualizarCuestionario.visualizarRespuestasCuestionario(cuestionarioEnviado);
        }
    }
    
    /**
     * Guarda los cambios realizados a la asignación de areas a usuarios provisionales secundarios por parte del
     * principal.
     * 
     */
    public void asignarAreas() {
        try {
            listaAreasUsuarioCuestEnv = areaUsuarioCuestEnvService
                    .asignarAreasUsuarioYActivar(listaAreasUsuarioCuestEnv);
            if (listaAreasUsuarioCuestEnv != null) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Asignación",
                        "Areas asignadas con éxito, cuando los usuarios elegidos completen su parte volverá a tener asignadas dichas areas y podrá enviar el cuestionario.");
                generarMapaAreaUsuarioCuestEnv();
            }
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al asignar areas del cuestionario. ");
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
        }
    }
    
    /**
     * Guarda las respuestas introducidas por el usuario en BBDD, incluidos los documentos subidos, desactiva el acceso
     * de dicho usuario provisional secundario y reasigna las areas al usuario principal.
     */
    public void guardarYAsignarAreasAlPrincipal() {
        try {
            List<RespuestaCuestionario> listaRespuestas = new ArrayList<>();
            guardarRespuestasTipoTexto(listaRespuestas);
            guardarRespuestasTipoTablaMatriz(listaRespuestas);
            
            String nombreUsuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
            
            respuestaService.guardarRespuestasYAsignarAreasPrincipal(listaRespuestas, nombreUsuarioActual,
                    cuestionarioEnviado.getCorreoEnvio(), listaAreasUsuarioCuestEnv);
            
            if (listaAreasUsuarioCuestEnv != null) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Cumplimentación",
                        "Guardado con éxito, su contribución al cuestionario ha finalizado.", "dialogMessageReasignar");
                generarMapaAreaUsuarioCuestEnv();
            }
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al finalizar su parte.");
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
        }
    }
    
    /**
     * Construye un mapa que relaciona las areas de un cuestionario enviado con los usuarios provisionales asignados
     * para responderlas, se utiliza para habilitar o inhabilitar la edición de los campos de respuesta en el formulario
     * del cuestionario.
     * 
     * @author EZENTIS
     */
    public void generarMapaAreaUsuarioCuestEnv() {
        mapaAreaUsuarioCuestEnv = new HashMap<>();
        
        listaAreasUsuarioCuestEnv.forEach(
                areaUsuario -> mapaAreaUsuarioCuestEnv.put(areaUsuario.getIdArea(), areaUsuario.getUsernameProv()));
        
    }
    
    /**
     * Permite descargar una plantilla anexa al cuestionario.
     * 
     * @param plantilla Plantilla a descargar
     * 
     */
    public void descargarPlantilla(Documento plantilla) {
        try {
            this.setFile(respuestaService.descargarPlantilla(plantilla.getId()));
        } catch (ProgesinException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error descargar la plantilla.");
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
        }
    }
    
}
