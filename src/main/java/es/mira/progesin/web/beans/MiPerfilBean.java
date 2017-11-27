package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.jsf.scope.FacesViewScope;
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
@Scope(FacesViewScope.NAME)
public class MiPerfilBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
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
    private User user;
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private IUserService userService;
    
    /**
     * Constante patrón de contraseña.
     */
    private static final String PASSPATTERN = "^(?=.*?[A-Z])(?=.*?[0-9]).{2,}$";
    
    /**
     * Método usado para que el usuario pueda cambiar su contraseña.
     */
    public void cambiarClave() {
        if (this.getClaveNueva().equals(this.getClaveConfirm()) == Boolean.FALSE) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "Las contraseñas introducidas no coinciden", "", null);
        } else {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(this.getClaveActual(), user.getPassword())) {
                if (validaPass(this.claveNueva)) {
                    user.setPassword(passwordEncoder.encode(this.getClaveNueva()));
                    userService.save(user);
                    FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO,
                            "La contraseña ha sido modificada con éxito", "", "dialogMessage");
                } else {
                    FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                            "La nueva contraseña al menos debe tener un número y una letra mayúscula. Inténtelo de nuevo",
                            "", null);
                }
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                        "La contraseña actual introducida no es válida. Inténtelo de nuevo", "", null);
            }
        }
        
    }
    
    /**
     * Método qué que indica si una cadena cumple un ptrón determinado.
     * @param password String a validar
     * @return ¿Válida?
     */
    private boolean validaPass(String password) {
        Pattern pattern = Pattern.compile(PASSPATTERN);
        Matcher matcher = pattern.matcher(password);
        
        return matcher.matches();
    }
    
    /**
     * Inicilización del usuario a mostrar en pantalla.
     */
    @PostConstruct
    public void init() {
        user = userService.findOne((String) SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
