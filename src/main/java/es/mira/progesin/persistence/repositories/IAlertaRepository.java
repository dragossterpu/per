package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Alerta;

/**
 * 
 * Repositorio de operaciones de BDD para la entity Alerta.
 * 
 * Es un repositorio de tipo CRUD
 * 
 * @author EZENTIS
 * 
 */

public interface IAlertaRepository extends CrudRepository<Alerta, Long> {
    
    /**
     * 
     * Devuelve una lista de alertas que no han sido dadas de baja.
     * 
     * @return List<Alerta>
     * 
     */
    
    List<Alerta> findByFechaBajaIsNull();
    
}
