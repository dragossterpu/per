package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.TipoUnidad;

/**
 * Repositorio para los tipos de unidad.
 * 
 * @author EZENTIS
 *
 */
public interface ITipoUnidadRepository extends CrudRepository<TipoUnidad, Long> {
    
}
