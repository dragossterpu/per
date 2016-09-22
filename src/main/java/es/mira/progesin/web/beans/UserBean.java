package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
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
		return "/users/altaUsuario";
	}
	
	public String altaUsuario() {
		System.out.println("alta usuario");
		System.out.println(user);
		user.setCuerpoEstado(getCuerpoEstadoSeleccionado());
		user.setPuestoTrabajo(getPuestoTrabajoSeleccionado());
		String password = Utilities.getPassword();
		// TODO enviar correo al usuario con la contraseña
		String passwordEncrycted = Utilities.codePassword(password);
		user.setPassword(passwordEncrycted);
		user.setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());
		user.setRole(RoleEnum.ADMIN);
		userService.save(user);
		return null;
	}
}
