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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.RegActividad;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.ITipoDocumentacionService;
import es.mira.progesin.services.gd.IGestDocSolicitudDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("provisionalBean")
@SessionScoped

public class ProvisionalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String NOMBRESECCION = "Acceso a la solicitud de documentación";

	RegActividad regActividad = new RegActividad();

	@Autowired
	IRegActividadService regActividadService;

	@Autowired
	IModeloCuestionarioService modeloCuestionarioService;

	String nombreCuestionarioPrevio;

	@Autowired
	ISolicitudDocumentacionService solicitudDocumentacionService;

	@Autowired
	INotificacionService notificacionService;

	@Autowired
	ITipoDocumentacionService tipoDocumentacionService;

	@Autowired
	IGestDocSolicitudDocumentacionService gestDocumentacionService;

	List<DocumentacionPrevia> listadoDocumentosPrevios = new ArrayList<DocumentacionPrevia>();

	List<GestDocSolicitudDocumentacion> listadoDocumentosCargados = new ArrayList<GestDocSolicitudDocumentacion>();

	SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();

	private Integer anio;

	private String fechaAntes;

	private String fechaLimite;

	private String fechaEmision;

	private StreamedContent file;

	public String visualizarSolicitud() {
		String correo = SecurityContextHolder.getContext().getAuthentication().getName();
		// ñapa
		try {
			solicitudDocumentacionPrevia = solicitudDocumentacionService.findByCorreoDestiantario("prueba@gmail.com");
			listadoDocumentosPrevios = tipoDocumentacionService.findByIdSolicitud(solicitudDocumentacionPrevia.getId());
			listadoDocumentosCargados = gestDocumentacionService
					.findByIdSolicitud(solicitudDocumentacionPrevia.getId());
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			fechaEmision = formatter.format(solicitudDocumentacionPrevia.getFechaAlta());
			fechaAntes = formatter.format(solicitudDocumentacionPrevia.getFechaAntes());
			fechaLimite = formatter.format(solicitudDocumentacionPrevia.getFechaLimiteCumplimentar());
			return "/provisional/provisional";
		}
		catch (Exception e) {
			return null;
		}

	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
				"Documento/s subidos  con éxito");
	}

	@PostConstruct
	public void init() {
		solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
		listadoDocumentosPrevios = new ArrayList<DocumentacionPrevia>();
		listadoDocumentosCargados = new ArrayList<GestDocSolicitudDocumentacion>();
		nombreCuestionarioPrevio = null;
		anio = null;
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
