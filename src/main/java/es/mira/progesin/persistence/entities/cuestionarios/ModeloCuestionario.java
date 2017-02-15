package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "modeloscuestionarios")
public class ModeloCuestionario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_modeloscuestionarios", sequenceName = "seq_modeloscuestionarios", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_modeloscuestionarios")
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "codigo", nullable = false)
	private String codigo;

	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "idCuestionario")
	private List<AreasCuestionario> areas;

}
