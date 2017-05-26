package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public enum RolEquipoEnum {
    JEFE_EQUIPO("Jefe de equipo"), MIEMBRO("Componente"), COLABORADOR("Colaborador");
    
    private String descripcion;
}
