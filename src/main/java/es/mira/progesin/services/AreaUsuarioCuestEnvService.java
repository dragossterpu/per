package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;
import es.mira.progesin.persistence.repositories.IAreaUsuarioCuestEnvRepository;

/**
 * Servicio de aéreas/usuario para cuestionarios enviados.
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
     * Guarda las áreas de un cuestionario asignadas a un usuario provisional.
     * 
     * @param listaAreasUsuarioCuestEnv areas de un cuestionario asignadas a un usuario provisional
     * @return areas de un cuestionario asignadas a usuarios provisionales
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public List<AreaUsuarioCuestEnv> save(List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv) {
        return (List<AreaUsuarioCuestEnv>) areaUsuarioCuestEnvRepository.save(listaAreasUsuarioCuestEnv);
    }
    
    /**
     * Busca un determinado cuestionario enviado conociendo su identificador.
     * 
     * @param idCuestionarioEnviado identificador del cuestionario enviado
     * @return relacion de areas de un cuestionario enviado asignadas a usuarios provisionales
     */
    @Override
    public List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviado(Long idCuestionarioEnviado) {
        return areaUsuarioCuestEnvRepository.findByIdCuestionarioEnviado(idCuestionarioEnviado);
    }
    
    /**
     * Busca un determinado cuestionario enviado conociendo el identificador del cuestionario y el usuario provisional.
     * 
     * @param idCuestionarioEnviado identificador del cuestionario enviado.
     * @param usernameProv login del usuario provisional conectado
     * @return lista de cuestionarios enviados
     */
    @Override
    public List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviadoAndUsuarioProv(Long idCuestionarioEnviado,
            String usernameProv) {
        return areaUsuarioCuestEnvRepository.findByIdCuestionarioEnviadoAndUsernameProv(idCuestionarioEnviado,
                usernameProv);
    }
    
    /**
     * Borra un cuestionario enviado conociendo su identificador.
     * 
     * @param id identificador del cuestionario enviado.
     */
    @Override
    public void deleteByIdCuestionarioEnviado(Long idCuestionarioEnviado) {
        areaUsuarioCuestEnvRepository.deleteByIdCuestionarioEnviado(idCuestionarioEnviado);
        
    }
}
