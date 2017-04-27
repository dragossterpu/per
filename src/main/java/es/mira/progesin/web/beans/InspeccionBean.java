package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.criterion.Order;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.data.SortEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ITipoInspeccionService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * @author EZENTIS
 *
 */
@Setter
@Getter
@Controller("inspeccionBean")
@Scope("session")
public class InspeccionBean {
    
    private String vieneDe;
    
    private InspeccionBusqueda inspeccionBusqueda;
    
    private InspeccionBusqueda inspeccionBusquedaCopia;
    
    private Inspeccion inspeccion;
    
    private List<Boolean> list;
    
    private List<Inspeccion> inspeccionesAsignadas;
    
    private List<Inspeccion> inspeccionesSeleccionadas;
    
    @Autowired
    private IRegistroActividadService regActividadService;
    
    @Autowired
    private IInspeccionesService inspeccionesService;
    
    @Autowired
    private IEquipoService equipoService;
    
    @Autowired
    private ITipoInspeccionService tipoInspeccionService;
    
    private static final int MAX_RESULTS_PAGE = 20;
    
    private static final int TODOS_RESULTS_PAGE = -1;
    
    private static final int FIRST_PAGE = 1;
    
    private long numeroRegistros;
    
    private int primerRegistro;
    
    private long actualPage;
    
    private long numPages;
    
    @PersistenceContext
    private EntityManager em;
    
    private List<Municipio> listaMunicipios;
    
    private List<Equipo> listaEquipos;
    
    private Provincia provinciSelec;
    
    private List<Order> listaOrden;
    
    private boolean sortOrder;
    
    private static final String FECHAALTA = "fechaAlta";
    
    private boolean selectedAll;
    
    private List<TipoInspeccion> listaTiposInspeccion;
    
    /**************************************************************
     * 
     * Busca las inspeccions según los filtros introducidos en el formulario de búsqueda situandose en la primera página
     * de la tabla y con el orden por defecto
     * 
     **************************************************************/
    public void buscarInspeccion() {
        inspeccionBusqueda.setProvincia(provinciSelec);
        listaOrden = new ArrayList<>();
        listaOrden.add(Order.desc(FECHAALTA));
        primerRegistro = 0;
        actualPage = FIRST_PAGE;
        inspeccionBusquedaCopia = copiaInspeccionBusqueda(inspeccionBusqueda);
        buscarConOrden(listaOrden, primerRegistro, MAX_RESULTS_PAGE);
        
    }
    
    /**************************************************************
     * 
     * Busca las inspeccions según los filtros introducidos en el formulario de búsqueda
     * 
     **************************************************************/
    private void buscarConOrden(List<Order> listaOrden, int primerResgistro, int numRegistros) {
        
        setNumeroRegistros(getCountRegistrosInspecciones());
        numPages = getCountPages(numeroRegistros);
        inspeccionBusqueda.setListaInspecciones(inspeccionesService.buscarInspeccionPorCriteria(primerResgistro,
                numRegistros, inspeccionBusquedaCopia, listaOrden));
        
        if ("asociarEditar".equals(vieneDe) || "asociarAlta".equals(vieneDe)) {
            
            if (inspeccion != null && inspeccionBusqueda.getListaInspecciones().remove(inspeccion)) {
                numeroRegistros--;
                setNumPages(getCountPages(numeroRegistros));
            }
            setInspeccionesSeleccionadas(inspeccionesAsignadas);
            
        }
        
    }
    
    /*********************************************************
     * 
     * Visualiza la inspeccion personalizada pasada como parámetro redirigiendo a la vista "visualizaInspección"
     * 
     * @param inspeccion
     * @return String
     * 
     *********************************************************/
    
    public String visualizaInspeccion(Inspeccion inspeccion) {
        this.inspeccion = inspeccionesService.findInspeccionById(inspeccion.getId());
        List<Inspeccion> listaAsociadas = inspeccionesService.listaInspeccionesAsociadas(this.inspeccion);
        setInspeccionesAsignadas(listaAsociadas);
        return "/inspecciones/visualizarInspeccion?faces-redirect=true";
    }
    
