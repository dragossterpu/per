package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Provincia;

/**
 * Repositorio para las provincias.
 * 
 * @author EZENTIS
 *
 */
public interface IProvinciaRepository extends CrudRepository<Provincia, String> {
    
    /**
     * Recupera todas las provincias ordenadas ascendentemente por su descripción.
     * 
     * @return lista de provincias
     */
    List<Provincia> findAllByOrderByNombreAsc();
}
