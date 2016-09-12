package es.mira.progesin.web.views;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import es.mira.progesin.persistence.entities.User;

@Component("userBean")
@RequestScope
public class UserBean extends User {

	private String nuevoPassword;
	
	public UserBean() {
	}

	

}
