package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.informes.ModeloInforme;

/**
 * Interfaz del servicio de modelos de informe.
 * 
 * @author EZENTIS
 */
public interface IModeloInformeService {
    
    /**
     * Recupera una lista con todos los modelos de informe de la bdd.
     * 
     * @return Lista de todos los modelos
     */
    public List<ModeloInforme> findAll();
    
    /**
     * Recupera un modelo de informe con sus areas y subareas a partir del id.
     * 
     * @param id id del modelo
     * @return modelo completo
     */
    public ModeloInforme findDistinctById(Long id);
    
}
