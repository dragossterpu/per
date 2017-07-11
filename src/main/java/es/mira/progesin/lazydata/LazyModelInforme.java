/**
 * 
 */
package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.services.IInformeService;
import es.mira.progesin.web.beans.informes.InformeBusqueda;
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
public class LazyModelInforme extends LazyDataModel<Informe> implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Objeto que contiene los parámetros de búsqueda.
     */
    private InformeBusqueda busqueda;
    
    /**
     * Servicio.
     */
    private transient IInformeService servicio;
    
    /**
     * Lista que sirve al modelo como fuente de datos.
     */
    private List<Informe> datasource;
    
    /**
     * Constructor.
     * 
     * @param service Servicio a utilizar
     */
    public LazyModelInforme(IInformeService service) {
        this.servicio = service;
        this.busqueda = new InformeBusqueda();
    }
    
    /**
     * Sobreescritura del método getRowData para adaptarlo a nuestro modelo.
     * 
     * @param rowKey Fila que se ha seleccionado en la vista
     * @return Informe que corresponde a la fila seleccionada
     */
    @Override
    public Informe getRowData(String rowKey) {
        Informe informe = null;
        for (Informe inf : datasource) {
            if (inf.getId().toString().equals(rowKey))
                informe = inf;
        }
        return informe;
    }
    
    /**
     * Sobreestritura del método getRowKey para adaptarlo a nuestro modelo.
     * 
     * @param informe del que se desea recuperar la clave
     * @return Clave del informe
     */
    @Override
    public Object getRowKey(Informe informe) {
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
    public List<Informe> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        List<Informe> listado = null;
        if (busqueda != null) {
            this.setRowCount(servicio.getCountInformeCriteria(busqueda));
            listado = servicio.buscarInformeCriteria(first, pageSize, sortField, sortOrder, busqueda);
            this.setDatasource(listado);
        } else {
            this.setRowCount(0);
        }
        return listado;
        
    }
    
}
