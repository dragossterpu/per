package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.enums.AdministracionAccionEnum;

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
     * @param accion alta/baja/modificación
     * @return PuestoTrabajo actualizado
     */
    PuestoTrabajo save(PuestoTrabajo puesto, AdministracionAccionEnum accion);
    
    /**
     * Elimina un puesto de trabajo.
     * 
     * @param puesto puesto a eliminar.
     */
    void delete(PuestoTrabajo puesto);
    
    /**
     * Busca todos los puestos de trabajo dados de alta en la BBDD.
     * 
     * @return lista de puestos existentes
     */
    List<PuestoTrabajo> findAll();
    
}
