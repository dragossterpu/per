package es.mira.progesin.web.beans.cuestionarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.repositories.IAreaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.services.ICuestionarioPersonalizadoService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("edicionCuestionarioBean")
@RequestScoped
public class EdicionCuestionarioBean {
	// String nombreCuestionario;

	ModeloCuestionario modeloCuestionario;

	List<AreasCuestionario> listaAreasCuestionario;

	private Map<AreasCuestionario, PreguntasCuestionario[]> preguntasSelecciondas;

	private String nombreCuestionario = "";

	@Autowired
	private IAreaCuestionarioRepository areaCuestionarioRepository;

	@Autowired
	private IPreguntaCuestionarioRepository pregunaCuestionarioRepository;

	@Autowired
	private ICuestionarioPersonalizadoService cuestionarioPersonalizadoService;

	public String editarCuestionario(ModeloCuestionario modeloCuestionario) {
		this.modeloCuestionario = modeloCuestionario;
		preguntasSelecciondas = new HashMap<>();
		listaAreasCuestionario = areaCuestionarioRepository.findByIdCuestionario(modeloCuestionario.getId());
		return "/cuestionarios/editarCuestionario";
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

	public void guardarFormulario() {
		try {
			RequestContext.getCurrentInstance().execute("PF('cuestionarioDialog').hide()");
			CuestionarioPersonalizado cp = new CuestionarioPersonalizado();
			cp.setModeloCuestionario(modeloCuestionario);
			cp.setNombreCuestionario(nombreCuestionario);
			List<PreguntasCuestionario> preguntasElegidas = new ArrayList<>();
			for (AreasCuestionario area : listaAreasCuestionario) {
				Object[] preg = preguntasSelecciondas.get(area);
				for (int i = 0; i < preg.length; i++) {
					PreguntasCuestionario pc = (PreguntasCuestionario) preg[i];
					preguntasElegidas.add(pc);
				}
			}
			cp.setPreguntasElegidas(preguntasElegidas);
			cuestionarioPersonalizadoService.save(cp);
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Cuestionario",
					"Se ha guardado su cuestionario con éxito");
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR",
					"Se ha producido un error al guardar el cuestionario");
			e.printStackTrace();
		}
	}

}