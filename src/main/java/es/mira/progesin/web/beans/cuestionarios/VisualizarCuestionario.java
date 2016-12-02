package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
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

	private static final long serialVersionUID = 1L;

	// para la visualización
	private CuestionarioPersonalizado cuestionarioPersonalizado;

	private Map<AreasCuestionario, List<PreguntasCuestionario>> mapaAreaPreguntas;

	private List<AreasCuestionario> areas;

	// Tipos de respuesta
	private List<DatosTablaGenerica> listaTablaSalidas;

	private Map<PreguntasCuestionario, String> mapaRespuestas;

	private Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla;

	/**
	 * Muestra en pantalla el cuestionario personalizado, mostrando las diferentes opciones de responder (cajas de
	 * texto, adjuntos, tablas...)
	 * 
	 * @param cuestionario que se desea visualizar
	 * @return Nombre de la vista a mostrar
	 */
	public String visualizarVacio(CuestionarioPersonalizado cuestionario) {
		mapaRespuestasTabla = new HashMap<>();
		mapaRespuestas = new HashMap<>();
		return visualizar(cuestionario);
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
		mapaRespuestas = new HashMap<>();
		List<RespuestaCuestionario> listaRespuestas = respuestaRepository
				.findByRespuestaIdCuestionarioEnviadoAndRespuestaTextoNotNull(cuestionarioEnviado);
		mapaRespuestas = new HashMap<>();
		listaRespuestas.forEach(respuesta -> mapaRespuestas.put(respuesta.getRespuestaId().getPregunta(),
				respuesta.getRespuestaTexto()));
		return visualizar(cuestionarioEnviado.getCuestionarioPersonalizado());
	}

	private String visualizar(CuestionarioPersonalizado cuestionario) {
		mapaAreaPreguntas = new HashMap<>();
		this.setCuestionarioPersonalizado(cuestionario);
		List<PreguntasCuestionario> preguntas = cuestionario.getPreguntasElegidas();
		// Agrupo las preguntas por areas para poder pintarlas agrupadas
		List<PreguntasCuestionario> listaPreguntas;
		for (PreguntasCuestionario pregunta : preguntas) {
			listaPreguntas = mapaAreaPreguntas.get(pregunta.getArea());
			if (listaPreguntas == null) {
				listaPreguntas = new ArrayList<>();
			}
			listaPreguntas.add(pregunta);
			mapaAreaPreguntas.put(pregunta.getArea(), listaPreguntas);
			if (pregunta.getTipoRespuesta() != null && pregunta.getTipoRespuesta().startsWith("TABLA")) {
				construirTipoRespuestaTabla(pregunta);
			}
			else if (pregunta.getTipoRespuesta() != null && pregunta.getTipoRespuesta().startsWith("MATRIZ")) {
				construirTipoRespuestaMatriz(pregunta);
			}
		}

		Set<AreasCuestionario> areasSet = mapaAreaPreguntas.keySet();

		// JSF ui:repeat no funciona con Set
		setAreas(new ArrayList<>(areasSet));

		// Ordeno las áreas por su id para que aparezcan en el mismo orden que en el modelo
		Collections.sort(areas, (o1, o2) -> Long.compare(o1.getId(), o2.getId()));

		return "/cuestionarios/responderCuestionario";
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
	 * Construye la tabla que se usará en el formulario para responder las preguntas cuyo tipo de respuesta empieza por
	 * TABLA
	 * 
	 * @see visualizar
	 * @param pregunta Pregunta del tipo respuesta TABLAxx
	 */
	public void construirTipoRespuestaTabla(PreguntasCuestionario pregunta) {
		DataTableView dataTableView = new DataTableView();
		List<ConfiguracionRespuestasCuestionario> valoresColumnas = configuracionRespuestaRepository
				.findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta());
		dataTableView.crearTabla(valoresColumnas);
		mapaRespuestasTabla.put(pregunta, dataTableView);
	}

	/**
	 * Método usado para construir la tabla que se usará en el formulario para responder las preguntas cuyo tipo de
	 * respuesta empieza por MATRIZ
	 * @see visualizar
	 * @param pregunta Pregunta del tipo respuesta MATRIZxx
	 */
	public void construirTipoRespuestaMatriz(PreguntasCuestionario pregunta) {
		DataTableView dataTableView = new DataTableView();
		List<ConfiguracionRespuestasCuestionario> valoresColumnas = configuracionRespuestaRepository
				.findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta());
		dataTableView.crearMatriz(valoresColumnas);
		mapaRespuestasTabla.put(pregunta, dataTableView);
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
	 * Elimina una fila nueva a la pregunta pasada como parámetro. El tipo de respuesta de esta pregunta siempre deberá
	 * empezar por TABLA
	 * @param pregunta Pregunta de un cuestionario
	 */
	public void eliminarFilaRespuestaTabla(PreguntasCuestionario pregunta) {
		if (mapaRespuestasTabla.get(pregunta) != null) {
			DataTableView dataTableView = mapaRespuestasTabla.get(pregunta);
			dataTableView.eliminarFila();
			mapaRespuestasTabla.put(pregunta, dataTableView);
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		System.out.println("upload file");
	}

}
