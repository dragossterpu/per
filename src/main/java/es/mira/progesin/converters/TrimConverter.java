package es.mira.progesin.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.stereotype.Component;

/**
 * Clase para evitar los espacios en blanco en los formularios de entrada.
 * @author Ezentis
 *
 */
@Component("trimConverter")
public class TrimConverter implements Converter {
    
    /**
     * Elimina espacios antes y despues de un texto.
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        String trimmed = null;
        if (submittedValue != null) {
            trimmed = submittedValue.trim();
            if (trimmed.isEmpty()) {
                trimmed = null;
            }
        }
        return trimmed;
    }
    
    /**
     * Recupera el texto.
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        String cadena = null;
        if (modelValue != null)
            cadena = modelValue.toString();
        return cadena;
    }
    
}