package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;

/**
 * Interfaz del repositorio de modelos de cuestionario
 * 
 * @author EZENTIS
 */
public interface IModeloCuestionarioRepository extends CrudRepository<ModeloCuestionario, Integer> {
    
    /**
     * Recupera una lista con todos los modelos de cuestionario de la bdd.
     * 
     * @author Ezentis
     * @return lista de modelos
     */
    @Override
    List<ModeloCuestionario> findAll();
}
