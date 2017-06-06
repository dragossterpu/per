package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Objeto para almacenar los parámetros de búsqueda de Guías.
 * 
 * @author EZENTIS
 *
 */
@Setter
@Getter
public class GuiaBusqueda implements Serializable {
    
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
     * Username del usuario sobre el que se desea hacer la búsqueda.
     */
    private String usuarioCreacion;
    
    /**
     * Nombre de la guía sobre la que se desea hacer la búsqueda.
     */
    private String nombre;
    
    /**
     * Tipo de inspección sobre la que se desea hacer la búsqueda.
     */
    private TipoInspeccion tipoInspeccion;
    
    /**
     * Estado en el que se encuenta la guía a buscar.
     */
    private EstadoEnum estado;
    
}
