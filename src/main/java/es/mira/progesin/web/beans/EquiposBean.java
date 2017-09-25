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
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.lazydata.LazyModelEquipos;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IInspeccionesService;
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
    
    /**
     * Constante con el literal "jefeEquipo".
     */
    private static final String JEFEEQUIPO = "jefeEquipo";
    
    /**
     * Constante con el literal "miembros".
     */
    private static final String MIEMBROS = "miembros";
    
    /**
     * Número de columnas en el listado.
     */
    private static final int NUMEROCOLUMNASLISTADOEQUIPOS = 4;
    
    /**
     * Equipo.
     */
    private Equipo equipo;
    
    /**
     * Jefe seleccionado en la vista.
     */
    private User jefeSeleccionado;
    
    /**
     * Lista de miembros seleccionados.
     */
    private List<User> miembrosSeleccionados;
    
    /**
     * Listado de booleanos para llevar el control de las columnas que son visibles en la vista.
     */
    private List<Boolean> columnasVisibles;
    
    /**
     * Estado del equipo.
     */
    private String estado;
    
    /**
     * Lista de usuarios.
     */
    private List<User> listaUsuarios;
    
    /**
     * Lista de jefes potenciales.
     */
    private List<User> listadoPotencialesJefes;
    
    /**
     * Listado de miembros potenciales.
     */
    private List<User> listadoPotencialesMiembros;
    
    /**
     * Objeto que contiene los parámetros de búsqueda.
     */
    private EquipoBusqueda equipoBusqueda;
    
    /**
     * Variable que controla que se salte o no un paso.
     */
    private boolean skip;
    
    /**
     * Tipo de equipo.
     */
    private TipoEquipo tipoEquipo;
    
    /**
     * LazyModel para la visualización paginada de datos en la vista.
     */
    private LazyModelEquipos model;
    
    /**
     * Servicio de tipos de equipo.
     */
    @Autowired
    private transient ITipoEquipoService tipoEquipoService;
    
    /**
     * Servicio de equipos.
     */
    @Autowired
    private transient IEquipoService equipoService;
    
    /**
     * Servicio de miembros.
     */
    @Autowired
    private transient IMiembroService miembroService;
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private transient IUserService userService;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Servicio de notificaciones.
     */
    @Autowired
    transient INotificacionService notificacionService;
    
    /**
     * Servicio de Inspecciones.
     */
    @Autowired
    transient IInspeccionesService inspeccionesService;
    
    /**
     * Muestra formulario de alta de nuevos equipos, inicializando lo necesario para mostrar correctamente la página. Se
     * llama desde la página de búsqueda de equipos.
     * 
     * @return vista altaEquipo
     */
    public String getFormAltaEquipo() {
        this.setJefeSeleccionado(null);
        this.setMiembrosSeleccionados(new ArrayList<>());
        this.setListadoPotencialesJefes(new ArrayList<>());
        this.setListadoPotencialesMiembros(new ArrayList<>());
        this.setEquipo(new Equipo());
        this.setTipoEquipo(null);
        listaUsuarios = userService.buscarNoMiembroEquipoNoJefe(null);
        listadoPotencialesJefes.addAll(userService.buscarUserSinEquipo());
        listadoPotencialesMiembros.addAll(listaUsuarios);
        skip = false;
        return "/equipos/altaEquipo?faces-redirect=true";
    }
    
    /**
     * Recoge los valores introducidos en el formulario y da de alta un equipo normal en la BBDD.
     */
    public void altaEquipo() {
        
        try {
            equipo.setJefeEquipo(jefeSeleccionado);
            
            equipo.setTipoEquipo(tipoEquipo);
            
            List<Miembro> miembrosNuevoEquipo = new ArrayList<>();
            Miembro jefe = crearMiembro(RolEquipoEnum.JEFE_EQUIPO, jefeSeleccionado);
            miembrosNuevoEquipo.add(jefe);
            
            String nombresCompletos = aniadirMiembrosEquipo(RolEquipoEnum.MIEMBRO, miembrosNuevoEquipo);
            equipo.setMiembros(miembrosNuevoEquipo);
            
            equipoService.save(equipo);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                    "El equipo ha sido creado con éxito");
            
            StringBuilder descripcion = new StringBuilder().append("Se ha creado un nuevo equipo de inspecciones ");
            descripcion.append(equipo.getNombreEquipo());
            descripcion.append("\n\n");
            descripcion.append("Jefe de equipo: ");
            descripcion.append(jefe.getUsuario().getNombreCompleto());
            descripcion.append("\n\n");
            descripcion.append("Nombre de componentes: ");
            descripcion.append(nombresCompletos);
            
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion.toString(), TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.INSPECCION.getDescripcion());
            notificacionService.crearNotificacionEquipo(descripcion.toString(),
                    SeccionesEnum.INSPECCION.getDescripcion(), equipo);
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
                    "Se ha producido un error al dar de alta el equipo, inténtelo de nuevo más tarde");
            // Guardamos los posibles errores en bbdd
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), e);
        }
        
    }
    
    /**
     * Devuelve al formulario de búsqueda de equipos a su estado inicial y borra los resultados de búsquedas anteriores
     * si se navega desde el menú u otra sección.
     * @return ruta siguiente
     * 
     */
    public String getFormularioBusquedaEquipos() {
        limpiarBusqueda();
        return "/equipos/equipos?faces-redirect=true";
    }
    
    /**
     * Devuelve al formulario de búsqueda de equipos a su estado inicial y borra los resultados de búsquedas anteriores.
     * 
     */
    public void limpiarBusqueda() {
        equipoBusqueda = new EquipoBusqueda();
        setEstado(null);
        model.setRowCount(0);
    }
    
    /**
     * Busca equipos en base a los campos rellenados en el formulario tanto de equipo como de sus miembros.
     * 
     */
    public void buscarEquipo() {
        
        model.setBusqueda(equipoBusqueda);
        model.load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
    }
    
    /**
     * Elimina un equipo. Se invoca desde la lista de resultados del buscador.
     * 
     * @param eq recuperado del formulario de búsqueda de equipos
     */
    public void eliminarEquipo(Equipo eq) {
        try {
            if (inspeccionesService.existenInspeccionesNoFinalizadas(eq)) {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                        "No se puede eliminar este equipo. Tiene inspecciones sin finalizar.", null);
            } else {
                eq.setFechaBaja(new Date());
                eq.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
                
                equipoService.save(eq);
                
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Baja",
                        "Se ha dado de baja con éxito un equipo de inspecciones", null);
                String descripcion = "Se ha eliminado el equipo inspecciones '" + eq.getNombreEquipo() + "'.";
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                        SeccionesEnum.INSPECCION.getDescripcion());
                notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.INSPECCION.getDescripcion(),
                        equipo);
            }
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al eliminar el equipo, inténtelo de nuevo más tarde", null);
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), e);
        }
    }
    
    /**
     * Pasa los datos del equipo que queremos modificar al formulario de modificación para que cambien los valores que
     * quieran.
     * 
     * @param equip recuperado del formulario de búsqueda de equipos
     * @return vista modificarEquipo
     */
    public String getFormModificarEquipo(Equipo equip) {
        
        Equipo equipoAux = equipoService.findOne(equip.getId());
        String redireccion = null;
        if (equipoAux != null) {
            this.miembrosSeleccionados = new ArrayList<>();
            List<Miembro> miembrosEquipo = miembroService.findByEquipo(equipoAux);
            equipoAux.setMiembros(miembrosEquipo);
            this.equipo = equipoAux;
            redireccion = "/equipos/modificarEquipo?faces-redirect=true";
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Modificar equipo",
                    "Se ha producido un error al acceder al equipo. El equipo no existe");
        }
        return redireccion;
    }
    
    /**
     * Elimina un miembro de un equipo, ya sea componente o colaborador del equipo que está siendo modificado.
     * 
     * @param miembro seleccionado en la tabla de integrantes de un equipo
     */
    public void eliminarMiembro(Miembro miembro) {
        try {
            List<Miembro> listaMiembros = equipo.getMiembros();
            listaMiembros.remove(miembro);
            equipo.setMiembros(listaMiembros);
            
            equipoService.save(equipo);
            
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Eliminación",
                    "Se ha eliminado con éxito el componente o colaborador del equipo", null);
            String descripcion = "Se ha eliminado un componente o colaborador del equipo inspecciones '"
                    + equipo.getNombreEquipo() + "'. Nombre del componente o colaborador del equipo: "
                    + miembro.getUsuario().getNombreCompleto();
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                    SeccionesEnum.INSPECCION.getDescripcion());
            notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.INSPECCION.getDescripcion(), equipo);
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al eliminar un componente o colaborador del equipo de inspecciones, inténtelo de nuevo más tarde",
                    null);
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), e);
        }
    }
    
    /**
     * Carga el formulario para cambiar el jefe del equipo que está siendo modificado.
     *
     * @return vista cambiarJefeEquipo
     */
    public String getFormCambiarJefeEquipo() {
        this.jefeSeleccionado = null;
        listaUsuarios = userService.buscarUserSinEquipo();
        return "/equipos/cambiarJefeEquipo?faces-redirect=true";
    }
    
    /**
     * Elimina el jefe anterior y agrega el nuevo seleccionado en el formulario cambiarJefeEquipo al equipo que está
     * siendo modificado.
     *
     */
    public void cambiarJefeEquipo() {
        
        try {
            if (jefeSeleccionado == null) {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                        "Debe seleccionar un nuevo jefe de equipo", "", null);
            } else {
                List<Miembro> listaMiembros = equipo.getMiembros();
                listaMiembros.removeIf(m -> RolEquipoEnum.JEFE_EQUIPO.equals(m.getPosicion()));
                
                equipo.setJefeEquipo(jefeSeleccionado);
                
                Miembro jefe = crearMiembro(RolEquipoEnum.JEFE_EQUIPO, jefeSeleccionado);
                listaMiembros.add(jefe);
                
                equipo.setMiembros(listaMiembros);
                
                equipoService.save(equipo);
                
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                        "jefe cambiado con éxito");
                String descripcion = "Se ha cambiado el jefe del equipo inspecciones '" + equipo.getNombreEquipo()
                        + "'. Nombre del nuevo jefe: " + equipo.getJefeEquipo().getNombreCompleto();
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                        SeccionesEnum.INSPECCION.getDescripcion());
                notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.INSPECCION.getDescripcion(),
                        equipo);
            }
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al cambiar el jefe del equipo, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), e);
        }
    }
    
    /**
     * Carga el formulario para añadir un miembro al equipo que está siendo modificado.
     *
     * @return vista anadirMiembroEquipo
     */
    public String getFormAniadirMiembroEquipo() {
        this.miembrosSeleccionados = new ArrayList<>();
        listaUsuarios = userService.buscarNoMiembroEquipoNoJefe(equipo.getId());
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
                    SeccionesEnum.INSPECCION.getDescripcion());
            notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.INSPECCION.getDescripcion(), equipo);
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al añadir nuevos componentes o colaboradores al equipo, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), e);
        }
    }
    
    /**
     * Crear un miembro a partir de los datos de un usuario.
     * 
     * @param posicion Posición del usuario
     * @param user Usuario a partir de la que se crea el miembro
     * @return miembro nuevo
     */
    private Miembro crearMiembro(RolEquipoEnum posicion, User user) {
        Miembro miembroNuevo;
        miembroNuevo = new Miembro();
        miembroNuevo.setEquipo(equipo);
        miembroNuevo.setUsuario(user);
        miembroNuevo.setPosicion(posicion);
        return miembroNuevo;
    }
    
    /**
     * Añadir miembros con un rol específico a una lista a partir de los usuarios seleccionados en un formulario.
     * 
     * @param posicion Rol que ocupará en el equipo (componente o colaborador)
     * @param miembros Lista de miembros donde se van a insertar
     * @return nombresCompletos nombre de los nuevos usuarios para generar un registro de actividad
     */
    private String aniadirMiembrosEquipo(RolEquipoEnum posicion, List<Miembro> miembros) {
        List<String> nombresCompletos = new ArrayList<>();
        for (User user : miembrosSeleccionados) {
            Miembro miembroNuevo = crearMiembro(posicion, user);
            miembros.add(miembroNuevo);
            nombresCompletos.add(miembroNuevo.getUsuario().getNombreCompleto());
        }
        return String.join(", ", nombresCompletos);
    }
    
    /**
     * Controla las columnas visibles en la lista de resultados del buscador.
     * 
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
     * @param event info de la pestaña actual y la siguente que se solicita
     * @return nombre de la siguente pestaña a mostrar
     */
    public String onFlowProcess(FlowEvent event) {
        String siguientePaso = event.getNewStep();
        if (JEFEEQUIPO.equals(event.getOldStep()) && MIEMBROS.equals(event.getNewStep())) {
            if (jefeSeleccionado != null) {
                
                listadoPotencialesMiembros.remove(jefeSeleccionado);
                miembrosSeleccionados.removeIf(m -> m.equals(jefeSeleccionado));
                
            } else {
                
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Debe elegir un jefe de equipo", "",
                        "");
                
                siguientePaso = event.getOldStep();
            }
        } else if (confirmarSinMiembrosSinCheckear(event.getOldStep(), event.getNewStep())) {
            
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "Debe elegir uno o más componentes o confirmar que no desea ninguno aparte del jefe", "", "");
            
            siguientePaso = event.getOldStep();
            
        } else if (MIEMBROS.equals(event.getOldStep()) && JEFEEQUIPO.equals(event.getNewStep())) {
            
            listadoPotencialesMiembros.add(jefeSeleccionado);
            
        }
        return siguientePaso;
    }
    
    /**
     * Comprueba si intenta ir al último paso del flujo (confirmar) sin haber seleccionado ningún miembro y sin haber
     * chequeado la opción equipo sin componentes.
     * 
     * @param pasoAnterior paso anterior del flujo
     * @param pasoSiguiente paso siguiente del flujo
     * @return true o false
     */
    private boolean confirmarSinMiembrosSinCheckear(String pasoAnterior, String pasoSiguiente) {
        return MIEMBROS.equals(pasoAnterior) && "confirm".equals(pasoSiguiente) && miembrosSeleccionados.isEmpty()
                && skip == Boolean.FALSE;
    }
    
    /**
     * PostConstruct, inicializa el bean.
     * 
     */
    @PostConstruct
    public void init() {
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
