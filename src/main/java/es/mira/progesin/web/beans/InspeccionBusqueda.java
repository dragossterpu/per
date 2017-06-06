package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.TipoUnidad;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.CuatrimestreEnum;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean usado como filtro para el buscador de inspeciones.
 * 
 * @author EZENTIS
 *
 */
@Getter
@Setter
public class InspeccionBusqueda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Fecha desde la que la búsqueda incluye inspecciones.
     */
    private Date fechaDesde;
    
    /**
     * Fecha hasta la que la búsqueda incluye inspecciones.
     */
    private Date fechaHasta;
    
    /**
     * Filtro usuario de creación.
     */
    private String usuarioCreacion;
    
    /**
     * Filtro año de la inspección.
     */
    private String anio;
    
    /**
     * Filtro equipo.
     */
    private Equipo equipo;
    
    /**
     * Filtro tipo de inspección.
     */
    private TipoInspeccion tipoInspeccion;
    
    /**
     * Filtro nombre de la unidad.
     */
    private String nombreUnidad;
    
    /**
     * Filtro jefe de equipo.
     */
    private String jefeEquipo;
    
    /**
     * Filtro identificador.
     */
    private String id;
    
    /**
     * Filtro ámbito.
     */
    private AmbitoInspeccionEnum ambito;
    
    /**
     * Filtro cuatrimestre.
     */
    private CuatrimestreEnum cuatrimestre;
    
    /**
     * Filtro stado.
     */
    private EstadoInspeccionEnum estado;
    
    /**
     * Filtro provincia.
     */
    private Provincia provincia;
    
    /**
     * Filtro municipio.
     */
    private Municipio municipio;
    
    /**
     * Filtro tipo de unidad.
     */
    private TipoUnidad tipoUnidad;
    
    /**
     * Campo de ordenación en la tabla de inspecciones.
     */
    private String sortField;
    
    /**
     * Orden de ordenación en la tabla de inspecciones (ascendente o descendente).
     */
    private SortOrder sortOrder;
    
    /**
     * Inspección que se esta modificando o creando en el momento. Es la que se muestra en las vistas.
     */
    private Inspeccion inspeccionModif;
    
    /**
     * Distingue entre la vista de búsqueda de inspecciones y de asociar inspecciones ya que se emplea el mismo xhtml
     * para ambas.
     */
    private boolean asociar;
    
}
