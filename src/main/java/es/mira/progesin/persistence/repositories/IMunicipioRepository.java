package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;

public interface IMunicipioRepository extends CrudRepository<Municipio, Long> {
    /**
     * Comprueba si existe un municipio
     * 
     * @param name
     * @param provincia
     * @return existe?
     */
    boolean existsByNameIgnoreCaseAndProvincia(String name, Provincia provincia);
}
