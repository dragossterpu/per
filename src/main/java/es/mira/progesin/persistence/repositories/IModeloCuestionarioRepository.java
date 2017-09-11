package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;

/**
 * Interfaz del repositorio de modelos de cuestionario.
 * 
 * @author EZENTIS
 */
public interface IModeloCuestionarioRepository extends CrudRepository<ModeloCuestionario, Integer> {
    
    /**
     * Lista todos los modelos de cuestionario que no hayan sido dados de baja.
     * 
     * @return Listado de modelos de cuestionario.
     */
    List<ModeloCuestionario> findAllByFechaBajaIsNull();
    
}
