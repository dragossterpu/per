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
import org.springframework.stereotype.Controller;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IAlertasNotificacionesUsuarioService;
import es.mira.progesin.services.IRegistroActividadService;
import lombok.Getter;
import lombok.Setter;

/******************************************************
 * 
 * Bean para los mensajes (Alertas y notificaciones)
 * @author EZENTIS
 * 
 ******************************************************/

@Setter
@Getter
@Controller("AlertasNotificacionesUsuarioBean")
@Scope(FacesViewScope.NAME)
public class AlertasNotificacionesUsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;

	@Autowired
	private IRegistroActividadService regActividad;

	private List<Alerta> listaAlertas = new ArrayList<Alerta>();

	private Page<AlertasNotificacionesUsuario> pageAlertas;

	private List<Notificacion> listaNotificaciones = new ArrayList<Notificacion>();

	private Page<AlertasNotificacionesUsuario> pageNotificaciones;

	/******************************************************
	 * 
	 * Inicializa las listas de alertas y notificaciones recuperando las que corresponden al usuario logado
	 * 
	 ******************************************************/

	private void initList() {
		pageAlertas = alertasNotificacionesUsuarioService.findByUsuarioAndTipo(
				SecurityContextHolder.getContext().getAuthentication().getName(), TipoMensajeEnum.ALERTA,
				new PageRequest(0, 20, Direction.DESC, "fechaAlta"));
		listaAlertas = alertasNotificacionesUsuarioService.findAlertas(pageAlertas.getContent());

		pageNotificaciones = alertasNotificacionesUsuarioService.findByUsuarioAndTipo(
				SecurityContextHolder.getContext().getAuthentication().getName(), TipoMensajeEnum.NOTIFICACION,
				new PageRequest(0, 20, Direction.DESC, "fechaAlta"));
		listaNotificaciones = alertasNotificacionesUsuarioService.findNotificaciones(pageNotificaciones.getContent());

	}

	/******************************************************
	 * 
	 * Inicializa el bean
	 * 
	 ******************************************************/

	@PostConstruct
	public void init() {
		initList();
	}

	/******************************************************
	 * 
	 * Elimina, para el usuario logado, la alerta pasada como parámetro
	 * 
	 * @param Alerta
	 * 
	 ******************************************************/

	public void eliminarAlertas(Alerta alerta) {
		try {
			alertasNotificacionesUsuarioService.delete(SecurityContextHolder.getContext().getAuthentication().getName(),
					alerta.getIdAlerta(), TipoMensajeEnum.ALERTA);
			listaAlertas.remove(alerta);
			String descripcion = "Se ha eliminado la alerta :" + alerta.getDescripcion();
			regActividad.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(), "Alertas");
		}
		catch (Exception e) {
			regActividad.altaRegActividadError("Alertas", e);
		}

	}

	/******************************************************
	 * 
	 * Elimina, para el usuario logado, la notificación pasada como parámetro
	 * 
	 * @param Notificacion
	 * 
	 ******************************************************/

	public void eliminarNotificacion(Notificacion notificacion) {
		try {
			alertasNotificacionesUsuarioService.delete(SecurityContextHolder.getContext().getAuthentication().getName(),
					notificacion.getIdNotificacion(), TipoMensajeEnum.NOTIFICACION);
			listaNotificaciones.remove(notificacion);
			String descripcion = "Se ha eliminado la notificación :" + notificacion.getDescripcion();
			regActividad.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(), "Notificaciones");
		}
		catch (Exception e) {
			regActividad.altaRegActividadError("Notificaciones", e);
		}

	}

	/******************************************************
	 * 
	 * Avanza la paginación de Notificaciones
	 * 
	 * 
	 ******************************************************/

	public void nextNotificacion() {
		if (pageNotificaciones.hasNext()) {
			pageNotificaciones = alertasNotificacionesUsuarioService.findByUsuarioAndTipo(
					SecurityContextHolder.getContext().getAuthentication().getName(), TipoMensajeEnum.NOTIFICACION,
					pageNotificaciones.nextPageable());
			listaNotificaciones = alertasNotificacionesUsuarioService
					.findNotificaciones(pageNotificaciones.getContent());
		}
	}

	/******************************************************
	 * 
	 * Retrocede la paginación de Notificaciones
	 * 
	 * 
	 ******************************************************/

	public void previousNotificacion() {
		if (pageNotificaciones.hasPrevious()) {
			pageNotificaciones = alertasNotificacionesUsuarioService.findByUsuarioAndTipo(
					SecurityContextHolder.getContext().getAuthentication().getName(), TipoMensajeEnum.NOTIFICACION,
					pageNotificaciones.previousPageable());
			listaNotificaciones = alertasNotificacionesUsuarioService
					.findNotificaciones(pageNotificaciones.getContent());
		}
	}

	/******************************************************
	 * 
	 * Avanza la paginación de Alertas
	 * 
	 * 
	 ******************************************************/

	public void nextAlerta() {
		if (pageAlertas.hasNext()) {
			pageAlertas = alertasNotificacionesUsuarioService.findByUsuarioAndTipo(
					SecurityContextHolder.getContext().getAuthentication().getName(), TipoMensajeEnum.ALERTA,
					pageAlertas.nextPageable());
			listaAlertas = alertasNotificacionesUsuarioService.findAlertas(pageAlertas.getContent());
		}
	}

	/******************************************************
	 * 
	 * Retrocede la paginación de Alertas
	 * 
	 * 
	 ******************************************************/

	public void previousAlerta() {
		if (pageAlertas.hasPrevious()) {
			pageAlertas = alertasNotificacionesUsuarioService.findByUsuarioAndTipo(
					SecurityContextHolder.getContext().getAuthentication().getName(), TipoMensajeEnum.ALERTA,
					pageAlertas.previousPageable());
			listaAlertas = alertasNotificacionesUsuarioService.findAlertas(pageAlertas.getContent());
		}
	}
}
