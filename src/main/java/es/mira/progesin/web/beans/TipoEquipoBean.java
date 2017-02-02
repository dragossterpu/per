package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ITipoEquipoService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Controller("tipoEquipoBean")
@Scope("session")
public class TipoEquipoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String NOMBRESECCION = "Tipos de Equipo";

	private TipoEquipo tipoEquipoNuevo;

	private List<TipoEquipo> listaTipoEquipo;

	@Autowired
	private transient ITipoEquipoService tipoEquipoService;

	@Autowired
	private transient IEquipoService equipoService;
	
	@Autowired
	private transient IRegistroActividadService regActividadService;

	/**
	 * Método que nos lleva al listado de los tipos de equipos. Se llama en la carga de la página
	 * 
	 */
	public void tipoEquipoListado() {
		listaTipoEquipo = (List<TipoEquipo>) tipoEquipoService.findAll();
	}

	public void eliminarTipo(TipoEquipo tipo) {
		try {
			if (equipoService.existsByTipoEquipo(tipo)) {
				FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Eliminación abortada",
						"No se puede eliminar, existen equipos de este tipo", null);				
			} else {
				tipoEquipoService.delete(tipo.getId());
				listaTipoEquipo.remove(tipo);
			} 
		} catch (Exception e) {
			FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Error",
					"Se ha producido un error al eliminar el tipo de equipo, inténtelo de nuevo más tarde", null);
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
	}

	@PostConstruct
	public void init() throws MessagingException {

		listaTipoEquipo = (List<TipoEquipo>) tipoEquipoService.findAll();

	}

	public String getFormNuevoTipoEquipo() {
		tipoEquipoNuevo = new TipoEquipo();
		return "/equipos/altaTipoEquipo";
	}

	public void altaTipo() {
		try {
			if (tipoEquipoService.save(tipoEquipoNuevo) != null) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
						"El tipo de equipo ha sido creado con éxito");
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
					"Se ha producido un error al dar de alta el tipo de equipo, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
		listaTipoEquipo = (List<TipoEquipo>) tipoEquipoService.findAll();
		// TODO generar alerta / notificación
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			TipoEquipo tipoEquipo = (TipoEquipo) event.getObject();
			if (tipoEquipoService.save(tipoEquipo) != null) {
				FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Modificación",
						"Tipo de equipo modificado con éxito", null);
			}
		} catch (Exception e) {
			FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Error",
					"Se ha producido un error al modificar el tipo de equipo, inténtelo de nuevo más tarde", null);
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
	}

	public void onRowCancel(RowEditEvent event) {
		TipoEquipo tipoEquipo = (TipoEquipo) event.getObject();
		FacesMessage msg = new FacesMessage("Modificación cancelada", tipoEquipo.getCodigo() + " - " + tipoEquipo.getDescripcion());
		FacesContext.getCurrentInstance().addMessage("msgs", msg);
		FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Modificación cancelada",
				"", null);
	}

}
