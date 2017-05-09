package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enum con los posibles ámbitos de una inspección
 * 
 * @author Ezentis
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("javadoc")
public enum AmbitoInspeccionEnum {
    PN("Policía Nacional"), GC("Guardia Civil"), OTROS("Otros");
    
    String descripcion;
}
