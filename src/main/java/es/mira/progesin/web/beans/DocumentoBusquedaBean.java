package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import lombok.Getter;
import lombok.Setter;

/**
 * POJO para almacenar los parámetros de búsqueda de documentos
 * 
 * @author Ezentis
 *
 */
@Setter
@Getter
public class DocumentoBusquedaBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Date fechaDesde;
    
    private Date fechaHasta;
    
    private String nombre;
    
    private TipoDocumento tipoDocumento;
    
    private String materiaIndexada;
    
    private Inspeccion inspeccion;
    
    private boolean eliminado = false;
    
    /**
     * Limpia los valores del objeto
     * 
     */
    public void resetValues() {
        this.fechaDesde = null;
        this.fechaHasta = null;
        this.nombre = null;
        this.tipoDocumento = null;
        this.materiaIndexada = null;
        this.inspeccion = null;
    }
    
}
