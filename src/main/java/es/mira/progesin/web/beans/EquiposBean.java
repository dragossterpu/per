package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembros;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("equiposBean")
@RequestScoped
@ViewScoped
public class EquiposBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Equipo equipo;

	private Date fechaDesde;

	private Date fechaHasta;

	private String nombreJefe;

	private String username;

	private String nombreMiembro;

	private transient List<Miembros> listMiembros;

	private String nombreEquipo;

	private User jefeSelecionado;

	private List<User> miembrosSeleccionados;

	private List<User> colaboradoresSelecionados;

	private List<Boolean> list;

	private List<User> miembros;

	private List<Equipo> listaEquipos;

	private String estado = null;

	private Boolean equipoEspecial;

	private Miembros miembro = new Miembros();

	private List<User> listadoJefes = new ArrayList<>();

	private List<User> listadoMiembros = new ArrayList<>();

	private List<User> listadoColaboradores;

	private int numeroColumnasListadoEquipos = 5;

	private EquipoBusqueda equipoBusqueda;

	private boolean skip;

	private String tipoEquipo;

	RegistroActividad regActividad = new RegistroActividad();

	private List<User> listaUsuarios;

	private static final String NOMBRESECCION = "Equipos de inspecciones";

	private static final String VISTAEQUIPOS = "/equipos/equipos";

	private static final String JEFEEQUIPO = "jefeEquipo";

	private static final String MIEMBROS = "miembros";

	private String vieneDe;

	@Autowired
	transient IEquipoService equipoService;

	@Autowired
	transient IUserService userService;

	@Autowired
	transient IRegistroActividadService regActividadService;

	@Autowired
	transient INotificacionService notificacionService;

	/**
	 * Método que nos lleva al formulario de alta de nuevos equipos, inicializando todo lo necesario para mostrar
	 * correctamente la página. Se llama desde la página de búsqueda de equipos.
	 * @return
	 */
	public String nuevoEquipo() {
		this.jefeSelecionado = null;
		this.miembrosSeleccionados = null;
		this.listadoMiembros = null;
		this.equipo = null;
		this.tipoEquipo = null;
		equipo = new Equipo();
		jefeSelecionado = new User();
		miembrosSeleccionados = new ArrayList<User>();
		equipo.setFechaAlta(new Date());
		if (equipoEspecial) {
			equipo.setEquipoEspecial("SI");
		}
		else {
			equipo.setEquipoEspecial("NO");
		}
		equipo.setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());
		listaUsuarios = userService.findByfechaBajaIsNullAndRoleNotIn(RoleEnum.getRolesProv());
		listadoJefes = listaUsuarios;
		return "/equipos/altaEquipo";
	}

	/**
	 * Método que recoge los valores introducidos en el formulario y da de alta un equipo normal en la BBDD
	 * @return
	 */
	public String altaEquipo() {

		equipo.setJefeEquipo(jefeSelecionado.getUsername());
		equipo.setNombreJefe(jefeSelecionado.getNombre() + " " + jefeSelecionado.getApellido1() + " "
				+ jefeSelecionado.getApellido2());
		// equipo.setNombreEquipo(jefeSelecionado.getNombre() + " " + jefeSelecionado.getApellido1());
		equipo.setTipoEquipo(tipoEquipo);
		try {
			if (equipoService.save(equipo) != null) {
				RequestContext context = RequestContext.getCurrentInstance();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta",
						"El equipo ha sido creado con éxito");
				FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
				context.execute("PF('dialogMessage').show()");
			}
			// alta en la tabla miembros de un equipo
			altaJefeEquipo();
			altaMiembrosEquipo();
			String descripcion = "Alta nuevo equipo inspecciones. Nombre jefe equipo " + jefeSelecionado.getNombre()
					+ " " + jefeSelecionado.getApellido1() + " " + jefeSelecionado.getApellido2();
			// Guardamos la actividad en bbdd
			regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.ALTA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());

			// Guardamos la notificacion en bbdd
			notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
		}
		catch (Exception e) {
			// Guardamos loe posibles errores en bbdd
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}

		this.equipoEspecial = false;
		return VISTAEQUIPOS;
	}

	public void getFormularioBusquedaEquipos() {
		if ("menu".equalsIgnoreCase(this.vieneDe)) {
			equipoBusqueda.resetValues();
			this.estado = null;
			this.vieneDe = null;
		}

	}

	public String buscarEquipo() {

		List<Equipo> listaEquipos = equipoService.buscarEquipoCriteria(equipoBusqueda);
		for (Equipo equipo : listaEquipos) {
			User user = userService.findOne(equipo.getJefeEquipo());
			equipo.setNombreJefe(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
		}
		equipoBusqueda.setListaEquipos(listaEquipos);
		return VISTAEQUIPOS;
	}

	public String limpiarValores() {
		this.equipoEspecial = false;
		// limpiamos los datos del formulario
		this.miembrosSeleccionados = null;
		return null;
	}

	public String eliminarEquipo(Equipo equipo) {
		equipo.setFechaBaja(new Date());
		equipo.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			equipoService.save(equipo);
			equipoBusqueda.getListaEquipos().remove(equipo);
			String descripcion = "Se ha eliminado el equipo inspecciones. Nombre jefe equipo " + equipo.getNombreJefe();
			// Guardamos la actividad en bbdd
			regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.BAJA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());
			// Guardamos la notificacion en bbdd
			notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
		}
		catch (Exception e) {
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}

		return VISTAEQUIPOS;
	}

	/**
	 * Pasa los datos del equipo que queremos modificar al formulario de modificación para que cambien los valores que
	 * quieran
	 * @param equipo recuperado del formulario de búsqueda de equipos
	 * @return
	 */
	public String getFormModificarEquipo(Equipo equipo) {
		this.miembrosSeleccionados = null;
		this.listadoColaboradores = null;
		listMiembros = new ArrayList<>();
		jefeSelecionado = userService.findOne(equipo.getJefeEquipo());
		listMiembros = equipoService.findByIdEquipo(equipo.getIdEquipo());
		equipo.setJefeEquipo(jefeSelecionado.getUsername());
		equipo.setNombreJefe(jefeSelecionado.getNombre() + " " + jefeSelecionado.getApellido1() + " "
				+ jefeSelecionado.getApellido2());
		this.equipo = equipo;
		return "/equipos/modificarEquipo";
	}

	/**
	 * Modifica los datos de un equipo en función de los valores recuperados del formulario
	 */
	public void modificarEquipo() {
		this.listadoColaboradores = null;
		try {
			if (equipoService.save(equipo) != null) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
						"El equipo ha sido modificado con éxito");
			}
			String descripcion = "Se ha modificado el equipo inspecciones. Nombre jefe equipo "
					+ equipo.getNombreJefe();
			// Guardamos la actividad en bbdd
			regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());
			// Guardamos la notificacion en bbdd
			notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
		}
		catch (Exception e) {
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}

	}

	public String borrarMiembro(Equipo equipo) {
		return VISTAEQUIPOS;
	}

	public String aniadirColaborador() {
		listaUsuarios = userService.findByfechaBajaIsNullAndRoleNotIn(RoleEnum.getRolesProv());
		return "/equipos/anadirColaboradorEquipo";
	}

	public String borrarMiembro() {
		listaUsuarios = userService.findByfechaBajaIsNullAndRoleNotIn(RoleEnum.getRolesProv());
		return "/equipos/anadirColaboradorEquipo";
	}

	public String aniadirMiembro() {
		this.miembrosSeleccionados = null;
		listaUsuarios = userService.findByfechaBajaIsNullAndRoleNotIn(RoleEnum.getRolesProv());
		return "/equipos/anadirMiembroEquipo";
	}

	public String guardarColaborador() {

		for (User user : colaboradoresSelecionados) {
			Miembros miembro = new Miembros();
			miembro.setIdEquipo(equipo.getIdEquipo());
			miembro.setNombreCompleto(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
			miembro.setUsername(user.getUsername());
			miembro.setPosicion(RolEquipoEnum.COLABORADOR); 
			try {
				equipoService.save(miembro);
				String descripcion = "Se ha añadido un nuevo colaborador al equipo inspecciones. Nombre colaborador "
						+ user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2();
				// Guardamos la actividad en bbdd
				regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						SecurityContextHolder.getContext().getAuthentication().getName());
				// Guardamos la notificacion en bbdd
				notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
			}
			catch (Exception e) {
				regActividadService.altaRegActividadError(NOMBRESECCION, e);
			}

		}
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta",
				"Colaborador/es añadido/s con éxito");
		FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
		context.execute("PF('dialogMessage').show()");
		return VISTAEQUIPOS;
	}

	public String guardarMiembro() {

		for (User user : miembrosSeleccionados) {
			Miembros miembro = new Miembros();
			miembro.setIdEquipo(equipo.getIdEquipo());
			miembro.setNombreCompleto(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
			miembro.setUsername(user.getUsername());
			miembro.setPosicion(RolEquipoEnum.MIEMBRO);
			try {
				equipoService.save(miembro);
				String descripcion = "Se ha añadido un nuevo componente al equipo inspecciones. Nombre componente "
						+ user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2();
				// Guardamos la actividad en bbdd
				regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						SecurityContextHolder.getContext().getAuthentication().getName());
				// Guardamos la notificacion en bbdd
				notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
			}
			catch (Exception e) {
				regActividadService.altaRegActividadError(NOMBRESECCION, e);
			}

		}
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta", "componente/s añadido/s con éxito");
		FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
		context.execute("PF('dialogMessage').show()");
		return VISTAEQUIPOS;
	}

	public void onToggle(ToggleEvent e) {
		list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	/**
	 * 
	 */
	private void altaJefeEquipo() {
		Miembros miembro = new Miembros();
		miembro.setIdEquipo(equipo.getIdEquipo());
		miembro.setNombreCompleto(jefeSelecionado.getNombre() + " " + jefeSelecionado.getApellido1() + " "
				+ jefeSelecionado.getApellido2());
		miembro.setUsername(jefeSelecionado.getUsername());
		miembro.setPosicion(RolEquipoEnum.JEFE_EQUIPO);
		equipoService.save(miembro);
	}

	/**
	 * 
	 */
	private void altaMiembrosEquipo() {
		for (User user : miembrosSeleccionados) {
			Miembros miembro2 = new Miembros();
			miembro2.setNombreCompleto(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
			miembro2.setIdEquipo(equipo.getIdEquipo());
			miembro2.setUsername(user.getUsername());
			miembro2.setPosicion(RolEquipoEnum.MIEMBRO);
			equipoService.save(miembro2);
		}
	}

	public String onFlowProcess(FlowEvent event) {
		cleanParam(event);
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		}
		else {
			return event.getNewStep();
		}
	}

	/**
	 * @param event
	 */
	private void cleanParam(FlowEvent event) {
		if (JEFEEQUIPO.equals(event.getOldStep()) && MIEMBROS.equals(event.getNewStep())) {
			User jefe = jefeSelecionado;
			listadoJefes.remove(jefe);
			listadoMiembros = listadoJefes;
		}
		if ("confirm".equals(event.getOldStep()) && MIEMBROS.equals(event.getNewStep())) {
			this.miembrosSeleccionados = null;
			listadoJefes = listaUsuarios;
		}
		if (MIEMBROS.equals(event.getOldStep()) && JEFEEQUIPO.equals(event.getNewStep())) {
			listadoJefes.add(jefeSelecionado);
			this.jefeSelecionado = null;
		}
		if (JEFEEQUIPO.equals(event.getOldStep()) && "general".equals(event.getNewStep())) {
			this.equipo.setNombreEquipo("");
			this.tipoEquipo = null;
			this.jefeSelecionado = null;
			this.miembrosSeleccionados = null;
		}
	}

	public void save() {
		FacesMessage msg = new FacesMessage("Successful", "Welcome :" + equipo.getIdEquipo());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	@PostConstruct
	public void init() {
		// para que en el select cargue por defecto la opción "Seleccione uno..."
		estado = null;
		this.equipo = null;
		this.equipoEspecial = false;
		equipoBusqueda = new EquipoBusqueda();
		equipoBusqueda.resetValues();
		list = new ArrayList<>();
		for (int i = 0; i <= numeroColumnasListadoEquipos; i++) {
			list.add(Boolean.TRUE);
		}
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

}
