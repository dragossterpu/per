package es.mira.progesin.web.beans;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.services.ICuestionarioPersonalizadoService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("responderCuestionarioBean")
@Scope(FacesViewScope.NAME)
public class ResponderCuestionarioBean {

	@Autowired
	ICuestionarioPersonalizadoService cuestionarioPersonalizadoService;

	private Map<PreguntasCuestionario, Object> mapaRespuestas;

	private CuestionarioPersonalizado cuestionarioPersonalizado;

	private Map<Long, List<PreguntasCuestionario>> mapaAreaPreguntas;

	private List<Long> areas;

	@PostConstruct
	public void init() {
		// TODO Esto es para probar, hay que cambiarlo y que busque el cuestionario asociado al username logado.
		List<CuestionarioPersonalizado> cp = (List<CuestionarioPersonalizado>) cuestionarioPersonalizadoService
				.findAll();
		// if (cp != null && cp.isEmpty() == Boolean.FALSE) {
		// CuestionarioPersonalizado cuestionario = cp.get(0);
		// this.setCuestionarioPersonalizado(cuestionario);
		// List<PreguntasCuestionario> preguntas = cuestionario.getPreguntasElegidas();
		// // Agrupo las preguntas por areas para poder pintarlas agrupadas
		// mapaAreaPreguntas = new HashMap<>();
		// List<PreguntasCuestionario> listaPreguntas;
		// for (PreguntasCuestionario pregunta : preguntas) {
		// listaPreguntas = mapaAreaPreguntas.get(pregunta.getIdArea());
		// if (listaPreguntas != null) {
		// listaPreguntas.add(pregunta);
		// mapaAreaPreguntas.put(pregunta.getIdArea(), listaPreguntas);
		// }
		// else {
		// listaPreguntas = new ArrayList<>();
		// listaPreguntas.add(pregunta);
		// mapaAreaPreguntas.put(pregunta.getIdArea(), listaPreguntas);
		// }
		// }
		// Set<Long> areasSet = mapaAreaPreguntas.keySet();
		// // JSF ui:repeat no funciona con Set
		// setAreas(new ArrayList<>(areasSet));
		// }
	}
}
