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
 * Entity creada para almacenar un equipo de inspecciones.
 *
 * @author EZENTIS
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
    
    /**
     * ID.
     */
    @Id
    @SequenceGenerator(name = "seq_equipo", sequenceName = "seq_equipo", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_equipo")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Nombre del equipo.
     */
    @Column(name = "nombreEquipo", length = 100, nullable = false)
    private String nombreEquipo;
    
    /**
     * Tipo.
     */
    @ManyToOne
    @JoinColumn(name = "idTipoEquipo", foreignKey = @ForeignKey(name = "fk_eq_TipoEquipo"))
    private TipoEquipo tipoEquipo;
    
    /**
     * User del jefe del equipo.
     */
    
    @ManyToOne
    @JoinColumn(name = "jefeEquipo", foreignKey = @ForeignKey(name = "FK_EQ_JEFE"))
    private User jefeEquipo;
    
    /**
     * Miembros que integran el equipo incluidos jefe y colaboradores.
     */
    @OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Miembro> miembros;
    
    /**
     * Recupera el nombre completo del jefe.
     * 
     * @return nombre completo
     */
    public String getNombreJefe() {
        StringBuilder nombreCompleto = new StringBuilder();
        nombreCompleto.append(this.jefeEquipo.nombre);
        nombreCompleto.append(' ');
        nombreCompleto.append(this.jefeEquipo.apellido1);
        if (this.jefeEquipo.nombre != null) {
            nombreCompleto.append(' ');
            nombreCompleto.append(this.jefeEquipo.apellido2);
        }
        return nombreCompleto.toString();
    }
    
}
