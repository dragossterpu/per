package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AmbitoInspeccionEnum {
	PN("Policía Nacional"), GC("Guardia Civil"), OTROS("Otros");

	String descripcion;
}
