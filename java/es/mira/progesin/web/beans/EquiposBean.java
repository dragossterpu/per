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
import es.mira.progesin.persistence.entities.Miembros;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
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

	private List<Miembros> listMiembros;

	private String nombreEquipo;

	private User jefeSelecionado;

	private List<User> miembrosSelecionados;

	// private Miembros listMiembros;

	private List<User> miembros;

	private List<Equipo> listaEquipos;

	private String estado = null;

	Miembros miembro = new Miembros();

	List<User> listadoJefes = new ArrayList<User>();

	List<User> listadoMiembros = new ArrayList<User>();

	List<User> listadoColaboradores = new ArrayList<User>();

	private EquipoBusqueda equipoBusqueda;

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
		this.jefeSelecionado = null;
		this.miembrosSelecionados = null;

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
			FacesContext.getCurrentInstance().addMessage("equipoJefes", message);
		}

		miembrosSelecionados.add(jefeSelecionado);
		equipo.setJefeEquipo(jefeSelecionado.getUsername());
		equipo.setNombreJefe(jefeSelecionado.getNombre() + " " + jefeSelecionado.getApellido1() + " "
				+ jefeSelecionado.getApellido2());
		equipo.setEquipoEspecial("NO");
		equipo.setNombreEquipo(jefeSelecionado.getNombre() + " " + jefeSelecionado.getApellido1());
		if (equipoService.save(equipo) != null) {
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta",
					"El equipo ha sido creado con éxito");
			FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
			context.execute("PF('dialogMessage').show()");
		}
		Miembros miembro = new Miembros();
		miembro.setIdMiembros(equipo.getIdEquipo());
		miembro.setNombreCompleto(jefeSelecionado.getNombre() + " " + jefeSelecionado.getApellido1() + " "
				+ jefeSelecionado.getApellido2());
		miembro.setUsername(jefeSelecionado.getUsername());
		miembro.setPosicion("Jefe de equipo");
		equipoService.save(miembro);
		for (User user : miembrosSelecionados) {
			Miembros miembro2 = new Miembros();
			miembro2.setNombreCompleto(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
			miembro2.setIdMiembros(equipo.getIdEquipo());
			miembro2.setUsername(user.getUsername());
			miembro2.setPosicion("Miembro");
			equipoService.save(miembro2);
		}

		// TODO generar NOTIFICACIÓN
		// TODO registrar actividad en log
		return null;
	}

	public String getFormularioBusquedaEquipos() {
		equipoBusqueda.resetValues();
		return "/equipos/equipos";
	}

	public String buscarEquipo() {
		limpiarValores();
		equipoBusqueda.resetValues();
		equipoBusqueda.setListaEquipos(null);
		if (estado.equals("") || estado.equals("ACTIVO")) {
			equipoBusqueda.setEstado("ACTIVO");
		}
		else {
			equipoBusqueda.setEstado("INACTIVO");
		}
		listaEquipos = equipoService.buscarEquipoCriteria(equipoBusqueda);
		for (Equipo equipo : listaEquipos) {
			User user = userService.findOne(equipo.getJefeEquipo());
			equipo.setNombreJefe(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
		}
		equipoBusqueda.setListaEquipos(listaEquipos);
		return "/equipos/listadoEquipos";
	}

	public String limpiarValores() {
		equipoBusqueda.resetValues();
		return null;
	}

	public String eliminarEquipo(Equipo equipo) {
		equipo.setFechaBaja(new Date());
		equipo.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
		equipoService.save(equipo);
		equipoBusqueda.getListaEquipos().remove(equipo);
		return "/equipos/listadoEquipos";
	}

	/**
	 * Pasa los datos del equipo que queremos modificar al formulario de modificación para que cambien los valores que
	 * quieran
	 * @param equipo recuperado del formulario de búsqueda de equipos
	 * @return
	 */
	public String getFormModificarEquipo(Equipo equipo) {
		listMiembros = new ArrayList<Miembros>();
		jefeSelecionado = userService.findOne(equipo.getJefeEquipo());
		listMiembros = equipoService.findByIdMiembros(equipo.getIdEquipo());
		equipo.setJefeEquipo(jefeSelecionado.getNombre() + " " + jefeSelecionado.getApellido1() + " "
				+ jefeSelecionado.getApellido2());
		// for (Miembros listado : listMiembros) {
		//
		// User user = userService.findOne(listado.getUsername());
		// listadoMiembros.add(user);
		// }

		this.equipo = equipo;
		return "/equipos/modificarEquipo";
	}

	/**
	 * Modifica los datos de un equipo en función de los valores recuperados del formulario
	 */
	public void modificarEquipo() {
		if (equipoService.save(equipo) != null) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
					"El equipo ha sido modificado con éxito");
		}
	}

	@PostConstruct
	public void init() {
		// para que en el select cargue por defecto la opción "Seleccine uno..."
		estado = null;
		equipoBusqueda = new EquipoBusqueda();

	}
}
