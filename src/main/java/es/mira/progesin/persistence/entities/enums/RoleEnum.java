package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum RoleEnum {
	ADMIN("Administrador"), JEFE_INSPECCIONES("Jefe de inspecciones"), EQUIPO_INSPECCIONES(
			"Equipo de inspecciones"), SERVICIO_APOYO("Servicio de apoyo"), GABINETE(
					"Gabinete de estudios y análisis"), PROV_SOLICITUD(
							"Provisional vista documentación previa"), PROV_CUESTIONARIO(
									"Provisional vista cuestionario");

	private String descripcion;
}
