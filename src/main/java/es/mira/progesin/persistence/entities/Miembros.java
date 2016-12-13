package es.mira.progesin.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
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

	@Column(name = "ID_EQUIPO")
	private Long idEquipo;

	@Column(name = "username")
	private String username;

	@Column(name = "nombre_completo")
	private String nombreCompleto;

	@Column(name = "posicion")
	@Enumerated(EnumType.STRING)
	private RolEquipoEnum posicion;

}
