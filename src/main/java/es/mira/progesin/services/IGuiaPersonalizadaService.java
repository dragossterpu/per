package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Guia;
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
     * @param guia
     * 
     ****************************************/
    
    void eliminar(GuiaPersonalizada guia);
    
    /*********************************
     * 
     * Anula una guía personalizada de la base de datos
     * 
     * @param guia
     * 
     ****************************************/
    
    void anular(GuiaPersonalizada guia);
    
    /*********************************
     * 
     * Almacena una guía personalizada en la base de datos
     * 
     * @param guia
     * @return GuiaPersonalizada
     * 
     ****************************************/
    
    GuiaPersonalizada save(GuiaPersonalizada guia);
    
    /*********************************
     * 
     * Devuelve una lista de pasos contenidos en una guía personalizada pasada como parámetro
     * 
     * @param guia
     * @return List<GuiaPasos>
     * 
     ****************************************/
    
    public List<GuiaPasos> listaPasos(GuiaPersonalizada guia);
    
    /**
     * Devuelve el número de registros de la base de datos que cumplen con los criterio pasados como parámetro
     * 
     * @param busqueda Objeto que contiene los parámetros de búsqueda
     * @return Número de registros que responden a los parámetros
     * 
     */
    long getCountGuiaCriteria(GuiaPersonalizadaBusqueda busqueda);
    
    /**
     * Busca en base de datos los resultados que se ajustan a los parámetros recibidos en el objeto de tipo
     * GuiaPersonalizadaBusqueda acotados por los parámetros firstResult (primer resultado) y maxresult (máximo de
     * resultados de la búsqueda)
     * 
     * @param firstResult Primer elemento de la búsqueda
     * @param maxResults Máximo número de resultados
     * @param busqueda Objeto que contiene los parámetros de búsqueda
     * @return Listado de guías que responden a los parámetros
     * 
     */
    List<GuiaPersonalizada> buscarGuiaPorCriteria(int firstResult, int maxResults, GuiaPersonalizadaBusqueda busqueda);
    
    /**
     * Comprueba la existencia en base de datos de guías personalizadas cuya guía modelo corresponde a la recibida como
     * parámetro
     * 
     * @param guia Guía de la que quiere confirmarse si existen guías personalizadas
     * @return Booleano correspondiendo a la respuesta
     * 
     */
    boolean buscarPorModeloGuia(Guia guia);
    
}
