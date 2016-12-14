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
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.services.IAlertasNotificacionesUsuarioService;
import es.mira.progesin.services.IRegistroActividadService;
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
	IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
	
	@Autowired
	IRegistroActividadService regActividad;

	private List<Alerta> listaAlertas = new ArrayList<Alerta>();

	private final String NOMBRESECCION = "Alertas";

	private List<Boolean> list;

	private int numColListAlert = 5;


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
			//alertasService.save(alerta);
			alertasNotificacionesUsuarioService.delete(SecurityContextHolder.getContext().getAuthentication().getName(), alerta.getIdAlerta(), TipoMensajeEnum.ALERTA);
			listaAlertas.remove(alerta);
			String descripcion = "Se ha eliminado la alerta :" + alerta.getDescripcion();
			// Guardamos la actividad en bbdd
			
			regActividad.altaRegActividad(descripcion, EstadoRegActividadEnum.BAJA.name(), NOMBRESECCION);
		}
		catch (Exception e) {
			// Guardamos loe posibles errores en bbdd
			regActividad.altaRegActividadError(NOMBRESECCION, e);
		}

	}

	private void initList() {
		//listaAlertas = alertasService.findByFechaBajaIsNull();
		listaAlertas=alertasNotificacionesUsuarioService.findAlertasByUser(SecurityContextHolder.getContext().getAuthentication().getName());
		//listaAlertas=alertasService.buscarAlertasUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
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


}
