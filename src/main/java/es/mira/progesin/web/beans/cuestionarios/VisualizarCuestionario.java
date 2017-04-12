package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IDatosTablaGenericaRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.DataTableView;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.PdfGenerator;
import es.mira.progesin.util.WordGenerator;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean para la visualización de los cuestionarios por pantalla
 * 
 * @author EZENTIS
 *
 */
@Setter
@Getter
@Controller("visualizarCuestionario")
@Scope("session")
public class VisualizarCuestionario implements Serializable {
    
    private static final String NOMBRESECCION = "Visualizar cuestionario";
    
    @Autowired
    private transient IConfiguracionRespuestasCuestionarioRepository configuracionRespuestaRepository;
    
    @Autowired
    private transient IRespuestaCuestionarioRepository respuestaRepository;
    
    @Autowired
    private transient IDatosTablaGenericaRepository datosTablaRepository;
    
    @Autowired
    private transient IPreguntaCuestionarioRepository preguntasRepository;
    
    @Autowired
    transient IRegistroActividadService regActividadService;
    
    @Autowired
    transient IDocumentoService documentoService;
    
    private static final long serialVersionUID = 1L;
    
    // para la visualización
    private CuestionarioEnvio cuestionarioEnviado;
    
    // para la visualización
    private CuestionarioPersonalizado cuestionarioPersonalizado;
    
    private Map<AreasCuestionario, List<PreguntasCuestionario>> mapaAreaPreguntas;
    
    private List<AreasCuestionario> areas;
    
    private Map<AreasCuestionario, Boolean> mapaValidacionAreas;
    
    private Map<PreguntasCuestionario, Boolean> mapaValidacionRespuestas;
    
    private Map<PreguntasCuestionario, String> mapaRespuestas;
    
    private Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla;
    
    private HashMap<PreguntasCuestionario, List<DatosTablaGenerica>> mapaRespuestasTablaAux;
    
    private Map<PreguntasCuestionario, List<Documento>> mapaDocumentos;
    
    private List<RespuestaCuestionario> listaRespuestas;
    
    private transient StreamedContent file;
    
    @Autowired
    private transient WordGenerator wordGenerator;
    
    @Autowired
    private transient PdfGenerator pdfGenerator;
    
    private User usuarioActual;
    
    private boolean esUsuarioProvisional;
    
    private List<AreasCuestionario> listaAreasVisualizarUsuario;
    
    private Map<Long, AreasCuestionario> mapaAreasVisualizarUsuario;
    
    /**
     * Muestra en pantalla el cuestionario personalizado, mostrando las diferentes opciones de responder (cajas de
     * texto, adjuntos, tablas...)
     * 
     * @author EZENTIS
     * @param cuestionario que se desea visualizar
     * @return Nombre de la vista a mostrar
     */
    public String visualizarVacio(CuestionarioPersonalizado cuestionario) {
        cuestionarioEnviado = null;
        mapaRespuestasTabla = new HashMap<>();
        mapaRespuestas = new HashMap<>();
        mapaDocumentos = new HashMap<>();
        return visualizar(cuestionario, false, false);
    }
    
    /**
     * Muestra en pantalla el cuestionario con las respuestas de la unidad inspeccionada
     * 
     * @author EZENTIS
     * @param cuestionarioEnviado seleccionado en los resultados de la búsqueda
     * @return vista desde la que ha sido llamada responderCuestionario o validarCuestionario
     */
    public String visualizarRespuestasCuestionario(CuestionarioEnvio cuestionarioEnviado) {
        this.setCuestionarioEnviado(cuestionarioEnviado);
        setMapaRespuestas(new HashMap<>());
        setMapaRespuestasTabla(new HashMap<>());
        setMapaDocumentos(new HashMap<>());
        setMapaRespuestasTablaAux(new HashMap<>());
        setMapaValidacionAreas(new HashMap<>());
        setMapaValidacionRespuestas(new HashMap<>());
        
        // Para inspectores se recuperan todas las respuestas
        // Para usuarios provisionales sólo las no validadas, todas si es el principal y sólo las que tengan asignadas
        // para el resto
        if (esUsuarioProvisional) {
            setListaRespuestas(respuestaRepository
                    .findDistinctByRespuestaIdCuestionarioEnviadoAndFechaValidacionIsNullAndRespuestaIdPreguntaAreaIn(
                            cuestionarioEnviado, listaAreasVisualizarUsuario));
        } else {
            setListaRespuestas(respuestaRepository.findDistinctByRespuestaIdCuestionarioEnviado(cuestionarioEnviado));
        }
        
        listaRespuestas.forEach(respuesta -> {
            PreguntasCuestionario pregunta = respuesta.getRespuestaId().getPregunta();
            String tipoRespuesta = pregunta.getTipoRespuesta();
            if ((tipoRespuesta.startsWith(Constantes.TIPO_RESPUESTA_TABLA)
                    || tipoRespuesta.startsWith(Constantes.TIPO_RESPUESTA_MATRIZ))
                    && respuesta.getRespuestaTablaMatriz() != null) {
                mapaRespuestasTablaAux.put(pregunta, respuesta.getRespuestaTablaMatriz());
            } else {
                mapaRespuestas.put(pregunta, respuesta.getRespuestaTexto());
                if (respuesta.getDocumentos() != null && respuesta.getDocumentos().isEmpty() == Boolean.FALSE) {
                    mapaDocumentos.put(pregunta, respuesta.getDocumentos());
                }
            }
            mapaValidacionAreas.putIfAbsent(pregunta.getArea(), true);
            mapaValidacionRespuestas.put(pregunta, respuesta.getFechaValidacion() != null);
            if (respuesta.getFechaValidacion() == null) {
                mapaValidacionAreas.replace(pregunta.getArea(), false);
            }
        });
        
        return visualizar(cuestionarioEnviado.getCuestionarioPersonalizado(),
                listaRespuestas.isEmpty() == Boolean.FALSE,
                esUsuarioProvisional && cuestionarioEnviado.getFechaNoConforme() != null);
    }
    
