package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.Documento;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**********************************************************
 * Bean para el gestor documental
 * 
 * @author EZENTIS
 * 
 ********************************************************/

@Setter
@Getter
@Controller("gestorDocumentalBean")
@Scope("session")
@Slf4j
public class GestorDocumentalBean {

	List<Documento> listadoDocumentos = new ArrayList<>();

	private StreamedContent file;

	@Autowired
	private IDocumentoService documentoService;

	@Autowired
	private IAlertaService alertaService;

	@Autowired
	private INotificacionService notificacionService;

	@Autowired
	private IRegistroActividadService registroActividadService;

	/**********************************************************
	 * Inicializa el bean
	 * 
	 ********************************************************/

	@PostConstruct
	public void init() {
		recargaLista();
	}

	/**********************************************************
	 * Carga la lista de documentos
	 * 
	 ********************************************************/

	public void recargaLista() {
		listadoDocumentos = (List<Documento>) documentoService.findAll();
	}

	/**********************************************************
	 * Descarga el fichero contenido en la base de datos
	 * 
	 * @param documento
	 * 
	 * 
	 ********************************************************/

	public void descargarFichero(Documento documento) {
		try {
			file = documentoService.descargaDocumento(documento);
		}
		catch (Exception e) {
			log.error("Error en la descarga de documentos: ", e);
		}
	}

	/**********************************************************
	 * Carga el fichero seleccionado en la base de datos
	 * 
	 * @param event event FileUploadEvent
	 * @throws Exception
	 * 
	 ********************************************************/

	public void cargaFichero(FileUploadEvent event) throws Exception {
		try {

			UploadedFile uFile = event.getFile();
			if (documentoService.extensionCorrecta(uFile)) {
				documentoService.cargaDocumento(uFile);

			}
			else {
				RequestContext context = RequestContext.getCurrentInstance();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Carga de ficheros",
						"La extensión del fichero '" + event.getFile().getFileName()
								+ "' no corresponde a su tipo real");
				FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
				context.execute("PF('dialogMessage').show()");
				registroActividadService.altaRegActividad("La extensión del fichero no corresponde a su tipo real",
						TipoRegistroEnum.ERROR.name(), "Gestor documental");

			}

			recargaLista();
		}
		catch (Exception ex) {
			registroActividadService.altaRegActividadError("Gestor documental", ex);
			throw ex;
		}
	}

	/**********************************************************
	 * Elimina el fichero seleccionado de la base de datos
	 * 
	 * @param documento
	 * 
	 ********************************************************/

	public void eliminarDocumento(Documento documento) {
		documentoService.delete(documento);
		recargaLista();
	}
}
