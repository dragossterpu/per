package es.mira.progesin.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "MIEMBROS")

public class Miembros {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	private Long id;

	// TODO Esto en verdad es el id del equipo, cambiar y refractorizar
	@Column(name = "ID_MIEMBROS")
	private Long idMiembros;

	@Column(name = "username")
	private String username;

	@Column(name = "nombre_completo")
	private String nombreCompleto;

	@Column(name = "posicion")
	private String posicion;

}
