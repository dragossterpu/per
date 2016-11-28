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
import javax.persistence.SequenceGenerator;
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
 * @see es.mira.progesin.persistence.entities.BORRARModeloSolicitud
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
@Table(name = "SOLICITUD_DOC_PREVIA")
public class SolicitudDocumentacionPrevia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "seq_soldocprevia", sequenceName = "seq_soldocprevia", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_soldocprevia")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "asunto")
	private String asunto;

	@ManyToOne
	@JoinColumn(name = "id_inspeccion")
	private Inspeccion inspeccion;

	@Column(name = "destinatario")
	private String destinatario;

	@Column(name = "correoDestinatario")
	private String correoDestinatario;

	@Column(name = "cuatrimestre")
	@Enumerated(EnumType.STRING)
	private CuatrimestreEnum cuatrimestre;

	@Column(name = "anio")
	private Integer anio;

	@Column(name = "fechaLimiteEnvio")
	private Date fechaLimiteEnvio;

	@Column(name = "fechaLimiteCumplimentar")
	private Date fechaLimiteCumplimentar;

	@Column(name = "descargaPlantillas")
	private String descargaPlantillas;

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

	@Column(name = "fechaValidApoyo")
	private Date fechaValidApoyo;

	@Column(name = "usernameValidApoyo")
	private String usernameValidApoyo;

	@Column(name = "fechaValidJefeEquipo")
	private Date fechaValidJefeEquipo;

	@Column(name = "usernameValidJefeEquipo")
	private String usernameValidJefeEquipo;

	@Column(name = "fecha_envio")
	private Date fechaEnvio;

	@Column(name = "usernameEnvio")
	private String usernameEnvio;

	@Column(name = "nombreCompletoInterlocutor")
	private String nombreCompletoInterlocutor;

	@Column(name = "categoriaInterlocutor")
	private String categoriaInterlocutor;

	@Column(name = "cargoInterlocutor")
	private String cargoInterlocutor;

	@Column(name = "correoCorporativoInter")
	private String correoCorporativoInterlocutor;

	@Column(name = "correoCorporativoInterCompl")
	private String correoCorporativoInterlocutorCompl;

	@Column(name = "telefonoInterlocutor")
	private String telefonoInterlocutor;

	@Column(name = "fechaFinalizacion")
	private Date fechaFinalizacion;

	@Column(name = "usuarioFinalizacion")
	private String usuarioFinalizacion;

	@Column(name = "fechaCumplimentacion")
	private Date fechaCumplimentacion;

	@Column(name = "fechaNoConforme")
	private Date fechaNoConforme;

	@Column(name = "usuarioNoConforme")
	private String usuarioNoConforme;
}
