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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.Documento;
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
@Table(name = "respuestascuestionario")
public class RespuestaCuestionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_respuestascuestionario", sequenceName = "seq_respuestascuestionario", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_respuestascuestionario")
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "id_cuest_enviado")
	private CuestionarioEnvio cuestionarioEnviado;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_pregunta")
	private PreguntasCuestionario pregunta;

	// private DataTableView respuestaTablaMatriz;

	@OneToMany(mappedBy = "respuesta", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<DatosTablaGenerica> respuestaTablaMatriz;

	private String respuestaTexto;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "respuestas_cuest_docs", joinColumns = @JoinColumn(name = "id_respuesta"), inverseJoinColumns = @JoinColumn(name = "id_documento"))
	private List<Documento> documentos;
}
