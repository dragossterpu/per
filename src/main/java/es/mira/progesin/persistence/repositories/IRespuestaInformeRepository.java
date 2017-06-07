package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.RespuestaInforme;
import es.mira.progesin.persistence.entities.RespuestaInformeId;

/**
 * Repositorio de respuestas a las subareas de informe.
 * 
 * @author EZENTIS
 */
public interface IRespuestaInformeRepository extends CrudRepository<RespuestaInforme, RespuestaInformeId> {
    
}
