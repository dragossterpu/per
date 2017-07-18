package es.mira.progesin.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;

/**
 * Servicio de subareas de informe.
 * 
 * @author EZENTIS
 */
public interface ISubareaInformeService {
    
    /**
     * Guarda en base de datos una subárea.
     * 
     * @param subarea subarea a guardar
     * @return subárea guardada
     */
    SubareaInforme save(SubareaInforme subarea);
    
    /**
     * Elimina las subáreas de las áreas de una lista pasada como parámetro.
     * 
     * @param listaAreas Áreas de las que se desea eliminar las subáreas.
     */
    @Transactional(readOnly = false)
    void deleteByArea(List<AreaInforme> listaAreas);
    
}
