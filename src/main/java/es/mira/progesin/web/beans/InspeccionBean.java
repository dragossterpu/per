package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
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
 * 
 * Bean controller donde se ubica la lógica de la administración de inspecciones. Busca inspecciones, las guarda,
 * edita...
 * 
 * @author EZENTIS
 *
 */
@Setter
@Getter
@Controller("inspeccionBean")
@Scope("session")
public class InspeccionBean {
    
    /**
     * Variable utilizada para almacenar un parámetro que corresponde a la última úbicación, última vista visitada
     * cuando realizamos una navegación.
     * 
     */
    private String vieneDe;
    
    /**
     * Variable utilizada para almacenar la inspección los valores de filtrado cuando se realiza una busqueda de
     * inspecciones.
     * 
     */
    private InspeccionBusqueda inspeccionBusqueda;
    
    /**
     * Variable utilizada para almacenar provisionalmente la búsqueda del buscador y así distinguir del de asociación.
     * 
     */
    private InspeccionBusqueda busquedaBuscador;
    
    /**
     * Variable utilizada para almacenar la inspección cargada en memoria en ese momento. Puede ser una a modificar, una
     * nueva inspección...
     * 
     */
    private Inspeccion inspeccion;
    
    /**
     * Variable utilizada para almacenar el resultado de mostrar una columna o no en la tabla de búsqueda de
     * inspecciones.
     * 
     */
    private List<Boolean> list;
    
    /**
     * Variable utilizada para almacenar la lista de inspecciones asignadas a cierta inspección antes de proceder a su
     * almacenamiento en base de datos.
     * 
     */
    private List<Inspeccion> inspeccionesAsignadasActuales;
    
    /**
     * Variable utilizada para inyectar el servicio del registro de actividad.
     * 
     */
    @Autowired
    private IRegistroActividadService regActividadService;
    
    /**
     * Variable utilizada para inyectar el servicio de inspecciones.
     * 
     */
    @Autowired
    private IInspeccionesService inspeccionesService;
    
    /**
     * Variable utilizada para inyectar el servicio de equipos.
     * 
     */
    @Autowired
    private IEquipoService equipoService;
    
    /**
     * Variable utilizada para inyectar el servicio de los municipios.
     * 
     */
    @Autowired
    private IMunicipioService municipioService;
    
    /**
     * Variable utilizada para inyectar el servicio de los miembros de un equipo.
     * 
     */
    @Autowired
    private IMiembroService miembroService;
    
    /**
     * Variable utilizada para inyectar el servicio de los tipos de inspección.
     * 
     */
    @Autowired
    private ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Variable utilizada para almacenar la lista de municipios asociados a una inspección.
     * 
     */
    private List<Municipio> listaMunicipios;
    
    /**
     * Variable utilizada para cargar la lista de equipos.
     * 
     */
    private List<Equipo> listaEquipos;
    
    /**
     * Variable utilizada para almacenar el valor de la provincia seleccionada.
     * 
     */
    private Provincia provinciSelec;
    
    /**
     * Declaración del modelo lazy de inspecciones utilizada para la paginación del servidor.
     * 
     */
    private LazyModelInspeccion model;
    
    /**
     * Lista de tipos de inspecciones precargadas para utilizar en los combos de selección.
     * 
     */
    private List<TipoInspeccion> listaTiposInspeccion;
    
    /**
     * Variable utilizada para inyectar el servicio del registro de actividad.
     * 
     */
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    /**
     * Número de columnas de la vista.
     */
    private static final int NUMCOLSTABLA = 17;
    
    /**
     * 
     * Busca las inspeccions según los filtros introducidos en el formulario de búsqueda situandose en la primera página
     * de la tabla y con el orden por defecto.
     * 
     */
    public void buscarInspeccion() {
        inspeccionBusqueda.setProvincia(provinciSelec);
        model.setBusqueda(inspeccionBusqueda);
        model.load(0, 20, "fechaAlta", SortOrder.DESCENDING, null);
        if (!inspeccionBusqueda.isAsociar()) {
            busquedaBuscador = new InspeccionBusqueda();
            setBusquedaBuscador(inspeccionBusqueda);
        }
    }
    
