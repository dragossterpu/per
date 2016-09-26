package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.services.ICuerpoEstadoService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.jsf.scope.FacesViewScope;
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
	private CuerpoEstado cuerpoEstado;
	private PuestoTrabajo puestoTrabajo;
	
	private List<User> listaUsuarios;
	
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
	}
	
}
