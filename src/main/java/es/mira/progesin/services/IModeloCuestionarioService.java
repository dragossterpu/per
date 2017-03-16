package es.mira.progesin.services;

import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;

/**
 * Interfaz que contiene los métodos necesarios para trabajar con la clase ModeloCuestionario
 * 
 * @author EZENTIS
 *
 */
public interface IModeloCuestionarioService {
    
    @Transactional(readOnly = false)
    public ModeloCuestionario save(ModeloCuestionario modeloCuestionario);
    
    public Iterable<ModeloCuestionario> findAll();
    
    public ModeloCuestionario findOne(Integer id);
    
}
