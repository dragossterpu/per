package es.mira.progesin.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;

/**
 * @author EZENTIS
 */
public interface IAreaUsuarioCuestEnvService {
    /**
     * @param listaAreasUsuarioCuestEnv
     * @return areas de un cuestionario asignadas a usuarios provisionales
     */
    @Transactional(readOnly = false)
    List<AreaUsuarioCuestEnv> save(List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv);
    
    /**
     * @param cuestionarioEnviado
     * @return relacion de areas de un cuestionario enviado asignadas a usuarios provisionales
     */
    List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviado(Long idCuestionarioEnviado);
    
    List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviadoAndUsuarioProv(Long id, String usernameUsuarioActual);
    
    @Transactional(readOnly = false)
    void deleteByIdCuestionarioEnviado(Long id);
    
}
