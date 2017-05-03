package es.mira.progesin.lazydata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.web.beans.SolicitudDocPreviaBusqueda;
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
public class LazyModelSolicitudes extends LazyDataModel<SolicitudDocumentacionPrevia> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private SolicitudDocPreviaBusqueda busqueda;
    
    private ISolicitudDocumentacionService servicio;
    
    private List<SolicitudDocumentacionPrevia> datasource;
    
    /**
     * Constructor que recibe un SessionFactory
     * 
     * @param servicio Servicio de SolicitudDocumentacionPrevia
     */
    public LazyModelSolicitudes(ISolicitudDocumentacionService servicio) {
        this.servicio = servicio;
        this.busqueda = new SolicitudDocPreviaBusqueda();
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
    @Override
    public List<SolicitudDocumentacionPrevia> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        List<SolicitudDocumentacionPrevia> listaSolicitudesDocPrevia = null;
        if (busqueda != null) {
            this.setRowCount(servicio.getCountSolicitudDocPreviaCriteria(busqueda));
            listaSolicitudesDocPrevia = servicio.buscarSolicitudDocPreviaCriteria(first, pageSize, sortField, sortOrder,
                    busqueda);
            this.setDatasource(listaSolicitudesDocPrevia);
        } else {
            this.setRowCount(0);
        }
        return listaSolicitudesDocPrevia;
        
    }
    
}
