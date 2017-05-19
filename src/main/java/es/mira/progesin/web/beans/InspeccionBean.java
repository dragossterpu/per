package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.lazydata.LazyModelInspeccion;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.IMiembroService;
import es.mira.progesin.services.IMunicipioService;
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
    
    private Inspeccion inspeccion;
    
    private List<Boolean> list;
    
    private List<Inspeccion> inspeccionesAsignadasActuales;
    
    private List<Inspeccion> inspeccionesSeleccionadas;
    
    @Autowired
    private IRegistroActividadService regActividadService;
    
    @Autowired
    private IInspeccionesService inspeccionesService;
    
    @Autowired
    private IEquipoService equipoService;
    
    @Autowired
    private IMunicipioService municipioService;
    
    @Autowired
    private IMiembroService miembroService;
    
    @Autowired
    private ITipoInspeccionService tipoInspeccionService;
    
    private List<Municipio> listaMunicipios;
    
    private List<Equipo> listaEquipos;
    
    private Provincia provinciSelec;
    
    private boolean selectedAll;
    
    private LazyModelInspeccion model;
    
    private static final String EL_USUARIO = "El usuario";
    
    private List<TipoInspeccion> listaTiposInspeccion;
    
    /**************************************************************
     * 
     * Busca las inspeccions según los filtros introducidos en el formulario de búsqueda situandose en la primera página
     * de la tabla y con el orden por defecto
     * 
     **************************************************************/
    public void buscarInspeccion() {
        model.setBusqueda(inspeccionBusqueda);
        model.load(0, 20, "fechaAlta", SortOrder.DESCENDING, null);
    }
    
    /*********************************************************
     * 
     * Visualiza la inspeccion personalizada pasada como parámetro redirigiendo a la vista "visualizaInspección"
     * 
     * @param inspeccion a visualizar
     * @return Devuelve la ruta de la vista visualizarInspecciones
     * 
     *********************************************************/
    
    public String visualizaInspeccion(Inspeccion inspeccion) {
        this.inspeccion = inspeccionesService.findInspeccionById(inspeccion.getId());
        List<Inspeccion> listaAsociadas = inspeccionesService.listaInspeccionesAsociadas(this.inspeccion);
        setInspeccionesAsignadasActuales(listaAsociadas);
        return "/inspecciones/visualizarInspeccion?faces-redirect=true";
    }
    
    /**
     * Visualiza la vista de busqueda de inspecciones donde podemos asociar otras inspecciones a la que estamos
     * modificando o creando nueva
     * 
     * @return devuelve la ruta de la vista donde asociamos las inspecciones
     */
    public String getAsociarInspecciones() {
        inspeccionBusqueda.resetValues();
        inspeccionBusqueda.setAsociar(true);
        setInspeccionesSeleccionadas(inspeccionesAsignadasActuales);
        buscarInspeccion();
        
        return "/inspecciones/inspecciones?faces-redirect=true";
    }
    
    /**
     * Después de asociar/desasociar inspeccines nos redirige a la vista de alta o de modificación dependiendo de si
     * estamos haciendo un alta nueva o una modificación respectivamente.
     * 
     * @return devuelve la ruta del alta o modificación de inspecciones dependiendo de la accción que estamos realizando
     */
    public String asociarInspecciones() {
        String rutaSiguiente = null;
        String vaHacia = this.vieneDe;
        if (inspeccion.getMunicipio() != null) {
            setProvinciSelec(inspeccion.getMunicipio().getProvincia());
        }
        Collections.sort(inspeccionesAsignadasActuales);
        if ("asociarAlta".equals(vaHacia)) {
            rutaSiguiente = "/inspecciones/altaInspeccion?faces-redirect=true";
            
        } else if ("asociarEditar".equals(vaHacia)) {
            rutaSiguiente = "/inspecciones/modificarInspeccion?faces-redirect=true";
            
        }
        inspeccionBusqueda.setAsociar(false);
        
        return rutaSiguiente;
    }
    
    /*********************************************************
     * 
     * Limpia los valores del objeto de búsqueda de inspecciones
     * 
     *********************************************************/
    
    public void limpiarBusqueda() {
        inspeccionBusqueda.resetValues();
        model.setRowCount(0);
    }
    
    /**
     * Método que nos lleva al formulario de alta de nueva inspección, inicializando todo lo necesario para mostrar
     * correctamente la página. Se llama desde la página de búsqueda de inspecciones.
     * @return devuelve la ruta donde realizamos el alta de la inspección
     */
    public String nuevaInspeccion() {
        setListaMunicipios(null);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        inspeccionesAsignadasActuales = new ArrayList<>();
        inspeccion = new Inspeccion();
        inspeccion.setEstadoInspeccion(EstadoInspeccionEnum.SIN_INICIAR);
        inspeccion.setInspecciones(new ArrayList<>());
        inspeccionBusqueda.setInspeccionModif(inspeccion);
        setProvinciSelec(null);
        
        Miembro miembro = miembroService.buscaMiembroByUsername(user.getUsername());
        
        if (miembro != null && miembro.getPosicion().equals(RolEquipoEnum.JEFE_EQUIPO)) {
            listaEquipos = new ArrayList<>();
            listaEquipos.add(miembro.getEquipo());
        }
        
        return "/inspecciones/altaInspeccion?faces-redirect=true";
        
    }
    
    /**
     * Método que guarda la inspección nueva creada en la base de datos
     * @return null no redirigimos
     * 
     */
    public String altaInspeccion() {
        try {
            
            Inspeccion inspeccionProvisional = inspeccionesService.save(inspeccion);
            inspeccionProvisional.setFechaAlta(new Date());
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            inspeccionProvisional.setUsernameAlta(user);
            
            StringBuilder numero = new StringBuilder(String.valueOf(inspeccionProvisional.getId()));
            numero.append("/");
            numero.append(inspeccionProvisional.getAnio());
            inspeccion.setNumero(numero.toString());
            
            if (inspeccionesAsignadasActuales != null) {
                inspeccion.setInspecciones(inspeccionesAsignadasActuales);
            }
            inspeccionesService.save(inspeccion);
            inspeccionBusqueda.resetValues();
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                    "La inspección " + numero + " ha sido creada con éxito");
            String descripcion = "Alta nueva inspección " + inspeccion.getNumero();
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.INSPECCION.name());
        } catch (
        
        Exception e) {
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
        return null;
    }
    
    /**
     * Método que prepara el objeto y los parámetros necesarios antes de proceder a su modificación. Posteriormente
     * redirige a la vista de modificar inspección.
     * 
     * @param inspeccion
     * @return devuelve la ruta donde se realiza la modificaión de la inspección
     */
    public String getFormModificarInspeccion(Inspeccion inspeccion) {
        setListaMunicipios(municipioService.findByProvincia(inspeccion.getMunicipio().getProvincia()));
        
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Miembro miembro = miembroService.buscaMiembroByUsername(user.getUsername());
        
        if (miembro != null && miembro.getPosicion().equals(RolEquipoEnum.JEFE_EQUIPO)) {
            listaEquipos = new ArrayList<>();
            listaEquipos.add(miembro.getEquipo());
        }
        
        this.inspeccion = inspeccion;
        setProvinciSelec(inspeccion.getMunicipio().getProvincia());
        inspeccionBusqueda.setInspeccionModif(inspeccion);
        inspeccionesAsignadasActuales = inspeccionesService.listaInspeccionesAsociadas(this.inspeccion);
        
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
            if (inspeccionesAsignadasActuales != null) {
                inspeccion.setInspecciones(inspeccionesAsignadasActuales);
            }
            inspeccion.setFechaModificacion(new Date());
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            inspeccion.setUsernameModif(user);
            
            inspeccionesService.save(inspeccion);
            inspeccionBusqueda.resetValues();
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                    "La inspección ha sido modificada con éxito");
            
            String descripcion = "Modificación de la inspección : " + inspeccion.getNumero();
            
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.INSPECCION.name());
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
        inspeccionBusqueda.setAsociar(false);
        listaMunicipios = new ArrayList<>();
        inspeccionesSeleccionadas = new ArrayList<>();
        setList(new ArrayList<>());
        for (int i = 0; i <= 14; i++) {
            list.add(Boolean.TRUE);
        }
        model = new LazyModelInspeccion(inspeccionesService);
        setListaTiposInspeccion(tipoInspeccionService.buscaTodos());
    }
    
    /*********************************************************
     * 
     * Limpia el menú de búsqueda si se accede a través del menú lateral
     * 
     *********************************************************/
    
    public void getFormularioBusqueda() {
        if ("menu".equalsIgnoreCase(this.vieneDe)) {
            inspeccionBusqueda.setAsociar(false);
            limpiarBusqueda();
            this.vieneDe = null;
            listaEquipos = equipoService.findAll();
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
            inspeccionesService.save(inspeccionEliminar);
            
            String descripcion = EL_USUARIO + " " + user + " ha eliminado la inspección " + inspeccion.getNumero();
            
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                    SeccionesEnum.INSPECCION.name());
            
        } catch (Exception e) {
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), e);
        }
    }
    
    /**
     * Guarda un nuevo municipio
     * @param nombre del municipio nuevo
     * @param provincia a la que se asocia el nuevo municipio
     */
    public void nuevoMunicipio(String nombre, Provincia provincia) {
        boolean existeMunicipio = municipioService.existeByNameIgnoreCaseAndProvincia(nombre.trim(), provincia);
        if (existeMunicipio) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Acción no permitida",
                    "Ya existe un municipio perteneciente a la misma provincia con ese nombre", "inputNombre");
        } else {
            Municipio nuevoMunicipio = municipioService.crearMunicipio(nombre, provincia);
            listaMunicipios.add(nuevoMunicipio);
            Collections.sort(listaMunicipios);
            inspeccion.setMunicipio(nuevoMunicipio);
            RequestContext.getCurrentInstance().execute("PF('dialogMunicipio').hide()");
        }
    }
    
    /**
     * Devuelve una lista de municipios pertenecientes a una provincia. Se utiliza para recargar la lista de municipios
     * dependiendo de la provincia seleccionad.
     * @param provincia
     */
    public void onChangeProvincia(Provincia provincia) {
        if (provincia != null) {
            setListaMunicipios(municipioService.findByProvincia(provincia));
        } else {
            setListaMunicipios(null);
        }
        
    }
    
    /**
     * Método que desasocia una inspección al seleccionar el checkboc
     *
     * @param event
     */
    public void onRowUnSelected(UnselectEvent event) {
        Inspeccion i = (Inspeccion) event.getObject();
        inspeccionesAsignadasActuales.remove(i);
    }
    
    /**
     * Método que asocia una inspección al seleccionar el checkboc
     *
     * @param event
     */
    public void onRowSelected(SelectEvent event) {
        Inspeccion i = (Inspeccion) event.getObject();
        inspeccionesAsignadasActuales.add(i);
    }
    
    /**
     * Método que capura el evento "Seleccionar todos" o "Deseleccionar todos" en el momento de asociar una inspección.
     * Automáticamente los carga en la lista de inspecciones asociadas.
     */
    public void onToggleSelect() {
        if (!isSelectedAll()) {
            setInspeccionesAsignadasActuales(inspeccionesService.buscarInspeccionPorCriteria(0,
                    inspeccionesService.getCountInspeccionCriteria(inspeccionBusqueda), null, null,
                    inspeccionBusqueda));
            setSelectedAll(true);
        } else {
            setInspeccionesAsignadasActuales(null);
            setSelectedAll(false);
        }
    }
    
    /**
     * Añade todas las inspecciones asignadas a las inspecciones seleccionadas en la tabla para no perderlas cuando
     * realizamos la paginación de servidor.
     */
    public void paginator() {
        inspeccionesSeleccionadas = inspeccionesAsignadasActuales;
    }
    
    /**
     * Elimina una inspección de la lista de inspecciones asociadas
     * 
     * @param inspeccion Inspección a desasociar
     */
    public void desAsociarInspeccion(Inspeccion inspeccion) {
        inspeccionesAsignadasActuales.remove(inspeccion);
    }
    
}
