package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
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
	private transient IInspeccionesService inspeccionService;

	@Autowired
	private transient ISolicitudDocumentacionService solDocService;

	@Autowired
	private transient IRegistroActividadService regActividadService;

	@Autowired
	private transient IUserService userService;

	@Autowired
	private transient PasswordEncoder passwordEncoder;

	@Autowired
	private ICuestionarioEnvioService cuestionarioEnvioService;

	/**
	 * Método que devuelve una lista con las inspecciones cuyo número contienen alguno de los caracteres pasado como
	 * parámetro. Se usa en el formulario de envío para el autocompletado.
	 * 
	 * @param numeroInspeccion Número de inspección que teclea el usuario en el formulario de envío
	 * @return Devuelve la lista de inspecciones que contienen algún caracter coincidente con el número introducido
	 */
	public List<Inspeccion> autocompletarInspeccion(String nombreUnidad) {
		// TODO añadir que no tenga ya un cuestionario sin finalizar
		return inspeccionService.findByNombreUnidadLikeIgnoringCaseAndFechaFinalizacionNull("%" + nombreUnidad + "%");
	}

	/**
	 * Completa los datos del formulario (correo, nombre, cargo, fecha límite) si el tipo de inspección asociada es de
	 * tipo General Periódica y tiene una solicitud de documentación previa finalizada.
	 */
	public void completarDatosSolicitudPrevia() {
		try {
			if ("I.G.P.".equals(cuestionarioEnvio.getInspeccion().getTipoInspeccion().getCodigo())) {
				List<SolicitudDocumentacionPrevia> listaSolicitudes = solDocService
						.findSolicitudDocumentacionFinalizadaPorInspeccion(this.cuestionarioEnvio.getInspeccion());
				if (listaSolicitudes != null && listaSolicitudes.isEmpty() == Boolean.FALSE
						&& listaSolicitudes.get(0) != null) {
					// Como está ordenado en orden descendente por fecha finalización, recupero la más reciente
					SolicitudDocumentacionPrevia solDocPrevia = listaSolicitudes.get(0);
					this.cuestionarioEnvio.setCorreoEnvio(solDocPrevia.getCorreoCorporativoInterlocutor());
					this.cuestionarioEnvio.setNombreUsuarioEnvio(solDocPrevia.getNombreCompletoInterlocutor());
					this.cuestionarioEnvio.setCargo(solDocPrevia.getCargoInterlocutor());
					this.cuestionarioEnvio.setFechaLimiteCuestionario(solDocPrevia.getFechaLimiteCumplimentar());
					enviar = true;
				}
				else {
					mostrarMensajeNoDocumentacionPrevia();
				}
			}
			else {
				enviar = true;
			}
		}
		catch (Exception e) {
			regActividadService.altaRegActivError("ENVIO CUESTIONARIO", e);
		}
	}

	/**
	 * Método que guarda los datos introducidos en el formulario de envío en BBDD.Además crea un usuario provisional
	 * para el destinatario del correo 1 y le envía un correo electrónico informando de la URL de acceso a la aplicación
	 * y su contraseña.
	 */
	public void enviarCuestionario() {
		try {
			if (enviar) {
				String password = Utilities.getPassword();
				System.out.println(password);
				User user = new User(cuestionarioEnvio.getCorreoEnvio(), passwordEncoder.encode(password),
						RoleEnum.PROV_CUESTIONARIO);
				cuestionarioEnvioService.enviarCuestionarioService(user, cuestionarioEnvio);
				String asunto = "Cuestionario para la inspección " + cuestionarioEnvio.getInspeccion().getNumero();
				String textoAutomatico = "Para responder el cuestionario debe conectarse a la aplicación PROGESIN. El enlace de acceso a la "
						+ "aplicación es xxxxxxx, su usuario de acceso es su correo electrónico y la contraseña es "
						+ password + "\r\n. Una vez enviado el cuestionario su usuario quedará inativo. \r\n"
						+ "Muchas gracias y un saludo.";
				String cuerpo = cuestionarioEnvio.getMotivoCuestionario().concat(textoAutomatico);
				// TODO enviar correo
				// envioCorreo(cuestionarioEnvio.getCorreoEnvio(), asunto, cuerpo);
				// TODO crear notificación
			}
			else {
				mostrarMensajeNoDocumentacionPrevia();
			}
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "",
					"El cuestionario se ha enviado con éxito");
		}
		catch (Exception e) {
			FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
					"Se ha produdico un error en el envio del cuestionario", e.getMessage(), "mensajeerror");
			regActividadService.altaRegActivError("ENVIO CUESTIONARIO", e);
		}
	}

	/**
	 * Muestra en el formulario un mensaje de error indiciando que no existe documentación previa finalizada para la
	 * inspección introducida
	 */
	private void mostrarMensajeNoDocumentacionPrevia() {
		String mensaje = "No se puede enviar el cuestionario ya que no existe documentación previa finalizada para la inspección. "
				+ "Debe finalizar la solicitud de documentación previa antes de poder enviar el cuestionario.";
		FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "", mensaje, "mensajeerror");
	}
}
