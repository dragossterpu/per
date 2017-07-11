/**
 * 
 */
package es.mira.progesin.web.beans.informes;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import lombok.Getter;
import lombok.Setter;

/**
 * POJO con los parámetros de búsqueda de informes personalizados.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
public class InformePersonalizadoBusqueda implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Modelo informe por el que buscar.
     */
    private ModeloInforme modeloInformeSeleccionado;
    
    /**
     * Fecha desde.
     */
    private Date fechaDesde;
    
    /**
     * Fecha hasta.
     */
    private Date fechaHasta;
    
    /**
     * Nombre de usuario de creación del informe personalizado.
     */
    private String usernameAlta;
    
    /**
     * Nombre del informe personalizado.
     */
    private String nombreInforme;
    
}
