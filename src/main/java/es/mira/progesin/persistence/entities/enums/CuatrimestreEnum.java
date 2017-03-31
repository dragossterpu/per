package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CuatrimestreEnum {
    PRIMERO("Primer cuatrimestre"), SEGUNDO("Segundo cuatrimestre"), TERCERO("Tercer cuatrimestre");
    
    private String descripcion;
}
