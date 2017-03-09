package es.mira.progesin.web.beans.cuestionarios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionarioId;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.enums.TiposRespuestasPersonalizables;
import es.mira.progesin.persistence.repositories.IAreaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.DataTableView;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Controller("modificarModeloCuestionarioBean")
@Scope("session")

public class ModificarModeloCuestionarioBean {
    
    ModeloCuestionario modeloCuestionario;
    
    private DataTableView datosTabla;
    
    private String tipoSeleccionado;
    
    private List<PreguntasCuestionario> listaPreguntasBajaLogica;
    
    private List<AreasCuestionario> listaAreasCuestionario;
    
    private List<String> listaTipoPreguntas;
    
    private List<String> listaTipoPreguntasFinal;
    
    private DualListModel<String> listaTipoPreguntasPick;
    
    private boolean esNuevoModelo;
    
    @Autowired
    IModeloCuestionarioService modeloCuestionarioService;
    
    @Autowired
    INotificacionService notificacionesService;
    
    @Autowired
    IRegistroActividadService registroActividadService;
    
    @Autowired
    IConfiguracionRespuestasCuestionarioRepository configuracionRespuestasCuestionarioRepository;
    
    AreasCuestionario areaSelec = new AreasCuestionario();
    
    PreguntasCuestionario preguntaSelec;
    
    // Nuevo tipo pregunta
    private String tipoPersonalizado = "";
    
    private List<TiposRespuestasPersonalizables> listaTiposPersonalizables;
    
    private List<String> listadoValoresNuevaRespuesta;
    
    private List<String> listadoValoresFila;
    
    private String valorSeleccionado;
    
    @Autowired
    private IAreaCuestionarioRepository areaCuestionarioRepository;
    
    @Autowired
    private IPreguntaCuestionarioRepository preguntasCuestionarioRepository;
    
    public String editarModelo(ModeloCuestionario modeloCuestionario) {
        this.esNuevoModelo = false;
        this.modeloCuestionario = modeloCuestionario;
        listaAreasCuestionario = areaCuestionarioRepository
                .findDistinctByIdCuestionarioOrderByOrdenAsc(modeloCuestionario.getId());
        listaTipoPreguntas = configuracionRespuestasCuestionarioRepository
                .findAllDistinctTipoRespuestaOrderByTipoRespuestaAsc();
        listaTipoPreguntasFinal = new ArrayList<>();
        
        listaTipoPreguntasPick = new DualListModel<>(listaTipoPreguntas, listaTipoPreguntasFinal);
        
        listaTiposPersonalizables = Arrays.asList(TiposRespuestasPersonalizables.values());
        listadoValoresNuevaRespuesta = new ArrayList<>();
        listadoValoresFila = new ArrayList<>();
        tipoPersonalizado = "";
        
        return "/cuestionarios/modificarModeloCuestionario?faces-redirect=true";
    }
    
    public String nuevoModelo() {
        this.esNuevoModelo = true;
        this.modeloCuestionario = new ModeloCuestionario();
        
        listaAreasCuestionario = new ArrayList<>();
        listaTipoPreguntas = configuracionRespuestasCuestionarioRepository
                .findAllDistinctTipoRespuestaOrderByTipoRespuestaAsc();
        
        listaTiposPersonalizables = Arrays.asList(TiposRespuestasPersonalizables.values());
        listaTipoPreguntasFinal = new ArrayList<>();
        
        listaTipoPreguntasPick = new DualListModel<>(listaTipoPreguntas, listaTipoPreguntasFinal);
        
        listadoValoresNuevaRespuesta = new ArrayList<>();
        listadoValoresFila = new ArrayList<>();
        tipoPersonalizado = "";
        
        return "/cuestionarios/modificarModeloCuestionario?faces-redirect=true";
    }
    
    public void aniadeArea(String nombreArea) {
        if (nombreArea.isEmpty() == Boolean.FALSE) {
            AreasCuestionario areaAux = new AreasCuestionario();
            areaAux.setArea(nombreArea);
            areaAux.setIdCuestionario(modeloCuestionario.getId());
            areaAux.setPreguntas(new ArrayList<PreguntasCuestionario>());
            listaAreasCuestionario.add(areaAux);
            modeloCuestionario.setAreas(listaAreasCuestionario);
        }
    }
    
