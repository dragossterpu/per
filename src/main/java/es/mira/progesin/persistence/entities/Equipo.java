package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author EZENTIS
 * 
 * Entity para un equipo de inspecciones
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "id")
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "EQUIPO")
public class Equipo extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "seq_equipo", sequenceName = "seq_equipo", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_equipo")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "nombreEquipo", length = 100, nullable = false)
    private String nombreEquipo;
    
    @ManyToOne
    @JoinColumn(name = "idTipoEquipo", foreignKey = @ForeignKey(name = "fk_eq_TipoEquipo"))
    private TipoEquipo tipoEquipo;
    
    @Column(name = "jefeEquipo", length = 100, nullable = false)
    private String jefeEquipo;
    
    @Column(name = "nombreJefe", length = 150)
    private String nombreJefe;
    
    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Miembro> miembros;
    
}
