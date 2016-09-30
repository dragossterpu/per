package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
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
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "USERS", schema = "public")
// @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "username", length = 15)
	protected String username;

	@OneToOne(mappedBy = "jefeEquipo", fetch = FetchType.LAZY)
	private Equipo equipo;

	@Column(name = "password", length = 100, nullable = false)
	protected String password;

	@Column(name = "estado", length = 8, nullable = false)
	@Enumerated(EnumType.STRING)
	protected EstadoEnum estado;

	@Column(name = "nombre", length = 50, nullable = false)
	protected String nombre;

	@Column(name = "prim_apellido", length = 50, nullable = false)
	protected String apellido1;

	@Column(name = "segundo_apellido", length = 50)
	protected String apellido2;

	@Column(name = "doc_identidad", length = 12, nullable = false)
	protected String docIndentidad;

	@Column(name = "telefono", length = 12)
	protected String telefono;

	@Column(name = "correo", length = 50, nullable = false)
	protected String correo;

	@Column(name = "role", length = 10, nullable = false)
	protected RoleEnum role;

	@Column(name = "num_identificacion", length = 15, nullable = false)
	protected String numIdentificacion;

	@Column(name = "envio_notif", length = 2, nullable = false)
	protected String envioNotificacion;

	@ManyToOne
	@JoinColumn(name = "ID_CUERPO", nullable = false)
	private CuerpoEstado cuerpoEstado;

	@ManyToOne
	@JoinColumn(name = "ID_PUESTO", nullable = false)
	private PuestoTrabajo puestoTrabajo;

	@Column(name = "nivel", nullable = false)
	private Integer nivel;

	@CreatedDate
	@Column(name = "fecha_alta", nullable = false)
	protected Date fechaAlta;

	@Column(name = "fecha_destino_ipss", nullable = false)
	protected Date fechaDestinoIPSS;

	@Column(name = "fecha_baja")
	protected Date fechaBaja;

	@LastModifiedDate
	@Column(name = "fecha_modificacion")
	protected Date fechaModificacion;

	@Column(name = "fecha_inactivo")
	protected Date fechaInactivo;

	@CreatedBy
	@Column(name = "username_alta", length = 12, nullable = false)
	protected String usernameAlta;

	@LastModifiedBy
	@Column(name = "username_modif", length = 12)
	protected String usernameModif;

	@Column(name = "username_baja", length = 12)
	protected String usernameBaja;

}
