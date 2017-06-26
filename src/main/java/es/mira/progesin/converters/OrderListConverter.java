package es.mira.progesin.converters;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.primefaces.component.orderlist.OrderList;
import org.springframework.stereotype.Component;

/**
 * 
 * Conversor para objetos OrderList de PrimeFaces.
 * 
 */

@Component("orderlistConverter")
public class OrderListConverter implements Converter {
    
    /**
     * Convierte el objeto que le llega a String para poder visualizar su valor.
     */
    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        String retorno = "";
        if (value != null) {
            retorno = value.toString();
        }
        return retorno;
    }
    
    /**
     * Devuelve un objeto a partir del componente OrderList recibido y del valor seleccionado del OrderList recibido
     * como string.
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object ret = null;
        Object list = ((OrderList) component).getValue();
        @SuppressWarnings("unchecked")
        List<Object> lista = (ArrayList<Object>) list;
        boolean encontrado = false;
        for (int i = 0; i < lista.size() && !encontrado; i++) {
            Object objeto = lista.get(i);
            String nameSinSaltos = cadenaSinSaltos(objeto.toString());
            String valueSinSaltos = cadenaSinSaltos(value);
            if (valueSinSaltos.equals(nameSinSaltos)) {
                ret = objeto;
                encontrado = true;
            }
        }
        return ret;
    }
    
    /**
     * Devuelve la cadena sin saltos.
     * 
     * @param cadena a modificar
     * @return cadena modificada
     */
    private String cadenaSinSaltos(String cadena) {
        return cadena.replace('\n', ' ').replace('\r', ' ').replaceAll(" ", "");
    }
    
}
