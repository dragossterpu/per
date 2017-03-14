package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
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
    public void reemplazarAreaModelo(List<AreasCuestionario> listaAreas, AreasCuestionario area) {
        boolean noEncontrada = true;
        for (int i = 0; i < listaAreas.size() && noEncontrada; i++) {
            // El área es la misma pero la lista de preguntas que contienen no
            if (listaAreas.get(i).equals(area)) {
                listaAreas.set(i, area);
                noEncontrada = false;
            }
        }
    }
    
}
