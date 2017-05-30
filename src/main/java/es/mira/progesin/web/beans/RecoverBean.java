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
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.CorreoElectronico;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Gestiona la restauración del acceso al sistema por parte de un usuario que no recuerde su alias o contraseña.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
@Controller("recoverBean")
@Scope("request")
public class RecoverBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constante con el literal "Clave olvidada".
     */
    private static final String NOMBRESECCION = "Clave olvidada";
    
    /**
     * Constante con el literal "Error".
     */
    private static final String ERROR = "Error";
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private transient IUserService userService;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Envío de correos electrónicos.
     */
    @Autowired
    private transient CorreoElectronico correoElectronico;
    
    /**
     * Encriptador de palabra clave.
     */
    @Autowired
    private transient PasswordEncoder passwordEncoder;
    
    /**
     * Lista de usuarios.
     */
    private List<User> listaUsers = new ArrayList<>();
    
    /**
     * Busca un usuario en base a su dirección de correo o al NIF, y en caso de hayarlo genera una nueva contraseña y
     * envía ésta junto al username al correo asociado a la cuenta.
     * 
     * @author EZENTIS
     * @param correo electrónico
     * @param nif documento de identidad
     * @return vista recuperarPassword
     */
    public String claveOlvidada(String correo, String nif) {
        
        if ("".equals(nif) && "".equals(correo)) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, ERROR,
                    "Debe proporcionar su correo electrónico o NIF para identificar su usuario", null);
            return null;
        } else {
            User user = userService.findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase(correo, nif);
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
                    regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                            NOMBRESECCION);
                } catch (Exception e) {
                    FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
                            "Se ha producido un error en la regeneración o envío de la contraseña");
                    regActividadService.altaRegActividadError(NOMBRESECCION, e);
                }
                
                return "/acceso/recuperarPassword";
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, ERROR,
                        "No existe el usuario en el sistema. Contacte con el administrador", null);
                return null;
            }
            
        }
    }
    
}
