package es.mira.progesin.configuration.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IRegistroActividadService;

/**
 * Clase que gestiona los accesos exitosos a la aplicación. Registra en acceso para auditoría y redirige a la vista
 * Index.
 * 
 * @author EZENTIS
 *
 */
@Component
public class AuthenticationSuccessHandlerPersonalizado implements AuthenticationSuccessHandler {
    /**
     * Servicio del registro de actividad.
     */
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    /**
     * Auditoría de inicios de sesión satisfactorios.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication autentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        String textoReg = "El usuario " + autentication.getName() + " ha iniciado sesión en la aplicación";
        registroActividadService.altaRegActividad(textoReg, TipoRegistroEnum.AUDITORIA.name(),
                SeccionesEnum.LOGIN.getDescripcion());
        
        User usuario = (User) autentication.getPrincipal();
        
        switch (usuario.getRole()) {
            case ROLE_PROV_CUESTIONARIO:
                response.sendRedirect("./cuestionarios/responderCuestionario.xhtml");
                break;
            case ROLE_PROV_SOLICITUD:
                response.sendRedirect("./provisionalSolicitud/provisionalSolicitud.xhtml");
                break;
            default:
                response.sendRedirect("./index.xhtml");
                break;
            
        }
        
    }
    
}
