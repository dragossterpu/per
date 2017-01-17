package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.CuestionarioEnviadoEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Controller("cuestionarioEnviadoBusqueda")
@Scope("session")
public class CuestionarioEnviadoBusqueda implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date fechaDesde;

	private Date fechaHasta;

	private Date fechaLimiteRespuesta;

	private CuestionarioEnviadoEnum estado;

	private String nombreUsuarioEnvio;

	private String numeroInspeccion;

	private TipoInspeccion tipoInspeccion;

	private AmbitoInspeccionEnum ambitoInspeccion;

	private String nombreEquipo;

	private String nombreUnidad;

	private String nombreCuestionario;

	private ModeloCuestionario modeloCuestionarioSeleccionado;

	private List<TipoInspeccion> listaTiposInspeccion;

	@PersistenceContext
	private transient EntityManager em;

	@PostConstruct
	public void init() {
		TypedQuery<TipoInspeccion> query = em.createNamedQuery("TipoInspeccion.findAll", TipoInspeccion.class);
		listaTiposInspeccion = query.getResultList();
	}

	public void limpiar() {
		this.fechaDesde = null;
		this.fechaHasta = null;
		this.fechaLimiteRespuesta = null;
		this.estado = null;
		this.nombreUsuarioEnvio = null;
		this.numeroInspeccion = null;
		this.tipoInspeccion = null;
		this.ambitoInspeccion = null;
		this.nombreEquipo = null;
		this.nombreUnidad = null;
		this.nombreCuestionario = null;
		this.modeloCuestionarioSeleccionado = null;
	}

}
