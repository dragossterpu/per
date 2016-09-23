package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.services.ICuerpoEstadoService;
import es.mira.progesin.services.IPuestoTrabajoService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("userBean")
@RequestScoped
public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private User 				user;
	private List<CuerpoEstado>	cuerposEstado;
	private CuerpoEstado 		cuerpoEstadoSeleccionado;
	private List<PuestoTrabajo> puestosTrabajo;
	private PuestoTrabajo		puestoTrabajoSeleccionado;

	
	@Autowired
	IUserService userService;
	@Autowired
	ICuerpoEstadoService cuerposEstadoService;
	@Autowired
	IPuestoTrabajoService puestosTrabajoService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	List<User> listaUsuarios;
	
	public String getUserPerfil() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userService.findOne(username);
		return "/principal/miPerfil";
	}
	
	
//	public List<User> getUsers() {
//		listaUsuarios = (List<User>) userService.findAll();
//		return listaUsuarios;
//	}
//	
//	@PostConstruct
//    public void init(){
//         getUsers();
//    }
	
	/**
	 * Método que nos lleva al formulario de alta de nuevos usuarios, inicializando todo lo necesario para
	 * mostrar correctamente la página (cuerpos de estado, puestos de trabajo, usuario nuevo).
	 * Se llama desde la página de búsqueda de usuarios.
	 * @return
	 */
	public String nuevoUsuario() {
		user = new User();
		user.setFechaAlta(new Date());
		user.setEstado(EstadoEnum.ACTIVO);
		cuerposEstado = (List<CuerpoEstado>) cuerposEstadoService.findAll();
		puestosTrabajo = (List<PuestoTrabajo>) puestosTrabajoService.findAll();
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
		if(userService.exists(user.getUsername())) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ya existe un usuario con ese nombre de usuario. Pruebe con otro");
			FacesContext.getCurrentInstance().addMessage("username", message);
		} else {
			user.setCuerpoEstado(getCuerpoEstadoSeleccionado());
			user.setPuestoTrabajo(getPuestoTrabajoSeleccionado());
			String password = Utilities.getPassword();
			// TODO enviar correo al usuario con la contraseña
			user.setPassword(passwordEncoder.encode(password));
			user.setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());
			if (userService.save(user) != null) {
				RequestContext context = RequestContext.getCurrentInstance();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta", "El usuario ha sido creado con éxito");
				FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
				context.execute("PF('dialogMessage').show()");
			}
		}
		return null;
	}
	
}
