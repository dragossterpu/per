package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.web.beans.UserBusqueda;
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
public class LazyModelUsuarios extends LazyDataModel<User> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private UserBusqueda busqueda;
    
    private transient IUserService servicio;
    
    private List<User> datasource;
    
    /**
     * Constructor que recibe un SessionFactory
     * 
     * @param servicio Servicio de SolicitudDocumentacionPrevia
     */
    public LazyModelUsuarios(IUserService servicio) {
        this.servicio = servicio;
        this.busqueda = new UserBusqueda();
    }
    
    /**
     * Sobreescritura del método load que se ejecuta cada vez que se hace alguna acción sobre la datatable de la vista
     * 
     * @param first primer elemento que se desea recuperar
     * @param pageSize número máximo de registros que deseamos recuperar por página
     * @param sortField columna por la que se ordenarán los resultados. Corresponde a la propiedad 'sortBy' de la
     * columna de la vista
     * @param sortOrder orden por el que se desea ordenar los resultados
     * @param filters mapa de filtros. Este valor no se utiliza en esta sobreescritura.
     * @return lista de registros que corresponden a los criterios de búsqueda
     */
    // @Override
    @Override
    public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        List<User> listado = null;
        if (busqueda != null) {
            this.setRowCount(servicio.contarRegistros(busqueda));
            listado = servicio.buscarUsuarioCriteria(first, pageSize, sortField, sortOrder, busqueda);
            this.setDatasource(listado);
        } else {
            this.setRowCount(0);
        }
        return listado;
        
    }
    
}
