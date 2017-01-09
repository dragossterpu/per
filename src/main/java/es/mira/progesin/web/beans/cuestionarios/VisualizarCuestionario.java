package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.Documento;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IDatosTablaGenericaRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.util.DataTableView;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean para la visualización de los cuestionarios por pantalla
 * 
 * @author EZENTIS
 *
 */
@Setter
@Getter
@Component("visualizarCuestionario")
public class VisualizarCuestionario implements Serializable {

	@Autowired
	private transient IConfiguracionRespuestasCuestionarioRepository configuracionRespuestaRepository;

	@Autowired
	private transient IRespuestaCuestionarioRepository respuestaRepository;

	@Autowired
	private transient IDatosTablaGenericaRepository datosTablaRepository;

	@Autowired
	private transient IPreguntaCuestionarioRepository preguntasRepository;

	private static final long serialVersionUID = 1L;

	// para la visualización
	private CuestionarioEnvio cuestionarioEnviado;

	// para la visualización
	private CuestionarioPersonalizado cuestionarioPersonalizado;

	private Map<AreasCuestionario, List<PreguntasCuestionario>> mapaAreaPreguntas;

	private List<AreasCuestionario> areas;

	private Map<AreasCuestionario, Boolean> mapaValidacionAreas;

	private Map<PreguntasCuestionario, Boolean> mapaValidacionRespuestas;

	private Map<PreguntasCuestionario, String> mapaRespuestas;

	private Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla;

	HashMap<PreguntasCuestionario, List<DatosTablaGenerica>> mapaRespuestasTablaAux;

	private Map<PreguntasCuestionario, List<Documento>> mapaDocumentos;

	private List<RespuestaCuestionario> listaRespuestas;

	/**
	 * Muestra en pantalla el cuestionario personalizado, mostrando las diferentes opciones de responder (cajas de
	 * texto, adjuntos, tablas...)
	 * 
	 * @param cuestionario que se desea visualizar
	 * @return Nombre de la vista a mostrar
	 */
	public String visualizarVacio(CuestionarioPersonalizado cuestionario) {
		cuestionarioEnviado = null;
		mapaRespuestasTabla = new HashMap<>();
		mapaRespuestas = new HashMap<>();
		mapaDocumentos = new HashMap<>();
		return visualizar(cuestionario, false);
	}

	/**
	 * visualizarRespuestasCuestionario
	 * 
	 * Muestra en pantalla el cuestionario con las respuestas de la unidad inspeccionada
	 * 
	 * @param cuestionarioEnviado
	 * @return
	 */
	public String visualizarRespuestasCuestionario(CuestionarioEnvio cuestionarioEnviado) {
		this.setCuestionarioEnviado(cuestionarioEnviado);
		mapaRespuestas = new HashMap<>();
		mapaRespuestasTabla = new HashMap<>();
		mapaDocumentos = new HashMap<>();
		mapaRespuestasTablaAux = new HashMap<>();
		mapaValidacionAreas = new HashMap<>();
		mapaValidacionRespuestas = new HashMap<>();
		listaRespuestas = respuestaRepository.findByRespuestaIdCuestionarioEnviado(cuestionarioEnviado);
		listaRespuestas.forEach(respuesta -> {
			String tipoRespuesta = respuesta.getRespuestaId().getPregunta().getTipoRespuesta();
			if ((tipoRespuesta.startsWith("TABLA") || tipoRespuesta.startsWith("MATRIZ"))
					&& respuesta.getRespuestaTablaMatriz() != null) {
				mapaRespuestasTablaAux.put(respuesta.getRespuestaId().getPregunta(),
						respuesta.getRespuestaTablaMatriz());
			}
			else {
				mapaRespuestas.put(respuesta.getRespuestaId().getPregunta(), respuesta.getRespuestaTexto());
				if (respuesta.getDocumentos() != null && respuesta.getDocumentos().isEmpty() == Boolean.FALSE) {
					mapaDocumentos.put(respuesta.getRespuestaId().getPregunta(), respuesta.getDocumentos());
				}
			}
			mapaValidacionAreas.putIfAbsent(respuesta.getRespuestaId().getPregunta().getArea(), true);
			mapaValidacionRespuestas.put(respuesta.getRespuestaId().getPregunta(),
					respuesta.getFechaValidacion() != null);
			if (respuesta.getFechaValidacion() == null) {
				mapaValidacionAreas.replace(respuesta.getRespuestaId().getPregunta().getArea(), false);
			}
		});

		return visualizar(cuestionarioEnviado.getCuestionarioPersonalizado(), true);
	}

