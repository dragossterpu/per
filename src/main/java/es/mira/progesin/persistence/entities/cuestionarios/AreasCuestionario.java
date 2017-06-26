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
 * 
 * Entity para el área de un modelo de cuestionario.
 * 
 * @author EZENTIS
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
    
    /**
     * Identificador de areascuestionario.
     */
    @Id
    @SequenceGenerator(name = "seq_areascuestionarios", sequenceName = "seq_areascuestionarios", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_areascuestionarios")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Área.
     */
    @Column(name = "nombre_area", nullable = false)
    private String area;
    
    /**
     * Identificador de cuestionario.
     */
    private Integer idCuestionario;
    
    /**
     * Orden.
     */
    @Column(name = "orden")
    private Integer orden;
    
    /**
     * Listado de preguntas.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_area")
    private List<PreguntasCuestionario> preguntas;
    
    /**
     * Fecha de baja.
     */
    @Column(name = "fecha_baja")
    private Date fechaBaja;
    
    /**
     * Usuario que da de baja.
     */
    @Column(name = "username_baja")
    private String usernameBaja;
    
    /**
     * Sobreescritura del método toString para poder utilizarlo en el conversor implementado para los OrderList de
     * Primefaces.
     */
    @Override
    public String toString() {
        return area;
    }
    
}