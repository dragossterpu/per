package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.repositories.IAreaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IModeloCuestionarioRepository;

@Service
public class ModeloCuestionarioService implements IModeloCuestionarioService {
    @Autowired
    IModeloCuestionarioRepository modeloCuestionarioRepository;
    
    @Autowired
    IAreaCuestionarioRepository areaCuestionarioRepository;
    
    @Override
    @Transactional(readOnly = false)
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
    
    @Override
    @Transactional(readOnly = false)
    public void saveModeloCuestionarioModificado(ModeloCuestionario modeloCuestionario,
            List<AreasCuestionario> listaAreasCuestionario, List<AreasCuestionario> listaAreasEliminacionFisica) {
        modeloCuestionarioRepository.save(modeloCuestionario);
        areaCuestionarioRepository.save(listaAreasCuestionario);
        listaAreasEliminacionFisica.removeAll(listaAreasCuestionario);
        if (listaAreasEliminacionFisica.isEmpty() == Boolean.FALSE) {
            areaCuestionarioRepository.delete(listaAreasEliminacionFisica);
        }
    }
    
}
