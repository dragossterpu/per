package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.TipoDocumentacion;
import es.mira.progesin.services.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("tipoDocumentacionBean")
@Scope(FacesViewScope.NAME)
public class TipoDocumentacionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private TipoDocumentacion documentacion;

	static Logger LOG = Logger.getLogger(TipoDocumentacionBean.class);

	private List<TipoDocumentacion> listaTipoDocumentacion;

	private String descripcionNuevo;

	private String nombreNuevo;

	private String extensionNuevo;

	@Autowired
	ITipoDocumentacionService tipoDocumentacionService;

	/**
	 * Método que nos lleva al listado de la documentacion. Se llama desde el menu lateral
	 * @return
	 */
	public String tipoDocumentacionListado() {
		listaTipoDocumentacion = tipoDocumentacionService.findAll();
		return "/documentacionPrevia/documentacionPrevia";
	}

	public void eliminarDocumentacion(TipoDocumentacion documentacion) {
		tipoDocumentacionService.delete(documentacion.getIdTipoDocumento());
		this.listaTipoDocumentacion = null;
		listaTipoDocumentacion = tipoDocumentacionService.findAll();
	}

	@PostConstruct
	public void init() throws MessagingException {
		listaTipoDocumentacion = tipoDocumentacionService.findAll();

	}

	public void altaTipo() {
		TipoDocumentacion documentacion = new TipoDocumentacion();
		documentacion.setDescripcion(descripcionNuevo);
		documentacion.setNombre(nombreNuevo);
		documentacion.setExtension(extensionNuevo);
		try {
			if (tipoDocumentacionService.save(documentacion) != null) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
						"La documentación ha sido creado con éxito");
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
					"Se ha producido un error al dar de alta la documentación, inténtelo de nuevo más tarde");
			// TODO log de errores
		}
		listaTipoDocumentacion = tipoDocumentacionService.findAll();
		// TODO generar alerta / notificación
	}

	public void onRowEdit(RowEditEvent event) {
		TipoDocumentacion documentacion = (TipoDocumentacion) event.getObject();
		tipoDocumentacionService.save(documentacion);
		FacesMessage msg = new FacesMessage("Documentación modificada", documentacion.getDescripcion());
		FacesContext.getCurrentInstance().addMessage("msgs", msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Modificación cancelada",
				((TipoDocumentacion) event.getObject()).getDescripcion());
		FacesContext.getCurrentInstance().addMessage("msgs", msg);
	}

}
