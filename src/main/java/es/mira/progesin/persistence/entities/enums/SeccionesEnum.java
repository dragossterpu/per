package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SeccionesEnum {
	ADMINISTRATIVAS("Herramientas Administrativas"),
	DOCUMENTACION("Solicitud previa cuestionario"),
	CUESTIONARIO("Cuestionario"),
	GESTOR("Gestor Documental"),
	GUIAS("Guías"),
	INSPECCION("Inspección"),
	INFORMES("Informe"),
	ALERTAS("Alertas"),
	NOTIFICACIONES("Notificaciones"),
	MENSAJES("Mensajes");
	
	private String descripcion;
	

}
