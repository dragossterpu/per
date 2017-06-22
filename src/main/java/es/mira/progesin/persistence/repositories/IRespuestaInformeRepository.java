package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.RespuestaInforme;
import es.mira.progesin.persistence.entities.informes.RespuestaInformeId;

/**
 * Repositorio de respuestas a las subareas de informe.
 * 
 * @author EZENTIS
 */
public interface IRespuestaInformeRepository extends JpaRepository<RespuestaInforme, RespuestaInformeId> {
    
    /**
     * Recupera las respuestas de un informe dado.
     * 
     * @param informe seleccionado
     * @return lista de respuestas
     */
    List<RespuestaInforme> findByInforme(Informe informe);
    
}
