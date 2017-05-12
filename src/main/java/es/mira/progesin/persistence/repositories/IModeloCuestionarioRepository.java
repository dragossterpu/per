package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;

/**
 * Interfaz del repositorio de modelos de cuestionario
 * 
 * @author EZENTIS
 */
public interface IModeloCuestionarioRepository extends CrudRepository<ModeloCuestionario, Integer> {
    
}
