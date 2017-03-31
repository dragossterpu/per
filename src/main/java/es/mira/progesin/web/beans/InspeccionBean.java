package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.criterion.Order;
import org.primefaces.event.data.SortEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Controller("inspeccionBean")
@Scope("session")
public class InspeccionBean {
    
    private Inspeccion nuevaInspeccion;
    
    private String vieneDe;
    
    private InspeccionBusqueda inspeccionBusqueda;
    
    private InspeccionBusqueda inspeccionBusquedaCopia;
    
    private Inspeccion inspeccion;
    
    private List<Boolean> list;
    
    boolean alta = false;
    
    @Autowired
    private IRegistroActividadService regActividadService;
    
    @Autowired
    private IInspeccionesService inspeccionesService;
    
    @Autowired
    private IEquipoService equipoService;
    
    private static final int MAX_RESULTS_PAGE = 20;
    
    private static final int FIRST_PAGE = 1;
    
    private long numeroRegistros;
    
    private int primerRegistro;
    
    private long actualPage;
    
    private long numPages;
    
    @PersistenceContext
    private transient EntityManager em;
    
    private List<Municipio> listaMunicipios;
    
    private List<Equipo> listaEquipos;
    
    private Provincia provinciSelec;
    
    private List<Provincia> listaProvincias;
    
    private List<Order> listaOrden;
    
    private boolean sortOrder;
    
    /**************************************************************
     * 
     * Busca las guías según los filtros introducidos en el formulario de búsqueda
     * 
     **************************************************************/
    public void buscarInspeccion() {
        List<Order> listaOrden = new ArrayList<>();
        listaOrden.add(Order.desc("fechaAlta"));
        buscarConOrden(listaOrden);
    }
    
    /**************************************************************
     * 
     * Busca las guías según los filtros introducidos en el formulario de búsqueda
     * 
     **************************************************************/
    private void buscarConOrden(List<Order> listaOrden) {
        primerRegistro = 0;
        actualPage = FIRST_PAGE;
        numeroRegistros = getCountRegistrosInspecciones();
        numPages = getCountPagesGuia(numeroRegistros);
        inspeccionBusquedaCopia = copiaInspeccionBusqueda(inspeccionBusqueda);
        List<Inspeccion> listaInspecciones = inspeccionesService.buscarInspeccionPorCriteria(0, MAX_RESULTS_PAGE,
                inspeccionBusquedaCopia, listaOrden);
        inspeccionBusqueda.setListaInspecciones(listaInspecciones);
        // inspeccionBusqueda.setPaginaActual(FIRST_PAGE);
        
    }
    
    /*********************************************************
     * 
     * Visualiza la guía personalizada pasada como parámetro redirigiendo a la vista "visualizaInspección"
     * 
     * @param inspeccion
     * @return String
     * 
     *********************************************************/
    
    public String visualizaInspeccion(Inspeccion inspeccion) {
        this.inspeccion = inspeccionesService.findInspeccionById(inspeccion.getId());
        return "/inspecciones/visualizarInspeccion?faces-redirect=true";
    }
    
    /*********************************************************
     * 
     * Limpia los valores del objeto de búsqueda
     * 
     *********************************************************/
    
    public void limpiarBusqueda() {
        inspeccionBusqueda.resetValues();
    }
    
    /**
     * Método que nos lleva al formulario de alta de nuevos usuarios, inicializando todo lo necesario para mostrar
     * correctamente la página (cuerpos de estado, puestos de trabajo, usuario nuevo). Se llama desde la página de
     * búsqueda de usuarios.
     * @return
     */
    public String nuevaInspeccion() {
        inicializarProvincias();
        nuevaInspeccion = new Inspeccion();
        nuevaInspeccion.setFechaAlta(new Date());
        nuevaInspeccion.setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());
        nuevaInspeccion.setFechaBaja(null);
        nuevaInspeccion.setFechaFinalizacion(null);
        nuevaInspeccion.setUsernameFinalizacion(null);
        nuevaInspeccion.setAnio(null);
        nuevaInspeccion.setCuatrimestre(null);
        nuevaInspeccion.setEstadoInspeccion(null);
        nuevaInspeccion.setEquipo(null);
        nuevaInspeccion.setAmbito(null);
        nuevaInspeccion.setMunicipio(null);
        nuevaInspeccion.setTipoUnidad(null);
        nuevaInspeccion.setFechaPrevista(null);
        nuevaInspeccion.setTipoInspeccion(null);
        nuevaInspeccion.setTipoUnidad(null);
        
