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
    
    /**
     * Variable utilizada para inyectar el repositorio de departamentos.
     * 
     */
    @Autowired
    IDepartamentoRepository departamentoRepository;
    
    /**
     * Guarda o actualiza un departamento.
     * 
     * @param departamento a guardar
     * @return Departamento actualizado
     */
    @Override
    public Departamento save(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }
    
    /**
     * Busca todos los departamentos de trabajo dados de alta en la BBDD.
     * @return Iterable<Departamento> iterable con todos los departamentos en BBDD
     */
    @Override
    public Iterable<Departamento> findAll() {
        return departamentoRepository.findAll();
    }
    
    /**
     * Busca todos los departamentos sin fecha de baja, es decir activos.
     * @return List<Departamento> lista departamentos en alta
     */
    @Override
    public List<Departamento> findByFechaBajaIsNull() {
        return departamentoRepository.findByFechaBajaIsNull();
    }
    
}
