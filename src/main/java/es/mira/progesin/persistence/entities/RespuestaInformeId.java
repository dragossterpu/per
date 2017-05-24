package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clave primera de una respuesta del informe
 * 
 * @author EZENTIS
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// @Embeddable
public class RespuestaInformeId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // @ManyToOne
    // @JoinColumn(name = "informe_id", foreignKey = @ForeignKey(name = "fk_informe"))
    private Informe informe;
    
    // @ManyToOne
    // @JoinColumn(name = "subarea_id", foreignKey = @ForeignKey(name = "fk_subarea_inf"))
    private SubareaInforme subarea;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((informe == null) ? 0 : informe.hashCode());
        result = prime * result + ((subarea == null) ? 0 : subarea.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RespuestaInformeId other = (RespuestaInformeId) obj;
        if (informe == null) {
            if (other.informe != null)
                return false;
        } else if (!informe.getId().equals(other.informe.getId()))
            return false;
        if (subarea == null) {
            if (other.subarea != null)
                return false;
        } else if (!subarea.getId().equals(other.subarea.getId()))
            return false;
        return true;
    }
    
}
