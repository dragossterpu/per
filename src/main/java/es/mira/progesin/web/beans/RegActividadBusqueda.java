package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.mira.progesin.persistence.entities.RegistroActividad;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegActividadBusqueda implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date fechaDesde;

	private Date fechaHasta;
	
	private String nombreSeccion;
	
	private String usernameRegActividad;
	
	private String tipoRegActividad;
	
	private List<RegistroActividad> listaRegActividad;

	
	public void resetValues() {
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.nombreSeccion = null;
		this.usernameRegActividad = null;
		this.tipoRegActividad = null;
		this.listaRegActividad = null;
	}

}