    /**
     * 
     * Visualiza la inspeccion personalizada pasada como parámetro redirigiendo a la vista "visualizaInspección".
     * 
     * @param insp a visualizar
     * @return Devuelve la ruta de la vista visualizarInspecciones
     * 
     */
    
    public String visualizaInspeccion(Inspeccion insp) {
        this.inspeccion = inspeccionesService.findInspeccionById(insp.getId());
        List<Inspeccion> listaAsociadas = inspeccionesService.listaInspeccionesAsociadas(this.inspeccion);
        setInspeccionesAsignadasActuales(listaAsociadas);
        return "/inspecciones/visualizarInspeccion?faces-redirect=true";
    }
    
    /**
     * 
     * Visualiza la vista de busqueda de inspecciones donde podemos asociar otras inspecciones a la que estamos
     * modificando o creando nueva.
     * 
     * @return devuelve la ruta de la vista donde asociamos las inspecciones
     */
    public String getAsociarInspecciones() {
        limpiarBusqueda();
        
        inspeccionBusqueda.setInspeccionModif(inspeccion);
        inspeccionBusqueda.setAsociar(true);
        
        List<Inspeccion> listInspecciones = new ArrayList<>();
        listInspecciones.addAll(inspeccionesAsignadasActuales);
        inspeccionBusqueda.setInspeccionesSeleccionadas(listInspecciones);
        
        setProvinciSelec(null);
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
        if (!inspeccionesAsignadasActuales.isEmpty()) {
            Collections.sort(inspeccionesAsignadasActuales, (o1, o2) -> Long.compare(o1.getId(), o2.getId()));
        }
        if ("asociarAlta".equals(vaHacia)) {
            rutaSiguiente = "/inspecciones/altaInspeccion?faces-redirect=true";
            
        } else if ("asociarEditar".equals(vaHacia)) {
            rutaSiguiente = "/inspecciones/modificarInspeccion?faces-redirect=true";
            
        }
        inspeccionBusqueda.setAsociar(false);
        setVieneDe("distinta");
        
        return rutaSiguiente;
    }
    
    /**
     * 
     * Limpia los valores del objeto de búsqueda de inspecciones.
     * 
     */
    
    public void limpiarBusqueda() {
        inspeccionBusqueda = new InspeccionBusqueda();
        setProvinciSelec(null);
        model.setRowCount(0);
        if ("asociarAlta".equals(vieneDe) || "asociarEditar".equals(vieneDe)) {
            inspeccionBusqueda.setAsociar(true);
        } else {
            inspeccionBusqueda.setAsociar(false);
        }
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
        inspeccion.setInspecciones(new ArrayList<>());
        setProvinciSelec(null);
        
        Miembro miembro = miembroService.buscaMiembroByUsername(user.getUsername());
        
        if (miembro != null && miembro.getPosicion().equals(RolEquipoEnum.JEFE_EQUIPO)) {
            listaEquipos = new ArrayList<>();
            listaEquipos.add(miembro.getEquipo());
        }
        
        return "/inspecciones/altaInspeccion?faces-redirect=true";
        
    }
    
