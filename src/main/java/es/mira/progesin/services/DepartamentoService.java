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
 */
@Service
public class DepartamentoService implements IDepartamentoService {
    
    /**
     * Variable utilizada para inyectar el repositorio de departamentos.
     */
    @Autowired
    private IDepartamentoRepository departamentoRepository;
    
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
     * @return lista con todos los departamentos en BBDD
     */
    @Override
    public List<Departamento> findAll() {
        return (List<Departamento>) departamentoRepository.findAll();
    }
    
    /**
     * Elimina un departamento.
     * 
     * @param id clave de departamento
     */
    @Override
    public void delete(Long id) {
        departamentoRepository.delete(id);
    }
    
}
