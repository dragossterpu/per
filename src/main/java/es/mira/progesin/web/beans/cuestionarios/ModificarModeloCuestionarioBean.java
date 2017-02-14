package es.mira.progesin.web.beans.cuestionarios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIInput;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionarioId;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TiposRespuestasPersonalizables;
import es.mira.progesin.persistence.repositories.IAreaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Controller("modificarModeloCuestionarioBean")
@Scope("session")
public class ModificarModeloCuestionarioBean {

	ModeloCuestionario modeloCuestionario;

	List<AreasCuestionario> listaAreasCuestionario;

	List<AreasCuestionario> listaAreasCuestionarioAux;

	List<String> listaTipoPreguntas;

	private List<String> listaTipoPreguntasFinal;

	private DualListModel<String> listaTipoPreguntasPick;

	@Autowired
	IModeloCuestionarioService modeloCuestionarioService;

	@Autowired
	INotificacionService notificacionesService;

	@Autowired
	IRegistroActividadService registroActividadService;

	@Autowired
	IConfiguracionRespuestasCuestionarioRepository configuracionRespuestasCuestionarioRepository;

	// Nuevo area
	UIInput nombreArea;

	AreasCuestionario areaSelec = new AreasCuestionario();

	// Nueva pregunta
	UIInput textoPregunta;

	UIInput nuevoTipoPregunta;

	PreguntasCuestionario preguntaSelec;

	// Nuevo tipo pregunta
	private String tipoPersonalizado = "";

	private List<TiposRespuestasPersonalizables> listaTiposPersonalizables;

	private List<String> listadoValoresNuevaRespuesta;

	private List<String> listadoValoresFila;

	private UIInput nuevoValor;

	private UIInput nuevoValorRadio;

	private UIInput nuevoValorTabla;

	private UIInput nuevoValorFila;

	private UIInput preguntaNueva;

	private UIInput nuevaRespuesta;

	private UIInput nombreTipoPregunta;

	@Autowired
	private IAreaCuestionarioRepository areaCuestionarioRepository;

	public String editarModelo(ModeloCuestionario modeloCuestionario) {
		this.modeloCuestionario = modeloCuestionario;
		listaAreasCuestionario = areaCuestionarioRepository
				.findDistinctByIdCuestionarioOrderByOrdenAsc(modeloCuestionario.getId());
		listaTipoPreguntas = configuracionRespuestasCuestionarioRepository
				.findAllDistinctTipoRespuestaOrderByTipoRespuestaAsc();
		listaAreasCuestionarioAux = areaCuestionarioRepository
				.findDistinctByIdCuestionarioOrderByOrdenAsc(modeloCuestionario.getId());

		listaTipoPreguntasFinal = new ArrayList<String>();

		listaTipoPreguntasPick = new DualListModel<String>(listaTipoPreguntas, listaTipoPreguntasFinal);

		listaTiposPersonalizables = Arrays.asList(TiposRespuestasPersonalizables.values());
		listadoValoresNuevaRespuesta = new ArrayList<String>();
		listadoValoresFila = new ArrayList<String>();

		return "/cuestionarios/modificarModeloCuestionario";
	}

	public String nuevoModelo() {
		this.modeloCuestionario = new ModeloCuestionario();

		modeloCuestionario.setCodigo("");
		listaAreasCuestionario = new ArrayList<AreasCuestionario>();
		listaTipoPreguntas = configuracionRespuestasCuestionarioRepository
				.findAllDistinctTipoRespuestaOrderByTipoRespuestaAsc();
		listaAreasCuestionarioAux = new ArrayList<AreasCuestionario>();

		listaTipoPreguntasFinal = new ArrayList<String>();

		listaTipoPreguntasPick = new DualListModel<String>(listaTipoPreguntas, listaTipoPreguntasFinal);

		return "/cuestionarios/nuevoModeloCuestionario";
	}

	// public void aniadeArea() {
	// AreasCuestionario areaAux = new AreasCuestionario();
	// areaAux.setArea(nombreArea.getLocalValue().toString());
	// areaAux.setIdCuestionario(modeloCuestionario.getId());
	// areaAux.setPreguntas(new ArrayList<PreguntasCuestionario>());
	// listaAreasCuestionario.add(areaAux);
	// listaAreasCuestionarioAux.add(areaAux);
	// }

