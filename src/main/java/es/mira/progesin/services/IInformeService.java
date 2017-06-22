package es.mira.progesin.services;

import java.util.List;
import java.util.Map;

import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;

/**
 * Servicio de informes de inspección.
 * 
 * @author EZENTIS
 */
public interface IInformeService {
    
    /**
     * Guarda la información de un informe en la bdd.
     * 
     * @param informe solicitud creada o modificada
     * @return informe sincronizado
     */
    Informe save(Informe informe);
    
    /**
     * Guarda el informe y todas las subareas que hayan sido respondidas.
     * 
     * @param informe informe
     * @param mapaRespuestas respuestas
     * @return informe actualizado
     */
    Informe saveConRespuestas(Informe informe, Map<SubareaInforme, String> mapaRespuestas);
    
    /**
     * Recupera un informe con sus respuestas a partir de su id.
     * 
     * @param id id del informe
     * @return informe completo
     */
    Informe findOne(Long id);
    
    /**
     * Recupera todos los informes.
     * 
     * @return listado de informes
     */
    // TODO cambiar por criteria
    List<Informe> findAll();
    
}
