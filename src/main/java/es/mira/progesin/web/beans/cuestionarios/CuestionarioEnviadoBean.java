package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.services.IAreaCuestionarioService;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author EZENTIS Esta clase contiene todos los métodos necesarios para el tratamiento de los cuestionarios enviados a
 * partir de un cuestionario personalizado
 *
 */
@Setter
@Getter
@Component("cuestionarioEnviadoBean")
public class CuestionarioEnviadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private transient IRespuestaCuestionarioRepository respuestaRepository;

	@Autowired
	private CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda;

	@Autowired
	private VisualizarCuestionario visualizarCuestionario;

	private List<CuestionarioEnvio> listaCuestionarioEnvio;

	private String vieneDe;

	private String motivosNoConforme;

	@Autowired
	private transient ICuestionarioEnvioService cuestionarioEnvioService;

	@Autowired
	private transient IAreaCuestionarioService areaService;

	@Autowired
	private EnvioCuestionarioBean envioCuestionarioBean;

	public void buscarCuestionario() {
		listaCuestionarioEnvio = cuestionarioEnvioService
				.buscarCuestionarioEnviadoCriteria(cuestionarioEnviadoBusqueda);
	}

	/**
	 * Devuelve al formulario de búsqueda de modelos de cuestionario a su estado inicial y borra los resultados de
	 * búsquedas anteriores si se navega desde el menú u otra sección.
	 * 
	 * @author EZENTIS
	 */
	public void getFormBusquedaCuestionarios() {

		if ("menu".equalsIgnoreCase(this.vieneDe)) {
			limpiar();
			this.vieneDe = null;
		}

	}

	/**
	 * Resetea los valores de búsqueda introducidos en el formulario y el resultado de la búsqueda
	 */
	public void limpiar() {

		cuestionarioEnviadoBusqueda.limpiar();
		listaCuestionarioEnvio = null;

	}

	/**
	 * Elimina un cuestionario
	 * @param cuestionario Cuestionario a eliminar
	 */
	public void eliminarCuestionario(CuestionarioEnvio cuestionario) {
		// TODO comprobar que no se ha usado para el envío antes de borrar
		cuestionario.setUsernameAnulacion(SecurityContextHolder.getContext().getAuthentication().getName());
		cuestionario.setFechaAnulacion(new Date());
		cuestionario.setFechaFinalizacion(cuestionario.getFechaAnulacion());
		cuestionarioEnvioService.save(cuestionario);
	}

	@PostConstruct
	public void init() {
		cuestionarioEnviadoBusqueda.limpiar();
		listaCuestionarioEnvio = new ArrayList<>();
	}

	/**
	 * Guarda fecha validación de las respuestas de las preguntas
	 * @see guardarRespuestas
	 *
	 */
	public void validarRespuestas() {
		try {
			Date fechaValidacion = new Date();
			CuestionarioEnvio cuestionario = visualizarCuestionario.getCuestionarioEnviado();
			List<RespuestaCuestionario> listaRespuestasTotales = visualizarCuestionario.getListaRespuestas();
			List<RespuestaCuestionario> listaRespuestasValidadas = new ArrayList<>();
			Map<PreguntasCuestionario, Boolean> mapaValidacionRespuestas = visualizarCuestionario
					.getMapaValidacionRespuestas();
			for (RespuestaCuestionario respuesta : listaRespuestasTotales) {
				if (mapaValidacionRespuestas.get(respuesta.getRespuestaId().getPregunta())) {

					respuesta.setFechaValidacion(fechaValidacion);
					listaRespuestasValidadas.add(respuesta);
				}
			}
			if (listaRespuestasValidadas.isEmpty() == Boolean.FALSE) {
				respuestaRepository.save(listaRespuestasValidadas);
				respuestaRepository.flush();
			}

			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Validación",
					"Se ha validado con éxito las respuestas");
			if (listaRespuestasValidadas.size() == listaRespuestasTotales.size()) {
				cuestionario.setFechaFinalizacion(new Date());
				cuestionarioEnvioService.transaccSaveElimUsuariosProv(cuestionario);
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Finalización",
						"Cuestionario finalizado con éxito, todas sus respuestas han sido validadas");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR,
					"Se ha producido un error al validar las respuestas. ", e.getMessage());
			// TODO registro actividad
		}

	}

}
