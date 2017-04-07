package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * POJO con los parámetros de búsqueda de cuestionarios personalizados
 * 
 * @author EZENTIS
 */
@Setter
@Getter
public class CuestionarioPersonalizadoBusqueda implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ModeloCuestionario modeloCuestionarioSeleccionado;
    
    private Date fechaDesde;
    
    private Date fechaHasta;
    
    private String username;
    
    private String nombreCuestionario;
    
    private EstadoEnum estado;
    
    /**
     * Limpia valores seleccionados en anteriores búsquedas
     * 
     * @author EZENTIS
     */
    public void limpiar() {
        setFechaDesde(null);
        setFechaHasta(null);
        setUsername(null);
        setNombreCuestionario(null);
        setModeloCuestionarioSeleccionado(null);
        setEstado(null);
    }
    
}
