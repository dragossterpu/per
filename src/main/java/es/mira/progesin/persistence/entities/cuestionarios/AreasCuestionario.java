package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
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
 * Entity para el área de un modelo de cuestionario
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@Getter
@Setter
@Entity
@Table(name = "areascuestionario")
@NamedEntityGraph(name = "AreasCuestionario.preguntas", attributeNodes = @NamedAttributeNode("preguntas"))
public class AreasCuestionario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "seq_areascuestionarios", sequenceName = "seq_areascuestionarios", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_areascuestionarios")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "nombre_area", nullable = false)
    private String area;
    
    private Integer idCuestionario;
    
    @Column(name = "orden")
    private Integer orden;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_area")
    private List<PreguntasCuestionario> preguntas;
    
    @Column(name = "fecha_baja")
    private Date fechaBaja;
    
    @Column(name = "username_baja")
    private String usernameBaja;
    
    @Override
    public String toString() {
        return "AreasCuestionario [id=" + id + ", area=" + area + ", idCuestionario=" + idCuestionario + ", orden="
                + orden + ", fechaBaja=" + fechaBaja + ", usernameBaja=" + usernameBaja + "]";
    }
    
}