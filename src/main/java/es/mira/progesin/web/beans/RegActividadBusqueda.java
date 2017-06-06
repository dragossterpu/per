package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Bean para almacenar los criterios de búsqueda en el registro de actividad de la aplicación.
 * 
 * @author EZENTIS
 * 
 */

@Setter
@Getter
public class RegActividadBusqueda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Fecha desde la que se desea hacer la búsqueda.
     */
    private Date fechaDesde;
    
    /**
     * Fecha hasta la que se desea hacer la búsqueda.
     */
    private Date fechaHasta;
    
    /**
     * Sección para la que se desea hacer la búsqueda.
     */
    private String nombreSeccion;
    
    /**
     * Username del usuario para el que se desea hacer la búsqueda.
     */
    
    private String usernameRegActividad;
    
    /**
     * Tipo de registro de la que se desea hacer la búsqueda.
     */
    private String tipoRegActividad;
    
}
