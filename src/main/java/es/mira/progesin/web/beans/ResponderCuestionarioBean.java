package es.mira.progesin.web.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.services.ICuestionarioPersonalizadoService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("responderCuestionarioBean")
@Scope("session")
public class ResponderCuestionarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private CuestionarioPersonalizadoBean cuestionarioPersBean;

	private String titulo = "";

	@Autowired
	private transient ICuestionarioPersonalizadoService cuestionarioPersService;

	@PostConstruct
	public void init() {
		System.out.println("INICIALIZANDO RESPUESTA......");
		// cuestionarioPersBean = new CuestionarioPersonalizadoBean();
		CuestionarioPersonalizado cuestionario = cuestionarioPersService.findOne(10L);
		cuestionarioPersBean.visualizar(cuestionario);
	}
}
