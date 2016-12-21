package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.repositories.IAlertasNotificacionesUsuarioRepository;
import es.mira.progesin.services.IAlertasNotificacionesUsuarioService;
import es.mira.progesin.services.IRegistroActividadService;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean para los mensajes (Alertas y notificaciones)
 * @author EZENTIS
 */

@Setter
@Getter
@Component("AlertasNotificacionesUsuarioBean")
@Scope(FacesViewScope.NAME)
public class AlertasNotificacionesUsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
	
	
	@Autowired
	IRegistroActividadService regActividad;
	
	private List<Alerta> listaAlertas = new ArrayList<Alerta>();
	private Page<AlertasNotificacionesUsuario> pageAlertas;
				
	
	private List<Notificacion> listaNotificaciones = new ArrayList<Notificacion>();
	private Page<AlertasNotificacionesUsuario> pageNotificaciones;
	
	
	private void initList() {
		pageAlertas=(Page<AlertasNotificacionesUsuario>) alertasNotificacionesUsuarioService.findByUsuarioAndTipo(SecurityContextHolder.getContext().getAuthentication().getName(),TipoMensajeEnum.ALERTA, new PageRequest(0,20,Direction.DESC, "fechaAlta"));
		listaAlertas=alertasNotificacionesUsuarioService.findAlertas(pageAlertas.getContent());
		
		pageNotificaciones=(Page<AlertasNotificacionesUsuario>) alertasNotificacionesUsuarioService.findByUsuarioAndTipo(SecurityContextHolder.getContext().getAuthentication().getName(),TipoMensajeEnum.NOTIFICACION, new PageRequest(0,20,Direction.DESC, "fechaAlta"));
		listaNotificaciones=alertasNotificacionesUsuarioService.findNotificaciones(pageNotificaciones.getContent());
		
	}

	
	@PostConstruct
	public void init() {
		initList();
	}
	
	
	public void eliminarAlertas(Alerta alerta) {
		try {
			alertasNotificacionesUsuarioService.delete(SecurityContextHolder.getContext().getAuthentication().getName(), alerta.getIdAlerta(), TipoMensajeEnum.ALERTA);
			listaAlertas.remove(alerta);
			String descripcion = "Se ha eliminado la alerta :" + alerta.getDescripcion();
			regActividad.altaRegActividad(descripcion, EstadoRegActividadEnum.BAJA.name(), "Alertas");
		}
		catch (Exception e) {
			regActividad.altaRegActividadError("Alertas", e);
		}

	}
	
	public void eliminarNotificacion(Notificacion notificacion) {
		try {
			alertasNotificacionesUsuarioService.delete(SecurityContextHolder.getContext().getAuthentication().getName(), notificacion.getIdNotificacion(), TipoMensajeEnum.NOTIFICACION);
			listaNotificaciones.remove(notificacion);
			String descripcion = "Se ha eliminado la notificación :" + notificacion.getDescripcion();
			regActividad.altaRegActividad(descripcion, EstadoRegActividadEnum.BAJA.name(), "Notificaciones");
		}
		catch (Exception e) {
			regActividad.altaRegActividadError("Notificaciones", e);
		}

	}
	
	public String nextNotificacion(){
		if(pageNotificaciones.hasNext()){
			pageNotificaciones = (Page<AlertasNotificacionesUsuario>) alertasNotificacionesUsuarioService.findByUsuarioAndTipo(SecurityContextHolder.getContext().getAuthentication().getName(),TipoMensajeEnum.NOTIFICACION, pageNotificaciones.nextPageable());
			listaNotificaciones = alertasNotificacionesUsuarioService.findNotificaciones(pageNotificaciones.getContent());
		}
		return "";
	}

	public String previousNotificacion(){
		if(pageNotificaciones.hasPrevious()){
			pageNotificaciones = (Page<AlertasNotificacionesUsuario>) alertasNotificacionesUsuarioService.findByUsuarioAndTipo(SecurityContextHolder.getContext().getAuthentication().getName(),TipoMensajeEnum.NOTIFICACION, pageNotificaciones.previousPageable());
			listaNotificaciones = alertasNotificacionesUsuarioService.findNotificaciones(pageNotificaciones.getContent());
		}
		return "";
	}
	
	public String nextAlerta(){
		if(pageAlertas.hasNext()){
			pageAlertas = (Page<AlertasNotificacionesUsuario>) alertasNotificacionesUsuarioService.findByUsuarioAndTipo(SecurityContextHolder.getContext().getAuthentication().getName(),TipoMensajeEnum.ALERTA, pageAlertas.nextPageable());
			listaAlertas = alertasNotificacionesUsuarioService.findAlertas(pageAlertas.getContent());
		}
		return "";
	}

	public String previousAlerta(){
		if(pageAlertas.hasPrevious()){
			pageAlertas = (Page<AlertasNotificacionesUsuario>) alertasNotificacionesUsuarioService.findByUsuarioAndTipo(SecurityContextHolder.getContext().getAuthentication().getName(),TipoMensajeEnum.ALERTA, pageAlertas.previousPageable());
			listaAlertas = alertasNotificacionesUsuarioService.findAlertas(pageAlertas.getContent());
		}
		return "";
	}
}