    public void onSelectArea(SelectEvent event) {
        areaSelec = (AreasCuestionario) event.getObject();
    }
    
    public void borraArea() {
        if (areaSelec != null) {
            if (areaSelec.getId() != null) {
                // Comprobamos si el área está siendo usada en algún
                // cuestionario personalizado
                AreasCuestionario areaUsada = areaCuestionarioRepository
                        .findAreaExistenteEnCuestionariosPersonalizados(areaSelec.getId());
                if (areaUsada != null) {
                    // baja lógica
                    int index = listaAreasCuestionario.indexOf(areaSelec);
                    areaSelec.setFechaBaja(new Date());
                    areaSelec.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
                    listaAreasCuestionario.set(index, areaSelec);
                } else {
                    // baja física
                    listaAreasCuestionario.remove(areaSelec);
                }
            } else {
                // Es un area que acaba de añadir el usuario
                borraAreaNueva(listaAreasCuestionario, areaSelec);
            }
            modeloCuestionario.setAreas(listaAreasCuestionario);
        }
    }
    
    /**
     * Borra del listado de áreas la nueva área introducida en el modelo a través de la pantalla de edición
     * 
     * @param listado Listado de áreas actual
     * @param area El área a eliminar del listado
     */
    private void borraAreaNueva(List<AreasCuestionario> listado, AreasCuestionario area) {
        boolean noEncontrada = true;
        for (int i = 0; i < listado.size() && noEncontrada; i++) {
            AreasCuestionario a = listado.get(i);
            if (a.getId() == null && a.getArea().equals(area.getArea())) {
                listado.remove(i);
                noEncontrada = false;
            }
        }
    }
    
