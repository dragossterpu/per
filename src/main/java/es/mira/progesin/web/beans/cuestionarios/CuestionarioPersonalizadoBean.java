package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.services.IAreaCuestionarioService;
import es.mira.progesin.services.ICuestionarioPersonalizadoService;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author EZENTIS Esta clase contiene todos los métodos necesarios para el tratamiento de los cuestionarios creados a
 * partir de un modelo
 *
 */
@Setter
@Getter
@Component("cuestionarioPersonalizadoBean")
public class CuestionarioPersonalizadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private CuestionarioPersonalizadoBusqueda cuestionarioBusqueda;

	private List<CuestionarioPersonalizado> listaCuestionarioPersonalizado;

	// // para la visualización
	// private CuestionarioPersonalizado cuestionarioPersonalizado;
	//
	// private Map<AreasCuestionario, List<PreguntasCuestionario>> mapaAreaPreguntas;
	//
	// private List<AreasCuestionario> areas;

	@Autowired
	private transient ICuestionarioPersonalizadoService cuestionarioPersonalizadoService;

	@Autowired
	private transient IAreaCuestionarioService areaService;

	@Autowired
	private EnvioCuestionarioBean envioCuestionarioBean;

	// // Tipos de respuesta
	// private List<DatosTablaGenerica> listaTablaSalidas;
	//
	// private Map<PreguntasCuestionario, String> mapaRespuestas;
	//
	// private Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla;

	public void buscarCuestionario() {
		listaCuestionarioPersonalizado = cuestionarioPersonalizadoService
				.buscarCuestionarioPersonalizadoCriteria(cuestionarioBusqueda);
	}

	/**
	 * Resetea los valores de búsqueda introducidos en el formulario y el resultado de la búsqueda
	 */
	public void limpiar() {
		cuestionarioBusqueda.limpiar();
		listaCuestionarioPersonalizado = null;
	}

	/**
	 * Elimina un cuestionario
	 * @param cuestionario Cuestionario a eliminar
	 */
	public void eliminarCuestionario(CuestionarioPersonalizado cuestionario) {
		// TODO comprobar que no se ha usado para el envío antes de borrar
		cuestionarioPersonalizadoService.delete(cuestionario);
		listaCuestionarioPersonalizado.remove(cuestionario);
	}

	// /**
	// * Se muestra en pantalla el cuestionario personalizado, mostrando las diferentes opciones de responder (cajas de
	// * texto, adjuntos, tablas...)
	// *
	// * @param cuestionario que se desea visualizar
	// * @return Nombre de la vista a mostrar
	// */
	// public String visualizar(CuestionarioPersonalizado cuestionario) {
	// this.setCuestionarioPersonalizado(cuestionario);
	// List<PreguntasCuestionario> preguntas = cuestionario.getPreguntasElegidas();
	// // Agrupo las preguntas por areas para poder pintarlas agrupadas
	// mapaAreaPreguntas = new HashMap<>();
	// mapaRespuestasTabla = new HashMap<>();
	// List<PreguntasCuestionario> listaPreguntas;
	// for (PreguntasCuestionario pregunta : preguntas) {
	// listaPreguntas = mapaAreaPreguntas.get(pregunta.getArea());
	// if (listaPreguntas == null) {
	// listaPreguntas = new ArrayList<>();
	// }
	// listaPreguntas.add(pregunta);
	// mapaAreaPreguntas.put(pregunta.getArea(), listaPreguntas);
	// if (pregunta.getTipoRespuesta() != null && pregunta.getTipoRespuesta().startsWith("TABLA")) {
	// construirTipoRespuestaTabla(pregunta);
	// }
	// else if (pregunta.getTipoRespuesta() != null && pregunta.getTipoRespuesta().startsWith("MATRIZ")) {
	// construirTipoRespuestaMatriz(pregunta);
	// }
	// }
	//
	// Set<AreasCuestionario> areasSet = mapaAreaPreguntas.keySet();
	//
	// // JSF ui:repeat no funciona con Set
	// setAreas(new ArrayList<>(areasSet));
	//
	// // Ordeno las áreas por su id para que aparezcan en el mismo orden que en el modelo
	// Collections.sort(areas, (o1, o2) -> Long.compare(o1.getId(), o2.getId()));
	//
	// return "/cuestionarios/previsualizarEnvioCuestionario";
	// }

	/**
	 * mostrarFormularioEnvio
	 *
	 * Muestra la pantalla de envío del cuestionario
	 *
	 * @param cuestionario Cuestionario a enviar
	 * @return Nombre de la vista del formulario de envío
	 */
	public String mostrarFormularioEnvio(CuestionarioPersonalizado cuestionario) {
		CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
		cuestionarioEnvio.setCuestionarioPersonalizado(cuestionario);
		Inspeccion inspeccion = new Inspeccion();
		cuestionarioEnvio.setInspeccion(inspeccion);
		envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
		return "/cuestionarios/enviarCuestionario";
	}

	// /**
	// * getValoresTipoRespuesta
	// *
	// * Obtiene los valores asociados a un tipo de respuesta CHECKBOX o similar. Se usa dentro del xhtml para obtener
	// los
	// * valores a visualizar en pantalla.
	// *
	// * @param tipo Tipo de respuesta de la pregunta
	// * @return Lista de valores asociados al tipo de respuesta
	// */
	// public List<String> getValoresTipoRespuesta(String tipo) {
	// return configuracionRespuestaRepository.findValoresPorSeccion(tipo);
	// }
	//
	// /**
	// * Construye la tabla que se usará en el formulario para responder las preguntas cuyo tipo de respuesta empieza
	// por
	// * TABLA
	// *
	// * @see visualizar
	// * @param pregunta Pregunta del tipo respuesta TABLAxx
	// */
	// public void construirTipoRespuestaTabla(PreguntasCuestionario pregunta) {
	// DataTableView dataTableView = new DataTableView();
	// List<ConfiguracionRespuestasCuestionario> valoresColumnas = configuracionRespuestaRepository
	// .findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta());
	// dataTableView.crearTabla(valoresColumnas);
	// mapaRespuestasTabla.put(pregunta, dataTableView);
	// }
	//
	// /**
	// * Método usado para construir la tabla que se usará en el formulario para responder las preguntas cuyo tipo de
	// * respuesta empieza por MATRIZ
	// * @see visualizar
	// * @param pregunta Pregunta del tipo respuesta MATRIZxx
	// */
	// public void construirTipoRespuestaMatriz(PreguntasCuestionario pregunta) {
	// DataTableView dataTableView = new DataTableView();
	// List<ConfiguracionRespuestasCuestionario> valoresColumnas = configuracionRespuestaRepository
	// .findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta());
	// dataTableView.crearMatriz(valoresColumnas);
	// mapaRespuestasTabla.put(pregunta, dataTableView);
	// }
	//
	// /**
	// * Añade una fila nueva a la pregunta pasada como parámetro. El tipo de respuesta de esta pregunta siempre deberá
	// * empezar por TABLA
	// * @param pregunta Pregunta de un cuestionario
	// */
	// public void aniadirFilaRespuestaTabla(PreguntasCuestionario pregunta) {
	// if (mapaRespuestasTabla.get(pregunta) != null) {
	// DataTableView dataTableView = mapaRespuestasTabla.get(pregunta);
	// dataTableView.crearFilaVacia();
	// mapaRespuestasTabla.put(pregunta, dataTableView);
	// }
	// }
	//
	// /**
	// * Elimina una fila nueva a la pregunta pasada como parámetro. El tipo de respuesta de esta pregunta siempre
	// deberá
	// * empezar por TABLA
	// * @param pregunta Pregunta de un cuestionario
	// */
	// public void eliminarFilaRespuestaTabla(PreguntasCuestionario pregunta) {
	// if (mapaRespuestasTabla.get(pregunta) != null) {
	// DataTableView dataTableView = mapaRespuestasTabla.get(pregunta);
	// dataTableView.eliminarFila();
	// mapaRespuestasTabla.put(pregunta, dataTableView);
	// }
	// }
	//
	// public void handleFileUpload(FileUploadEvent event) {
	// System.out.println("upload file");
	// }

	@PostConstruct
	public void init() {
		cuestionarioBusqueda = new CuestionarioPersonalizadoBusqueda();
		// mapaRespuestas = new HashMap<>();
		// mapaRespuestasTabla = new HashMap<>();
	}

}
