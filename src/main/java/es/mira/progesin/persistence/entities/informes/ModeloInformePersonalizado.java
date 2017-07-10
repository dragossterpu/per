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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity asociada a un modelo de informe personalizado.
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
@Table(name = "MODELOS_INFORME_PERSONALIZADOS")
@NamedEntityGraph(name = "InformePersonalizado.subareas", attributeNodes = @NamedAttributeNode("subareas"))
public class ModeloInformePersonalizado implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del modelo de informe. Generada por secuencia.
     */
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "seq_informepersonal", sequenceName = "seq_informepersonal", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_informepersonal")
    private Long id;
    
    /**
     * Nombre del informe personalizado.
     */
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    
    /**
     * Modelo del informe a partir del que se crea el informe personalizado.
     */
    @ManyToOne
    @JoinColumn(name = "id_modelo_informe", foreignKey = @ForeignKey(name = "fk_modelo_informe"), nullable = false)
    private ModeloInforme modeloInforme;
    
    /**
     * Lista de subáreas que formarán el informe personalizado.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "informe_personal_subareas", joinColumns = @JoinColumn(name = "id_informe_pers", foreignKey = @ForeignKey(name = "fk_infor_pers")), inverseJoinColumns = @JoinColumn(name = "id_subarea", foreignKey = @ForeignKey(name = "fk_subarea_inf_pers")))
    @OrderBy("orden")
    private List<SubareaInforme> subareas;
    
    /**
     * Usuario de creación.
     */
    @CreatedBy
    @Column(name = "username_alta", nullable = false)
    private String usernameAlta;
    
    /**
     * Fecha de creaión.
     */
    @CreatedDate
    @Column(name = "fecha_alta", nullable = false)
    private Date fechaAlta;
    
    /**
     * Usuario de baja.
     */
    @Column(name = "username_baja")
    private String usernameBaja;
    
    /**
     * Fecha de baja.
     */
    @Column(name = "fecha_baja")
    private Date fechaBaja;
}
