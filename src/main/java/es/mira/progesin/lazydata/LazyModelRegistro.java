package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.web.beans.RegActividadBusqueda;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Modelo para paginación desde servidor extendiendo el modelo LazyDataModel de Primefaces
 * 
 * @author Ezentis
 *
 */
@Component
@Setter
@Getter
@Scope("session")
public class LazyModelRegistro extends LazyDataModel<RegistroActividad> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private RegActividadBusqueda busqueda;
    
    private List<RegistroActividad> datasource;
    
    private IRegistroActividadService registroActividadService;
    
    /**
     * 
     * Constructor del modelo que recibe el servicio como parámetro
     * 
     * @param registroActividadService
     */
    public LazyModelRegistro(IRegistroActividadService registroActividadService) {
        this.registroActividadService = registroActividadService;
        this.busqueda = new RegActividadBusqueda();
    }
    
    /**
     * 
     * Sobreescritura del método getRowData para que funcione con objetos de tipo RegistroActividad
     * 
     * @param rowkey clave de la fila sobre la que se ha hecho click en la vista
     * @return registro que se corresponde con la clave recibida por parámetro
     * 
     */
    @Override
    public RegistroActividad getRowData(String rowKey) {
        for (RegistroActividad registro : datasource) {
            if (registro.getIdRegActividad().toString().equals(rowKey))
                return registro;
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
