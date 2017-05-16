package es.mira.progesin.web.beans;

import java.io.Serializable;

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
 * @author EZENTIS
 * 
 * Bean para la edición de informes
 *
 */
@Setter
@Getter
@Controller("informeBean")
@Scope("session")
public class InformeBean implements Serializable {
    
    private String texto;
    
    private Informe informe;
    
    @Autowired
    private transient IInformeRepository informeRepository;
    
    @Autowired
    private transient HtmlPdfGenerator htmlPdfGenerator;
    
    // @Autowired
    // private transient HtmlDocGenerator htmlDocGenerator;
    
    @Autowired
    private transient RegistroActividadService regActividadService;
    
    private transient StreamedContent file;
    
    public void guardarInforme() {
        informe = Informe.builder().texto(texto).build();
        informeRepository.save(informe);
    }
    
    public void crearInformePDF(Informe informe) {
        try {
            setFile(htmlPdfGenerator.generarInformePdf(informe));
        } catch (Exception e) {
            e.printStackTrace();
            // FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
            // "Se ha producido un error en la generación del PDF");
            regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.name(), e);
        }
    }
    
    public void crearInformeDOC(Informe informe) {
        try {
            // setFile(htmlDocGenerator.generarInformeDoc(informe));
        } catch (Exception e) {
            e.printStackTrace();
            // FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
            // "Se ha producido un error en la generación del PDF");
            regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.name(), e);
        }
    }
    
    @PostConstruct
    public void init() {
        informe = informeRepository.findOne(1L);
        if (informe != null) {
            texto = informe.getTexto();
        }
    }
    
}
