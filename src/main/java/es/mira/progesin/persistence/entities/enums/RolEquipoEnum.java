package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public enum RolEquipoEnum {
	JEFE("Jefe de equipo"), MIEMBRO("Miembro"), COLABORADOR("Colaborador");

	private String descripcion;
}
