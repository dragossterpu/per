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
 * Modelo para paginación desde servidor extendiendo el modelo LazyDataModel de Primefaces.
 * 
 * @author EZENTIS
 *
 */
@Component
@Setter
@Getter
public class LazyModelDocumentos extends LazyDataModel<Documento> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Objeto que contiene los parámetros de búsqueda.
     */
    private DocumentoBusqueda busqueda;
    
    /**
     * Servicio.
     */
    private transient IDocumentoService servicio;
    
    /**
     * Lista que sirve al modelo como fuente de datos.
     */
    private List<Documento> datasource;
    
    /**
     * Constructor.
     * 
     * @param service Servicio a utilizar
     */
    public LazyModelDocumentos(IDocumentoService service) {
        this.servicio = service;
        this.busqueda = new DocumentoBusqueda();
    }
    
    /**
     * Sobreescritura del método getRowData para adaptarlo a nuestro modelo.
     * 
     * @param rowKey Fila que se ha seleccionado en la vista
     * @return Documento que corresponde a la fila seleccionada
     */
    @Override
    public Documento getRowData(String rowKey) {
        for (Documento solicitud : datasource) {
            if (solicitud.getId().toString().equals(rowKey))
                return solicitud;
        }
        return null;
    }
    
    /**
     * Sobreestritura del método getRowKey para adaptarlo a nuestro modelo.
     * 
     * @param solicitud Documento del que se desea recuperar la clave
     * @return Clave del documento
     */
    @Override
    public Object getRowKey(Documento solicitud) {
        return solicitud.getId();
    }
    
    /**
     * Sobreescritura del método load para realizar la carga paginada.
     * 
     * @param first primer elemento de la paginación
     * @param pageSize número máximo de registros recuperados
     * @param sortField campo por el que se ordena
     * @param sortOrder sentido de la orientación
     * @param filters que se aplicarán a la búsqueda.
     * @return listado de registros que corresponden a la búsqueda
     */
    @Override
    public List<Documento> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, Object> filters) {
        List<Documento> listado = null;
        if (busqueda != null) {
            this.setRowCount(servicio.getCounCriteria(busqueda));
            listado = servicio.buscarDocumentoPorCriteria(first, pageSize, sortField, sortOrder, busqueda);
            this.setDatasource(listado);
        } else {
            this.setRowCount(0);
        }
        return listado;
        
    }
    
}
