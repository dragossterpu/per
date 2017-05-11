package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enum con los estados posibles de una inspeccion
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("javadoc")
public enum EstadoInspeccionEnum {
    ESTADO_SIN_INICIAR("Sin iniciar"), ESTADO_FASE_PREVIA("Fase previa"), ESTADO_PENDIENTE_CUESTIONARIO(
            "Pendiente recibir cuestionario"), ESTADO_PENDIENTE_INFORME("Pediente informe"), ESTADO_FINALIZADA(
                    "Finalizada"), ESTADO_SUSPENDIDA("Suspendida"), ESTADO_APLAZADA("Aplazada");
    
    String descripcion;
}
