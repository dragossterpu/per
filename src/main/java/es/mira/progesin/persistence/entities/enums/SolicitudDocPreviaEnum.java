package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enumerado para los diferentes estados por los que pasa una solicitud de documentación previa.
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SolicitudDocPreviaEnum {
    /**
     * Solicitud creada.
     */
    CREADA("Creada"),
    /**
     * Solicitud validada por el grupo de apoyo.
     */
    VALIDADA_APOYO("Validada por apoyo"),
    /**
     * Solicitud validada por el jefe de equipo.
     */
    VALIDADA_JEFE_EQUIPO("Validada por jefe equipo"),
    /**
     * Solicitud enviada al remitente.
     */
    ENVIADA("Enviada"),
    /**
     * Solicitud cumplimentada por el remitente.
     */
    CUMPLIMENTADA("Cumplimentada"),
    /**
     * Solicitud finalizada y validada por todos.
     */
    FINALIZADA("Finalizada"),
    /**
     * Solicitud no conforme con la documentación aportada por el remitente.
     */
    NO_CONFORME("No conforme"),
    /**
     * Solicitud anulada.
     */
    ANULADA("Anulada");
    /**
     * Descripción de los estados de solicitud de documentación previa.
     */
    private String descripcion;
}
