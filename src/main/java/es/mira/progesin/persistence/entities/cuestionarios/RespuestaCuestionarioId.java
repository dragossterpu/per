package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author EZENTIS
 *
 */
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
    
    /**
     * Sobreescritura del método hasCode().
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cuestionarioEnviado == null) ? 0 : cuestionarioEnviado.hashCode());
        result = prime * result + ((pregunta == null) ? 0 : pregunta.hashCode());
        return result;
    }
    
    /**
     * Sobreescritura del método equals().
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RespuestaCuestionarioId other = (RespuestaCuestionarioId) obj;
        if (cuestionarioEnviado == null) {
            if (other.cuestionarioEnviado != null)
                return false;
        } else if (!cuestionarioEnviado.getId().equals(other.cuestionarioEnviado.getId()))
            return false;
        if (pregunta == null) {
            if (other.pregunta != null)
                return false;
        } else if (!pregunta.getId().equals(other.pregunta.getId()))
            return false;
        return true;
    }
    
}
