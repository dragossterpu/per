package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EquipoBusqueda implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date fechaDesde;

	private Date fechaHasta;

	private String nombreEquipo;

	private String nombreJefe;

	private String nombreMiembro;

	private User jefeEquipo;

	private List<User> miembros;

	private List<Equipo> listaEquipos;

	private String estado;

	public void resetValues() {
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.nombreEquipo = null;
		this.jefeEquipo = null;
		this.miembros = null;
		this.estado = null;
		this.nombreJefe = null;
		this.nombreMiembro = null;
		this.listaEquipos = null;
	}

}
