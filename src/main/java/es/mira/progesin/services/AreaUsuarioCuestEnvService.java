package es.mira.progesin.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.repositories.IAreaUsuarioCuestEnvRepository;

/**
 * Servicio para la gestión de la asignación de áreas de un cuestionario enviado a usuarios provisionales para que
 * cumplimenten las preguntas de las mismas.
 * 
 * @author EZENTIS
 */
@Service
public class AreaUsuarioCuestEnvService implements IAreaUsuarioCuestEnvService {
    
    /**
     * Repositorio de áreas/usuario de cuestionarios enviados.
     */
    @Autowired
    private IAreaUsuarioCuestEnvRepository areaUsuarioCuestEnvRepository;
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private IUserService userService;
    
    /**
     * Guarda las asignaciones de áreas a usuarios provisionales de un cuestionario.
     * 
     * @param listaAreasUsuarioCuestEnv lista de asignaciones
     * @return lista de asignaciones con id
     */
    @Override
    @Transactional
    public List<AreaUsuarioCuestEnv> save(List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv) {
        return (List<AreaUsuarioCuestEnv>) areaUsuarioCuestEnvRepository.save(listaAreasUsuarioCuestEnv);
    }
    
    /**
     * Busca las asignaciones de áreas a usuarios provisionales de un cuestionario enviado conociendo su identificador.
     * 
     * @param idCuestionarioEnviado identificador del cuestionario enviado
     * @return lista de asignaciones
     */
    @Override
    public List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviado(Long idCuestionarioEnviado) {
        return areaUsuarioCuestEnvRepository.findByIdCuestionarioEnviado(idCuestionarioEnviado);
    }
    
    /**
     * Busca las asignaciones de áreas a un usuario provisional de un cuestionario enviado conociendo el username y el
     * identificador.
     * 
     * @param idCuestionarioEnviado identificador del cuestionario enviado.
     * @param usernameProv username del usuario provisional conectado
     * @return lista de asignaciones
     */
    @Override
    public List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviadoAndUsuarioProv(Long idCuestionarioEnviado,
            String usernameProv) {
        return areaUsuarioCuestEnvRepository.findByIdCuestionarioEnviadoAndUsernameProv(idCuestionarioEnviado,
                usernameProv);
    }
    
    /**
     * Borra todas las asignaciones de areas de un cuestionario enviado.
     * 
     * @param idCuestionarioEnviado identificador del cuestionario enviado.
     */
    @Override
    public void deleteByIdCuestionarioEnviado(Long idCuestionarioEnviado) {
        areaUsuarioCuestEnvRepository.deleteByIdCuestionarioEnviado(idCuestionarioEnviado);
        
    }
    
    /**
     * Asigna las áreas a los usuarios provisionales elegidos por el principal.
     * 
     * @param listaAreasUsuarioCuestEnv lista de áreas a asignar
     * @return lista de areas asignadas actualizadas
     */
    @Override
    public List<AreaUsuarioCuestEnv> asignarAreasUsuarioYActivar(List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv) {
        List<String> usuariosAsignados = new ArrayList<>();
        listaAreasUsuarioCuestEnv.forEach(areaUsuario -> {
            if (usuariosAsignados.contains(areaUsuario.getUsernameProv()) == Boolean.FALSE) {
                usuariosAsignados.add(areaUsuario.getUsernameProv());
            }
        });
        usuariosAsignados.forEach(usuarioProv -> userService.cambiarEstado(usuarioProv, EstadoEnum.ACTIVO));
        
        return (List<AreaUsuarioCuestEnv>) areaUsuarioCuestEnvRepository.save(listaAreasUsuarioCuestEnv);
    }
}
