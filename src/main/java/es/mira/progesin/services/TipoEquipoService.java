package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.repositories.ITipoEquiposRepository;

@Service
public class TipoEquipoService implements ITipoEquipoService {
    @Autowired
    ITipoEquiposRepository tipoEquiposRepository;
    
    @Override
    public Iterable<TipoEquipo> findAll() {
        return tipoEquiposRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        tipoEquiposRepository.delete(id);
    }
    
    @Override
    @Transactional(readOnly = false)
    public TipoEquipo save(TipoEquipo entity) {
        return tipoEquiposRepository.save(entity);
    }
    
    @Override
    public TipoEquipo findByCodigoIgnoreCase(String codigo) {
        return tipoEquiposRepository.findByCodigoIgnoreCase(codigo);
    }
    
}
