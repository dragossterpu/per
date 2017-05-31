package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;

/**
 * Repositorio de asignación de áreas de un cuestionario enviado a usuarios provisionales.
 * 
 * @author EZENTIS
 */
public interface IAreaUsuarioCuestEnvRepository extends CrudRepository<AreaUsuarioCuestEnv, Long> {
    
    /**
     * Busca las asignaciones de áreas a usuarios provisionales de un cuestionario enviado conociendo su identificador.
     * 
     * @param idCuestionarioEnviado identificador del cuestionario enviado
     * @return lista de asignaciones
     */
    @Query("SELECT DISTINCT a FROM AreaUsuarioCuestEnv a, CuestionarioEnvio ce WHERE a.idCuestionarioEnviado = ce.id AND a.idCuestionarioEnviado = :idCuestionarioEnviado AND (ce.fechaNoConforme IS NULL OR a.idArea IN (SELECT p.area FROM PreguntasCuestionario p, RespuestaCuestionario r WHERE p.id = r.respuestaId.pregunta AND r.fechaValidacion IS NULL))")
    List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviado(@Param("idCuestionarioEnviado") Long idCuestionarioEnviado);
    
    /**
     * Busca las asignaciones de áreas a un usuario provisional de un cuestionario enviado conociendo el username y el
     * identificador.
     * 
     * @param idCuestionarioEnviado identificador del cuestionario enviado.
     * @param usernameProv username del usuario provisional conectado
     * @return lista de asignaciones
     */
    @Query("SELECT DISTINCT a FROM AreaUsuarioCuestEnv a, CuestionarioEnvio ce WHERE a.idCuestionarioEnviado = ce.id AND a.idCuestionarioEnviado = :idCuestionarioEnviado AND a.usernameProv = :usernameProv AND (ce.fechaNoConforme IS NULL OR a.idArea IN (SELECT p.area FROM PreguntasCuestionario p, RespuestaCuestionario r WHERE p.id = r.respuestaId.pregunta AND r.fechaValidacion IS NULL))")
    List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviadoAndUsernameProv(
            @Param("idCuestionarioEnviado") Long idCuestionarioEnviado, @Param("usernameProv") String usernameProv);
    
    /**
     * Borra todas las asignaciones de areas de un cuestionario enviado.
     * 
     * @param idCuestionarioEnviado identificador del cuestionario enviado.
     */
    void deleteByIdCuestionarioEnviado(Long idCuestionarioEnviado);
    
}
