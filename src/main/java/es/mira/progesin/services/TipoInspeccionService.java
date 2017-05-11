package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.repositories.ITipoInspeccionRepository;

/**
 * Servicio para la gestión de tipos de inspección
 * @author EZENTIS
 *
 */
@Service
public class TipoInspeccionService implements ITipoInspeccionService {
    
    @Autowired
    ITipoInspeccionRepository tipoInspeccionRepository;
    
    @Override
    @Transactional(readOnly = false)
    public void borrarTipo(TipoInspeccion tipo) {
        tipoInspeccionRepository.delete(tipo);
    }
    
    @Override
    @Transactional(readOnly = false)
    public TipoInspeccion guardarTipo(TipoInspeccion entity) {
        return tipoInspeccionRepository.save(entity);
    }
    
    @Override
    public boolean existeByCodigoIgnoreCase(String codigo) {
        return tipoInspeccionRepository.existsByCodigoIgnoreCase(codigo);
    }
    
    @Override
    public List<TipoInspeccion> buscaTodos() {
        return (List<TipoInspeccion>) tipoInspeccionRepository.findAll();
    }
    
}
