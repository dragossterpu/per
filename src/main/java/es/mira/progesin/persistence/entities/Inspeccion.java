package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.CuatrimestreEnum;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author EZENTIS
 * 
 * Entidad para una inspección
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
@Builder
@Getter
@Setter
@Entity
@Table(name = "inspecciones")
public class Inspeccion extends AbstractEntity implements Serializable, Comparable<Inspeccion> {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "seq_inspeccion", sequenceName = "seq_inspeccion", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_inspeccion")
    @Column(name = "id")
    private Long id;
    
    @Column(name = "numero", length = 100)
    private String numero;
    
    @ManyToOne
    @JoinColumn(name = "tipo_inspeccion", foreignKey = @ForeignKey(name = "fk_i_tipo_inspeccion"), nullable = false)
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
    
    @ManyToOne
    @JoinColumn(name = "tipo_unidad", foreignKey = @ForeignKey(name = "FK_i_TIPOUNIDAD"))
    private TipoUnidad tipoUnidad;
    
    @Column(name = "ambito", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private AmbitoInspeccionEnum ambito;
    
    @Column(name = "cuatrimestre", length = 30)
    @Enumerated(EnumType.STRING)
    private CuatrimestreEnum cuatrimestre;
    
    @Column(name = "estado_inspeccion", length = 30)
    @Enumerated(EnumType.STRING)
    private EstadoInspeccionEnum estadoInspeccion;
    
    @Column(name = "anio", nullable = false)
    private Integer anio;
    
    @ManyToOne
    @JoinColumn(name = "id_municipio", foreignKey = @ForeignKey(name = "FK_i_MUNICIPIO"))
    private Municipio municipio;
    
    @Column(name = "fecha_finalizacion")
    private Date fechaFinalizacion;
    
    @Column(name = "username_finalizacion")
    private String usernameFinalizacion;
    
    @Column(name = "fecha_prevista")
    private Date fechaPrevista;
    
    @Column(name = "username_anulacion")
    protected String usernameAnulacion;
    
    @Column(name = "fecha_anulacion")
    protected Date fechaAnulacion;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "inspecciones_asociadas", joinColumns = {
            @JoinColumn(name = "id_inspeccion") }, inverseJoinColumns = {
                    @JoinColumn(name = "id_inspeccion_asociada") })
    private List<Inspeccion> inspecciones;
    
    @Override
    public int compareTo(Inspeccion i) {
        return this.id.compareTo(i.id);
    }
    
}
