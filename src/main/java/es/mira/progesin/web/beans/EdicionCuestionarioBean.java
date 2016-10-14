package es.mira.progesin.web.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.AreasCuestionario;
import es.mira.progesin.persistence.entities.ModeloCuestionario;
import es.mira.progesin.persistence.entities.PreguntasCuestionario;
import es.mira.progesin.persistence.repositories.IAreaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("edicionCuestionarioBean")
@RequestScoped
public class EdicionCuestionarioBean {
	String nombreCuestionario;

	List<AreasCuestionario> listaAreasCuestionario;

	private Map<AreasCuestionario, PreguntasCuestionario[]> preguntasSelecciondas;

	@Autowired
	private IAreaCuestionarioRepository areaCuestionarioRepository;

	@Autowired
	private IPreguntaCuestionarioRepository pregunaCuestionarioRepository;

	public String editarCuestionario(ModeloCuestionario modeloCuestionario) {
		nombreCuestionario = modeloCuestionario.getDescripcion();
		preguntasSelecciondas = new HashMap<>();
		listaAreasCuestionario = areaCuestionarioRepository.findByIdCuestionario(modeloCuestionario.getId());
		return "/cuestionarios/editarCuestionario";
	}

	public List<PreguntasCuestionario> getPreguntasArea(Long idArea) {
		return pregunaCuestionarioRepository.findByIdArea(idArea);
	}

	public String previsualizarFormulario() {
		boolean hayPreguntasSeleccionadas = false;
		String page;
		int cont = 0;
		while (hayPreguntasSeleccionadas == Boolean.FALSE && cont < listaAreasCuestionario.size()) {
			Object[] preg = preguntasSelecciondas.get(listaAreasCuestionario.get(cont));
			if (preg.length > 0) {
				hayPreguntasSeleccionadas = true;
			}
			cont++;
		}
		// for (AreasCuestionario area : listaAreasCuestionario) {
		// Object[] preg = preguntasSelecciondas.get(area);
		// if (preg.length > 0) {
		// System.err.println(preg[0]);
		// PreguntasCuestionario pc = (PreguntasCuestionario) preg[0];
		// System.err.println("....." + pc.getPregunta());
		// hayPreguntasSeleccionadas = true;
		// }
		// }

		if (hayPreguntasSeleccionadas) {
			page = "/cuestionarios/previsualizarCuestionario";
		}
		else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Debe seleccionar al menos una pregunta", "");
			FacesContext.getCurrentInstance().addMessage("message", message);
			page = null;
		}
		return page;
	}
}
