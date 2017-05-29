package es.mira.progesin.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;

/**
 * 
 * Servicio para la gestión de las áreas de un cuestionario enviado a usuarios provisionales.
 * 
 * @author EZENTIS
 */
public interface IAreaUsuarioCuestEnvService {
    /**
     * Guarda las áreas de un cuestionario asignadas a un usuario provisional.
     * 
     * @param listaAreasUsuarioCuestEnv areas de un cuestionario asignadas a un usuario provisional
     * @return areas de un cuestionario asignadas a usuarios provisionales
     */
    @Transactional(readOnly = false)
    List<AreaUsuarioCuestEnv> save(List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv);
    
    /**
     * Busca un determinado cuestionario enviado conociendo su identificador.
     * 
     * @param idCuestionarioEnviado identificador del cuestionario enviado
     * @return relacion de areas de un cuestionario enviado asignadas a usuarios provisionales
     */
    List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviado(Long idCuestionarioEnviado);
    
    /**
     * Busca un determinado cuestionario enviado conociendo el identificador del cuestionario y el usuario provisional.
     * 
     * @param id identificador del cuestionario enviado.
     * @param usernameUsuarioActual login del usuario provisional conectado
     * @return lista de cuestionarios enviados
     */
    List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviadoAndUsuarioProv(Long id, String usernameUsuarioActual);
    
    /**
     * Borra un cuestionario enviado conociendo su identificador.
     * 
     * @param id identificador del cuestionario enviado.
     */
    @Transactional(readOnly = false)
    void deleteByIdCuestionarioEnviado(Long id);
    
}
