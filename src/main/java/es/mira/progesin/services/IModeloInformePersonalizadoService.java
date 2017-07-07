package es.mira.progesin.services;

import java.util.Map;

import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;

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
    ModeloInformePersonalizado guardarInformePersonalizado(String nombre, ModeloInforme modelo, Map<AreaInforme, SubareaInforme[]> subareasSeleccionadas);
}
