package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "INSPECCIONES")
public class Inspeccion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "seq_inspeccion", sequenceName = "seq_inspeccion", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_inspeccion")
    @Column
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String numero;
    
    @ManyToOne
    @JoinColumn(name = "tipo_inspeccion")
    private TipoInspeccion tipoInspeccion;
    
    @ManyToOne
    @JoinColumn(name = "id_equipo")
    private Equipo equipo;
    
    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // @JoinColumn(name = "inspeccion")
    // private List<SolicitudDocumentacionPrevia> solicitudes;
    //
    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // @JoinColumn(name = "inspeccion")
    // private List<CuestionarioEnvio> cuestionarios;
    
    @Column(length = 100)
    private String nombreUnidad;
    
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private AmbitoInspeccionEnum ambito;
    
    @Column
    @Enumerated(EnumType.STRING)
    private CuatrimestreEnum cuatrimestre;
    
    @Column
    private Integer anio;
    
    @CreatedDate
    @Column(nullable = false)
    private Date fechaAlta;
    
    @CreatedBy
    @Column(nullable = false)
    private String usernameAlta;
    
    @Column
    private Date fechaBaja;
    
    @Column
    private String usernameBaja;
    
    @Column
    private Date fechaFinalizacion;
    
    @Column
    private String usernameFinalizacion;
    
}
