package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.web.beans.DocumentoBusqueda;
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
public class LazyModelDocumentos extends LazyDataModel<Documento> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private DocumentoBusqueda busqueda;
    
    private transient IDocumentoService servicio;
    
    private List<Documento> datasource;
    
    /**
     * Constructor
     * 
     * @param service
     * 
     */
    public LazyModelDocumentos(IDocumentoService service) {
        this.servicio = service;
        this.busqueda = new DocumentoBusqueda();
    }
    
    @Override
    public Documento getRowData(String rowKey) {
        for (Documento solicitud : datasource) {
            if (solicitud.getId().toString().equals(rowKey))
                return solicitud;
        }
        return null;
    }
    
    @Override
    public Object getRowKey(Documento solicitud) {
        return solicitud.getId();
    }
    
    @Override
    public List<Documento> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        List<Documento> listado = null;
        if (busqueda != null) {
            this.setRowCount(servicio.getCounCriteria(busqueda));
            listado = servicio.buscarGuiaPorCriteria(first, pageSize, sortField, sortOrder, busqueda);
            this.setDatasource(listado);
        } else {
            this.setRowCount(0);
        }
        return listado;
        
    }
    
}
