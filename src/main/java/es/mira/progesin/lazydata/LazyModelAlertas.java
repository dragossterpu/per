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
 * Modelo para paginación desde servidor extendiendo el modelo LazyDataModel de Primefaces.
 * 
 * @author EZENTIS
 *
 */

@Setter
@Getter
public class LazyModelAlertas extends LazyDataModel<Alerta> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Usuario del que se buscarán las alertas.
     */
    private String usuario;
    
    /**
     * Servicio de alertas.
     */
    private transient IAlertaService servicio;
    
    /**
     * Lista que sirve al modelo como fuente de datos.
     */
    private List<Alerta> datasource;
    
    /**
     * Constructor.
     * 
     * @param service servicio de alertas a utilizar
     * @param user usuario para el que se desea consultar las alertas
     */
    public LazyModelAlertas(IAlertaService service, String user) {
        this.servicio = service;
        this.usuario = user;
        
    }
    
    /**
     * Sobreescritura del método getRowData para adaptarlo a nuestro modelo.
     * 
     * @param rowKey fila que se ha seleccionado en la vista
     * @return alerta que corresponde a la fila seleccionada
     */
    @Override
    public Alerta getRowData(String rowKey) {
        Alerta alerta = null;
        for (Alerta mensaje : datasource) {
            if (mensaje.getIdAlerta().toString().equals(rowKey))
                alerta = mensaje;
        }
        return alerta;
    }
    
    /**
     * Sobreestritura del método getRowKey para adaptarlo a nuestro modelo.
     * 
     * @param mensaje de la que se desea recuperar la clave
     * @return clave de la alerta
     */
    @Override
    public Object getRowKey(Alerta mensaje) {
        return mensaje.getIdAlerta();
    }
    
    /**
     * Sobreescritura del método load para realizar la carga paginada de las alertas para un usuario.
     * 
     * @param first primer elemento de la paginación
     * @param pageSize número máximo de registros recuperados
     * @param sortField campo por el que se ordena
     * @param sortOrder sentido de la orientación
     * @param filters que se aplicarán a la búsqueda.
     * @return listado de registros que corresponden a la búsqueda
     */
    @Override
    public List<Alerta> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        this.setRowCount(servicio.getCounCriteria(usuario));
        List<Alerta> listado = servicio.buscarPorCriteria(first, pageSize, sortField, sortOrder, usuario);
        this.setDatasource(listado);
        
        return listado;
        
    }
    
}
