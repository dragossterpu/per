package es.mira.progesin.configuration.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IRegistroActividadService;

/**
 * Manejador de errores de autenticación en el login.
 * 
 * @author EZENTIS
 *
 */
@Component
public class AuthenticationFailureHandlerPersonalizado extends SimpleUrlAuthenticationFailureHandler {
    /**
     * Servicio del registro de actividad.
     */
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    /**
     * Controla el funcionamiento del sistema cuando se produce un error al hacer login.
     * 
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        String usuario = request.getParameter("username");
        
        StringBuilder textoReg = new StringBuilder("Se ha producido un intento de login fallido en el sistema\n\n");
        textoReg.append("Usuario implicado: ").append(usuario).append('\n');
        textoReg.append("Error detectado: ");
        if (exception.getMessage().contains("locked")) {
            textoReg.append("Cuenta bloqueada");
        } else if (exception.getMessage().contains("Bad credentials")) {
            textoReg.append("Usuario o contraseña incorrectos");
        } else if (exception.getMessage().contains("Maximum sessions")) {
            textoReg.append("Ya existe una sesión abierta para este usuario");
        }
        
        registroActividadService.altaRegActividad(textoReg.toString(), TipoRegistroEnum.AUDITORIA.name(),
                SeccionesEnum.LOGIN.getDescripcion());
        saveException(request, exception);
        getRedirectStrategy().sendRedirect(request, response, Constantes.RUTALOGIN);
        
    }
    
}
