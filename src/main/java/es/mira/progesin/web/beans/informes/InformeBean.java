package es.mira.progesin.web.beans.informes;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.persistence.repositories.IInformeRepository;
import es.mira.progesin.persistence.repositories.IModeloInformeRepository;
import es.mira.progesin.services.IInformeService;
import es.mira.progesin.services.IModeloInformeService;
import es.mira.progesin.services.ITipoInspeccionService;
import es.mira.progesin.services.RegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.HtmlDocxGenerator;
import es.mira.progesin.util.HtmlPdfGenerator;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean para la edición de informes.
 *
 * @author EZENTIS
 */
@Setter
@Getter
@Controller("informeBean")
@Scope("session")
public class InformeBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Formato de fecha.
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * Informe con respuestas.
     */
    private Informe informe;
    
    /**
     * Modelo del informe seleccionado.
     */
    private ModeloInforme modeloInforme;
    
    /**
     * Objeto de búsqueda de informes.
     */
    private InformeBusqueda informeBusqueda;
    
    /**
     * Listado de informes del buscador.
     */
    private List<Informe> model;
    
    // /**
    // * LazyModel para la visualización de datos paginados en la vista.
    // */
    // private LazyModelInformes model;
    
    /**
     * Lista de tipos de inspección.
     */
    private List<TipoInspeccion> listaTiposInspeccion;
    
    /**
     * Mapa de respuestas.
     */
    private Map<SubareaInforme, String> mapaRespuestas;
    
    /**
     * Servicio de informes.
     */
    @Autowired
    private transient IInformeService informeService;
    
    /**
     * Servicio de modelos de informe.
     */
    @Autowired
    private transient IModeloInformeService modeloInformeService;
    
    /**
     * BORRAR SOLO PRUEBAS.
     */
    @Autowired
    private transient IModeloInformeRepository modeloInformeRepository;
    
    /**
     * BORRAR SOLO PRUEBAS.
     */
    @Autowired
    private transient IInformeRepository informeRepository;
    
    // /**
    // * Generador de PDFs a partir de código html.
    // */
    // @Autowired
    // private transient HtmlPdfGenerator htmlPdfGenerator;
    
    // @Autowired
    // private transient HtmlDocGenerator htmlDocGenerator;
    
    /**
     * Servicio de modelos de informe.
     */
    @Autowired
    private transient ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Servicio del registro de actividad.
     */
    @Autowired
    private transient RegistroActividadService regActividadService;
    
    /**
     * Archivo descargable generado a partir del informe.
     */
    private transient StreamedContent file;
    
    /**
     * Inicializa el bean.
     */
    @PostConstruct
    public void init() {
        setInformeBusqueda(new InformeBusqueda());
        model = new ArrayList<>();// new LazyModelInformes(informeService);
        setListaTiposInspeccion(tipoInspeccionService.buscaTodos());
    }
    
    /**
     * Devuelve al formulario de búsqueda de informes a su estado inicial y borra los resultados de búsquedas anteriores
     * si se navega desde el menú u otra sección.
     * @return ruta siguiente
     */
    // TODO implementar buscador con criteria
    // TODO Añadir paginación servidor
    public String getFormBusquedaInformes() {
        limpiarBusqueda();
        return "/informes/informes?faces-redirect=true";
    }
    
    /**
     * Borra los resultados de búsquedas anteriores.
     */
    public void limpiarBusqueda() {
        setInformeBusqueda(new InformeBusqueda());
        model.clear();// setRowCount(0);
    }
    
    /**
     * Busca las solicitudes de documentación previa según los filtros introducidos en el formulario de búsqueda.
     * 
     * 
     */
    public void buscarInforme() {
        // model.setBusqueda(informeBusqueda);
        // model.load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
        setModel(informeService.findAll());
    }
    
    /**
     * Cargar formulario para editar informe.
     * 
     * @param informeId clave informe seleccionado
     * @return ruta de la vista
     */
    public String getFormEditarInforme(Long informeId) {
        cargarInforme(informeId);
        return "/informes/editarInforme?faces-redirect=true";
    }
    
    /**
     * Cargar formulario para visualizar informe.
     * 
     * @param informeId clave informe seleccionado
     * @return ruta de la vista
     */
    public String getFormVisualizarInforme(Long informeId) {
        cargarInforme(informeId);
        return "/informes/visualizarInforme?faces-redirect=true";
    }
    
    /**
     * Carga los datos de las respuestas del informe y el modelo con las areas y subareas.
     * @param id clave del informe
     */
    private void cargarInforme(Long id) {
        setInforme(informeService.findOne(id));
        setModeloInforme(modeloInformeService.findDistinctById(informe.getModelo().getId()));
        generarMapaRespuestas();
    }
    
    /**
     * Genera mapa con respuestas del informe asociadas a subareas del modelo.
     */
    private void generarMapaRespuestas() {
        mapaRespuestas = new HashMap<>();
        informe.getRespuestas().forEach(respuesta -> {
            try {
                mapaRespuestas.put(respuesta.getSubarea(), new String(respuesta.getTexto(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                        "Se ha producido un error en la recuperación del texto");
                regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.name(), e);
            }
        });
        modeloInforme.getAreas()
                .forEach(area -> area.getSubareas().forEach(subarea -> mapaRespuestas.putIfAbsent(subarea, "")));
    }
    
    /**
     * Guarda el informe actual.
     */
    public void guardarInforme() {
        try {
            setInforme(informeService.saveConRespuestas(informe, mapaRespuestas));
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                    "El informe ha sido guardado con éxito.");
        } catch (DataAccessException e) {
            e.printStackTrace();
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al guardar el informe");
            regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.name(), e);
        }
    }
    
    /**
     * Genera XHTML con los datos del informe.
     * 
     * @return texto completo del informe
     */
    private String generarInformeXHTML() {
        StringBuilder informeFormateado = new StringBuilder();
        informeFormateado.append("<div class=\"ql-snow ql-editor\">");
        modeloInforme.getAreas().forEach(area -> {
            informeFormateado.append("<h1>");
            informeFormateado.append(area.getDescripcion());
            informeFormateado.append("</h1>");
            area.getSubareas().forEach(subarea -> {
                informeFormateado.append("<h2>");
                informeFormateado.append(subarea.getDescripcion());
                informeFormateado.append("</h2>");
                informeFormateado.append(mapaRespuestas.get(subarea));
            });
        });
        informeFormateado.append("</div>");
        
        // Asegurarse de que es XHTML
        String informeXHTML = Utilities.limpiarHtml(informeFormateado.toString());
        return informeXHTML;
    }
    
    /**
     * Crea un archivo PDF o DOCX con los datos del informe.
     * 
     * @param tipoArchivo formato al que se exporta el informe
     */
    public void crearInforme(String tipoArchivo) {
        try {
            String nombreArchivo = String.format("Informe_Inspeccion_%s-%s", informe.getInspeccion().getId(),
                    informe.getInspeccion().getAnio());
            String informeXHTML = generarInformeXHTML();
            String titulo = String.format("Inspección realizada a la %s de la %s de %s",
                    informe.getInspeccion().getTipoUnidad().getDescripcion(),
                    informe.getInspeccion().getAmbito().getDescripcion(),
                    informe.getInspeccion().getMunicipio().getName());
            String fechaFinalizacion = sdf.format(informe.getFechaFinalizacion());
            String imagenPortada = Constantes.PORTADAINFORME;
            String autor = informe.getUsernameFinalizacion();
            if ("PDF".equals(tipoArchivo)) {
                setFile(HtmlPdfGenerator.generarInformePdf(nombreArchivo, informeXHTML, titulo, fechaFinalizacion,
                        imagenPortada, autor));
            } else if ("DOCX".equals(tipoArchivo)) {
                setFile(HtmlDocxGenerator.generarInformeDocx(nombreArchivo, informeXHTML, titulo, fechaFinalizacion,
                        imagenPortada, autor));
            }
        } catch (ProgesinException e) {
            e.printStackTrace();
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error en la generación del " + tipoArchivo);
            regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.name(), e);
        }
    }
    
}
