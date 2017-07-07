package es.mira.progesin.persistence.entities.informes;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.mira.progesin.persistence.entities.AbstractEntity;
import es.mira.progesin.persistence.entities.Inspeccion;
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
@EqualsAndHashCode(callSuper = false, of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "INFORMES")
@NamedEntityGraph(name = "Informe.respuestas", attributeNodes = @NamedAttributeNode("respuestas"))
public class Informe extends AbstractEntity implements Serializable {
    
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
    @JoinColumn(name = "informe_personal_id", foreignKey = @ForeignKey(name = "fk_infor_personal"))
    private ModeloInformePersonalizado modeloPersonalizado;
    
    /**
     * Inspección a la que pertenece el informe.
     */
    @OneToOne
    @JoinColumn(name = "inspeccion_id", foreignKey = @ForeignKey(name = "fk_inf_inspecc"))
    private Inspeccion inspeccion;
    
    /**
     * Respuestas a las subareas del informe.
     */
    @OneToMany(mappedBy = "informe", fetch = FetchType.LAZY)
    // @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    // @JoinColumn(name = "informe_id")
    private List<RespuestaInforme> respuestas;
    
    /**
     * Fecha de finalización deL informe.
     */
    @Column(name = "fechaFinalizacion")
    private Date fechaFinalizacion;
    
    /**
     * Usuario de finalización del informe.
     */
    @Column(name = "usernameFinalizacion")
    private String usernameFinalizacion;
    
}
