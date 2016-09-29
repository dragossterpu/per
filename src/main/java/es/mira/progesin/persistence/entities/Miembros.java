package es.mira.progesin.persistence.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "MIEMBROS", schema = "public")

public class Miembros {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_MIEMBROS", length = 5)
	private Integer idMiembros;

	@OneToOne(mappedBy = "jefeEquipo", fetch = FetchType.LAZY)
	private Miembros miembros;

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
