package es.mira.progesin.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * @author EZENTIS
 */
@Component("correoDestinatarioValidator")
public class CorreoDestinatarioValidator implements Validator {
    
    @Autowired
    ISolicitudDocumentacionService solicitudDocumentacionService;
    
    @Autowired
    ICuestionarioEnvioService cuestionarioEnvioService;
    
    @Autowired
    ApplicationBean applicationBean;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        
        String msg = null;
        
        String nuevoCorreoDestinatario = value.toString();
        
        String actualCorreoDestinatario = (String) component.getAttributes().get("actualCorreo");
        
        String regex = "(?i)^[_a-z0-9-+]+(.[_a-z0-9-]+)*@(" + applicationBean.getDominiosValidos() + ")$";
        if (!nuevoCorreoDestinatario.matches(regex)) {
            msg = "Formato de correo incorrecto o dominio no válido.";
        } else if (nuevoCorreoDestinatario.equals(actualCorreoDestinatario) == Boolean.FALSE) {
            if (solicitudDocumentacionService.findNoFinalizadaPorCorreoDestinatario(nuevoCorreoDestinatario) != null) {
                msg = "No se puede asignar esta solicitud al destinatario con este correo, ya tiene otra solicitud en curso. Debe finalizarla o anularla antes de proseguir.";
            } else if (cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(nuevoCorreoDestinatario) != null) {
                msg = "No se puede asignar esta solicitud al destinatario con este correo, ya tiene un cuestionario en curso. Debe finalizarlo o anularlo antes de proseguir.";
            }
        }
        if (msg != null) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            throw new ValidatorException(facesMsg);
        }
    }
    
}
