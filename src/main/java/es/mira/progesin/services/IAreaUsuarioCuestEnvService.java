package es.mira.progesin.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;

/**
 * Servicio para la gestión de la asignación de áreas de un cuestionario enviado a usuarios provisionales para que
 * cumplimenten las preguntas de las mismas.
 * 
 * @author EZENTIS
 */
public interface IAreaUsuarioCuestEnvService {
    
    /**
     * Guarda las asignaciones de áreas a usuarios provisionales de un cuestionario.
     * 
     * @param listaAreasUsuarioCuestEnv lista de asignaciones
     * @return lista de asignaciones con id
     */
    @Transactional(readOnly = false)
    List<AreaUsuarioCuestEnv> save(List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv);
    
    /**
     * Busca las asignaciones de áreas a usuarios provisionales de un cuestionario enviado conociendo su identificador.
     * 
     * @param idCuestionarioEnviado identificador del cuestionario enviado
     * @return lista de asignaciones
     */
    List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviado(Long idCuestionarioEnviado);
    
    /**
     * Busca las asignaciones de áreas a un usuario provisional de un cuestionario enviado conociendo el username y el
     * identificador.
     * 
     * @param id identificador del cuestionario enviado.
     * @param usernameUsuarioActual username del usuario provisional conectado
     * @return lista de asignaciones
     */
    List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviadoAndUsuarioProv(Long id, String usernameUsuarioActual);
    
    /**
     * Borra todas las asignaciones de areas de un cuestionario enviado.
     * 
     * @param id identificador del cuestionario enviado.
     */
    @Transactional(readOnly = false)
    void deleteByIdCuestionarioEnviado(Long id);
    
}
