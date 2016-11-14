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
@Table(name = "MIEMBROS", schema = "public")

public class Miembros {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", length = 5)
	private Integer id;

	@Column(name = "ID_MIEMBROS", length = 5)
	private Integer idMiembros; // Esto en verdad es el id del equipo

	@Column(name = "username")
	private String username;

	@Column(name = "nombre_completo")
	private String nombreCompleto;

	@Column(name = "posicion")
	private String posicion;

}
