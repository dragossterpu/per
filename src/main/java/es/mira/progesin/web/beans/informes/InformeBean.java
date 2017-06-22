package es.mira.progesin.web.beans.informes;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.persistence.repositories.IInformeRepository;
import es.mira.progesin.persistence.repositories.IModeloInformeRepository;
import es.mira.progesin.services.IInformeService;
import es.mira.progesin.services.IModeloInformeService;
import es.mira.progesin.services.RegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.HtmlPdfGenerator;
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
     * Listado de informes del buscador.
     */
    private List<Informe> listadoInformes;
    
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
        
    }
    
    /**
     * Cargar formulario para buscar informes.
     * 
     * @return ruta de la vista
     */
    // TODO implementar buscador con criteria
    public String getFormBusquedaInformes() {
        setListadoInformes(informeService.findAll());
        return "/informes/informes?faces-redirect=true";
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
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
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
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
                    "Se ha producido un error al guardar el informe");
            regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.name(), e);
        }
    }
    
    /**
     * Genera HTML con los datos del informe.
     * 
     * @return texto completo del informe
     */
    private String obtenerDatosInformeConEstilo() {
        StringBuilder informeFormateado = new StringBuilder();
        informeFormateado.append("<div class=\"ql-snow ql-editor\">");
        modeloInforme.getAreas().forEach(area -> {
            informeFormateado.append("<h2>");
            informeFormateado.append(area.getDescripcion());
            informeFormateado.append("</h2>");
            area.getSubareas().forEach(subarea -> {
                informeFormateado.append("<h2>");
                informeFormateado.append(subarea.getDescripcion());
                informeFormateado.append("</h2>");
                informeFormateado.append(mapaRespuestas.get(subarea));
            });
        });
        informeFormateado.append("</div>");
        return informeFormateado.toString();
    }
    
    /**
     * Crea un archivo PDF con los datos del informe.
     */
    public void crearInformePDF() {
        try {
            String nombreArchivo = String.format("Informe_Inspeccion_%s-%s", informe.getInspeccion().getId(),
                    informe.getInspeccion().getAnio());
            String informeHTML = obtenerDatosInformeConEstilo();
            String titulo = String.format("Inspección realizada a la %s de la %s de %s",
                    informe.getInspeccion().getTipoUnidad().getDescripcion(),
                    informe.getInspeccion().getAmbito().getDescripcion(),
                    informe.getInspeccion().getMunicipio().getName());
            String fechaFinalizacion = sdf.format(informe.getFechaFinalizacion());
            String imagenPortada = Constantes.PORTADAINFORME;
            String autor = informe.getUsernameFinalizacion();
            setFile(HtmlPdfGenerator.generarInformePdf(nombreArchivo, informeHTML, titulo, fechaFinalizacion,
                    imagenPortada, autor));
        } catch (ProgesinException e) {
            e.printStackTrace();
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
                    "Se ha producido un error en la generación del PDF");
            regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.name(), e);
        }
    }
    
    /**
     * Crea un archivo DOC con los datos del informe.
     * 
     * @param inform cumplimentado por los inspectores
     */
    public void crearInformeDOC(Informe inform) {
        // try {
        // // setFile(htmlDocGenerator.generarInformeDoc(inform));
        // } catch (IOException | DocumentException e) {
        // // e.printStackTrace();
        // // FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
        // // "Se ha producido un error en la generación del DOC");
        // regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.name(), e);
        // }
    }
    
}
