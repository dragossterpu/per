package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.services.ICuerpoEstadoService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.SendSimpleMail;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("userBean")

public class UserBean {

	private User user;

	private List<CuerpoEstado> cuerposEstado;

	private CuerpoEstado cuerpoEstadoSeleccionado;

	private List<PuestoTrabajo> puestosTrabajo;

	private PuestoTrabajo puestoTrabajoSeleccionado;

	private UserBusqueda userBusqueda;

	private List<Boolean> list;

	private int numeroColumnasListadoUsarios = 9;

	private String estadoUsuario = null;

	private String vieneDe;

	@Autowired
	ApplicationBean applicationBean;

	@Autowired
	IUserService userService;

	@Autowired
	ICuerpoEstadoService cuerposEstadoService;

	@Autowired
	IRegistroActividadService regActividadService;

	@Autowired
	INotificacionService notificacionService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final String NOMBRESECCION = "Usuarios";

	public String getUserPerfil() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userService.findOne(username);
		return "/principal/miPerfil";
	}

	/**
	 * Método que nos lleva al formulario de alta de nuevos usuarios, inicializando todo lo necesario para mostrar
	 * correctamente la página (cuerpos de estado, puestos de trabajo, usuario nuevo). Se llama desde la página de
	 * búsqueda de usuarios.
	 * @return
	 */
	public String nuevoUsuario() {
		user = new User();
		user.setFechaAlta(new Date());
		user.setEstado(EstadoEnum.ACTIVO);
		cuerposEstado = (List<CuerpoEstado>) cuerposEstadoService.findAll();
		puestosTrabajo = applicationBean.getListaPuestosTrabajo();
		// para que en el select cargue por defecto la opción "Seleccine uno..."
		puestoTrabajoSeleccionado = null;
		cuerpoEstadoSeleccionado = null;
		return "/users/altaUsuario";
	}

	/**
	 * Método que recoge los valores introducidos en el formulario y da de alta al usuario en la BBDD
	 * @return
	 */
	public String altaUsuario() {
		if (userService.exists(user.getUsername())) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Ya existe un usuario con ese nombre de usuario. Pruebe con otro");
			FacesContext.getCurrentInstance().addMessage("username", message);
		}
		else {
			user.setCuerpoEstado(getCuerpoEstadoSeleccionado());
			user.setPuestoTrabajo(getPuestoTrabajoSeleccionado());
			String password = Utilities.getPassword();
			// TODO enviar correo al usuario con la contraseña
			user.setPassword(passwordEncoder.encode(password));
			try {
				if (userService.save(user) != null) {
					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
							"El usuario ha sido creado con éxito");
					String descripcion = "Alta nuevo usuario " + user.getNombre() + " " + user.getApellido1() + " "
							+ user.getApellido2();
					// Guardamos la actividad en bbdd
					saveReg(descripcion, EstadoRegActividadEnum.ALTA.name(),
							SecurityContextHolder.getContext().getAuthentication().getName());
					// Guardamos la notificacion en bbdd
					saveNotificacion(descripcion, EstadoRegActividadEnum.ALTA.name(),
							SecurityContextHolder.getContext().getAuthentication().getName());
				}
			}
			catch (Exception e) {
				// Guardamos loe posibles errores en bbdd
				regActividadService.altaRegActivError(getNOMBRESECCION(), e);
			}

		}
		return null;
	}

	/**
	 * Devuelve al formulario de búsqueda de usuarios a su estado inicial y borra los resultados de búsquedas anteriores
	 * si se navega desde el menú u otra sección.
	 * 
	 * @author EZENTIS
	 */
	public void getFormularioBusquedaUsuarios() {
		if ("menu".equalsIgnoreCase(this.vieneDe)) {
			limpiarBusqueda();
			this.vieneDe = null;
		}
	}

	/**
	 * Borra los resultados de búsquedas anteriores.
	 * 
	 * @author EZENTIS
	 */
	public void limpiarBusqueda() {
		userBusqueda.resetValues();
	}

	/**
	 * Busca los usuarios según los filtros introducidos en el formulariod de búsqueda
	 */
	public void buscarUsuario() {
		this.estadoUsuario = null;
		List<User> listaUsuarios = userService.buscarUsuarioCriteria(userBusqueda);
		userBusqueda.setListaUsuarios(listaUsuarios);
	}

	/**
	 * Realiza una eliminación lógico del usuario (le pone fecha de baja)
	 * @param user El usuario seleccionado de la tabla del resultado de la búsqueda
	 */
	public void eliminarUsuario(User user) {
		RegistroActividad regActividad = new RegistroActividad();
		user.setFechaBaja(new Date());
		user.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			userService.save(user);
			userBusqueda.getListaUsuarios().remove(user);
			String descripcion = "Se ha eliminado el usuario " + user.getNombre() + " " + user.getApellido1() + " "
					+ user.getApellido2();
			// Guardamos la actividad en bbdd
			saveReg(descripcion, EstadoRegActividadEnum.BAJA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());
			// Guardamos la notificacion en bbdd
			saveNotificacion(descripcion, EstadoRegActividadEnum.BAJA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());
		}
		catch (Exception e) {
			// Guardamos loe posibles errores en bbdd
			regActividadService.altaRegActivError(getNOMBRESECCION(), e);
		}

	}

	/**
	 * Pasa los datos del usuario que queremos modificar al formulario de modificación para que cambien los valores que
	 * quieran
	 * @param user usuario recuperado del formulario de búsqueda de usuarios
	 * @return
	 */
	public String getFormModificarUsuario(User user) {
		estadoUsuario = user.getEstado().name();
		this.user = user;
		return "/users/modificarUsuario";
	}

	/**
	 * Modifica los datos del usuario en función de los valores recuperados del formulario
	 */
	public void modificarUsuario() {
		try {

			if (userService.save(user) != null) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
						"El usuario ha sido modificado con éxito");
				String descripcion = "Modificación del usuario :" + " " + user.getNombre() + " " + user.getApellido1()
						+ " " + user.getApellido2();
				if (estadoUsuario != user.getEstado().name()) {
					// Generamos la alerta
				}
				// Guardamos la actividad en bbdd
				saveReg(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						SecurityContextHolder.getContext().getAuthentication().getName());
				// Guardamos la notificacion en bbdd
				saveNotificacion(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						SecurityContextHolder.getContext().getAuthentication().getName());
				// Exception e = new Exception();
				// throw e;
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Modificación",
					"Se ha producido un error al modificar el usuario. Inténtelo de nuevo más tarde");
			// Guardamos loe posibles errores en bbdd
			regActividadService.altaRegActivError(getNOMBRESECCION(), e);
		}

	}

	/**
	 * Genera una contraseña nueva y se la envía por correo al usuario
	 */
	public void restaurarClave() {
		try {
			String password = Utilities.getPassword();
			this.user.setPassword(passwordEncoder.encode(password));
			String cuerpoCorreo = "Su nueva contraseña es: " + password;
			userService.save(user);
			SendSimpleMail.sendMail("Restauración de la contraseña", user.getCorreo(), user.getNombre(), cuerpoCorreo);
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Clave",
					"Se ha enviado un correo al usuario con la nueva contraseña");
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Clave",
					"Se ha producido un error en la regeneración o envío de la contraseña");
			// Guardamos loe posibles errores en bbdd
			regActividadService.altaRegActivError(getNOMBRESECCION(), e);
		}
	}

	public void onToggle(ToggleEvent e) {
		list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	@PostConstruct
	public void init() {
		userBusqueda = new UserBusqueda();
		this.cuerposEstado = (List<CuerpoEstado>) cuerposEstadoService.findAll();
		this.puestosTrabajo = applicationBean.getListaPuestosTrabajo();
		// para que en el select cargue por defecto la opción "Seleccine uno..."
		this.puestoTrabajoSeleccionado = null;
		this.cuerpoEstadoSeleccionado = null;
		list = new ArrayList<>();
		for (int i = 0; i <= numeroColumnasListadoUsarios; i++) {
			list.add(Boolean.TRUE);
		}
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
	 * @param tipoNotificacion
	 * @param username
	 */
	private void saveNotificacion(String descripcion, String tipoNotificacion, String username) {
		Notificacion notificacion = new Notificacion();
		notificacion.setTipoNotificacion(tipoNotificacion);
		notificacion.setNombreSeccion(NOMBRESECCION);
		notificacion.setUsernameNotificacion(username);
		notificacion.setFechaAlta(new Date());
		notificacion.setDescripcion(descripcion);
		notificacionService.save(notificacion);
	}

	// ************* Alta mensajes de notificacion, regActividad y alertas Progesin END********************
}
