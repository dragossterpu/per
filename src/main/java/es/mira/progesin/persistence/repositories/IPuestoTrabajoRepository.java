package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.PuestoTrabajo;

/**
 * Respositorio de los puestos de trabajo.
 * 
 * @author EZENTIS
 *
 */
public interface IPuestoTrabajoRepository extends CrudRepository<PuestoTrabajo, Long> {
    
}
