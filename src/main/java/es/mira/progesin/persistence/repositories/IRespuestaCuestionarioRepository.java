package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionarioId;

/**
 * Repositorio de respuestas de cuestionario.
 * 
 * @author EZENTIS
 *
 */
public interface IRespuestaCuestionarioRepository
        extends JpaRepository<RespuestaCuestionario, RespuestaCuestionarioId> {
    
    /**
     * Busca las respuestas asociadas a un cuestionario enviado.
     * 
     * @param cuestionarioEnviado cuestionario enviado
     * @return lista de respuestas
     */
    @EntityGraph(value = "RespuestaCuestionario.documentos", type = EntityGraphType.LOAD)
    List<RespuestaCuestionario> findDistinctByRespuestaIdCuestionarioEnviado(CuestionarioEnvio cuestionarioEnviado);
    
    /**
     * Busca las respuestas asociadas a un cuestionario enviado que no tienen fecha de validación y que son de unas
     * áreas concretas.
     * 
     * @param cuestionarioEnviado cuestionario enviado
     * @param listaAreasCuestionario lista de áreas
     * @return lista de respuestas
     */
    @EntityGraph(value = "RespuestaCuestionario.documentos", type = EntityGraphType.LOAD)
    List<RespuestaCuestionario> findDistinctByRespuestaIdCuestionarioEnviadoAndFechaValidacionIsNullAndRespuestaIdPreguntaAreaIn(
            CuestionarioEnvio cuestionarioEnviado, List<AreasCuestionario> listaAreasCuestionario);
    
}