        return "/inspecciones/altaInspeccion?faces-redirect=true";
    }
    
    public String altaInspeccion() {
        try {
            
            Inspeccion inspeccionProvisional = null;
            if ((inspeccionProvisional = inspeccionesService.save(nuevaInspeccion)) != null) {
                
                nuevaInspeccion.setId(inspeccionProvisional.getId());
                nuevaInspeccion.setNumero(nuevaInspeccion.getId() + "/" + nuevaInspeccion.getAnio());
                nuevaInspeccion.setEstadoInspeccion(EstadoInspeccionEnum.ESTADO_SIN_INICIAR);
                
                inspeccionesService.save(nuevaInspeccion);
                
                inspeccion = inspeccionesService.findInspeccionById(nuevaInspeccion.getId());
                
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                        "La inspección ha sido creada con éxito");
                String descripcion = "Alta nueva inspección " + nuevaInspeccion.getNumero();
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                        SeccionesEnum.INSPECCION.name());
            }
        } catch (Exception e) {
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
        return null;
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
        listaEquipos = (List<Equipo>) equipoService.findAll();
        inicializarProvincias();
        listaMunicipios = new ArrayList<>();
        list = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            list.add(Boolean.TRUE);
        }
    }
    
    private void inicializarProvincias() {
        listaProvincias = em.createNamedQuery("Provincia.findAll", Provincia.class).getResultList();
        Provincia provinciaDefecto = new Provincia();
        provinciaDefecto.setCodigo("00");
        provinciaDefecto.setCodigoMN("");
        provinciaDefecto.setProvincia("Todos");
        listaProvincias.add(0, provinciaDefecto);
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
    public void nextInspeccion() {
        
        if (actualPage < numPages) {
            
            primerRegistro += MAX_RESULTS_PAGE;
            actualPage++;
            
            List<Inspeccion> listaInspecciones = inspeccionesService.buscarInspeccionPorCriteria(primerRegistro,
                    MAX_RESULTS_PAGE, inspeccionBusquedaCopia, listaOrden);
            inspeccionBusqueda.setListaInspecciones(listaInspecciones);
            // inspeccionBusqueda.setPaginaActual(actualPage);
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
                    MAX_RESULTS_PAGE, inspeccionBusquedaCopia, listaOrden);
            inspeccionBusqueda.setListaInspecciones(listaInspecciones);
            // inspeccionBusqueda.setPaginaActual(actualPage);
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
        long numPag;
        if (countRegistros % MAX_RESULTS_PAGE == 0)
            numPag = countRegistros / MAX_RESULTS_PAGE;
        else
            numPag = countRegistros / MAX_RESULTS_PAGE + 1;
        
        return numPag;
    }
    
    /**
     * @param inspeccion
     * @return
     */
    public InspeccionBusqueda copiaInspeccionBusqueda(InspeccionBusqueda inspeccion) {
        InspeccionBusqueda inspeccionCopia = new InspeccionBusqueda();
        inspeccionCopia.setId(inspeccion.getId());
        inspeccionCopia.setNombreUnidad(inspeccion.getNombreUnidad());
        inspeccionCopia.setCuatrimestre(inspeccion.getCuatrimestre());
        inspeccionCopia.setTipoInspeccion(inspeccion.getTipoInspeccion());
        inspeccionCopia.setAmbito(inspeccion.getAmbito());
        inspeccionCopia.setEquipo(inspeccion.getEquipo());
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
        Provincia provinciaSeleccionada = (Provincia) event.getNewValue();
        TypedQuery<Municipio> queryEmpleo = em.createNamedQuery("Municipio.findByCode_province", Municipio.class);
        queryEmpleo.setParameter("provinciaSeleccionada", provinciaSeleccionada);
        listaMunicipios = queryEmpleo.getResultList();
    }
    
    public void onSort(SortEvent event) {
        String columna = event.getSortColumn().getHeaderText();
        sortOrder = event.isAscending();
        
        if ("Numero".equals(columna)) {
            listaOrden = new ArrayList<>();
            if (sortOrder) {
                listaOrden.add(Order.asc("id"));
                listaOrden.add(Order.asc("anio"));
            } else {
                listaOrden.add(Order.desc("id"));
                listaOrden.add(Order.desc("anio"));
            }
            
        } else if ("Fecha prev.".equals(columna)) {
            listaOrden = new ArrayList<>();
            if (sortOrder) {
                listaOrden.add(Order.asc("fechaPrevista"));
            } else {
                listaOrden.add(Order.desc("fechaPrevista"));
            }
            
        } else if ("Tipo".equals(columna)) {
            listaOrden = new ArrayList<>();
            if (sortOrder) {
                listaOrden.add(Order.asc("tipoInspeccion"));
            } else {
                listaOrden.add(Order.desc("tipoInspeccion"));
            }
            
        } else if ("Equipo".equals(columna)) {
            listaOrden = new ArrayList<>();
            if (sortOrder) {
                listaOrden.add(Order.asc("equipo.nombreEquipo"));
            } else {
                listaOrden.add(Order.desc("equipo.nombreEquipo"));
            }
            
        } else if ("Cuatrimestre".equals(columna)) {
            listaOrden = new ArrayList<>();
            if (sortOrder) {
                listaOrden.add(Order.asc("cuatrimestre"));
            } else {
                listaOrden.add(Order.desc("cuatrimestre"));
            }
            
        } else if ("Ámbito".equals(columna)) {
            listaOrden = new ArrayList<>();
            if (sortOrder) {
                listaOrden.add(Order.asc("ambito"));
            } else {
                listaOrden.add(Order.desc("ambito"));
            }
            
        } else if ("Estado".equals(columna)) {
            listaOrden = new ArrayList<>();
            if (sortOrder) {
                listaOrden.add(Order.asc("estadoInspeccion"));
            } else {
                listaOrden.add(Order.desc("estadoInspeccion"));
            }
        }
        buscarConOrden(listaOrden);
    }
    
    public String formateaNumeroInspeccion() {
        return Utilities.leadingZeros(inspeccion.getNumero(), 9);
    }
    
    public String formateaNumeroInspeccion(Inspeccion inspeccion) {
        return Utilities.leadingZeros(inspeccion.getNumero(), 9);
    }
    
}
