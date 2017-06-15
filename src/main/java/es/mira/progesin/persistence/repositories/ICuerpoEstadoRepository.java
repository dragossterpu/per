package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.CuerpoEstado;

/**
 * Repositorio de operaciones con base de datos para Cuerpos de Estado.
 * 
 * @author EZENTIS
 *
 */
public interface ICuerpoEstadoRepository extends CrudRepository<CuerpoEstado, Integer> {
    
    /**
     * Existe un Cuerpo que no coincide con un id pero coincide con un nombre corto.
     * 
     * @param nombreCorto del cuerpo
     * @param id del cuerpo
     * 
     * @return booleano con el valor de la respuesta
     */
    boolean existsByNombreCortoIgnoreCaseAndIdNotIn(String nombreCorto, int id);
    
    /**
     * 
     * Busca todos los cuerpos del estado existentes en la BBDD ordenados por descripción.
     * 
     * @return lista con todos los cuerpos
     */
    List<CuerpoEstado> findAllByOrderByDescripcionAsc();
    
}
