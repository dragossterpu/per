package es.mira.progesin.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.services.ITipoInspeccionService;

/**
 * Validador para tipos de inspección. Inpide que se puedan crear modelos de inspección con un nombre corto que ya
 * existe.
 * @author EZENTIS
 *
 */
@Component("codigoTipoInspeccionValidator")
public class CodigoTipoInspeccionValidator implements Validator {
    
    /**
     * Variable utilizada para inyectar el servicio de tipos de inspección.
     * 
     */
    @Autowired
    private ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Si existe el nombre corto de una inspección, lanza una excepción.
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        
        String codigo = value.toString();
        
        if (tipoInspeccionService.existeByCodigoIgnoreCase(codigo)) {
            FacesMessage facesMsg = new FacesMessage("Ya existe un tipo de inspección con ese código");
            facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMsg);
        }
        
    }
    
}
