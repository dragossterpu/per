package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "areascuestionario")
@NamedEntityGraph(name = "AreasCuestionario.preguntas", attributeNodes = @NamedAttributeNode("preguntas"))
public class AreasCuestionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_areascuestionarios", sequenceName = "seq_areascuestionarios", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_areascuestionarios")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "nombre_area", nullable = false)
	private String area;

	private Integer idCuestionario;

	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	private List<PreguntasCuestionario> preguntas;

	@Override
	public String toString() {
		return "AreasCuestionario [id=" + id + ", area=" + area + ", cuestionario=" + idCuestionario + "]";
	}

}
