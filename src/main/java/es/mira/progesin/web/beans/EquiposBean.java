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

import org.apache.log4j.Logger;
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

	private static Logger LOG = Logger.getLogger(EquiposBean.class.getName());

	private Equipo equipo;

	private Date fechaDesde;

	private Date fechaHasta;

	private String nombreJefe;

	private String username;

	private String nombreMiembro;

	private List<Miembros> listMiembros;

	private String nombreEquipo;

	private User jefeSelecionado;

	private List<User> miembrosSelecionados;

	private List<User> colaboradoresSelecionados;

	private List<Boolean> list;

	private List<User> miembros;

	private List<Equipo> listaEquipos;

	private String estado = null;

	private Boolean equipoEspecial;

	Miembros miembro = new Miembros();

	List<User> listadoJefes = new ArrayList<User>();

	List<User> listadoMiembros = new ArrayList<User>();

	List<User> listadoColaboradores;

	private int numeroColumnasListadoEquipos = 5;

	private EquipoBusqueda equipoBusqueda;

	private boolean skip;

	private String tipoEquipo;

	RegistroActividad regActividad = new RegistroActividad();

	List<User> listaUsuarios;

	private final String NOMBRESECCION = "Equipos de inspecciones";

	@Autowired
	IEquipoService equipoService;

	@Autowired
	IUserService userService;

	@Autowired
	IRegistroActividadService regActividadService;

	@Autowired
	INotificacionService notificacionService;

	/**
	 * Método que nos lleva al formulario de alta de nuevos equipos, inicializando todo lo necesario para mostrar
	 * correctamente la página. Se llama desde la página de búsqueda de equipos.
	 * @return
	 */
	public String nuevoEquipo() {
		this.jefeSelecionado = null;
		this.miembrosSelecionados = null;
		this.listadoMiembros = null;
		this.equipo = null;
		this.tipoEquipo = null;
		equipo = new Equipo();
		jefeSelecionado = new User();
		miembrosSelecionados = new ArrayList<User>();
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
			saveReg(descripcion, EstadoRegActividadEnum.ALTA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());

			// Guardamos la notificacion en bbdd
			saveNotificacion(descripcion, EstadoRegActividadEnum.ALTA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());
		}
		catch (Exception e) {
			// Guardamos loe posibles errores en bbdd
			altaRegActivError(e);
		}

		this.equipoEspecial = false;
		return "/equipos/equipos";
	}

	public void getFormularioBusquedaEquipos() {
		equipoBusqueda.resetValues();
		this.estado = null;
	}

	public String buscarEquipo() {

		List<Equipo> listaEquipos = equipoService.buscarEquipoCriteria(equipoBusqueda);
		for (Equipo equipo : listaEquipos) {
			User user = userService.findOne(equipo.getJefeEquipo());
			equipo.setNombreJefe(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
		}
		equipoBusqueda.setListaEquipos(listaEquipos);
		return "/equipos/equipos";
	}

	public String limpiarValores() {
		this.equipoEspecial = false;
		// limpiamos los datos del formulario
		this.miembrosSelecionados = null;
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
			saveReg(descripcion, EstadoRegActividadEnum.BAJA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());
			// Guardamos la notificacion en bbdd
			saveNotificacion(descripcion, EstadoRegActividadEnum.BAJA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());
		}
		catch (Exception e) {
			altaRegActivError(e);
		}

		return "/equipos/equipos";
	}

	/**
	 * Pasa los datos del equipo que queremos modificar al formulario de modificación para que cambien los valores que
	 * quieran
	 * @param equipo recuperado del formulario de búsqueda de equipos
	 * @return
	 */
	public String getFormModificarEquipo(Equipo equipo) {
		this.miembrosSelecionados = null;
		this.listadoColaboradores = null;
		listMiembros = new ArrayList<Miembros>();
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
			saveReg(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());
			// Guardamos la notificacion en bbdd
			saveNotificacion(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());
		}
		catch (Exception e) {
			altaRegActivError(e);
		}

	}

	public String borrarMiembro(Equipo equipo) {
		return "/equipos/equipos";
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
		this.miembrosSelecionados = null;
		listaUsuarios = userService.findByfechaBajaIsNullAndRoleNotIn(RoleEnum.getRolesProv());
		return "/equipos/anadirMiembroEquipo";
	}

	public String guardarColaborador() {

		for (User user : colaboradoresSelecionados) {
			Miembros miembro = new Miembros();
			miembro.setIdEquipo(equipo.getIdEquipo());
			miembro.setNombreCompleto(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
			miembro.setUsername(user.getUsername());
			miembro.setPosicion("Colaborador");
			try {
				equipoService.save(miembro);
				String descripcion = "Se ha añadido un nuevo colaborador al equipo inspecciones. Nombre colaborador "
						+ user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2();
				// Guardamos la actividad en bbdd
				saveReg(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						SecurityContextHolder.getContext().getAuthentication().getName());
				// Guardamos la notificacion en bbdd
				saveNotificacion(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						SecurityContextHolder.getContext().getAuthentication().getName());
			}
			catch (Exception e) {
				altaRegActivError(e);
			}

		}
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta",
				"Colaborador/es añadido/s con éxito");
		FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
		context.execute("PF('dialogMessage').show()");
		return "/equipos/equipos";
	}

	public String guardarMiembro() {

		for (User user : miembrosSelecionados) {
			Miembros miembro = new Miembros();
			miembro.setIdEquipo(equipo.getIdEquipo());
			miembro.setNombreCompleto(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
			miembro.setUsername(user.getUsername());
			miembro.setPosicion("Miembro");
			try {
				equipoService.save(miembro);
				String descripcion = "Se ha añadido un nuevo miembro al equipo inspecciones. Nombre miembro "
						+ user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2();
				// Guardamos la actividad en bbdd
				saveReg(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						SecurityContextHolder.getContext().getAuthentication().getName());
				// Guardamos la notificacion en bbdd
				saveNotificacion(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						SecurityContextHolder.getContext().getAuthentication().getName());
			}
			catch (Exception e) {
				altaRegActivError(e);
			}

		}
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta", "Miembro/es añadido/s con éxito");
		FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
		context.execute("PF('dialogMessage').show()");
		return "/equipos/equipos";
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
		miembro.setPosicion("Jefe de equipo");
		equipoService.save(miembro);
	}

	/**
	 * 
	 */
	private void altaMiembrosEquipo() {
		for (User user : miembrosSelecionados) {
			Miembros miembro2 = new Miembros();
			miembro2.setNombreCompleto(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
			miembro2.setIdEquipo(equipo.getIdEquipo());
			miembro2.setUsername(user.getUsername());
			miembro2.setPosicion("Miembro");
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
		if (event.getOldStep().equals("jefeEquipo") & event.getNewStep().equals("miembros")) {
			User jefe = jefeSelecionado;
			listadoJefes.remove(jefe);
			listadoMiembros = listadoJefes;
		}
		if (event.getOldStep().equals("confirm") & event.getNewStep().equals("miembros")) {
			this.miembrosSelecionados = null;
			listadoJefes = listaUsuarios;
		}
		if (event.getOldStep().equals("miembros") & event.getNewStep().equals("jefeEquipo")) {
			listadoJefes.add(jefeSelecionado);
			this.jefeSelecionado = null;
		}
		if (event.getOldStep().equals("jefeEquipo") & event.getNewStep().equals("general")) {
			this.equipo.setNombreEquipo("");
			this.tipoEquipo = null;
			this.jefeSelecionado = null;
			this.miembrosSelecionados = null;
		}
	}

	public void save() {
		FacesMessage msg = new FacesMessage("Successful", "Welcome :" + equipo.getIdEquipo());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	@PostConstruct
	public void init() {
		// para que en el select cargue por defecto la opción "Seleccine uno..."
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

	// ************* Alta mensajes de notificacion, regActividad y alertas Progesin ********************
	/**
	 * @param descripcion
	 * @param tipoReg
	 * @param username
	 */
	private void saveReg(String descripcion, String tipoReg, String username) {
		RegistroActividad regActividad = new RegistroActividad();
		regActividad.setTipoRegActividad(tipoReg);
		regActividad.setUsernameRegActividad(username);
		regActividad.setFechaAlta(new Date());
		regActividad.setNombreSeccion(NOMBRESECCION);
		regActividad.setDescripcion(descripcion);
		regActividadService.save(regActividad);
	}

	/**
	 * @param descripcion
	 * @param tipoReg
	 * @param username
	 */
	private void saveNotificacion(String descripcion, String tipoNotificacion, String username) {
		Notificacion notificacion = new Notificacion();
		notificacion.setTipoNotificacion(tipoNotificacion);
		notificacion.setUsernameNotificacion(username);
		notificacion.setNombreSeccion(NOMBRESECCION);
		notificacion.setFechaAlta(new Date());
		notificacion.setDescripcion(descripcion);
		notificacionService.save(notificacion);
	}

	/**
	 * @param e
	 */
	private void altaRegActivError(Exception e) {
		regActividad.setTipoRegActividad(EstadoRegActividadEnum.ERROR.name());
		String message = Utilities.messageError(e);
		regActividad.setFechaAlta(new Date());
		regActividad.setNombreSeccion(NOMBRESECCION);
		regActividad.setUsernameRegActividad(SecurityContextHolder.getContext().getAuthentication().getName());
		regActividad.setDescripcion(message);
		regActividadService.save(regActividad);
	}
	// ************* Alta mensajes de notificacion, regActividad y alertas Progesin END********************
}
