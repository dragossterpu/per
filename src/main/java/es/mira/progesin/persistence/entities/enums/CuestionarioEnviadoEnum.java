package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Posibles estados de un cuestionario que ha sido enviado
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("javadoc")
public enum CuestionarioEnviadoEnum {
    ENVIADO("Enviado"), CUMPLIMENTADO("Cumplimentado"), FINALIZADO("Finalizado"), NO_CONFORME("No conforme"), ANULADO(
            "Anulado");
    private String descripcion;
}
