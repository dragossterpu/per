package es.mira.progesin.persistence.entities.gd;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.LastModifiedDate;

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
@Table(name = "gestdocsolicituddocumentacion", schema = "public")
public class GestDocSolicitudDocumentacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "idSolicitud")
	private Integer idSolicitud;

	@Column(name = "extension", nullable = false, length = 4)
	private String extension;

	// no vale con postgres
	// @Column(name = "fichero")
	// @Lob
	// private Blob fichero;

	@Column(name = "nombre", nullable = false)
	private String nombreFichero;

	@Column(name = "fichero", nullable = false)
	private byte[] fichero;

	@Column(name = "fecha_alta", nullable = false)
	protected Date fechaAlta;

	@LastModifiedDate
	@Column(name = "fecha_modificacion")
	protected Date fechaModificacion;

	@Column(name = "fecha_baja")
	protected Date fechaBaja;

	@Column(name = "username_alta", length = 12, nullable = false)
	protected String usernameAlta;

	@Column(name = "username_modificacion")
	protected String usernameModificacion;

	@Column(name = "username_Baja")
	protected String usernameBaja;

}
