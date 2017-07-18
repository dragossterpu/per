package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;

/**
 * Repositorio de subareas de modelo de informe.
 * 
 * @author EZENTIS
 */
public interface ISubareaInformeRepository extends CrudRepository<SubareaInforme, Long> {
    /**
     * Elimina las subáreas de un área pasada como parámetro.
     * 
     * @param area Área de la que se desea eliminar las subáreas.
     */
    @Transactional(readOnly = false)
    void deleteByArea(AreaInforme area);
}
