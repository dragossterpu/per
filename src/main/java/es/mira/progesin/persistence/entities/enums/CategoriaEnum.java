package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CategoriaEnum {
	A1,
	A2,
	B,
	C1,
	C2,
	D,
	E,
	LABORALES,
	OTROS;
	
	private String descripcion;
}
