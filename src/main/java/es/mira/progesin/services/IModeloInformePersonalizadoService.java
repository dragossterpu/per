package es.mira.progesin.services;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.web.beans.informes.InformePersonalizadoBusqueda;

/**
 * Interfaz del servicio de modelos de informe personalizados.
 * 
 * @author EZENTIS
 *
 */
public interface IModeloInformePersonalizadoService {
    
    /**
     * Busca el informe personalizado cargando las subareas en el objeto devuelto.
     * 
     * @param idInformePersonalizado id del informe personalizado
     * @return modelo de informe personalizado
     */
    ModeloInformePersonalizado findModeloPersonalizadoCompleto(Long idInformePersonalizado);
    
    /**
     * Guarda el modelo de informe personalizado en BBDD.
     * 
     * @param nombre nombre del informe personalizado
     * @param modelo modelo a partir del que se ha creado
     * @param subareasSeleccionadas subareas que formarán parte del informe personalizado
     * @return mode modelo de informe personalizado
     */
    ModeloInformePersonalizado guardarInformePersonalizado(String nombre, ModeloInforme modelo,
            Map<AreaInforme, SubareaInforme[]> subareasSeleccionadas);
    
    /**
     * Método que devuelve la lista de modelos de cuestionario personalizados en una consulta basada en criteria.
     * 
     * @param informePersonalizadoBusqueda objeto con los criterios de búsqueda
     * @param first primer elemento de la consulta
     * @param pageSize tamaño de cada página de resultados
     * @param sortField campo por el que se ordenan los resultados
     * @param sortOrder sentido de la ordenacion (ascendente/descendente)
     * @return la lista de modelos de informes personalizados.
     */
    List<ModeloInformePersonalizado> buscarInformePersonalizadoCriteria(int first, int pageSize, String sortField,
            SortOrder sortOrder, InformePersonalizadoBusqueda informePersonalizadoBusqueda);
    
    /**
     * Método que devuelve el número de modelos de cuestionario personalizados en una consulta basada en criteria.
     * 
     * @param informePersonalizadoBusqueda objeto con parámetros de búsqueda
     * @return devuelve el número de registros de la consulta criteria.
     */
    int getCountInformePersonalizadoCriteria(InformePersonalizadoBusqueda informePersonalizadoBusqueda);
    
    /**
     * Elimina o anula un modelo personalizado de informe ya usado.
     * 
     * @param modeloPersonalizado modelo seleccionado
     * @return modelo sincronizado
     */
    ModeloInformePersonalizado eliminarModeloPersonalizado(ModeloInformePersonalizado modeloPersonalizado);
}
