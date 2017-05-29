package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Clase de objetos para el almacenaje de los parámetros de búsqueda de guías personalizadas.
 * 
 * @author EZENTIS
 *
 */
@Setter
@Getter
public class GuiaPersonalizadaBusqueda implements Serializable {
    
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
     * Usuario para el que se desea hacer la búsqueda.
     */
    private String usuarioCreacion;
    
    /**
     * Nombre para elque se desea hacer la búsqueda.
     */
    private String nombre;
    
    /**
     * Tipo de inspección para el que se desea hacer la búsqueda.
     */
    private TipoInspeccion tipoInspeccion;
    
    /**
     * Estado activio inactivo de las guías a buscar.
     */
    private EstadoEnum estado;
    
    /**
     * Limpia los valores del objeto.
     * 
     */
    public void resetValues() {
        this.setFechaDesde(null);
        this.setFechaHasta(null);
        this.setUsuarioCreacion(null);
        this.setNombre(null);
        this.setTipoInspeccion(null);
        this.setEstado(null);
    }
}
