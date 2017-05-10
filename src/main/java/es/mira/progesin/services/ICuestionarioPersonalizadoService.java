package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBusqueda;

/**
 * 
 * Interfaz del servicio de modelos de cuestionario personalizados
 * 
 * @author Ezentis
 *
 */
public interface ICuestionarioPersonalizadoService {
    
    /**
     * Elimina un cuestionario personalizado a partir de su objeto
     * 
     * @author Ezentis
     * @param cuestionario
     */
    void delete(CuestionarioPersonalizado cuestionario);
    
    /**
     * Guarda la información de un modelo de cuestionario personalizado en la bdd.
     * 
     * @author Ezentis
     * @param cuestionario
     * @return cuestionario con id
     */
    CuestionarioPersonalizado save(CuestionarioPersonalizado cuestionario);
    
    /**
     * Método que devuelve la lista de modelos de cuestionario personalizados en una consulta basada en criteria.
     * 
     * @author EZENTIS
     * @param cuestionarioBusqueda objeto con los criterios de búsqueda
     * @param first primer elemento
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @return la lista de modelos de cuestionario personalizados.
     */
    List<CuestionarioPersonalizado> buscarCuestionarioPersonalizadoCriteria(int first, int pageSize, String sortField,
            SortOrder sortOrder, CuestionarioPersonalizadoBusqueda cuestionarioBusqueda);
    
    /**
     * Método que devuelve el número de modelos de cuestionario personalizados en una consulta basada en criteria
     * 
     * @author EZENTIS
     * @param cuestionarioBusqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de una consulta criteria.
     */
    int getCountCuestionarioCriteria(CuestionarioPersonalizadoBusqueda cuestionarioBusqueda);
    
}
