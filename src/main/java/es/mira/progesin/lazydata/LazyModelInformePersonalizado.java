/**
 * 
 */
package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.services.IModeloInformePersonalizadoService;
import es.mira.progesin.web.beans.informes.InformePersonalizadoBusqueda;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Modelo para paginación desde servidor extendiendo el modelo LazyDataModel de Primefaces.
 * 
 * @author EZENTIS
 *
 */

@Setter
@Getter
public class LazyModelInformePersonalizado extends LazyDataModel<ModeloInformePersonalizado> implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Objeto que contiene los parámetros de búsqueda.
     */
    private InformePersonalizadoBusqueda busqueda;
    
    /**
     * Servicio.
     */
    private transient IModeloInformePersonalizadoService servicio;
    
    /**
     * Lista que sirve al modelo como fuente de datos.
     */
    private List<ModeloInformePersonalizado> datasource;
    
    /**
     * Constructor.
     * 
     * @param service Servicio a utilizar
     */
    public LazyModelInformePersonalizado(IModeloInformePersonalizadoService service) {
        this.servicio = service;
        this.busqueda = new InformePersonalizadoBusqueda();
    }
    
    /**
     * Sobreescritura del método getRowData para adaptarlo a nuestro modelo.
     * 
     * @param rowKey Fila que se ha seleccionado en la vista
     * @return Informe personalizado que corresponde a la fila seleccionada
     */
    @Override
    public ModeloInformePersonalizado getRowData(String rowKey) {
        ModeloInformePersonalizado informePersonalizado = null;
        for (ModeloInformePersonalizado inf : datasource) {
            if (inf.getId().toString().equals(rowKey))
                informePersonalizado = inf;
        }
        return informePersonalizado;
    }
    
    /**
     * Sobreestritura del método getRowKey para adaptarlo a nuestro modelo.
     * 
     * @param informe personalizado del que se desea recuperar la clave
     * @return Clave del informe personalizado
     */
    @Override
    public Object getRowKey(ModeloInformePersonalizado informe) {
        return informe.getId();
    }
    
    /**
     * Sobreescritura del método load para realizar la carga paginada.
     * 
     * @param first primer elemento de la paginación
     * @param pageSize número máximo de registros recuperados
     * @param sortField campo por el que se ordena
     * @param sortOrder sentido de la orientación
     * @param filters que se aplicarán a la búsqueda.
     * @return listado de registros que corresponden a la búsqueda
     */
    @Override
    public List<ModeloInformePersonalizado> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        List<ModeloInformePersonalizado> listado = null;
        if (busqueda != null) {
            this.setRowCount(servicio.getCountInformePersonalizadoCriteria(busqueda));
            listado = servicio.buscarInformePersonalizadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
            this.setDatasource(listado);
        } else {
            this.setRowCount(0);
        }
        return listado;
        
    }
    
}
