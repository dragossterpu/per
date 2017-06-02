package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Informe;

/**
 * Repositorio de informes de inspecciones.
 * 
 * @author EZENTIS
 */
public interface IInformeRepository extends CrudRepository<Informe, Long> {
    
}
