package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.Sugerencia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.services.ISugerenciaService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("sugerenciasBean")
@Scope(FacesViewScope.NAME)
public class SugerenciasBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private User user;
	 private String modulo;
	 private String descripcion;
	  
	 @Autowired
		ISugerenciaService sugerenciaService;
	 
	 public String guardarSugerencia() {
	    	if(modulo.equals("")){
		 		FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Es obligatorio elegir un modulo", ""));
					 return null;
		 	}
	    	else if(descripcion == null || descripcion.length()<10){
	    		FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"La descrición debe ser mayor de 10 caracteres", ""));
				 return null;
	    	}
	    	else{
	    	Date fecha = new Date();
	    	Sugerencia sugerencia = new Sugerencia();
	    	user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	sugerencia.setModulo(modulo);
	    	sugerencia.setDescripcion(descripcion);
	    	sugerencia.setFechaAlta(fecha);
	    	sugerencia.setUsuario(user.getUsername());
	    	sugerenciaService.save(sugerencia);
	    	System.out.println("modulo"+modulo);
		 	System.out.println("descripcion"+descripcion);
				 return "index";
	    	}
	 }
	 
	 
	
    @PostConstruct
    public void init(){

    }
    
}
