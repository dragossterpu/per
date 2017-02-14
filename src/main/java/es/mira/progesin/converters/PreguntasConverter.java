package es.mira.progesin.converters;

import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.primefaces.component.orderlist.OrderList;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;

/***********************************
 * 
 * Conversor para objetos orderList de PrimeFaces
 * 
 * Para listas de AreasCuestionario y PreguntasCuestionario
 * 
 */

@Component("preguntasConverter")
public class PreguntasConverter implements Converter {

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {

		return ((PreguntasCuestionario) value).getPregunta().toString();
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Object ret = null;
		if (component instanceof OrderList) {
			Object list = ((OrderList) component).getValue();

			ArrayList<PreguntasCuestionario> lista = (ArrayList<PreguntasCuestionario>) list;
			for (Object objeto : lista) {
				String name = ((PreguntasCuestionario) objeto).getPregunta();
				if (value.equals(name)) {
					ret = objeto;
					break;
				}
			}

		}
		return ret;
	}

}
