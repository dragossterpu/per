package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.services.ICuerpoEstadoService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.SendSimpleMail;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@Component("userBean")
@RequestScoped
public class UserBean {

	private User user;

	private List<CuerpoEstado> cuerposEstado;

	private CuerpoEstado cuerpoEstadoSeleccionado;

	private List<PuestoTrabajo> puestosTrabajo;

	private PuestoTrabajo puestoTrabajoSeleccionado;

	private UserBusqueda userBusqueda;

	private List<Boolean> list;

	private int numeroColumnasListadoUsarios = 9;

	@Autowired
	ApplicationBean applicationBean;

	@Autowired
	IUserService userService;

	@Autowired
	ICuerpoEstadoService cuerposEstadoService;

	@Autowired
	private PasswordEncoder passwordEncoder;

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
			if (userService.save(user) != null) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
						"El usuario ha sido creado con éxito");
			}

			// TODO generar NOTIFICACIÓN
			// TODO registrar actividad en log
		}
		return null;
	}

	public String getFormularioBusquedaUsuarios() {
		userBusqueda.resetValues();
		return "/users/usuarios";
	}

	/**
	 * Busca los usuarios según los filtros introducidos en el formulariod de búsqueda
	 */
	public void buscarUsuario() {
		List<User> listaUsuarios = userService.buscarUsuarioCriteria(userBusqueda);
		userBusqueda.setListaUsuarios(listaUsuarios);
	}

	/**
	 * Realiza una eliminación lógico del usuario (le pone fecha de baja)
	 * @param user El usuario seleccionado de la tabla del resultado de la búsqueda
	 */
	public void eliminarUsuario(User user) {
		user.setFechaBaja(new Date());
		user.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
		userService.save(user);
		userBusqueda.getListaUsuarios().remove(user);
	}

	/**
	 * Pasa los datos del usuario que queremos modificar al formulario de modificación para que cambien los valores que
	 * quieran
	 * @param user usuario recuperado del formulario de búsqueda de usuarios
	 * @return
	 */
	public String getFormModificarUsuario(User user) {
		this.user = user;
		return "/users/modificarUsuario";
	}

	/**
	 * Modifica los datos del usuario en función de los valores recuperados del formulario
	 */
	public void modificarUsuario() {
		if (userService.save(user) != null) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
					"El usuario ha sido modificado con éxito");
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
			log.error("Error en la restaruación de la contraseña", e);
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
}
