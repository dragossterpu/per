package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionarioId;

public interface IRespuestaCuestionarioRepository
        extends JpaRepository<RespuestaCuestionario, RespuestaCuestionarioId> {
    
    @Query("select r from RespuestaCuestionario r where r.respuestaId.cuestionarioEnviado = :cuestionarioEnviado and (r.respuestaId.pregunta.tipoRespuesta like 'TABLA%' or r.respuestaId.pregunta.tipoRespuesta like 'MATRIZ%')")
    List<RespuestaCuestionario> findRespuestasTablaMatrizByCuestionarioEnviado(
            @Param("cuestionarioEnviado") CuestionarioEnvio cuestionarioEnviado);
    
    @EntityGraph(value = "RespuestaCuestionario.documentos", type = EntityGraphType.LOAD)
    List<RespuestaCuestionario> findDistinctByRespuestaIdCuestionarioEnviado(CuestionarioEnvio cuestionarioEnviado);
    
    @EntityGraph(value = "RespuestaCuestionario.documentos", type = EntityGraphType.LOAD)
    List<RespuestaCuestionario> findDistinctByRespuestaIdCuestionarioEnviadoAndFechaValidacionIsNull(
            CuestionarioEnvio cuestionarioEnviado);
    
    @EntityGraph(value = "RespuestaCuestionario.documentos", type = EntityGraphType.LOAD)
    List<RespuestaCuestionario> findDistinctByRespuestaIdCuestionarioEnviadoAndFechaValidacionIsNullAndRespuestaIdPreguntaAreaIn(
            CuestionarioEnvio cuestionarioEnviado, List<AreasCuestionario> listaAreasCuestionario);
    
}