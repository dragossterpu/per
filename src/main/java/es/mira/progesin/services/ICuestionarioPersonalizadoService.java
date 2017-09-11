package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBusqueda;

/**
 * 
 * Interfaz del servicio de modelos de cuestionario personalizados.
 * 
 * @author EZENTIS
 *
 */
public interface ICuestionarioPersonalizadoService {
    
    /**
     * Elimina un cuestionario personalizado a partir de su objeto.
     * 
     * @param cuestionario a eliminar
     */
    void delete(CuestionarioPersonalizado cuestionario);
    
    /**
     * Guarda la información de un modelo de cuestionario personalizado en la bdd.
     * 
     * @param cuestionario a guardar
     * @return cuestionario guardado con id
     */
    CuestionarioPersonalizado save(CuestionarioPersonalizado cuestionario);
    
    /**
     * Método que devuelve la lista de modelos de cuestionario personalizados en una consulta basada en criteria.
     * 
     * @param cuestionarioBusqueda objeto con los criterios de búsqueda
     * @param first primer elemento de la consulta
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @return la lista de modelos de cuestionario personalizados.
     */
    List<CuestionarioPersonalizado> buscarCuestionarioPersonalizadoCriteria(int first, int pageSize, String sortField,
            SortOrder sortOrder, CuestionarioPersonalizadoBusqueda cuestionarioBusqueda);
    
    /**
     * Método que devuelve el número de modelos de cuestionario personalizados en una consulta basada en criteria.
     * 
     * @param cuestionarioBusqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de la consulta criteria.
     */
    int getCountCuestionarioCriteria(CuestionarioPersonalizadoBusqueda cuestionarioBusqueda);
    
    /**
     * Metodo que comprueba la existencia de cuestionarios personalizados que tengan como modelo base el que se recibe
     * como parámetro.
     * 
     * @param modelo Modelo del que se desea conocer si hay cuestionarios personalizados.
     * @return Respuesta de la consulta
     */
    boolean existsByModelo(ModeloCuestionario modelo);
    
}
