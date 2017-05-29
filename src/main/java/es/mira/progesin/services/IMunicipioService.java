package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;

/**
 * Interfaz del servicio que gestiona los municipios.
 * 
 * @author EZENTIS
 *
 */
public interface IMunicipioService {
    
    /**
     * Comprueba si existe un municipio conociendo su nombre.
     * 
     * @param name nombre del municipio
     * @param provincia a la que pertenece el municipio
     * @return valor booleano
     */
    boolean existeByNameIgnoreCaseAndProvincia(String name, Provincia provincia);
    
    /**
     * Guarda un nuevo municipio.
     * @param nombre del municipio
     * @param provincia a la que pertenece el municipio
     * @return municipio creado (true si es guardado correctamente)
     */
    Municipio crearMunicipio(String nombre, Provincia provincia);
    
    /**
     * Busca los municipios pertenecientes a una provincia.
     * 
     * @param provincia que queremos consultar
     * @return lista de municipios por provincia
     */
    List<Municipio> findByProvincia(Provincia provincia);
}
