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
				ret = buscaEnBolsa(list, value);
			}
			else {
				ret = buscaEnLista(list, value);

			}
		}
		return ret;
	}

	private Object buscaEnBolsa(Object list, Object value) {
		PersistentBag bolsa = (PersistentBag) list;
		Iterator<?> iterador = bolsa.iterator();
		while (iterador.hasNext()) {
			Object objeto = iterador.next();
			String name = ((GuiaPasos) objeto).getPaso();
			if (value.equals(name)) {
				return objeto;
			}
		}
		return null;
	}

	private Object buscaEnLista(Object list, Object value) {
		ArrayList<GuiaPasos> lista = (ArrayList<GuiaPasos>) list;
		for (Object objeto : lista) {
			String name = ((GuiaPasos) objeto).getPaso();
			if (value.equals(name)) {
				return objeto;
			}
		}
		return null;
	}
}
