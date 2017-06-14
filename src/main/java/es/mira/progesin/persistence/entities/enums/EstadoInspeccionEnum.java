package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enumerado con los diferentes tipos de estado en que se puede encontrar una inspeccion. La primera letra de cada capo
 * se utiliza para realizar la ordenación alfabeticamente.
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
    A_SIN_INICIAR("0. Sin iniciar"),
    
    /**
     * Estado en el que se encuentra una inspección desde el momento en el que se comunica su realización.
     */
    B_COMUNICACION_REALIZACION("1. Comunicación realización inspección"),
    
    /**
     * Estado en el que se encuentra una inspección cuando aún no se ha solicitado documentación previa.
     */
    C_PEND_SOLICITAR_DOC_PREVIA("2. Pendiente solicitar documentación previa"),
    
    /**
     * Estado en el que se encuentra una inspección cuando aún no se ha solicitado documentación previa.
     */
    D_PEND_RECIBIR_DOC_PREVIA("3. Pendiente recibir documentación previa"),
    
    /**
     * Estado en el que se encuentra una inspección cuando se ha solicitado la rspuesta de ningún cuestionario.
     */
    E_PEND_ENVIAR_CUESTIONARIO("4. Pendiente enviar cuestionario"),
    
    /**
     * Estado en el que se encuentra una inspección cuando se ha enviado un cuestionario pero aún no ha sido respondido
     * por el receptor.
     */
    F_PEND_RECIBIR_CUESTIONARIO("5. Pendiente recibir cuestionario"),
    
    /**
     * Estado en el que se encuentra una inspección cuando ya se dispone de la documentación y de los cuestionarios
     * requeridos.
     * 
     */
    G_PENDIENTE_VISITA_INSPECCION("6. Pendiente visita inspección"),
    
    /**
     * Estado en el que se encuentra una inspección cuando ya se está realizando la visita.
     * 
     */
    H_VISITA("7. Visita"),
    
    /**
     * Estado en el que se encuentra una inspección cuando se está realizando el informe de la instalación visitada.
     * 
     */
    I_ELABORACION_INFORME("8. Elaboración informe"),
    
    /**
     * Estado en el que se encuentra una inspección cuando se está realizando el informe de la instalación visitada.
     * 
     */
    J_INFORME_REALIZADO("9. Informe finalizado. Pendiente de firma"),
    
    /**
     * Estado en el que se encuentra una inspección cuando se ha realizado en informe pe aún está pendiente de firmarse.
     * 
     */
    K_REMISION_SES("10. Remisión SES"),
    
    /**
     * Remisión SES.
     */
    L_REMISION_UNIDADES("11. Remisión a unidades"),
    
    /**
     * Estado en el que se encuentra una inspección cuando ha finalizado.
     * 
     */
    M_FINALIZADA("12. Finalizada"),
    
    /**
     * Estado en el que se encuentra una inspección cuando se aplaza en cualquier estado en que se encuentre.
     * 
     */
    N_APLAZADA("13. Aplazada"),
    
    /**
     * Estado en el que se encuentra una inspección cuando se suspende.
     * 
     */
    O_SUSPENDIDA("14. Suspendida");
    
    /**
     * Variable que almacena el valor del estado.
     */
    String descripcion;
    
}
