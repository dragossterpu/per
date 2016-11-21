package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean para las notificaciones
 * @author EZENTIS
 */
@Setter
@Getter
@Component("notificacionesBean")
@Scope(FacesViewScope.NAME)
public class NotificacionesBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Boolean> list;

	@Autowired
	INotificacionService notificacionService;

	@Autowired
	IRegistroActividadService regActividadService;

	private List<Notificacion> listaNotificaciones = new ArrayList<Notificacion>();

	private int numColListNotif = 4;

	private final String NOMBRESECCION = "Notificaciones";

	private RegistroActividad regActividad = new RegistroActividad();

	private void initList() {
		listaNotificaciones = notificacionService.findByFechaBajaIsNull();

	}

	public void onToggle(ToggleEvent e) {
		list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	/**
	 * @comment Realiza una eliminación lógico de la notificación (le pone fecha de baja)
	 * @comment La notificacion seleccionada de la tabla de notificaciones
	 * @param notificacion
	 * @author EZENTIS
	 */
	public void eliminarNotificacion(Notificacion notificacion) {
		notificacion.setFechaBaja(new Date());
		notificacion.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			notificacionService.save(notificacion);
			listaNotificaciones.remove(notificacion);
			String descripcion = "Se ha eliminado la notificación " + notificacion.getDescripcion();
			// Guardamos la actividad en bbdd
			saveReg(descripcion, EstadoRegActividadEnum.BAJA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());
		}
		catch (Exception e) {
			// Guardamos loe posibles errores en bbdd
			altaRegActivError(e);
		}

	}

	@PostConstruct
	public void init() {
		initList();
		list = new ArrayList<>();
		for (int i = 0; i <= numColListNotif; i++) {
			list.add(Boolean.TRUE);
		}
	}

	// ************* Alta mensajes de notificacion, regActividad y alertas Progesin ********************
	/**
	 * @param descripcion
	 * @param tipoReg
	 * @param username
	 */
	private void saveReg(String descripcion, String tipoReg, String username) {

		regActividad.setTipoRegActividad(tipoReg);
		regActividad.setUsernameRegActividad(username);
		regActividad.setFechaAlta(new Date());
		regActividad.setNombreSeccion(NOMBRESECCION);
		regActividad.setDescripcion(descripcion);
		regActividadService.save(regActividad);
	}

	/**
	 * @param e
	 */
	private void altaRegActivError(Exception e) {
		regActividad.setTipoRegActividad(EstadoRegActividadEnum.ERROR.name());
		String message = Utilities.messageError(e);
		regActividad.setFechaAlta(new Date());
		regActividad.setNombreSeccion(NOMBRESECCION);
		regActividad.setUsernameRegActividad(SecurityContextHolder.getContext().getAuthentication().getName());
		regActividad.setDescripcion(message);
		regActividadService.save(regActividad);
	}
	// ************* Alta mensajes de notificacion, regActividad y alertas Progesin END********************
}
