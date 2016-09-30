package es.mira.progesin.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.stereotype.Component;

/**
 * Clase para evitar los espacios en blanco en los formularios de entrada.
 * @author sperezp
 *
 */
@Component("trimConverter")
public class TrimConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		String trimmed = (submittedValue != null) ? submittedValue.trim() : null;
		return (trimmed == null || trimmed.isEmpty()) ? null : trimmed;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		return (modelValue != null) ? modelValue.toString() : null;
	}

}