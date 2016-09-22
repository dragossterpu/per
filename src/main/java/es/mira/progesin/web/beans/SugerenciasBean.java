package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
	 private List<Sugerencia>	sugerenciasListado; 
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
	    	sugerencia.setEstado("ACTIVO");
	    	sugerenciaService.save(sugerencia);
	    	System.out.println("modulo"+modulo);
		 	System.out.println("descripcion"+descripcion);
				 return "/index";
	    	}
	 }
	 
	 /**
		 * Método que nos lleva al listado de sugerencias
		 * Se llama desde el menu lateral
		 * @return
		 */
		public String sugerenciasListado() {
			
			sugerenciasListado = (List<Sugerencia>) sugerenciaService.findAll();
			return "/principal/sugerenciasListado";
		}
		
		
		
//		public String eliminarSugerencia() {
//			return "/principal/sugerenciasListado";
//		}
		
		
		
    @PostConstruct
    public void init(){
  	sugerenciasListado = (List<Sugerencia>) sugerenciaService.findAll();
//  	eliminarSugerencia();

    }
    
}
