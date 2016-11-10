package es.mira.progesin.persistence.entities.enums;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum RoleEnum {
	ADMIN("Administrador", false), JEFE_INSPECCIONES("Jefe de inspecciones", false), EQUIPO_INSPECCIONES(
			"Equipo de inspecciones", false), SERVICIO_APOYO("Servicio de apoyo", false), GABINETE(
					"Gabinete de estudios y análisis", false), PROV_SOLICITUD("Provisional vista documentación previa",
							true), PROV_CUESTIONARIO("Provisional vista cuestionario", true);

	private String descripcion;

	private boolean prov;

	public static List<RoleEnum> getRolesProv() {
		List<RoleEnum> rolesProv = new ArrayList<>();
		for (RoleEnum rol : RoleEnum.values()) {
			if (rol.isProv())
				rolesProv.add(rol);
		}
		return rolesProv;
	}
}
