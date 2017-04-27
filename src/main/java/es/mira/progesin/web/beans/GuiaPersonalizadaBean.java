package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

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
    
    private GuiaPersonalizadaBusqueda guiaPersonalizadaBusquedaCopia;
    
    private GuiaPasos pasoSeleccionada;
    
    private List<Boolean> list;
    
    private List<GuiaPasos> listaPasos;
    
    private StreamedContent file;
    
    boolean alta = false;
    
    @Autowired
    private WordGenerator wordGenerator;
    
    @Autowired
    private IRegistroActividadService regActividadService;
    
    @Autowired
    private IGuiaPersonalizadaService guiaPersonalizadaService;
    
    private static final int MAX_RESULTS_PAGE = 20;
    
    private static final int FIRST_PAGE = 1;
    
    private long numeroRegistros;
    
    private int primerRegistro;
    
    private long actualPage;
    
    private long numPages;
    
    /**************************************************************
     * 
     * Busca las guías según los filtros introducidos en el formulario de búsqueda
     * 
     **************************************************************/
    public void buscarGuia() {
        primerRegistro = 0;
        actualPage = FIRST_PAGE;
        setNumeroRegistros(getCountRegistrosGuia());
        numPages = getCountPagesGuia(numeroRegistros);
        guiaPersonalizadaBusquedaCopia = copiaGuiaBusqueda(guiaPersonalizadaBusqueda);
        List<GuiaPersonalizada> listaGuias = guiaPersonalizadaService.buscarGuiaPorCriteria(0, MAX_RESULTS_PAGE,
                guiaPersonalizadaBusquedaCopia);
        guiaPersonalizadaBusqueda.setListaGuias(listaGuias);
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
        buscarGuia();
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
        buscarGuia();
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
            // FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
            // "Se ha producido un error en la generación del documento Word");
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
     * 
     */
    public void nextGuia() {
        
        if (actualPage < numPages) {
            
            primerRegistro += MAX_RESULTS_PAGE;
            actualPage++;
            
            List<GuiaPersonalizada> listaGuias = guiaPersonalizadaService.buscarGuiaPorCriteria(primerRegistro,
                    MAX_RESULTS_PAGE, guiaPersonalizadaBusquedaCopia);
            guiaPersonalizadaBusqueda.setListaGuias(listaGuias);
        }
        
    }
    
    /**
     * Cargar la página anterior de resultados de la búsqueda
     * 
     * @author EZENTIS
     */
    public void previousGuia() {
        
        if (actualPage > FIRST_PAGE) {
            
            primerRegistro -= MAX_RESULTS_PAGE;
            actualPage--;
            
            List<GuiaPersonalizada> listaGuias = guiaPersonalizadaService.buscarGuiaPorCriteria(primerRegistro,
                    MAX_RESULTS_PAGE, guiaPersonalizadaBusquedaCopia);
            guiaPersonalizadaBusqueda.setListaGuias(listaGuias);
        }
    }
    
    /**
     * @return devuelve el número de registros de la consulta criteria.
     * @author EZENTIS
     * 
     */
    public long getCountRegistrosGuia() {
        return guiaPersonalizadaService.getCountGuiaCriteria(guiaPersonalizadaBusqueda);
    }
    
    /**
     * Devuelve el número de páginas de la consulta.
     * 
     * @param countRegistros
     * @return número de páginas.
     * @author EZENTIS
     */
    public long getCountPagesGuia(long countRegistros) {
        
        if (countRegistros % MAX_RESULTS_PAGE == 0)
            return countRegistros / MAX_RESULTS_PAGE;
        else
            return countRegistros / MAX_RESULTS_PAGE + 1;
    }
    
    /**
     * @param guia
     * @return
     */
    public GuiaPersonalizadaBusqueda copiaGuiaBusqueda(GuiaPersonalizadaBusqueda guia) {
        GuiaPersonalizadaBusqueda guiaBusquedaCopia = new GuiaPersonalizadaBusqueda();
        guiaBusquedaCopia.setFechaDesde(guia.getFechaDesde());
        guiaBusquedaCopia.setFechaHasta(guia.getFechaHasta());
        guiaBusquedaCopia.setTipoInspeccion(guia.getTipoInspeccion());
        guiaBusquedaCopia.setNombre(guia.getNombre());
        guiaBusquedaCopia.setUsuarioCreacion(guia.getUsuarioCreacion());
        guiaBusquedaCopia.setEstado(guia.getEstado());
        
        return guiaBusquedaCopia;
    }
    
    public void asignar(GuiaPersonalizada guia) {
        guiaPersonalizada = guia;
        RequestContext.getCurrentInstance().execute("PF('inspeccionDialogo').show()");
    }
    
    public void asignarInspeccion(Inspeccion inspeccion) {
        try {
            RequestContext.getCurrentInstance().execute("PF('inspeccionDialogo').hide()");
            guiaPersonalizada.setInspeccion(inspeccion);
            guiaPersonalizadaService.save(guiaPersonalizada);
            buscarGuia();
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
                buscarGuia();
            }
        } catch (Exception e) {
            regActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
        }
    }
    
}
