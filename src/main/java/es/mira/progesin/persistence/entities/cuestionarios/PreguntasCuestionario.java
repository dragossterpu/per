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
@Getter
@Setter
@Entity
@Table(name = "preguntascuestionario")
public class PreguntasCuestionario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID.
     */
    @Id
    @SequenceGenerator(name = "seq_preguntascuestionario", sequenceName = "seq_preguntascuestionario", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_preguntascuestionario")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Pregunta.
     */
    @Column(name = "pregunta", nullable = false, length = 2000)
    private String pregunta;
    
    /**
     * Área a la que pertenecen las preguntas.
     */
    @ManyToOne
    @JoinColumn(name = "id_area", foreignKey = @ForeignKey(name = "fk_pc_area"))
    private AreasCuestionario area;
    
    /**
     * Orden de la pregunta dentro del área.
     */
    @Column(name = "orden")
    private Integer orden;
    
    /**
     * Tipo de respuesta.
     */
    @Column(name = "tipo_respuesta", nullable = true, length = 100)
    private String tipoRespuesta;
    
    /**
     * Fecha de baja.
     */
    @Column(name = "fecha_baja")
    private Date fechaBaja;
    
    /**
     * Usuario de baja.
     */
    @Column(name = "username_baja")
    private String usernameBaja;
    
    /**
     * Sobreescritura del método toString para poder utilizarlo en el conversor implementado para los OrderList de
     * Primefaces.
     */
    @Override
    public String toString() {
        return pregunta;
    }
    
}