	private String visualizar(CuestionarioPersonalizado cuestionario, boolean visualizarRespuestas) {
		setMapaAreaPreguntas(new HashMap<>());
		this.setCuestionarioPersonalizado(cuestionario);

		List<PreguntasCuestionario> preguntas = preguntasRepository
				.findPreguntasElegidasCuestionarioPersonalizado(cuestionario.getId());
		// Agrupo las preguntas por areas para poder pintarlas agrupadas
		List<PreguntasCuestionario> listaPreguntas;

		for (PreguntasCuestionario pregunta : preguntas) {
			listaPreguntas = mapaAreaPreguntas.get(pregunta.getArea());
			if (listaPreguntas == null) {
				listaPreguntas = new ArrayList<>();
			}
			listaPreguntas.add(pregunta);
			mapaAreaPreguntas.put(pregunta.getArea(), listaPreguntas);
			if (pregunta.getTipoRespuesta() != null && (pregunta.getTipoRespuesta().startsWith("TABLA")
					|| pregunta.getTipoRespuesta().startsWith("MATRIZ"))) {
				construirTipoRespuestaTablaMatrizVacia(pregunta);
			}
		}

		/******************************************************/
		if (visualizarRespuestas) {
			construirTipoRespuestaTablaMatrizConDatos();
		}

		/******************************************************/
		Set<AreasCuestionario> areasSet = mapaAreaPreguntas.keySet();

		// JSF ui:repeat no funciona con Set
		setAreas(new ArrayList<>(areasSet));

		// Ordeno las áreas por su id para que aparezcan en el mismo orden que en el modelo
		Collections.sort(areas, (o1, o2) -> Long.compare(o1.getId(), o2.getId()));

		if (cuestionarioEnviado == null) {
			return "/cuestionarios/responderCuestionario";
		}
		else {
			return "/cuestionarios/validarCuestionario";
		}
	}

	/**
	 * getValoresTipoRespuesta
	 * 
	 * Obtiene los valores asociados a un tipo de respuesta CHECKBOX o similar. Se usa dentro del xhtml para obtener los
	 * valores a visualizar en pantalla.
	 * 
	 * @param tipo Tipo de respuesta de la pregunta
	 * @return Lista de valores asociados al tipo de respuesta
	 */
	public List<String> getValoresTipoRespuesta(String tipo) {
		return configuracionRespuestaRepository.findValoresPorSeccion(tipo);
	}

	/**
	 * Construye la tabla o matriz que se usará en el formulario para responder las preguntas cuyo tipo de respuesta
	 * empieza por TABLA o MATRIZ
	 * 
	 * @see visualizar
	 * @param pregunta Pregunta del tipo respuesta que empiezan por TABLA o MATRIZ
	 */
	private void construirTipoRespuestaTablaMatrizVacia(PreguntasCuestionario pregunta) {
		DataTableView dataTableView = new DataTableView();
		List<ConfiguracionRespuestasCuestionario> valoresColumnas = configuracionRespuestaRepository
				.findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta());
		if (pregunta.getTipoRespuesta() != null && pregunta.getTipoRespuesta().startsWith("TABLA")) {
			dataTableView.crearTabla(valoresColumnas);
		}
		else {
			dataTableView.crearMatriz(valoresColumnas);
		}
		mapaRespuestasTabla.put(pregunta, dataTableView);
	}

	/**
	 * Construye la tabla o matriz que se usará del cuestionario con las respuestas cumplimentadas
	 * 
	 * @see visualizar
	 * @param pregunta Pregunta del tipo respuesta que empiezan por TABLA o MATRIZ
	 */
	private void construirTipoRespuestaTablaMatrizConDatos() {
		mapaRespuestasTablaAux.forEach((pregunta, listaDatos) -> {
			DataTableView dataTableView = mapaRespuestasTabla.get(pregunta);
			dataTableView.crearTablaMatriConDatos(listaDatos);
			mapaRespuestasTabla.put(pregunta, dataTableView);
		});
	}

	/**
	 * Añade una fila nueva a la pregunta pasada como parámetro. El tipo de respuesta de esta pregunta siempre deberá
	 * empezar por TABLA
	 * @param pregunta Pregunta de un cuestionario
	 */
	public void aniadirFilaRespuestaTabla(PreguntasCuestionario pregunta) {
		if (mapaRespuestasTabla.get(pregunta) != null) {
			DataTableView dataTableView = mapaRespuestasTabla.get(pregunta);
			dataTableView.crearFilaVacia();
			mapaRespuestasTabla.put(pregunta, dataTableView);
		}
	}

	/**
	 * Comprueba si el usuario logueado es el jefe del equipo encargado de la inspeccion.
	 * 
	 * @author EZENTIS
	 * @return result booleano
	 */
	public boolean esJefeEquipoInspeccion() {
		String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
		String jefeEquipoInspeccion = cuestionarioEnviado.getInspeccion().getEquipo().getJefeEquipo();
		return usuarioActual.equals(jefeEquipoInspeccion);
	}

}
