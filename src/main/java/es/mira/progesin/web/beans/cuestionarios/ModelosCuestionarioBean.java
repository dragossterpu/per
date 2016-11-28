package es.mira.progesin.web.beans.cuestionarios;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		this.file= documentoService.descargaDocumento(cuestionario.getIdDocumento());
	}

}
