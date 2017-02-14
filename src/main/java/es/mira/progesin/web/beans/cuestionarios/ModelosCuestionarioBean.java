package es.mira.progesin.web.beans.cuestionarios;

import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.services.IRegistroActividadService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Controller("modelosCuestionarioBean")
@Scope("session")
public class ModelosCuestionarioBean {

	private List<ModeloCuestionario> listadoCuestionarios;

	private StreamedContent file;

	@Autowired
	private IModeloCuestionarioService modeloCuestionarioService;

	@Autowired
	private IDocumentoService documentoService;

	@Autowired
	private IRegistroActividadService regActividadService;

	@PostConstruct
	public void init() {
		setListadoCuestionarios((List<ModeloCuestionario>) modeloCuestionarioService.findAll());
	}

	public void list() {
		setListadoCuestionarios((List<ModeloCuestionario>) modeloCuestionarioService.findAll());
	}

}
