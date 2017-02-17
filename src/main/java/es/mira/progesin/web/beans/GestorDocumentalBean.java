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
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Controller("gestorDocumentalBean")
@Scope("session")
@Slf4j
public class GestorDocumentalBean {

	List<Documento> listadoDocumentos = new ArrayList<>();

	private StreamedContent file;

	@Autowired
	IDocumentoService documentoService;

	@Autowired
	IAlertaService alertaService;

	@Autowired
	INotificacionService notificacionService;

	@Autowired
	IRegistroActividadService registroActividadService;

	@PostConstruct
	public void init() {
		recargaLista();
	}

	public void recargaLista() {
		listadoDocumentos = (List<Documento>) documentoService.findAll();
	}

	public void descargarFichero(Documento documento) {
		try {
			file = documentoService.descargaDocumento(documento);
		}
		catch (Exception e) {
			log.error("Error en la descarga de documentos: ", e);
		}
	}

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
						EstadoRegActividadEnum.ERROR.name(), "Gestor documental");

			}

			recargaLista();
		}
		catch (Exception ex) {
			registroActividadService.altaRegActividadError("Gestor documental", ex);
			throw ex;
		}
	}

	public void eliminarDocumento(Documento documento) {
		documentoService.delete(documento);
		recargaLista();
	}
}
