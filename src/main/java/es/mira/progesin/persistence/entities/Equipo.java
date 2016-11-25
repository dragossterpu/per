package es.mira.progesin.persistence.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

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
@Table(name = "EQUIPO")

public class Equipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_EQUIPO")
	private Long idEquipo;

	@Column(name = "nombreEquipo", length = 100, nullable = false)
	private String nombreEquipo;

	@Column(name = "tipoEquipo", length = 100, nullable = false)
	private String tipoEquipo;

	@Column(name = "jefeEquipo", length = 100, nullable = false)
	private String jefeEquipo;

	@Column(name = "equipoEspecial", length = 2)
	private String equipoEspecial;

	@CreatedDate
	@Column(name = "fecha_alta", nullable = false)
	private Date fechaAlta;

	@LastModifiedDate
	@Column(name = "fecha_baja")
	private Date fechaBaja;

	@CreatedBy
	@Column(name = "username_alta", length = 12, nullable = false)
	private String usernameAlta;

	@LastModifiedBy
	@Column(name = "username_baja", length = 12)
	private String usernameBaja;

	@Column(name = "nombreJefe", length = 150)
	private String nombreJefe;

}
