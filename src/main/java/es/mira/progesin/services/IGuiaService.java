package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.web.beans.GuiaBusqueda;

/*******************************************************************************
 * 
 * Servicio de guías
 * 
 * @author Ezentis
 * 
 *****************************************************************************/

public interface IGuiaService {

	/***************************************
	 * 
	 * Devuelve una lista de guías en función de los criterios de búsqueda recibidos como parámetro
	 * 
	 * @return List<Guia>
	 * @param GuiaBusqueda
	 * 
	 *************************************/

	public List<Guia> buscarGuiaPorCriteria(GuiaBusqueda busqueda);

	/***************************************
	 * 
	 * Devuelve la lista de pasos contenidos en una guía recibida como parámetro
	 * 
	 * @return List<GuiaPasos>
	 * @param Guia
	 * 
	 *************************************/

	public List<GuiaPasos> listaPasos(Guia guia);

	/***************************************
	 * 
	 * Almacena en BDD una guía pasada como parámetro
	 * 
	 * @return Guia
	 * @param Guia
	 * 
	 *************************************/

	public Guia guardaGuia(Guia guia);

	/***************************************
	 * 
	 * Devuelve una lista de todas las guías
	 * 
	 * @return List<Guia>
	 * 
	 *************************************/

	public List<Guia> findAll();

	/***************************************
	 * 
	 * Conprueba la existencia de un paso recibido como parámetro en las guías personalizadas
	 * 
	 * @return boolean
	 * @param GuiaPasos
	 * 
	 *************************************/

	public boolean existePaso(GuiaPasos paso);

}