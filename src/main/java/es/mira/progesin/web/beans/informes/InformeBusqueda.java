package es.mira.progesin.web.beans.informes;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.InformeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto que almacena los filtros de búsqueda de informees.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
public class InformeBusqueda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Fecha alta de informes desde la que busca.
     */
    private Date fechaDesde;
    
    /**
     * Fecha alta de informes hasta la que busca.
     */
    private Date fechaHasta;
    
    /**
     * Filtro estado de la informe.
     */
    private InformeEnum estado;
    
    /**
     * Fecha alta de informes hasta la que busca.
     */
    private Date fechaFinalizacion;
    
    /**
     * Filtro username usuario que realizó el alta.
     */
    private String usuarioCreacion;
    
    /**
     * Filtro número inspección a la que pertenece.
     */
    private String idInspeccion;
    
    /**
     * Filtro año inspección a la que pertenece.
     */
    private String anioInspeccion;
    
    /**
     * Filtro número inspección a la que pertenece.
     */
    private TipoInspeccion tipoInspeccion;
    
    /**
     * Filtro ámbito inspección a la que pertenece.
     */
    private AmbitoInspeccionEnum ambitoInspeccion;
    
    /**
     * Filtro nombre unidad inspeccionada a la que se envía la informe.
     */
    private String nombreUnidad;
    
}
