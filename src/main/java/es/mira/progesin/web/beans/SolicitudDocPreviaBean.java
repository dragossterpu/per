package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Parametro;
import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.persistence.repositories.IParametrosRepository;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.services.gd.IGestDocSolicitudDocumentacionService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador de las operaciones relacionadas con las solicitudes de documentación previas al envio de cuestionarios.
 * Carga de nuevos modelos de solicitud, creación de solicitudes, validación por parte de apoyo, envío a la unidad en
 * cuestión, cumplimentación por parte de ésta y finalización de las mismas.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia
 */
@Setter
@Getter
@Component("solicitudDocPreviaBean")
@SessionScoped
public class SolicitudDocPreviaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	static String dialogMessage = "PF('dialogMessage').show()";

	static String system = "system";

	RegistroActividad regActividad = new RegistroActividad();

	@Autowired
	private SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda;

	private static final String NOMBRESECCION = "Generación de solicitud documentación";

	private static final String VISTASOLICITUD = "/solicitudesPrevia/vistaSolicitud";

	@Autowired
	transient IRegistroActividadService regActividadService;

	@Autowired
	transient INotificacionService notificacionService;

	transient List<SolicitudDocumentacionPrevia> listaSolicitudesPrevia;

	private List<Boolean> listaColumnToggler;

	@Autowired
	transient ISolicitudDocumentacionService solicitudDocumentacionService;

	@Autowired
	transient ITipoDocumentacionService tipoDocumentacionService;

	@Autowired
	transient IInspeccionesService inspeccionesService;

	SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();

	private boolean skip;

	private transient StreamedContent file;

	transient List<DocumentacionPrevia> listadoDocumentosPrevios = new ArrayList<>();

	transient List<GestDocSolicitudDocumentacion> listadoDocumentosCargados = new ArrayList<>();

	private List<TipoDocumentacion> documentosSelecionados;

	transient List<TipoDocumentacion> listadoDocumentos = new ArrayList<>();

	@Autowired
	transient IGestDocSolicitudDocumentacionService gestDocumentacionService;

	@Autowired
	transient IUserService userService;

	@Autowired
	transient IDocumentoService documentoService;

	@Autowired
	transient IParametrosRepository parametrosRepository;

	private Map<String, String> datosApoyo;

	@Autowired
	private transient PasswordEncoder passwordEncoder;

	/**
	 * Crea una solicitud de documentación en base a los datos introducidos en el formulario de la vista crearSolicitud.
	 * 
	 * @author EZENTIS
	 * @return vista busquedaSolicitudesDocPrevia
	 */
	public String crearSolicitud() {

		try {
			datosApoyo();

			if (solicitudDocumentacionService.save(solicitudDocumentacionPrevia) != null) {
				RequestContext context = RequestContext.getCurrentInstance();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta",
						"La solicitud de documentación ha sido creada con éxito");
				FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
				context.execute(dialogMessage);
			}
			altaDocumentos();
			String descripcion = "Solicitud documentación previa cuestionario. Usuario creación : "
					+ SecurityContextHolder.getContext().getAuthentication().getName();
			// Guardamos la actividad en bbdd
			regActividadService.crearRegistroActividad(descripcion, EstadoRegActividadEnum.ALTA.name(), NOMBRESECCION);

			// Guardamos la notificacion en bbdd
			// TODO usar NotificacionEnum
			notificacionService.crearNotificacion(descripcion, EstadoRegActividadEnum.ALTA.name(), NOMBRESECCION);
			this.solicitudDocumentacionPrevia = null;
		}
		catch (Exception e) {
			// Guardamos los posibles errores en bbdd
			regActividadService.altaRegActivError(NOMBRESECCION, e);
		}
		return "solicitudesPrevia/crearSolicitud";

	}

	/**
	 * Obtener los datos del jefe del equipo de apoyo. Se encuentran almacenados en la tabla parametros para que puedan
	 * ser modificados por el DBA
	 * 
	 * @author EZENTIS
	 * @see #getFormularioCrearSolicitud()
	 * @see es.mira.progesin.persistence.entities.Parametro
	 */
	private void datosApoyo() {
		solicitudDocumentacionPrevia.setApoyoCorreo(datosApoyo.get("ApoyoCorreo"));
		solicitudDocumentacionPrevia.setApoyoNombre(datosApoyo.get("ApoyoNombre"));
		solicitudDocumentacionPrevia.setApoyoPuesto(datosApoyo.get("ApoyoPuesto"));
		solicitudDocumentacionPrevia.setApoyoTelefono(datosApoyo.get("ApoyoTelefono"));
	}

	/**
	 * Permite dar de alta los documentos seleccionados al crear una solicitud de documentación. Colección de documentos
	 * de entre los disponibles en TipoDocumentación que se asignan a la solicitud.
	 * 
	 * @author EZENTIS
	 * @see es.mira.progesin.persistence.entities.gd.TipoDocumentacion
	 * @see es.mira.progesin.persistence.entities.DocumentacionPrevia
	 * @see #crearSolicitud()
	 */
	private void altaDocumentos() {

		for (TipoDocumentacion documento : documentosSelecionados) {
			DocumentacionPrevia docPrevia = new DocumentacionPrevia();
			docPrevia.setIdSolicitud(solicitudDocumentacionPrevia.getId());
			docPrevia.setDescripcion(documento.getDescripcion());
			docPrevia.setExtension(documento.getExtension());
			docPrevia.setNombre(documento.getNombre());
			tipoDocumentacionService.save(docPrevia);
		}
	}

	/**
	 * Pasa los datos de la solicitud que queremos modificar al formulario de modificación para que cambien los valores
	 * que quieran.
	 * 
	 * @author EZENTIS
	 * @param solicitud recuperado del formulario
	 * @return vista modificarSolicitud
	 */
	public String getFormModificarSolicitud(SolicitudDocumentacionPrevia solicitud) {
		this.solicitudDocumentacionPrevia = solicitud;
		return "/solicitudesPrevia/modificarSolicitud";
	}

	/**
	 * Permite visualizar una solicitud creada, muestra su información y dependiendo del estado en que se encuentre
	 * permite pasar al siguiente estado si se tiene el rol adecuado. Posibles estados: alta, validada por apoyo,
	 * enviada, cumplimentada y finalizada
	 * 
	 * @author EZENTIS
	 * @param solicitud
	 * @return vista vistaSolicitud
	 */
	public String visualizarSolicitud(SolicitudDocumentacionPrevia solicitud) {
		try {

			listadoDocumentosCargados = gestDocumentacionService.findByIdSolicitud(solicitud.getId());
			listadoDocumentosPrevios = tipoDocumentacionService.findByIdSolicitud(solicitud.getId());
			solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
			solicitudDocumentacionPrevia = solicitud;
			return "/solicitudesPrevia/vistaSolicitud";
		}
		catch (Exception e) {
			regActividadService.altaRegActivError(NOMBRESECCION, e);
			return null;
		}

	}

	/**
	 * Permite al equipo de apoyo validar la solicitud de documentación
	 * 
	 * @author EZENTIS
	 * @return vista vistaSolicitud
	 */
	public String validacionApoyo() {
		solicitudDocumentacionPrevia.setFechaValidApoyo(new Date());
		solicitudDocumentacionPrevia
				.setUsernameValidApoyo(SecurityContextHolder.getContext().getAuthentication().getName());
		if (solicitudDocumentacionService.save(solicitudDocumentacionPrevia) != null) {
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Validación",
					"Se ha validado con éxito la solicitud de documentación");
			FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
			context.execute(dialogMessage);
		}
		return VISTASOLICITUD;
	}

	/**
	 * Permite al jefe del equipo de apoyo validar la solicitud de documentación
	 * 
	 * @author EZENTIS
	 * @return vista vistaSolicitud
	 */
	public String validacionJefeEquipo() {
		solicitudDocumentacionPrevia.setFechaValidJefeEquipo(new Date());
		solicitudDocumentacionPrevia
				.setUsernameValidJefeEquipo(SecurityContextHolder.getContext().getAuthentication().getName());
		if (solicitudDocumentacionService.save(solicitudDocumentacionPrevia) != null) {
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Validación",
					"Se ha validado con éxito la solicitud de documentación");
			FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
			context.execute(dialogMessage);
		}
		return VISTASOLICITUD;
	}

	/**
	 * Carga el formulario para crear una solicitud.
	 * 
	 * @author EZENTIS
	 * @return vista crearSolicitud
	 */
	public String getFormularioCrearSolicitud() {
		this.documentosSelecionados = null;
		solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
		solicitudDocumentacionPrevia.setInspeccion(new Inspeccion());
		datosApoyo();
		listadoDocumentos = tipoDocumentacionService.findAll();
		return "/solicitudesPrevia/crearSolicitud";
	}

	/**
	 * PostConstruct, inicializa el bean
	 * 
	 * @author EZENTIS
	 */
	@PostConstruct
	public void init() {
		solicitudDocPreviaBusqueda.resetValues();
		listadoDocumentosCargados = new ArrayList<>();
		listaSolicitudesPrevia = new ArrayList<>();
		List<Parametro> parametrosDatosApoyo = parametrosRepository.findParamByParamSeccion("datosApoyo");
		datosApoyo = new HashMap<>();
		for (Parametro p : parametrosDatosApoyo) {
			datosApoyo.put(p.getParam().getClave(), p.getParam().getValor());
		}
	}

	/**
	 * Permite descargar el fichero seleccionado. Utilizado para documentos previos subidos por el interlocutor de una
	 * unidad al cumplimentar una solicitud.
	 * 
	 * @author EZENTIS
	 * @param idDocumento
	 */
	public void descargarFichero(Long idDocumento) {
		try {
			file = documentoService.descargaDocumento(idDocumento);
		}
		catch (Exception e) {
			regActividadService.altaRegActivError(NOMBRESECCION, e);
		}
	}

	/**
	 * Permite el webFlow
	 * 
	 * @author EZENTIS
	 * @param event
	 * @return
	 */
	public String onFlowProcess(FlowEvent event) {
		// cleanParam(event);
		if (skip) {
			// reset in case user goes back
			skip = false;
			return "confirm";
		}
		else {
			return event.getNewStep();
		}
	}

	public void onToggle(ToggleEvent e) {
		listaColumnToggler.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	/**
	 * Elimina la solicitud de documentación previa
	 * 
	 * @author EZENTIS
	 * @param solicitudDocumentacionPrevia solicitud a eliminar
	 */
	public void eliminarSolicitudDocumentacion(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia) {
		// TODO ¿Eliminar documentos asociados si los tiene?
		solicitudDocumentacionService.delete(solicitudDocumentacionPrevia.getId());
		this.listaSolicitudesPrevia = null;
		listaSolicitudesPrevia = solicitudDocumentacionService.findAll();
	}

	/**
	 * Modifica los datos de la solicitud de documentación previa
	 * 
	 * @author EZENTIS
	 */
	public void modificarSolicitud() {
		try {

			if (solicitudDocumentacionService.save(solicitudDocumentacionPrevia) != null) {
				RequestContext context = RequestContext.getCurrentInstance();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificación",
						"La solicitud de documentación ha sido modificada con éxito");
				FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
				context.execute(dialogMessage);
				listadoDocumentosCargados = gestDocumentacionService
						.findByIdSolicitud(solicitudDocumentacionPrevia.getId());
			}
		}
		catch (Exception e) {
			// Guardamos los posibles errores en bbdd
			regActividadService.altaRegActivError(NOMBRESECCION, e);
		}

	}

	/**
	 * Permite al jefe de equipo de inspecciones validar la solicitud de documentación y enviarla y dar de alta un
	 * usuario provisional para que algún miembro de la unidad a inspeccionar la cumplimente.
	 * 
	 * @author EZENTIS
	 * @return vista vistaSolicitud
	 */
	public String enviarSolicitud() {

		String password = Utilities.getPassword();

		solicitudDocumentacionPrevia.setFechaEnvio(new Date());
		solicitudDocumentacionPrevia.setUsernameEnvio(SecurityContextHolder.getContext().getAuthentication().getName());

		User usuarioProv = new User(solicitudDocumentacionPrevia.getCorreoDestinatario(),
				passwordEncoder.encode(password), RoleEnum.PROV_SOLICITUD);

		if (solicitudDocumentacionService.transaccSaveCreaUsuarioProv(solicitudDocumentacionPrevia, usuarioProv)) {
			System.out.println("Password usuario provisional  : " + password);
			// TODO usar servicio correo
			// String asunto = "Usuario provisional solicitud documentación";
			// String correoEnvio = "dragossterpu@gmail.com";
			// String nombre = "Prueba";
			// String respuesta = "El password es :" + password;
			// try {
			// SendSimpleMail.sendMail(asunto, correoEnvio, nombre, respuesta);
			// }
			// catch (MessagingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Envío",
					"Se ha enviado con éxito la solicitud de documentación");
			FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
			context.execute(dialogMessage);
		}
		return VISTASOLICITUD;
	}

	/**
	 * Permite al jefe del equipo finalizar una solicitud de documentación ya cumplimentada, después de revisar la
	 * documentación adjuntada por la unidad que se va a inspeccionar. Adicionalmente elimina el usuario provisinal que
	 * se usó para llevarla a cabo puesto que ya no se va a usar más.
	 * 
	 * @author EZENTIS
	 * @return vista vistaSolicitud
	 */
	public String finalizarSolicitud() {
		// TODO ¿avisar destinatario?
		solicitudDocumentacionPrevia.setFechaFinalizacion(new Date());
		String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
		solicitudDocumentacionPrevia.setUsuarioFinalizacion(usuarioActual);
		String usuarioProv = solicitudDocumentacionPrevia.getCorreoDestinatario();
		if (solicitudDocumentacionService.transaccSaveElimUsuarioProv(solicitudDocumentacionPrevia, usuarioProv)) {
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Finalización",
					"Se ha finalizado con éxito la solicitud de documentación");
			FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
			context.execute(dialogMessage);
		}
		return VISTASOLICITUD;
	}

	/**
	 * Permite al jefe de equipo declarar no conforme una solicitud de documentación ya cumplimentada, después de
	 * revisar la documentación adjuntada por la unidad que se va a inspeccionar. Para ello elimina la fecha de
	 * cumplimentación y reenvia la solicitud al destinatario de la unidad con el motivo de dicha no conformidad.
	 * Adicionalmente reactiva el usuario provisinal que se usó para llevarla a cabo.
	 * 
	 * @author EZENTIS
	 * @return vista vistaSolicitud
	 */
	public String noConformeSolicitud() {
		// TODO avisar destinatario y permitir añadir un motivo
		solicitudDocumentacionPrevia.setFechaCumplimentacion(null);
		solicitudDocumentacionPrevia.setFechaNoConforme(new Date());
		String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
		solicitudDocumentacionPrevia.setUsuarioNoConforme(usuarioActual);
		String usuarioProv = solicitudDocumentacionPrevia.getCorreoDestinatario();
		if (solicitudDocumentacionService.transaccSaveActivaUsuarioProv(solicitudDocumentacionPrevia, usuarioProv)) {
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "No Conforme",
					"Declarada no conforme con éxito la solicitud de documentación. El destinatario de la unidad será notificado y reactivado su acceso al sistema");
			FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
			context.execute(dialogMessage);
		}
		return VISTASOLICITUD;
	}

	/**
	 * Devuelve al formulario de búsqueda de solicitudes de documentación previa a su estado inicial y borra los
	 * resultados de búsquedas anteriores.
	 * 
	 * @author EZENTIS
	 */
	public void limpiarBusqueda() {
		solicitudDocPreviaBusqueda.resetValues();
		listaSolicitudesPrevia = null;
	}

	/**
	 * Busca las solicitudes de documentación previa según los filtros introducidos en el formulario de búsqueda.
	 * 
	 * @author EZENTIS
	 */
	public void buscarSolicitudDocPrevia() {
		listaSolicitudesPrevia = solicitudDocumentacionService
				.buscarSolicitudDocPreviaCriteria(solicitudDocPreviaBusqueda);
	}

	/**
	 * Comprueba si el usuario logueado es el jefe del equipo encargado de la solicitud de inspeccion.
	 * 
	 * @author EZENTIS
	 * @return result booleano
	 */
	public boolean esJefeEquipoInspeccion() {
		boolean esJefe = false;
		User usuarioActual = userService.findOne(SecurityContextHolder.getContext().getAuthentication().getName());
		if (RoleEnum.EQUIPO_INSPECCIONES.equals(usuarioActual.getRole())) {
			String jefeEquipoInspeccion = solicitudDocumentacionPrevia.getInspeccion().getEquipo().getJefeEquipo();
			esJefe = usuarioActual.getUsername().equals(jefeEquipoInspeccion);
		}
		return esJefe || RoleEnum.ADMIN.equals(usuarioActual.getRole());
	}

	/**
	 * Devuelve una lista con las inspecciones cuyo nombre de unidad o número contienen alguno de los caracteres pasado
	 * como parámetro. Se usa en los formularios de creación y modificación para el autocompletado.
	 * 
	 * @param inspeccion texto con parte del nombre de unidad o el número de la inspección que teclea el usuario en los
	 * formularios de creación y modificación
	 * @return Devuelve la lista de inspecciones que contienen algún caracter coincidente con el texto introducido
	 */
	public List<Inspeccion> autocompletarInspeccion(String infoInspeccion) {
		return inspeccionesService
				.buscarNoFinalizadaPorNombreUnidadONumeroSinSolicitudNoFinalizada("%" + infoInspeccion + "%");
	}

}
