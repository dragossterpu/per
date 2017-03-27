package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipios;
import es.mira.progesin.persistence.entities.Provincias;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.WordGenerator;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Controller("inspeccionBean")
@Scope("session")
public class InspeccionBean {
    
    private Inspeccion inspeccion;
    
    private String vieneDe;
    
    private InspeccionBusqueda inspeccionBusqueda;
    
    private InspeccionBusqueda inspeccionBusquedaCopia;
    
    private GuiaPasos pasoSeleccionada;
    
    private List<Boolean> list;
    
    private StreamedContent file;
    
    boolean alta = false;
    
    @Autowired
    private WordGenerator wordGenerator;
    
    @Autowired
    private IRegistroActividadService regActividadService;
    
    @Autowired
    private IInspeccionesService inspeccionesService;
    
    private static final int MAX_RESULTS_PAGE = 20;
    
    private static final int FIRST_PAGE = 1;
    
    private long numeroRegistros;
    
    private int primerRegistro;
    
    private long actualPage;
    
    private long numPages;
    
    @PersistenceContext
    private transient EntityManager em;
    
    private List<Municipios> listaMunicipios;
    
    /**************************************************************
     * 
     * Busca las guías según los filtros introducidos en el formulario de búsqueda
     * 
     **************************************************************/
    public void buscarInspeccion() {
        primerRegistro = 0;
        actualPage = FIRST_PAGE;
        numeroRegistros = getCountRegistrosInspecciones();
        numPages = getCountPagesGuia(numeroRegistros);
        inspeccionBusquedaCopia = copiaInspeccionBusqueda(inspeccionBusqueda);
        List<Inspeccion> listaInspecciones = inspeccionesService.buscarInspeccionPorCriteria(0, MAX_RESULTS_PAGE,
                inspeccionBusquedaCopia);
        inspeccionBusqueda.setListaInspecciones(listaInspecciones);
    }
    
    /*********************************************************
     * 
     * Visualiza la guía personalizada pasada como parámetro redirigiendo a la vista "visualizaGuíaPersonalizada"
     * 
     * @param inspeccion
     * @return String
     * 
     *********************************************************/
    
    // public String visualizaInspeccion(Inspeccion inspeccion) {
    // this.inspeccion = inspeccion;
    // // listaPasos = guiaPersonalizadaService.listaPasos(guiaPersonalizada);
    // return "/guias/visualizaGuiaPersonalizada?faces-redirect=true";
    // }
    
    /*********************************************************
     * 
     * Limpia los valores del objeto de búsqueda
     * 
     *********************************************************/
    
    public void limpiarBusqueda() {
        inspeccionBusqueda.resetValues();
    }
    
    /*********************************************************
     * 
     * Anula una guía personalizada pasada como parámetro.
     * 
     * @param guiaPersonalizada
     * 
     *********************************************************/
    
    // public void anular(GuiaPersonalizada guiaPersonalizada) {
    // inspeccionesService.anular(guiaPersonalizada);
    // }
    
    /*********************************************************
     * 
     * Borra de base de datos una guía personalizada pasada como parámetro
     * 
     * @param guiaPersonalizada
     * 
     *********************************************************/
    
    // public void eliminar(GuiaPersonalizada guiaPersonalizada) {
    // inspeccionesService.eliminar(guiaPersonalizada);
    // }
    
    /*********************************************************
     * 
     * Inicializa el bean
     * 
     *********************************************************/
    
    @PostConstruct
    public void init() {
        inspeccionBusqueda = new InspeccionBusqueda();
        listaMunicipios = new ArrayList<>();
        list = new ArrayList<>();
        for (int i = 0; i <= 12; i++) {
            list.add(Boolean.TRUE);
        }
    }
    
    /*********************************************************
     * 
     * Crea un documento Word a partir de una guía personalizada pasada como parámetro
     * 
     * @param inspeccion
     * 
     *********************************************************/
    
    // public void crearDocumentoWordInspecciones(Inspeccion inspeccion) {
    // try {
    // setFile(wordGenerator.crearDocumentoGuia(inspeccion));
    // } catch (Exception e) {
    // // FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
    // // "Se ha producido un error en la generación del documento Word");
    // regActividadService.altaRegActividadError(TipoRegistroEnum.ERROR.name(), e);
    //
    // FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
    // "Se ha producido un error en la generación del documento Word", "");
    // FacesContext.getCurrentInstance().addMessage("message", message);
    // }
    // }
    
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
    public void nextInspeccion() {
        
        if (actualPage < numPages) {
            
            primerRegistro += MAX_RESULTS_PAGE;
            actualPage++;
            
            List<Inspeccion> listaInspecciones = inspeccionesService.buscarInspeccionPorCriteria(primerRegistro,
                    MAX_RESULTS_PAGE, inspeccionBusquedaCopia);
            inspeccionBusqueda.setListaInspecciones(listaInspecciones);
        }
        
    }
    
    /**
     * Cargar la página anterior de resultados de la búsqueda
     * 
     * @author EZENTIS
     */
    public void previousInspeccion() {
        
        if (actualPage > FIRST_PAGE) {
            
            primerRegistro -= MAX_RESULTS_PAGE;
            actualPage--;
            
            List<Inspeccion> listaInspecciones = inspeccionesService.buscarInspeccionPorCriteria(primerRegistro,
                    MAX_RESULTS_PAGE, inspeccionBusquedaCopia);
            inspeccionBusqueda.setListaInspecciones(listaInspecciones);
        }
    }
    
    /**
     * @return devuelve el número de registros de la consulta criteria.
     * @author EZENTIS
     * 
     */
    public long getCountRegistrosInspecciones() {
        return inspeccionesService.getCountInspeccionCriteria(inspeccionBusqueda);
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
     * @param inspeccion
     * @return
     */
    public InspeccionBusqueda copiaInspeccionBusqueda(InspeccionBusqueda inspeccion) {
        InspeccionBusqueda inspeccionCopia = new InspeccionBusqueda();
        inspeccionCopia.setNumero(inspeccion.getNumero());
        inspeccionCopia.setNombreEquipo(inspeccion.getNombreEquipo());
        inspeccionCopia.setCuatrimestre(inspeccion.getCuatrimestre());
        inspeccionCopia.setTipoInspeccion(inspeccion.getTipoInspeccion());
        inspeccionCopia.setAmbito(inspeccion.getAmbito());
        inspeccionCopia.setNombreUnidad(inspeccion.getNombreUnidad());
        inspeccionCopia.setEstado(inspeccion.getEstado());
        inspeccionCopia.setFechaDesde(inspeccion.getFechaDesde());
        inspeccionCopia.setFechaHasta(inspeccion.getFechaHasta());
        inspeccionCopia.setUsuarioCreacion(inspeccion.getUsuarioCreacion());
        inspeccionCopia.setAnio(inspeccion.getAnio());
        inspeccionCopia.setTipoUnidad(inspeccion.getTipoUnidad());
        inspeccionCopia.setProvincia(inspeccion.getProvincia());
        inspeccionCopia.setMunicipio(inspeccion.getMunicipio());
        inspeccionCopia.setJefeEquipo(inspeccion.getJefeEquipo());
        
        return inspeccionCopia;
    }
    
    public void onChangeProvincia(ValueChangeEvent event) {
        Provincias provinciaSeleccionada = (Provincias) event.getNewValue();
        TypedQuery<Municipios> queryEmpleo = em.createNamedQuery("Municipios.findByCode_province", Municipios.class);
        queryEmpleo.setParameter("provinciaSeleccionada", provinciaSeleccionada);
        listaMunicipios = queryEmpleo.getResultList();
    }
    
}
