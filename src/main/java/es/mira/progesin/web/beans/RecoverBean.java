package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.CorreoException;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
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
     * Servicio de usuarios.
     */
    @Autowired
    private transient IUserService userService;
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private transient ApplicationBean applicationBean;
    
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
     * Busca un usuario en base a su dirección de correo o al NIF, y en caso de hayarlo genera una nueva contraseña y
     * envía ésta junto al username al correo asociado a la cuenta.
     * 
     * @author EZENTIS
     * @param correo electrónico
     * @param nif documento de identidad
     * @return vista recuperarPassword
     */
    public String claveOlvidada(String correo, String nif) {
        String retorno = null;
        if ("".equals(nif) && "".equals(correo)) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Debe proporcionar su correo electrónico o NIF para identificar su usuario", null);
        } else {
            // Recuperamos una lista de usuarios ya que pueden existir varios con el mismo email en el caso de los
            // usuarios provisionales
            List<User> listaUser = userService.findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase(correo, nif);
            
            if (listaUser != null && !listaUser.isEmpty()) {
                try {
                    String password = Utilities.getPassword();
                    String passCodificada = passwordEncoder.encode(password);
                    Map<String, String> paramPlantilla = new HashMap<>();
                    paramPlantilla.put("enlaceProgesin",
                            applicationBean.getMapaParametros().get("URLPROGESIN").get("PN"));
                    paramPlantilla.put("password", password);
                    listaUser.forEach((User user) -> {
                        user.setPassword(passCodificada);
                        if (listaUser.size() > 1) {
                            paramPlantilla.put("login", user.getCorreo());
                        } else {
                            paramPlantilla.put("login", user.getUsername());
                        }
                    });
                    userService.save(listaUser);
                    
                    correoElectronico.envioCorreo(listaUser.get(0).getCorreo(),
                            "Reestablecido acceso aplicación software, Programa de Gestión de Inspecciones “PROGESIN”.",
                            Constantes.TEMPLATECORREORESTABLECERPASSWORD, paramPlantilla);
                    
                    FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Clave",
                            "Se ha reestablecido su acceso al sistema, se le han enviado sus nuevos credenciales por correo electrónico");
                    String descripcion = "Reestablecida clave del usuario " + paramPlantilla.get("login");
                    regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                            SeccionesEnum.CLAVE_OLVIDADA.getDescripcion());
                } catch (DataAccessException | CorreoException e) {
                    FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                            "Se ha producido un error en la regeneración o envío de la contraseña");
                    regActividadService.altaRegActividadError(SeccionesEnum.CLAVE_OLVIDADA.getDescripcion(), e);
                }
                
                retorno = "/acceso/recuperarPassword";
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                        "No existe el usuario en el sistema. Contacte con el administrador", null);
            }
            
        }
        return retorno;
    }
    
}
