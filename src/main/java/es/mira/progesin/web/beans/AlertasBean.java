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
import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean para las alertas
 * @author EZENTIS
 */
@Setter
@Getter
@Component("alertasBean")
@Scope(FacesViewScope.NAME)
public class AlertasBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	IAlertaService alertasService;

	private List<Alerta> listaAlertas = new ArrayList<Alerta>();

	private RegistroActividad regActividad = new RegistroActividad();

	private final String NOMBRESECCION = "Alertas";

	private List<Boolean> list;

	private int numColListAlert = 5;

	@Autowired
	IRegistroActividadService regActividadService;

	/**
	 * @comment Realiza una eliminación lógico de la alerta (le pone fecha de baja)
	 * @comment La alerta seleccionada de la tabla de alertass
	 * @param alerta
	 * @author EZENTIS
	 */
	public void eliminarAlertas(Alerta alerta) {
		alerta.setFechaBaja(new Date());
		alerta.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			alertasService.save(alerta);
			listaAlertas.remove(alerta);
			String descripcion = "Se ha eliminado la alerta :" + alerta.getDescripcion();
			// Guardamos la actividad en bbdd
			saveReg(descripcion, EstadoRegActividadEnum.BAJA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());
		}
		catch (Exception e) {
			// Guardamos loe posibles errores en bbdd
			altaRegActivError(e);
		}

	}

	private void initList() {
		listaAlertas = alertasService.findByFechaBajaIsNull();

	}

	public void onToggle(ToggleEvent e) {
		list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	@PostConstruct
	public void init() {

		initList();
		list = new ArrayList<>();
		for (int i = 0; i <= numColListAlert; i++) {
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
