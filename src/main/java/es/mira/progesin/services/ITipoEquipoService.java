package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.TipoEquipo;

/**
 * Servicio de Tipos de equipo.
 * 
 * @author EZENTIS
 *
 */
public interface ITipoEquipoService {
    
    /**
     * Devuelve un listado de todos los tipos de equipo definidos.
     * 
     * @return lista de tipos de equipo
     */
    List<TipoEquipo> findAll();
    
    /**
     * Elimina un tipo de equipo.
     * 
     * @param id identificador del tipo de equipo a eliminar
     */
    void delete(Long id);
    
    /**
     * Guarda en base de datos un nuevo tipo de equipo.
     * 
     * @param entity tipo de equipo a guardar
     * @return tipo de equipo guardado
     */
    TipoEquipo save(TipoEquipo entity);
    
    /**
     * Busca un tipo de equipo a partir de su código.
     * 
     * @param codigo por el que se buscará en la base de datos. Es case-insensitive
     * @return el tipo de equipo que corresponde a la búsqueda
     */
    TipoEquipo findByCodigoIgnoreCase(String codigo);
    
}
