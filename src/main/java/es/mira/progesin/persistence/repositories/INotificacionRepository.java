package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Notificacion;

/**
 * 
 * Repositorio de operaciones de BDD para la entity Notificacion.
 * 
 * Es un repositorio de tipo CRUD
 * 
 * @author EZENTIS
 * 
 */

public interface INotificacionRepository extends CrudRepository<Notificacion, Long> {
    
    /**
     * 
     * Devuelve una lista de notificaciones que no han sido dadas de baja.
     * 
     * @return Listado de notificaciones no dadas de baja
     * 
     */
    
    List<Notificacion> findByFechaBajaIsNull();
    
}
