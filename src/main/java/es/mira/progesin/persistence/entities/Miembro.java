package es.mira.progesin.persistence.entities;

import java.io.Serializable;

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

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity para almacenar los miembros pertenecientes a un equipo.
 * 
 * @author EZENTIS
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Table(name = "MIEMBROS")
public class Miembro implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * ID.
     */
    @Id
    @SequenceGenerator(name = "seq_miembros", sequenceName = "seq_miembros", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_miembros")
    @Column(name = "id", nullable = false)
    private Long id;
    
    /**
     * Equipo al que pertenece el miembro.
     */
    @ManyToOne
    @JoinColumn(name = "ID_EQUIPO", foreignKey = @ForeignKey(name = "FK_M_EQUIPO"))
    private Equipo equipo;
    
    /**
     * Login del miembro.
     */
    @Column(name = "username")
    private String username;
    
    /**
     * Nombre compelto del miembro.
     */
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    
    /**
     * Posición, rol dentro del equipo: jefe, componente o colaborador.
     */
    @Column(name = "posicion")
    @Enumerated(EnumType.STRING)
    private RolEquipoEnum posicion;
    
}
