package es.mira.progesin.persistence.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "MIEMBROS", schema = "public")

public class Miembros {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_MIEMBROS", length = 5)
	private Integer idMiembros;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_EQUIPO")
	private Equipo equipo;

	@Column(name = "equipoEspecial", length = 2)
	private String equipoEspecial;

	@Column(name = "fecha_alta", nullable = false)
	private Date fechaAlta;

	@Column(name = "fecha_baja")
	private Date fechaBaja;

	@Column(name = "username_alta", length = 12)
	private String usernameAlta;

	@Column(name = "username_baja", length = 12)
	private String usernameBaja;

}
