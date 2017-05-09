package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enum con los cuatrimestres de un año
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("javadoc")
public enum CuatrimestreEnum {
    PRIMERO("Primer cuatrimestre"), SEGUNDO("Segundo cuatrimestre"), TERCERO("Tercer cuatrimestre");
    
    private String descripcion;
}
