package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enumerado con los diferentes tipos de estado en que se puede encontrar una inspeccion.
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EstadoInspeccionEnum {
    /**
     * Estado en el que se encuentra una inspección desde el momento en el que se crea.
     */
    SIN_INICIAR("0. Sin iniciar"),
    /**
     * Estado en el que se encuentra una inspección desde el momento en el que se comunica su realización.
     */
    COMUNICACION_REALIZACION("1. Comunicación realización inspección"),
    /**
     * Estado en el que se encuentra una inspección cuando aún no se ha solicitado documentación previa.
     */
    PEND_SOLICITAR_DOC_PREVIA("2. Pendiente solicitar documentación previa"),
    /**
     * Estado en el que se encuentra una inspección cuando se ha solicitado documentación previa pero aún no se ha
     * obtenido respuesta.
     */
    PEND_RECIBIR_DOC_PREVIA("3. Pendiente recibir documentación previa"),
    /**
     * Estado en el que se encuentra una inspección cuando se ha solicitado la rspuesta de ningún cuestionario.
     */
    PEND_ENVIAR_CUESTIONARIO("4. Pendiente enviar cuestionario"),
    /**
     * Estado en el que se encuentra una inspección cuando se ha enviado un cuestionario pero aún no ha sido respondido
     * por el receptor.
     */
    PEND_RECIBIR_CUESTIONARIO("5. Pendiente recibir cuestionario"),
    /**
     * Estado en el que se encuentra una inspección cuando ya se dispone de la documentación y de los cuestionarios
     * requeridos.
     * 
     */
    PENDIENTE_VISITA_INSPECCION("6. Pediente visita inspección"),
    /**
     * Estado en el que se encuentra una inspección cuando ya se está realizando la visita.
     * 
     */
    VISITA("7. Visita"),
    /**
     * Estado en el que se encuentra una inspección cuando se está realizando el informe de la instalación visitada.
     * 
     */
    ELABORACION_INFORME("8. Elaboración informe"),
    /**
     * Estado en el que se encuentra una inspección cuando se está realizando el informe de la instalación visitada.
     * 
     */
    INFORME_REALIZADO("9. Informe finalizado. Pendiente de firma"),
    /**
     * Estado en el que se encuentra una inspección cuando se ha realizado en informe pe aún está pendiente de firmarse.
     * 
     */
    REMISION_SES("10. Remisión SES"),
    /**
     * Remisión SES.
     */
    REMISION_UNIDADES("11. Remisión a unidades"),
    /**
     * Estado en el que se encuentra una inspección cuando ha finalizado.
     * 
     */
    FINALIZADA("12. Finalizada"),
    /**
     * Estado en el que se encuentra una inspección cuando se aplaza en cualquier estado en que se encuentre.
     * 
     */
    APLAZADA("13. Aplazada"),
    /**
     * Estado en el que se encuentra una inspección cuando se suspende.
     * 
     */
    SUSPENDIDA("14. Suspendida");
    /**
     * Variable que almacena el valor del estado.
     */
    String descripcion;
}
