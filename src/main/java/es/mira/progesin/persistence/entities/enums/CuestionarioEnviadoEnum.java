package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CuestionarioEnviadoEnum {
	ENVIADO("Enviado"), CUMPLIMENTADO("Cumplimentado"), FINALIZADO("Finalizado"), NO_CONFORME("No conforme"), ANULADO(
			"Anulado");
	private String descripcion;
}
