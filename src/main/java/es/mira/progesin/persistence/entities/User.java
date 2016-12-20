package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "USERS")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PROVISIONAL = "provisional";

	@Id
	@Column(name = "username")
	protected String username;

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

	@Column(name = "tfno_movil_oficial", length = 12)
	protected String telefonoMovilOficial;
	
	@Column(name = "tfno_movil_particular", length = 12)
	protected String telefonoMovilParticular;
	
	@Column(name = "correo", length = 50, nullable = false)
	protected String correo;

	@Column(name = "role", length = 20, nullable = false)
	protected RoleEnum role;
	
	@Column(name="despacho", length = 20)
	protected String despacho;

	@Column(name = "num_identificacion", length = 15, nullable = false)
	protected String numIdentificacion;

	@ManyToOne
	@JoinColumn(name = "ID_CUERPO")
	private CuerpoEstado cuerpoEstado;
	
	@ManyToOne
	@JoinColumn(name="ID_EMPLEO")
	protected Empleo empleo;

	@ManyToOne
	@JoinColumn(name = "ID_PUESTO")
	private PuestoTrabajo puestoTrabajo;
	
	@ManyToOne
	@JoinColumn(name="ID_DEPARTAMENTO")
	protected Departamento departamento;

	@Column(name = "nivel")
	private Integer nivel;
	
	@Column(name="categoria", length = 20)
	protected String categoria;
	

	@CreatedDate
	@Column(name = "fecha_alta", nullable = false)
	protected Date fechaAlta;

	@Column(name = "fecha_destino_ipss")
	protected Date fechaDestinoIPSS;
	
	@Column(name = "fecha_ingreso")
	protected Date fechaIngreso;

	@Column(name = "fecha_baja")
	protected Date fechaBaja;

	@LastModifiedDate
	@Column(name = "fecha_modificacion")
	protected Date fechaModificacion;

	@Column(name = "fecha_inactivo")
	protected Date fechaInactivo;

	@CreatedBy
	@Column(name = "username_alta", nullable = false)
	protected String usernameAlta;

	@LastModifiedBy
	@Column(name = "username_modif")
	protected String usernameModif;

	@Column(name = "username_baja")
	protected String usernameBaja;


	public User(String username, String password, RoleEnum role) {
		this.setUsername(username);
		this.setPassword(password);
		this.setRole(role);
		this.setEstado(EstadoEnum.ACTIVO);
		this.setNombre(PROVISIONAL);
		this.setApellido1(PROVISIONAL);
		this.setDocIndentidad(PROVISIONAL);
		this.setCorreo(username);
		this.setNumIdentificacion(PROVISIONAL);
		this.setFechaDestinoIPSS(new Date());
		this.setNivel(0);
		this.setFechaAlta(new Date());
	}
}
