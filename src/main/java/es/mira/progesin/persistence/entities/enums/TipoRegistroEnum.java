package es.mira.progesin.persistence.entities.enums;

/**
 * 
 * Enumeración con los posibles valores del registro de actividad.
 * 
 */

public enum TipoRegistroEnum {
    /**
     * El registro corresponde a un alta.
     */
    ALTA,
    /**
     * El registro corresponde a una modificación.
     */
    MODIFICACION,
    /**
     * El registro corresponde a un baja.
     */
    BAJA,
    /**
     * El registro corresponde a un error.
     */
    ERROR,
    /**
     * El registro corresponde a una auditoría.
     */
    AUDITORIA
}
