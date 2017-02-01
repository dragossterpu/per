package es.mira.progesin.persistence.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public enum ClaseUsuarioEnum {
		FCSE("FCSE"),
		FCSEFC("FCSE-FC"),
		RPT("RPT"),
		RPTFC("RPT-FC");
		
	
	private String descripcion;
}
