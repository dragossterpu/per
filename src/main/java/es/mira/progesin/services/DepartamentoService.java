package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.repositories.IDepartamentoRepository;

/**
 * Implementación del servicio de departamentos.
 * 
 * @author EZENTIS
 *
 */
@Service
public class DepartamentoService implements IDepartamentoService {
    
    @Autowired
    IDepartamentoRepository departamentoRepository;
    
    @Override
    public Departamento save(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }
    
    @Override
    public Iterable<Departamento> findAll() {
        return departamentoRepository.findAll();
    }
    
    @Override
    public List<Departamento> findByFechaBajaIsNull() {
        return departamentoRepository.findByFechaBajaIsNull();
    }
    
}