    public String getAsociarInspecciones() {
        inspeccionBusqueda.resetValues();
        setProvinciSelec(null);
        buscarInspeccion();
        
        return "/inspecciones/inspecciones?faces-redirect=true";
    }
    
    /**
     * Después de asociar/desasociar inspeccines nos redirige a la vista de alta o de modificación dependiendo de si
     * estamos haciendo un alta nueva o una modificación respectivamente.
     * 
     * @return
     */
    public String asociarInspecciones() {
        String rutaSiguiente = null;
        String vaHacia = this.vieneDe;
        if (inspeccion.getMunicipio() != null) {
            setProvinciSelec(inspeccion.getMunicipio().getProvincia());
        }
        
        if ("asociarAlta".equals(vaHacia)) {
            rutaSiguiente = "/inspecciones/altaInspeccion?faces-redirect=true";
            
        } else if ("asociarEditar".equals(vaHacia)) {
            rutaSiguiente = "/inspecciones/modificarInspeccion?faces-redirect=true";
            
        }
        return rutaSiguiente;
    }
    
    /*********************************************************
     * 
     * Limpia los valores del objeto de búsqueda
     * 
     *********************************************************/
    
    public void limpiarBusqueda() {
        inspeccionBusqueda.resetValues();
        setProvinciSelec(null);
    }
    
    /**
     * Método que nos lleva al formulario de alta de nueva inspección, inicializando todo lo necesario para mostrar
     * correctamente la página. Se llama desde la página de búsqueda de inspecciones.
     * @return
     */
    public String nuevaInspeccion() {
        setProvinciSelec(null);
        setListaMunicipios(null);
        inspeccionesAsignadas = new ArrayList<>();
        inspeccionesSeleccionadas = new ArrayList<>();
        inspeccion = new Inspeccion();
        inspeccion.setEstadoInspeccion(EstadoInspeccionEnum.ESTADO_SIN_INICIAR);
        inspeccion.setInspecciones(new ArrayList<>());
        listaTiposInspeccion = tipoInspeccionService.buscaByFechaBajaIsNull();
        
        return "/inspecciones/altaInspeccion?faces-redirect=true";
    }
    
