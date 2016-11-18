package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CuatrimestreEnum {
	PRIMERO("primer cuatrimestre"), SEGUNDO("segundo cuatrimestre"), TERCERO("tercer cuatrimestre"), CUARTO(
			"cuarto cuatrimestre");

	private String descripcion;
}
