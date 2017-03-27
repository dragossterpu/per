package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipios;
import es.mira.progesin.persistence.entities.Provincias;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.CuatrimestreEnum;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspeccionBusqueda implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Date fechaDesde;
    
    private Date fechaHasta;
    
    private String usuarioCreacion;
    
    private String anio;
    
    private String nombreUnidad;
    
    private TipoInspeccion tipoInspeccion;
    
    private String nombreEquipo;
    
    private String jefeEquipo;
    
    private String numero;
    
    private AmbitoInspeccionEnum ambito;
    
    private CuatrimestreEnum cuatrimestre;
    
    private EstadoInspeccionEnum estado;
    
    private List<Inspeccion> listaInspecciones;
    
    private Provincias provincia;
    
    private Municipios municipio;
    
    private String tipoUnidad;
    
    /**
     * Limpia los valores del objeto
     * 
     */
    public void resetValues() {
        this.fechaDesde = null;
        this.fechaHasta = null;
        this.usuarioCreacion = null;
        this.anio = null;
        this.tipoInspeccion = null;
        this.nombreEquipo = null;
        this.numero = null;
        this.ambito = null;
        this.cuatrimestre = null;
        this.estado = null;
        this.provincia = null;
        this.municipio = null;
        this.tipoUnidad = null;
        this.nombreUnidad = null;
        this.jefeEquipo = null;
        this.listaInspecciones = null;
    }
    
}
