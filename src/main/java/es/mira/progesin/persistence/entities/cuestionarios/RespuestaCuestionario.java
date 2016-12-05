package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
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

	@EmbeddedId
	RespuestaCuestionarioId respuestaId;

	@OneToMany(mappedBy = "respuesta", fetch = FetchType.EAGER)
	private List<DatosTablaGenerica> respuestaTablaMatriz;

	private String respuestaTexto;

	@OneToMany
	@JoinTable(name = "respuestas_cuest_docs", joinColumns = { @JoinColumn(name = "id_cuestionario_enviado"),
			@JoinColumn(name = "id_pregunta") }, inverseJoinColumns = @JoinColumn(name = "id_documento"))
	private List<Documento> documentos;
}
