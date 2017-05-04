package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.CuerpoEstado;

public interface ICuerpoEstadoRepository extends CrudRepository<CuerpoEstado, Integer> {
    /**
     * 
     * @return Cuerpos del estado sin fecha de baja, es decir activos
     */
    List<CuerpoEstado> findByFechaBajaIsNull();
    
    /**
     * Comprueba si existe un cuerpo con un determinado nombre corto
     * 
     * @param nombreCorto
     * @return existe?
     */
    boolean existsByNombreCortoIgnoreCase(String nombreCorto);
    
    /**
     * Existe un Cuerpo que no coincide con un id pero coincide con un nombre corto
     * @param nombreCorto
     * @param id
     * 
     * @return
     */
    boolean existsByNombreCortoIgnoreCaseAndIdNotIn(String nombreCorto, int id);
    
}
