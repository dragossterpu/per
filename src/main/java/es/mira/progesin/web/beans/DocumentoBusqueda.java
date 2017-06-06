package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * POJO para almacenar los parámetros de búsqueda de documentos.
 * 
 * @author EZENTIS
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class DocumentoBusqueda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Fecha desde la que se desea hacer la búsqueda.
     */
    private Date fechaDesde;
    
    /**
     * Fecha hasta la que se quiere hacer la búsqueda.
     */
    private Date fechaHasta;
    
    /**
     * Nombre por el que se desea hacer la búsqueda.
     */
    private String nombre;
    
    /**
     * Tipo de documento que se desea buscar.
     */
    private TipoDocumento tipoDocumento;
    
    /**
     * Descripción por la que se desea hacer la búsqueda.
     */
    private String descripcion;
    
    /**
     * Palabras claves por las que se desea hacer la búsqueda.
     */
    private String materiaIndexada;
    
    /**
     * Inspección sobre la que se desea hacer la búsqueda.
     */
    private Inspeccion inspeccion;
    
    /**
     * Discriminador de documentos eliminados/no eliminados en la búsqueda.
     */
    private boolean eliminado;
    
    /**
     * Crea un objeto de búsqueda a partir de otro.
     * 
     * @param original El objeto a duplicar
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
    
}
