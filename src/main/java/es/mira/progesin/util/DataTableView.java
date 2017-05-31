package es.mira.progesin.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import lombok.Getter;
import lombok.Setter;

/**
 * Modelo usado para poder pintar tablas y matrices de forma genérica en la vista con primefaces.
 * 
 * @author EZENTIS
 *
 */
@Getter
@Setter
public class DataTableView implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Columnas de la tabla o matriz.
     */
    private List<ColumnModel> columns;
    
    /**
     * Datos que almacena la tabla o matriz, incluido el nombre de la fila.
     */
    private List<DatosTablaGenerica> listaDatosTabla;
    
    /**
     * Constructor.
     */
    public DataTableView() {
        columns = new ArrayList<>();
        listaDatosTabla = new ArrayList<>();
    }
    
    /**
     * Crea una tabla con los valores pasados como parámetros.
     * 
     * @param valoresColumnas
     */
    public void crearTabla(List<ConfiguracionRespuestasCuestionario> valoresColumnas) {
        crearColumnasDinamicamente(valoresColumnas);
        crearFilaVacia();
    }
    
    /**
     * Crea una matriz con la información grabada por los usuarios al responder un cuestionario.
     * 
     * @param listaDatos
     */
    public void crearTablaMatriConDatos(List<DatosTablaGenerica> listaDatos) {
        setListaDatosTabla(listaDatos);
    }
    
    /**
     * Crea una matriz vacía, sólo con los nombres de las columnas y las filas.
     * 
     * @param valoresColumnasyFilas lista de nombres.
     */
    public void crearMatriz(List<ConfiguracionRespuestasCuestionario> valoresColumnasyFilas) {
        // Inicializado la lista con un campo vacío para que muestre el datatable con una fila vacía de inputText
        for (ConfiguracionRespuestasCuestionario config : valoresColumnasyFilas) {
            if (config.getConfig().getClave().startsWith("nombreFila")) {
                crearFilaMatriz(config.getConfig().getValor());
            } else {
                columns.add(new ColumnModel(config.getConfig().getValor(), config.getConfig().getClave()));
            }
        }
    }
    
    /**
     * Agrega una fila a la matriz con el nombre dado.
     * 
     * @param nombreFila nombre que identifica la fila.
     */
    private void crearFilaMatriz(String nombreFila) {
        DatosTablaGenerica dtg = new DatosTablaGenerica();
        dtg.setNombreFila(nombreFila);
        listaDatosTabla.add(dtg);
    }
    
    /**
     * Agrega una serie de columnas con los nombres proporcionados a una tabla o matriz.
     * 
     * @param valoresColumnas lista de nombres.
     */
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
     * Crea una fila vacía en una tabla.
     */
    public void crearFilaVacia() {
        DatosTablaGenerica dtg = new DatosTablaGenerica();
        listaDatosTabla.add(dtg);
    }
    
    /**
     * Elimina el último registro de la tabla, siempre y cuando la tabla tenga más de una fila.
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
     * Definición de una columna de tabla o matriz.
     */
    public class ColumnModel implements Serializable {
        
        private static final long serialVersionUID = 1L;
        
        /**
         * Nombre de la columna.
         */
        private String header;
        
        /**
         * Valor de la columna.
         */
        private String property;
        
        /**
         * @param nombre nombre de la columna
         * @param valor valor de la columna
         */
        public ColumnModel(String nombre, String valor) {
            this.header = nombre;
            this.property = valor;
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
