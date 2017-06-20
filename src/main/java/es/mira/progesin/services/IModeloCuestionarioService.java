package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;

/**
 * Interfaz del servicio de modelos de cuestionario.
 * 
 * @author EZENTIS
 */
public interface IModeloCuestionarioService {
    
    /**
     * Guarda la información de un cuestionario enviado en la bdd.
     * 
     * @param modelo Modelo de cuestionario a guardar
     * @return Modelo guardado
     */
    public ModeloCuestionario save(ModeloCuestionario modelo);
    
    /**
     * Recupera una lista con todos los modelos de cuestionario de la bdd.
     * 
     * @return Lista de todos los modelos
     */
    public List<ModeloCuestionario> findAll();
    
    /**
     * Devuelve un modelo de cuestionario identificado por su id.
     * 
     * @param id Identificador del modelo
     * @return Modelo recuperado
     */
    public ModeloCuestionario findOne(Integer id);
    
}
