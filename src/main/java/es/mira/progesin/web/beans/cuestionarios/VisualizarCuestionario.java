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
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.DatosTablaGenerica;
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
 * Bean para la visualización de los cuestionarios por pantalla.
 * 
 * @author EZENTIS
 *
 */
@Setter
@Getter
@Controller("visualizarCuestionario")
@Scope("session")
public class VisualizarCuestionario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Nombre de la sección.
     */
    private static final String NOMBRESECCION = "Visualizar cuestionario";
    
    /**
     * CuestionarioEnvio para la visualización.
     */
    private CuestionarioEnvio cuestionarioEnviado;
    
    /**
     * CuestionarioPersonalizado para la visualización.
     */
    private CuestionarioPersonalizado cuestionarioPersonalizado;
    
    /**
     * Mapa de áreas y preguntas.
     */
    private Map<AreasCuestionario, List<PreguntasCuestionario>> mapaAreaPreguntas;
    
    /**
     * Lista de áreas.
     */
    private List<AreasCuestionario> areas;
    
    /**
     * Mapa de validaciones de áreas.
     */
    private Map<AreasCuestionario, Boolean> mapaValidacionAreas;
    
    /**
     * Mapa de validación de respuestas.
     */
    private Map<PreguntasCuestionario, Boolean> mapaValidacionRespuestas;
    
    /**
     * Mapa de respuestas.
     */
    private Map<PreguntasCuestionario, String> mapaRespuestas;
    
    /**
     * Mapa de respuestas tabla.
     */
    private Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla;
    
    /**
     * Mapa de respuestas tabla auxiliar.
     */
    private Map<PreguntasCuestionario, List<DatosTablaGenerica>> mapaRespuestasTablaAux;
    
    /**
     * Mapa de documentos.
     */
    private Map<PreguntasCuestionario, List<Documento>> mapaDocumentos;
    
    /**
     * Lista de respuestas.
     */
    private List<RespuestaCuestionario> listaRespuestas;
    
    /**
     * Fichero para la descarga de documento.
     */
    private transient StreamedContent file;
    
    /**
     * Usuario actual.
     */
    private User usuarioActual;
    
    /**
     * Booleano que indica que si el usuario es provisional.
     */
    
    private boolean esUsuarioProvisional;
    
    /**
     * Lista de las áreas que visualiza el usuario.
     */
    private List<AreasCuestionario> listaAreasVisualizarUsuario;
    
    /**
     * Mapa de las áreas visualizables.
     */
    private Map<Long, AreasCuestionario> mapaAreasVisualizarUsuario;
    
    /**
     * Repositorio de tipos de respuestas de cuestionario.
     */
    @Autowired
    private transient IConfiguracionRespuestasCuestionarioRepository configuracionRespuestaRepository;
    
    /**
     * Repositorio de respuestas de cuestionario.
     */
    @Autowired
    private transient IRespuestaCuestionarioRepository respuestaRepository;
    
    /**
     * Repositorio de tabla de datos.
     */
    @Autowired
    private transient IDatosTablaGenericaRepository datosTablaRepository;
    
    /**
     * Repositorio de preguntas.
     */
    @Autowired
    private transient IPreguntaCuestionarioRepository preguntasRepository;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    transient IRegistroActividadService regActividadService;
    
    /**
     * Servicio de documentos.
     */
    @Autowired
    transient IDocumentoService documentoService;
    
    /**
     * Generador de words.
     */
    @Autowired
    private transient WordGenerator wordGenerator;
    
    /**
     * Generador de PDF.
     */
    @Autowired
    private transient PdfGenerator pdfGenerator;
    
    /**
     * Muestra en pantalla el cuestionario personalizado, mostrando las diferentes opciones de responder (cajas de
     * texto, adjuntos, tablas...).
     * 
     * 
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
     * Muestra en pantalla el cuestionario con las respuestas de la unidad inspeccionada.
     * 
     * 
     * @param cuestionario seleccionado en los resultados de la búsqueda
     * @return vista desde la que ha sido llamada responderCuestionario o validarCuestionario
     */
    public String visualizarRespuestasCuestionario(CuestionarioEnvio cuestionario) {
        cuestionario.setPlantillas(documentoService.findPlantillas(cuestionario.getId()));
        this.setCuestionarioEnviado(cuestionario);
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
            if (esTipoRespuestaTablaOMatriz(tipoRespuesta) && respuesta.getRespuestaTablaMatriz() != null) {
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
     * que precisen una tabla o matriz para representar su respuesta.
     * 
     * 
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
                // listaPreguntas = mapaAreaPreguntas.get(pregunta.getArea());
                // if (listaPreguntas == null) {
                // listaPreguntas = new ArrayList<>();
                // }
                listaPreguntas = new ArrayList<>();
                listaPreguntas.add(pregunta);
                mapaAreaPreguntas.put(pregunta.getArea(), listaPreguntas);
                if (esTipoRespuestaTablaOMatriz(pregunta.getTipoRespuesta())) {
                    construirTipoRespuestaTablaMatrizVacia(pregunta);
                }
            }
        }
        
        if (visualizarRespuestas) {
            construirTipoRespuestaTablaMatrizConDatos();
        }
        
        Set<AreasCuestionario> areasSet = mapaAreaPreguntas.keySet();
        
        // JSF ui:repeat no funciona con Set
        setAreas(new ArrayList<>(areasSet));
        
        // Ordeno las áreas por su campo orden
        Collections.sort(areas, (o1, o2) -> Long.compare(o1.getOrden(), o2.getOrden()));
        String rutaVista;
        if (cuestionarioEnviado == null) {
            rutaVista = "/cuestionarios/responderCuestionario?faces-redirect=true";
        } else {
            rutaVista = "/cuestionarios/validarCuestionario?faces-redirect=true";
        }
        return rutaVista;
    }
    
    /**
     * Comprueba si el tipo de respuesta es de tipo TABLA o MATRIZ.
     * 
     * @param tipoRespuesta tipo de la respuesta
     * @return true o false
     */
    private boolean esTipoRespuestaTablaOMatriz(String tipoRespuesta) {
        return tipoRespuesta != null && (tipoRespuesta.startsWith(Constantes.TIPORESPUESTATABLA)
                || tipoRespuesta.startsWith(Constantes.TIPORESPUESTAMATRIZ));
    }
    
    /**
     * Recupera el listado de preguntas de un cuestionario.
     * 
     * @param cuestionario Cuestionario del que se desea extraer las preguntas
     * @return Listado de preguntas
     */
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
     * 
     * @param tipo Tipo de respuesta de la pregunta
     * @return Lista de valores asociados al tipo de respuesta
     */
    public List<String> getValoresTipoRespuesta(String tipo) {
        return configuracionRespuestaRepository.findValoresPorSeccion(tipo);
    }
    
    /**
     * Construye la tabla o matriz que se usará en el formulario para responder las preguntas cuyo tipo de respuesta
     * empieza por TABLA o MATRIZ.
     * 
     * 
     * @see visualizar
     * @param pregunta Pregunta del tipo respuesta que empiezan por TABLA o MATRIZ
     */
    private void construirTipoRespuestaTablaMatrizVacia(PreguntasCuestionario pregunta) {
        DataTableView dataTableView = new DataTableView();
        List<ConfiguracionRespuestasCuestionario> valoresColumnas = configuracionRespuestaRepository
                .findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta());
        if (pregunta.getTipoRespuesta() != null
                && pregunta.getTipoRespuesta().startsWith(Constantes.TIPORESPUESTATABLA)) {
            dataTableView.crearTabla(valoresColumnas);
        } else {
            dataTableView.crearMatriz(valoresColumnas);
        }
        mapaRespuestasTabla.put(pregunta, dataTableView);
    }
    
    /**
     * Construye la tabla o matriz que se usará del cuestionario con las respuestas cumplimentadas.
     * 
     * 
     * @see #visualizar
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
     * empezar por TABLA.
     * 
     * 
     * @param pregunta Pregunta de un cuestionario
     */
    public void aniadirFilaRespuestaTabla(PreguntasCuestionario pregunta) {
        if (mapaRespuestasTabla.get(pregunta) != null) {
            DataTableView dataTableView = mapaRespuestasTabla.get(pregunta);
            dataTableView.crearFilaVacia();
            mapaRespuestasTabla.put(pregunta, dataTableView);
        }
    }
    
    /**
     * Descarga de un documento subido por el usuario provisional.
     * 
     * 
     * @param documento seleccionado
     */
    public void descargarFichero(Documento documento) {
        setFile(null);
        try {
            setFile(documentoService.descargaDocumento(documento));
        } catch (ProgesinException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al descargar del fichero");
            regActividadService.altaRegActividadError(NOMBRESECCION, e);
        }
    }
    
    /**
     * Genera un archivo word para ser descargado con las preguntas del modelo de cuestionario personalizado.
     * 
     * 
     * @param cuestionario mostrado
     */
    public void crearDocumentoWordCuestionarioPersonalizado(CuestionarioPersonalizado cuestionario) {
        try {
            setFile(wordGenerator.crearDocumentoCuestionarioPersonalizado(cuestionario));
        } catch (ProgesinException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error en la generación del documento Word");
            regActividadService.altaRegActividadError(NOMBRESECCION, e);
        }
    }
    
    /**
     * Genera un archivo pdf para ser descargado con las preguntas y las respuestas del cuestionario enviado una vez
     * cumplimentado.
     * 
     * 
     * @param cuestionario mostrado
     */
    public void crearPdfCuestionarioEnviado(CuestionarioEnvio cuestionario) {
        try {
            setFile(pdfGenerator.crearCuestionarioEnviado(cuestionario));
        } catch (ProgesinException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error en la generación del PDF");
            regActividadService.altaRegActividadError(NOMBRESECCION, e);
        }
    }
    
    /**
     * PostConstruct, inicializa el bean.
     * 
     */
    @PostConstruct
    public void init() {
        setUsuarioActual((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        esUsuarioProvisional = RoleEnum.ROLE_PROV_CUESTIONARIO.equals(usuarioActual.getRole());
    }
    
    /**
     * Construye un mapa que relaciona el id con su objeto area de un cuestionario enviado para recuperar el nombre del
     * area.
     * 
     * 
     */
    public void generarMapaAreasVisualizarUsuario() {
        mapaAreasVisualizarUsuario = new HashMap<>();
        
        listaAreasVisualizarUsuario.forEach(
                areaCuestionario -> mapaAreasVisualizarUsuario.put(areaCuestionario.getId(), areaCuestionario));
        
    }
    
}
