package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SolicitudDocPreviaEnum;
import es.mira.progesin.services.IUserService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("solicitudDocPreviaBusqueda")
@SessionScoped
public class SolicitudDocPreviaBusqueda implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date fechaDesde;

	private Date fechaHasta;

	private SolicitudDocPreviaEnum estado;

	private User usuarioCreacion;

	private String numeroInspeccion;

	private TipoInspeccion tipoInspeccion;

	private AmbitoInspeccionEnum ambitoInspeccion;

	private String nombreUnidad;

	@Autowired
	private transient IUserService userService;

	private List<User> listaUsuarios;

	private List<TipoInspeccion> listaTiposInspeccion;

	@PersistenceContext
	private transient EntityManager em;

	@PostConstruct
	public void init() {
		listaUsuarios = userService.findByfechaBajaIsNullAndRoleNotIn(RoleEnum.getRolesProv());
		TypedQuery<TipoInspeccion> query = em.createNamedQuery("TipoInspeccion.findAll", TipoInspeccion.class);
		listaTiposInspeccion = query.getResultList();
	}

	public void resetValues() {
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.estado = null;
		this.usuarioCreacion = null;
		this.numeroInspeccion = null;
		this.tipoInspeccion = null;
		this.ambitoInspeccion = null;
		this.nombreUnidad = null;
	}

}
