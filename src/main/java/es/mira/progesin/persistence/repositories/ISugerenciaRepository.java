package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Sugerencia;

/**
 * Repositorio para las sugerencias.
 * 
 * @author EZENTIS
 *
 */
public interface ISugerenciaRepository extends CrudRepository<Sugerencia, Integer> {
    
}
