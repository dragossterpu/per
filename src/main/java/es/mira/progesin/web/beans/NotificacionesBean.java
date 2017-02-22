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
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
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
			listaNotificaciones.remove(notificacion);
			String descripcion = "Se ha eliminado la notificación " + notificacion.getDescripcion();
			// Guardamos la actividad en bbdd
			regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(), NOMBRESECCION);		
		}
		catch (Exception e) {
			// Guardamos loe posibles errores en bbdd
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
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

	
}
