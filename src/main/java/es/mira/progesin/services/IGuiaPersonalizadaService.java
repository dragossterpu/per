package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.web.beans.GuiaPersonalizadaBusqueda;

/**
 * 
 * Interfaz del servicio de guías personalizadas
 * 
 * @author Ezentis
 * 
 */

public interface IGuiaPersonalizadaService {
    
    /**
     * 
     * Elimina una guía personalizada de la base de datos
     * 
     * @param guia
     * 
     */
    
    void eliminar(GuiaPersonalizada guia);
    
    /**
     * 
     * Anula una guía personalizada de la base de datos
     * 
     * @param guia
     * 
     */
    
    void anular(GuiaPersonalizada guia);
    
    /**
     * 
     * Almacena una guía personalizada en la base de datos
     * 
     * @param guia
     * @return GuiaPersonalizada
     * 
     */
    
    GuiaPersonalizada save(GuiaPersonalizada guia);
    
    /**
     * 
     * Devuelve una lista de pasos contenidos en una guía personalizada pasada como parámetro
     * 
     * @param guia
     * @return List<GuiaPasos>
     * 
     */
    
    public List<GuiaPasos> listaPasos(GuiaPersonalizada guia);
    
    /**
     * Devuelve el número de registros de la base de datos que cumplen con los criterio pasados como parámetro
     * 
     * @param busqueda Objeto que contiene los parámetros de búsqueda
     * @return Número de registros que responden a los parámetros
     * 
     */
    int getCountGuiaCriteria(GuiaPersonalizadaBusqueda busqueda);
    
    /**
     * Busca en base de datos los resultados que se ajustan a los parámetros recibidos en el objeto de tipo
     * GuiaPersonalizadaBusqueda acotados por los parámetros first (primer resultado) y pageSize (máximo de resultados
     * de la búsqueda)
     * 
     * @param first primer elemento de los resultados
     * @param pageSize número máximo de resultados
     * @param sortField campo de ordenación
     * @param sortOrder sentido de la orientación
     * @param busqueda objeto que contiene los parámetros de búsqueda
     * @return lista de Guías personalizadas que corresponden a la búsqueda
     * 
     */
    List<GuiaPersonalizada> buscarGuiaPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            GuiaPersonalizadaBusqueda busqueda);
    
    /**
     * Comprueba la existencia en base de datos de guías personalizadas cuya guía modelo corresponde a la recibida como
     * parámetro
     * 
     * @param guia Guía de la que quiere confirmarse si existen guías personalizadas
     * @return Booleano correspondiendo a la respuesta
     * 
     */
    boolean buscarPorModeloGuia(Guia guia);
    
    /**
     * Lista las inspecciones asignadas a una guía personalizada
     * 
     * @param guia Guía de la que se quiere recuperar las inspecciones
     * @return respuesta
     */
    public List<Inspeccion> listaInspecciones(GuiaPersonalizada guia);
    
}
