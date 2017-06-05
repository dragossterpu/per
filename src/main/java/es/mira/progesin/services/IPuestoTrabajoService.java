package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.PuestoTrabajo;

/**
 * Interfaz del servicio que gestiona los puestos de trabajo.
 * 
 * @author EZENTIS
 *
 */
public interface IPuestoTrabajoService {
    
    /**
     * @param puesto a guardar.
     * @return PuestoTrabajo actualizado
     */
    PuestoTrabajo save(PuestoTrabajo puesto);
    
    /**
     * Busca todos los puestos de trabajo dados de alta en la BBDD.
     * @return Iterable<PuestoTrabajo> lista de puestos existentes
     */
    Iterable<PuestoTrabajo> findAll();
    
    /**
     * puestos de trabajo sin fecha de baja, es decir activos.
     * @return List<PuestoTrabajo> lista de puestos no dados de baja
     */
    List<PuestoTrabajo> findByFechaBajaIsNull();
}
