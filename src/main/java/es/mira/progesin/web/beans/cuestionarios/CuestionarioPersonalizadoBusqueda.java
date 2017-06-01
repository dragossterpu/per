package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * POJO con los parámetros de búsqueda de cuestionarios personalizados.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
public class CuestionarioPersonalizadoBusqueda implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Modelo cuestionario por el que buscar.
     */
    private ModeloCuestionario modeloCuestionarioSeleccionado;
    
    /**
     * Fecha desde.
     */
    private Date fechaDesde;
    
    /**
     * Fecha hasta.
     */
    private Date fechaHasta;
    
    /**
     * Nombre de usuario de creación del cuestionario personalizado.
     */
    private String username;
    
    /**
     * Nombre del cuestionario personalizado.
     */
    private String nombreCuestionario;
    
    /**
     * Estado del cuestionario.
     */
    private EstadoEnum estado;
    
    /**
     * Limpia valores seleccionados en anteriores búsquedas.
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
