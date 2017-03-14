package es.mira.progesin.converters;

import java.util.ArrayList;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.hibernate.collection.internal.PersistentBag;
import org.primefaces.component.orderlist.OrderList;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;

/***********************************
 * 
 * Conversor para objetos orderList de PrimeFaces
 * 
 * Para listas PreguntasCuestionario
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
			if (list instanceof PersistentBag) {
				PersistentBag bolsa = (PersistentBag) list;
				Iterator<?> iterador = bolsa.iterator();
				while (iterador.hasNext()) {
					Object objeto = iterador.next();
					String name = ((PreguntasCuestionario) objeto).getPregunta();
					if (value.equals(name)) {
						ret = objeto;
						break;
					}
				}
			}
			else {
				ArrayList<PreguntasCuestionario> lista = ((ArrayList<PreguntasCuestionario>) list);
				for (Object objeto : lista) {
					String name = ((PreguntasCuestionario) objeto).getPregunta();
					if (value.equals(name)) {
						ret = objeto;
						break;
					}
				}
			}
		}
		return ret;
	}

}
