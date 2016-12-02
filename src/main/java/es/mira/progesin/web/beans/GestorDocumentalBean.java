package es.mira.progesin.web.beans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.tika.exception.TikaException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

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
@Component("gestorDocumentalBean")
@SessionScoped
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

	public void cargaFichero(FileUploadEvent event) throws SQLException, IOException, TikaException, SAXException {
		alertaService.crearAlerta("Gestor documental", "cargaFichero", null, null);
		notificacionService.crearNotificacion("cargaFichero", "Prueba", "Gestor documental");
		registroActividadService.crearRegistroActividad("cargaFichero", EstadoRegActividadEnum.ALTA.name(), "Gestor documental");
		if (documentoService.extensionCorrecta(event.getFile())){
			documentoService.cargaDocumento(event.getFile());}
		else{
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Carga de ficheros",
					"La extensión del fichero '"+ event.getFile().getFileName()+"' no corresponde a su tipo real");
			FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
			context.execute("PF('dialogMessage').show()");
		}
		
		recargaLista();
	}

	public void eliminarDocumento(Documento documento) {
		documentoService.delete(documento);
		recargaLista();
	}
}
