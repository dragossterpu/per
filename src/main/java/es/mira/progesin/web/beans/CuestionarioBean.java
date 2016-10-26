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
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.DatosJasper;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.PreEnvioCuest;
import es.mira.progesin.persistence.entities.RegActividad;
import es.mira.progesin.persistence.entities.SolicitudDocumentacion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.util.DescargasHelper;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("cuestionarioBean")
@SessionScoped

public class CuestionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	RegActividad regActividad = new RegActividad();

	private final String NOMBRESECCION = "Generación de plantilla jasper";

	@Autowired
	IRegActividadService regActividadService;

	@Autowired
	INotificacionService notificacionService;

	@Autowired
	IModeloCuestionarioService modeloCuestionarioService;

	List<PreEnvioCuest> listadoPreEnvioCuestionarios;

	String nombreCuestionarioPrevio;

	List<SolicitudDocumentacionPrevia> listaSolicitudesPrevia;

	private List<Boolean> list;

	@Autowired
	ISolicitudDocumentacionService solicitudDocumentacionService;

	SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();

	private boolean skip;

	private Integer anio;

	private StreamedContent file;

	private DatosJasper model;

	private String fechaAntes;

	private String fechaLimite;

	private String fechaEmision;

	List<User> listadoDocumentos = new ArrayList<User>();

	private List<User> documentosSelecionados;

	// Url de la plantilla jasper
	private static final String RUTA_JASPER = "jasper/gcZonaPluriprovincial.jasper";

	public String creaCuestionario() {

		return "/cuestionarios/preenvio";

	}

	public String execute() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(true);
		DatosJasper datosjasper = new DatosJasper();
		datosjasper = model;
		datosjasper.setNombre("GC_ZONA_PLURI_PROVINCIAL");
		datosjasper.setUrl(RUTA_JASPER);
		try {
			SolicitudDocumentacion documento = new SolicitudDocumentacion();
			DescargasHelper.preparaDescargaJasper(datosjasper, session, documento);
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

	public String executeSolicitud() {
		this.fechaAntes = null;
		this.fechaLimite = null;
		try {
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
				context.execute("PF('dialogMessage').show()");
			}

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
		return "/cuestionarios/previo";

	}

	public String getListaSolicitudes() {
		this.listaSolicitudesPrevia = null;
		listaSolicitudesPrevia = solicitudDocumentacionService.findAllPrevia();
		return "/cuestionarios/listaSolicitudes";
	}

	public String visualizarSolicitud(SolicitudDocumentacionPrevia solicitud) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		fechaEmision = formatter.format(solicitud.getFechaAlta());
		fechaAntes = formatter.format(solicitud.getFechaAntes());
		fechaLimite = formatter.format(solicitud.getFechaLimiteCumplimentar());
		solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
		solicitudDocumentacionPrevia = solicitud;
		return "/cuestionarios/vistaSolicitud";
	}

	/**
	 * Método que recoge los valores introducidos en el formulario y da de alta un equipo normal en la BBDD
	 * @return
	 */
	public String enviarPreCuestionario(PreEnvioCuest cuestionario) {
		solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
		nombreCuestionarioPrevio = cuestionario.getDescripcion();
		return "/cuestionarios/previo";
	}

	/**
	 * Método que recoge los valores introducidos en el formulario y da de alta un equipo normal en la BBDD
	 * @return
	 */
	public String enviarPreCuestionarioOld(PreEnvioCuest cuestionario) {

		return "/cuestionarios/preenvio";
	}

	public String limpiar() {
		model.resetValues();
		return "/cuestionarios/preenvio";
	}

	@PostConstruct
	public void init() {
		nombreCuestionarioPrevio = null;
		anio = null;
		listaSolicitudesPrevia = new ArrayList<SolicitudDocumentacionPrevia>();
		model = new DatosJasper();
		// insertar();
		listadoPreEnvioCuestionarios = modeloCuestionarioService.findAllPre();

	}

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
				System.out.println(fichero.getName().substring(fichero.getName().lastIndexOf('.') + 1));
				cuestionario.setExtension(
						fichero.getName().substring(fichero.getName().lastIndexOf('.') + 1).toLowerCase());
				// Blob fichero = Hibernate.getLobCreator(sessionFactory.openSession()).createBlob(data);
				cuestionario.setFichero(data);
				modeloCuestionarioService.savePre(cuestionario);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

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
			e.printStackTrace();
		}
	}

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
	 */
	private void saveReg(String descripcion, String tipoReg, String username) {
		RegActividad regActividad = new RegActividad();
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
}
