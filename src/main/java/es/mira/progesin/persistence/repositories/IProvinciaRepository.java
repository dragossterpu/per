package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Provincia;

/**
 * Repositorio para las provincias.
 * 
 * @author EZENTIS
 *
 */
public interface IProvinciaRepository extends CrudRepository<Provincia, String> {
    
}
