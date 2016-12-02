package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionarioId;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.services.ICuestionarioEnvioService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("responderCuestionarioBean")
@Scope("session")
public class ResponderCuestionarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	// @Autowired
	// private CuestionarioPersonalizadoBean cuestionarioPersBean;

	@Autowired
	private VisualizarCuestionario visualizarCuestionario;

	@Autowired
	private ICuestionarioEnvioService cuestionarioEnvioService;

	private RespuestaCuestionario respuestaCuestionario;

	private CuestionarioEnvio cuestionarioEnviado;

	@Autowired
	private transient IRespuestaCuestionarioRepository respuestaRepository;

	public void guardarRespuestas() {
		System.out.println("GUARDAR RESPUESTAS");
		Map<PreguntasCuestionario, String> mapaRespuestas = visualizarCuestionario.getMapaRespuestas();

		List<RespuestaCuestionario> listaRespuestas = new ArrayList<>();
		mapaRespuestas.forEach((pregunta, respuesta) -> {
			System.out.println(
					"pregunta: " + pregunta.getId() + " - " + pregunta.getPregunta() + ", respuesta: " + respuesta);
			respuestaCuestionario = new RespuestaCuestionario();
			RespuestaCuestionarioId idRespuesta = new RespuestaCuestionarioId();
			idRespuesta.setCuestionarioEnviado(cuestionarioEnviado);
			idRespuesta.setPregunta(pregunta);
			respuestaCuestionario.setRespuestaId(idRespuesta);
			respuestaCuestionario.setRespuestaTexto(respuesta);
			listaRespuestas.add(respuestaCuestionario);
		});
		respuestaRepository.save(listaRespuestas);
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
