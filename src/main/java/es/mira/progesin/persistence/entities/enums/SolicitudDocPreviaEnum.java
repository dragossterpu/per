package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SolicitudDocPreviaEnum {
	CREADA("Creada"), VALIDADA_APOYO("Validada por apoyo"), VALIDADA_JEFE_EQUIPO("Validada por jefe equipo"), ENVIADA(
			"Enviada"), CUMPLIMENTADA(
					"Cumplimentada"), FINALIZADA("Finalizada"), NO_CONFORME("No conforme"), ANULADA("Anulada");
	private String descripcion;
}
