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
    SIN_INICIAR("0. Sin iniciar"), COMUNICACION_REALIZACION(
            "1. Comunicación realización inspección"), PEND_SOLICITAR_DOC_PREVIA(
                    "2. Pendiente solicitar documentación previa"), PEND_RECIBIR_DOC_PREVIA(
                            "3. Pendiente recibir documentación previa"), PEND_ENVIAR_CUESTIONARIO(
                                    "4. Pendiente enviar cuestionario"), PEND_RECIBIR_CUESTIONARIO(
                                            "5. Pendiente recibir cuestionario"), PENDIENTE_VISITA_INSPECCION(
                                                    "6. Pediente visita inspección"), VISITA(
                                                            "7. Visita"), ELABORACION_INFORME(
                                                                    "8. Elaboración informe"), INFORME_REALIZADO(
                                                                            "9. Informe finalizado. Pendiente de firma"), REMISION_SES(
                                                                                    "10. Remisión SES"), REMISION_UNIDADES(
                                                                                            "11. Remisión a unidades"), FINALIZADA(
                                                                                                    "12. Finalizada"), APLAZADA(
                                                                                                            "13. Aplazada"), SUSPENDIDA(
                                                                                                                    "14. Suspendida");
    
    String descripcion;
}
