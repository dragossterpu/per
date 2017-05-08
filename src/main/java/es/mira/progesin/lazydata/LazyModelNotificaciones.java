package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.services.INotificacionService;
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
public class LazyModelNotificaciones extends LazyDataModel<Notificacion> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String usuario;
    
    private transient INotificacionService servicio;
    
    private List<Notificacion> datasource;
    
    /**
     * Constructor
     * 
     * @param service
     * @param user
     *
     */
    public LazyModelNotificaciones(INotificacionService service, String user) {
        this.servicio = service;
        this.usuario = user;
        
    }
    
    @Override
    public Notificacion getRowData(String rowKey) {
        for (Notificacion mensaje : datasource) {
            if (mensaje.getIdNotificacion().toString().equals(rowKey))
                return mensaje;
        }
        return null;
    }
    
    @Override
    public Object getRowKey(Notificacion mensaje) {
        return mensaje.getIdNotificacion();
    }
    
    @Override
    public List<Notificacion> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        
        this.setRowCount(servicio.getCounCriteria(usuario));
        List<Notificacion> listado = servicio.buscarPorCriteria(first, pageSize, sortField, sortOrder, usuario);
        this.setDatasource(listado);
        
        return listado;
        
    }
    
}