    /**
     * Método que guarda la inspección nueva creada en la base de datos.
     * @return null nos quedamos en la vista actual.
     * 
     */
    public String altaInspeccion() {
        try {
            inspeccion.setEstadoInspeccion(EstadoInspeccionEnum.SIN_INICIAR);
            inspeccion.setInspecciones(inspeccionesAsignadasActuales);
            
            inspeccion = inspeccionesService.save(inspeccion);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                    "La inspección " + inspeccion.getNumero() + " ha sido creada con éxito");
            String descripcion = "Alta nueva inspección " + inspeccion.getNumero();
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.INSPECCION.name());
        } catch (DataAccessException e) {
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
        return null;
    }
    
    /**
     * Método que prepara el objeto y los parámetros necesarios antes de proceder a su modificación. Posteriormente
     * redirige a la vista de modificar inspección.
     * 
     * @param insp seleccionada
     * @return devuelve la ruta donde se realiza la modificaión de la inspección
     */
    public String getFormModificarInspeccion(Inspeccion insp) {
        setListaMunicipios(municipioService.findByProvincia(insp.getMunicipio().getProvincia()));
        
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Miembro miembro = miembroService.buscaMiembroByUsername(user.getUsername());
        
        if (miembro != null && miembro.getPosicion().equals(RolEquipoEnum.JEFE_EQUIPO)) {
            listaEquipos = new ArrayList<>();
            listaEquipos.add(miembro.getEquipo());
        }
        
        this.inspeccion = insp;
        setProvinciSelec(insp.getMunicipio().getProvincia());
        setInspeccion(insp);
        inspeccionesAsignadasActuales = inspeccionesService.listaInspeccionesAsociadas(this.inspeccion);
        if (inspeccionesAsignadasActuales == null) {
            inspeccionesAsignadasActuales = new ArrayList<>();
        }
        
        return "/inspecciones/modificarInspeccion?faces-redirect=true";
    }
    
    /**
     * 
     * Guarda la inspección modificada en la base de datos.
     * 
     */
    public void modificarInspeccion() {
        try {
            
            inspeccion.setInspecciones(inspeccionesAsignadasActuales);
            inspeccionesService.save(inspeccion);
            limpiarBusqueda();
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                    "La inspección ha sido modificada con éxito");
            
            String descripcion = "Modificación de la inspección : " + inspeccion.getNumero();
            
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.INSPECCION.name());
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Modificación",
                    "Se ha producido un error al modificar la inspección. Inténtelo de nuevo más tarde");
            // Guardamos los posibles errores en bbdd
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
        
    }
    
    /**
     * 
     * Inicializa el bean de búsqueda.
     * 
     */
    
    @PostConstruct
    public void init() {
        inspeccionBusqueda = new InspeccionBusqueda();
        listaMunicipios = new ArrayList<>();
        setList(new ArrayList<>());
        for (int i = 0; i <= NUMCOLSTABLA; i++) {
            list.add(Boolean.TRUE);
        }
        model = new LazyModelInspeccion(inspeccionesService);
        setListaTiposInspeccion(tipoInspeccionService.buscaTodos());
    }
    
    /**
     * 
     * Limpia el menú de búsqueda si se accede a través del menú lateral.
     * @return ruta siguiente
     * 
     */
    
    public String getFormularioBusqueda() {
        setVieneDe("menu");
        listaEquipos = equipoService.findAll();
        limpiarBusqueda();
        
        return "/inspecciones/inspecciones?faces-redirect=true";
    }
    
    /**
     * Cuando un usuario use la opción de eliminar se hará una elimianción lógica de la base de datos (fecha de baja
     * será null).
     * 
     * @param inspeccionEliminar inspección a dar de baja
     * 
     */
    public void eliminar(Inspeccion inspeccionEliminar) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        inspeccionEliminar.setFechaBaja(new Date());
        inspeccionEliminar.setUsernameBaja(user);
        
        try {
            inspeccionesService.save(inspeccionEliminar);
            
            String descripcion = "El usuario " + user + " ha eliminado la inspección " + inspeccion.getNumero();
            
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                    SeccionesEnum.INSPECCION.name());
            
        } catch (DataAccessException e) {
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), e);
        }
    }
    
    /**
     * Guarda un nuevo municipio.
     * 
     * @param nombre del municipio nuevo
     * @param provincia a la que se asocia el nuevo municipio
     */
    public void nuevoMunicipio(String nombre, Provincia provincia) {
        boolean existeMunicipio = municipioService.existeByNameIgnoreCaseAndProvincia(nombre.trim(), provincia);
        if (existeMunicipio) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Acción no permitida",
                    "Ya existe un municipio perteneciente a la misma provincia con ese nombre", "inputNombre");
        } else {
            Municipio nuevoMunicipio;
            try {
                nuevoMunicipio = municipioService.crearMunicipio(nombre, provincia);
                listaMunicipios.add(nuevoMunicipio);
                Collections.sort(listaMunicipios, (o1, o2) -> Long.compare(o1.getId(), o2.getId()));
                inspeccion.setMunicipio(nuevoMunicipio);
                RequestContext.getCurrentInstance().execute("PF('dialogMunicipio').hide()");
            } catch (DataAccessException e) {
                registroActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), e);
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                        "Error al guardar municipio. Inténtelo de nuevo más tarde.", null, "inputNombre");
            }
            
        }
    }
    
    /**
     * Devuelve una lista de municipios pertenecientes a una provincia. Se utiliza para recargar la lista de municipios
     * dependiendo de la provincia seleccionada.
     * @param provincia seleccionada
     */
    public void onChangeProvincia(Provincia provincia) {
        if (provincia != null) {
            setListaMunicipios(municipioService.findByProvincia(provincia));
        } else {
            setListaMunicipios(null);
        }
        
    }
    
    /**
     * Método que desasocia una inspección al deseleccionar su checkbox.
     *
     * @param event evento lanzado que contiene la inspección
     */
    public void onRowUnSelected(UnselectEvent event) {
        Inspeccion i = (Inspeccion) event.getObject();
        inspeccionesAsignadasActuales.remove(i);
        inspeccionBusqueda.setSelectedAll(false);
    }
    
    /**
     * Método que asocia una inspección al seleccionar su checkbox.
     *
     * @param event evento lanzado que contiene la inspección
     */
    public void onRowSelected(SelectEvent event) {
        Inspeccion i = (Inspeccion) event.getObject();
        inspeccionesAsignadasActuales.add(i);
        if (model.getRowCount() == inspeccionesAsignadasActuales.size()) {
            inspeccionBusqueda.setSelectedAll(true);
        }
    }
    
    /**
     * Método que capura el evento "Seleccionar todos" o "Deseleccionar todos" en la vista de asociar inspecciones.
     * Automáticamente los carga en la lista de inspecciones asociadas.
     * 
     */
    public void onToggleSelect() {
        List<Inspeccion> inspeccionesBusqueda = inspeccionesService.buscarInspeccionPorCriteria(0, model.getRowCount(),
                null, null, inspeccionBusqueda);
        
        if (!inspeccionBusqueda.isSelectedAll()) {
            for (Inspeccion i : inspeccionesBusqueda) {
                if (!inspeccionesAsignadasActuales.contains(i)) {
                    inspeccionesAsignadasActuales.add(i);
                }
            }
            
            inspeccionBusqueda.setSelectedAll(true);
        } else {
            for (Inspeccion i : inspeccionesBusqueda) {
                if (inspeccionesAsignadasActuales.contains(i)) {
                    inspeccionesAsignadasActuales.remove(i);
                }
            }
            inspeccionBusqueda.setSelectedAll(false);
        }
    }
    
    /**
     * Añade todas las inspecciones asignadas a las inspecciones seleccionadas en la tabla para no perderlas al cambiar
     * de página.
     * 
     */
    public void paginator() {
        List<Inspeccion> listInspecciones = new ArrayList<>();
        listInspecciones.addAll(inspeccionesAsignadasActuales);
        inspeccionBusqueda.setInspeccionesSeleccionadas(listInspecciones);
    }
    
    /**
     * Elimina una inspección de la lista de inspecciones asociadas.
     * 
     * @param insp Inspección a desasociar
     */
    public void desAsociarInspeccion(Inspeccion insp) {
        inspeccionesAsignadasActuales.remove(insp);
    }
    
    /**
     * Controla las columnas visibles en la lista de resultados del buscador.
     * 
     * @param e checkbox de la columna seleccionada
     */
    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
    
    /**
     * Recupera el objeto de búsqueda al volver a la vista de búsqueda de inspecciones.
     * @return ruta siguiente
     */
    public String recuperarBusqueda() {
        inspeccionBusqueda = new InspeccionBusqueda();
        setProvinciSelec(busquedaBuscador.getProvincia());
        setInspeccionBusqueda(busquedaBuscador);
        model.setBusqueda(inspeccionBusqueda);
        model.load(0, 20, "fechaAlta", SortOrder.DESCENDING, null);
        return "/inspecciones/inspecciones?faces-redirect=true";
    }
    
}
