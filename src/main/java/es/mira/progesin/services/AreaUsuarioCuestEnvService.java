package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;
import es.mira.progesin.persistence.repositories.IAreaUsuarioCuestEnvRepository;

/**
 * @author EZENTIS
 */
@Service
public class AreaUsuarioCuestEnvService implements IAreaUsuarioCuestEnvService {
    
    @Autowired
    private IAreaUsuarioCuestEnvRepository areaUsuarioCuestEnvRepository;
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public List<AreaUsuarioCuestEnv> save(List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv) {
        return (List<AreaUsuarioCuestEnv>) areaUsuarioCuestEnvRepository.save(listaAreasUsuarioCuestEnv);
    }
    
    @Override
    public List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviado(Long idCuestionarioEnviado) {
        return areaUsuarioCuestEnvRepository.findByIdCuestionarioEnviado(idCuestionarioEnviado);
    }
    
    @Override
    public List<AreaUsuarioCuestEnv> findByIdCuestionarioEnviadoAndUsuarioProv(Long idCuestionarioEnviado,
            String usernameProv) {
        return areaUsuarioCuestEnvRepository.findByIdCuestionarioEnviadoAndUsernameProv(idCuestionarioEnviado,
                usernameProv);
    }
    
    @Override
    public void deleteByIdCuestionarioEnviado(Long idCuestionarioEnviado) {
        areaUsuarioCuestEnvRepository.deleteByIdCuestionarioEnviado(idCuestionarioEnviado);
        
    }
}
