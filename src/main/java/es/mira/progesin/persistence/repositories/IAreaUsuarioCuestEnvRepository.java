package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

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
    List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviado(Long idCuestionarioEnviado);
    
    List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviadoAndUsernameProv(Long idCuestionarioEnviado,
            String usernameProv);
    
    void deleteByIdCuestionarioEnviado(Long idCuestionarioEnviado);
    
}
