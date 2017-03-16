package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.repositories.IAreaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IModeloCuestionarioRepository;

/**
 * @author EZENTIS
 *
 */
@Service
public class ModeloCuestionarioService implements IModeloCuestionarioService {
    @Autowired
    IModeloCuestionarioRepository modeloCuestionarioRepository;
    
    @Autowired
    IAreaCuestionarioRepository areaCuestionarioRepository;
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ModeloCuestionario save(ModeloCuestionario modeloCuestionario) {
        return modeloCuestionarioRepository.save(modeloCuestionario);
    }
    
    @Override
    public Iterable<ModeloCuestionario> findAll() {
        return modeloCuestionarioRepository.findAll();
    }
    
    @Override
    public ModeloCuestionario findOne(Integer id) {
        return modeloCuestionarioRepository.findOne(id);
    }
    
}
