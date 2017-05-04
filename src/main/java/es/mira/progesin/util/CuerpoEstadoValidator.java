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
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage facesMsg = null;
        
        String nuevoNombreCorto = value.toString();
        int id = (Integer) component.getAttributes().get("actualNombreCorto");
        boolean existeCuerpo = cuerpoEstadoService.existeByNombreCortoIgnoreCaseAndIdNotIn(nuevoNombreCorto, id);
        if (existeCuerpo) {
            facesMsg = new FacesMessage("El cuerpo " + nuevoNombreCorto + " ya existe");
            facesMsg.setSeverity(FacesMessage.SEVERITY_WARN);
            throw new ValidatorException(facesMsg);
        }
        
    }
    
}
