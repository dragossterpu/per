package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.informes.RespuestaInforme;
import es.mira.progesin.persistence.entities.informes.RespuestaInformeId;

/**
 * Repositorio de respuestas a las subareas de informe.
 * 
 * @author EZENTIS
 */
public interface IRespuestaInformeRepository extends CrudRepository<RespuestaInforme, RespuestaInformeId> {
    
}
