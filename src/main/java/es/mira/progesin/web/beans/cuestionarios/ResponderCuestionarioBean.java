package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.ICuestionarioPersonalizadoService;
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

	@Autowired
	private transient ICuestionarioPersonalizadoService cuestionarioPersService;

	@PostConstruct
	public void init() {
		System.out.println("INICIALIZANDO RESPUESTA......");
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (RoleEnum.PROV_CUESTIONARIO.equals(user.getRole())) {
			CuestionarioEnvio cuestionario = cuestionarioEnvioService
					.findByCorreoEnvioAndFechaFinalizacionIsNull(user.getUsername());
			visualizarCuestionario.visualizarVacio(cuestionario.getCuestionarioPersonalizado());
		}
	}
}