    /**
     * Carga las preguntas de un cuestionario en base a su modelo personalizado y construye la estructura de aquellas
     * que precisen una tabla o matriz para representar su respuesta
     * 
     * @author EZENTIS
     * @param cuestionario seleccionado
     * @param visualizarRespuestas indica si se llama para mostrar un cuestionario vacío o ya respondido
     * @param soloNoValidadas indica si hay que mostrar todas las preguntas/respuestas o sólo aquellas que aún no han
     * sido validadas
     * @return vista desde la que ha sido llamada en base a si ya ha sido enviado, responder/validarCuestionario
     */
    private String visualizar(CuestionarioPersonalizado cuestionario, boolean visualizarRespuestas,
            boolean soloNoValidadas) {
        setMapaAreaPreguntas(new HashMap<>());
        this.setCuestionarioPersonalizado(cuestionario);
        
        List<PreguntasCuestionario> preguntas = recuperarPreguntas(cuestionario);
        
        Collections.sort(preguntas, (o1, o2) -> Long.compare(o1.getOrden(), o2.getOrden()));
        
        // Agrupo las preguntas por areas para poder pintarlas agrupadas
        List<PreguntasCuestionario> listaPreguntas;
        
        for (PreguntasCuestionario pregunta : preguntas) {
            // Si es user provisional y existe no conformidad, pintar sólo aquellas que no tengan respuesta ya validada
            // (las recuperadas de la BDD en listaRespuestas y presentes en el mapa de validaciones)
            if (soloNoValidadas == Boolean.FALSE || mapaValidacionRespuestas.containsKey(pregunta)) {
                listaPreguntas = mapaAreaPreguntas.get(pregunta.getArea());
                if (listaPreguntas == null) {
                    listaPreguntas = new ArrayList<>();
                }
                listaPreguntas.add(pregunta);
                mapaAreaPreguntas.put(pregunta.getArea(), listaPreguntas);
                if (pregunta.getTipoRespuesta() != null
                        && (pregunta.getTipoRespuesta().startsWith(Constantes.TIPO_RESPUESTA_TABLA)
                                || pregunta.getTipoRespuesta().startsWith(Constantes.TIPO_RESPUESTA_MATRIZ))) {
                    construirTipoRespuestaTablaMatrizVacia(pregunta);
                }
            }
        }
        
        /******************************************************/
        if (visualizarRespuestas) {
            construirTipoRespuestaTablaMatrizConDatos();
        }
        
        /******************************************************/
        Set<AreasCuestionario> areasSet = mapaAreaPreguntas.keySet();
        
        // JSF ui:repeat no funciona con Set
        setAreas(new ArrayList<>(areasSet));
        
        // Ordeno las áreas por su campo orden
        Collections.sort(areas, (o1, o2) -> Long.compare(o1.getOrden(), o2.getOrden()));
        
        if (cuestionarioEnviado == null) {
            return "/cuestionarios/responderCuestionario?faces-redirect=true";
        } else {
            return "/cuestionarios/validarCuestionario?faces-redirect=true";
        }
    }
    
    private List<PreguntasCuestionario> recuperarPreguntas(CuestionarioPersonalizado cuestionario) {
        List<PreguntasCuestionario> preguntas;
        if (esUsuarioProvisional == Boolean.FALSE) {
            preguntas = preguntasRepository.findPreguntasElegidasCuestionarioPersonalizado(cuestionario.getId());
        } else {
            preguntas = preguntasRepository.findPreguntasElegidasCuestionarioPersonalizadoAndAreaIn(
                    cuestionario.getId(), new ArrayList<Long>(mapaAreasVisualizarUsuario.keySet()));
        }
        return preguntas;
    }
    
