package es.mira.progesin.persistence.entities.informes;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity asociada a un modelo de informe.
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "MODELOS_INFORME")
@NamedEntityGraph(name = "ModeloInforme.areas", attributeNodes = @NamedAttributeNode("areas"))
public class ModeloInforme implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del modelo de informe. Generada por secuencia.
     */
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "seq_modeloinforme", sequenceName = "seq_modeloinforme", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_modeloinforme")
    private Long id;
    
    /**
     * Nombre del modelo.
     */
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    
    /**
     * Determina si es un modelo estandar.
     */
    @Column(name = "estandar")
    private Boolean estandar;
    
    /**
     * Areas que conforman el modelo de informe.
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelo_informe_id", foreignKey = @ForeignKey(name = "fk_area_modeloinf"))
    @OrderBy("orden")
    private List<AreaInforme> areas;
    
    /**
     * Usuario de creación.
     */
    @CreatedBy
    @Column(name = "username_alta", nullable = false)
    protected String usernameAlta;
    
    /**
     * Fecha de creación.
     */
    @CreatedDate
    @Column(name = "fecha_alta", nullable = false)
    protected Date fechaAlta;
    
    /**
     * Usuario de baja.
     */
    @Column(name = "username_baja")
    protected String usernameBaja;
    
    /**
     * Fecha de baja.
     */
    @Column(name = "fecha_baja")
    protected Date fechaBaja;
    
}
