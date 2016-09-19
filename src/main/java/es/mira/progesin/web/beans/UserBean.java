package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.services.IUserService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("userBean")
//@ManagedBean
@RequestScoped
public class UserBean extends User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private User user;

	@Autowired
	IUserService userService;
	
	List<User> listaUsuarios;
	
	public String getUserPerfil() {
		//String username = SecurityContextHolder.getContext().getAuthentication().getName();
		user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//this.user = userService.findOne(username);
		setUserBeanFromUser(user);
		return "/principal/miPerfil.xhtml";
	}
	
	public void setUserBeanFromUser(User user) {
		this.nombre = user.getNombre();
		this.apellido1 = user.getApellido1();
		this.apellido2 = user.getApellido2();
		this.fechaAlta = user.getFechaAlta();
		this.numIdentificacion = user.getNumIdentificacion();
		this.telefono = user.getTelefono();
		this.correo = user.getCorreo();
		this.docIndentidad = user.getDocIndentidad();
		this.username = user.getUsername();
		this.estado = user.getEstado();
		this.envioNotificacion = user.getEnvioNotificacion();
	}
	
	public List<User> getUsers() {
		listaUsuarios = (List<User>) userService.findAll();
		return listaUsuarios;
	}
	
	@PostConstruct
    public void init(){
         getUsers();
    }
	
	
	
	
}
