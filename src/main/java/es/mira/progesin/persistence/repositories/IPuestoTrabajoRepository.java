package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.PuestoTrabajo;

public interface IPuestoTrabajoRepository extends CrudRepository<PuestoTrabajo, Integer> {
    /**
     * 
     * @return Puestos de trabajo sin fecha de baja, es decir activos
     */
    List<PuestoTrabajo> findByFechaBajaIsNull();
    
}
