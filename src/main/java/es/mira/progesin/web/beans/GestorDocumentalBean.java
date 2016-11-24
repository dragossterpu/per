package es.mira.progesin.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.Documento;
import es.mira.progesin.services.IDocumentoService;
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

	public void cargaFichero(FileUploadEvent event) {

		documentoService.cargaDocumento(event.getFile());
		recargaLista();
	}

	public void eliminarDocumento(Documento documento) {
		documentoService.delete(documento);
		recargaLista();
	}
}
