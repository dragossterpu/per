package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enumerado con los cuatrimestres de un año.
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CuatrimestreEnum {
    /**
     * Primer cuatrimestre.
     */
    PRIMERO("Primer cuatrimestre"),
    /**
     * Segundo cuatrimestre.
     */
    SEGUNDO("Segundo cuatrimestre"),
    /**
     * Tercer cuatrimestre.
     */
    TERCERO("Tercer cuatrimestre");
    /**
     * Descripción de cuatrimestre.
     */
    private String descripcion;
}
