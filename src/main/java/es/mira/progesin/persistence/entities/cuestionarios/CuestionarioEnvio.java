package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import es.mira.progesin.persistence.entities.Inspeccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity con la información relativa al envío de un cuestionario personalizado.
 * 
 * @author EZENTIS
 *
 */
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "CUESTIONARIOS_ENVIADOS")
public class CuestionarioEnvio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador del cuestionario envío. Generada por secuencia.
     */
    @Id
    @SequenceGenerator(name = "seq_cuestionariosenviados", sequenceName = "seq_cuestionariosenviados", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cuestionariosenviados")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Cuestionario personalizado.
     */
    @ManyToOne
    @JoinColumn(name = "id_cuestionario_personalizado", foreignKey = @ForeignKey(name = "fk_ce_cuest_person"), nullable = false)
    private CuestionarioPersonalizado cuestionarioPersonalizado;
    
    /**
     * Inspección asignada.
     */
    @ManyToOne
    @JoinColumn(name = "id_inspeccion", foreignKey = @ForeignKey(name = "fk_ce_inspeccion"), nullable = false)
    private Inspeccion inspeccion;
    
    /**
     * Nombre del usuario.
     */
    @Column(name = "nombre_usuario", length = 500, nullable = false)
    private String nombreUsuarioEnvio;
    
    /**
     * Cargo del usuario.
     */
    @Column(name = "cargo", length = 500)
    private String cargo;
    
    /**
     * Correo al que se envía.
     */
    @Column(name = "correo", length = 500, nullable = false)
    private String correoEnvio;
    
    /**
     * Motivo por el que se envía.
     */
    @Column(name = "motivo", length = 2000, nullable = false)
    private String motivoCuestionario;
    
    /**
     * Fecha límite para enviar el cuestionario.
     */
    @Column(nullable = false)
    private Date fechaLimiteCuestionario;
    
    /**
     * Fecha en la que se cumplimenta en cuestionario.
     */
    @Column
    private Date fechaCumplimentacion;
    
    /**
     * Fecha de no conformidad.
     */
    @Column
    private Date fechaNoConforme;
    
    /**
     * Username del suario que da la no conformidad.
     */
    @Column
    private String usernameNoConforme;
    
    /**
     * Fecha en la que se finaliza.
     */
    @Column
    private Date fechaFinalizacion;
    
    /**
     * Username del usuario que finaliza.
     */
    @Column
    private String usernameFinalizacion;
    
    /**
     * Fecha en la que se envía.
     */
    @CreatedDate
    private Date fechaEnvio;
    
    /**
     * Username del usuario que hace el envío.
     */
    @CreatedBy
    private String usernameEnvio;
    
    /**
     * Fecha en la que se anula.
     */
    @Column
    private Date fechaAnulacion;
    
    /**
     * Username del usuario que anula.
     */
    @Column
    private String usernameAnulacion;
    
}
