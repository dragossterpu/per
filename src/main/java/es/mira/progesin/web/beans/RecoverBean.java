package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.CorreoElectronico;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Controller("recoverBean")
@Scope("request")
public class RecoverBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String NOMBRESECCION = "Clave olvidada";

	private static final String ERROR = "Error";

	private static final String MENSAJES = "msgs";

	private String correo;

	private String nif;

	@Autowired
	private transient IUserService userService;

	@Autowired
	private transient IRegistroActividadService regActividadService;

	@Autowired
	private transient CorreoElectronico correoElectronico;

	@Autowired
	private transient PasswordEncoder passwordEncoder;

	private List<User> listaUsers = new ArrayList<>();

	public String claveOlvidada() {

		if ("".equals(nif) && "".equals(correo)) {
			FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, ERROR,
					"Introduzca un parametro para recuperar la contraseña o el usuario", MENSAJES);
			return null;
		}
		else {
			User user = userService.findByParams(correo, nif);
			if (user != null) {
				try {
					String password = Utilities.getPassword();
					user.setPassword(passwordEncoder.encode(password));
					correoElectronico.envioCorreo(user.getCorreo(), "Reestablecido acceso a la herramienta PROGESIN",
							"Se le ha asignado una nueva contraseña, sus credenciales son " + user.getUsername() + " / "
									+ password);
					userService.save(user);
					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Clave",
							"Se ha reestablecido su acceso al sistema, se le han enviado sus nuevos credenciales por correo electrónico");
					String descripcion = "Reestablecida clave del usuario " + user.getUsername();
					regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
							NOMBRESECCION);
				}
				catch (Exception e) {
					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
							"Se ha producido un error en la regeneración o envío de la contraseña");
					regActividadService.altaRegActividadError(NOMBRESECCION, e);
				}

				return "/acceso/recuperarPassword";
			}
			else {
				FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, ERROR,
						"No existe el usuario en el sistema. Contacte con el administrador", MENSAJES);
				return null;
			}

		}
	}

}
