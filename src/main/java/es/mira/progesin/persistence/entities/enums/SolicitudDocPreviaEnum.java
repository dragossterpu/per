package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SolicitudDocPreviaEnum {
	CREADA("Creada"), VALIDADA_APOYO("Validada por apoyo"), ENVIADA("Enviada"), CUMPLIMENTADA(
			"Cumplimentada"), FINALIZADA("Finalizada");
	private String descripcion;
}
