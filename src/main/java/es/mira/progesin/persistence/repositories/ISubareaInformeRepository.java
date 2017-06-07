package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.informes.SubareaInforme;

/**
 * Repositorio de subareas de modelo de informe.
 * 
 * @author EZENTIS
 */
public interface ISubareaInformeRepository extends CrudRepository<SubareaInforme, Long> {
    
}
