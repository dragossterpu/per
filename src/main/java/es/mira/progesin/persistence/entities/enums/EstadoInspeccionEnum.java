package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EstadoInspeccionEnum {
    ESTADO_0("Sin iniciar"), ESTADO_1("Fase previa"), ESTADO_2("Pendiente recibir cuestionario"), ESTADO_3(
            "Pediente informe"), ESTADO_4("Finalizada"), ESTADO_5("Suspendida");
    
    String descripcion;
}
