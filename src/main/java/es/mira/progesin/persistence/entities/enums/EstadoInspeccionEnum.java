package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EstadoInspeccionEnum {
    ESTADO_SIN_INICIAR("Sin iniciar"), ESTADO_FASE_PREVIA("Fase previa"), ESTADO_PENDIENTE_CUESTIONARIO(
            "Pendiente recibir cuestionario"), ESTADO_PENDIENTE_INFORME(
                    "Pediente informe"), ESTADO_FINALIZADA("Finalizada"), ESTADO_SUSPENDIDA("Suspendida");
    
    String descripcion;
}
