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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity con la información relativa al envío de un cuestionario personalizado
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
@Table(name = "CUESTIONARIOS_ENVIADOS")
public class CuestionarioEnvio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "seq_cuestionariosenviados", sequenceName = "seq_cuestionariosenviados", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cuestionariosenviados")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_cuestionario_personalizado", foreignKey = @ForeignKey(name = "fk_ce_cuest_person"), nullable = false)
    private CuestionarioPersonalizado cuestionarioPersonalizado;
    
    @ManyToOne
    @JoinColumn(name = "id_inspeccion", foreignKey = @ForeignKey(name = "fk_ce_inspeccion"), nullable = false)
    private Inspeccion inspeccion;
    
    @Column(name = "nombre_usuario", length = 500, nullable = false)
    private String nombreUsuarioEnvio;
    
    @Column(name = "cargo", length = 500)
    private String cargo;
    
    @Column(name = "correo", length = 500, nullable = false)
    private String correoEnvio;
    
    @Column(name = "motivo", length = 2000, nullable = false)
    private String motivoCuestionario;
    
    @Column(nullable = false)
    private Date fechaLimiteCuestionario;
    
    @Column
    private Date fechaCumplimentacion;
    
    @Column
    private Date fechaNoConforme;
    
    @Column
    private String usernameNoConforme;
    
    @Column
    private Date fechaFinalizacion;
    
    @Column
    private String usernameFinalizacion;
    
    @CreatedDate
    private Date fechaEnvio;
    
    @CreatedBy
    private String usernameEnvio;
    
    @Column
    private Date fechaAnulacion;
    
    @Column
    private String usernameAnulacion;
    
}
