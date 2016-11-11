package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CuestionarioPersonalizadoBusqueda implements Serializable {
	private static final long serialVersionUID = 1L;

	private ModeloCuestionario modeloCuestionarioSeleccionado;

	private Date fechaDesde;

	private Date fechaHasta;

	private String username;

	private String nombreCuestionario;

	public void limpiar() {
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.username = null;
		this.nombreCuestionario = null;
		this.modeloCuestionarioSeleccionado = null;
	}

}
