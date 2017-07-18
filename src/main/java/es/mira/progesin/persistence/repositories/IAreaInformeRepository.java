/**
 * 
 */
package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.informes.AreaInforme;

/**
 * Repositorio de areas de modelo de informe.
 * 
 * @author EZENTIS
 */
public interface IAreaInformeRepository extends CrudRepository<AreaInforme, Long> {
    
    /**
     * Borra área dependiendo del id de modelo.
     * 
     * @param modeloId Id del modelo del que se desean eliminar las áreas.
     */
    @Transactional(readOnly = false)
    void deleteByModeloInformeId(Long modeloId);
    
    /**
     * recupera todas las áreas vinculadas a un modelo de informe.
     * 
     * @param modeloId Modelo del que se desea recuperar todas las áreas.
     * @return Listado de áreas recuperadas.
     */
    List<AreaInforme> findByModeloInformeId(Long modeloId);
    
}
