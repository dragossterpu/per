package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "preguntascuestionario")
public class PreguntasCuestionario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "seq_preguntascuestionario", sequenceName = "seq_preguntascuestionario", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_preguntascuestionario")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "pregunta", nullable = false, length = 2000)
    private String pregunta;
    
    @ManyToOne
    @JoinColumn(name = "id_area")
    private AreasCuestionario area;
    
    @Column(name = "orden")
    private Integer orden;
    
    @Column(name = "tipo_respuesta", nullable = true, length = 100)
    private String tipoRespuesta;
    
    private Date fechaBaja;
    
    private String usernameBaja;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        PreguntasCuestionario other = (PreguntasCuestionario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
}
