package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.web.beans.GuiaPersonalizadaBusqueda;

/*********************************
 * 
 * Interfaz del servicio de guías personalizadas
 * 
 * @author Ezentis
 * 
 ****************************************/

public interface IGuiaPersonalizadaService {

	/*********************************
	 * 
	 * Elimina una guía personalizada de la base de datos
	 * 
	 * @param GuiaPersonalizada
	 * 
	 ****************************************/

	void eliminar(GuiaPersonalizada guia);

	/*********************************
	 * 
	 * Almacena una guía personalizada en la base de datos
	 * 
	 * @param GuiaPersonalizada
	 * @return GuiaPersonalizada
	 * 
	 ****************************************/

	GuiaPersonalizada save(GuiaPersonalizada guia);

	/*********************************
	 * 
	 * Devuelve una lista de guías personalizadas de la base de datos en función a unos criterios de búsqueda
	 * 
	 * @param GuiaPersonalizadaBusqueda
	 * @return List<GuiaPersonalizada>
	 * 
	 ****************************************/

	public List<GuiaPersonalizada> buscarGuiaPorCriteria(GuiaPersonalizadaBusqueda busqueda);

	/*********************************
	 * 
	 * Devuelve una lista de pasos contenidos en una guía personalizada pasada como parámetro
	 * 
	 * @param GuiaPersonalizada
	 * @return ist<GuiaPasos>
	 * 
	 ****************************************/

	public List<GuiaPasos> listaPasos(GuiaPersonalizada guia);

}
