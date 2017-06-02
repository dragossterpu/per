package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.Informe;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.repositories.IInformeRepository;
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
    private Map<String, List<String>> mapaAreaSubareas;
    
    /**
     * Areas de un informe.
     */
    private List<String> areasInforme;
    
    /**
     * Repositorio de informes.
     */
    @Autowired
    private transient IInformeRepository informeRepository;
    
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
        informe = Informe.builder().texto(texto.getBytes()).build();
        informeRepository.save(informe);
    }
    
    /**
     * Crea un archivo PDF con los datos del informe.
     * 
     * @param inform cumplimentado por los inspectores
     */
    public void crearInformePDF(Informe inform) {
        try {
            setFile(htmlPdfGenerator.generarInformePdf(inform));
        } catch (Exception e) {
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
        try {
            // setFile(htmlDocGenerator.generarInformeDoc(inform));
        } catch (Exception e) {
            // e.printStackTrace();
            // FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
            // "Se ha producido un error en la generación del DOC");
            regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.name(), e);
        }
    }
    
    /**
     * Inicializa el bean.
     */
    @PostConstruct
    public void init() {
        setAreasInforme(crearAreasInforme());
        setMapaAreaSubareas(crearMapaAreasSubareas());
        informe = informeRepository.findOne(1L);
        if (informe != null) {
            try {
                texto = new String(informe.getTexto(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                // FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
                // "Se ha producido un error en la recuperación del texto");
                regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.name(), e);
            }
        }
    }
    
    /**
     * 
     * @return areas del informe
     */
    public List<String> crearAreasInforme() {
        return Arrays.asList("1.- ÁMBITO Y ORIGEN DE LA INSPECCIÓN", "2.- REUNIONES Y VISITAS INSTITUCIONALES",
                "3.-INFRAESTRUCTURAS E INSTALACIONES");
    }
    
    /**
     * 
     * @return mapa de areas y sus subareas
     */
    public Map<String, List<String>> crearMapaAreasSubareas() {
        Map<String, List<String>> estructuraInforme = new HashMap<>();
        estructuraInforme.put("1.- ÁMBITO Y ORIGEN DE LA INSPECCIÓN",
                Arrays.asList("1.1.- Unidad inspeccionada", "1.2.- Ambito territorial, poblacion y servicios",
                        "1.3.- Objetivos generales y especificos", "1.4.- Problematica de interes policial"));
        
        estructuraInforme.put("2.- REUNIONES Y VISITAS INSTITUCIONALES", Arrays.asList("2.1.- Con Autoridades",
                "2.2.- Con Asociaciones Civiles", "2.2.- Con Asociaciones Profesionales"));
        
        estructuraInforme.put("3.-INFRAESTRUCTURAS E INSTALACIONES",
                Arrays.asList("3.1.- Situacion y estado de los inmuebles", "3.2.- Instalaciones y equipos",
                        "3.3.- Medidas de seguridad y protecclon de los acuartelamientos",
                        "3.4.- Deposito de detenidos", "3.5.- Galeria de Tiro", "3.6.- Otros aspectos relevantes",
                        "3.7.- Conclusiones y propuestas"));
        
        return estructuraInforme;
    }
}
