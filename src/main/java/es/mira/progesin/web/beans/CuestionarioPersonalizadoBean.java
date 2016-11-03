package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.PreguntasCuestionario;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.services.IAreaCuestionarioService;
import es.mira.progesin.services.ICuestionarioPersonalizadoService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("cuestionarioPersonalizadoBean")
@RequestScoped
public class CuestionarioPersonalizadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private CuestionarioPersonalizadoBusqueda cuestionarioBusqueda;

	private List<CuestionarioPersonalizado> listaCuestionarioPersonalizado;

	// para la visualización
	private CuestionarioPersonalizado cuestionarioPersonalizado;

	private Map<Long, List<PreguntasCuestionario>> mapaAreaPreguntas;

	private List<Long> areas;

	@Autowired
	ICuestionarioPersonalizadoService cuestionarioPersonalizadoService;

	@Autowired
	IAreaCuestionarioService areaService;

	@Autowired
	IConfiguracionRespuestasCuestionarioRepository configuracionRespuestaRepository;

	// Tipos de respuesta
	private List<DatosTablaGenerica> listaTablaSalidas;

	private Map<PreguntasCuestionario, Object> mapaRespuestas;

	private Map<PreguntasCuestionario, DatosTablaGenerica> mapaRespuestasTabla;

	public void buscarCuestionario() {
		listaCuestionarioPersonalizado = cuestionarioPersonalizadoService
				.buscarCuestionarioPersonalizadoCriteria(cuestionarioBusqueda);
	}

	public void limpiar() {
		cuestionarioBusqueda.limpiar();
		listaCuestionarioPersonalizado = null;
	}

	public void eliminarCuestionario(CuestionarioPersonalizado cuestionario) {
		// TODO comprobar que no se ha usado para el envío antes de borrar
		cuestionarioPersonalizadoService.delete(cuestionario);
		listaCuestionarioPersonalizado.remove(cuestionario);
	}

	public String visualizar(CuestionarioPersonalizado cuestionario) {
		this.setCuestionarioPersonalizado(cuestionario);
		List<PreguntasCuestionario> preguntas = cuestionario.getPreguntasElegidas();
		// Agrupo las preguntas por areas para poder pintarlas agrupadas
		mapaAreaPreguntas = new HashMap<>();
		List<PreguntasCuestionario> listaPreguntas;
		for (PreguntasCuestionario pregunta : preguntas) {
			listaPreguntas = mapaAreaPreguntas.get(pregunta.getIdArea());
			if (listaPreguntas != null) {
				listaPreguntas.add(pregunta);
				mapaAreaPreguntas.put(pregunta.getIdArea(), listaPreguntas);
			}
			else {
				listaPreguntas = new ArrayList<>();
				listaPreguntas.add(pregunta);
				mapaAreaPreguntas.put(pregunta.getIdArea(), listaPreguntas);
			}
		}
		Set<Long> areasSet = mapaAreaPreguntas.keySet();
		// JSF ui:repeat no funciona con Set
		setAreas(new ArrayList<>(areasSet));

		return "/cuestionarios/previsualizarEnvioCuestionario";
	}

	public String enviar(CuestionarioPersonalizado cuestionario) {
		System.out.println("enviar");
		return null;
	}

	public String getNombreArea(Long idArea) {
		return areaService.getNombreArea(idArea);
	}

	public List<String> getValoresTipoRespuesta(String tipo) {
		return configuracionRespuestaRepository.findValuesForKey(tipo);
	}

	/************************************* Métodos para responder al cuestionario ***********************************/
	public String responderCuestinario() {
		String pagina = null;
		// TODO Esto es para probar, hay que cambiarlo y que busque el cuestionario asociado al username logado.
		List<CuestionarioPersonalizado> cp = (List<CuestionarioPersonalizado>) cuestionarioPersonalizadoService
				.findAll();
		if (cp != null && cp.isEmpty() == Boolean.FALSE) {
			pagina = visualizar(cp.get(0));
		}
		return pagina;
	}

	public void aniadirFila(PreguntasCuestionario pregunta) {
		if ("TABLASALIDAS".equals(pregunta.getTipoRespuesta())) {
			if (listaTablaSalidas == null) {
				listaTablaSalidas = new ArrayList<>();
			}
			DatosTablaGenerica datos = new DatosTablaGenerica();
			// datos.setMeses("5");
			// datos.setMotivos("motivos");
			// datos.setNumSalidas("6");
			// datos.setSexo("M");
			listaTablaSalidas.add(datos);
			mapaRespuestas.put(pregunta, listaTablaSalidas);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		System.out.println("upload file");
	}

	public void guardarRespuestas() {
		System.out.println("guardar respuestas");
		System.out.println(mapaRespuestas);
	}

	@PostConstruct
	public void init() {
		cuestionarioBusqueda = new CuestionarioPersonalizadoBusqueda();
		listaTablaSalidas = new ArrayList<>();
		mapaRespuestas = new HashMap<>();
		mapaRespuestasTabla = new HashMap<>();
	}

}
