package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Sugerencia;
import es.mira.progesin.persistence.repositories.ISugerenciaRepository;

@Service
public class SugerenciaService implements ISugerenciaService {
    @Autowired
    ISugerenciaRepository sugerenciaRepository;
    
    @Override
    @Transactional(readOnly = false)
    public void delete(Integer id) {
        sugerenciaRepository.delete(id);
    }
    
    @Override
    public Iterable<Sugerencia> findAll() {
        return sugerenciaRepository.findAll();
    }
    
    @Override
    public Sugerencia findOne(Integer id) {
        return sugerenciaRepository.findOne(id);
    }
    
    @Override
    @Transactional(readOnly = false)
    public Sugerencia save(Sugerencia entity) {
        return sugerenciaRepository.save(entity);
    }
    
}
