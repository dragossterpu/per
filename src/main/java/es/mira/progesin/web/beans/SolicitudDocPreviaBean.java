package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

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
import es.mira.progesin.util.PdfGenerator;
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
@Controller("solicitudDocPreviaBean")
@Scope("session")
public class SolicitudDocPreviaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda;

	private static final String NOMBRESECCION = "Generación de solicitud documentación";

	private static final String DESCRIPCION = "Solicitud documentación previa cuestionario para la inspección ";

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

	@Autowired
	private PdfGenerator pdfGenerator;

	private StreamedContent pdfFile;

	/**
	 * Crea una solicitud de documentación en base a los datos introducidos en el formulario de la vista crearSolicitud.
	 * 
	 * @author EZENTIS
	 * @return vista busquedaSolicitudesDocPrevia
	 */
	public String crearSolicitud() {

		try {
			List<SolicitudDocumentacionPrevia> listaSolicitudes = solicitudDocumentacionService
					.findByFechaFinalizacionIsNullAndInspeccion(solicitudDocumentacionPrevia.getInspeccion());
			if (listaSolicitudes != null && listaSolicitudes.isEmpty() == Boolean.FALSE) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_WARN, "Alta abortada",
						"No se puede crear una solicitud para la inspección "
								+ solicitudDocumentacionPrevia.getInspeccion().getNumero()
								+ ", ya existe otra en curso. Debe finalizarla o eliminarla antes de proseguir.");
			}
			else {
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
						String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero();
						// Guardamos la actividad en bbdd
						regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.ALTA.name(),
								NOMBRESECCION);

						// Guardamos la notificacion en bbdd
						notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
					}
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

				String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero()
						+ " validada por apoyo";

				regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						NOMBRESECCION);

				notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al validar apoyo la solicitud, inténtelo de nuevo más tarde");
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

				String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero()
						+ " validada por jefe equipo";

				regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						NOMBRESECCION);

				notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al validar el jefe del equipo la solicitud, inténtelo de nuevo más tarde");
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
		documentosSeleccionados = new ArrayList<>();
		solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
		solicitudDocumentacionPrevia.setInspeccion(new Inspeccion());
		datosApoyo();
		skip = false;
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

		if ("general".equals(event.getOldStep()) && "documentacion".equals(event.getNewStep())) {
			AmbitoInspeccionEnum ambito = solicitudDocumentacionPrevia.getInspeccion().getAmbito();
			if (AmbitoInspeccionEnum.OTROS.equals(ambito)) {
				listadoDocumentos = tipoDocumentacionService.findAll();
			}
			else {
				listadoDocumentos = tipoDocumentacionService.findByAmbito(ambito);
			}
		}
		if ("documentacion".equals(event.getOldStep()) && "apoyo".equals(event.getNewStep()) && documentosSeleccionados.isEmpty() && skip == Boolean.FALSE) {
			FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
					"Debe elegir uno o más documentos o confirmar que no desea ninguno", "", "");
			return event.getOldStep();
		}
		return event.getNewStep();
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
			User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<RoleEnum> rolesAdmitidos = new ArrayList<>();
			rolesAdmitidos.add(RoleEnum.JEFE_INSPECCIONES);
			rolesAdmitidos.add(RoleEnum.SERVICIO_APOYO);
			rolesAdmitidos.add(RoleEnum.ADMIN);
			if (solicitud.getFechaFinalizacion() == null && rolesAdmitidos.contains(usuarioActual.getRole())) {

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

						String descripcion = DESCRIPCION + solicitud.getInspeccion().getNumero();

						regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.BAJA.name(),
								NOMBRESECCION);

						notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
					}
				}
			}
			else {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_WARN, "Eliminación abortada",
						"Ya ha sido anulada con anterioridad o no tiene permisos para realizar esta acción");
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al eliminar la solicitud, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
		listaSolicitudesPrevia = null;
		listaSolicitudesPrevia = solicitudDocumentacionService
				.buscarSolicitudDocPreviaCriteria(solicitudDocPreviaBusqueda);
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
					StringBuilder asunto = new StringBuilder("Solicitud de documentación previa para la inspección ")
							.append(solicitudDocumentacionPrevia.getInspeccion().getNumero());
					StringBuilder textoAutomatico = new StringBuilder(
							"\r\n \r\nEl plazo del que disponía para enviar la documentación previa conectándose a la aplicación PROGESIN ha sido modificado.")
									.append("\r\n \r\nFecha límite de envío anterior: ")
									.append(sdf.format(backupFechaLimiteEnvio))
									.append("\r\nFecha límite de envío nueva: ")
									.append(sdf.format(solicitudDocumentacionPrevia.getFechaLimiteEnvio()))
									.append("\r\n \r\nMuchas gracias y un saludo.");
					String cuerpo = "Asunto: " + solicitudDocumentacionPrevia.getAsunto() + textoAutomatico;

					correoElectronico.envioCorreo(solicitudDocumentacionPrevia.getCorreoDestinatario(),
							asunto.toString(), cuerpo);
					mensajeCorreoEnviado = ". Se ha comunicado al destinatario de la unidad el cambio de fecha.";
				}
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
						"La solicitud de documentación ha sido modificada con éxito" + mensajeCorreoEnviado);
				listadoDocumentosCargados = gestDocumentacionService
						.findByIdSolicitud(solicitudDocumentacionPrevia.getId());

				String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero();

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
					StringBuilder asunto = new StringBuilder("Solicitud de documentación previa para la inspección ")
							.append(solicitudDocumentacionPrevia.getInspeccion().getNumero());
					StringBuilder textoAutomatico = new StringBuilder(
							"\r\n \r\nPara cumplimentar la solicitud de documentación previa debe conectarse a la aplicación PROGESIN. El enlace de acceso a la aplicación es ")
									.append(applicationBean.getMapaParametros().get("URLPROGESIN")
											.get(solicitudDocumentacionPrevia.getInspeccion().getAmbito().name()))
									.append(", su usuario de acceso es su correo electrónico y la contraseña es ")
									.append(password)
									.append(". \r\n \r\nUna vez enviada la solicitud cumplimentada su usuario quedará inactivo. \r\n \r\n")
									.append("Muchas gracias y un saludo.");
					String cuerpo = "Asunto: " + solicitudDocumentacionPrevia.getAsunto() + textoAutomatico;
					correoElectronico.envioCorreo(solicitudDocumentacionPrevia.getCorreoDestinatario(),
							asunto.toString(), cuerpo);

					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Envío",
							"Se ha enviado con éxito la solicitud de documentación");

					String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero()
							+ " enviada";

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

				String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero()
						+ "finalizada";

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

				StringBuilder asunto = new StringBuilder("Solicitud de documentación previa para la inspección ")
						.append(solicitudDocumentacionPrevia.getInspeccion().getNumero());
				StringBuilder textoAutomatico = new StringBuilder(
						"\r\n \r\nSe ha declarado no conforme la solicitud que usted envió por los motivos que se exponen a continuación:")
								.append("\r\n \r\n").append(motivosNoConforme)
								.append("\r\n \r\nPara solventarlo debe volver a conectarse a la aplicación PROGESIN. El enlace de acceso a la aplicación es ")
								.append(applicationBean.getMapaParametros().get("URLPROGESIN")
										.get(solicitudDocumentacionPrevia.getInspeccion().getAmbito().name()))
								.append("\r\n \r\nEn caso de haber perdido dicha información póngase en contacto con el administrador de la aplicación a través del correo xxxxx@xxxx para solicitar una nueva contraseña.")
								.append("\r\n \r\nUna vez enviada la solicitud cumplimentada su usuario quedará inactivo de nuevo. \r\n \r\n")
								.append("Muchas gracias y un saludo.");
				String cuerpo = "Asunto: " + solicitudDocumentacionPrevia.getAsunto() + textoAutomatico;

				correoElectronico.envioCorreo(solicitudDocumentacionPrevia.getCorreoDestinatario(), asunto.toString(),
						cuerpo);

				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "No Conforme",
						"Declarada no conforme con éxito la solicitud de documentación. El destinatario de la unidad será notificado y reactivado su acceso al sistema");

				String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero()
						+ " declarada no conforme";

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

	/**
	 * Imprime la vista en formato PDF
	 * 
	 * @author EZENTIS
	 */
	public void imprimirPdf() {
		try {
			setPdfFile(pdfGenerator.imprimirSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia,
					listadoDocumentosPrevios));
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error en la generación del PDF");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
	}
}
