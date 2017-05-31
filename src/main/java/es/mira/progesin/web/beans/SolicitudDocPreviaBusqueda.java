package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.SolicitudDocPreviaEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador búsqueda de documentación necesaria para las solicitudes de documentación previas al envío de
 * cuestionarios. Reseteo de campos de búsqueda de documentación previa al envío de cuestionarios.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.gd.TipoDocumentacion
 */

@Setter
@Getter
public class SolicitudDocPreviaBusqueda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Date fechaDesde;
    
    private Date fechaHasta;
    
    private SolicitudDocPreviaEnum estado;
    
    private String usuarioCreacion;
    
    private String idInspeccion;
    
    private String anioInspeccion;
    
    private TipoInspeccion tipoInspeccion;
    
    private AmbitoInspeccionEnum ambitoInspeccion;
    
    private String nombreUnidad;
    
    /**
     * Limpia valores seleccionados en anteriores búsquedas
     * 
     * @author EZENTIS
     */
    public void resetValues() {
        setFechaDesde(null);
        setFechaHasta(null);
        setEstado(null);
        setUsuarioCreacion(null);
        setIdInspeccion(null);
        setAnioInspeccion(null);
        setTipoInspeccion(null);
        setAmbitoInspeccion(null);
        setNombreUnidad(null);
    }
    
}
