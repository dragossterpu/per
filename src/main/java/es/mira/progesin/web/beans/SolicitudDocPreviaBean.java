package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;

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
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.services.gd.IGestDocSolicitudDocumentacionService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
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

	@Autowired
	private SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda;

	private static final String NOMBRESECCION = "Generación de solicitud documentación";

	private static final String VISTASOLICITUD = "/solicitudesPrevia/vistaSolicitud";

	private static final String ERROR = "Error";

	private String vieneDe;

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

	private SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();

	private Date backupFechaLimiteEnvio = null;

	private boolean skip;

	private transient StreamedContent file;

	transient List<DocumentacionPrevia> listadoDocumentosPrevios = new ArrayList<>();

	transient List<GestDocSolicitudDocumentacion> listadoDocumentosCargados = new ArrayList<>();

	private List<TipoDocumentacion> documentosSeleccionados;

	transient List<TipoDocumentacion> listadoDocumentos = new ArrayList<>();

	@Autowired
	transient IGestDocSolicitudDocumentacionService gestDocumentacionService;

	@Autowired
	transient IUserService userService;

	@Autowired
	transient IDocumentoService documentoService;

	private Map<String, String> datosApoyo;

	@Autowired
	private transient PasswordEncoder passwordEncoder;

	@Autowired
	private transient ICorreoElectronico correoElectronico;

	@Autowired
	transient ApplicationBean applicationBean;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private String motivosNoConforme;

	private Map<String, String> parametrosVistaSolicitud;

	/**
	 * Crea una solicitud de documentación en base a los datos introducidos en el formulario de la vista crearSolicitud.
	 * 
	 * @author EZENTIS
	 * @return vista busquedaSolicitudesDocPrevia
	 */
	public String crearSolicitud() {

		try {
			String correoDestinatario = solicitudDocumentacionPrevia.getCorreoDestinatario();
			if (solicitudDocumentacionService
					.findByFechaFinalizacionIsNullAndCorreoDestinatario(correoDestinatario) != null) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_WARN, "Alta abortada",
						"No se puede crear una solicitud para el destinatario con correo " + correoDestinatario
								+ ", ya existe otra en curso. Debe finalizarla o eliminarla antes de proseguir.");
			}
			else {
				if (solicitudDocumentacionService.save(solicitudDocumentacionPrevia) != null) {
					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
							"La solicitud de documentación ha sido creada con éxito");

					altaDocumentos();
					String descripcion = "Solicitud documentación previa cuestionario. Usuario creación : "
							+ SecurityContextHolder.getContext().getAuthentication().getName();
					// Guardamos la actividad en bbdd
					regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.ALTA.name(),
							NOMBRESECCION);

					// Guardamos la notificacion en bbdd
					notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
				}
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al crear la solicitud, inténtelo de nuevo más tarde");
			// Guardamos los posibles errores en bbdd
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
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

		for (TipoDocumentacion documento : documentosSeleccionados) {
			DocumentacionPrevia docPrevia = new DocumentacionPrevia();
			docPrevia.setIdSolicitud(solicitudDocumentacionPrevia.getId());
			docPrevia.setDescripcion(documento.getDescripcion());
			docPrevia.setExtensiones(documento.getExtensiones());
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
		solicitudDocumentacionPrevia = solicitud;
		backupFechaLimiteEnvio = solicitud.getFechaLimiteEnvio();
		return "/solicitudesPrevia/modificarSolicitud";
	}

	/**
	 * Permite visualizar una solicitud creada, muestra su información y dependiendo del estado en que se encuentre
	 * permite pasar al siguiente estado si se tiene el rol adecuado. Posibles estados: alta, validada por apoyo,
	 * validada por jefe equipo, enviada, cumplimentada, no conforme y finalizada
	 * 
	 * @author EZENTIS
	 * @param solicitud
	 * @return vista vistaSolicitud
	 */
	public String visualizarSolicitud(SolicitudDocumentacionPrevia solicitud) {
		try {
			// parametrosVistaSolicitud = applicationBean.getMapaParametros()
			// .get("vistaSolicitud" + solicitud.getInspeccion().getAmbito());
			listadoDocumentosCargados = gestDocumentacionService.findByIdSolicitud(solicitud.getId());
			listadoDocumentosPrevios = tipoDocumentacionService.findByIdSolicitud(solicitud.getId());
			solicitudDocumentacionPrevia = solicitud;
			return "/solicitudesPrevia/vistaSolicitud";
		}
		catch (Exception e) {
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
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
		try {
			solicitudDocumentacionPrevia.setFechaValidApoyo(new Date());
			solicitudDocumentacionPrevia
					.setUsernameValidApoyo(SecurityContextHolder.getContext().getAuthentication().getName());
			if (solicitudDocumentacionService.save(solicitudDocumentacionPrevia) != null) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Validación",
						"Se ha validado con éxito la solicitud de documentación");
				String descripcion = "Solicitud documentación previa cuestionario. Usuario validación jefe equipo : "
						+ SecurityContextHolder.getContext().getAuthentication().getName();

				regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						NOMBRESECCION);

				notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al validar jefe equipo la solicitud, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
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
		try {
			solicitudDocumentacionPrevia.setFechaValidJefeEquipo(new Date());
			solicitudDocumentacionPrevia
					.setUsernameValidJefeEquipo(SecurityContextHolder.getContext().getAuthentication().getName());
			if (solicitudDocumentacionService.save(solicitudDocumentacionPrevia) != null) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Validación",
						"Se ha validado con éxito la solicitud de documentación");

				String descripcion = "Solicitud documentación previa cuestionario. Usuario validación jefe equipo : "
						+ SecurityContextHolder.getContext().getAuthentication().getName();

				regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						NOMBRESECCION);

				notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al validar jefe equipo la solicitud, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
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
		documentosSeleccionados = null;
		solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
		solicitudDocumentacionPrevia.setInspeccion(new Inspeccion());
		datosApoyo();
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
		datosApoyo = applicationBean.getMapaParametros().get("datosApoyo");
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
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
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
			if ("documentacion".equals(event.getNewStep())) {
				AmbitoInspeccionEnum ambito = solicitudDocumentacionPrevia.getInspeccion().getAmbito();
				if (AmbitoInspeccionEnum.OTROS.equals(ambito)) {
					listadoDocumentos = tipoDocumentacionService.findAll();
				}
				else {
					listadoDocumentos = tipoDocumentacionService.findByAmbito(ambito);
				}
			}
			return event.getNewStep();
		}
	}

	public void onToggle(ToggleEvent e) {
		listaColumnToggler.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	/**
	 * Elimina la solicitud de documentación previa. Se hace eliminación física si no ha sido enviada aún sino sólo
	 * lógica junto a la eliminación física del usuario provisional. Además desde la interfaz las solicitudes
	 * finalizadas no se pueden eliminar.
	 * 
	 * @author EZENTIS
	 * @param solicitudDocumentacionPrevia solicitud a eliminar
	 */
	public void eliminarSolicitud(SolicitudDocumentacionPrevia solicitud) {
		try {
			// Si no ha sido enviada se trata como un borrador y se hace eliminación física
			if (solicitud.getFechaEnvio() == null) {
				solicitudDocumentacionService.transaccDeleteElimDocPrevia(solicitud.getId());

				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Eliminación",
						"Se ha eliminado con éxito la solicitud de documentación");
			}
			else {
				// Enviada pero no finalizada, existe usuario provisional
				solicitud.setFechaBaja(new Date());
				solicitud.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
				solicitud.setFechaFinalizacion(new Date());
				String usuarioProv = solicitud.getCorreoDestinatario();
				if (solicitudDocumentacionService.transaccSaveElimUsuarioProv(solicitud, usuarioProv)) {
					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Baja",
							"Se ha dado de baja con éxito la solicitud de documentación");

					String descripcion = "Solicitud documentación previa cuestionario. Usuario baja : "
							+ SecurityContextHolder.getContext().getAuthentication().getName();

					regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.BAJA.name(),
							NOMBRESECCION);

					notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
				}
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al eliminar la solicitud, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
		listaSolicitudesPrevia = null;
		listaSolicitudesPrevia = solicitudDocumentacionService.findAll();
	}

	/**
	 * Modifica los datos de la solicitud de documentación previa
	 * 
	 * @author EZENTIS
	 */
	public String modificarSolicitud() {
		try {
			if (solicitudDocumentacionService.save(solicitudDocumentacionPrevia) != null) {
				String mensajeCorreoEnviado = "";
				// Avisar al destinatario si la fecha limite para la solicitud ha cambiado
				if (solicitudDocumentacionPrevia.getFechaEnvio() != null
						&& !backupFechaLimiteEnvio.equals(solicitudDocumentacionPrevia.getFechaLimiteEnvio())) {
					String asunto = "Solicitud de documentación previa para la inspección "
							+ solicitudDocumentacionPrevia.getInspeccion().getNumero();
					String textoAutomatico = "\r\n \r\nEl plazo del que disponía para enviar la documentación previa conectándose a la aplicación PROGESIN ha sido modificado."
							+ "\r\n \r\nFecha límite de envío anterior: " + sdf.format(backupFechaLimiteEnvio)
							+ "\r\nFecha límite de envío nueva :"
							+ sdf.format(solicitudDocumentacionPrevia.getFechaLimiteEnvio())
							+ "\r\n \r\nMuchas gracias y un saludo.";
					String cuerpo = "Asunto: " + solicitudDocumentacionPrevia.getAsunto() + textoAutomatico;
					correoElectronico.setDatos(solicitudDocumentacionPrevia.getCorreoDestinatario(), asunto, cuerpo);
					correoElectronico.envioCorreo();
					mensajeCorreoEnviado = ". Se ha comunicado al destinatario de la unidad el cambio de fecha.";
				}
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
						"La solicitud de documentación ha sido modificada con éxito" + mensajeCorreoEnviado);
				listadoDocumentosCargados = gestDocumentacionService
						.findByIdSolicitud(solicitudDocumentacionPrevia.getId());

				String descripcion = "Solicitud documentación previa cuestionario. Usuario modificación : "
						+ SecurityContextHolder.getContext().getAuthentication().getName();

				regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						NOMBRESECCION);

				notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al modificar la solicitud, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
		return "/solicitudesPrevia/modificarSolicitud";
	}

	/**
	 * Permite al jefe de equipo de inspecciones validar la solicitud de documentación y enviarla y dar de alta un
	 * usuario provisional para que algún miembro de la unidad a inspeccionar la cumplimente.
	 * 
	 * @author EZENTIS
	 * @return vista vistaSolicitud
	 */
	public String enviarSolicitud() {
		try {
			String correoDestinatario = solicitudDocumentacionPrevia.getCorreoDestinatario();
			if (userService.exists(correoDestinatario)) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_WARN, "Envío abortado",
						"No se puede crear un usuario provisional para el destinatario con correo " + correoDestinatario
								+ ", ya existe para otra tarea en curso. Debe finalizarla o eliminarla antes de proseguir.");
			}
			else {
				String password = Utilities.getPassword();

				solicitudDocumentacionPrevia.setFechaEnvio(new Date());
				solicitudDocumentacionPrevia
						.setUsernameEnvio(SecurityContextHolder.getContext().getAuthentication().getName());

				User usuarioProv = new User(solicitudDocumentacionPrevia.getCorreoDestinatario(),
						passwordEncoder.encode(password), RoleEnum.PROV_SOLICITUD);

				if (solicitudDocumentacionService.transaccSaveCreaUsuarioProv(solicitudDocumentacionPrevia,
						usuarioProv)) {
					System.out.println("Password usuario provisional  : " + password);

					String asunto = "Solicitud de documentación previa para la inspección "
							+ solicitudDocumentacionPrevia.getInspeccion().getNumero();
					String textoAutomatico = "\r\n \r\nPara cumplimentar la solicitud de documentación previa debe conectarse a la aplicación PROGESIN. El enlace de acceso a la "
							+ "aplicación es "
							+ applicationBean.getMapaParametros().get("URLPROGESIN")
									.get(solicitudDocumentacionPrevia.getInspeccion().getAmbito().name())
							+ ", su usuario de acceso es su correo electrónico y la contraseña es " + password
							+ ". \r\n \r\nUna vez enviada la solicitud cumplimentada su usuario quedará inactivo. \r\n \r\n"
							+ "Muchas gracias y un saludo.";
					String cuerpo = "Asunto: " + solicitudDocumentacionPrevia.getAsunto() + textoAutomatico;
					correoElectronico.setDatos(solicitudDocumentacionPrevia.getCorreoDestinatario(), asunto, cuerpo);
					correoElectronico.envioCorreo();

					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Envío",
							"Se ha enviado con éxito la solicitud de documentación");

					String descripcion = "Solicitud documentación previa cuestionario. Usuario envío : "
							+ SecurityContextHolder.getContext().getAuthentication().getName();

					regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
							NOMBRESECCION);

					notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
				}
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al enviar la solicitud, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
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
		try {
			solicitudDocumentacionPrevia.setFechaFinalizacion(new Date());
			String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
			solicitudDocumentacionPrevia.setUsuarioFinalizacion(usuarioActual);
			String usuarioProv = solicitudDocumentacionPrevia.getCorreoDestinatario();
			if (solicitudDocumentacionService.transaccSaveElimUsuarioProv(solicitudDocumentacionPrevia, usuarioProv)) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Finalización",
						"Se ha finalizado con éxito la solicitud de documentación");

				String descripcion = "Solicitud documentación previa cuestionario. Usuario finalización : "
						+ SecurityContextHolder.getContext().getAuthentication().getName();
				regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						NOMBRESECCION);

				notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al finalizar la solicitud, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
		return VISTASOLICITUD;
	}

	/**
	 * Carga el formulario para declarar no conforme una solicitud.
	 * 
	 * @author EZENTIS
	 * @return vista noConformeSolicitud
	 */
	public String getFormNoConformeSolicitud() {
		motivosNoConforme = null;
		return "/solicitudesPrevia/noConformeSolicitud";
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
		try {
			solicitudDocumentacionPrevia.setFechaCumplimentacion(null);
			solicitudDocumentacionPrevia.setFechaNoConforme(new Date());
			String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
			solicitudDocumentacionPrevia.setUsuarioNoConforme(usuarioActual);
			String usuarioProv = solicitudDocumentacionPrevia.getCorreoDestinatario();
			if (solicitudDocumentacionService.transaccSaveActivaUsuarioProv(solicitudDocumentacionPrevia,
					usuarioProv)) {

				String asunto = "Solicitud de documentación previa para la inspección "
						+ solicitudDocumentacionPrevia.getInspeccion().getNumero();
				String textoAutomatico = "\r\n \r\nSe ha declarado no conforme la solicitud que usted envió por los motivos que se exponen a continuación:"
						+ "\r\n \r\n" + motivosNoConforme
						+ "\r\n \r\nPara solventarlo debe volver a conectarse a la aplicación PROGESIN. El enlace de acceso a la aplicación es "
						+ applicationBean.getMapaParametros().get("URLPROGESIN")
								.get(solicitudDocumentacionPrevia.getInspeccion().getAmbito().name())
						+ ", su usuario de acceso es su correo electrónico y la contraseña es la que consta en la primera comunicación que se le envió."
						+ "\r\n \r\nEn caso de haber perdido dicha información póngase en contacto con el administrador de la aplicación a través del correo xxxxx@xxxx para solicitar una nueva contraseña."
						+ "\r\n \r\nUna vez enviada la solicitud cumplimentada su usuario quedará inactivo de nuevo. \r\n \r\n"
						+ "Muchas gracias y un saludo.";
				String cuerpo = "Asunto: " + solicitudDocumentacionPrevia.getAsunto() + textoAutomatico;
				correoElectronico.setDatos(solicitudDocumentacionPrevia.getCorreoDestinatario(), asunto, cuerpo);
				correoElectronico.envioCorreo();

				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "No Conforme",
						"Declarada no conforme con éxito la solicitud de documentación. El destinatario de la unidad será notificado y reactivado su acceso al sistema");

				String descripcion = "Solicitud documentación previa cuestionario. Usuario no conforme : "
						+ SecurityContextHolder.getContext().getAuthentication().getName();

				regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						NOMBRESECCION);

				notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al declarar no conforme la solicitud, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
		return "/solicitudesPrevia/noConformeSolicitud";
	}

	/**
	 * Devuelve al formulario de búsqueda de solicitudes de documentación previa a su estado inicial y borra los
	 * resultados de búsquedas anteriores si se navega desde el menú u otra sección.
	 * 
	 * @author EZENTIS
	 */
	public void getFormBusquedaSolicitudes() {
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
		String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
		String jefeEquipoInspeccion = solicitudDocumentacionPrevia.getInspeccion().getEquipo().getJefeEquipo();
		return usuarioActual.equals(jefeEquipoInspeccion);
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
