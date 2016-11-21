package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import es.mira.progesin.persistence.entities.enums.CuatrimestreEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO Solicitud de documentación previa basada en un modelo de solicitud y asociada a una inspección. Por cada
 * solicitud podrá haber documentación previa que el interlocutor de una unidad deberá subir, por medio de un usuario
 * provisional.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.ModeloSolicitud
 * @see es.mira.progesin.persistence.entities.Inspeccion
 * @see es.mira.progesin.persistence.entities.DocumentacionPrevia
 * @see es.mira.progesin.web.beans.SolicitudDocPreviaBean
 * @see es.mira.progesin.web.beans.ProvisionalSolicitudBean
 */
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

	@ManyToOne
	@JoinColumn(name = "id_inspeccion")
	private Inspeccion inspeccion;

	@Column(name = "destinatario")
	private String destinatario;

	@Column(name = "correoDestiantario")
	private String correoDestiantario;

	@Column(name = "cuatrimestre")
	@Enumerated(EnumType.STRING)
	private CuatrimestreEnum cuatrimestre;

	@Column(name = "anio")
	private Integer anio;

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

	@CreatedDate
	@Column(name = "fechaAlta")
	private Date fechaAlta;

	@CreatedBy
	@Column(name = "username_alta", nullable = false)
	private String usernameAlta;

	@Column(name = "fecha_envio")
	private Date fechaEnvio;

	@Column(name = "nombreSolicitud")
	private String nombreSolicitud;

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

	@Column(name = "correoCorporativoInterlocutorCompl")
	private String correoCorporativoInterlocutorCompl;

	@Column(name = "telefonoInterlocutor")
	private String telefonoInterlocutor;

	@Column(name = "fechaFinalizacion")
	private Date fechaFinalizacion;

	@Column(name = "usuarioFinalizacion")
	private String usuarioFinalizacion;

	@Column(name = "fechaCumplimentacion")
	private Date fechaCumplimentacion;
}
