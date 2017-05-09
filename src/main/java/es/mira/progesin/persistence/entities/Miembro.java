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
 * Entity para los miembros pertenecientes a un equipo
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
    
    @Id
    @SequenceGenerator(name = "seq_miembros", sequenceName = "seq_miembros", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_miembros")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ID_EQUIPO", foreignKey = @ForeignKey(name = "FK_M_EQUIPO"))
    private Equipo equipo;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    
    @Column(name = "posicion")
    @Enumerated(EnumType.STRING)
    private RolEquipoEnum posicion;
    
    // @Override
    // public int hashCode() {
    // final int prime = 31;
    // int result = 1;
    // result = prime * result + ((equipo == null) ? 0 : equipo.hashCode());
    // result = prime * result + ((nombreCompleto == null) ? 0 : nombreCompleto.hashCode());
    // result = prime * result + ((posicion == null) ? 0 : posicion.hashCode());
    // result = prime * result + ((username == null) ? 0 : username.hashCode());
    // return result;
    // }
    //
    // @Override
    // public boolean equals(Object obj) {
    // if (this == obj)
    // return true;
    // if (obj == null)
    // return false;
    // if (getClass() != obj.getClass())
    // return false;
    // Miembro other = (Miembro) obj;
    // if (equipo == null) {
    // if (other.equipo != null)
    // return false;
    // } else if (!equipo.equals(other.equipo))
    // return false;
    // if (nombreCompleto == null) {
    // if (other.nombreCompleto != null)
    // return false;
    // } else if (!nombreCompleto.equals(other.nombreCompleto))
    // return false;
    // if (posicion != other.posicion)
    // return false;
    // if (username == null) {
    // if (other.username != null)
    // return false;
    // } else if (!username.equals(other.username))
    // return false;
    // return true;
    // }
    
}