    /**
     * Método que guarda la inspección nueva creada en la base de datos
     * 
     * @return
     */
    public String altaInspeccion() {
        try {
            
            Inspeccion inspeccionProvisional = null;
            if ((inspeccionProvisional = inspeccionesService.save(inspeccion)) != null) {
                
                StringBuilder numero = new StringBuilder(String.valueOf(inspeccionProvisional.getId()));
                numero.append("/");
                numero.append(inspeccionProvisional.getAnio());
                inspeccion.setNumero(numero.toString());
                
                if (inspeccionesAsignadas != null) {
                    inspeccion.setInspecciones(inspeccionesAsignadas);
                }
                inspeccionesService.save(inspeccion);
                inspeccionBusqueda.resetValues();
                setProvinciSelec(null);
                
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                        "La inspección " + numero + " ha sido creada con éxito");
                String descripcion = "Alta nueva inspección " + inspeccion.getNumero();
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                        SeccionesEnum.INSPECCION.name());
            }
        } catch (Exception e) {
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
        return null;
    }
    
    /**
     * Método que prepara el objeto y los parámetros necesarios antes de proceder a su modificación. Posteriormente
     * redirige a la vista de modificar inspección.
     * 
     * @param inspeccion
     * @return ruta modificar objeto
     */
    public String getFormModificarInspeccion(Inspeccion inspeccion) {
        setProvinciSelec(inspeccion.getMunicipio().getProvincia());
        TypedQuery<Municipio> queryEmpleo = em.createNamedQuery("Municipio.findByCode_province", Municipio.class);
        queryEmpleo.setParameter("provinciaSeleccionada", provinciSelec);
        listaMunicipios = queryEmpleo.getResultList();
        this.inspeccion = inspeccion;
        inspeccionesAsignadas = inspeccionesService.listaInspeccionesAsociadas(this.inspeccion);
        listaTiposInspeccion = tipoInspeccionService.buscaByFechaBajaIsNull();
        
        return "/inspecciones/modificarInspeccion?faces-redirect=true";
    }
    
    /**
     * Guarda la inspección modificada en la base de datos.
     */
    public void modificarInspeccion() {
        try {
            
            StringBuilder numero = new StringBuilder(String.valueOf(inspeccion.getId()));
            numero.append("/");
            numero.append(inspeccion.getAnio());
            inspeccion.setNumero(numero.toString());
            if (inspeccionesAsignadas != null) {
                inspeccion.setInspecciones(inspeccionesAsignadas);
            }
            
            if (inspeccionesService.save(inspeccion) != null) {
                inspeccionBusqueda.resetValues();
                setProvinciSelec(null);
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                        "La inspección ha sido modificada con éxito");
                
                String descripcion = "Modificación de la inspección : " + inspeccion.getNumero();
                
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                        SeccionesEnum.INSPECCION.name());
            }
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Modificación",
                    "Se ha producido un error al modificar la inspección. Inténtelo de nuevo más tarde");
            // Guardamos los posibles errores en bbdd
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
        
    }
    
    /*********************************************************
     * 
     * Inicializa el bean
     * 
     *********************************************************/
    
    @PostConstruct
    public void init() {
        inspeccionBusqueda = new InspeccionBusqueda();
        setListaEquipos(equipoService.findByFechaBajaIsNotNull());
        setProvinciSelec(null);
        listaMunicipios = new ArrayList<>();
        setList(new ArrayList<>());
        for (int i = 0; i <= 14; i++) {
            list.add(Boolean.TRUE);
        }
    }
    
    /*********************************************************
     * 
     * Limpia el menú de búsqueda si se accede a la vista desde el menú lateral o si venimos de la asignación de una
     * inspeción
     * 
     *********************************************************/
    
    public void getFormularioBusqueda() {
        if ("menu".equalsIgnoreCase(this.vieneDe)) {
            
            setProvinciSelec(null);
            limpiarBusqueda();
            this.vieneDe = null;
            listaTiposInspeccion = tipoInspeccionService.buscaTodos();
        }
        
    }
    
    /**
     * Cargar la página siguiente de resultados de la búsqueda
     */
    public void nextInspeccion() {
        
        if (actualPage < numPages) {
            
            primerRegistro += MAX_RESULTS_PAGE;
            actualPage++;
            
            buscarConOrden(listaOrden, primerRegistro, MAX_RESULTS_PAGE);
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
            
            buscarConOrden(listaOrden, primerRegistro, MAX_RESULTS_PAGE);
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
     * Calcula el número de páginas de la consulta criteria a partir del número de registros devuelto.
     * 
     * @param countRegistros
     * @return número de páginas.
     * @author EZENTIS
     */
    public long getCountPages(long countRegistros) {
        long numPag;
        if (countRegistros % MAX_RESULTS_PAGE == 0)
            numPag = countRegistros / MAX_RESULTS_PAGE;
        else
            numPag = countRegistros / MAX_RESULTS_PAGE + 1;
        
        return numPag;
    }
    
    /**
     * Construye una copia del filtro de búsqueda de inspecciones. Funciona de la siguiente manera: sólo se construirá
     * un objeto nuevo al realizar una nueva búsqueda; en el resto de casos (ordenar, página siguiente, página
     * anterior..) se trabajará con una copia del primero.
     * 
     * @param inspeccion
     * @return devolvemos la copia
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
    
    /**
     * 
     */
    public void onChangeProvincia() {
        TypedQuery<Municipio> queryEmpleo = em.createNamedQuery("Municipio.findByCode_province", Municipio.class);
        queryEmpleo.setParameter("provinciaSeleccionada", this.provinciSelec);
        listaMunicipios = queryEmpleo.getResultList();
        
    }
    
    /**
     * Realiza la ordenación de la tabla de inspecciones pasandole a la consulta criteria una lista de órdenes.
     * 
     * @param event
     */
    public void onSort(SortEvent event) {
        String columna = event.getSortColumn().getHeaderText();
        setSortOrder(event.isAscending());
        setListaOrden(new ArrayList<>());
        
        if ("Numero".equals(columna)) {
            if (sortOrder) {
                listaOrden.add(Order.asc("id"));
                listaOrden.add(Order.asc("anio"));
            } else {
                listaOrden.add(Order.desc("id"));
                listaOrden.add(Order.desc("anio"));
            }
            
        } else if ("Fecha".equals(columna)) {
            if (sortOrder) {
                listaOrden.add(Order.asc(FECHAALTA));
            } else {
                listaOrden.add(Order.desc(FECHAALTA));
            }
            
        } else if ("Usuario alta inspección".equals(columna)) {
            if (sortOrder) {
                listaOrden.add(Order.asc("usernameAlta"));
            } else {
                listaOrden.add(Order.desc("usernameAlta"));
            }
            
        } else if ("Tipo".equals(columna)) {
            if (sortOrder) {
                listaOrden.add(Order.asc("tipoInspeccion"));
            } else {
                listaOrden.add(Order.desc("tipoInspeccion"));
            }
            
        } else if ("Equipo".equals(columna)) {
            if (sortOrder) {
                listaOrden.add(Order.asc("equipo.nombreEquipo"));
            } else {
                listaOrden.add(Order.desc("equipo.nombreEquipo"));
            }
            
        } else if ("Jefe".equals(columna)) {
            if (sortOrder) {
                listaOrden.add(Order.asc("equipo.jefeEquipo"));
            } else {
                listaOrden.add(Order.desc("equipo.jefeEquipo"));
            }
            
        } else if ("Cuatrimestre".equals(columna)) {
            if (sortOrder) {
                listaOrden.add(Order.asc("cuatrimestre"));
            } else {
                listaOrden.add(Order.desc("cuatrimestre"));
            }
            
        } else if ("Ámbito".equals(columna)) {
            if (sortOrder) {
                listaOrden.add(Order.asc("ambito"));
            } else {
                listaOrden.add(Order.desc("ambito"));
            }
            
        } else if ("Estado".equals(columna)) {
            if (sortOrder) {
                listaOrden.add(Order.asc("estadoInspeccion"));
            } else {
                listaOrden.add(Order.desc("estadoInspeccion"));
            }
            
        } else if ("Tipo unid.".equals(columna)) {
            if (sortOrder) {
                listaOrden.add(Order.asc("tipoUnidad"));
            } else {
                listaOrden.add(Order.desc("tipoUnidad"));
            }
            
        } else if ("Nombre unid.".equals(columna)) {
            if (sortOrder) {
                listaOrden.add(Order.asc("nombreUnidad"));
            } else {
                listaOrden.add(Order.desc("nombreUnidad"));
            }
            
        } else if ("Municipio".equals(columna)) {
            if (sortOrder) {
                listaOrden.add(Order.asc("municipio"));
            } else {
                listaOrden.add(Order.desc("municipio"));
            }
            
        } else if ("Provincia".equals(columna)) {
            if (sortOrder) {
                listaOrden.add(Order.asc("municipio.provincia"));
            } else {
                listaOrden.add(Order.desc("municipio.provincia"));
            }
        }
        
        primerRegistro = 0;
        actualPage = FIRST_PAGE;
        buscarConOrden(listaOrden, primerRegistro, MAX_RESULTS_PAGE);
    }
    
    /**
     * 
     * Elimina la fecha de baja de la inspeccion para volver a ponerla activa. Proceso contrario a anular.
     * 
     * @param inspeccion
     */
    public void activa(Inspeccion inspeccion) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        inspeccion.setFechaAnulacion(null);
        inspeccion.setUsernameAnulacion(null);
        inspeccion.setEstadoInspeccion(EstadoInspeccionEnum.ESTADO_SUSPENDIDA);
        try {
            
            if (inspeccionesService.save(inspeccion) != null) {
                String descripcion = "El usuario " + user + " ha activado la inspección " + inspeccion.getNumero();
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                        SeccionesEnum.INSPECCION.name());
                
                buscarConOrden(listaOrden, primerRegistro, MAX_RESULTS_PAGE);
            }
        } catch (Exception e) {
            regActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
        }
    }
    
    /**
     * 
     * Permite la anulación de una inspeccion por lo que su estado cambiará a suspendiada. Una vez anulada no podrá ser
     * usada aunque se mantendrá en la base de datos.
     * 
     * @param inspeccion La inspeccion a anular
     * 
     */
    
    public void anular(Inspeccion inspeccion) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        inspeccion.setFechaAnulacion(new Date());
        inspeccion.setUsernameAnulacion(user);
        try {
            if (inspeccionesService.save(inspeccion) != null) {
                String descripcion = "El usuario " + user + " ha anulado la inspección " + inspeccion.getNumero();
                
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                        SeccionesEnum.INSPECCION.name());
                buscarConOrden(listaOrden, primerRegistro, MAX_RESULTS_PAGE);
            }
        } catch (Exception e) {
            // Guardamos los posibles errores en bbdd
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
        
    }
    
    /**
     * 
     * Cuando un usuario use la opción de eliminar se hará una elimianción lógica de la base de datos (fecha de baja
     * será null)
     * 
     * @param inspeccionEliminar La inspección a anular
     * 
     */
    public void eliminar(Inspeccion inspeccionEliminar) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        inspeccionEliminar.setFechaBaja(new Date());
        inspeccionEliminar.setUsernameBaja(user);
        
        try {
            if (inspeccionesService.save(inspeccionEliminar) != null) {
                
                String descripcion = "El usuario " + user + " ha eliminado la inspección " + inspeccion.getNumero();
                
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                        SeccionesEnum.INSPECCION.name());
                
                primerRegistro = 0;
                actualPage = FIRST_PAGE;
                buscarConOrden(listaOrden, primerRegistro, MAX_RESULTS_PAGE);
            }
        } catch (Exception e) {
            regActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
        }
    }
    
    /**
     * Método que desasocia una inspección al seleccionar el checkboc
     * 
     * @param event
     */
    public void onRowUnSelected(UnselectEvent event) {
        Inspeccion i = (Inspeccion) event.getObject();
        inspeccionesAsignadas.remove(i);
    }
    
    /**
     * Método que asocia una inspección al seleccionar el checkboc
     * 
     * @param event
     */
    public void onRowSelected(SelectEvent event) {
        Inspeccion i = (Inspeccion) event.getObject();
        inspeccionesAsignadas.add(i);
    }
    
    /**
     * Método que capura el evento "Seleccionar todos" en el momento de aociar una inspección
     * 
     * @param event captura el evento
     */
    public void onToggleSelect(AjaxBehaviorEvent event) {
        if (!isSelectedAll()) {
            inspeccionesSeleccionadas = inspeccionesService.buscarInspeccionPorCriteria(0, TODOS_RESULTS_PAGE,
                    inspeccionBusquedaCopia, listaOrden);
            inspeccionesSeleccionadas.remove(inspeccion);
            setInspeccionesAsignadas(inspeccionesSeleccionadas);
            setSelectedAll(true);
        } else {
            setInspeccionesAsignadas(null);
            setSelectedAll(false);
        }
    }
    
}
