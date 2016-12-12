package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionarioId;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IDatosTablaGenericaRepository;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.util.DataTableView;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("responderCuestionarioBean")
@Scope("session")
public class ResponderCuestionarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private VisualizarCuestionario visualizarCuestionario;

	@Autowired
	private ICuestionarioEnvioService cuestionarioEnvioService;

	private CuestionarioEnvio cuestionarioEnviado;

	@Autowired
	private transient IRespuestaCuestionarioRepository respuestaRepository;

	@Autowired
	private transient IDatosTablaGenericaRepository datosTablaRepository;

	// private List<RespuestaCuestionario> listaRespuestas;

	public void guardarBorrador() {
		try {
			guardarRespuestasTipoTexto();
			guardarRespuestasTipoTablaMatriz();
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Borrador",
					"El borrador se ha guardado con éxito");
		}
		catch (Exception e) {
			FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
					"Se ha producido un error al guardar las respuestas", e.getMessage(), "mensajeerror");
			// TODO registro actividad
		}
	}

	/**
	 * @see guardarRespuestas
	 * @param listaRespuestas Lista donde se guardarán todas las respuestas del cuestionario
	 */
	private void guardarRespuestasTipoTexto() {
		List<RespuestaCuestionario> listaRespuestas = new ArrayList<>();
		Map<PreguntasCuestionario, String> mapaRespuestas = visualizarCuestionario.getMapaRespuestas();
		mapaRespuestas.forEach((pregunta, respuesta) -> {
			if (respuesta != null) {
				System.out.println(
						"pregunta: " + pregunta.getId() + " - " + pregunta.getPregunta() + ", respuesta: " + respuesta);
				RespuestaCuestionario respuestaCuestionario = new RespuestaCuestionario();
				RespuestaCuestionarioId idRespuesta = new RespuestaCuestionarioId();
				idRespuesta.setCuestionarioEnviado(cuestionarioEnviado);
				idRespuesta.setPregunta(pregunta);
				respuestaCuestionario.setRespuestaId(idRespuesta);
				respuestaCuestionario.setRespuestaTexto(respuesta);
				listaRespuestas.add(respuestaCuestionario);
			}
		});
		if (listaRespuestas.isEmpty() == Boolean.FALSE) {
			respuestaRepository.save(listaRespuestas);
		}
	}

	/**
	 * Guarda en BBDD las respuestas de tipo TABLA o MATRIZ
	 * @see guardarRespuestas
	 * @param listaRespuestas
	 */
	private void guardarRespuestasTipoTablaMatriz() {
		List<RespuestaCuestionario> listaRespuestas = new ArrayList<>();
		Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla = visualizarCuestionario.getMapaRespuestasTabla();
		List<DatosTablaGenerica> listaDatosTablaSave = new ArrayList<>();

		mapaRespuestasTabla.forEach((pregunta, respuesta) -> {
			if (respuesta != null) {
				List<DatosTablaGenerica> listaDatosTabla = respuesta.getListaDatosTabla();
				RespuestaCuestionario rtaCuestionario = new RespuestaCuestionario();
				RespuestaCuestionarioId idRespuesta = new RespuestaCuestionarioId();
				idRespuesta.setCuestionarioEnviado(cuestionarioEnviado);
				idRespuesta.setPregunta(pregunta);
				rtaCuestionario.setRespuestaId(idRespuesta);
				for (int i = 0; i < listaDatosTabla.size(); i++) {
					DatosTablaGenerica datosTablaGenerica = listaDatosTabla.get(i);
					// Si no estaba ya en la respuesta
					if (datosTablaGenerica.getId() == null) {
						RespuestaCuestionario respuestaCuestionarioTabla = new RespuestaCuestionario();
						respuestaCuestionarioTabla.setRespuestaId(idRespuesta);
						datosTablaGenerica.setRespuesta(respuestaCuestionarioTabla);
						listaDatosTabla.set(i, datosTablaGenerica);
					}
				}
				rtaCuestionario.setRespuestaTablaMatriz(listaDatosTabla);
				listaRespuestas.add(rtaCuestionario);
				listaDatosTablaSave.addAll(listaDatosTabla);
			}
		});
		respuestaRepository.save(listaRespuestas);
		datosTablaRepository.save(listaDatosTablaSave);
	}

	/**
	 * Elimina una fila nueva a la pregunta pasada como parámetro. El tipo de respuesta de esta pregunta siempre deberá
	 * empezar por TABLA
	 * @param pregunta Pregunta de un cuestionario
	 */
	public void eliminarFilaRespuestaTabla(PreguntasCuestionario pregunta) {
		Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla = visualizarCuestionario.getMapaRespuestasTabla();
		if (mapaRespuestasTabla.get(pregunta) != null) {
			DataTableView dataTableView = mapaRespuestasTabla.get(pregunta);
			DatosTablaGenerica datoTablaEliminar = dataTableView.eliminarFila();
			if (datoTablaEliminar.getId() != null) {
				datosTablaRepository.delete(datoTablaEliminar);
			}
			mapaRespuestasTabla.put(pregunta, dataTableView);
			visualizarCuestionario.setMapaRespuestasTabla(mapaRespuestasTabla);
		}
	}

	@PostConstruct
	public void init() {
		System.out.println("INICIALIZANDO RESPUESTA......");
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (RoleEnum.PROV_CUESTIONARIO.equals(user.getRole())) {
			cuestionarioEnviado = cuestionarioEnvioService
					.findByCorreoEnvioAndFechaFinalizacionIsNull(user.getUsername());
			visualizarCuestionario.visualizarRespuestasCuestionario(cuestionarioEnviado);
		}
	}
}
