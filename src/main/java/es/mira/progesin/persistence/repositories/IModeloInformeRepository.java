package es.mira.progesin.persistence.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.ModeloInforme;

/**
 * Repositorio de modelos de informe.
 * 
 * @author EZENTIS
 */
public interface IModeloInformeRepository extends CrudRepository<ModeloInforme, Long> {
    
    /**
     * Recupera un modelo con sus areas y subareas
     * @param id clave del modelo
     * @return modelo con todo mapeado
     */
    @EntityGraph(value = "ModeloInforme.areas", type = EntityGraphType.LOAD)
    ModeloInforme findDistinctById(Long id);
    
}
