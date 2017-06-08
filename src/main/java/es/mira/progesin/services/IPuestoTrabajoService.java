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
     * Guarda la información de un puesto de trabajo en la bbdd.
     * 
     * @param puesto a guardar.
     * @return PuestoTrabajo actualizado
     */
    PuestoTrabajo save(PuestoTrabajo puesto);
    
    /**
     * Elimina un puesto de trabajo.
     * 
     * @param idPuesto clave del puesto a eliminar.
     */
    void delete(Long idPuesto);
    
    /**
     * Busca todos los puestos de trabajo dados de alta en la BBDD.
     * 
     * @return lista de puestos existentes
     */
    List<PuestoTrabajo> findAll();
    
}
