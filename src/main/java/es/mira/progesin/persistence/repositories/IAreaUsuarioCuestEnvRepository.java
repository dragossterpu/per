package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;

/**
 * 
 * 
 * @author EZENTIS
 */
public interface IAreaUsuarioCuestEnvRepository extends CrudRepository<AreaUsuarioCuestEnv, Long> {
    
    /**
     * Recupera para un cuestionario enviado una relación de areas y su respectivo usuario provisional asignado para
     * responder las preguntas de dicho area
     * 
     * @author EZENTIS
     * @param idCuestionarioEnviado
     * @return listado
     */
    @Query("SELECT DISTINCT a FROM AreaUsuarioCuestEnv a, CuestionarioEnvio ce WHERE a.idCuestionarioEnviado = ce.id AND a.idCuestionarioEnviado = :idCuestionarioEnviado AND ce.fechaNoConforme IS NULL OR (a.idArea IN (SELECT p.area FROM PreguntasCuestionario p, RespuestaCuestionario r WHERE p.id = r.respuestaId.pregunta AND r.fechaValidacion IS NULL))")
    List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviado(@Param("idCuestionarioEnviado") Long idCuestionarioEnviado);
    
    @Query("SELECT DISTINCT a FROM AreaUsuarioCuestEnv a, CuestionarioEnvio ce WHERE a.idCuestionarioEnviado = ce.id AND a.idCuestionarioEnviado = :idCuestionarioEnviado AND a.usernameProv = :usernameProv AND ce.fechaNoConforme IS NULL OR (a.idArea IN (SELECT p.area FROM PreguntasCuestionario p, RespuestaCuestionario r WHERE p.id = r.respuestaId.pregunta AND r.fechaValidacion IS NULL))")
    List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviadoAndUsernameProv(
            @Param("idCuestionarioEnviado") Long idCuestionarioEnviado, @Param("usernameProv") String usernameProv);
    
    void deleteByIdCuestionarioEnviado(Long idCuestionarioEnviado);
    
}
