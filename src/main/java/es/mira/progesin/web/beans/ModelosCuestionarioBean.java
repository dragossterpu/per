package es.mira.progesin.web.beans;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.Documento;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IModeloCuestionarioService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("modelosCuestionarioBean")
public class ModelosCuestionarioBean {

	private List<ModeloCuestionario> listadoCuestionarios;

	private StreamedContent file;

	@Autowired
	private IModeloCuestionarioService modeloCuestionarioService;

	@Autowired
	private IDocumentoService documentoService;

	@PostConstruct
	public void init() {
		listadoCuestionarios = (List<ModeloCuestionario>) modeloCuestionarioService.findAll();
	}

	public void descargarFichero(ModeloCuestionario cuestionario) {
		try {
			Documento documento = documentoService.findOne(cuestionario.getIdDocumento());
			if (documento != null) {
				InputStream stream = new ByteArrayInputStream(documento.getFichero());
				String contentType = "application/msword";
				// if ("pdf".equals(cuestionario.getExtension())) {
				// contentType = "application/pdf";
				// }
				// else if (cuestionario.getExtension().startsWith("xls")) {
				// contentType = "application/x-msexcel";
				// }
				this.file = new DefaultStreamedContent(stream, contentType, cuestionario.getNombreFichero());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
