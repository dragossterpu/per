package es.mira.progesin.persistence.entities.cuestionarios;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity con la información relativa a un cuestionario personalizado.
 * 
 * @author EZENTIS
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "CUESTIONARIO_PERSONALIZADO")
public class CuestionarioPersonalizado implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID.
     */
    @Id
    @SequenceGenerator(name = "seq_cuestionariopersonalizado", sequenceName = "seq_cuestionariopersonalizado", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cuestionariopersonalizado")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Nombre del cuestionario.
     */
    @Column(name = "nombre_cuestionario", nullable = false, length = 100)
    private String nombreCuestionario;
    
    /**
     * Fecha de creación.
     */
    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false)
    private Date fechaCreacion;
    
    /**
     * Usuario de creación.
     */
    @CreatedBy
    @Column(name = "username_creacion", nullable = false)
    private String usernameCreacion;
    
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
     * Modelo del cuestionario del que se crea.
     */
    @ManyToOne
    @JoinColumn(name = "id_modelo_cuestionario", foreignKey = @ForeignKey(name = "fk_cp_modelo_cuestionario"), nullable = false)
    private ModeloCuestionario modeloCuestionario;
    
    /**
     * Lista de preguntas del cuestionario.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cuest_per_preguntas", joinColumns = @JoinColumn(name = "id_cuest_pers"), inverseJoinColumns = @JoinColumn(name = "id_preg_elegida"))
    private List<PreguntasCuestionario> preguntasElegidas;
}
