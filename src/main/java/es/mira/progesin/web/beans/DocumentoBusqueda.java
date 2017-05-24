package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * POJO para almacenar los parámetros de búsqueda de documentos
 * 
 * @author Ezentis
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class DocumentoBusqueda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Date fechaDesde;
    
    private Date fechaHasta;
    
    private String nombre;
    
    private TipoDocumento tipoDocumento;
    
    private String descripcion;
    
    private String materiaIndexada;
    
    private Inspeccion inspeccion;
    
    private boolean eliminado = false;
    
    /**
     * Crea un objeto de búsqueda a partir de otro
     * 
     * @param original el objeto a duplicar
     */
    public DocumentoBusqueda(DocumentoBusqueda original) {
        this.setFechaDesde(original.getFechaDesde());
        this.setFechaHasta(original.getFechaHasta());
        this.setNombre(original.getNombre());
        this.setTipoDocumento(original.getTipoDocumento());
        this.setMateriaIndexada(original.getMateriaIndexada());
        this.setInspeccion(original.getInspeccion());
        this.setEliminado(original.isEliminado());
        this.setDescripcion(original.getDescripcion());
    }
    
    /**
     * Limpia los valores del objeto
     * 
     */
    public void resetValues() {
        this.setFechaDesde(null);
        this.setFechaHasta(null);
        this.setNombre(null);
        this.setTipoDocumento(null);
        this.setMateriaIndexada(null);
        this.setDescripcion(null);
        this.setInspeccion(null);
    }
    
}
