package es.mira.progesin.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "preguntascuestionario", schema = "public")
public class PreguntasCuestionario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "pregunta", nullable = false, length = 4000)
	private String pregunta;

	private Long idArea;
	// @ManyToOne
	// @JoinColumn(name = "id_area", nullable = false)
	// private AreasCuestionario area;

	@Column(name = "tipo_respuesta", nullable = true, length = 100)
	private String tipoRespuesta;
}