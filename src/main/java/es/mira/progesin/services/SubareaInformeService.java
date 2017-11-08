package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.persistence.repositories.ISubareaInformeRepository;

/**
 * Servicio de subareas de informe.
 * 
 * @author EZENTIS
 */
@Service
public class SubareaInformeService implements ISubareaInformeService {
    
    /**
     * Repositorio de subareas de informe.
     */
    @Autowired
    private ISubareaInformeRepository subareaInformeRepository;
    
    /**
     * Guarda en base de datos una subárea.
     * 
     * @param subarea subarea a guardar
     * @return subárea guardada
     */
    @Override
    public SubareaInforme save(SubareaInforme subarea) {
        return subareaInformeRepository.save(subarea);
    }
    
    /**
     * Elimina las subáreas de las áreas de una lista pasada como parámetro.
     * 
     * @param listaAreas Áreas de las que se desea eliminar las subáreas.
     */
    @Override
    public void deleteByArea(List<AreaInforme> listaAreas) {
        for (AreaInforme area : listaAreas) {
            subareaInformeRepository.deleteByArea(area);
        }
        
    }
    
    /**
     * Devuelve un listado de las subáreas pertenecientes a un área recibida como parámetro.
     * 
     * @param area Área de la que se desean recuperar las subáreas.
     * @return Listado de subáreas.
     */
    @Override
    public List<SubareaInforme> findByArea(AreaInforme area) {
        return subareaInformeRepository.findByArea(area);
    }
    
    /**
     * Devuelve un listado de todas las subáreas.
     * 
     * @return Listado de subáreas.
     */
    @Override
    public List<SubareaInforme> findAll() {
        return (List<SubareaInforme>) subareaInformeRepository.findAll();
    }
}
