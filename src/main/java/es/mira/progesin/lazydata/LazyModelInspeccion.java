package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.ArrayList;
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
 * Modelo para paginación desde servidor extendiendo el modelo LazyDataModel de Primefaces.
 * 
 * @author EZENTIS
 *
 */
@Setter
@Getter
public class LazyModelInspeccion extends LazyDataModel<Inspeccion> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Objeto que contiene los parámetros de búsqueda.
     */
    private InspeccionBusqueda busqueda;
    
    /**
     * Servicio a usar.
     */
    private List<Inspeccion> datasource;
    
    /**
     * Lista que sirve al modelo como fuente de datos.
     */
    private transient IInspeccionesService inspeccionesService;
    
    /**
     * Constructor del modelo que recibe el servicio como parámetro.
     * @param inspeccionesServicio Servicio que se usará
     * 
     */
    public LazyModelInspeccion(IInspeccionesService inspeccionesServicio) {
        this.inspeccionesService = inspeccionesServicio;
        this.busqueda = new InspeccionBusqueda();
    }
    
    /**
     * 
     * Sobreescritura del método getRowData para que funcione con objetos de tipo Inspeccion.
     * 
     * @param rowKey Clave de la fila sobre la que se ha hecho click en la vista
     * @return registro que se corresponde con la clave recibida por parámetro
     * 
     */
    @Override
    public Inspeccion getRowData(String rowKey) {
        Inspeccion ins = null;
        for (Inspeccion inspeccion : datasource) {
            if (inspeccion.getId().toString().equals(rowKey))
                ins = inspeccion;
        }
        return ins;
    }
    
    /**
     * Sobrescritura del método getRowKey.
     * 
     * @param inspeccion Objeto del que se desea obtener la clave
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
            busqueda.setSelectedAll(false);
            
            this.setRowCount(inspeccionesService.getCountInspeccionCriteria(busqueda));
            listado = inspeccionesService.buscarInspeccionPorCriteria(first, pageSize, sortField, sortOrder, busqueda);
            this.datasource = listado;
            
            if (busqueda.isAsociar() && busqueda.getInspeccionesSeleccionadas() != null) {
                List<Inspeccion> inspeccionesSeleccionadas = new ArrayList<>();
                inspeccionesSeleccionadas.addAll(busqueda.getInspeccionesSeleccionadas());
                List<Inspeccion> inspeccionesBuscadas = new ArrayList<>();
                inspeccionesBuscadas.addAll(this.datasource);
                inspeccionesBuscadas.removeAll(inspeccionesSeleccionadas);
                
                if (inspeccionesBuscadas.isEmpty()) {
                    busqueda.setSelectedAll(true);
                }
            }
        } else {
            this.setRowCount(0);
        }
        return listado;
    }
    
}
