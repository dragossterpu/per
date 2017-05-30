package es.mira.progesin.web.beans;

import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador de las operaciones relacionadas con el perfil. Cambiar contraseña.
 * 
 * @author EZENTIS
 */
@Getter
@Setter
@Controller("miPerfilBean")
@Scope("session")
public class MiPerfilBean {
    /**
     * Clave actual del usuario.
     */
    private String claveActual;
    
    /**
     * Clave nueva del usuario.
     */
    private String claveNueva;
    
    /**
     * Confirmación de la clave.
     */
    private String claveConfirm;
    
    /**
     * Usuario a mostrar.
     */
    @Autowired
    private UserBean user;
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    IUserService userService;
    
    /**
     * Método usado para que el usuario pueda cambiar su contraseña.
     */
    public void cambiarClave() {
        if (this.getClaveNueva().equals(this.getClaveConfirm()) == Boolean.FALSE) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "Las contraseñas introducidas no coinciden", "", null);
        } else {
            User usuario = user.getUser();
            // TODO Comprobar que cumple los siguientes criterios: como mínimo un carácter en mayúscula y un número,
            // permitiéndose cualquier carácter UTF-8, en cualquier posición.
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(this.getClaveActual(), usuario.getPassword())) {
                usuario.setPassword(passwordEncoder.encode(this.getClaveNueva()));
                userService.save(usuario);
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO,
                        "La contraseña ha sido modificada con éxito", "", null);
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                        "La contraseña actual introducida no es válida. Inténtelo de nuevo", "", null);
            }
        }
    }
}
