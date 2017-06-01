package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clave primera de RespuestaCuestionario.
 * 
 * @author EZENTIS
 *
 */
@EqualsAndHashCode(of = { "cuestionarioEnviado", "pregunta" })
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class RespuestaCuestionarioId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * ID del cuestionario enviado.
     */
    @OneToOne
    @JoinColumn(name = "id_cuest_enviado", foreignKey = @ForeignKey(name = "fk_rc_cuest_enviado"))
    private CuestionarioEnvio cuestionarioEnviado;
    
    /**
     * ID de la pregunta.
     */
    @OneToOne
    @JoinColumn(name = "id_pregunta", foreignKey = @ForeignKey(name = "fk_rc_pregunta"))
    private PreguntasCuestionario pregunta;
    
}