	public void aniadeArea(String nombreArea) {
		AreasCuestionario areaAux = new AreasCuestionario();
		areaAux.setArea(nombreArea);
		areaAux.setIdCuestionario(modeloCuestionario.getId());
		areaAux.setPreguntas(new ArrayList<PreguntasCuestionario>());
		listaAreasCuestionario.add(areaAux);
		listaAreasCuestionarioAux.add(areaAux);
	}

	public void onSelectArea(SelectEvent event) {
		areaSelec = (AreasCuestionario) event.getObject();
		nombreArea.setValue(areaSelec.getArea());
	}

	public void borraArea() {
		String prueba = areaSelec.getArea();
		areaSelec.setArea(prueba);
		listaAreasCuestionarioAux.remove(areaSelec);
		listaAreasCuestionario.remove(areaSelec);
	}

	public void aniadePregunta(AreasCuestionario area) {
		PreguntasCuestionario preguntaAux = new PreguntasCuestionario();
		preguntaAux.setArea(area);
		preguntaAux.setPregunta(textoPregunta.getLocalValue().toString());
		preguntaAux.setTipoRespuesta(nuevoTipoPregunta.getLocalValue().toString());
		List<PreguntasCuestionario> listado = area.getPreguntas();
		listado.add(preguntaAux);
		area.setPreguntas(listado);
	}

	public void onSelectPregunta(SelectEvent event) {
		preguntaSelec = (PreguntasCuestionario) event.getObject();
		textoPregunta.setValue(preguntaSelec.getPregunta());
		nuevoTipoPregunta.setValue(preguntaSelec.getTipoRespuesta());
	}

	public void borraPregunta(AreasCuestionario area) {
		List<PreguntasCuestionario> listado = area.getPreguntas();
		listado.remove(preguntaSelec);
		area.setPreguntas(listado);
	}

	public String onFlowProcess(FlowEvent event) {
		if ("areas".equals(event.getNewStep())) {

		}
		if ("preguntas".equals(event.getNewStep())) {
			listaAreasCuestionario = ordenarAreas(listaAreasCuestionarioAux);
			modeloCuestionario.setAreas(listaAreasCuestionario);
		}
		if ("finalizar".equals(event.getNewStep())) {
			for (AreasCuestionario area : listaAreasCuestionario) {
				List<PreguntasCuestionario> lista = new ArrayList<PreguntasCuestionario>();
				lista = ordenarPreguntas(area.getPreguntas());
				area.setPreguntas(lista);
			}

		}

		return event.getNewStep();
	}

	public List<String> getValoresTipoRespuesta(String tipo) {
		return configuracionRespuestasCuestionarioRepository.findValoresPorSeccion(tipo);
	}

	public void aniadeValor() {
		listadoValoresNuevaRespuesta.add(nuevoValor.getLocalValue().toString());
	}

	public void borraValor() {
		listadoValoresNuevaRespuesta.remove(nuevoValor.getLocalValue().toString());
	}

	public void onSelectValor(SelectEvent event) {
		String nuevo = event.getObject().toString();
		nuevoValor.setValue(nuevo);
	}

	public void aniadeValorRadio() {
		listadoValoresNuevaRespuesta.add(nuevoValorRadio.getLocalValue().toString());
	}

	public void borraValorRadio() {
		listadoValoresNuevaRespuesta.remove(nuevoValorRadio.getLocalValue().toString());
	}

	public void onSelectValorRadio(SelectEvent event) {
		String nuevo = event.getObject().toString();
		nuevoValorRadio.setValue(nuevo);
	}

	public void aniadeValorTabla() {
		listadoValoresNuevaRespuesta.add(nuevoValorTabla.getLocalValue().toString());
	}

	public void borraValorTabla() {
		listadoValoresNuevaRespuesta.remove(nuevoValorTabla.getLocalValue().toString());
	}

	public void onSelectValorTabla(SelectEvent event) {
		String nuevo = event.getObject().toString();
		nuevoValorTabla.setValue(nuevo);
	}

	public void aniadeValorFila() {
		listadoValoresFila.add(nuevoValorFila.getLocalValue().toString());
	}

	public void borraValorFila() {
		listadoValoresFila.remove(nuevoValorFila.getLocalValue().toString());
	}

	public void onSelectValorFila(SelectEvent event) {
		String nuevo = event.getObject().toString();
		nuevoValorFila.setValue(nuevo);
	}

