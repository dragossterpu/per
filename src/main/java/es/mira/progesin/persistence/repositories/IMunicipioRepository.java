package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Municipio;

public interface IMunicipioRepository extends CrudRepository<Municipio, Long> {
    /**
     * Comprueba si existe un municipio
     * 
     * @param name
     * @return existe?
     */
    boolean existsByNameIgnoreCase(String name);
}
