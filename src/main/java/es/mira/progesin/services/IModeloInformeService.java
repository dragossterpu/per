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
    List<ModeloInforme> findAll();
    
    /**
     * Recupera una lista con todos los modelos de informe de la bdd que no tengan fecha de baja.
     * 
     * @return Lista de todos los modelos
     */
    List<ModeloInforme> findAllByFechaBajaIsNull();
    
    /**
     * Recupera un modelo de informe con sus areas y subareas a partir del id.
     * 
     * @param id id del modelo
     * @return modelo completo
     */
    ModeloInforme findDistinctById(Long id);
    
    /**
     * Elimina un modelo. La eliminación será lógica si existen modelos personalizados de este tipo o física si no es
     * así.
     * 
     * @param modelo Modelo a eliminar
     * @return modelo eliminado
     */
    ModeloInforme eliminarModelo(ModeloInforme modelo);
    
}
