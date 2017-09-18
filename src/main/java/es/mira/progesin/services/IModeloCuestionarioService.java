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
    ModeloCuestionario save(ModeloCuestionario modelo);
    
    /**
     * Recupera una lista con todos los modelos de cuestionario de la bdd.
     * 
     * @return Lista de todos los modelos
     */
    List<ModeloCuestionario> findAllByFechaBajaIsNull();
    
    /**
     * Devuelve un modelo de cuestionario identificado por su id.
     * 
     * @param id Identificador del modelo
     * @return Modelo recuperado
     */
    ModeloCuestionario findOne(Integer id);
    
    /**
     * Elimina un modelo pasado como parámetro.
     * 
     * @param modelo Modelo a eliminar
     * @return Modelo eliminado
     */
    ModeloCuestionario eliminarModelo(ModeloCuestionario modelo);
    
    /**
     * Visualiza un modelo de cuestionario.
     * 
     * @param id Modelo a visualizar.
     * @return Modelo visualizado
     */
    ModeloCuestionario visualizarModelo(Integer id);
    
}
