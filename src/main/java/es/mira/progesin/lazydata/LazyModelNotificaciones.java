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
 * Modelo para paginación desde servidor extendiendo el modelo LazyDataModel de Primefaces.
 * 
 * @author EZENTIS
 *
 */

@Setter
@Getter
public class LazyModelNotificaciones extends LazyDataModel<Notificacion> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Usuario del que se buscarán las notificaciones.
     */
    private String usuario;
    
    /**
     * Servicio a usar.
     */
    private transient INotificacionService servicio;
    
    /**
     * Lista que sirve al modelo como fuente de datos.
     */
    private List<Notificacion> datasource;
    
    /**
     * Constructor.
     * 
     * @param service servicio a utilizar
     * @param user usuario para el que se desea consultar las notificaciones
     */
    public LazyModelNotificaciones(INotificacionService service, String user) {
        this.servicio = service;
        this.usuario = user;
        
    }
    
    /**
     * Sobreescritura del método getRowData para adaptarlo a nuestro modelo.
     * 
     * @param rowKey Fila que se ha seleccionado en la vista
     * @return Notificación que corresponde a la fila seleccionada
     */
    @Override
    public Notificacion getRowData(String rowKey) {
        Notificacion notificacion = null;
        for (Notificacion mensaje : datasource) {
            if (mensaje.getIdNotificacion().toString().equals(rowKey))
                notificacion = mensaje;
        }
        return notificacion;
    }
    
    /**
     * Sobreestritura del método getRowKey para adaptarlo a nuestro modelo.
     * 
     * @param mensaje Notificación de la que se desea recuperar la clave
     * @return clave de la notificación
     */
    @Override
    public Object getRowKey(Notificacion mensaje) {
        return mensaje.getIdNotificacion();
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
    public List<Notificacion> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        
        this.setRowCount(servicio.getCounCriteria(usuario));
        List<Notificacion> listado = servicio.buscarPorCriteria(first, pageSize, sortField, sortOrder, usuario);
        this.setDatasource(listado);
        
        return listado;
        
    }
    
}