	public void guardaTipoRespuesta() {

		// String seccion=tipoPersonalizado.concat(listadoValoresNuevaRespuesta.toString());

		String seccion = tipoPersonalizado.concat(nombreTipoPregunta.getLocalValue().toString());

		for (int i = 0; i < listadoValoresNuevaRespuesta.size(); i++) {
			String valor = listadoValoresNuevaRespuesta.get(i);
			ConfiguracionRespuestasCuestionario nuevoValor = new ConfiguracionRespuestasCuestionario();
			ConfiguracionRespuestasCuestionarioId datos = new ConfiguracionRespuestasCuestionarioId();

			switch (tipoPersonalizado) {
			case "RADIO":
				datos.setSeccion(seccion);
				datos.setValor(valor);
				datos.setClave(valor);
				break;
			default:
				datos.setSeccion(seccion);
				datos.setValor(valor);
				datos.setClave("campo" + (i + 1));
				break;
			}
			nuevoValor.setConfig(datos);
			configuracionRespuestasCuestionarioRepository.save(nuevoValor);

		}

		// Valores de fila para Matriz

		if (tipoPersonalizado.equals("MATRIZ")) {
			for (int i = 0; i < listadoValoresFila.size(); i++) {
				String valor = listadoValoresFila.get(i);
				ConfiguracionRespuestasCuestionario nuevoValor = new ConfiguracionRespuestasCuestionario();
				ConfiguracionRespuestasCuestionarioId datos = new ConfiguracionRespuestasCuestionarioId();
				datos.setSeccion(seccion);
				datos.setValor(valor);
				datos.setClave("nombreFila");
				nuevoValor.setConfig(datos);
				configuracionRespuestasCuestionarioRepository.save(nuevoValor);
			}
		}

		// Actualizar lista
		List<String> nuevoListado = configuracionRespuestasCuestionarioRepository
				.findAllDistinctTipoRespuestaOrderByTipoRespuestaAsc();

		listaTipoPreguntasPick.setSource(nuevoListado);

		listadoValoresNuevaRespuesta = new ArrayList<String>();
		listadoValoresFila = new ArrayList<String>();
	}

	public void guardaCuestionario() {

		modeloCuestionario.setNombreFichero("");
		modeloCuestionario.setIdDocumento((long) 0);

		try {
			if (modeloCuestionarioService.save(modeloCuestionario) != null) {
				notificacionesService.crearNotificacionRol(
						"Se modifica el modelo de cuestionario ".concat(modeloCuestionario.getDescripcion()),
						SeccionesEnum.CUESTIONARIO.getDescripcion(), RoleEnum.ADMIN);
				registroActividadService.altaRegActividad(
						"Se modifica el modelo de cuestionario :".concat(modeloCuestionario.getDescripcion()),
						EstadoRegActividadEnum.ALTA.name(), SeccionesEnum.CUESTIONARIO.getDescripcion());
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('dialogCierre').show();");

			}
		}
		catch (Exception e) {
			registroActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
		}
	}

	public void guardaNuevoCuestionario() {
		modeloCuestionario.setNombreFichero("");
		modeloCuestionario.setIdDocumento((long) 0);
		try {
			if (modeloCuestionarioService.save(modeloCuestionario) != null) {
				notificacionesService.crearNotificacionRol(
						"Se crea el modelo de cuestionario ".concat(modeloCuestionario.getDescripcion()),
						SeccionesEnum.CUESTIONARIO.getDescripcion(), RoleEnum.ADMIN);
				registroActividadService.altaRegActividad(
						"Se crea el modelo de cuestionario :".concat(modeloCuestionario.getDescripcion()),
						EstadoRegActividadEnum.ALTA.name(), SeccionesEnum.CUESTIONARIO.getDescripcion());
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('dialogCierre').show();");
			}
		}
		catch (Exception e) {
			registroActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
		}
	}

	public List<AreasCuestionario> ordenarAreas(List<AreasCuestionario> listado) {
		List<AreasCuestionario> listaNueva = new ArrayList<AreasCuestionario>();
		for (int i = 0; i < listado.size(); i++) {
			AreasCuestionario area = listado.get(i);
			area.setOrden(i);
			listaNueva.add(area);
		}
		return listaNueva;
	}

	public List<PreguntasCuestionario> ordenarPreguntas(List<PreguntasCuestionario> listado) {
		List<PreguntasCuestionario> listaNueva = new ArrayList<PreguntasCuestionario>();
		for (int i = 0; i < listado.size(); i++) {
			PreguntasCuestionario pregunta = listado.get(i);
			pregunta.setOrden(i);
			listaNueva.add(pregunta);
		}
		return listaNueva;
	}
}
