package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;

import es.mira.progesin.persistence.entities.ModeloCuestionario;
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
		setFechaDesde(null);
		setFechaHasta(null);
		setUsername(null);
		setNombreCuestionario(null);
	}

}
