package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.Sugerencia;

/**
 * Interfaz del servicio para la gestión de sugerencias.
 * 
 * @author EZENTIS
 *
 */
public interface ISugerenciaService {
    /**
     * Borra una sugerencia de BBDD conociendo su identificador.
     * 
     * @param id identificador de la sugerencia a elimianr
     */
    void delete(Integer id);
    
    /**
     * Busca una sugerencia conociendo su identificador.
     * 
     * @param id identificador
     * @return objeto sugerencia encontrado
     */
    Sugerencia findOne(Integer id);
    
    /**
     * Busca todas las sugerencias en BBDD.
     * 
     * @return iterable con la lista de todas las sugerencias en BBDD
     */
    Iterable<Sugerencia> findAll();
    
    /**
     * Guarda o actualiza una sugerencia.
     * 
     * @param entity sugerencia a guardar
     * @return sugerencia resultado booleano
     */
    Sugerencia save(Sugerencia entity);
}
