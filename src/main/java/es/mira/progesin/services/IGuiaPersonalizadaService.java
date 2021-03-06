package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.web.beans.GuiaBusqueda;

/**
 * 
 * Interfaz del servicio de guías personalizadas.
 * 
 * @author EZENTIS
 * 
 */

public interface IGuiaPersonalizadaService {
    
    /**
     * 
     * Elimina una guía personalizada de la base de datos.
     * 
     * @param guia que se desea eliminar
     * 
     */
    
    void eliminar(GuiaPersonalizada guia);
    
    /**
     * 
     * Anula una guía personalizada de la base de datos.
     * 
     * @param guia que se desea eliminar
     * 
     */
    
    void anular(GuiaPersonalizada guia);
    
    /**
     * 
     * Almacena una guía personalizada en la base de datos.
     * 
     * @param guia guia a guardar
     * @return guia guardada
     * 
     */
    
    GuiaPersonalizada save(GuiaPersonalizada guia);
    
    /**
     * 
     * Devuelve una lista de pasos contenidos en una guía personalizada pasada como parámetro.
     * 
     * @param guia de la que se desea recuperar los pasos
     * @return lista de los pasos
     * 
     */
    
    List<GuiaPasos> listaPasos(GuiaPersonalizada guia);
    
    /**
     * Devuelve el número de registros de la base de datos que cumplen con los criterio pasados como parámetro.
     * 
     * @param busqueda Objeto que contiene los parámetros de búsqueda
     * @return Número de registros que responden a los parámetros
     * 
     */
    int getCountGuiaCriteria(GuiaBusqueda busqueda);
    
    /**
     * Busca en base de datos los resultados que se ajustan a los parámetros recibidos en el objeto de tipo
     * GuiaPersonalizadaBusqueda acotados por los parámetros first (primer resultado) y pageSize (máximo de resultados
     * de la búsqueda).
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
            GuiaBusqueda busqueda);
    
    /**
     * Comprueba la existencia en base de datos de guías personalizadas cuya guía modelo corresponde a la recibida como
     * parámetro.
     * 
     * @param guia Guía de la que quiere confirmarse si existen guías personalizadas
     * @return Booleano correspondiendo a la respuesta
     * 
     */
    boolean buscarPorModeloGuia(Guia guia);
    
    /**
     * Lista las inspecciones asignadas a una guía personalizada.
     * 
     * @param guia Guía de la que se quiere recuperar las inspecciones
     * @return respuesta
     */
    List<Inspeccion> listaInspecciones(GuiaPersonalizada guia);
    
    /**
     * Devuelve una guía personalizada por medio de su id.
     * 
     * @param id Identificador de la gía
     * @return Guía personalizada
     */
    GuiaPersonalizada findOne(Long id);
    
    /**
     * Devuelve un listado de modelos de guía.
     * 
     * @return Listado de modelos
     */
    List<Guia> listadoModelos();
    
}
