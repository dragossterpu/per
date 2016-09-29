package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UserBusqueda implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Date fechaDesde;
	private Date fechaHasta;
	private String username;
	private String nombre;
	private String apellido1;
	private	RoleEnum role;
	private EstadoEnum estado;
	private CuerpoEstado cuerpoEstado;
	private PuestoTrabajo puestoTrabajo;
	
	
	private List<User> listaUsuarios;
	
	/**
	 * Resetea los valores del formulario de búsqueda de usuarios
	 */
	public void resetValues(){
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.username = null;
		this.nombre = null;
		this.apellido1 = null;
		this.role = null;
		this.cuerpoEstado = null;
		this.puestoTrabajo = null;
		this.listaUsuarios = null;
		this.estado = null;
	}
	
}
