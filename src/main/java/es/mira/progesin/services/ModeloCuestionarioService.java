package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.repositories.IAreaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IModeloCuestionarioRepository;

/**
 * Implementación del servicio que gestiona los modelos de cuestionario.
 * 
 * @author EZENTIS
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
    public List<ModeloCuestionario> findAll() {
        return (List<ModeloCuestionario>) modeloCuestionarioRepository.findAll();
    }
    
}
