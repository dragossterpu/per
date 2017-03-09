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
import org.springframework.stereotype.Controller;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IAlertasNotificacionesUsuarioService;
import es.mira.progesin.services.IRegistroActividadService;
import lombok.Getter;
import lombok.Setter;

/*************************************************
 * 
 * Bean para las alertas
 * 
 * @author EZENTIS
 *************************************************/
@Setter
@Getter
@Controller("alertasBean")
@Scope(FacesViewScope.NAME)
public class AlertasBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	transient IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;

	@Autowired
	transient IRegistroActividadService regActividad;

	private List<Alerta> listaAlertas = new ArrayList<>();

	private List<Boolean> list;

	private int numColListAlert = 5;

	/********************************************************************************
	 * 
	 * Realiza una eliminación lógico de la alerta (le pone fecha de baja)
	 * 
	 * @param alerta
	 * 
	 * @author EZENTIS
	 *********************************************************************************/
	public void eliminarAlertas(Alerta alerta) {
		alerta.setFechaBaja(new Date());
		alerta.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			alertasNotificacionesUsuarioService.delete(SecurityContextHolder.getContext().getAuthentication().getName(),
					alerta.getIdAlerta(), TipoMensajeEnum.ALERTA);
			listaAlertas.remove(alerta);
			String descripcion = "Se ha eliminado la alerta :" + alerta.getDescripcion();
			// Guardamos la actividad en bbdd

			regActividad.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
					SeccionesEnum.ALERTAS.getDescripcion());
		}
		catch (Exception e) {
			// Guardamos los posibles errores en bbdd
			regActividad.altaRegActividadError(SeccionesEnum.ALERTAS.getDescripcion(), e);
		}

	}

	/********************************************************************************
	 * 
	 * Inicializa el listado de alertas para el usuario logado
	 * 
	 *********************************************************************************/

	private void initList() {
		listaAlertas = alertasNotificacionesUsuarioService
				.findAlertasByUser(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	/**********************************************************************************
	 * 
	 * Controla las columnas visibles en la lista de resultados del buscador
	 * 
	 * @param ToggleEvent
	 * 
	 **********************************************************************************/

	public void onToggle(ToggleEvent e) {
		list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	/********************************************************************************
	 * 
	 * Inicializa el bean
	 * 
	 *********************************************************************************/

	@PostConstruct
	public void init() {

		initList();
		list = new ArrayList<>();
		for (int i = 0; i <= numColListAlert; i++) {
			list.add(Boolean.TRUE);
		}
	}

}
