package es.mira.progesin.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.services.ICuerpoEstadoService;

/**
 * @author EZENTIS
 *
 */
@Component("cuerpoEstadoValidator")
public class CuerpoEstadoValidator implements Validator {
    
    @Autowired
    ICuerpoEstadoService cuerpoEstadoService;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        
        String nuevoNombreCorto = value.toString();
        int id = Integer.parseInt(component.getAttributes().get("actualNombreCorto").toString());
        boolean existeCuerpo = cuerpoEstadoService.existeByNombreCortoIgnoreCaseAndIdNotIn(nuevoNombreCorto, id);
        if (existeCuerpo) {
            FacesMessage facesMsg = new FacesMessage(
                    "El nombre corto " + nuevoNombreCorto + " ya está siendo utilizado por otro cuerpo.");
            facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMsg);
        }
        
    }
    
}
