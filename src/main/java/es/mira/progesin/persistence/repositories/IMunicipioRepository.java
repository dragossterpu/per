package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;

/**
 * Repositorio de municipios
 * 
 * @author EZENTIS
 *
 */
public interface IMunicipioRepository extends CrudRepository<Municipio, Long> {
    /**
     * Comprueba si existe un municipio
     * 
     * @param name
     * @param provincia
     * @return existe?
     */
    boolean existsByNameIgnoreCaseAndProvincia(String name, Provincia provincia);
    
    /**
     * Busca los municipios de una provincia
     * 
     * @param provincia
     * @return lista de municipios
     */
    List<Municipio> findByProvincia(Provincia provincia);
}
