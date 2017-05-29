package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enumerado con los posibles ámbitos de una inspección.
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AmbitoInspeccionEnum {
    /**
     * Policía nacional.
     */
    PN("Policía Nacional"),
    /**
     * Guardia civil.
     */
    GC("Guardia Civil"),
    /**
     * Otros ámbitos.
     */
    OTROS("Otros");
    /**
     * Descripción del ámbito.
     */
    String descripcion;
}
