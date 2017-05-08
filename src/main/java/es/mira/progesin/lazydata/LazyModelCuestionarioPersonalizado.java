package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.services.ICuestionarioPersonalizadoService;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBusqueda;
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
public class LazyModelCuestionarioPersonalizado extends LazyDataModel<CuestionarioPersonalizado>
        implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private CuestionarioPersonalizadoBusqueda busqueda;
    
    private transient ICuestionarioPersonalizadoService servicio;
    
    private List<CuestionarioPersonalizado> datasource;
    
    /**
     * Constructor
     * @param service
     * 
     * 
     */
    public LazyModelCuestionarioPersonalizado(ICuestionarioPersonalizadoService service) {
        this.servicio = service;
        this.busqueda = new CuestionarioPersonalizadoBusqueda();
    }
    
    @Override
    public CuestionarioPersonalizado getRowData(String rowKey) {
        for (CuestionarioPersonalizado solicitud : datasource) {
            if (solicitud.getId().toString().equals(rowKey))
                return solicitud;
        }
        return null;
    }
    
    @Override
    public Object getRowKey(CuestionarioPersonalizado solicitud) {
        return solicitud.getId();
    }
    
    @Override
    public List<CuestionarioPersonalizado> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        List<CuestionarioPersonalizado> listado = null;
        if (busqueda != null) {
            this.setRowCount(servicio.getCountCuestionarioCriteria(busqueda));
            listado = servicio.buscarCuestionarioPersonalizadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
            this.setDatasource(listado);
        } else {
            this.setRowCount(0);
        }
        return listado;
        
    }
    
}
