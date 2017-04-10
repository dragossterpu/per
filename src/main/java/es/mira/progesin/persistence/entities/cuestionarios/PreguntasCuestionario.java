package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author EZENTIS
 * 
 * Entity para una pregunta de un área perteneciente a un modelo de cuestionario
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
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
    @JoinColumn(name = "id_area", foreignKey = @ForeignKey(name = "fk_pc_area"))
    private AreasCuestionario area;
    
    @Column(name = "orden")
    private Integer orden;
    
    @Column(name = "tipo_respuesta", nullable = true, length = 100)
    private String tipoRespuesta;
    
    private Date fechaBaja;
    
    private String usernameBaja;
    
}
