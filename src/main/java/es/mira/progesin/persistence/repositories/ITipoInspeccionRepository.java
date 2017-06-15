package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.TipoInspeccion;

/**
 * Interfaz del servicio de tipos de inspección.
 * 
 * @author EZENTIS
 *
 */
public interface ITipoInspeccionRepository extends CrudRepository<TipoInspeccion, String> {
    
    /**
     * Comprueba si existe o no un tipo de inspección filtrado por su código.
     * 
     * @param codigo del tipo
     * @return resultado de la consulta
     */
    boolean existsByCodigoIgnoreCase(String codigo);
    
    /**
     * Busca todos los tipos de inspección dados de alta en la BBDD ordenados por su descripción.
     * 
     * @return lista con todas las clases en BBDD
     */
    List<TipoInspeccion> findAllByOrderByDescripcionAsc();
}
