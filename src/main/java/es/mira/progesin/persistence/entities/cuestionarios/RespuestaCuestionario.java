package es.mira.progesin.persistence.entities.cuestionarios;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "respuestascuestionario")
@NamedEntityGraph(name = "RespuestaCuestionario.documentos", attributeNodes = @NamedAttributeNode("documentos"))
public class RespuestaCuestionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	RespuestaCuestionarioId respuestaId;

	@OneToMany(mappedBy = "respuesta", fetch = FetchType.EAGER)
	private List<DatosTablaGenerica> respuestaTablaMatriz;

	@Column(name = "respuesta_texto", length = 2000)
	private String respuestaTexto;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "respuestas_cuest_docs", joinColumns = { @JoinColumn(name = "id_cuestionario_enviado"),
			@JoinColumn(name = "id_pregunta") }, inverseJoinColumns = @JoinColumn(name = "id_documento"))
	private List<Documento> documentos;

	@CreatedBy
	private String usernameCreacion;

	@CreatedDate
	private Date fechaCreacion;

	@LastModifiedBy
	private String usernameModificacion;

	@LastModifiedDate
	private Date fechaModificacion;

	@Column
	private Date fechaValidacion;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((respuestaId == null) ? 0 : respuestaId.hashCode());
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
		if (respuestaId == null) {
			if (other.respuestaId != null)
				return false;
		}
		else if (!respuestaId.equals(other.respuestaId))
			return false;
		return true;
	}

}
