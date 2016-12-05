package es.mira.progesin.converters;

import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.stereotype.Component;

/**
 * Clase para evitar los espacios en blanco en los formularios de entrada.
 * @author sperezp
 *
 */
@Component("listaExtensionesConverter")
public class ListaExtensionesConverter implements Converter {

	private static final String SEPARADOR = ", ";

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		return (submittedValue != null) ? Arrays.asList(submittedValue.split(SEPARADOR)) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		return (modelValue != null) ? String.join(SEPARADOR, (List<String>) modelValue) : null;
	}

}