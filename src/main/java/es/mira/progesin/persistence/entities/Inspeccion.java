package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.CuatrimestreEnum;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "inspecciones")
public class Inspeccion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "seq_inspeccion", sequenceName = "seq_inspeccion", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_inspeccion")
    @Column(name = "id")
    private Long id;
    
    @Column(name = "numero", length = 100, nullable = false)
    private String numero;
    
    @ManyToOne
    @JoinColumn(name = "tipo_inspeccion", foreignKey = @ForeignKey(name = "fk_i_tipo_inspeccion"))
    private TipoInspeccion tipoInspeccion;
    
    @ManyToOne
    @JoinColumn(name = "id_equipo", foreignKey = @ForeignKey(name = "fk_i_equipo"), nullable = false)
    private Equipo equipo;
    
    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // @JoinColumn(name = "inspeccion")
    // private List<SolicitudDocumentacionPrevia> solicitudes;
    //
    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // @JoinColumn(name = "inspeccion")
    // private List<CuestionarioEnvio> cuestionarios;
    
    @Column(name = "nombre_unidad")
    private String nombreUnidad;
    
    @Column(name = "tipo_unidad")
    private String tipoUnidad;
    
    @Column(name = "ambito", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private AmbitoInspeccionEnum ambito;
    
    @Column(name = "cuatrimestre", length = 30)
    @Enumerated(EnumType.STRING)
    private CuatrimestreEnum cuatrimestre;
    
    @Column(name = "estado_inspeccion", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoInspeccionEnum estadoInspeccion;
    
    @Column(name = "anio", nullable = false)
    private Integer anio;
    
    @CreatedDate
    @Column(name = "fecha_alta", nullable = false)
    private Date fechaAlta;
    
    @CreatedBy
    @Column(name = "username_alta", nullable = false)
    private String usernameAlta;
    
    @Column(name = "fecha_baja")
    private Date fechaBaja;
    
    @Column(name = "username_baja")
    private String usernameBaja;
    
    @Column(name = "fecha_finalizacion")
    private Date fechaFinalizacion;
    
    @CreatedBy
    @Column(name = "username_finalizacion")
    private String usernameFinalizacion;
    
}
