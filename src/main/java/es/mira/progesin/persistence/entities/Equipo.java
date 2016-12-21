package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Table(name = "EQUIPO")

public class Equipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_equipo", sequenceName = "seq_equipo", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_equipo")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "nombreEquipo", length = 100, nullable = false)
	private String nombreEquipo;

	@ManyToOne
	@JoinColumn(name = "idTipoEquipo")
	private TipoEquipo tipoEquipo;

	@Column(name = "jefeEquipo", length = 100, nullable = false)
	private String jefeEquipo;

	@CreatedDate
	@Column(name = "fecha_alta", nullable = false)
	private Date fechaAlta;

	@Column(name = "fecha_baja")
	private Date fechaBaja;

	@CreatedBy
	@Column(name = "username_alta", length = 12, nullable = false)
	private String usernameAlta;

	@Column(name = "username_baja", length = 12)
	private String usernameBaja;

	@Column(name = "nombreJefe", length = 150)
	private String nombreJefe;

	@OneToMany(mappedBy = "equipo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Miembro> miembros;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipo other = (Equipo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}

}
