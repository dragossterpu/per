package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.services.IAreaCuestionarioService;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.ICuestionarioPersonalizadoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
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
@Controller("cuestionarioPersonalizadoBean")
@Scope("session")
public class CuestionarioPersonalizadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String NOMBRESECCION = "Gestión de cuestionario personalizado";

	private static final String ERROR = "Error";

	private CuestionarioPersonalizadoBusqueda cuestionarioBusqueda;

	private List<CuestionarioPersonalizado> listaCuestionarioPersonalizado;

	private String vieneDe;

	@Autowired
	private transient ICuestionarioPersonalizadoService cuestionarioPersonalizadoService;

	@Autowired
	private transient ICuestionarioEnvioService cuestionarioEnvioService;

	@Autowired
	private transient IAreaCuestionarioService areaService;

	@Autowired
	private EnvioCuestionarioBean envioCuestionarioBean;

	@Autowired
	transient IRegistroActividadService regActividadService;

	public void buscarCuestionario() {
		listaCuestionarioPersonalizado = cuestionarioPersonalizadoService
				.buscarCuestionarioPersonalizadoCriteria(cuestionarioBusqueda);
	}

	/**
	 * Devuelve al formulario de búsqueda de modelos de cuestionario a su estado inicial y borra los resultados de
	 * búsquedas anteriores si se navega desde el menú u otra sección.
	 * 
	 * @author EZENTIS
	 */
	public void getFormBusquedaModelosCuestionario() {

		if ("menu".equalsIgnoreCase(this.vieneDe)) {
			limpiar();
			this.vieneDe = null;
		}

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
		try {
			if (cuestionarioEnvioService.findByCuestionarioPersonalizado(cuestionario) != null) {
				cuestionario.setFechaBaja(new Date());
				cuestionario.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
				cuestionarioPersonalizadoService.save(cuestionario);
			}
			else {
				cuestionarioPersonalizadoService.delete(cuestionario);
			}
			listaCuestionarioPersonalizado.remove(cuestionario);
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Eliminación",
					"Cuestionario personalizado eliminado con éxito");
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al eliminar el cuestionario personalizado, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}

	}

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

	@PostConstruct
	public void init() {
		cuestionarioBusqueda = new CuestionarioPersonalizadoBusqueda();
	}

}
