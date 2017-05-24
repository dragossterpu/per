package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.CuerpoEstado;

/**
 * Repositorio de operaciones con base de datos para Cuerpos de Estado
 * 
 * @author EZENTIS
 *
 */
public interface ICuerpoEstadoRepository extends CrudRepository<CuerpoEstado, Integer> {
    /**
     * Devuelbve los cuerpos del estado activos
     * 
     * @return Cuerpos del estado sin fecha de baja, es decir activos
     */
    List<CuerpoEstado> findByFechaBajaIsNullOrderByIdAsc();
    
    /**
     * Existe un Cuerpo que no coincide con un id pero coincide con un nombre corto
     * 
     * @param nombreCorto
     * @param id
     * 
     * @return booleano con el valor de la respuesta
     */
    boolean existsByNombreCortoIgnoreCaseAndIdNotIn(String nombreCorto, int id);
    
    /**
     * Devuelde todos los cuerpos de estado ordenados ascendentemente por id
     * 
     * @return lista ordenada
     */
    Iterable<CuerpoEstado> findAllByOrderByIdAsc();
    
}
