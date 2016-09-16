package es.mira.progesin.web.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.web.beans.UserBean;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Component("userController")
@RequestScoped
public class MiPerfilController {
	private String claveActual;
	private String claveNueva;
	private String claveConfirm;
	
    @Autowired
	private UserBean user;
    
    @Autowired 
    IUserService userService;
	

	public MiPerfilController() {
//		FacesContext.getCurrentInstance().getViewRoot()
//				.setLocale(new Locale("es"));
	}

	public String cambiarClave() {
		if (this.getClaveNueva().equals(this.getClaveConfirm()) == false) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Las contraseñas introducidas no coinciden", ""));
		} else {
			User usuario = user.getUser();
			if(usuario.getPassword().equals(this.getClaveActual()) == false) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"La contraseña actual introducida no es válida. Inténtelo de nuevo", ""));
			} else {
				usuario.setPassword(this.getClaveNueva());
				userService.save(usuario);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"La contraseña ha sido modificada con éxito", ""));
			}
		}
		return null;
	}

}
