package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.web.beans.InspeccionBusqueda;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Modelo para paginación desde servidor extendiendo el modelo LazyDataModel de Primefaces
 * 
 * @author Ezentis
 *
 */
@Setter
@Getter
public class LazyModelInspeccion extends LazyDataModel<Inspeccion> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private InspeccionBusqueda busqueda;
    
    private List<Inspeccion> datasource;
    
    private transient IInspeccionesService inspeccionesService;
    
    /**
     * 
     * Constructor del modelo que recibe el servicio como parámetro
     * @param inspeccionesService
     * 
     * 
     */
    public LazyModelInspeccion(IInspeccionesService inspeccionesService) {
        this.inspeccionesService = inspeccionesService;
        this.busqueda = new InspeccionBusqueda();
    }
    
    /**
     * 
     * Sobreescritura del método getRowData para que funcione con objetos de tipo Inspeccion
     * 
     * @param rowkey clave de la fila sobre la que se ha hecho click en la vista
     * @return registro que se corresponde con la clave recibida por parámetro
     * 
     */
    @Override
    public Inspeccion getRowData(String rowKey) {
        for (Inspeccion inspeccion : datasource) {
            if (inspeccion.getId().toString().equals(rowKey))
                return inspeccion;
        }
        return null;
    }
    
    /**
     * Sobrescritura del método getRowKey
     * 
     * @param objeto del que se desea obtener la clave
     * @return clave del objeto pasado como parámetro
     */
    
    @Override
    public Object getRowKey(Inspeccion inspeccion) {
        return inspeccion.getId();
    }
    
    /**
     * Sobreescritura del método Load para que funcione con un critera y sólo nos devuelva estríctamente el número de
     * registros solicitados.
     * 
     * @param first primer elemento que se desea recuperar
     * @param pageSize número máximo de inspecciones que deseamos recuperar por página
     * @param sortField columna por la que se ordenarán los resultados. Corresponde a la propiedad 'sortBy' de la
     * columna de la vista
     * @param sortOrder orden por el que se desea ordenar los resultados
     * @param filters mapa de filtros. Este valor no se utiliza en esta sobreescritura.
     * @return lista de inspecciones que corresponden a los criterios de búsqueda
     * 
     */
    
    @Override
    public List<Inspeccion> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        List<Inspeccion> listado = null;
        if (busqueda != null) {
            this.setRowCount(inspeccionesService.getCountInspeccionCriteria(busqueda));
            listado = inspeccionesService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder, busqueda);
            this.datasource = listado;
        } else {
            this.setRowCount(0);
        }
        return listado;
    }
    
}
