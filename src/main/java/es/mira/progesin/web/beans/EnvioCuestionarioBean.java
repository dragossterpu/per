package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean asociado a la pantalla de envío de cuestionarios
 * @author EZENTIS
 *
 */
@Setter
@Getter
@Component("envioCuestionarioBean")
public class EnvioCuestionarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private CuestionarioEnvio cuestionarioEnvio;

	boolean enviar = false;

	@Autowired
	private IInspeccionesRepository inspeccionRepository;

	@Autowired
	private ISolicitudDocumentacionService solDocService;

	@Autowired
	private IRegistroActividadService regActividadService;

	@Autowired
	private IUserService userService;

	/**
	 * Método que devuelve una lista con las inspecciones cuyo número contienen alguno de los caracteres pasado como
	 * parámetro. Se usa en el formulario de envío para el autocompletado.
	 * 
	 * @param numeroInspeccion Número de inspección que teclea el usuario en el formulario de envío
	 * @return Devuelve la lista de inspecciones que contienen algún caracter coincidente con el número introducido
	 */
	public List<Inspeccion> autocompletarInspeccion(String numeroInspeccion) {
		return inspeccionRepository.findByNumeroLike("%" + numeroInspeccion + "%");
	}

	/**
	 * 
	 */
	public void enviarCuestionario() {
		try {
			if (enviar) {
				String password = Utilities.getPassword();
				userService.crearUsuarioProvisional(this.cuestionarioEnvio.getCorreoEnvio(), password,
						RoleEnum.PROV_CUESTIONARIO);
				// TODO enviar correo al usuario con la contraseña y el tiempo restante
			}
			else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
						"No se puede enviar el cuestionario ya que no existe documentación previa finalizada para la inspección. "
								+ "Debe finalizar la solicitud de documentación previa antes de poder enviar el cuestionario.");
				FacesContext.getCurrentInstance().addMessage("mensajeerror", message);
			}
		}
		catch (Exception e) {
			regActividadService.altaRegActivError("ENVIO CUESTIONARIO", e);
		}
	}

	public void completarDatosSolicitudPrevia() {
		try {
			SolicitudDocumentacionPrevia solDocPrevia = solDocService
					.findSolicitudDocumentacionFinalizadaPorInspeccion(this.cuestionarioEnvio.getInspeccion());
			if (solDocPrevia != null && solDocPrevia.getId() != null) {
				this.cuestionarioEnvio.setCorreoEnvio(solDocPrevia.getCorreoCorporativoInterlocutor());
				this.cuestionarioEnvio.setNombreUsuarioEnvio(solDocPrevia.getNombreCompletoInterlocutor());
				this.cuestionarioEnvio.setCargo(solDocPrevia.getCargoInterlocutor());
				enviar = true;
				// TODO poner fecha límite
			}
			// this.cuestionarioEnvio.setCorreoEnvio("correo@gmail.com");
			// this.cuestionarioEnvio.setNombreUsuarioEnvio("Pepito de los palotes");
			// this.cuestionarioEnvio.setCargo("Coronel");
		}
		catch (Exception e) {
			regActividadService.altaRegActivError("ENVIO CUESTIONARIO", e);
		}
	}

}
