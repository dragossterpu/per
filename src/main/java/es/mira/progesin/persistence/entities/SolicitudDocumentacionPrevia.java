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
@Table(name = "solicituddocumentacionprevia", schema = "public")
public class SolicitudDocumentacionPrevia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "asunto")
	private String asunto;

	@Column(name = "numeroReferencia")
	private String numeroReferencia;

	@Column(name = "destinatario")
	private String destinatario;

	@Column(name = "correoDestiantario")
	private String correoDestiantario;

	@Column(name = "tipoInspeccion")
	private String tipoInspeccion;

	@Column(name = "identificadorTrimestre")
	private String identificadorTrimestre;

	@Column(name = "fechaAntes")
	private Date fechaAntes;

	@Column(name = "fechaLimiteCumplimentar")
	private Date fechaLimiteCumplimentar;

	@Column(name = "apoyoPuesto")
	private String apoyoPuesto;

	@Column(name = "apoyoNombre")
	private String apoyoNombre;

	@Column(name = "apoyoTelefono")
	private String apoyoTelefono;

	@Column(name = "apoyoCorreo")
	private String apoyoCorreo;

	@Column(name = "puestoJefeInspectorAuditor")
	private String puestoJefeInspectorAuditor;

	@Column(name = "nombreJefeInspectorAuditor")
	private String nombreJefeInspectorAuditor;

	@Column(name = "fechaAlta")
	private Date fechaAlta;

	@Column(name = "username_alta", length = 12, nullable = false)
	private String usernameAlta;

	@Column(name = "fecha_envio")
	private Date fechaEnvio;

	@Column(name = "nombreCuestionarioPrevio")
	private String nombreCuestionarioPrevio;

	@Column(name = "fechaValidApoyo")
	private Date fechaValidApoyo;

	@Column(name = "usernameValidApoyo")
	private String usernameValidApoyo;

	@Column(name = "usernameEnvio")
	private String usernameEnvio;

	@Column(name = "nombreCompletoInterlocutor")
	private String nombreCompletoInterlocutor;

	@Column(name = "categoriaInterlocutor")
	private String categoriaInterlocutor;

	@Column(name = "cargoInterlocutor")
	private String cargoInterlocutor;

	@Column(name = "correoCorporativoInterlocutor")
	private String correoCorporativoInterlocutor;

	@Column(name = "telefonoInterlocutor")
	private String telefonoInterlocutor;

	@Column(name = "mensajeCorreo")
	private String mensajeCorreo;

	@Column(name = "fechaFinalizacion")
	private Date fechaFinalizacion;

	@Column(name = "usuarioFinalizacion")
	private String usuarioFinalizacion;

	@Column(name = "fechaCumplimentacion")
	private Date fechaCumplimentacion;
}
