package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.TipoUnidad;

/**
 * Repositorio para los tipos de unidad.
 * 
 * @author EZENTIS
 *
 */
public interface ITipoUnidadRepository extends CrudRepository<TipoUnidad, Long> {
    
    /**
     * Recupera todos los tipos de unidad ordenados ascendentemente por su descripción.
     * 
     * @return lista de tipos de unidad
     */
    List<TipoUnidad> findAllByOrderByDescripcionAsc();
    
}
