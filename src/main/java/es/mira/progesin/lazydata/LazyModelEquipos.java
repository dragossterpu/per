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
 * Modelo para paginación desde servidor extendiendo el modelo LazyDataModel de Primefaces
 * 
 * @author Ezentis
 *
 */

@Setter
@Getter
public class LazyModelEquipos extends LazyDataModel<Equipo> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private EquipoBusqueda busqueda;
    
    private transient IEquipoService servicio;
    
    private List<Equipo> datasource;
    
    /**
     * Constructor
     * @param service
     * 
     */
    public LazyModelEquipos(IEquipoService service) {
        this.servicio = service;
        this.busqueda = new EquipoBusqueda();
    }
    
    @Override
    public Equipo getRowData(String rowKey) {
        for (Equipo solicitud : datasource) {
            if (solicitud.getId().toString().equals(rowKey))
                return solicitud;
        }
        return null;
    }
    
    @Override
    public Object getRowKey(Equipo solicitud) {
        return solicitud.getId();
    }
    
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
