package es.mira.progesin.web.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itextpdf.text.DocumentException;

import es.mira.progesin.persistence.entities.AreaInforme;
import es.mira.progesin.persistence.entities.Informe;
import es.mira.progesin.persistence.entities.RespuestaInforme;
import es.mira.progesin.persistence.entities.SubareaInforme;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.services.IInformeService;
import es.mira.progesin.services.IModeloInformeService;
import es.mira.progesin.services.RegistroActividadService;
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
     * Contenido del editor de texto.
     */
    private String texto;
    
    /**
     * Informe completo con los textos de cada area y subarea.
     */
    private Informe informe;
    
    /**
     * Estructura interna de las areas.
     */
    private Map<AreaInforme, List<SubareaInforme>> mapaAreaSubareas;
    
    /**
     * Areas de un informe.
     */
    private List<AreaInforme> areasInforme;
    
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
     * Generador de PDFs a partir de código html.
     */
    @Autowired
    private transient HtmlPdfGenerator htmlPdfGenerator;
    
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
     * Guarda el informe actual.
     */
    public void guardarInforme() {
        informe = Informe.builder().respuestas(new ArrayList<RespuestaInforme>()).build();
        informe.getRespuestas().add(RespuestaInforme.builder().respuesta(texto.getBytes()).informe(informe).build());
        informeService.save(informe);
    }
    
    /**
     * Crea un archivo PDF con los datos del informe.
     * 
     * @param inform cumplimentado por los inspectores
     */
    public void crearInformePDF(Informe inform) {
        try {
            setFile(htmlPdfGenerator.generarInformePdf(inform));
        } catch (IOException | DocumentException e) {
            // e.printStackTrace();
            // FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
            // "Se ha producido un error en la generación del PDF");
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
    
    /**
     * Inicializa el bean.
     */
    @PostConstruct
    public void init() {
        // informe = informeService.findOne(1L);
        // setAreasInforme(crearAreasInforme(informe.getModelo()));
        // setMapaAreaSubareas(crearMapaAreasSubareas());
        // if (informe != null) {
        // try {
        // texto = new String(informe.getRespuestas().get(0).getRespuesta(), "UTF-8");
        // } catch (UnsupportedEncodingException e) {
        // // TODO Auto-generated catch block
        // // e.printStackTrace();
        // // FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
        // // "Se ha producido un error en la recuperación del texto");
        // regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.name(), e);
        // }
        // }
    }
    
    // /**
    // *
    // * @return mapa de areas y sus subareas
    // */
    // public Map<AreaInforme, List<SubareaInforme>> crearMapaAreasSubareas(ModeloInforme modelo) {
    // Map<AreaInforme, List<SubareaInforme>> estructuraInforme = new HashMap<>();
    // List<AreaInforme> listaAreasInforme = modelo.getAreas();
    // areasInforme.forEach(area -> {
    // // List<SubareaInforme> subareas = subareaInformeService.findByArea(area);
    // // estructuraInforme.put(area, subareas);
    // });
    // return estructuraInforme;
    // }
}
