package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
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
@ViewScoped
public class EquiposBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Logger LOG = Logger.getLogger(EquiposBean.class.getName());

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

	private List<User> colaboradoresSelecionados;

	private List<Boolean> list;

	private List<User> miembros;

	private List<Equipo> listaEquipos;

	private String estado = null;

	private Boolean equipoEspecial;

	Miembros miembro = new Miembros();

	List<User> listadoJefes = new ArrayList<User>();

	List<User> listadoMiembros = new ArrayList<User>();

	List<User> listadoColaboradores;

	private int numeroColumnasListadoEquipos = 5;

	private EquipoBusqueda equipoBusqueda;

	private boolean skip;

	private String tipoEquipo;

	List<User> listaUsuarios;

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
		this.listadoMiembros = null;
		this.equipo = null;
		this.tipoEquipo = null;
		equipo = new Equipo();
		jefeSelecionado = new User();
		miembrosSelecionados = new ArrayList<User>();
		equipo.setFechaAlta(new Date());
		if (equipoEspecial) {
			equipo.setEquipoEspecial("SI");
		}
		else {
			equipo.setEquipoEspecial("NO");
		}
		equipo.setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());
		listaUsuarios = (List<User>) userService.findAll();
		listadoJefes = listaUsuarios;
		return "/equipos/altaEquipo";
	}

	/**
	 * Método que recoge los valores introducidos en el formulario y da de alta un equipo normal en la BBDD
	 * @return
	 */
	public String altaEquipo() {

		equipo.setJefeEquipo(jefeSelecionado.getUsername());
		equipo.setNombreJefe(jefeSelecionado.getNombre() + " " + jefeSelecionado.getApellido1() + " "
				+ jefeSelecionado.getApellido2());
		// equipo.setNombreEquipo(jefeSelecionado.getNombre() + " " + jefeSelecionado.getApellido1());
		equipo.setTipoEquipo(tipoEquipo);
		if (equipoService.save(equipo) != null) {
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta",
					"El equipo ha sido creado con éxito");
			FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
			context.execute("PF('dialogMessage').show()");
		}
		// alta en la tabla miembros de un equipo
		altaJefeEquipo();
		altaMiembrosEquipo();

		this.equipoEspecial = false;
		// TODO generar NOTIFICACIÓN
		// TODO registrar actividad en log
		return "/equipos/equipos";
	}

	public String getFormularioBusquedaEquipos() {
		equipoBusqueda.resetValues();
		this.estado = null;
		return "/equipos/equipos";
	}

	public String buscarEquipo() {

		List<Equipo> listaEquipos = equipoService.buscarEquipoCriteria(equipoBusqueda);
		for (Equipo equipo : listaEquipos) {
			User user = userService.findOne(equipo.getJefeEquipo());
			equipo.setNombreJefe(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
		}
		equipoBusqueda.setListaEquipos(listaEquipos);
		return "/equipos/equipos";
	}

	public String limpiarValores() {
		this.equipoEspecial = false;
		// limpiamos los datos del formulario
		this.miembrosSelecionados = null;
		return null;
	}

	public String eliminarEquipo(Equipo equipo) {
		equipo.setFechaBaja(new Date());
		equipo.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
		equipoService.save(equipo);
		equipoBusqueda.getListaEquipos().remove(equipo);
		return "/equipos/equipos";
	}

	/**
	 * Pasa los datos del equipo que queremos modificar al formulario de modificación para que cambien los valores que
	 * quieran
	 * @param equipo recuperado del formulario de búsqueda de equipos
	 * @return
	 */
	public String getFormModificarEquipo(Equipo equipo) {
		this.miembrosSelecionados = null;
		this.listadoColaboradores = null;
		listMiembros = new ArrayList<Miembros>();
		jefeSelecionado = userService.findOne(equipo.getJefeEquipo());
		listMiembros = equipoService.findByIdMiembros(equipo.getIdEquipo());
		equipo.setJefeEquipo(jefeSelecionado.getUsername());
		equipo.setNombreJefe(jefeSelecionado.getNombre() + " " + jefeSelecionado.getApellido1() + " "
				+ jefeSelecionado.getApellido2());
		this.equipo = equipo;
		return "/equipos/modificarEquipo";
	}

	/**
	 * Modifica los datos de un equipo en función de los valores recuperados del formulario
	 */
	public void modificarEquipo() {
		this.listadoColaboradores = null;
		if (equipoService.save(equipo) != null) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
					"El equipo ha sido modificado con éxito");
		}
	}

	public String aniadirMiembro(Equipo equipo) {
		return "/equipos/equipos";
	}

	public String borrarMiembro(Equipo equipo) {
		return "/equipos/equipos";
	}

	public String aniadirColaborador() {
		this.listadoColaboradores = null;
		colaboradoresSelecionados = null;
		listadoColaboradores = new ArrayList<User>();
		List<User> listaUsuarios = (List<User>) userService.findAll();
		for (User user : listaUsuarios) {
			if (user.getPuestoTrabajo().getId() != 6 || user.getPuestoTrabajo().getId() != 7) {
				listadoColaboradores.add(user);
			}
		}
		return "/equipos/anadirColaboradorEquipo";
	}

	public String guardarColaborador() {

		for (User user : colaboradoresSelecionados) {
			Miembros miembro = new Miembros();
			miembro.setIdMiembros(equipo.getIdEquipo());
			miembro.setNombreCompleto(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
			miembro.setUsername(user.getUsername());
			miembro.setPosicion("Colaborador");
			equipoService.save(miembro);
		}
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta",
				"Colaborador/es añadido/s con éxito");
		FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
		context.execute("PF('dialogMessage').show()");
		return null;
	}

	public void onToggle(ToggleEvent e) {
		list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	/**
	 * 
	 */
	private void nuevosListados() {
		this.listadoJefes = null;
		listadoJefes = new ArrayList<User>();
		this.listadoMiembros = null;
		listadoMiembros = new ArrayList<User>();
		this.listadoColaboradores = null;
		listadoColaboradores = new ArrayList<User>();
	}

	/**
	 * 
	 */
	private void altaJefeEquipo() {
		Miembros miembro = new Miembros();
		miembro.setIdMiembros(equipo.getIdEquipo());
		miembro.setNombreCompleto(jefeSelecionado.getNombre() + " " + jefeSelecionado.getApellido1() + " "
				+ jefeSelecionado.getApellido2());
		miembro.setUsername(jefeSelecionado.getUsername());
		miembro.setPosicion("Jefe de equipo");
		equipoService.save(miembro);
	}

	/**
	 * 
	 */
	private void altaMiembrosEquipo() {
		for (User user : miembrosSelecionados) {
			Miembros miembro2 = new Miembros();
			miembro2.setNombreCompleto(user.getNombre() + " " + user.getApellido1() + " " + user.getApellido2());
			miembro2.setIdMiembros(equipo.getIdEquipo());
			miembro2.setUsername(user.getUsername());
			miembro2.setPosicion("Miembro");
			equipoService.save(miembro2);
		}
	}

	public String onFlowProcess(FlowEvent event) {
		cleanParam(event);
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		}
		else {
			return event.getNewStep();
		}
	}

	/**
	 * @param event
	 */
	private void cleanParam(FlowEvent event) {
		if (event.getOldStep().equals("jefeEquipo") & event.getNewStep().equals("miembros")) {
			User jefe = jefeSelecionado;
			listadoJefes.remove(jefe);
			listadoMiembros = listadoJefes;
		}
		if (event.getOldStep().equals("confirm") & event.getNewStep().equals("miembros")) {
			this.miembrosSelecionados = null;
			listadoJefes = listaUsuarios;
		}
		if (event.getOldStep().equals("miembros") & event.getNewStep().equals("jefeEquipo")) {
			listadoJefes.add(jefeSelecionado);
			this.jefeSelecionado = null;
		}
		if (event.getOldStep().equals("jefeEquipo") & event.getNewStep().equals("general")) {
			this.equipo.setNombreEquipo("");
			this.tipoEquipo = null;
			this.jefeSelecionado = null;
			this.miembrosSelecionados = null;
		}
	}

	public void save() {
		FacesMessage msg = new FacesMessage("Successful", "Welcome :" + equipo.getIdEquipo());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	@PostConstruct
	public void init() {
		// para que en el select cargue por defecto la opción "Seleccine uno..."
		estado = null;
		this.equipo = null;
		this.equipoEspecial = false;
		equipoBusqueda = new EquipoBusqueda();
		equipoBusqueda.resetValues();
		list = new ArrayList<>();
		for (int i = 0; i <= numeroColumnasListadoEquipos; i++) {
			list.add(Boolean.TRUE);
		}
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
}
