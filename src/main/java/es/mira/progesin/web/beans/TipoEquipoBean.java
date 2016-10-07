package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.services.ITipoEquipoService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("tipoEquipoBean")
@RequestScoped
public class TipoEquipoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private TipoEquipo equipo;

	static Logger LOG = Logger.getLogger(TipoEquipoBean.class);

	private List<TipoEquipo> listaTipoEquipo;
	
	private String tipoNuevo;

	@Autowired
	ITipoEquipoService tipoEquipoService;

	/**
	 * Método que nos lleva al listado de los tipos de equipos. Se llama desde el menu lateral
	 * @return
	 */
	public String tipoEquipoListado() {
		listaTipoEquipo = (List<TipoEquipo>) tipoEquipoService.findAll();
		return "/equipos/listadoEquipos";
	}

	public void eliminarEquipo(TipoEquipo equipo) {
		tipoEquipoService.delete(equipo.getIdTipoEquipo());
		this.listaTipoEquipo = null;
		listaTipoEquipo = (List<TipoEquipo>) tipoEquipoService.findAll();
	}

	@PostConstruct
	public void init() throws MessagingException {

		listaTipoEquipo = (List<TipoEquipo>) tipoEquipoService.findAll();
		

	}
	public void altaTipo() {
		TipoEquipo equipo = new TipoEquipo();
		equipo.setDescripcion(tipoNuevo);
		try {
			if (tipoEquipoService.save(equipo) != null) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
						"El tipo de equipo ha sido creado con éxito");
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
					"Se ha producido un error al dar de alta el tipo de equipo, inténtelo de nuevo más tarde");
			// TODO log de errores
		}
		listaTipoEquipo = (List<TipoEquipo>) tipoEquipoService.findAll();
		// TODO generar alerta / notificación
	}
	public void onRowEdit(RowEditEvent event) {
		TipoEquipo equipo = (TipoEquipo) event.getObject();
		tipoEquipoService.save(equipo);
		FacesMessage msg = new FacesMessage("Tipo de equipos  modificado", equipo.getDescripcion());
		FacesContext.getCurrentInstance().addMessage("msgs", msg);
	}
	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Modificación cancelada",
				((TipoEquipo) event.getObject()).getDescripcion());
		FacesContext.getCurrentInstance().addMessage("msgs", msg);
	}
	



}
