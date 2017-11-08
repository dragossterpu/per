package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.persistence.repositories.IAreaInformeRepository;

/**
 * Servicio de areas de informe.
 * 
 * @author EZENTIS
 */
@Service
public class AreaInformeService implements IAreaInformeService {
    
    /**
     * Repositorio de áreas de informe.
     */
    @Autowired
    private IAreaInformeRepository areaInformeRepository;
    
    /**
     * Servicio de subáreas de informe.
     */
    @Autowired
    private ISubareaInformeService subAreaInformeService;
    
    /**
     * Guarda en base de datos el área de un informe.
     * 
     * @param area Área a guardar
     * @param lista Lista de las subáreas que contiene.
     * @return Área salvada
     */
    @Override
    public AreaInforme save(AreaInforme area, List<SubareaInforme> lista) {
        area.setSubareas(null);
        AreaInforme resultado = areaInformeRepository.save(area);
        int orden = 1;
        for (SubareaInforme subArea : lista) {
            subArea.setOrden(orden++);
            subArea.setArea(area);
            subAreaInformeService.save(subArea);
        }
        area.setSubareas(lista);
        resultado = areaInformeRepository.save(area);
        
        return resultado;
    }
    
    /**
     * Borra área dependiendo del id de modelo.
     * 
     * @param modeloId Id del modelo del que se desean eliminar las áreas.
     */
    
    @Override
    public void deleteByModeloInformeId(Long modeloId) {
        areaInformeRepository.deleteByModeloInformeId(modeloId);
    }
    
    /**
     * recupera todas las áreas vinculadas a un modelo de informe.
     * 
     * @param modeloId Modelo del que se desea recuperar todas las áreas.
     * @return Listado de áreas recuperadas.
     */
    @Override
    public List<AreaInforme> findByModeloInformeId(Long modeloId) {
        return areaInformeRepository.findByModeloInformeId(modeloId);
    }
    
    /**
     * Recupera todas las áreas de informes.
     * 
     * @return Listado de áreas recuperadas.
     */
    @Override
    public List<AreaInforme> findAll() {
        return (List<AreaInforme>) areaInformeRepository.findAll();
    }
    
}
