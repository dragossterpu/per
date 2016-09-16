package es.mira.progesin.web.beans;

import java.io.Serializable;

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
@SessionScoped
public class UserBean extends User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String estadoTexto;
	private User user = new User();

	@Autowired
	IUserService userService;
	
	public String getUserPerfil() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		this.user = userService.findOne(username);
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
		if("1".equals(user.getEstado())) {
			this.estadoTexto = Constantes.ESTADO_ACTIVO;
		} else {
			this.estadoTexto = Constantes.ESTADO_INACTIVO;
		}
		this.envioNotificacion = user.getEnvioNotificacion();
	}
	
}
