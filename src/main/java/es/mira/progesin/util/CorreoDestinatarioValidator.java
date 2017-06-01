package es.mira.progesin.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * Comprueba que el correo introducido en el formulario de modificación de una solicitud es válido y no se está usando
 * en otra solicitud o cuestionario.
 * 
 * @author EZENTIS
 */
@Component("correoDestinatarioValidator")
public class CorreoDestinatarioValidator implements Validator {
    /**
     * Servicio de solicitud de documentación.
     */
    @Autowired
    ISolicitudDocumentacionService solicitudDocumentacionService;
    
    /**
     * Servicio de cuestionarios enviados.
     */
    @Autowired
    ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Bean de configuración de la aplicación.
     */
    @Autowired
    ApplicationBean applicationBean;
    
    /**
     * Comprueba que el correo introducido en el formulario de modificación de una solicitud es válido y no se está
     * usando en otra solicitud o cuestionario.
     * 
     * @param context Contexto en el que se produce la validación.
     * @param component Componente de la vista en el que se produce la edición a validar
     * @param value Objeto a validar
     * 
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        
        String msg = null;
        
        String nuevoCorreoDestinatario = value.toString();
        
        String actualCorreoDestinatario = (String) component.getAttributes().get("actualCorreo");
        
        String regex = "(?i)^[_a-z0-9-+]+(.[_a-z0-9-]+)*@(" + applicationBean.getDominiosValidos() + ")$";
        if (!nuevoCorreoDestinatario.matches(regex)) {
            msg = "Formato de correo incorrecto o dominio no válido.";
        } else if (nuevoCorreoDestinatario.equals(actualCorreoDestinatario) == Boolean.FALSE) {
            SolicitudDocumentacionPrevia solicitudPendiente = solicitudDocumentacionService
                    .findNoFinalizadaPorCorreoDestinatario(nuevoCorreoDestinatario);
            if (solicitudPendiente != null) {
                msg = "No se puede asignar esta solicitud al destinatario con este correo, ya tiene otra solicitud en curso para la inspeccion "
                        + solicitudPendiente.getInspeccion().getNumero()
                        + ". Debe finalizarla o anularla antes de proseguir.";
            } else {
                CuestionarioEnvio cuestionarioPendiente = cuestionarioEnvioService
                        .findNoFinalizadoPorCorreoEnvio(nuevoCorreoDestinatario);
                if (cuestionarioPendiente != null) {
                    msg = "No se puede asignar esta solicitud al destinatario con este correo, ya tiene un cuestionario en curso para la inspeccion "
                            + cuestionarioPendiente.getInspeccion().getNumero()
                            + ". Debe finalizarlo o anularlo antes de proseguir.";
                }
            }
        }
        if (msg != null) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            throw new ValidatorException(facesMsg);
        }
    }
    
}
