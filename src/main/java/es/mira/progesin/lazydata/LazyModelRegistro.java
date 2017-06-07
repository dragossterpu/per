package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.web.beans.RegActividadBusqueda;
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
public class LazyModelRegistro extends LazyDataModel<RegistroActividad> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Objeto que contiene los parámetros de búsqueda.
     */
    private RegActividadBusqueda busqueda;
    
    /**
     * Lista que sirve al modelo como fuente de datos.
     */
    private List<RegistroActividad> datasource;
    
    /**
     * Servicio a usar.
     */
    private transient IRegistroActividadService registroActividadService;
    
    /**
     * 
     * Constructor del modelo que recibe el servicio como parámetro.
     * 
     * @param servicio Servicio a emplear
     */
    public LazyModelRegistro(IRegistroActividadService servicio) {
        this.registroActividadService = servicio;
        this.busqueda = new RegActividadBusqueda();
    }
    
    /**
     * Sobreescritura del método getRowData para que funcione con objetos de tipo RegistroActividad.
     * 
     * @param rowKey Clave de la fila sobre la que se ha hecho click en la vista
     * @return Registro que se corresponde con la clave recibida por parámetro
     * 
     */
    @Override
    public RegistroActividad getRowData(String rowKey) {
        RegistroActividad reg = null;
        for (RegistroActividad registro : datasource) {
            if (registro.getIdRegActividad().toString().equals(rowKey))
                reg = registro;
        }
        return reg;
    }
    
    /**
     * Sobrescritura del método getRowKey.
     * 
     * @param solicitud Objeto del que se desea obtener la clave
     * @return Clave del objeto pasado como parámetro
     */
    
    @Override
    public Object getRowKey(RegistroActividad solicitud) {
        return solicitud.getIdRegActividad();
    }
    
    /**
     * Sobreescritura del método Load para que funcione con un critera y sólo nos devuelva estríctamente el número de
     * registros solicitados.
     * 
     * @param first primer elemento que se desea recuperar
     * @param pageSize número máximo de registros que deseamos recuperar por página
     * @param sortField columna por la que se ordenarán los resultados. Corresponde a la propiedad 'sortBy' de la
     * columna de la vista
     * @param sortOrder orden por el que se desea ordenar los resultados
     * @param filters mapa de filtros. Este valor no se utiliza en esta sobreescritura.
     * @return lista de registros que corresponden a los criterios de búsqueda
     * 
     */
    
    @Override
    public List<RegistroActividad> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        List<RegistroActividad> listado = null;
        if (busqueda != null) {
            this.setRowCount(registroActividadService.getCounCriteria(busqueda));
            listado = registroActividadService.buscarRegActividadCriteria(first, pageSize, sortField, sortOrder,
                    busqueda);
            this.datasource = listado;
        } else {
            this.setRowCount(0);
        }
        return listado;
    }
    
}
