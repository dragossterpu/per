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
     * @param busqueda
     * 
     *************************************/
    
    public List<Guia> buscarGuiaPorCriteria(GuiaBusqueda busqueda);
    
    /***************************************
     * 
     * Devuelve la lista de pasos contenidos en una guía recibida como parámetro
     * 
     * @return List<GuiaPasos>
     * @param guia Guía de la que se desean recuperar los pasos
     * 
     *************************************/
    
    public List<GuiaPasos> listaPasos(Guia guia);
    
    /**
     * Devuelve la lista de pasos que no han sido dados de baja y están contenidos en una guía recibida como parámetro
     * 
     * @param guia Guía de la que se desean recuperar los pasos
     * @return Lista de pasos
     */
    public List<GuiaPasos> listaPasosNoNull(Guia guia);
    
    /***************************************
     * 
     * Almacena en BDD una guía pasada como parámetro
     * 
     * @return Guia
     * @param guia
     * 
     *************************************/
    
    public Guia guardaGuia(Guia guia);
    
    /***************************************
     * 
     * Comprueba la existencia de un paso recibido como parámetro en las guías personalizadas
     * 
     * @return boolean
     * @param paso
     * 
     *************************************/
    
    public boolean existePaso(GuiaPasos paso);
    
    /**
     * Permite la eliminación de una guía de la base de datos
     * 
     * @param guia guía a eliminar
     */
    public void eliminar(Guia guia);
    
}