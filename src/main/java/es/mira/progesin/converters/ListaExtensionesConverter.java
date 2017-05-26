package es.mira.progesin.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.stereotype.Component;

/**
 * @author EZENTIS
 */
@Component("listaExtensionesConverter")
public class ListaExtensionesConverter implements Converter {
    
    private static final String SEPARADOR = ", ";
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        return (submittedValue != null) ? new ArrayList<>(Arrays.asList(submittedValue.split(SEPARADOR))) : null;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        return (modelValue != null) ? String.join(SEPARADOR, (List<String>) modelValue) : null;
    }
    
}