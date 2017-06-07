package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.ModeloInforme;

/**
 * Repositorio de modelos de informe.
 * 
 * @author EZENTIS
 */
public interface IModeloInformeRepository extends CrudRepository<ModeloInforme, Long> {
    
}
