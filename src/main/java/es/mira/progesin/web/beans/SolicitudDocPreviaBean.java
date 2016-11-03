package es.mira.progesin.web.beans;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.DatosJasper;
import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.PreEnvioCuest;
import es.mira.progesin.persistence.entities.RegActividad;
import es.mira.progesin.persistence.entities.SolicitudDocumentacion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SolicitudDocPreviaEnum;
import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.services.gd.IGestDocSolicitudDocumentacionService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.DescargasHelper;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.SendSimpleMail;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

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

	@Autowired
	IRegActividadService regActividadService;

	@Autowired
	transient INotificacionService notificacionService;

	@Autowired
	transient IModeloCuestionarioService modeloCuestionarioService;

	private transient UploadedFile ficheroNuevo;

	transient List<PreEnvioCuest> listadoPreEnvioCuestionarios;

	String nombreCuestionarioPrevio;

	transient List<SolicitudDocumentacionPrevia> listaSolicitudesPrevia;

	transient List<SolicitudDocumentacionPrevia> listaSolicitudesFinalizadas;

	private List<Boolean> list;

	@Autowired
	transient ISolicitudDocumentacionService solicitudDocumentacionService;

	@Autowired
	transient ITipoDocumentacionService tipoDocumentacionService;

	SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();

	private boolean skip;

	private Integer anio;

	private transient StreamedContent file;

	private transient DatosJasper model;

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

	// Url de la plantilla jasper
	private static final String RUTA_JASPER = "jasper/gcZonaPluriprovincial.jasper";

	@Autowired
	transient IUserService userService;

	@Autowired
	private transient PasswordEncoder passwordEncoder;

	/**
	 * @param
	 * @comment Metodo que crea una solicitus de documentacion
	 * @author EZENTIS STAD
	 * @return vista
	 */
	public String creaCuestionario() {
		return "/solicitudesPrevia/preenvio";

	}

	/**
	 * @param
	 * @comment Metodo que crea una solicitus de documentacion
	 * @author EZENTIS STAD
	 * @return vista
	 */
	public String execute() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(true);
		model.setNombre("GC_ZONA_PLURI_PROVINCIAL");
		model.setUrl(RUTA_JASPER);
		try {
			SolicitudDocumentacion documento = new SolicitudDocumentacion();
			DescargasHelper.preparaDescargaJasper(model, session, documento);
			String descripcion = "Solicitud documentación cuestionario. Usuario creación : "
					+ SecurityContextHolder.getContext().getAuthentication().getName();
			solicitudDocumentacionService.save(documento);
			// Guardamos la actividad en bbdd
			saveReg(descripcion, EstadoRegActividadEnum.ALTA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());

			// Guardamos la notificacion en bbdd
			saveNotificacion(descripcion, EstadoRegActividadEnum.ALTA.name(),
					SecurityContextHolder.getContext().getAuthentication().getName());

			model.resetValues();
		}
		catch (Exception e) {
			// Guardamos loe posibles errores en bbdd
			altaRegActivError(e);
		}
		return null;

	}

	/**
	 * @param
	 * @comment Metodo que crea una solicitus de documentacion
	 * @author EZENTIS STAD
	 * @return vista
	 */
	public String executeSolicitud() {
		this.fechaAntes = null;
		this.fechaLimite = null;

		try {
			datosApoyo();
			dirigido();

			solicitudDocumentacionPrevia.setIdentificadorTrimestre(
					solicitudDocumentacionPrevia.getIdentificadorTrimestre() + " del año " + anio);
			solicitudDocumentacionPrevia.setNombreCuestionarioPrevio(nombreCuestionarioPrevio);
			solicitudDocumentacionPrevia.setFechaAlta(new Date());
			solicitudDocumentacionPrevia
					.setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());
			if (solicitudDocumentacionService.savePrevia(solicitudDocumentacionPrevia) != null) {
				RequestContext context = RequestContext.getCurrentInstance();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta",
						"La solicitud de documentación ha sido creada con éxito");
				FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
				context.execute(dialogMessage);
			}
			altaDocumentos();
			String descripcion = "Solicitud documentación cuestionario. Usuario creación : "
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
			// Guardamos loe posibles errores en bbdd
			altaRegActivError(e);
		}
		return "solicitudesPrevia/preEnvioCuestionarios";

	}

	/**
	 * @param
	 * @see executeSolicitud()
	 * @comment Metodo que muestra el mensaje para quien es dirigida la solicitud
	 * @author EZENTIS STAD
	 * @return
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
	 * @param
	 * @see executeSolicitud()
	 * @comment Metodo para obtener los datos del jefe del equipo de apoyo
	 * @author EZENTIS STAD
	 * @return
	 */
	private void datosApoyo() {
		if (solicitudDocumentacionPrevia.getApoyoCorreo() == null
				|| solicitudDocumentacionPrevia.getApoyoCorreo().trim().equals("")) {
			solicitudDocumentacionPrevia.setApoyoCorreo("mmayo@interior.es");
		}
		if (solicitudDocumentacionPrevia.getApoyoNombre() == null
				|| solicitudDocumentacionPrevia.getApoyoNombre().trim().equals("")) {
			solicitudDocumentacionPrevia.setApoyoNombre("Manuel Mayo Rodríguez");
		}
		if (solicitudDocumentacionPrevia.getApoyoPuesto() == null
				|| solicitudDocumentacionPrevia.getApoyoPuesto().trim().equals("")) {
			solicitudDocumentacionPrevia.setApoyoPuesto("Inspector Auditor, Jefe del Servicio de Apoyo");
		}
		if (solicitudDocumentacionPrevia.getApoyoTelefono() == null
				|| solicitudDocumentacionPrevia.getApoyoTelefono().trim().equals("")) {
			solicitudDocumentacionPrevia.setApoyoTelefono("91.537.25.41");
		}
	}

	/**
	 * @param
	 * @see executeSolicitud() //Indica que este metodo esta componente del metodo indicado
	 * @comment Metodo que permite dar de alta los documentos selecionados
	 * @author EZENTIS STAD
	 * @return
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
	 * @param
	 * @comment Metodo que obtiene la lista de los solicitudes creadas
	 * @author EZENTIS STAD
	 * @return vista listaSolicitudes
	 */
	public String getListaSolicitudes() {
		this.documentosSelecionados = null;
		this.listaSolicitudesPrevia = null;
		anio = null;
		listaSolicitudesPrevia = solicitudDocumentacionService.findAllPrevia();
		return "/solicitudesPrevia/listaSolicitudes";
	}

	/**
	 * @param
	 * @comment Metodo que obtiene la lista de los solicitudes pendientes de enviar
	 * @author EZENTIS STAD
	 * @return vista listaSolicitudesPrevia
	 */
	public String getListaSolicitudesPendientes() {
		this.documentosSelecionados = null;
		this.listaSolicitudesPrevia = null;
		anio = null;
		listaSolicitudesPrevia = solicitudDocumentacionService.findAllPreviaEnvio();
		return "/solicitudesPrevia/listaSolicitudesPrevia";
	}

	/**
	 * @comment Pasa los datos dela solicitud que queremos modificar al formulario de modificación para que cambien los
	 * valores que quieran
	 * @param solicitud recuperado del formulario
	 * @author EZENTIS STAD
	 * @return vista modificarSolicitud
	 */
	public String getFormModificarSolicitud(SolicitudDocumentacionPrevia solicitud) {
		this.solicitudDocumentacionPrevia = solicitud;
		return "/solicitudesPrevia/modificarSolicitud";
	}

	/**
	 * @param
	 * @comment Metodo que obtiene la lista de los solicitudes finalizadas
	 * @author EZENTIS STAD
	 * @return vista listaSolicitudesFinalizadas
	 */
	public String getListaSolicitudesFinalizadas() {
		listaSolicitudesPrevia = new ArrayList<>();
		listaSolicitudesPrevia = solicitudDocumentacionService.findAllFinalizadas();
		return "/solicitudesPrevia/listaSolicitudesFinalizadas";
	}

	/**
	 * @param
	 * @comment Metodo que permite visualizar una solicitud creada
	 * @author EZENTIS STAD
	 * @return vista visualizarSolicitud
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
	 * @param
	 * @comment Metodo que permite al equipo de apoyo validar la solicitud de documentacion
	 * @author EZENTIS STAD
	 * @return vista previo
	 */
	public String validacionApoyo() {
		solicitudDocumentacionPrevia.setFechaValidApoyo(new Date());
		solicitudDocumentacionPrevia
				.setUsernameValidApoyo(SecurityContextHolder.getContext().getAuthentication().getName());
		if (solicitudDocumentacionService.savePrevia(solicitudDocumentacionPrevia) != null) {
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta",
					"Se ha validado con éxito la solicitud de documentación");
			FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
			context.execute(dialogMessage);
		}
		return "/solicitudesPrevia/previo";
	}

	/**
	 * @param cuestionario
	 * @comment Metodo que envia una solicitud de documentacion
	 * @author EZENTIS STAD
	 * @return vista previo
	 */
	public String enviarPreCuestionario(PreEnvioCuest cuestionario) {
		this.anio = null;
		this.documentosSelecionados = null;
		this.cuerpoEstado = null;
		solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
		nombreCuestionarioPrevio = cuestionario.getDescripcion();
		listadoDocumentos = tipoDocumentacionService.findAll();
		return "/solicitudesPrevia/previo";
	}

	/**
	 * @param cuestionario
	 * @comment Metodo que envia una solicitud de documentacion
	 * @author EZENTIS STAD
	 */
	public String enviarPreCuestionarioOld(PreEnvioCuest cuestionario) {

		return "/solicitudesPrevia/preenvio";
	}

	/**
	 * @param cuestionario
	 * @comment Metodo que limpia los valores
	 * @author EZENTIS STAD
	 */
	public String limpiar() {
		model.resetValues();
		return "/solicitudesPrevia/preenvio";
	}

	/**
	 * @param
	 * @comment PostConstruct
	 * @author EZENTIS STAD
	 * @return
	 */
	@PostConstruct
	public void init() {
		solicitudDocPreviaBusqueda = new SolicitudDocPreviaBusqueda();
		listadoDocumentosCargados = new ArrayList<>();
		nombreCuestionarioPrevio = null;
		anio = null;
		cuerpoEstado = null;
		listaSolicitudesPrevia = new ArrayList<>();
		model = new DatosJasper();
		// insertar();
		listadoPreEnvioCuestionarios = modeloCuestionarioService.findAllPre();

	}

	/**
	 * @param cuestionario
	 * @comment Metodo que permite insertar ficheros desde una ruta local
	 * @author EZENTIS STAD
	 */
	public void insertar() {
		try {

			File directory = new File("C:\\Users\\Admin\\Desktop\\Documentación IPSS\\preenvioCuestionarios");

			File[] listaFicheros = directory.listFiles();
			for (int i = 0; i < listaFicheros.length; i++) {
				File fichero = listaFicheros[i];

				// Obtiene el contenido del fichero en []bytes
				byte[] data = Files.readAllBytes(fichero.toPath());
				PreEnvioCuest cuestionario = new PreEnvioCuest();
				cuestionario.setCodigo("codigo");
				cuestionario.setDescripcion(
						fichero.getName().substring(0, fichero.getName().lastIndexOf('.')).toUpperCase());
				cuestionario.setNombreFichero(fichero.getName());
				cuestionario.setExtension(
						fichero.getName().substring(fichero.getName().lastIndexOf('.') + 1).toLowerCase());
				// Blob fichero = Hibernate.getLobCreator(sessionFactory.openSession()).createBlob(data);
				cuestionario.setFichero(data);
				modeloCuestionarioService.savePre(cuestionario);
			}
		}
		catch (Exception e) {
			altaRegActivError(e);
		}
	}

	/**
	 * @param cuestionario
	 * @comment Metodo que permite descargar el fichero selecionado
	 * @author EZENTIS STAD
	 */
	public void descargarFichero(PreEnvioCuest cuestionario) {
		try {
			InputStream stream = new ByteArrayInputStream(cuestionario.getFichero());
			String contentType = "application/msword";
			if ("pdf".equals(cuestionario.getExtension())) {
				contentType = "application/pdf";
			}
			else if (cuestionario.getExtension().startsWith("xls")) {
				contentType = "application/x-msexcel";
			}
			file = new DefaultStreamedContent(stream, contentType, cuestionario.getNombreFichero());
		}
		catch (Exception e) {
			altaRegActivError(e);
		}
	}

	/**
	 * @param event
	 * @comment Metodo que permite el webFlow
	 * @author EZENTIS STAD
	 */
	public String onFlowProcess(FlowEvent event) {
		// cleanParam(event);
		if (skip) {
			skip = false; // reset in case user goes back
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
	 * @param descripcion
	 * @param tipoReg
	 * @param username
	 * @see todos los metodos que guarda registro de actividad
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
	 * @param descripcion
	 * @param tipoReg
	 * @param username
	 * @see todos los metodos que guarda notificaciones
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
	 * @param e
	 * @see todos los metodos que guarda errores
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
	 * @param cuestionario
	 * @comment Metodo que elimina el cuestionario
	 * @author EZENTIS STAD
	 */
	public void eliminarPreEnvioCuestionario(PreEnvioCuest cuestionario) {
		modeloCuestionarioService.delete(cuestionario.getId());
		this.listadoPreEnvioCuestionarios = null;
		listadoPreEnvioCuestionarios = modeloCuestionarioService.findAllPre();
	}

	/**
	 * @param cuestionario
	 * @comment Metodo que elimina la solicitud previa de documentacion
	 * @author EZENTIS STAD
	 */
	public void eliminarSolicitudCuestionario(SolicitudDocumentacionPrevia solicitudDocumentacionPrevia) {
		solicitudDocumentacionService.delete(solicitudDocumentacionPrevia.getId());
		this.listaSolicitudesPrevia = null;
		listaSolicitudesPrevia = solicitudDocumentacionService.findAllPrevia();
	}

	/**
	 * @param
	 * @comment Metodo que da de alta el cuestionario
	 * @author EZENTIS STAD
	 */
	public void altaPreEnvioCuestionario() {
		if (ficheroNuevo != null && !"".equals(ficheroNuevo.getFileName().trim())) {
			try {
				PreEnvioCuest cuestionario = new PreEnvioCuest();
				cuestionario.setCodigo("codigo");
				cuestionario.setFichero(ficheroNuevo.getContents());
				cuestionario.setDescripcion(ficheroNuevo.getFileName()
						.substring(0, ficheroNuevo.getFileName().lastIndexOf('.')).toUpperCase());
				cuestionario.setNombreFichero(ficheroNuevo.getFileName());
				cuestionario.setExtension(ficheroNuevo.getFileName()
						.substring(ficheroNuevo.getFileName().lastIndexOf('.') + 1).toLowerCase());
				// Obtiene el contenido del fichero en []bytes
				cuestionario.setFichero(ficheroNuevo.getContents());

				if (modeloCuestionarioService.savePreAlta(cuestionario) != null) {
					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
							"La solicitud de cuestionario ha sido creada con éxito");
				}
			}
			catch (Exception e) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
						"Se ha producido un error al dar de alta la solicitud de cuestionario, inténtelo de nuevo más tarde");
				altaRegActivError(e);
			}

		}
		else {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Alta abortada",
					"No se ha seleccionado un archivo al dar de alta la solicitud de cuestionario");
		}
		listadoPreEnvioCuestionarios = modeloCuestionarioService.findAllPre();

	}

	/**
	 * @param event
	 * @comment Metodo que permite editar en caliente un registro
	 * @author EZENTIS STAD
	 */
	public void onRowEdit(RowEditEvent event) {
		PreEnvioCuest cuestionario = (PreEnvioCuest) event.getObject();
		modeloCuestionarioService.savePreAlta(cuestionario);
		FacesMessage msg = new FacesMessage("Solicitud de cuestionario modificada", cuestionario.getDescripcion());
		FacesContext.getCurrentInstance().addMessage("msgs", msg);
	}

	/**
	 * @param
	 * @comment Metodo que anula la edicion en caliente un registro
	 * @author EZENTIS STAD
	 */
	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Modificación cancelada",
				((PreEnvioCuest) event.getObject()).getDescripcion());
		FacesContext.getCurrentInstance().addMessage("msgs", msg);
	}

	/**
	 * @param
	 * @comment Metodo que modifica los datos de la solicitud previa de cuestionario
	 * @author EZENTIS STAD
	 */
	public void modificarSolicitud() {
		try {

			if (solicitudDocumentacionService.savePrevia(solicitudDocumentacionPrevia) != null) {
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
			// Guardamos loe posibles errores en bbdd
			altaRegActivError(e);
		}

	}

	/**
	 * @param
	 * @comment Metodo que permite al jefe de equipo de inspeciones validar la solicitud de documentacion y enviarla
	 * @author EZENTIS STAD
	 * @return vista previo
	 */
	public String enviarSolicitud() {
		solicitudDocumentacionPrevia.setFechaEnvio(new Date());
		solicitudDocumentacionPrevia.setUsernameEnvio(SecurityContextHolder.getContext().getAuthentication().getName());
		if (solicitudDocumentacionService.savePrevia(solicitudDocumentacionPrevia) != null) {
			RequestContext context = RequestContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta",
					"Se ha validado con éxito la solicitud de documentación");
			FacesContext.getCurrentInstance().addMessage("dialogMessage", message);
			context.execute(dialogMessage);
			userProvisionalSolicitud();
		}
		return "/solicitudesPrevia/previo";
	}

	/**
	 * @param
	 * @param
	 * @param
	 * @see todos los metodos que guarda registro de actividad
	 */
	private void userProvisionalSolicitud() {
		User user = new User();
		user.setFechaAlta(new Date());
		user.setUsername(solicitudDocumentacionPrevia.getCorreoDestiantario());
		String password = Utilities.getPassword();
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
		userService.save(user);
	}

	/**
	 * @param
	 * @comment Metodo que limpia y presenta el formulario de búsqueda de solicitudes previas de cuestionario
	 * @author EZENTIS GR
	 */
	public String getFormularioBusquedaSolicitudesDocPrevia() {
		solicitudDocPreviaBusqueda.resetValues();
		listaUsuarios = userService.findByfechaBajaIsNull();
		return "/solicitudesPrevia/busquedaSolicitudesDocPrevia";

	}

	/**
	 * @param
	 * @comment Metodo que busca las solicitudes previas de cuestionario según los filtros introducidos en el formulario
	 * de búsqueda
	 * @author EZENTIS GR
	 */
	public void buscarSolicitudDocPrevia() {
		List<SolicitudDocumentacionPrevia> listaSolicitudesDocPrevia = solicitudDocumentacionService
				.buscarSolicitudDocPreviaCriteria(solicitudDocPreviaBusqueda);
		solicitudDocPreviaBusqueda.setListaSolicitudesDocPrevia(listaSolicitudesDocPrevia);
	}

}
