package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;

/**
 * Interfaz del servicio de modelos de cuestionario
 * 
 * @author EZENTIS
 */
public interface IModeloCuestionarioService {
    
    /**
     * Guarda la información de un cuestionario enviado en la bdd.
     * 
     * @author Ezentis
     * @param modelo de cuestionario
     * @return modelo con id
     */
    public ModeloCuestionario save(ModeloCuestionario modelo);
    
    /**
     * Recupera una lista con todos los modelos de cuestionario de la bdd.
     * 
     * @author Ezentis
     * @return lista de modelos
     */
    public List<ModeloCuestionario> findAll();
    
}
