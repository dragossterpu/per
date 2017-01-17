package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.services.IParametroService;
import es.mira.progesin.services.IPuestoTrabajoService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
public class ApplicationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private transient IPuestoTrabajoService puestosTrabajoService;

	@Autowired
	private transient IParametroService parametroService;

	// Los cargo en la aplicación porque van a ser siempre los mismo y así agilizo la aplicación
	private List<PuestoTrabajo> listaPuestosTrabajo;

	private Map<String, Map<String, String>> mapaParametros;

	private String dominiosValidos;

	@PostConstruct
	public void init() {
		setListaPuestosTrabajo((List<PuestoTrabajo>) puestosTrabajoService.findAll());
		setMapaParametros(parametroService.getMapaParametros());
		setDominiosValidos(mapaParametros.get("dominiosCorreo").get("dominiosCorreo"));

	}
}
