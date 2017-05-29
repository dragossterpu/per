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
 * Modelo para paginación desde servidor extendiendo el modelo LazyDataModel de Primefaces.
 * 
 * @author EZENTIS
 *
 */

@Setter
@Getter
public class LazyModelCuestionarioPersonalizado extends LazyDataModel<CuestionarioPersonalizado>
        implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Objeto que contiene los parámetros de búsqueda.
     */
    private CuestionarioPersonalizadoBusqueda busqueda;
    
    /**
     * Servicio.
     */
    private transient ICuestionarioPersonalizadoService servicio;
    
    /**
     * Lista que sirve al modelo como fuente de datos.
     */
    private List<CuestionarioPersonalizado> datasource;
    
    /**
     * Constructor.
     * 
     * @param service Servicio a utilizar
     */
    public LazyModelCuestionarioPersonalizado(ICuestionarioPersonalizadoService service) {
        this.servicio = service;
        this.busqueda = new CuestionarioPersonalizadoBusqueda();
    }
    
    /**
     * Sobreescritura del método getRowData para adaptarlo a nuestro modelo.
     * 
     * @param rowKey Fila que se ha seleccionado en la vista
     * @return Cuestionario enviado que corresponde a la fila seleccionada
     */
    @Override
    public CuestionarioPersonalizado getRowData(String rowKey) {
        for (CuestionarioPersonalizado solicitud : datasource) {
            if (solicitud.getId().toString().equals(rowKey))
                return solicitud;
        }
        return null;
    }
    
    /**
     * Sobreestritura del método getRowKey para adaptarlo a nuestro modelo.
     * 
     * @param solicitud Cuestionario del que se desea recuperar la clave
     * @return Clave del cuestionario
     */
    @Override
    public Object getRowKey(CuestionarioPersonalizado solicitud) {
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
