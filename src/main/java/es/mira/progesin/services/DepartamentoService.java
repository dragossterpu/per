package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.repositories.IDepartamentoRepository;
import es.mira.progesin.web.beans.ApplicationBean;

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
     * Variable usada para actualizar la lista cargada en el contexto de la aplicación.
     */
    @Autowired
    private ApplicationBean applicationBean;
    
    /**
     * Guarda o actualiza un departamento.
     * 
     * @param departamento a guardar
     * @return Departamento actualizado
     */
    @Override
    public Departamento save(Departamento departamento) {
        Departamento departamentoActualizado = departamentoRepository.save(departamento);
        applicationBean.setListaDepartamentos(findAllByOrderByDescripcionAsc());
        return departamentoActualizado;
        
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
     * @param departamento departamento a eliminar
     */
    @Override
    public void delete(Departamento departamento) {
        departamentoRepository.delete(departamento);
        applicationBean.setListaDepartamentos(findAllByOrderByDescripcionAsc());
    }
    
    /**
     * Busca todos los departamentos de trabajo dados de alta en la BBDD ordenados por su descripción.
     * 
     * @return lista con todos los departamentos en BBDD
     */
    @Override
    public List<Departamento> findAllByOrderByDescripcionAsc() {
        return departamentoRepository.findAllByOrderByDescripcionAsc();
    }
    
}
