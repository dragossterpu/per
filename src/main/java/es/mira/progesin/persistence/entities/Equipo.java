package es.mira.progesin.persistence.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "EQUIPO", schema = "public")

public class Equipo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_EQUIPO", length = 5)
	private Integer idEquipo;

	@Column(name = "nombreEquipo", length = 100, nullable = false)
	private String nombreEquipo;

	@OneToOne
	@JoinColumn(name = "username")
	private User jefeEquipo;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "equipo_miembros", joinColumns = { @JoinColumn(name = "ID_EQUIPO") }, inverseJoinColumns = {
			@JoinColumn(name = "ID_MIEMBROS") })
	private List<Miembros> miembros = new ArrayList<Miembros>();

	// @OneToMany(cascade = CascadeType.ALL)
	// @JoinTable(name = "usuarios_miembros", joinColumns = { @JoinColumn(name = "ID_EQUIPO") }, inverseJoinColumns = {
	// @JoinColumn(name = "USERNAME") })
	// private List<User> miembros = new ArrayList<User>();

	// @OneToMany(cascade = CascadeType.ALL)
	// @JoinTable(name = "usuarios_colaboradores", joinColumns = {
	// @JoinColumn(name = "ID_EQUIPO") }, inverseJoinColumns = { @JoinColumn(name = "USERNAME") })
	// private List<User> colaboradores = new ArrayList<User>();

	@Column(name = "equipoEspecial", length = 2)
	private String equipoEspecial;

	@Column(name = "fecha_alta", nullable = false)
	private Date fechaAlta;

	@Column(name = "fecha_baja")
	private Date fechaBaja;

	@Column(name = "username_alta", length = 12, nullable = false)
	private String usernameAlta;

	@Column(name = "username_baja", length = 12)
	private String usernameBaja;

}
