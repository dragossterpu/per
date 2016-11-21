package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.ModeloSolicitud;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.RegActividad;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SolicitudDocPreviaEnum;
import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IModeloSolicitudService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.services.gd.IGestDocSolicitudDocumentacionService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.SendSimpleMail;
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

	RegActividad regActividad = new RegActividad();

	private List<User> listaUsuarios;

	private SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda;

	private Date fechaActual = new Date();

	private Date fechaDesde;

	private Date fechaHasta;

	private SolicitudDocPreviaEnum estado;

	private User usuarioCreacion;

	private static final String NOMBRESECCION = "Generación de solicitud documentación";

	private static final String VISTASOLICITUD = "/solicitudesPrevia/vistaSolicitud";

	@Autowired
	IRegActividadService regActividadService;

	@Autowired
	transient INotificacionService notificacionService;

	@Autowired
	transient IModeloSolicitudService modeloSolicitudService;

	private transient UploadedFile ficheroNuevo;

	transient List<ModeloSolicitud> listadoModelosSolicitud;

	String nombreSolicitud;

	transient List<SolicitudDocumentacionPrevia> listaSolicitudesPrevia;

	private List<Boolean> list;

	@Autowired
	transient ISolicitudDocumentacionService solicitudDocumentacionService;

	@Autowired
	transient ITipoDocumentacionService tipoDocumentacionService;

	@Autowired
	private IInspeccionesRepository inspeccionRepository;

	SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();

	Inspeccion inspeccion = new Inspeccion();

	private boolean skip;

	private transient StreamedContent file;

	private String fechaAntes;

	private String fechaLimite;

	private String fechaEmision;

	private String cuerpoEstado;

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
	private transient PasswordEncoder passwordEncoder;

	/**
	 * Método que crea una solicitud de documentación en base a un modelo y los datos introducidos en el formulario de
	 * la vista crearSolicitud.
	 * 
	 * @author EZENTIS
	 * @return vista modelosSolicitud
	 */
	public String crearSolicitud() {
		this.fechaAntes = null;
		this.fechaLimite = null;

		try {
			datosApoyo();
			dirigido();

			solicitudDocumentacionPrevia.setNombreSolicitud(nombreSolicitud);
			// solicitudDocumentacionPrevia.setFechaAlta(new Date());
			// solicitudDocumentacionPrevia
			// .setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());
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
			saveReg(descripcion, EstadoRegActividadEnum.ALTA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());

			// Guardamos la notificacion en bbdd
			saveNotificacion(descripcion, EstadoRegActividadEnum.ALTA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());
			this.solicitudDocumentacionPrevia = null;
		}
		catch (Exception e) {
			// Guardamos los posibles errores en bbdd
			altaRegActivError(e);
		}
		return "solicitudesPrevia/modelosSolicitud";

	}

	/**
	 * Método que muestra el mensaje para quien está dirigida la solicitud.
	 * 
	 * @author EZENTIS
	 * @see #crearSolicitud()
	 */
	private void dirigido() {
		if ("GC".trim().equals(cuerpoEstado)) {
			solicitudDocumentacionPrevia
					.setMensajeCorreo("(cuenta corporativa guardiacivil.org o guardiacivil.es, no cuenta personal).");
		}
		else if ("PN".trim().equals(cuerpoEstado)) {
			solicitudDocumentacionPrevia.setMensajeCorreo("(cuenta corporativa policia.es, no cuenta personal).");
		}
		else {
			solicitudDocumentacionPrevia.setMensajeCorreo("(no cuenta personal).");
		}
	}

	/**
	 * Método para obtener los datos del jefe del equipo de apoyo.
	 * 
	 * @author EZENTIS
	 * @see #crearSolicitud()
	 */
	private void datosApoyo() {
		if (solicitudDocumentacionPrevia.getApoyoCorreo() == null
				|| "".equals(solicitudDocumentacionPrevia.getApoyoCorreo().trim())) {
			solicitudDocumentacionPrevia.setApoyoCorreo("mmayo@interior.es");
		}
		if (solicitudDocumentacionPrevia.getApoyoNombre() == null
				|| "".equals(solicitudDocumentacionPrevia.getApoyoNombre().trim())) {
			solicitudDocumentacionPrevia.setApoyoNombre("Manuel Mayo Rodríguez");
		}
		if (solicitudDocumentacionPrevia.getApoyoPuesto() == null
				|| "".equals(solicitudDocumentacionPrevia.getApoyoPuesto().trim())) {
			solicitudDocumentacionPrevia.setApoyoPuesto("Inspector Auditor, Jefe del Servicio de Apoyo");
		}
		if (solicitudDocumentacionPrevia.getApoyoTelefono() == null
				|| "".equals(solicitudDocumentacionPrevia.getApoyoTelefono().trim())) {
			solicitudDocumentacionPrevia.setApoyoTelefono("91.537.25.41");
		}
	}

	/**
	 * Método que permite dar de alta los documentos seleccionados al crear una solicitud de documentación. Colección de
	 * documentos de entre los disponibles en TipoDocumentación que se asignan a la solicitud.
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
	 * Método que permite visualizar una solicitud creada, muestra su información y dependiendo del estado en que se
	 * encuentre permite pasar al siguiente estado si se tiene el rol adecuado. Posibles estados: alta, validada por
	 * apoyo, enviada, cumplimentada y finalizada
	 * 
	 * @author EZENTIS
	 * @param solicitud
	 * @return vista vistaSolicitud
	 */
	public String visualizarSolicitud(SolicitudDocumentacionPrevia solicitud) {
		try {

			listadoDocumentosCargados = gestDocumentacionService.findByIdSolicitud(solicitud.getId());
			listadoDocumentosPrevios = tipoDocumentacionService.findByIdSolicitud(solicitud.getId());
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			fechaEmision = formatter.format(solicitud.getFechaAlta());
			fechaLimite = formatter.format(solicitud.getFechaLimiteCumplimentar());
			solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
			solicitudDocumentacionPrevia = solicitud;
			return "/solicitudesPrevia/vistaSolicitud";
		}
		catch (Exception e) {
			altaRegActivError(e);
			return null;
		}

	}

	/**
	 * Método que permite al equipo de apoyo validar la solicitud de documentación
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
	 * Método que carga el formulario para crear una solicitud basada en un modelo.
	 * 
	 * @author EZENTIS
	 * @param modeloSolicitud
	 * @return vista crearSolicitud
	 */
	public String getFormularioCrearSolicitud(ModeloSolicitud modeloSolicitud) {
		this.documentosSelecionados = null;
		this.cuerpoEstado = null;
		solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
		nombreSolicitud = modeloSolicitud.getDescripcion();
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
		listaUsuarios = userService.findByfechaBajaIsNullAndRoleNotIn(RoleEnum.getRolesProv());
		solicitudDocPreviaBusqueda = new SolicitudDocPreviaBusqueda();
		listadoDocumentosCargados = new ArrayList<>();
		nombreSolicitud = null;
		cuerpoEstado = null;
		listaSolicitudesPrevia = new ArrayList<>();
		listadoModelosSolicitud = modeloSolicitudService.findAll();
		solicitudDocumentacionPrevia.setInspeccion(inspeccion);
	}

	/**
	 * Método que permite descargar el fichero seleccionado. Utilizado tanto para modelos de solicitud como documentos
	 * previos subidos por el interlocutor de una unidad al cumplimentar una solicitud.
	 * 
	 * @author EZENTIS
	 * @param idDocumento
	 */
	public void descargarFichero(Long idDocumento) {
		try {
			file = documentoService.descargaDocumento(idDocumento);
		}
		catch (Exception e) {
			altaRegActivError(e);
		}
	}

	/**
	 * Método que permite el webFlow
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
		list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
	}

	// ************* Alta mensajes de notificacion, regActividad y alertas Progesin ********************
	/**
	 * @author EZENTIS
	 * @param descripcion
	 * @param tipoReg
	 * @param username
	 * @see #crearSolicitud()
	 */
	private void saveReg(String descripcion, String tipoReg, String username) {
		regActividad.setTipoRegActividad(tipoReg);
		regActividad.setUsernameRegActividad(username);
		regActividad.setFechaAlta(new Date());
		regActividad.setNombreSeccion(NOMBRESECCION);
		regActividad.setDescripcion(descripcion);
		regActividadService.save(regActividad);
	}

	/**
	 * @author EZENTIS
	 * @param descripcion
	 * @param tipoNotificacion
	 * @param username
	 * @see #crearSolicitud()
	 */
	private void saveNotificacion(String descripcion, String tipoNotificacion, String username) {
		Notificacion notificacion = new Notificacion();
		notificacion.setTipoNotificacion(tipoNotificacion);
		notificacion.setUsernameNotificacion(username);
		notificacion.setNombreSeccion(NOMBRESECCION);
		notificacion.setFechaAlta(new Date());
		notificacion.setDescripcion(descripcion);
		notificacionService.save(notificacion);
	}

	/**
	 * Método que registra los errores generados por otros métodos
	 * 
	 * @author EZENTIS
	 * @param e
	 * @see #altaModeloSolicitud
	 * @see #descargarFichero(ModeloSolicitud)
	 * @see #crearSolicitud()
	 * @see #insertar()
	 * @see #modificarSolicitud()
	 * @see #visualizarSolicitud(SolicitudDocumentacionPrevia)
	 */
	private void altaRegActivError(Exception e) {
		regActividad.setTipoRegActividad(EstadoRegActividadEnum.ERROR.name());
		String message = Utilities.messageError(e);
		regActividad.setFechaAlta(new Date());
		regActividad.setNombreSeccion(NOMBRESECCION);
		regActividad.setUsernameRegActividad(SecurityContextHolder.getContext().getAuthentication().getName());
		regActividad.setDescripcion(message);
		regActividadService.save(regActividad);
	}
	// ************* Alta mensajes de notificacion, regActividad y alertas Progesin END********************

	/**
	 * Método que elimina un modelo de solicitud de documentación previa
	 * 
	 * @author EZENTIS
	 * @param modeloSolicitud
	 */
	public void eliminarModeloSolicitud(ModeloSolicitud modeloSolicitud) {
		documentoService.delete(modeloSolicitud.getIdDocumento());
		modeloSolicitudService.delete(modeloSolicitud.getId());
		this.listadoModelosSolicitud = null;
		listadoModelosSolicitud = modeloSolicitudService.findAll();
	}

	/**
	 * Método que elimina la solicitud de documentación previa
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
	 * Método que da de alta un nuevo modelo de solicitud de documentación previa
	 * 
	 * @author EZENTIS
	 */
	public void altaModeloSolicitud() {

		if (ficheroNuevo != null && !"".equals(ficheroNuevo.getFileName())) {
			try {
				ModeloSolicitud modeloSolicitud = new ModeloSolicitud();
				modeloSolicitud.setCodigo("codigo");
				modeloSolicitud.setDescripcion(ficheroNuevo.getFileName()
						.substring(0, ficheroNuevo.getFileName().lastIndexOf('.')).toUpperCase());
				modeloSolicitud.setNombreFichero(ficheroNuevo.getFileName());
				modeloSolicitud.setExtension(Utilities.getExtensionTipoContenido(ficheroNuevo.getContentType()));

				if (modeloSolicitudService.transaccSaveGuardaDoc(modeloSolicitud, ficheroNuevo) != null) {
					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
							"El modelo de solicitud de documentación ha sido creada con éxito");
				}
			}
			catch (Exception e) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
						"Se ha producido un error al dar de alta el modelo de solicitud de documentación, inténtelo de nuevo más tarde");
				altaRegActivError(e);
			}

		}
		else {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Alta abortada",
					"No se ha seleccionado un archivo al dar de alta el modelo de solicitud de documentación");
		}
		listadoModelosSolicitud = modeloSolicitudService.findAll();

	}

	/**
	 * Método que permite la edición en caliente de un registro
	 * 
	 * @author EZENTIS
	 * @param event evento disparado al pulsar el botón editar
	 */
	public void onRowEdit(RowEditEvent event) {
		ModeloSolicitud modeloSolicitud = (ModeloSolicitud) event.getObject();
		modeloSolicitudService.save(modeloSolicitud);
		FacesMessage msg = new FacesMessage("Modelo de solicitud de documentación modificado",
				modeloSolicitud.getDescripcion());
		FacesContext.getCurrentInstance().addMessage("msgs", msg);
	}

	/**
	 * Método que anula la edición en caliente de un registro
	 * 
	 * @author EZENTIS
	 * @param event evento disparado al pulsar el botón cancelar edición
	 */
	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Modificación cancelada",
				((ModeloSolicitud) event.getObject()).getDescripcion());
		FacesContext.getCurrentInstance().addMessage("msgs", msg);
	}

	/**
	 * Método que modifica los datos de la solicitud de documentación previa
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
			altaRegActivError(e);
		}

	}

	/**
	 * Método que permite al jefe de equipo de inspecciones validar la solicitud de documentación y enviarla y dar de
	 * alta un usuario provisional para que algún miembro de la unidad a inspeccionar la cumplimente.
	 * 
	 * @author EZENTIS
	 * @return vista vistaSolicitud
	 */
	public String enviarSolicitud() {

		String password = Utilities.getPassword();
		User usuarioProv = userProvisionalSolicitud(password);

		solicitudDocumentacionPrevia.setFechaEnvio(new Date());
		solicitudDocumentacionPrevia.setUsernameEnvio(SecurityContextHolder.getContext().getAuthentication().getName());

		if (solicitudDocumentacionService.transaccSaveCreaUsuarioProv(solicitudDocumentacionPrevia, usuarioProv)) {
			System.out.println("Password usuario provisional  : " + password);

			String asunto = "Usuario provisional solicitud documentación";
			String correoEnvio = "dragossterpu@gmail.com";
			String nombre = "Prueba";
			String respuesta = "El password es :" + password;
			try {
				SendSimpleMail.sendMail(asunto, correoEnvio, nombre, respuesta);
			}
			catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Envío",
					"Se ha enviado con éxito la solicitud de documentación");
			FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
			context.execute(dialogMessage);
		}
		return VISTASOLICITUD;
	}

	/**
	 * Método que crea un usuario provisional al enviarse una solicitud de documentación previa, de forma que ésta pueda
	 * ser cumplimentada. Sus credenciales son el correo electrónico como username y una contraseña generada
	 * automáticamente. Dado que su rol es PROV_SOLICITUD, no se le permite alterar ningún dato de su perfil ni acceder
	 * a otra acción que no sea la de cumplimentar la solicitud.
	 * 
	 * @author EZENTIS
	 * @see #enviarSolicitud()
	 */
	private User userProvisionalSolicitud(String password) {
		User user = new User();
		user.setFechaAlta(new Date());
		user.setUsername(solicitudDocumentacionPrevia.getCorreoDestiantario());
		user.setPassword(passwordEncoder.encode(password));
		user.setEstado(EstadoEnum.ACTIVO);
		user.setUsernameAlta(system);
		user.setNombre("Provisional");
		user.setApellido1("Solicitud documentación");
		user.setDocIndentidad(system);
		user.setCorreo(solicitudDocumentacionPrevia.getCorreoDestiantario());
		user.setRole(RoleEnum.PROV_SOLICITUD);
		user.setNumIdentificacion(system);
		user.setEnvioNotificacion("NO");
		return user;
	}

	/**
	 * Método que permite al jefe de inspecciones finalizar una solicitud de documentación ya cumplimentada, después de
	 * revisar la documentación adjuntada por la unidad que se va a inspeccionar. Adicionalmente elimina el usuario
	 * provisinal que se usó para llevarla a cabo puesto que ya no se va a usar más.
	 * 
	 * @author EZENTIS
	 * @return vista vistaSolicitud
	 */
	public String finalizarSolicitud() {
		solicitudDocumentacionPrevia.setFechaFinalizacion(new Date());
		solicitudDocumentacionPrevia
				.setUsuarioFinalizacion(SecurityContextHolder.getContext().getAuthentication().getName());
		String usuarioProv = solicitudDocumentacionPrevia.getCorreoDestiantario();
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
	 * Método que devuelve al formulario de búsqueda de solicitudes de documentación previa a su estado inicial y borra
	 * los resultados de búsquedas anteriores.
	 * 
	 * @author EZENTIS
	 */
	public void limpiarBusqueda() {
		solicitudDocPreviaBusqueda.resetValues();
		listaSolicitudesPrevia = null;
	}

	/**
	 * Método que busca las solicitudes de documentación previa según los filtros introducidos en el formulario de
	 * búsqueda.
	 * 
	 * @author EZENTIS
	 */
	public void buscarSolicitudDocPrevia() {
		listaSolicitudesPrevia = solicitudDocumentacionService
				.buscarSolicitudDocPreviaCriteria(solicitudDocPreviaBusqueda);
	}

	/**
	 * Método que comprueba si el usuario logueado es el jefe del equipo encargado de la solicitud de inspeccion.
	 * 
	 * @author EZENTIS
	 * @return result booleano
	 */
	public boolean esJefeEquipoInspeccion() {
		boolean result = false;

		// TODO Si -> User.role = RoleEnum.EQUIPO_INSPECCIONES
		// solicituddocumentacionprevia.inspeccion -> inspeccion(numero)
		// inspeccion(id_equipo) -> miembro(id_equipo)
		// miembro(posicion) -> RolEquipoEnum.JEFE
		//
		// ¿User.id = miembro.id?
		//

		return result;
	}

	/**
	 * Método que comprueba si la solicitud de documentación ya ha sido validada por apoyo. En caso de ser así se
	 * bloquea la modificación de solicitud.
	 * 
	 * @author EZENTIS
	 * @param solicitud
	 * @return result booleano
	 */
	public boolean estaValidadaApoyo(SolicitudDocumentacionPrevia solicitud) {
		return solicitud.getFechaValidApoyo() != null;
	}

	/**
	 * Método que devuelve una lista con las inspecciones cuyo número contienen alguno de los caracteres pasado como
	 * parámetro. Se usa en el formulario de envío para el autocompletado.
	 * 
	 * @param numeroInspeccion Número de inspección que teclea el usuario en los formularios de creación y modificación
	 * @return Devuelve la lista de inspecciones que contienen algún caracter coincidente con el número introducido
	 */
	public List<Inspeccion> autocompletarInspeccion(String numeroInspeccion) {
		return inspeccionRepository.findByNumeroLike("%" + numeroInspeccion + "%");
	}

}
