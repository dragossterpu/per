package es.mira.progesin.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import lombok.Getter;
import lombok.Setter;

/**
 * Modelo usado para poder pintar tablas y matrices de forma genérica en la vista con primefaces
 * 
 * @author EZENTIS
 *
 */
@Getter
@Setter
public class DataTableView implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<ColumnModel> columns;
    
    private List<DatosTablaGenerica> listaDatosTabla;
    
    /**
     * Constructor
     */
    public DataTableView() {
        columns = new ArrayList<>();
        listaDatosTabla = new ArrayList<>();
    }
    
    /**
     * Crea una tabla con los valores pasados como parámetros
     * 
     * @param valoresColumnas
     */
    public void crearTabla(List<ConfiguracionRespuestasCuestionario> valoresColumnas) {
        crearColumnasDinamicamente(valoresColumnas);
        crearFilaVacia();
    }
    
    /**
     * Crea una matriz con la información grabada por los usuarios al responder un cuestionario
     * 
     * @param listaDatos
     */
    public void crearTablaMatriConDatos(List<DatosTablaGenerica> listaDatos) {
        setListaDatosTabla(listaDatos);
    }
    
    /**
     * Crea una matriz vacía, sólo con los nombres de las columnas y las filas
     * 
     * @param valoresColumnas
     */
    public void crearMatriz(List<ConfiguracionRespuestasCuestionario> valoresColumnas) {
        // Inicializado la lista con un campo vacío para que muestre el datatable con una fila vacía de inputText
        for (ConfiguracionRespuestasCuestionario config : valoresColumnas) {
            if (config.getConfig().getClave().startsWith("nombreFila")) {
                crearFilaMatriz(config.getConfig().getValor());
            } else {
                columns.add(new ColumnModel(config.getConfig().getValor(), config.getConfig().getClave()));
            }
        }
    }
    
    private void crearFilaMatriz(String nombreFila) {
        DatosTablaGenerica dtg = new DatosTablaGenerica();
        dtg.setNombreFila(nombreFila);
        listaDatosTabla.add(dtg);
    }
    
    private void crearColumnasDinamicamente(List<ConfiguracionRespuestasCuestionario> valoresColumnas) {
        String header;
        String property;
        for (ConfiguracionRespuestasCuestionario config : valoresColumnas) {
            property = config.getConfig().getClave();
            header = config.getConfig().getValor();
            columns.add(new ColumnModel(header, property));
        }
    }
    
    /**
     * Crea una fila vacía en una tabla
     */
    public void crearFilaVacia() {
        DatosTablaGenerica dtg = new DatosTablaGenerica();
        listaDatosTabla.add(dtg);
    }
    
    /**
     * Elimina el último registro de la tabla, siempre y cuando la tabla tenga más de una fila
     * @return datos de la tabla sin la última fila
     */
    public DatosTablaGenerica eliminarFila() {
        DatosTablaGenerica filaEliminar = null;
        if (listaDatosTabla.size() > 1) {
            filaEliminar = listaDatosTabla.get(listaDatosTabla.size() - 1);
            listaDatosTabla.remove(listaDatosTabla.size() - 1);
        }
        return filaEliminar;
    }
    
    /**
     * @author sperezp
     *
     */
    public class ColumnModel implements Serializable {
        
        private static final long serialVersionUID = 1L;
        
        private String header;
        
        private String property;
        
        /**
         * @param header nombre de la columna
         * @param property valor de la columna
         */
        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }
        
        /**
         * @return header
         */
        public String getHeader() {
            return header;
        }
        
        /**
         * @return property
         */
        public String getProperty() {
            return property;
        }
    }
}
