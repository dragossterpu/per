package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.services.IAlertaService;
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
public class LazyModelAlertas extends LazyDataModel<Alerta> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String usuario;
    
    private transient IAlertaService servicio;
    
    private List<Alerta> datasource;
    
    /**
     * Constructor
     * @param service
     * @param user
     */
    public LazyModelAlertas(IAlertaService service, String user) {
        this.servicio = service;
        this.usuario = user;
        
    }
    
    @Override
    public Alerta getRowData(String rowKey) {
        for (Alerta mensaje : datasource) {
            if (mensaje.getIdAlerta().toString().equals(rowKey))
                return mensaje;
        }
        return null;
    }
    
    @Override
    public Object getRowKey(Alerta mensaje) {
        return mensaje.getIdAlerta();
    }
    
    @Override
    public List<Alerta> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        this.setRowCount(servicio.getCounCriteria(usuario));
        List<Alerta> listado = servicio.buscarPorCriteria(first, pageSize, sortField, sortOrder, usuario);
        this.setDatasource(listado);
        
        return listado;
        
    }
    
}
