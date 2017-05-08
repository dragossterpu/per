package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.lazydata.LazyModelGuiasPersonalizadas;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IGuiaPersonalizadaService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.WordGenerator;
import lombok.Getter;
import lombok.Setter;

/*********************************************
 * Bean de guías personalizadas
 * 
 * @author Ezentis
 * 
 *******************************************/

@Setter
@Getter
@Controller("guiaPersonalizadaBean")
@Scope("session")

public class GuiaPersonalizadaBean {
    
    private GuiaPersonalizada guiaPersonalizada;
    
    private String vieneDe;
    
    private GuiaPersonalizadaBusqueda guiaPersonalizadaBusqueda;
    
    private List<Boolean> list;
    
    private List<GuiaPasos> listaPasos;
    
    private StreamedContent file;
    
    private LazyModelGuiasPersonalizadas model;
    
    @Autowired
    private WordGenerator wordGenerator;
    
    @Autowired
    private IRegistroActividadService regActividadService;
    
    @Autowired
    private IGuiaPersonalizadaService guiaPersonalizadaService;
    
    /**************************************************************
     * 
     * Busca las guías según los filtros introducidos en el formulario de búsqueda
     * 
     **************************************************************/
    public void buscarGuia() {
        
        model.setBusqueda(guiaPersonalizadaBusqueda);
        model.load(0, 20, "fechaCreacion", SortOrder.DESCENDING, null);
    }
    
    /*********************************************************
     * 
     * Visualiza la guía personalizada pasada como parámetro redirigiendo a la vista "visualizaGuíaPersonalizada"
     * 
     * @param guiaPersonalizada
     * @return String
     * 
     *********************************************************/
    
    public String visualizaGuia(GuiaPersonalizada guiaPersonalizada) {
        this.guiaPersonalizada = guiaPersonalizada;
        setListaPasos(guiaPersonalizadaService.listaPasos(guiaPersonalizada));
        return "/guias/visualizaGuiaPersonalizada?faces-redirect=true";
    }
    
    /*********************************************************
     * 
     * Limpia los valores del objeto de búsqueda
     * 
     *********************************************************/
    
    public void limpiarBusqueda() {
        guiaPersonalizadaBusqueda.resetValues();
        model.setRowCount(0);
    }
    
    /*********************************************************
     * 
     * Anula una guía personalizada pasada como parámetro.
     * 
     * @param guiaPersonalizada
     * 
     *********************************************************/
    
    public void anular(GuiaPersonalizada guiaPersonalizada) {
        guiaPersonalizadaService.anular(guiaPersonalizada);
        
    }
    
    /*********************************************************
     * 
     * Borra de base de datos una guía personalizada pasada como parámetro
     * 
     * @param guiaPersonalizada
     * 
     *********************************************************/
    
    public void eliminar(GuiaPersonalizada guiaPersonalizada) {
        guiaPersonalizadaService.eliminar(guiaPersonalizada);
        
    }
    
    /*********************************************************
     * 
     * Inicializa el bean
     * 
     *********************************************************/
    
    @PostConstruct
    public void init() {
        guiaPersonalizadaBusqueda = new GuiaPersonalizadaBusqueda();
        List<Boolean> lista = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            lista.add(Boolean.TRUE);
        }
        setList(lista);
        
        model = new LazyModelGuiasPersonalizadas(guiaPersonalizadaService);
    }
    
    /*********************************************************
     * 
     * Crea un documento Word a partir de una guía personalizada pasada como parámetro
     * 
     * @param guia
     * 
     *********************************************************/
    
    public void crearDocumentoWordGuia(GuiaPersonalizada guia) {
        try {
            setFile(wordGenerator.crearDocumentoGuia(guia));
        } catch (Exception e) {
            regActividadService.altaRegActividadError(TipoRegistroEnum.ERROR.name(), e);
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "Se ha producido un error en la generación del documento Word", "", "message");
        }
    }
    
    /*********************************************************
     * 
     * Limpia el menú de búsqueda si se accede a la vista desde el menú lateral
     * 
     *********************************************************/
    
    public void getFormularioBusqueda() {
        if ("menu".equalsIgnoreCase(this.vieneDe)) {
            limpiarBusqueda();
            this.vieneDe = null;
        }
        
    }
    
    /**
     * Asigna la guía recibida como parámetro a la variable guiaPersonalizada
     * 
     * @param guia
     */
    public void asignar(GuiaPersonalizada guia) {
        guiaPersonalizada = guia;
        RequestContext.getCurrentInstance().execute("PF('inspeccionDialogo').show()");
    }
    
    /**
     * Asigna el valor de la inspección a la guia y guarda en base de datos
     * 
     * @param inspeccion
     */
    public void asignarInspeccion(Inspeccion inspeccion) {
        try {
            RequestContext.getCurrentInstance().execute("PF('inspeccionDialogo').hide()");
            guiaPersonalizada.setInspeccion(inspeccion);
            guiaPersonalizadaService.save(guiaPersonalizada);
            
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
                    "Se ha producido un error al asignar una inspección a la guía personalizada");
            regActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
        }
        
    }
    
    /**
     * 
     * Elimina la fecha de baja de la guía para volver a ponerla activa
     * 
     * @param guiaActivar
     */
    public void activa(GuiaPersonalizada guiaActivar) {
        try {
            guiaActivar.setFechaAnulacion(null);
            guiaActivar.setUsernameAnulacion(null);
            if (guiaPersonalizadaService.save(guiaActivar) != null) {
                regActividadService.altaRegActividad(
                        "La guía '".concat(guiaActivar.getNombreGuiaPersonalizada().concat("' ha sido activada")),
                        TipoRegistroEnum.BAJA.name(), SeccionesEnum.GUIAS.getDescripcion());
                
            }
        } catch (Exception e) {
            regActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
        }
    }
    
}
