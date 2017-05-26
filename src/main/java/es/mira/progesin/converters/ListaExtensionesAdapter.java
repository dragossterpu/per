package es.mira.progesin.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * .
 * @author EZENTIS
 *
 */
@Converter(autoApply = false)
public class ListaExtensionesAdapter implements AttributeConverter<Object, String> {
    
    private static final String SEPARADOR = ", ";
    
    /**
     * Convert object to a String
     */
    @SuppressWarnings("unchecked")
    @Override
    public String convertToDatabaseColumn(Object listaextensiones) {
        return String.join(SEPARADOR, (List<String>) listaextensiones);
    }
    
    /**
     * Convert a String to a object
     */
    @Override
    public List<String> convertToEntityAttribute(String extensiones) {
        return new ArrayList<>(Arrays.asList(extensiones.split(SEPARADOR)));
    }
    
}