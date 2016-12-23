package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.services.IAreaCuestionarioService;
import es.mira.progesin.services.ICuestionarioEnvioService;
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
	private CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda;

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

	/**
	 * mostrarFormularioEnvio
	 *
	 * Muestra la pantalla de envío del cuestionario
	 *
	 * @param cuestionario Cuestionario a enviar
	 * @return Nombre de la vista del formulario de envío
	 */
	public String mostrarFormularioEnvio(CuestionarioEnvio cuestionario) {
		envioCuestionarioBean.setCuestionarioEnvio(cuestionario);
		return "/cuestionarios/enviarCuestionario";
	}

	@PostConstruct
	public void init() {
		cuestionarioEnviadoBusqueda.limpiar();
		listaCuestionarioEnvio = new ArrayList<>();
	}

}
