package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity asociada a un informe.
 * 
 * @author EZENTIS
 */
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "INFORMES")
public class Informe implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del informe. Generada por secuencia.
     */
    @Id
    @SequenceGenerator(name = "seq_informe", sequenceName = "seq_informe", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_informe")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Modelo en que está basado el informe.
     */
    @ManyToOne
    @JoinColumn(name = "modelo_informe_id", foreignKey = @ForeignKey(name = "fk_modelo_informe"))
    private ModeloInforme modelo;
    
    /**
     * Inspección a la que pertenece el informe.
     */
    @OneToOne
    @JoinColumn(name = "inspeccion_id", foreignKey = @ForeignKey(name = "fk_insp_informe"))
    private Inspeccion inspeccion;
    
    /**
     * Respuestas a las subareas del informe.
     */
    @OneToMany(mappedBy = "informe")
    private List<RespuestaInforme> respuestas;
    
}
