package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ITipoEquipoService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Controller("equiposBean")
@Scope("session")
public class EquiposBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private static final String JEFEEQUIPO = "jefeEquipo";
    
    private static final String MIEMBROS = "miembros";
    
    private String vieneDe;
    
    private Equipo equipo;
    
    private transient List<Miembro> miembrosEquipo;
    
    private User jefeSeleccionado;
    
    private List<User> miembrosSeleccionados;
    
    private List<Boolean> columnasVisibles;
    
    private String estado = null;
    
    private List<User> listaUsuarios;
    
    private List<User> listadoPotencialesJefes = new ArrayList<>();
    
    private List<User> listadoPotencialesMiembros = new ArrayList<>();
    
    private int numeroColumnasListadoEquipos = 5;
    
    private EquipoBusqueda equipoBusqueda;
    
    private boolean skip;
    
    private TipoEquipo tipoEquipo;
    
    private transient Iterable<TipoEquipo> tiposEquipo;
    
    @Autowired
    transient ITipoEquipoService tipoEquipoService;
    
    @Autowired
    transient IEquipoService equipoService;
    
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
        this.jefeSeleccionado = null;
        this.miembrosSeleccionados = new ArrayList<>();
        this.listadoPotencialesMiembros = null;
        this.equipo = null;
        this.tipoEquipo = null;
        equipo = new Equipo();
        listaUsuarios = userService.buscarNoJefeNoMiembroEquipo(null);
        listadoPotencialesJefes = listaUsuarios;
        skip = false;
        return "/equipos/altaEquipo?faces-redirect=true";
    }
    
    /**
     * Recoge los valores introducidos en el formulario y da de alta un equipo normal en la BBDD
     * 
     * @author EZENTIS
     */
    public void altaEquipo() {
        
        equipo.setJefeEquipo(jefeSeleccionado.getUsername());
        equipo.setNombreJefe(jefeSeleccionado.getNombre() + " " + jefeSeleccionado.getApellido1() + " "
                + jefeSeleccionado.getApellido2());
        equipo.setTipoEquipo(tipoEquipo);
        
        List<Miembro> miembrosNuevoEquipo = new ArrayList<>();
        Miembro jefe = crearMiembro(RolEquipoEnum.JEFE_EQUIPO, jefeSeleccionado);
        miembrosNuevoEquipo.add(jefe);
        equipo.setMiembros(miembrosNuevoEquipo);
        
        try {
            if (equipoService.save(equipo) != null) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                        "El equipo ha sido creado con éxito");
            }
            
        } catch (Exception e) {
            // Guardamos los posibles errores en bbdd
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
        
    }
    
    /**
     * Devuelve al formulario de búsqueda de equipos a su estado inicial y borra los resultados de búsquedas anteriores
     * si se navega desde el menú u otra sección.
     * 
     * @author EZENTIS
     * @return vista equipos
     */
    public void getFormularioBusquedaEquipos() {
        if ("menu".equalsIgnoreCase(this.vieneDe)) {
            limpiarBusqueda();
            this.vieneDe = null;
            tiposEquipo = tipoEquipoService.findAll();
        }
        
    }
    
    public void limpiarBusqueda() {
        equipoBusqueda.resetValues();
        this.estado = null;
    }
    
    /**
     * Busca equipos en base a los campos rellenados en el formulario tanto de equipo como de sus miembros.
     * 
     * @author EZENTIS
     */
    public void buscarEquipo() {
        
        List<Equipo> listaEquipos = equipoService.buscarEquipoCriteria(equipoBusqueda);
        equipoBusqueda.setListaEquipos(listaEquipos);
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
        ;
        miembrosEquipo = new ArrayList<>();
        miembrosEquipo = equipoService.findByEquipo(equipo);
        equipo.setMiembros(miembrosEquipo);
        this.equipo = equipo;
        return "/equipos/modificarEquipo?faces-redirect=true";
    }
    
    /**
     * Modifica los datos de un equipo en función de los valores recuperados del formulario
     */
    public void modificarEquipo() {
        try {
            if (equipoService.save(equipo) != null) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                        "El equipo ha sido modificado con éxito");
                String descripcion = "Se ha modificado el equipo inspecciones '" + equipo.getNombreEquipo() + "'.";
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                        SeccionesEnum.INSPECCION.name());
                notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.INSPECCION.name(), equipo);
            }
        } catch (Exception e) {
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.name(), e);
        }
        
    }
    
    /**
     * Elimina un miembro de un equipo, ya sea componente o colaborador del equipo que está siendo modificado
     * @author EZENTIS
     * @param miembro
     */
    public void eliminarMiembro(Miembro miembro) {
        try {
            // TODO historico de miembros
            List<Miembro> listaMiembros = equipo.getMiembros();
            listaMiembros.remove(miembro);
            equipo.setMiembros(listaMiembros);
            if (equipoService.save(equipo) != null) {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Eliminación",
                        "El equipo ha eliminado con éxito el componente o colaborador del equipo", null);
                String descripcion = "Se ha eliminado un componente o colaborador del equipo inspecciones '"
                        + equipo.getNombreEquipo() + "'. Nombre del componente o colaborador del equipo: "
                        + miembro.getNombreCompleto();
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                        SeccionesEnum.INSPECCION.name());
                notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.INSPECCION.name(), equipo);
            }
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
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Modificación",
                        "Debe seleccionar un nuevo jefe de equipo", null);
            } else {
                List<Miembro> listaMiembros = equipo.getMiembros();
                listaMiembros.removeIf(m -> RolEquipoEnum.JEFE_EQUIPO.equals(m.getPosicion()));
                
                equipo.setJefeEquipo(jefeSeleccionado.getUsername());
                equipo.setNombreJefe(jefeSeleccionado.getNombre() + " " + jefeSeleccionado.getApellido1() + " "
                        + jefeSeleccionado.getApellido2());
                Miembro jefe = crearMiembro(RolEquipoEnum.JEFE_EQUIPO, jefeSeleccionado);
                listaMiembros.add(jefe);
                
                equipo.setMiembros(listaMiembros);
                if (equipoService.save(equipo) != null && !listaMiembros.isEmpty()) {
                    FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                            "jefe cambiado con éxito");
                    String descripcion = "Se ha cambiado el jefe del equipo inspecciones '" + equipo.getNombreEquipo()
                            + "'. Nombre del nuevo jefe: " + equipo.getNombreJefe();
                    // Guardamos la actividad en bbdd
                    regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                            SeccionesEnum.INSPECCION.name());
                    notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.INSPECCION.name(), equipo);
                }
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
     * @param miembro
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
            if (equipoService.save(equipo) != null && !listaMiembros.isEmpty()) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                        "componente/s o colaborador/es añadido/s con éxito");
                String descripcion = "Se ha añadido nuevos componentes o colaboradores al equipo inspecciones '"
                        + equipo.getNombreEquipo() + "'. Nombres de componentes " + nombresCompletos;
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                        SeccionesEnum.INSPECCION.name());
                notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.INSPECCION.name(), equipo);
                
            }
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
     * Gestiona la transición entre pestañas en el formulario de alta de equipos. Devuelve al estado inicial cada
     * pestaña del formulario en caso de volver a un paso anterior. Se obliga a elegir un jefe de equipo y controla que
     * se escoja al menos un miembro o se cornfirme que no se desea ninguno para este equipo antes de pasar a una
     * pestaña posterior.
     * 
     * @author EZENTIS
     * @param event info de la pestaña actual y la siguente que se solicita
     * @return nombre de la siguente pestaña a mostrar
     */
    public String onFlowProcess(FlowEvent event) {
        if (JEFEEQUIPO.equals(event.getOldStep()) && MIEMBROS.equals(event.getNewStep())) {
            if (jefeSeleccionado != null) {
                listadoPotencialesJefes.remove(jefeSeleccionado);
                listadoPotencialesMiembros = listadoPotencialesJefes;
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Debe elegir un jefe de equipo", "",
                        "");
                return event.getOldStep();
            }
        }
        if (MIEMBROS.equals(event.getOldStep()) && "confirm".equals(event.getNewStep())
                && miembrosSeleccionados.isEmpty()) {
            if (skip) {
                skip = false;
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                        "Debe elegir uno o más componentes o confirmar que no desea ninguno aparte del jefe", "", "");
                return event.getOldStep();
            }
        }
        if ("confirm".equals(event.getOldStep()) && MIEMBROS.equals(event.getNewStep())) {
            this.miembrosSeleccionados = null;
            listadoPotencialesJefes = listaUsuarios;
        }
        if (MIEMBROS.equals(event.getOldStep()) && JEFEEQUIPO.equals(event.getNewStep())) {
            listadoPotencialesJefes.add(jefeSeleccionado);
            this.jefeSeleccionado = null;
        }
        if (JEFEEQUIPO.equals(event.getOldStep()) && "general".equals(event.getNewStep())) {
            this.equipo.setNombreEquipo("");
            this.tipoEquipo = null;
            this.jefeSeleccionado = null;
            this.miembrosSeleccionados = null;
        }
        return event.getNewStep();
    }
    
    @PostConstruct
    public void init() {
        // para que en el select cargue por defecto la opción "Seleccione uno..."
        estado = null;
        this.equipo = null;
        equipoBusqueda = new EquipoBusqueda();
        equipoBusqueda.resetValues();
        columnasVisibles = new ArrayList<>();
        for (int i = 0; i <= numeroColumnasListadoEquipos; i++) {
            columnasVisibles.add(Boolean.TRUE);
        }
    }
    
}
