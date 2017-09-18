package es.mira.progesin.services;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import es.mira.progesin.exceptions.ExcepcionRollback;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.informes.AsignSubareaInformeUser;
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
     * @param mapaAsignaciones asignaciones
     * @return informe actualizado
     */
    Informe guardarInforme(Informe informe, Map<SubareaInforme, String[]> mapaRespuestas,
            Map<SubareaInforme, String> mapaAsignaciones);
    
    /**
     * Guarda el informe y todas las subareas que hayan sido respondidas y elimina las asignaciones del usuario actual.
     * 
     * @param informe informe
     * @param mapaRespuestas respuestas
     * @param mapaAsignaciones asignaciones
     * @return informe actualizado
     */
    Informe desasignarInforme(Informe informe, Map<SubareaInforme, String[]> mapaRespuestas,
            Map<SubareaInforme, String> mapaAsignaciones);
    
    /**
     * Finaliza y guarda el informe y todas las subareas que hayan sido respondidas.
     * 
     * @param informe informe
     * @param mapaRespuestas respuestas
     * @param mapaAsignaciones asignaciones
     * @return informe actualizado
     */
    Informe finalizarInforme(Informe informe, Map<SubareaInforme, String[]> mapaRespuestas,
            Map<SubareaInforme, String> mapaAsignaciones);
    
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
    
    /**
     * Comprueba si existe una asignación de un subárea de un informe y sino la crea asociada al usuario del inspector
     * al que pertenece la sesión actual.
     * 
     * @param subarea subárea seleccionada
     * @param informe informe de la inspección en curso
     * @return asignación existente o la nueva creada
     */
    AsignSubareaInformeUser asignarSubarea(SubareaInforme subarea, Informe informe);
    
    /**
     * Comprueba si para un informe dado existen subáres sin responder.
     * 
     * @param idInforme id del informe
     * @return 0 si todas las subáreas han sido respondidas
     */
    Long buscaSubareasSinResponder(Long idInforme);
    
    /**
     * Crea el informe de una inspección a partir de un modelo.
     * 
     * @param inspeccion inspección a partir de la que se creará el informe
     * @param modeloInformePersonalizado modelo que se utilizará para crear el informe
     * @throws ExcepcionRollback Excepción lanzada si no se puede crear el informe
     */
    void crearInforme(Inspeccion inspeccion, ModeloInformePersonalizado modeloInformePersonalizado)
            throws ExcepcionRollback;
    
    /**
     * Comprueba si existen otros informes no anulados asociados a la inspeccion.
     * 
     * @param inspeccion inspeccion asociada al informe
     * @throws ExcepcionRollback Excepción que se lanza si ya existe un informe para esta inspección sin anular
     */
    void existsByInspeccionAndFechaBajaIsNull(Inspeccion inspeccion) throws ExcepcionRollback;
    
}
