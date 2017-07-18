package es.mira.progesin.services;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.web.beans.informes.InformeBusqueda;

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
    Informe saveConRespuestas(Informe informe, Map<SubareaInforme, String[]> mapaRespuestas);
    
    /**
     * Finaliza y guarda el informe y todas las subareas que hayan sido respondidas.
     * 
     * @param informe informe
     * @param mapaRespuestas respuestas
     * @return informe actualizado
     */
    Informe finalizarSaveConRespuestas(Informe informe, Map<SubareaInforme, String[]> mapaRespuestas);
    
    /**
     * Recupera un informe con sus respuestas a partir de su id.
     * 
     * @param id id del informe
     * @return informe completo
     */
    Informe findConRespuestas(Long id);
    
    /**
     * Método que devuelve la lista de informes en una consulta basada en criteria.
     * 
     * @param informeBusqueda objeto con los criterios de búsqueda
     * @param first primer elemento de la consulta
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @return la lista de informes.
     */
    List<Informe> buscarInformeCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            InformeBusqueda informeBusqueda);
    
    /**
     * Método que devuelve el número de informes en una consulta basada en criteria.
     * 
     * @param informeBusqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de la consulta criteria.
     */
    int getCountInformeCriteria(InformeBusqueda informeBusqueda);
    
    /**
     * Comprobar si hay algún informe basado en éste modelo personalizado.
     * 
     * @param modeloPersonalizado modelo de informe personalizado
     * @return verdadero o falso
     */
    boolean existsByModeloPersonalizado(ModeloInformePersonalizado modeloPersonalizado);
    
}
