package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;

/**
 * @author Ezentis
 *
 */
public interface IMunicipioService {
    
    /**
     * Comprueba si existe un municipio
     * 
     * @param name
     * @param provincia
     * @return
     */
    boolean existeByNameIgnoreCaseAndProvincia(String name, Provincia provincia);
    
    /**
     * Guarda un nuevo municipio
     * @param nombre
     * @param provincia
     * @return municipio creado
     */
    Municipio crearMunicipio(String nombre, Provincia provincia);
    
}
