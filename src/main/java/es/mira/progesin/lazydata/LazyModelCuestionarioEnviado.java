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
 * Modelo para paginación desde servidor extendiendo el modelo LazyDataModel de Primefaces.
 * 
 * @author EZENTIS
 *
 */

@Setter
@Getter
public class LazyModelCuestionarioEnviado extends LazyDataModel<CuestionarioEnvio> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Objeto que contiene los parámetros de búsqueda.
     */
    private CuestionarioEnviadoBusqueda busqueda;
    
    /**
     * Servicio de cuestionarios enviados.
     */
    private transient ICuestionarioEnvioService servicio;
    
    /**
     * Lista que sirve al modelo como fuente de datos.
     */
    private List<CuestionarioEnvio> datasource;
    
    /**
     * Constructor.
     * 
     * @param service Servicio de cuestionarios enviados a utilizar
     */
    public LazyModelCuestionarioEnviado(ICuestionarioEnvioService service) {
        this.servicio = service;
        this.busqueda = new CuestionarioEnviadoBusqueda();
    }
    
    /**
     * Sobreescritura del método getRowData para adaptarlo a nuestro modelo.
     * 
     * @param rowKey Fila que se ha seleccionado en la vista
     * @return Cuestionario enviado que corresponde a la fila seleccionada
     */
    @Override
    public CuestionarioEnvio getRowData(String rowKey) {
        CuestionarioEnvio envio = null;
        for (CuestionarioEnvio cuestionario : datasource) {
            if (cuestionario.getId().toString().equals(rowKey))
                envio = cuestionario;
        }
        return envio;
    }
    
    /**
     * Sobreestritura del método getRowKey para adaptarlo a nuestro modelo.
     * 
     * @param solicitud Cuestionario del que se desea recuperar la clave
     * @return Clave del cuestionario
     */
    
    @Override
    public Object getRowKey(CuestionarioEnvio solicitud) {
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
