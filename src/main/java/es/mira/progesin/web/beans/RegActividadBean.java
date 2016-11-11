package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.RegActividad;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.services.IRegActividadService;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("regActividadBean")
@RequestScoped
public class RegActividadBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<RegActividad> listaRegActividad;

	private final String NOMBRESECCION = "Registros de actividad";

	private List<Boolean> list;

	private RegActividad regActividad;

	private Integer numColListRegActividad = 5;

	private RegActividadBusqueda regActividadBusqueda;

	public void eliminarRegActividad(RegActividad regActividad) {
		regActividad.setFechaBaja(new Date());
		regActividad.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			regActividadService.save(regActividad);
			regActividadBusqueda.getListaRegActividad().remove(regActividad);
		}
		catch (Exception e) {
			// Guardamos loe posibles errores en bbdd
			altaRegActivError(e);
		}

	}

	@Autowired
	IRegActividadService regActividadService;

	@Autowired
	ApplicationBean applicationBean;

	public void buscarRegActividad() {
		List<RegActividad> listaRegActividad = regActividadService.buscarRegActividadCriteria(regActividadBusqueda);
		regActividadBusqueda.setListaRegActividad(listaRegActividad);
	}

	public void onToggle(ToggleEvent e) {
		list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	
	public void getFormularioRegActividad() {
		regActividadBusqueda.resetValues();
	}

	@PostConstruct
	public void init() {
		regActividadBusqueda = new RegActividadBusqueda();
		list = new ArrayList<>();
		for (int i = 0; i <= numColListRegActividad; i++) {
			list.add(Boolean.TRUE);
		}
	}
	// ************* Alta mensajes de notificacion, regActividad y alertas Progesin ********************

	/**
	 * @param e
	 */
	private void altaRegActivError(Exception e) {
		RegActividad regActividad = new RegActividad();
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
