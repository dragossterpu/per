package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.lazydata.LazyModelEquipos;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IMiembroService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ITipoEquipoService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador de las operaciones relacionadas con equipos de inspección. Búsqueda, creación, modificación (cambiar el
 * jefe o añadir/quitar miembros) y eliminación de los mismos.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
@Controller("equiposBean")
@Scope("session")
public class EquiposBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private static final String JEFEEQUIPO = "jefeEquipo";
    
    private static final String MIEMBROS = "miembros";
    
    private static final int NUMEROCOLUMNASLISTADOEQUIPOS = 4;
    
    private String vieneDe;
    
    private Equipo equipo;
    
    private User jefeSeleccionado;
    
    private List<User> miembrosSeleccionados;
    
    private List<Boolean> columnasVisibles;
    
    private String estado;
    
    private List<User> listaUsuarios;
    
    private List<User> listadoPotencialesJefes;
    
    private List<User> listadoPotencialesMiembros;
    
    private EquipoBusqueda equipoBusqueda;
    
    private boolean skip;
    
    private TipoEquipo tipoEquipo;
    
    private transient Iterable<TipoEquipo> tiposEquipo;
    
    private LazyModelEquipos model;
    
    @Autowired
    transient ITipoEquipoService tipoEquipoService;
    
    @Autowired
    transient IEquipoService equipoService;
    
    @Autowired
    transient IMiembroService miembroService;
    
    @Autowired
    transient IUserService userService;
    
    @Autowired
    transient IRegistroActividadService regActividadService;
    
    @Autowired
    transient INotificacionService notificacionService;
    
    /**
     * Muestra formulario de alta de nuevos equipos, inicializando lo necesario para mostrar correctamente la página. Se
     * llama desde la página de búsqueda de equipos.
     * 
     * @author EZENTIS
     * @return vista altaEquipo
     */
    public String getFormAltaEquipo() {
        this.setJefeSeleccionado(null);
        this.setMiembrosSeleccionados(new ArrayList<>());
        this.setListadoPotencialesJefes(new ArrayList<>());
        this.setListadoPotencialesMiembros(new ArrayList<>());
        this.setEquipo(new Equipo());
        this.setTipoEquipo(null);
        listaUsuarios = userService.buscarNoJefeNoMiembroEquipo(null);
        listadoPotencialesJefes.addAll(listaUsuarios);
        listadoPotencialesMiembros.addAll(listaUsuarios);
        skip = false;
        return "/equipos/altaEquipo?faces-redirect=true";
    }
    
    /**
     * Recoge los valores introducidos en el formulario y da de alta un equipo normal en la BBDD
     * 
     * @author EZENTIS
     */
    public void altaEquipo() {
        
        try {
            equipo.setJefeEquipo(jefeSeleccionado.getUsername());
            equipo.setNombreJefe(jefeSeleccionado.getNombre() + " " + jefeSeleccionado.getApellido1() + " "
                    + jefeSeleccionado.getApellido2());
            equipo.setTipoEquipo(tipoEquipo);
            
            List<Miembro> miembrosNuevoEquipo = new ArrayList<>();
            Miembro jefe = crearMiembro(RolEquipoEnum.JEFE_EQUIPO, jefeSeleccionado);
            miembrosNuevoEquipo.add(jefe);
            String nombresCompletos = aniadirMiembrosEquipo(RolEquipoEnum.MIEMBRO, miembrosNuevoEquipo);
            equipo.setMiembros(miembrosNuevoEquipo);
            
            equipoService.save(equipo);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                    "El equipo ha sido creado con éxito");
            String descripcion = "Se ha creado un nuevo equipo de inspecciones '" + equipo.getNombreEquipo()
                    + "'. Nombres de componentes " + nombresCompletos;
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.INSPECCION.name());
            notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.INSPECCION.name(), equipo);
            
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
                    "Se ha producido un error al dar de alta el equipo, inténtelo de nuevo más tarde");
            // Guardamos los posibles errores en bbdd
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
        
    }
    
    /**
     * Devuelve al formulario de búsqueda de equipos a su estado inicial y borra los resultados de búsquedas anteriores
     * si se navega desde el menú u otra sección.
     * 
     * @author EZENTIS
     */
    public void getFormularioBusquedaEquipos() {
        if ("menu".equalsIgnoreCase(this.vieneDe)) {
            limpiarBusqueda();
            this.vieneDe = null;
            setTiposEquipo(tipoEquipoService.findAll());
        }
        
    }
    
    /**
     * Devuelve al formulario de búsqueda de equipos a su estado inicial y borra los resultados de búsquedas anteriores.
     * 
     * @author EZENTIS
     */
    public void limpiarBusqueda() {
        equipoBusqueda.resetValues();
        setEstado(null);
        model.setRowCount(0);
    }
    
    /**
     * Busca equipos en base a los campos rellenados en el formulario tanto de equipo como de sus miembros.
     * 
     * @author EZENTIS
     */
    public void buscarEquipo() {
        
        model.setBusqueda(equipoBusqueda);
        model.load(0, 20, "fechaAlta", SortOrder.DESCENDING, null);
    }
    
    /**
     * Elimina un equipo. Se invoca desde la lista de resultados del buscador.
     * 
     * @author EZENTIS
     * @param equipo recuperado del formulario de búsqueda de equipos
     */
    public void eliminarEquipo(Equipo equipo) {
        try {
            // TODO ¿comprobar si hay inspecciones sin finalizar?
            equipo.setFechaBaja(new Date());
            
            equipo.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
            
            equipoService.save(equipo);
            
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Baja",
                    "Se ha dado de baja con éxito un equipo de inspecciones", null);
            String descripcion = "Se ha eliminado el equipo inspecciones '" + equipo.getNombreEquipo() + "'.";
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                    SeccionesEnum.INSPECCION.name());
            
        } catch (Exception e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al eliminar el equipo, inténtelo de nuevo más tarde", null);
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
    }
    
    /**
     * Pasa los datos del equipo que queremos modificar al formulario de modificación para que cambien los valores que
     * quieran
     * 
     * @author EZENTIS
     * @param equipo recuperado del formulario de búsqueda de equipos
     * @return vista modificarEquipo
     */
    public String getFormModificarEquipo(Equipo equipo) {
        this.miembrosSeleccionados = new ArrayList<>();
        List<Miembro> miembrosEquipo = miembroService.findByEquipo(equipo);
        equipo.setMiembros(miembrosEquipo);
        this.equipo = equipo;
        return "/equipos/modificarEquipo?faces-redirect=true";
    }
    
    /**
     * Elimina un miembro de un equipo, ya sea componente o colaborador del equipo que está siendo modificado
     * @author EZENTIS
     * @param miembro seleccionado en la tabla de integrantes de un equipo
     */
    public void eliminarMiembro(Miembro miembro) {
        try {
            // TODO historico de miembros
            List<Miembro> listaMiembros = equipo.getMiembros();
            listaMiembros.remove(miembro);
            equipo.setMiembros(listaMiembros);
            
            equipoService.save(equipo);
            
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Eliminación",
                    "Se ha eliminado con éxito el componente o colaborador del equipo", null);
            String descripcion = "Se ha eliminado un componente o colaborador del equipo inspecciones '"
                    + equipo.getNombreEquipo() + "'. Nombre del componente o colaborador del equipo: "
                    + miembro.getNombreCompleto();
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                    SeccionesEnum.INSPECCION.name());
            notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.INSPECCION.name(), equipo);
            
        } catch (Exception e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al eliminar un componente o colaborador del equipo de inspecciones, inténtelo de nuevo más tarde",
                    null);
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
    }
    
    /**
     * Carga el formulario para cambiar el jefe del equipo que está siendo modificado.
     * 
     * @author EZENTIS
     * @return vista cambiarJefeEquipo
     */
    public String getFormCambiarJefeEquipo() {
        this.jefeSeleccionado = null;
        listaUsuarios = userService.buscarNoJefeNoMiembroEquipo(equipo);
        return "/equipos/cambiarJefeEquipo?faces-redirect=true";
    }
    
    /**
     * Elimina el jefe anterior y agrega el nuevo seleccionado en el formulario cambiarJefeEquipo al equipo que está
     * siendo modificado.
     * 
     * @author EZENTIS
     */
    public void cambiarJefeEquipo() {
        
        try {
            if (jefeSeleccionado == null) {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                        "Debe seleccionar un nuevo jefe de equipo", "", null);
            } else {
                List<Miembro> listaMiembros = equipo.getMiembros();
                listaMiembros.removeIf(m -> RolEquipoEnum.JEFE_EQUIPO.equals(m.getPosicion()));
                
                equipo.setJefeEquipo(jefeSeleccionado.getUsername());
                equipo.setNombreJefe(jefeSeleccionado.getNombre() + " " + jefeSeleccionado.getApellido1() + " "
                        + jefeSeleccionado.getApellido2());
                Miembro jefe = crearMiembro(RolEquipoEnum.JEFE_EQUIPO, jefeSeleccionado);
                listaMiembros.add(jefe);
                
                equipo.setMiembros(listaMiembros);
                
                equipoService.save(equipo);
                
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                        "jefe cambiado con éxito");
                String descripcion = "Se ha cambiado el jefe del equipo inspecciones '" + equipo.getNombreEquipo()
                        + "'. Nombre del nuevo jefe: " + equipo.getNombreJefe();
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                        SeccionesEnum.INSPECCION.name());
                notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.INSPECCION.name(), equipo);
            }
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al cambiar el jefe del equipo, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
    }
    
    /**
     * Carga el formulario para añadir un miembro al equipo que está siendo modificado.
     * 
     * @author EZENTIS
     * @return vista anadirMiembroEquipo
     */
    public String getFormAniadirMiembroEquipo() {
        this.miembrosSeleccionados = new ArrayList<>();
        listaUsuarios = userService.buscarNoJefeNoMiembroEquipo(equipo);
        return "/equipos/anadirMiembroEquipo?faces-redirect=true";
    }
    
    /**
     * Añade los usuarios seleccionados en el formulario aniadirMiembroEquipo al equipo que está siendo modificado.
     * 
     * @author EZENTIS
     * @param posicion rol que ocupa en el equipo (componente o colaborador)
     */
    public void guardarMiembros(RolEquipoEnum posicion) {
        
        try {
            List<Miembro> listaMiembros = equipo.getMiembros();
            String nombresCompletos = aniadirMiembrosEquipo(posicion, listaMiembros);
            equipo.setMiembros(listaMiembros);
            
            equipoService.save(equipo);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                    "componente/s o colaborador/es añadido/s con éxito");
            String descripcion = "Se ha añadido nuevos componentes o colaboradores al equipo inspecciones '"
                    + equipo.getNombreEquipo() + "'. Nombres de componentes " + nombresCompletos;
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.INSPECCION.name());
            notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.INSPECCION.name(), equipo);
            
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al añadir nuevos componentes o colaboradores al equipo, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
    }
    
    /**
     * Crear un miembro a partir de los datos de un usuario
     * 
     * @author EZENTIS
     * @param posicion
     * @param user
     * @return miembro nuevo
     */
    private Miembro crearMiembro(RolEquipoEnum posicion, User user) {
        Miembro miembroNuevo;
        miembroNuevo = new Miembro();
        miembroNuevo.setEquipo(equipo);
        miembroNuevo.setNombreCompleto(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
        miembroNuevo.setUsername(user.getUsername());
        miembroNuevo.setPosicion(posicion);
        return miembroNuevo;
    }
    
    /**
     * Añadir miembros con un rol específico a una lista a partir de los usuarios seleccionados en un formulario
     * 
     * @author EZENTIS
     * @param posicion rol que ocupará en el equipo (componente o colaborador)
     * @param miembros lista de miembros donde se van a instertar
     * @return nombresCompletos nombre de los nuevos usuarios para generar un registro de actividad
     */
    private String aniadirMiembrosEquipo(RolEquipoEnum posicion, List<Miembro> miembros) {
        List<String> nombresCompletos = new ArrayList<>();
        for (User user : miembrosSeleccionados) {
            Miembro miembroNuevo = crearMiembro(posicion, user);
            miembros.add(miembroNuevo);
            nombresCompletos.add(miembroNuevo.getNombreCompleto());
        }
        return String.join(", ", nombresCompletos);
    }
    
    /**
     * Controla las columnas visibles en la lista de resultados del buscador
     * 
     * @author EZENTIS
     * @param e checkbox de la columna seleccionada
     */
    public void onToggle(ToggleEvent e) {
        columnasVisibles.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
    
    /**
     * Gestiona la transición entre pestañas en el formulario de alta de equipos. Se obliga a elegir un jefe de equipo y
     * controla que se escoja al menos un miembro o se cornfirme que no se desea ninguno para este equipo antes de pasar
     * a una pestaña posterior.
     * 
     * @author EZENTIS
     * @param event info de la pestaña actual y la siguente que se solicita
     * @return nombre de la siguente pestaña a mostrar
     */
    public String onFlowProcess(FlowEvent event) {
        if (JEFEEQUIPO.equals(event.getOldStep()) && MIEMBROS.equals(event.getNewStep())) {
            if (jefeSeleccionado != null) {
                
                listadoPotencialesMiembros.remove(jefeSeleccionado);
                miembrosSeleccionados.removeIf(m -> jefeSeleccionado.equals(m));
                
            } else {
                
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Debe elegir un jefe de equipo", "",
                        "");
                
                return event.getOldStep();
            }
        } else if (MIEMBROS.equals(event.getOldStep()) && "confirm".equals(event.getNewStep())
                && miembrosSeleccionados.isEmpty() && skip == Boolean.FALSE) {
            
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "Debe elegir uno o más componentes o confirmar que no desea ninguno aparte del jefe", "", "");
            
            return event.getOldStep();
            
        } else if (MIEMBROS.equals(event.getOldStep()) && JEFEEQUIPO.equals(event.getNewStep())) {
            
            listadoPotencialesMiembros.add(jefeSeleccionado);
            
        }
        return event.getNewStep();
    }
    
    /**
     * PostConstruct, inicializa el bean
     * 
     * @author EZENTIS
     */
    @PostConstruct
    public void init() {
        // para que en el select cargue por defecto la opción "Seleccione uno..."
        setEstado(null);
        setEquipo(null);
        setEquipoBusqueda(new EquipoBusqueda());
        setColumnasVisibles(new ArrayList<>());
        for (int i = 0; i <= NUMEROCOLUMNASLISTADOEQUIPOS; i++) {
            columnasVisibles.add(Boolean.TRUE);
        }
        model = new LazyModelEquipos(equipoService);
    }
    
}
