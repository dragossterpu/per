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

/**
 * 
 * Bean para la creación y modificación de modelos de cuestionario
 * 
 * @author Ezentis
 *
 */

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
    
    // Lista para mostrar en pantalla las áreas y que el cebreado se visualice correctamente
    private List<AreasCuestionario> listaAreasCuestionarioVisualizar;
    
    private List<String> listaTipoPreguntas;
    
    private List<String> listaTipoPreguntasFinal;
    
    private DualListModel<String> listaTipoPreguntasPick;
    
    private boolean esNuevoModelo;
    
    @Autowired
    private IModeloCuestionarioService modeloCuestionarioService;
    
    @Autowired
    private INotificacionService notificacionesService;
    
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    @Autowired
    private IConfiguracionRespuestasCuestionarioRepository configuracionRespuestasCuestionarioRepository;
    
    private AreasCuestionario areaSelec = new AreasCuestionario();
    
    private PreguntasCuestionario preguntaSelec;
    
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
    
    /**
     * Incicia el proceso de edición de un modelo de cuestionario recibido como parámetro
     * 
     * @param modeloCuestionario
     * @return String
     */
    public String editarModelo(ModeloCuestionario modeloCuestionario) {
        this.esNuevoModelo = false;
        this.modeloCuestionario = modeloCuestionario;
        listaAreasCuestionario = areaCuestionarioRepository
                .findDistinctByIdCuestionarioOrderByOrdenAsc(modeloCuestionario.getId());
        listaAreasCuestionarioVisualizar = areaCuestionarioRepository
                .findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(modeloCuestionario.getId());
        // Asigno las preguntas que no tengan fecha de baja a su área respectiva
        listaAreasCuestionarioVisualizar.forEach(area -> area
                .setPreguntas(preguntasCuestionarioRepository.findByAreaAndFechaBajaIsNullOrderByOrdenAsc(area)));
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
    
    /**
     * 
     * Inicia el proceso de alta de un nuevo modelo de cuestionario
     * 
     * @return String
     */
    public String nuevoModelo() {
        this.esNuevoModelo = true;
        this.modeloCuestionario = new ModeloCuestionario();
        
        listaAreasCuestionario = new ArrayList<>();
        listaAreasCuestionarioVisualizar = new ArrayList<>();
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
    
    /**
     * Permite añadir áreas al modelo de cuestionario
     * 
     * @param nombreArea Nombre del área a añadir
     * 
     */
    public void aniadeArea(String nombreArea) {
        if (nombreArea.isEmpty() == Boolean.FALSE) {
            AreasCuestionario areaAux = new AreasCuestionario();
            areaAux.setArea(nombreArea);
            areaAux.setIdCuestionario(modeloCuestionario.getId());
            areaAux.setPreguntas(new ArrayList<PreguntasCuestionario>());
            listaAreasCuestionario.add(areaAux);
            listaAreasCuestionarioVisualizar.add(areaAux);
            modeloCuestionario.setAreas(listaAreasCuestionario);
        }
    }
    
    /**
     * 
     * Se guarda el área seleccionada por el usuario en la vista
     * 
     * @param event Se recibe un evento que contiene el área seleccionada
     */
    public void onSelectArea(SelectEvent event) {
        areaSelec = (AreasCuestionario) event.getObject();
    }
    
    /**
     * Elimina un área del modelo de cuestionario. Antes de hacer el borrado se comprueba si el área está siendo usada
     * en un cuestionario personalizado. En caso afirmativo la eliminación será lógica para que pueda seguir empleándose
     * en los personalizados. En caso negativo se realizará un borrado físico y se eliminará de la BDD
     */
    public void borraArea() {
        if (areaSelec != null) {
            if (areaSelec.getId() != null) {
                // Comprobamos si el área está siendo usada en algún
                // cuestionario personalizado
                AreasCuestionario areaUsada = areaCuestionarioRepository
                        .findAreaExistenteEnCuestionariosPersonalizados(areaSelec.getId());
                if (areaUsada != null) {
                    // baja lógica
                    AreasCuestionario areaGrabar = listaAreasCuestionario
                            .get(listaAreasCuestionario.indexOf(areaSelec));
                    areaGrabar.setFechaBaja(new Date());
                    areaGrabar.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
                } else {
                    // baja física
                    listaAreasCuestionario.remove(areaSelec);
                }
                listaAreasCuestionarioVisualizar.remove(areaSelec);
            } else {
                // Es un area que acaba de añadir el usuario
                listaAreasCuestionario.removeIf(a -> a.getArea().equals(areaSelec.getArea()));
                listaAreasCuestionarioVisualizar.removeIf(a -> a.getArea().equals(areaSelec.getArea()));
                
            }
            modeloCuestionario.setAreas(listaAreasCuestionario);
        }
    }
    
    /**
     * Añade preguntas a un área. Se reciben como parámetros el área al que pertenecerá, el texto de la pregunta y su
     * tipo.
     * 
     * @param area
     * @param pregunta
     * @param tipo
     */
    public void aniadePregunta(AreasCuestionario area, String pregunta, String tipo) {
        if (!pregunta.isEmpty() && !tipo.isEmpty()) {
            PreguntasCuestionario preguntaAux = new PreguntasCuestionario();
            preguntaAux.setArea(area);
            preguntaAux.setPregunta(pregunta);
            preguntaAux.setTipoRespuesta(tipo);
            List<PreguntasCuestionario> listado = area.getPreguntas();
            listado.add(preguntaAux);
            area.setPreguntas(listado);
            if (area.getId() != null) {
                listaAreasCuestionario.get(listaAreasCuestionario.indexOf(area)).getPreguntas().add(preguntaAux);
            }
        } else {
            String textoError = "Debe escribir un texto para la pregunta y seleccionar un tipo de respuesta";
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, textoError, null, null);
        }
    }
    
    /**
     * Almacena el valor de la pregunta seleccionada por el usuario en la vista
     * 
     * @param event
     */
    public void onSelectPregunta(SelectEvent event) {
        preguntaSelec = (PreguntasCuestionario) event.getObject();
    }
    
    /**
     * Elimina una pregunta del modelo. Se hace una comprobación de si la pregunta está en uso en algún cuestionario
     * personalizado. En caso afirmativo la eliminación será lógica mientras que lo será física en caso contrario.
     * 
     * @param area
     */
    public void borraPregunta(AreasCuestionario area) {
        if (preguntaSelec != null) {
            List<PreguntasCuestionario> listado;
            // Obtengo la lista de preguntas originales del área
            if (area.getId() != null && listaAreasCuestionario.contains(area)) {
                listado = listaAreasCuestionario.get(listaAreasCuestionario.indexOf(area)).getPreguntas();
            } else {
                listado = area.getPreguntas();
            }
            
            List<PreguntasCuestionario> listadoPreguntasVisualizar = new ArrayList<>();
            listadoPreguntasVisualizar.addAll(area.getPreguntas());
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
                listadoPreguntasVisualizar.remove(preguntaSelec);
            } else {
                // No existía la pregunta, es nueva, la han añadido al modelo, por lo tanto no tiene id y no se puede
                // hacer un remove del objeto, ya que el equals es con el id
                listado.removeIf(p -> p.getPregunta().equals(preguntaSelec.getPregunta()));
                listadoPreguntasVisualizar.removeIf(p -> p.getPregunta().equals(preguntaSelec.getPregunta()));
            }
            
            // Para cambiar las preguntas a visualizar en pantalla
            area.setPreguntas(listadoPreguntasVisualizar);
            
        }
    }
    
    /**
     * Este método controla el flujo de la pantalla permitiendo avanzar y retroceder entre los diversos pasos. Tambien
     * controla que cada paso haya sido completado antes de continuar al siguiente.
     * 
     * @param event
     * @return String
     */
    public String onFlowProcess(FlowEvent event) {
        boolean correcto = true;
        String textoError = "";
        if ("tipoRespuestas".equals(event.getNewStep()) && listaAreasCuestionario.isEmpty()) {
            correcto = false;
            textoError = "Debe añadir al menos un área para poder pasar de pantalla";
        }
        
        if ("preguntas".equals(event.getNewStep())) {
            // listaAreasCuestionario = ordenarAreas(listaAreasCuestionario);
            // modeloCuestionario.setAreas(listaAreasCuestionario);
            listaAreasCuestionarioVisualizar = ordenarAreas(listaAreasCuestionarioVisualizar);
        }
        
        if ("finalizar".equals(event.getNewStep())) {
            for (AreasCuestionario area : listaAreasCuestionarioVisualizar) {
                List<PreguntasCuestionario> lista = area.getPreguntas();
                if (lista.isEmpty()) {
                    correcto = false;
                    textoError = "Debe asignar preguntas a todas las áreas para poder pasar a la siguiente pantalla";
                } else {
                    ordenarPreguntas(area);
                }
            }
            modeloCuestionario.setAreas(listaAreasCuestionario);
        }
        
        if (correcto) {
            return event.getNewStep();
        } else {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, textoError, null, null);
            return event.getOldStep();
        }
    }
    
    /**
     * 
     * Recupera el listado de valores posibles para un tipo de respuesta
     * 
     * @param tipo
     * @return List<String>
     */
    public List<String> getValoresTipoRespuesta(String tipo) {
        return configuracionRespuestasCuestionarioRepository.findValoresPorSeccion(tipo);
    }
    
    /**
     * 
     * Añade un nuevo valor a un tipo de respuesta
     * 
     * @param valor
     * @param tipoRespuesta
     */
    public void aniadeValor(String valor, String tipoRespuesta) {
        if ((!valor.isEmpty() && Constantes.TIPO_RESPUESTA_RADIO.equals(tipoRespuesta))
                || (!valor.isEmpty() && listadoValoresNuevaRespuesta.size() <= 20)) {
            listadoValoresNuevaRespuesta.add(valor);
        }
        
    }
    
    /**
     * 
     * Borra uno de los posibles valores de un tipo de respuesta. Elimina el valor que previamente se ha seleccionado
     * 
     * @see onSelectValor
     */
    public void borraValor() {
        if (valorSeleccionado != null) {
            listadoValoresNuevaRespuesta.remove(valorSeleccionado);
        }
    }
    
    /**
     * 
     * Almacena en una variable el valor seleccionado por el usuario en la vista
     * 
     * @param event
     */
    public void onSelectValor(SelectEvent event) {
        valorSeleccionado = event.getObject().toString();
    }
    
    /**
     * Añade un nuevo valor a la lista de valores de fila (para matrices/tablas)
     * 
     * @param valorFila
     */
    public void aniadeValorFila(String valorFila) {
        listadoValoresFila.add(valorFila);
    }
    
    /**
     * Elimina un valor en la lista e valores de fila (para matrices/tablas) Elimina el valor que previamente se ha
     * seleccionado
     * 
     * @see onSelectValor
     */
    public void borraValorFila() {
        listadoValoresFila.remove(valorSeleccionado);
    }
    
    /**
     * Almacena en la base de datos un nuevo tipo de respuesta. Se actualiza la lista de tipos disponibles.
     * 
     * @param nombreTipoPregunta
     */
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
                    datos.setClave("campo" + String.format("%02d", i + 1));
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
                    datos.setClave("nombreFila" + String.format("%02d", i + 1));
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
    
    /**
     * Se almacena en base de datos el modelo de cuestionario modificado
     * 
     */
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
    
    /**
     * Se almacena en base de datos el nuevo modelo de cuestionario
     * 
     */
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
    
    /**
     * Se asigna un valor de orden a las áreas según su posición dentro de la lista de áreas
     * 
     * @param listado List<AreasCuestionario>
     * @return List<AreasCuestionario>
     */
    public List<AreasCuestionario> ordenarAreas(List<AreasCuestionario> listado) {
        List<AreasCuestionario> listaNueva = new ArrayList<>();
        for (int i = 0; i < listado.size(); i++) {
            AreasCuestionario area = listado.get(i);
            area.setOrden(i);
            listaNueva.add(area);
            
            ordenarListaGrabar(area);
        }
        return listaNueva;
    }
    
    private void ordenarListaGrabar(AreasCuestionario areaVisualizar) {
        if (areaVisualizar.getId() != null) {
            listaAreasCuestionario.get(listaAreasCuestionario.indexOf(areaVisualizar))
                    .setOrden(areaVisualizar.getOrden());
        } else {
            // área nueva
            for (AreasCuestionario areaGrabar : listaAreasCuestionario) {
                if (areaGrabar.getArea().equals(areaVisualizar.getArea()) && areaGrabar.getFechaBaja() == null) {
                    areaGrabar.setOrden(areaVisualizar.getOrden());
                }
            }
        }
    }
    
    /**
     * Se asigna un valor de orden a las preguntas según su posición dentro de la lista de preguntas de su área
     * 
     * @param area Área a la se le quieren ordenar las preguntas
     * 
     */
    public void ordenarPreguntas(AreasCuestionario area) {
        List<PreguntasCuestionario> listado = area.getPreguntas();
        for (int i = 0; i < listado.size(); i++) {
            PreguntasCuestionario pregunta = listado.get(i);
            pregunta.setOrden(i);
            ordenarPreguntasGrabar(area, pregunta);
        }
        modeloCuestionario.setAreas(listaAreasCuestionario);
    }
    
    /**
     * Se asigna un valor de orden a las preguntas según su posición dentro de la lista de preguntas de su área
     * 
     * @param area Área a la se le quieren ordenar las preguntas
     * @param pregunta Pregunta modificada con el nuevo orden que hay que actualizar en la lista de preguntas a grabar
     */
    private void ordenarPreguntasGrabar(AreasCuestionario area, PreguntasCuestionario pregunta) {
        int index = listaAreasCuestionario.indexOf(area);
        AreasCuestionario areaGrabar = listaAreasCuestionario.get(index);
        List<PreguntasCuestionario> listado = areaGrabar.getPreguntas();
        
        for (PreguntasCuestionario preguntaGrabar : listado) {
            if (pregunta.getId() != null && pregunta.equals(preguntaGrabar)) {
                int i = pregunta.getOrden();
                preguntaGrabar.setOrden(i);
            } else if (preguntaGrabar.getPregunta().equals(pregunta.getPregunta()) && pregunta.getId() == null
                    && pregunta.getFechaBaja() == null) {
                int i = pregunta.getOrden();
                preguntaGrabar.setOrden(i);
            }
        }
    }
    
    /**
     * Construye una visualización de tipo de respuesta seleccionado de forma que el usuario pueda ver de forma más
     * gráfica el tipo de respuesta que está añadiendo al cuestionario.
     * 
     * @param event
     */
    public void onSelectTipo(SelectEvent event) {
        setTipoSeleccionado(event.getObject().toString());
        if (tipoSeleccionado.startsWith(Constantes.TIPO_RESPUESTA_TABLA)
                || tipoSeleccionado.startsWith(Constantes.TIPO_RESPUESTA_MATRIZ)) {
            datosTabla = new DataTableView();
            construirTipoRespuestaTablaMatrizVacia(tipoSeleccionado);
        }
    }
    
    /**
     * 
     * Consutruye una tabla o matriz vacía para su visualización
     * 
     * @see onSelectTipo
     * 
     */
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
