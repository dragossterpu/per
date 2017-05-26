package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.web.beans.EquipoBusqueda;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Modelo para paginación desde servidor extendiendo el modelo LazyDataModel de Primefaces.
 * 
 * @author Ezentis
 *
 */

@Setter
@Getter
public class LazyModelEquipos extends LazyDataModel<Equipo> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Objeto que contiene los parámetros de búsqueda.
     */
    private EquipoBusqueda busqueda;
    
    /**
     * Servicio.
     */
    private transient IEquipoService servicio;
    
    /**
     * Lista que sirve al modelo como fuente de datos.
     */
    private List<Equipo> datasource;
    
    /**
     * Constructor.
     * 
     * @param service Servicio a utilizar
     */
    public LazyModelEquipos(IEquipoService service) {
        this.servicio = service;
        this.busqueda = new EquipoBusqueda();
    }
    
    /**
     * Sobreescritura del método getRowData para adaptarlo a nuestro modelo.
     * 
     * @param rowKey Fila que se ha seleccionado en la vista
     * @return Equipo que corresponde a la fila seleccionada
     */
    @Override
    public Equipo getRowData(String rowKey) {
        for (Equipo solicitud : datasource) {
            if (solicitud.getId().toString().equals(rowKey))
                return solicitud;
        }
        return null;
    }
    
    /**
     * Sobreestritura del método getRowKey para adaptarlo a nuestro modelo.
     * 
     * @param solicitud Equipo del que se desea recuperar la clave
     * @return Clave del equipo
     */
    @Override
    public Object getRowKey(Equipo solicitud) {
        return solicitud.getId();
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
    public List<Equipo> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        List<Equipo> listado = null;
        if (busqueda != null) {
            this.setRowCount(servicio.getCounCriteria(busqueda));
            listado = servicio.buscarEquipoCriteria(first, pageSize, sortField, sortOrder, busqueda);
            this.setDatasource(listado);
        } else {
            this.setRowCount(0);
        }
        return listado;
        
    }
    
}
