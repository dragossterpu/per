package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SeccionesEnum {
    ADMINISTRACION("ADMINISTRACIÓN"), DOCUMENTACION("SOLICITUD DOC. PREVIA"), CUESTIONARIO("CUESTIONARIOS"), GESTOR(
            "GESTOR DOCUMENTAL"), GUIAS(
                    "GUÍAS"), INSPECCION("INSPECCIONES"), INFORMES("INFORMES"), LOGIN("LOGIN"), USUARIOS("USUARIOS");
    
    private String descripcion;
    
}
