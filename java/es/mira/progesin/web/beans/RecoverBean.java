package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.mira.progesin.jsf.scope.FacesViewScope;

import es.mira.progesin.persistence.entities.User;

import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("recoverBean")
@Scope(FacesViewScope.NAME)
public class RecoverBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String correo;

	private String nif;

	@Autowired
	IUserService userService;

	private List<User> listaUsers = new ArrayList<User>();

	public String claveOlvidada() {

		if (nif.equals("") && correo.equals("")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Introduzca un parametro para recuperar la contraseña o el usuario", ""));
			return null;
		}
		else {
			User user = userService.findByParams(correo, nif);
			if (user != null) {
				// Generating new Password
				String newPassword = Utilities.getPassword();
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String codePassword = passwordEncoder.encode(newPassword);
				user.setPassword(codePassword);
				// Send mail
				// Utilities.sendMail(user);
				// Update new Password
				userService.save(user);
				return "login";
			}
			else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No existe el usuario en el sistema. Contacte con el administrador", ""));
				return null;
			}

		}
	}

	@PostConstruct
	public void init() {

	}

}