/**
 * 
 */
package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.AreaInforme;

/**
 * Repositorio de areas de modelo de informe.
 * 
 * @author EZENTIS
 */
public interface IAreaInformeRepository extends CrudRepository<AreaInforme, Long> {
    
}
