package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@Getter
@Setter
@Entity
@Table(name = "areascuestionario", schema = "public")
public class AreasCuestionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "area", nullable = false)
	private String area;

	private Integer idCuestionario;

	@OneToMany(mappedBy = "area", fetch = FetchType.EAGER)
	private List<PreguntasCuestionario> preguntas;

	@Override
	public String toString() {
		return "AreasCuestionario [id=" + id + ", area=" + area + ", idCuestionario=" + idCuestionario + "]";
	}

}
