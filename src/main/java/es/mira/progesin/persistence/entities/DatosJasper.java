package es.mira.progesin.persistence.entities;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosJasper {

	private String asunto;

	private String numeroReferencia;

	private String destinatario;

	private String tipoInspeccion;

	private String identificadorTrimestre;

	private String nombre;

	private String url;

	private String correoApoyo;

	private Date fechaAntes;

	private String nombreInspector;

	private String telefonoInspector;

	private String correoInspector;

	private String puestoInspector;

	private Date fechaCumplimentar;

	private String fechaEmision;

	/**
	 * Resetea los valores del formulario de búsqueda de usuarios
	 */
	public void resetValues() {
		this.asunto = null;
		this.numeroReferencia = null;
		this.destinatario = null;
		this.nombre = null;
		this.tipoInspeccion = null;
		this.identificadorTrimestre = null;
		this.url = null;
		this.correoApoyo = null;
		this.fechaAntes = null;
		this.puestoInspector = null;
		this.nombreInspector = null;
		this.telefonoInspector = null;
		this.correoInspector = null;
		this.fechaCumplimentar = null;
	}
}
