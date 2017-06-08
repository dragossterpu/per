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
     * Estado en el que se encuentra una inspección cuando se aplaza en cualquier estado en que se encuentre.
     * 
     */
    APLAZADA("Aplazada"),
    /**
     * Estado en el que se encuentra una inspección desde el momento en el que se comunica su realización.
     */
    COMUNICACION_REALIZACION("Comunicación realización inspección"),
    /**
     * Estado en el que se encuentra una inspección cuando se está realizando el informe de la instalación visitada.
     * 
     */
    ELABORACION_INFORME("Elaboración informe"),
    /**
     * Estado en el que se encuentra una inspección cuando ha finalizado.
     * 
     */
    FINALIZADA("Finalizada"),
    /**
     * Estado en el que se encuentra una inspección cuando se está realizando el informe de la instalación visitada.
     * 
     */
    INFORME_REALIZADO("Informe finalizado. Pendiente de firma"),
    /**
     * Estado en el que se encuentra una inspección cuando se ha solicitado la rspuesta de ningún cuestionario.
     */
    PEND_ENVIAR_CUESTIONARIO("Pendiente enviar cuestionario"),
    /**
     * Estado en el que se encuentra una inspección cuando se ha enviado un cuestionario pero aún no ha sido respondido
     * por el receptor.
     */
    PEND_RECIBIR_CUESTIONARIO("Pendiente recibir cuestionario"),
    /**
     * Estado en el que se encuentra una inspección cuando se ha solicitado documentación previa pero aún no se ha
     * obtenido respuesta.
     */
    PEND_RECIBIR_DOC_PREVIA("Pendiente recibir documentación previa"),
    /**
     * Estado en el que se encuentra una inspección cuando aún no se ha solicitado documentación previa.
     */
    PEND_SOLICITAR_DOC_PREVIA("Pendiente solicitar documentación previa"),
    /**
     * Estado en el que se encuentra una inspección cuando ya se dispone de la documentación y de los cuestionarios
     * requeridos.
     * 
     */
    PENDIENTE_VISITA_INSPECCION("Pendiente visita inspección"),
    /**
     * Remisión SES.
     */
    REMISION_UNIDADES("Remisión a unidades"),
    /**
     * Estado en el que se encuentra una inspección cuando se ha realizado en informe pe aún está pendiente de firmarse.
     * 
     */
    REMISION_SES("Remisión SES"),
    
    /**
     * Estado en el que se encuentra una inspección desde el momento en el que se crea.
     */
    SIN_INICIAR("Sin iniciar"),
    /**
     * Estado en el que se encuentra una inspección cuando se suspende.
     * 
     */
    SUSPENDIDA("Suspendida"),
    /**
     * Estado en el que se encuentra una inspección cuando ya se está realizando la visita.
     * 
     */
    VISITA("Visita");
    
    /**
     * Variable que almacena el valor del estado.
     */
    String descripcion;
    
    /**
     * Sobreescritura del método para porder realizar la ordenación de listas.
     */
    
}
