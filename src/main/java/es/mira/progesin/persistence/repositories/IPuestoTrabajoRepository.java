package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.PuestoTrabajo;

/**
 * Respositorio de los puestos de trabajo.
 * 
 * @author EZENTIS
 *
 */
public interface IPuestoTrabajoRepository extends CrudRepository<PuestoTrabajo, Long> {
    
    /**
     * Busca todos los puestos de trabajo dados de alta en la BBDD ordenados por descripción.
     * 
     * @return lista de puestos existentes
     */
    List<PuestoTrabajo> findAllByOrderByDescripcionAsc();
}
