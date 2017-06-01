package es.mira.progesin.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.services.ITipoEquipoService;

/**
 * Validador del código de tipo de equipo.
 * 
 * @author EZENTIS
 */
@Component("codigoTipoEquipoValidator")
public class CodigoTipoEquipoValidator implements Validator {
    /**
     * Servicio de tipo de equipo.
     */
    @Autowired
    private ITipoEquipoService tipoEquipoService;
    
    /**
     * Comprueba si el código de un tipo de equipo existe y no coincide con el mismo que se está modificando. Se emplea
     * para la edición en caliente de cuerpos.
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        
        String codigoNuevo = value.toString();
        Long idTipoEquipo = (Long) component.getAttributes().get("idTipoEquipo");
        
        TipoEquipo tipoEquipoBDD = tipoEquipoService.findByCodigoIgnoreCase(codigoNuevo);
        if (tipoEquipoBDD != null && tipoEquipoBDD.getId().equals(idTipoEquipo) == Boolean.FALSE) {
            FacesMessage facesMsg = new FacesMessage("Ya existe un tipo de equipo con ese código");
            facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMsg);
        }
        
    }
    
}
