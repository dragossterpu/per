package es.mira.progesin.persistence.entities.gd;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "tipodocumentacionprevia")

public class TipoDocumentacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "id_tipo_doc_seq", sequenceName = "id_tipo_doc_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_TIPO_DOCUMENTO", unique = true, updatable = false)
	private Long idTipoDocumento;

	@Column(name = "DESCRIPCION", length = 255)
	private String descripcion;

	@Column(name = "NOMBRE", length = 255)
	private String nombre;

	@Column(name = "EXTENSION", length = 25)
	private String extension;
}
