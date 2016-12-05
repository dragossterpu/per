package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
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
import es.mira.progesin.persistence.entities.Parametro;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.persistence.repositories.IParametrosRepository;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador de las operaciones relacionadas con los tipos de documentación necesarios para las solicitudes de
 * documentación previas al envio de cuestionarios.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.gd.TipoDocumentacion
 */
@Setter
@Getter
@Component("tipoDocumentacionBean")
@Scope(FacesViewScope.NAME)
public class TipoDocumentacionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private TipoDocumentacion documentacion;

	static Logger LOG = Logger.getLogger(TipoDocumentacionBean.class);

	private List<TipoDocumentacion> listaTipoDocumentacion;

	@Autowired
	transient IParametrosRepository parametrosRepository;

	private List<String> listaExtensionesPosibles = new ArrayList<>();

	private String descripcionNuevo;

	private String nombreNuevo;

	private List<String> extensionesNuevo;

	@Autowired
	ITipoDocumentacionService tipoDocumentacionService;

	/**
	 * Método que nos lleva al listado de la documentacion. Se llama desde el menu lateral
	 * 
	 * @author EZENTIS
	 * @return vista documentacionPrevia
	 */
	public String tipoDocumentacionListado() {
		listaTipoDocumentacion = tipoDocumentacionService.findAll();
		return "/documentacionPrevia/documentacionPrevia";
	}

	/**
	 * Método que elimina un tipo de documentación. Elimina el tipo de documentación y actualiza la lista de la vista
	 * documentacionPrevia
	 * 
	 * @author EZENTIS
	 * @param documentacion objeto tipo de documentacion a eliminar
	 */
	public void eliminarDocumentacion(TipoDocumentacion documentacion) {
		tipoDocumentacionService.delete(documentacion.getIdTipoDocumento());
		this.listaTipoDocumentacion = null;
		listaTipoDocumentacion = tipoDocumentacionService.findAll();
	}

	@PostConstruct
	public void init() throws MessagingException {
		listaTipoDocumentacion = tipoDocumentacionService.findAll();
		List<Parametro> parametrosExtensiones = parametrosRepository.findParamByParamSeccion("extensiones");
		for (Parametro p : parametrosExtensiones) {
			listaExtensionesPosibles.add(p.getParam().getClave());
		}

	}

	/**
	 * Método que da de alta un nuevo tipo de documentación. Recupera del formulario altaTipoDocumentacion los campos
	 * descripción, nombre y extensión, y muestra una ventana flotante con el mensaje resultado de la operación.
	 * 
	 * @author EZENTIS
	 */
	public void altaTipo() {
		TipoDocumentacion documentacion = new TipoDocumentacion();
		documentacion.setDescripcion(descripcionNuevo);
		documentacion.setNombre(nombreNuevo);
		documentacion.setExtensiones(extensionesNuevo);
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

	/**
	 * Método que guarda las modificaciones realizadas en caliente a un registro de la lista y cambia su estado a no
	 * editable
	 * 
	 * @author EZENTIS
	 * @param event evento disparado al pulsar el botón modificar edición
	 */
	public void onRowEdit(RowEditEvent event) {
		TipoDocumentacion tipoDoc = (TipoDocumentacion) event.getObject();
		tipoDocumentacionService.save(tipoDoc);
		FacesMessage msg = new FacesMessage("Documentación modificada", tipoDoc.getDescripcion());
		FacesContext.getCurrentInstance().addMessage("msgs", msg);
	}

	/**
	 * Método que cancela el estado de edición en caliente de un registro de la lista
	 * 
	 * @author EZENTIS
	 * @param event evento disparado al pulsar el botón cancelar edición
	 */
	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Modificación cancelada",
				((TipoDocumentacion) event.getObject()).getDescripcion());
		FacesContext.getCurrentInstance().addMessage("msgs", msg);
	}

}
