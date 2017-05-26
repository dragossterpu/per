package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.repositories.ITipoEquiposRepository;

/**
 * 
 * Implementación del servicio de tipos de equipo.
 * 
 * @author EZENTIS
 *
 */
@Service
public class TipoEquipoService implements ITipoEquipoService {
    @Autowired
    ITipoEquiposRepository tipoEquiposRepository;
    
    @Override
    public List<TipoEquipo> findAll() {
        return tipoEquiposRepository.findAllByOrderByIdAsc();
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
