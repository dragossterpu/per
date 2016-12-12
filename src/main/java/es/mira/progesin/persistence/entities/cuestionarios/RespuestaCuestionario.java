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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentos == null) ? 0 : documentos.hashCode());
		result = prime * result + ((respuestaId == null) ? 0 : respuestaId.hashCode());
		result = prime * result + ((respuestaTablaMatriz == null) ? 0 : respuestaTablaMatriz.hashCode());
		result = prime * result + ((respuestaTexto == null) ? 0 : respuestaTexto.hashCode());
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
		RespuestaCuestionario other = (RespuestaCuestionario) obj;
		if (documentos == null) {
			if (other.documentos != null)
				return false;
		}
		else if (!documentos.equals(other.documentos))
			return false;
		if (respuestaId == null) {
			if (other.respuestaId != null)
				return false;
		}
		else if (!respuestaId.equals(other.respuestaId))
			return false;
		if (respuestaTablaMatriz == null) {
			if (other.respuestaTablaMatriz != null)
				return false;
		}
		else if (!respuestaTablaMatriz.equals(other.respuestaTablaMatriz))
			return false;
		if (respuestaTexto == null) {
			if (other.respuestaTexto != null)
				return false;
		}
		else if (!respuestaTexto.equals(other.respuestaTexto))
			return false;
		return true;
	}

}
