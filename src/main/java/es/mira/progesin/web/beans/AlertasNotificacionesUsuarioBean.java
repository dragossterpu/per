package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
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
@Component("mensajesBean")
@Scope(FacesViewScope.NAME)
public class AlertasNotificacionesUsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
	
	@Autowired
	IRegistroActividadService regActividad;
	
	private List<Alerta> listaAlertas = new ArrayList<Alerta>();
	
	private List<Notificacion> listaNotificaciones = new ArrayList<Notificacion>();
	
	private void initList() {
		listaAlertas=alertasNotificacionesUsuarioService.findAlertasByUser(SecurityContextHolder.getContext().getAuthentication().getName());
		listaNotificaciones=alertasNotificacionesUsuarioService.findNotificacionesByUser(SecurityContextHolder.getContext().getAuthentication().getName());
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
}
