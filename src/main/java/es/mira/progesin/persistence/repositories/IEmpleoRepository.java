package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Empleo;

/**
 * Repositorio para los empleos de un usuario.
 * 
 * @author EZENTIS
 *
 */
public interface IEmpleoRepository extends CrudRepository<Empleo, Long> {
    
    /**
     * Busca los empleos asociados a un cuerpo.
     * 
     * @param cuerpo seleccionado
     * @return lista de empleos por cuerpo
     */
    public List<Empleo> findByCuerpoOrderByDescripcionAsc(CuerpoEstado cuerpo);
    
    /**
     * Comprueba si existe un empleo para un cuerpo.
     * 
     * @param cuerpo Cuerpo del que se desea consultar si tiene empleo
     * @return true si existe cuerpo, false en caso contrario
     */
    public boolean existsByCuerpo(CuerpoEstado cuerpo);
    
}
