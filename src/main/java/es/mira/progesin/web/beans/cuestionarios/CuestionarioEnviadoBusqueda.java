package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.CuestionarioEnviadoEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * POJO usado para el buscador de cuestionarios enviados.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
@NoArgsConstructor
public class CuestionarioEnviadoBusqueda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Fecha envio de cuestionarios desde la que busca.
     */
    private Date fechaDesde;
    
    /**
     * Fecha envio de cuestionarios hasta la que busca.
     */
    private Date fechaHasta;
    
    /**
     * Fecha respuesta de cuestionarios hasta la que busca.
     */
    private Date fechaLimiteRespuesta;
    
    /**
     * Filtro estado del cuestionario enviado.
     */
    private CuestionarioEnviadoEnum estado;
    
    /**
     * Filtro username del usuario que envió el cuestionario.
     */
    private String usernameEnvio;
    
    /**
     * Filtro número inspección a la que pertenece.
     */
    private String idInspeccion;
    
    /**
     * Filtro año inspección a la que pertenece.
     */
    private String anioInspeccion;
    
    /**
     * Filtro número inspección a la que pertenece.
     */
    private TipoInspeccion tipoInspeccion;
    
    /**
     * Filtro ámbito inspección a la que pertenece.
     */
    private AmbitoInspeccionEnum ambitoInspeccion;
    
    /**
     * Filtro nombre equipo encargada de la inspección a la que pertenece.
     */
    private String nombreEquipo;
    
    /**
     * Filtro nombre unidad inspeccionada a la que se envía el cuestionario.
     */
    private String nombreUnidad;
    
    /**
     * Filtro nombre del cuestionario personalizado enviado.
     */
    private String nombreCuestionario;
    
    /**
     * Filtro modelo de cuestionario en el que se vasa el enviado.
     */
    private ModeloCuestionario modeloCuestionarioSeleccionado;
    
}