    public void aniadePregunta(AreasCuestionario area, String pregunta, String tipo) {
        if (!pregunta.isEmpty() && !tipo.isEmpty()) {
            PreguntasCuestionario preguntaAux = new PreguntasCuestionario();
            preguntaAux.setArea(area);
            preguntaAux.setPregunta(pregunta);
            preguntaAux.setTipoRespuesta(tipo);
            List<PreguntasCuestionario> listado = area.getPreguntas();
            listado.add(preguntaAux);
            area.setPreguntas(listado);
        } else {
            String textoError = "Debe escribir un texto para la pregunta y seleccionar un tipo de respuesta";
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, textoError, null, null);
        }
    }
    
    public void onSelectPregunta(SelectEvent event) {
        preguntaSelec = (PreguntasCuestionario) event.getObject();
    }
    
    public void borraPregunta(AreasCuestionario area) {
        if (preguntaSelec != null) {
            List<PreguntasCuestionario> listado = area.getPreguntas();
            // Comprobar si la pregunta se ha usado en el cuestionario personalizado. Si se ha usado, baja lógica, si
            // no, baja física
            if (preguntaSelec.getId() != null) {
                PreguntasCuestionario preguntaEnCuestionarioPersonalizado = preguntasCuestionarioRepository
                        .findPreguntaExistenteEnCuestionariosPersonalizados(preguntaSelec.getId());
                if (preguntaEnCuestionarioPersonalizado != null) {
                    // baja lógica
                    int index = listado.indexOf(preguntaSelec);
                    preguntaSelec.setFechaBaja(new Date());
                    preguntaSelec.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
                    listado.set(index, preguntaSelec);
                } else {
                    // baja física
                    listado.remove(preguntaSelec);
                }
            } else {
                // No existía la pregunta, es nueva, la han añadido al modelo, por lo tanto no tiene id y no se puede
                // hacer un remove del objeto, ya que el equals es con el id
                borraPreguntaNueva(listado, preguntaSelec);
            }
            
            area.setPreguntas(listado);
        }
    }
    
    /**
     * Borra del listado de preguntas la nueva pregunta introducida en el modelo a través de la pantalla de edición del
     * modelo
     * 
     * @param listado Listado de preguntas actual
     * @param pregunta Pregunta a eliminar del listado
     */
    private void borraPreguntaNueva(List<PreguntasCuestionario> listado, PreguntasCuestionario pregunta) {
        boolean noEncontrada = true;
        for (int i = 0; i < listado.size() && noEncontrada; i++) {
            PreguntasCuestionario p = listado.get(i);
            if (p.getId() == null && p.getPregunta().equals(pregunta.getPregunta())) {
                listado.remove(i);
                noEncontrada = false;
            }
        }
    }
    
    public String onFlowProcess(FlowEvent event) {
        boolean correcto = true;
        String textoError = "";
        if ("tipoRespuestas".equals(event.getNewStep()) && listaAreasCuestionario.isEmpty()) {
            correcto = false;
            textoError = "Debe añadir al menos un área para poder pasar de pantalla";
        }
        
        if ("preguntas".equals(event.getNewStep())) {
            listaAreasCuestionario = ordenarAreas(listaAreasCuestionario);
        }
        
        if ("finalizar".equals(event.getNewStep())) {
            for (AreasCuestionario area : listaAreasCuestionario) {
                List<PreguntasCuestionario> lista = area.getPreguntas();
                if (lista.isEmpty()) {
                    correcto = false;
                    textoError = "Debe asignar preguntas a todas las áreas para poder pasar a la siguiente pantalla";
                } else {
                    lista = ordenarPreguntas(area.getPreguntas());
                    area.setPreguntas(lista);
                }
            }
        }
        
        if (correcto) {
            return event.getNewStep();
        } else {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, textoError, null, null);
            return event.getOldStep();
        }
    }
    
    public List<String> getValoresTipoRespuesta(String tipo) {
        return configuracionRespuestasCuestionarioRepository.findValoresPorSeccion(tipo);
    }
    
    public void aniadeValor(String valor, String tipoRespuesta) {
        if ((!valor.isEmpty() && Constantes.TIPO_RESPUESTA_RADIO.equals(tipoRespuesta))
                || (!valor.isEmpty() && listadoValoresNuevaRespuesta.size() < 9)) {
            listadoValoresNuevaRespuesta.add(valor);
        }
        
    }
    
    public void borraValor() {
        if (valorSeleccionado != null) {
            listadoValoresNuevaRespuesta.remove(valorSeleccionado);
        }
    }
    
    public void onSelectValor(SelectEvent event) {
        valorSeleccionado = event.getObject().toString();
    }
    
    public void aniadeValorFila(String valorFila) {
        listadoValoresFila.add(valorFila);
    }
    
    public void borraValorFila() {
        listadoValoresFila.remove(valorSeleccionado);
    }
    
    public void guardaTipoRespuesta(String nombreTipoPregunta) {
        try {
            String textoError = "";
            if (nombreTipoPregunta.isEmpty()) {
                textoError = "Introdzca el nombre de la respuesta";
            } else if (listadoValoresNuevaRespuesta.isEmpty()) {
                textoError = "Introduzca los valores del tipo de respuesta";
            } else if (Constantes.TIPO_RESPUESTA_MATRIZ.equals(tipoPersonalizado) && listadoValoresFila.isEmpty()) {
                textoError = "Introduzca los valores para las filas";
            }
            if (textoError.isEmpty()) {
                String seccion = tipoPersonalizado.concat(nombreTipoPregunta);
                List<ConfiguracionRespuestasCuestionario> tipoRespuestaNuevo = new ArrayList<>();
                
                for (int i = 0; i < listadoValoresNuevaRespuesta.size(); i++) {
                    String valor = listadoValoresNuevaRespuesta.get(i);
                    ConfiguracionRespuestasCuestionario nuevoValor = new ConfiguracionRespuestasCuestionario();
                    ConfiguracionRespuestasCuestionarioId datos = new ConfiguracionRespuestasCuestionarioId();
                    
                    datos.setSeccion(seccion);
                    datos.setValor(valor);
                    datos.setClave("campo" + (i + 1));
                    nuevoValor.setConfig(datos);
                    tipoRespuestaNuevo.add(nuevoValor);
                }
                
                // Valores de fila para Matriz
                for (int i = 0; i < listadoValoresFila.size(); i++) {
                    String valor = listadoValoresFila.get(i);
                    ConfiguracionRespuestasCuestionario nuevoValor = new ConfiguracionRespuestasCuestionario();
                    ConfiguracionRespuestasCuestionarioId datos = new ConfiguracionRespuestasCuestionarioId();
                    datos.setSeccion(seccion);
                    datos.setValor(valor);
                    datos.setClave("nombreFila" + (i + 1));
                    nuevoValor.setConfig(datos);
                    tipoRespuestaNuevo.add(nuevoValor);
                }
                
                configuracionRespuestasCuestionarioRepository.save(tipoRespuestaNuevo);
                // Actualizar lista
                List<String> nuevoListado = configuracionRespuestasCuestionarioRepository
                        .findAllDistinctTipoRespuestaOrderByTipoRespuestaAsc();
                
                listaTipoPreguntasPick.setSource(nuevoListado);
                
                listadoValoresNuevaRespuesta = new ArrayList<>();
                listadoValoresFila = new ArrayList<>();
                tipoPersonalizado = "";
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, textoError, null, null);
            }
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR,
                    "Se ha producido un error al guardar el tipo de respuesta", e.getMessage());
        }
    }
    
    public void guardaCuestionario() {
        
        try {
            modeloCuestionarioService.save(modeloCuestionario);
            
            String descripcion = "Se modifica el modelo de cuestionario :".concat(modeloCuestionario.getDescripcion());
            
            registroActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.CUESTIONARIO.getDescripcion());
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "",
                    "El cuestionario se ha modificado con éxito");
            
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR,
                    "Se ha producido un error al guardar el cuestionario", e.getMessage());
        }
    }
    
    public void guardaNuevoCuestionario() {
        try {
            modeloCuestionario.setAreas(listaAreasCuestionario);
            modeloCuestionarioService.save(modeloCuestionario);
            String descripcion = "Se crea el modelo de cuestionario: ".concat(modeloCuestionario.getDescripcion());
            
            registroActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.CUESTIONARIO.getDescripcion());
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "",
                    "El cuestionario se ha creado con éxito");
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR,
                    "Se ha producido un error al crear el cuestionario", e.getMessage());
        }
    }
    
    public List<AreasCuestionario> ordenarAreas(List<AreasCuestionario> listado) {
        List<AreasCuestionario> listaNueva = new ArrayList<>();
        for (int i = 0; i < listado.size(); i++) {
            AreasCuestionario area = listado.get(i);
            area.setOrden(i);
            listaNueva.add(area);
        }
        return listaNueva;
    }
    
    public List<PreguntasCuestionario> ordenarPreguntas(List<PreguntasCuestionario> listado) {
        List<PreguntasCuestionario> listaNueva = new ArrayList<>();
        for (int i = 0; i < listado.size(); i++) {
            PreguntasCuestionario pregunta = listado.get(i);
            pregunta.setOrden(i);
            listaNueva.add(pregunta);
        }
        return listaNueva;
    }
    
    public void onSelectTipo(SelectEvent event) {
        tipoSeleccionado = event.getObject().toString();
        if (tipoSeleccionado.startsWith(Constantes.TIPO_RESPUESTA_TABLA)
                || tipoSeleccionado.startsWith(Constantes.TIPO_RESPUESTA_MATRIZ)) {
            datosTabla = new DataTableView();
            construirTipoRespuestaTablaMatrizVacia(tipoSeleccionado);
        }
    }
    
    private void construirTipoRespuestaTablaMatrizVacia(String tipo) {
        List<ConfiguracionRespuestasCuestionario> valoresColumnas = configuracionRespuestasCuestionarioRepository
                .findByConfigSeccionOrderByConfigClaveAsc(tipo);
        if (tipo != null && tipo.startsWith(Constantes.TIPO_RESPUESTA_TABLA)) {
            datosTabla.crearTabla(valoresColumnas);
        } else {
            datosTabla.crearMatriz(valoresColumnas);
        }
    }
}
