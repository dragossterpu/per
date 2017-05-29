package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;

/**
 * Repositorio de municipios.
 * 
 * @author EZENTIS
 *
 */
public interface IMunicipioRepository extends CrudRepository<Municipio, Long> {
    /**
     * Comprueba si existe un municipio sabiendo su nombre y la provincia a la que pertenece.
     * 
     * @param name nombre del municipio.
     * @param provincia del municipio
     * @return existe?
     */
    boolean existsByNameIgnoreCaseAndProvincia(String name, Provincia provincia);
    
    /**
     * Busca todos los municipios de una provincia.
     * 
     * @param provincia seleccionada
     * @return lista de municipios por provincia
     */
    List<Municipio> findByProvincia(Provincia provincia);
}
