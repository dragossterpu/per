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
 * @author EZENTIS
 *
 */
@Getter
@Setter
public class InspeccionBusqueda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Date fechaDesde;
    
    private Date fechaHasta;
    
    private String usuarioCreacion;
    
    private String anio;
    
    private Equipo equipo;
    
    private TipoInspeccion tipoInspeccion;
    
    private String nombreUnidad;
    
    private String jefeEquipo;
    
    private String id;
    
    private AmbitoInspeccionEnum ambito;
    
    private CuatrimestreEnum cuatrimestre;
    
    private EstadoInspeccionEnum estado;
    
    private Provincia provincia;
    
    private Municipio municipio;
    
    private TipoUnidad tipoUnidad;
    
    private String sortField;
    
    private SortOrder sortOrder;
    
    private Inspeccion inspeccionModif;
    
    private boolean asociar;
    
    /**
     * Limpia los valores del objeto
     * 
     */
    public void resetValues() {
        this.setFechaDesde(null);
        this.setFechaHasta(null);
        this.setUsuarioCreacion(null);
        this.setAnio(null);
        this.setTipoInspeccion(null);
        this.setNombreUnidad(null);
        this.setId(null);
        this.setAmbito(null);
        this.setCuatrimestre(null);
        this.setEstado(null);
        this.setProvincia(null);
        this.setMunicipio(null);
        this.setTipoUnidad(null);
        this.setEquipo(null);
        this.setJefeEquipo(null);
    }
    
}
