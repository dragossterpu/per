package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.PuestoTrabajo;

public interface IPuestoTrabajoService {
    
    /**
     * @param puesto
     * @return PuestoTrabajo actualizado
     */
    PuestoTrabajo save(PuestoTrabajo puesto);
    
    /**
     * Busca todos los puestos de trabajo dados de alta en la BBDD
     * @return Iterable<PuestoTrabajo>
     */
    Iterable<PuestoTrabajo> findAll();
    
    /**
     * puestos de trabajo sin fecha de baja, es decir activos
     * @return List<PuestoTrabajo>
     */
    List<PuestoTrabajo> findByFechaBajaIsNull();
}
