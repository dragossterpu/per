package es.mira.progesin.converters;

import java.util.ArrayList;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.collection.internal.PersistentBag;
import org.primefaces.component.orderlist.OrderList;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.GuiaPasos;

/**
 * 
 * Conversor para objetos orderList de PrimeFaces.
 * 
 * Convierte objetos de tipo GuiaPasos para su uso con el control orderList
 * 
 * @author EZENTIS
 * 
 **/

@Component("guiasConverter")
public class GuiasConverter implements Converter {
    
    /**
     * Conversor de GuiaPasos que recupera su campo paso (La descripción) como cadena.
     */
    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        
        return ((GuiaPasos) value).getPaso();
    }
    
    /**
     * Conversor que devuelve el objeto entero a partir de su paso recibido como string.
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object ret = null;
        if (component instanceof OrderList) {
            Object list = ((OrderList) component).getValue();
            Iterator<?> iterador;
            if (list instanceof PersistentBag) {
                PersistentBag bolsa = (PersistentBag) list;
                iterador = bolsa.iterator();
            } else {
                @SuppressWarnings("unchecked")
                ArrayList<GuiaPasos> lista = (ArrayList<GuiaPasos>) list;
                iterador = lista.iterator();
            }
            while (iterador.hasNext()) {
                Object objeto = iterador.next();
                String nameSinSaltos = cadenaSinSaltos(((GuiaPasos) objeto).getPaso());
                String valueSinSaltos = cadenaSinSaltos(value);
                if (valueSinSaltos.equals(nameSinSaltos)) {
                    ret = objeto;
                    break;
                }
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
        String sinSaltos = cadena.replace('\n', ' ');
        sinSaltos = sinSaltos.replace('\r', ' ');
        sinSaltos = sinSaltos.replaceAll(" ", "");
        return sinSaltos;
        
    }
}