    /**
     * Obtiene los valores asociados a un tipo de respuesta RADIO o similar. Se usa dentro del xhtml para obtener los
     * valores a visualizar en pantalla.
     * 
     * @author EZENTIS
     * @param tipo Tipo de respuesta de la pregunta
     * @return Lista de valores asociados al tipo de respuesta
     */
    public List<String> getValoresTipoRespuesta(String tipo) {
        return configuracionRespuestaRepository.findValoresPorSeccion(tipo);
    }
    
    /**
     * Construye la tabla o matriz que se usará en el formulario para responder las preguntas cuyo tipo de respuesta
     * empieza por TABLA o MATRIZ
     * 
     * @author EZENTIS
     * @see visualizar
     * @param pregunta Pregunta del tipo respuesta que empiezan por TABLA o MATRIZ
     */
    private void construirTipoRespuestaTablaMatrizVacia(PreguntasCuestionario pregunta) {
        DataTableView dataTableView = new DataTableView();
        List<ConfiguracionRespuestasCuestionario> valoresColumnas = configuracionRespuestaRepository
                .findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta());
        if (pregunta.getTipoRespuesta() != null
                && pregunta.getTipoRespuesta().startsWith(Constantes.TIPO_RESPUESTA_TABLA)) {
            dataTableView.crearTabla(valoresColumnas);
        } else {
            dataTableView.crearMatriz(valoresColumnas);
        }
        mapaRespuestasTabla.put(pregunta, dataTableView);
    }
    
    /**
     * Construye la tabla o matriz que se usará del cuestionario con las respuestas cumplimentadas
     * 
     * @author EZENTIS
     * @see visualizar
     * @param pregunta Pregunta del tipo respuesta que empiezan por TABLA o MATRIZ
     */
    public void construirTipoRespuestaTablaMatrizConDatos() {
        mapaRespuestasTablaAux.forEach((pregunta, listaDatos) -> {
            DataTableView dataTableView = mapaRespuestasTabla.get(pregunta);
            dataTableView.crearTablaMatriConDatos(listaDatos);
            mapaRespuestasTabla.put(pregunta, dataTableView);
        });
    }
    
    /**
     * Añade una fila nueva a la pregunta pasada como parámetro. El tipo de respuesta de esta pregunta siempre deberá
     * empezar por TABLA
     * 
     * @author EZENTIS
     * @param pregunta Pregunta de un cuestionario
     */
    public void aniadirFilaRespuestaTabla(PreguntasCuestionario pregunta) {
        if (mapaRespuestasTabla.get(pregunta) != null) {
            DataTableView dataTableView = mapaRespuestasTabla.get(pregunta);
            dataTableView.crearFilaVacia();
            mapaRespuestasTabla.put(pregunta, dataTableView);
        }
    }
    
    public void descargarFichero(Documento documento) {
        try {
            setFile(documentoService.descargaDocumento(documento));
        } catch (Exception e) {
            regActividadService.altaRegActividadError(NOMBRESECCION, e);
        }
    }
    
    public void crearDocumentoWordCuestionarioPersonalizado(CuestionarioPersonalizado cuestionarioPersonalizado) {
        try {
            setFile(wordGenerator.crearDocumentoCuestionarioPersonalizado(cuestionarioPersonalizado));
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
                    "Se ha producido un error en la generación del documento Word");
            regActividadService.altaRegActividadError(NOMBRESECCION, e);
        }
    }
    
    public void crearPdfCuestionarioEnviado(CuestionarioEnvio cuestionarioEnviado) {
        try {
            setFile(pdfGenerator.crearCuestionarioEnviado(cuestionarioEnviado));
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
                    "Se ha producido un error en la generación del PDF");
            regActividadService.altaRegActividadError(NOMBRESECCION, e);
        }
    }
    
    @PostConstruct
    public void init() {
        setUsuarioActual((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        esUsuarioProvisional = RoleEnum.ROLE_PROV_CUESTIONARIO.equals(usuarioActual.getRole());
    }
    
    /**
     * Construye un mapa que relaciona el id con su objeto area de un cuestionario enviado para recuperar el nombre del
     * area.
     * 
     * @author EZENTIS
     */
    public void generarMapaAreasVisualizarUsuario() {
        mapaAreasVisualizarUsuario = new HashMap<>();
        
        listaAreasVisualizarUsuario.forEach(
                areaCuestionario -> mapaAreasVisualizarUsuario.put(areaCuestionario.getId(), areaCuestionario));
        
    }
    
}
