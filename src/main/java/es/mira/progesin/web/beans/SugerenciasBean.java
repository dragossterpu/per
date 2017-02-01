package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.Sugerencia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISugerenciaService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.CorreoElectronico;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Controller("sugerenciasBean")
@Scope("session")
public class SugerenciasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String NOMBRESECCION = "Sugerencias de mejora";

	private static final String ERROR = "Error";

	private Sugerencia sugerencia;

	private List<Sugerencia> sugerenciasListado;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	private transient CorreoElectronico correo;

	@Autowired
	private transient ISugerenciaService sugerenciaService;

	@Autowired
	private transient IUserService userService;

	@Autowired
	private transient IRegistroActividadService regActividadService;

	public String guardarSugerencia(String modulo, String descripcion) {
		try {
			Sugerencia sugerenciaNueva = new Sugerencia();
			sugerenciaNueva.setModulo(modulo);
			sugerenciaNueva.setDescripcion(descripcion);
			sugerenciaService.save(sugerenciaNueva);
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
					"Sugerencia guardada con éxito, cuando sea atendida recibirá un correo electrónico con la contestación.");
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al guardar la sugerencia. Inténtelo de nuevo más tarde.");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
		return "/principal/crearSugerencia";

	}

	/**
	 * Método que nos lleva al listado de sugerencias Se llama desde el menu lateral
	 * @return
	 */
	public void sugerenciasListado() {
		sugerencia = null;
		sugerenciasListado = (List<Sugerencia>) sugerenciaService.findAll();
	}

	public void eliminarSugerencia(Sugerencia sugerenciaSeleccionada) {
		try {
			sugerenciaService.delete(sugerenciaSeleccionada.getIdSugerencia());
			sugerenciasListado.remove(sugerenciaSeleccionada);
			FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Eliminación",
					"Se ha eliminado con éxito la sugerencia.", "msgs");
		}
		catch (Exception e) {
			FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al eliminar la sugerencia. Inténtelo de nuevo más tarde.", "msgs");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
	}

	public String contestarSugerencia(Sugerencia sugerenciaSeleccionada) {
		sugerencia = sugerenciaSeleccionada;
		return "/principal/contestarSugerencia";
	}

	public String contestar(Sugerencia sugerenciaSeleccionada, String contestacion) {
		try {
			String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
			Date fecha = new Date();
			sugerenciaSeleccionada.setFechaContestacion(fecha);
			sugerenciaSeleccionada.setUsuarioContestacion(usuarioActual);
			if (sugerenciaService.save(sugerenciaSeleccionada) != null) {
				String asunto = "Respuesta a su sugerencia sobre PROGESIN";
				String usuarioContestacion = sugerenciaSeleccionada.getUsuarioRegistro();
				User user = userService.findOne(usuarioContestacion);
				String fechaRegistro = sdf.format(sugerenciaSeleccionada.getFechaRegistro());
				StringBuilder mensaje = new StringBuilder("Sugerencia realizada el ").append(fechaRegistro)
						.append(" sobre el módulo ").append(sugerencia.getModulo()).append("\r\n \"")
						.append(sugerencia.getDescripcion()).append("\" \r\n \r\n").append(contestacion);
				correo.envioCorreo(user.getCorreo(), asunto, mensaje.toString());
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Contestación",
						"Mensaje de respuesta enviado correctamente.");
			}
		}
		catch (MailException e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al enviar el correo electrónico.");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al contestar la sugerencia. Inténtelo de nuevo más tarde.");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
		return "/principal/contestarSugerencia";
	}

	@PostConstruct
	public void init() {

		sugerenciasListado = (List<Sugerencia>) sugerenciaService.findAll();

	}

}
