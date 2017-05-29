package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enumerado con las categorías de un usuario de IPSS.
 * 
 * @author EZENTIS
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CategoriaEnum {
    /**
     * Funcionarios del grupo A1.
     */
    A1,
    /**
     * Funcionarios del grupo A2.
     */
    A2,
    /**
     * Funcionarios del grupo B.
     */
    B,
    /**
     * Funcionarios del grupo C1.
     */
    C1,
    /**
     * Funcionarios del grupo C2.
     */
    C2,
    /**
     * Funcionarios del grupo D.
     */
    D,
    /**
     * Funcionarios del grupo E.
     */
    E,
    /**
     * Personal laboral.
     */
    LABORALES,
    /**
     * Otro tipo de personal.
     */
    OTROS;
    /**
     * Descripción para la categoría.
     */
    private String descripcion;
}
