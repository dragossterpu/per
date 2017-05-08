package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBusqueda;
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
public class LazyModelCuestionarioEnviado extends LazyDataModel<CuestionarioEnvio> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private CuestionarioEnviadoBusqueda busqueda;
    
    private ICuestionarioEnvioService servicio;
    
    private List<CuestionarioEnvio> datasource;
    
    /**
     * Constructor
     * @param service
     */
    public LazyModelCuestionarioEnviado(ICuestionarioEnvioService service) {
        this.servicio = service;
        this.busqueda = new CuestionarioEnviadoBusqueda();
    }
    
    @Override
    public CuestionarioEnvio getRowData(String rowKey) {
        for (CuestionarioEnvio solicitud : datasource) {
            if (solicitud.getId().toString().equals(rowKey))
                return solicitud;
        }
        return null;
    }
    
    @Override
    public Object getRowKey(CuestionarioEnvio solicitud) {
        return solicitud.getId();
    }
    
    @Override
    public List<CuestionarioEnvio> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        List<CuestionarioEnvio> listado = null;
        if (busqueda != null) {
            this.setRowCount(servicio.getCountCuestionarioCriteria(busqueda));
            listado = servicio.buscarCuestionarioEnviadoCriteria(first, pageSize, sortField, sortOrder, busqueda);
            this.setDatasource(listado);
        } else {
            this.setRowCount(0);
        }
        return listado;
        
    }
    
}
