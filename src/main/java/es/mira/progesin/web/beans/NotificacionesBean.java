package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.Notificacion;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("notificacionesBean")
@Scope(FacesViewScope.NAME)
public class NotificacionesBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Notificacion> listaNotificaciones = new ArrayList<Notificacion>();
	
    private void initList(){
    	Notificacion notificacion1 = new Notificacion(1, "Cuestionario general de comisaría local generado", new Date(), "userAlta", "USER");
    	Notificacion notificacion2 = new Notificacion(1, "Guía comisaría generada", new Date(), "userAlta", "USER");
    	Notificacion notificacion3 = new Notificacion(1, "Cuestionario Riesgos laborales genereado", new Date(), "userAlta", "USER");
        listaNotificaciones.add(notificacion1);
        listaNotificaciones.add(notificacion2);
        listaNotificaciones.add(notificacion3);
    }
   
    @PostConstruct
    public void init(){
    	initList();
    }
    

}
