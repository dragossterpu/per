package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum RoleEnum {
	ADMIN("ADMINISTRADOR"), JEFE_INSPECCIONES("JEFE DE INSPECCIONES"), EQUIPO_INSPECCIONES(
			"EQUIPO DE INSPECCIONES"), SERVICIO_APOYO("SERVICIO DE APOYO"), GABINETE(
					"GABINETE DE ESTUDIOS Y ANÁLISIS"), PROV_SOLICITUD(
							"PROVISIONAL VISTA SOLICITUD"), PROV_CUESTIONARIO("PROVISIONAL VISTA CUESTIONARIO");

	private String descripcion;
}
