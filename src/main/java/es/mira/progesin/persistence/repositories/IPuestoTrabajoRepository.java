package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.PuestoTrabajo;

/**
 * Respositorio de los puestos de trabajo
 * 
 * @author EZENTIS
 *
 */
public interface IPuestoTrabajoRepository extends CrudRepository<PuestoTrabajo, Long> {
    /**
     * 
     * @return Puestos de trabajo sin fecha de baja, es decir activos
     */
    List<PuestoTrabajo> findByFechaBajaIsNullOrderByIdAsc();
    
}
