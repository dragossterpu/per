package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enumerado para los posibles estados de un cuestionario que ha sido enviado.
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("javadoc")
public enum CuestionarioEnviadoEnum {
    /**
     * Cuestionario enviado.
     */
    ENVIADO("Enviado"),
    /**
     * Cuestionario cumplimentado por el remitente.
     */
    CUMPLIMENTADO("Cumplimentado"),
    /**
     * Cuestionario cumplimentado, finalizado y por lo tanto validado por todos.
     */
    FINALIZADO("Finalizado"),
    /**
     * Cuestionario cumplimentado pero no validado por todos.
     */
    NO_CONFORME("No conforme"),
    /**
     * Cuestionario anulado.
     */
    ANULADO("Anulado");
    /**
     * Descripción del estado del cuestionario enviado.
     */
    private String descripcion;
}
