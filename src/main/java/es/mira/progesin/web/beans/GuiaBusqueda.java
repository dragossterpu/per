package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Objeto para almacenar los parámetros de búsqueda de Guías
 * 
 * @author Ezentis
 *
 */
@Setter
@Getter
public class GuiaBusqueda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Date fechaDesde;
    
    private Date fechaHasta;
    
    private String usuarioCreacion;
    
    private String nombre;
    
    private TipoInspeccion tipoInspeccion;
    
    private List<Guia> listaGuias;
    
    private EstadoEnum estado;
    
    /**
     * Limpia los valores del objeto
     */
    public void resetValues() {
        this.setFechaDesde(null);
        this.setFechaHasta(null);
        this.setUsuarioCreacion(null);
        this.setNombre(null);
        this.setTipoInspeccion(null);
        this.setListaGuias(null);
        this.setEstado(null);
    }
}
