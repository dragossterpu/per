package es.mira.progesin.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;

/**
 * Interfaz del servicio de Áreas de Informe.
 * 
 * @author EZENTIS
 *
 */
public interface IAreaInformeService {
    
    /**
     * Guarda en base de datos el área de un informe.
     * 
     * @param area Área a guardar
     * @param lista Lista de las subáreas que contiene.
     * @return Área salvada
     */
    @Transactional(readOnly = false)
    public AreaInforme save(AreaInforme area, List<SubareaInforme> lista);
    
    /**
     * Borra área dependiendo del id de modelo.
     * 
     * @param modeloId Id del modelo del que se desean eliminar las áreas.
     */
    @Transactional(readOnly = false)
    public void deleteByModeloInformeId(Long modeloId);
    
    /**
     * recupera todas las áreas vinculadas a un modelo de informe.
     * 
     * @param modeloId Modelo del que se desea recuperar todas las áreas.
     * @return Listado de áreas recuperadas.
     */
    List<AreaInforme> findByModeloInformeId(Long modeloId);
    
}
