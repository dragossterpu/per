package es.mira.progesin.web.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.Empleo;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.services.ICuerpoEstadoService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.CorreoElectronico;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("userBean")

public class UserBean {

	private User user;
	
	private List<Empleo> listaEmpleos;
	
	private Empleo empleoSeleccionado;
	
	private List<Departamento> listaDepartamentos;
	
	private Departamento departamentoSeleccionado;

	private List<CuerpoEstado> cuerposEstado;

	private CuerpoEstado cuerpoEstadoSeleccionado;

	private List<PuestoTrabajo> puestosTrabajo;

	private PuestoTrabajo puestoTrabajoSeleccionado;

	private UserBusqueda userBusqueda;

	private List<Boolean> list;

	private int numeroColumnasListadoUsarios = 9;

	private String estadoUsuario = null;

	private String vieneDe;
	
	@PersistenceContext
	private transient EntityManager em;

	@Autowired
	ApplicationBean applicationBean;

	@Autowired
	IUserService userService;

	@Autowired
	ICuerpoEstadoService cuerposEstadoService;

	@Autowired
	IRegistroActividadService regActividadService;

	@Autowired
	INotificacionService notificacionService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	CorreoElectronico correo;

	private final String NOMBRESECCION = "Usuarios";

	public String getUserPerfil() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userService.findOne(username);
		return "/principal/miPerfil";
	}

	/**
	 * Método que nos lleva al formulario de alta de nuevos usuarios, inicializando todo lo necesario para mostrar
	 * correctamente la página (cuerpos de estado, puestos de trabajo, usuario nuevo). Se llama desde la página de
	 * búsqueda de usuarios.
	 * @return
	 */
	public String nuevoUsuario() {
		user = new User();
		user.setFechaAlta(new Date());
		user.setEstado(EstadoEnum.ACTIVO);
		cuerposEstado = (List<CuerpoEstado>) cuerposEstadoService.findAll();
		puestosTrabajo = applicationBean.getListaPuestosTrabajo();
		// para que en el select cargue por defecto la opción "Seleccine uno..."
		puestoTrabajoSeleccionado = null;
		cuerpoEstadoSeleccionado = null;
		return "/users/altaUsuario";
	}

	/**
	 * Método que recoge los valores introducidos en el formulario y da de alta al usuario en la BBDD
	 * @return
	 */
	public String altaUsuario() {
		if (userService.exists(user.getUsername())) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					"Ya existe un usuario con ese nombre de usuario. Pruebe con otro");
			FacesContext.getCurrentInstance().addMessage("username", message);
		}
		else {
			
			try {
				user.setCuerpoEstado(getCuerpoEstadoSeleccionado());
				user.setPuestoTrabajo(getPuestoTrabajoSeleccionado());
				user.setEmpleo(getEmpleoSeleccionado());
				user.setDepartamento(getDepartamentoSeleccionado());
				String password = Utilities.getPassword();
				correo.setDatos(user.getCorreo(), "Alta en la herramienta Progesin", "Ha sido dado de alta en la herramienta PROGESIN con usuario/clave " + 
					user.getUsername() +"/" + password);
				user.setPassword(passwordEncoder.encode(password));
				correo.envioCorreo();
				if (userService.save(user) != null) {
					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
							"El usuario ha sido creado con éxito");
					String descripcion = "Alta nuevo usuario " + user.getNombre() + " " + user.getApellido1() + " "
							+ user.getApellido2();
					regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.ALTA.name(), NOMBRESECCION);
					notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
				}
			}
			catch (Exception e) {
				regActividadService.altaRegActividadError(getNOMBRESECCION(), e);
			}

		}
		return null;
	}

	/**
	 * Devuelve al formulario de búsqueda de usuarios a su estado inicial y borra los resultados de búsquedas anteriores
	 * si se navega desde el menú u otra sección.
	 * 
	 * @author EZENTIS
	 */
	public void getFormularioBusquedaUsuarios() {
		if ("menu".equalsIgnoreCase(this.vieneDe)) {
			limpiarBusqueda();
			this.vieneDe = null;
		}
	}

	/**
	 * Borra los resultados de búsquedas anteriores.
	 * 
	 * @author EZENTIS
	 */
	public void limpiarBusqueda() {
		userBusqueda.resetValues();
	}

	/**
	 * Busca los usuarios según los filtros introducidos en el formulariod de búsqueda
	 */
	public void buscarUsuario() {
		this.estadoUsuario = null;
		List<User> listaUsuarios = userService.buscarUsuarioCriteria(userBusqueda);
		userBusqueda.setListaUsuarios(listaUsuarios);
		auditoriaBusqueda(userBusqueda);
	}

	/**
	 * Realiza una eliminación lógico del usuario (le pone fecha de baja)
	 * @param user El usuario seleccionado de la tabla del resultado de la búsqueda
	 */
	public void eliminarUsuario(User user) {
		
		user.setFechaBaja(new Date());
		user.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			userService.save(user);
			userBusqueda.getListaUsuarios().remove(user);
			String descripcion = "Se ha eliminado el usuario " + user.getNombre() + " " + user.getApellido1() + " "
					+ user.getApellido2();
			// Guardamos la actividad en bbdd
			regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.BAJA.name(), NOMBRESECCION);
			// Guardamos la notificacion en bbdd
			notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
		}
		catch (Exception e) {
			// Guardamos loe posibles errores en bbdd
			regActividadService.altaRegActividadError(getNOMBRESECCION(), e);
		}

	}

	/**
	 * Pasa los datos del usuario que queremos modificar al formulario de modificación para que cambien los valores que
	 * quieran
	 * @param user usuario recuperado del formulario de búsqueda de usuarios
	 * @return
	 */
	public String getFormModificarUsuario(User user) {
		estadoUsuario = user.getEstado().name();
		this.user = user;
		
		auditoriaVisualizacion(user);
		return "/users/modificarUsuario";
	}

	/**
	 * Modifica los datos del usuario en función de los valores recuperados del formulario
	 */
	public void modificarUsuario() {
		try {

			if (userService.save(user) != null) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
						"El usuario ha sido modificado con éxito");
				String descripcion = "Modificación del usuario :" + " " + user.getNombre() + " " + user.getApellido1()
						+ " " + user.getApellido2();
				if (estadoUsuario != user.getEstado().name()) {
					// Generamos la alerta
				}
				
				regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(), NOMBRESECCION);
				notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Modificación",
					"Se ha producido un error al modificar el usuario. Inténtelo de nuevo más tarde");
			// Guardamos loe posibles errores en bbdd
			regActividadService.altaRegActividadError(getNOMBRESECCION(), e);
		}

	}

	/**
	 * Genera una contraseña nueva y se la envía por correo al usuario
	 */
	public void restaurarClave() {
		try {
			String password = Utilities.getPassword();
			this.user.setPassword(passwordEncoder.encode(password));
			String cuerpoCorreo = "Su nueva contraseña es: " + password;
			userService.save(user);
			correo.setDatos(user.getCorreo(), "Restauración de la contraseña", cuerpoCorreo);
			correo.envioCorreo();
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Clave",
					"Se ha enviado un correo al usuario con la nueva contraseña");
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Clave",
					"Se ha producido un error en la regeneración o envío de la contraseña");
			// Guardamos loe posibles errores en bbdd
			regActividadService.altaRegActividadError(getNOMBRESECCION(), e);
		}
	}

	public void onToggle(ToggleEvent e) {
		list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	@PostConstruct
	public void init() {
		userBusqueda = new UserBusqueda();
		this.cuerposEstado = (List<CuerpoEstado>) cuerposEstadoService.findAll();
		this.puestosTrabajo = applicationBean.getListaPuestosTrabajo();
		
		TypedQuery<Departamento> queryDepartamento = em.createNamedQuery("Departamento.findAll", Departamento.class);
		listaDepartamentos = queryDepartamento.getResultList();
		
		// para que en el select cargue por defecto la opción "Seleccine uno..."
		this.puestoTrabajoSeleccionado = null;
		this.cuerpoEstadoSeleccionado = null;
		this.empleoSeleccionado=null;
		this.departamentoSeleccionado=null;
		list = new ArrayList<>();
		for (int i = 0; i <= numeroColumnasListadoUsarios; i++) {
			list.add(Boolean.TRUE);
		}
	}
	
	public void buscarEmpleo(){
		TypedQuery<Empleo> queryEmpleo = em.createNamedQuery("EmpleoCuerpo.find", Empleo.class);
		CuerpoEstado cuerpo=this.cuerpoEstadoSeleccionado!=null?this.cuerpoEstadoSeleccionado:this.user.getCuerpoEstado();
		queryEmpleo.setParameter("cuerpoSeleccionado", cuerpo);
		listaEmpleos = queryEmpleo.getResultList();
	}

	private void auditoriaBusqueda(UserBusqueda usuario){
		
		String puesto=usuario.getPuestoTrabajo()!=null?usuario.getPuestoTrabajo().getDescripcion():"";
		String estado=usuario.getEstado()!=null?usuario.getEstado().name():"";
	
		
		DateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
		
		String fechaDesde=usuario.getFechaDesde()!=null?fecha.format(usuario.getFechaDesde()):"";
		String fechaHasta=usuario.getFechaHasta()!=null?fecha.format(usuario.getFechaHasta()):"";
		String rol=usuario.getRole()!=null?usuario.getRole().name():"";
		String cuerpo=usuario.getCuerpoEstado()!=null?usuario.getCuerpoEstado().getDescripcion():"";
		
		
		
		String descripcion="El usuario " + SecurityContextHolder.getContext().getAuthentication().getName() + " ha realizado una consulta de usuarios." 
				+ "\nLa consulta realizada ha sido la siguiente: " 
				+ "\n Nombre de usuario: " + usuario.getUsername()
				+ "\n Nombre: " + usuario.getNombre()
				+ "\n Primer apellido: " + usuario.getApellido1()
				+ "\n Segundo apellido: " + usuario.getApellido2()
				+ "\n Puesto de trabajo: " + puesto
				+ "\n Estado: " + estado
				+ "\n Fecha alta desde: " + fechaDesde
				+ "\n Fecha alta desde: " + fechaHasta
				+ "\n Rol: " +  rol
				+ "\n Cuerpo del estado: " + cuerpo;
		
		
	
		regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.AUDITORIA.name(), NOMBRESECCION);
		
	}
	
	private void auditoriaVisualizacion(User usuario){
		String descripcion="El usuario " + SecurityContextHolder.getContext().getAuthentication().getName() + " ha realizado ha visualizado un usuario." 
				+ "El usuario consultado es: " + usuario.getUsername();
	
		regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.AUDITORIA.name(), NOMBRESECCION);
	}
}



	
