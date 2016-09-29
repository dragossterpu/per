package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IUserService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("equiposBean")
@RequestScoped
public class EquiposBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Equipo equipo;

	private Date fechaDesde;

	private Date fechaHasta;

	private String nombreJefe;

	private String username;

	private String nombreMiembro;

	private String nombreEquipo;

	private User jefeSelecionado;

	private List<User> miembrosSelecionados;

	private List<User> colaboradoresSelecionados;

	List<User> listadoJefes = new ArrayList<User>();

	List<User> listadoMiembros = new ArrayList<User>();

	List<User> listadoColaboradores = new ArrayList<User>();

	private EquipoBusqueda equipoBusqueda;

	@Autowired
	ApplicationBean applicationBean;

	@Autowired
	IEquipoService equipoService;

	@Autowired
	IUserService userService;

	/**
	 * Método que nos lleva al formulario de alta de nuevos equipos, inicializando todo lo necesario para mostrar
	 * correctamente la página. Se llama desde la página de búsqueda de equipos.
	 * @return
	 */
	public String nuevoEquipo() {
		equipo = new Equipo();
		equipo.setFechaAlta(new Date());
		equipo.setEquipoEspecial("NO");
		equipo.setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());

		List<User> listaUsuarios = (List<User>) userService.findAll();
		listadoJefes = new ArrayList<User>();
		listadoMiembros = new ArrayList<User>();
		listadoColaboradores = new ArrayList<User>();
		for (User user : listaUsuarios) {
			if (user.getPuestoTrabajo().getId() == 6) {
				listadoJefes.add(user);
			}
			else if (user.getPuestoTrabajo().getId() == 7) {
				listadoMiembros.add(user);
			}
			else {
				listadoColaboradores.add(user);
			}
		}
		return "/equipos/altaEquipo";
	}

	/**
	 * Método que recoge los valores introducidos en el formulario y da de alta un equipo normal en la BBDD
	 * @return
	 */
	public String altaEquipo() {
		if (jefeSelecionado == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Se debe elegir un jefe de equipo");
			FacesContext.getCurrentInstance().addMessage("eqipoJefes", message);
		}
		equipo.setJefeEquipo(jefeSelecionado);
		equipo.setMiembros(miembrosSelecionados);
		// equipo.setColaboradores(colaboradoresSelecionados);
		equipo.setEquipoEspecial("NO");
		equipo.setFechaAlta(new Date());
		equipo.setNombreEquipo(jefeSelecionado.getNombre() + " " + jefeSelecionado.getApellido1());
		equipo.setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());
		if (equipoService.save(equipo) != null) {
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta",
					"El equipo ha sido creado con éxito");
			FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
			context.execute("PF('dialogMessage').show()");
		}

		// TODO generar NOTIFICACIÓN
		// TODO registrar actividad en log
		return null;
	}

	public String getFormularioBusquedaEquipos() {
		// equipoBusqueda.resetValues();
		return "/equipos/equipos";
	}

	public void buscarEquipo() {
		List<Equipo> listaEquipos = equipoService.buscarEquipoCriteria(equipoBusqueda);
		equipoBusqueda.setListaEquipos(listaEquipos);
	}

	public void eliminarUsuario(Equipo equipo) {
		equipo.setFechaBaja(new Date());
		equipo.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
		equipoService.save(equipo);
		equipoBusqueda.getListaEquipos().remove(equipo);
	}

	@PostConstruct
	public void init() {
		equipoBusqueda = new EquipoBusqueda();

	}
}
