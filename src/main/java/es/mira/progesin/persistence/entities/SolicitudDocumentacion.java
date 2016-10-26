package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Table(name = "solicituddocumentacion", schema = "public")
public class SolicitudDocumentacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "codigo")
	private String codigo;

	@Column(name = "descripcion")
	private String descripcion;

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

	@CreatedDate
	@Column(name = "fecha_alta", nullable = false)
	protected Date fechaAlta;

	@LastModifiedDate
	@Column(name = "fecha_modificacion")
	protected Date fechaModificacion;

	@CreatedBy
	@Column(name = "username_alta", length = 12, nullable = false)
	protected String usernameAlta;

	@Column(name = "fecha_envio")
	protected Date fechaEnvio;
}
