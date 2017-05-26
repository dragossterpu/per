package es.mira.progesin.converters;

import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.primefaces.component.orderlist.OrderList;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;

/**
 * 
 * Conversor para objetos orderList de PrimeFaces.
 * 
 * Para listas de AreasCuestionario
 * 
 * @author EZENTIS
 * 
 */

@Component("areasConverter")
public class AreasConverter implements Converter {
    
    /**
     * Conversor de AreasCuestionario que recupera su campo área (La descripción) como cadena.
     */
    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        
        return ((AreasCuestionario) value).getArea();
        
    }
    
    /**
     * Conversor que devuelve el objeto entero a partir de su descripción recibido como string.
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object ret = null;
        if (component instanceof OrderList) {
            Object list = ((OrderList) component).getValue();
            
            @SuppressWarnings("unchecked")
            ArrayList<AreasCuestionario> lista = (ArrayList<AreasCuestionario>) list;
            for (Object objeto : lista) {
                String name = ((AreasCuestionario) objeto).getArea();
                if (value.equals(name)) {
                    ret = objeto;
                    break;
                }
            }
        }
        
        return ret;
    }
    
}
