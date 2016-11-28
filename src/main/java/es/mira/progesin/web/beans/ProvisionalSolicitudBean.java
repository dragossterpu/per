package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Documento;
import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.gd.IGestDocSolicitudDocumentacionService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("provisionalSolicitudBean")
@SessionScoped

public class ProvisionalSolicitudBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String NOMBRESECCION = "Acceso a la solicitud de documentación";

	private static final String ERROR = "Error";

	RegistroActividad regActividad = new RegistroActividad();

	@Autowired
	transient IRegistroActividadService regActividadService;

	@Autowired
	transient IModeloCuestionarioService modeloCuestionarioService;

	@Autowired
	transient ISolicitudDocumentacionService solicitudDocumentacionService;

	@Autowired
	transient INotificacionService notificacionService;

	@Autowired
	transient ITipoDocumentacionService tipoDocumentacionService;

	@Autowired
	transient IGestDocSolicitudDocumentacionService gestDocumentacionService;

	@Autowired
	transient IDocumentoService documentoService;

	private List<DocumentacionPrevia> listadoDocumentosPrevios = new ArrayList<>();

	private List<GestDocSolicitudDocumentacion> listadoDocumentosCargados = new ArrayList<>();

	SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();

	private StreamedContent file;

	public void visualizarSolicitud() {
		String correo = SecurityContextHolder.getContext().getAuthentication().getName();
		// ñapa
		try {
			solicitudDocumentacionPrevia = solicitudDocumentacionService
					.findByFechaFinalizacionIsNullAndCorreoDestinatario(correo);
			listadoDocumentosPrevios = tipoDocumentacionService.findByIdSolicitud(solicitudDocumentacionPrevia.getId());
			listadoDocumentosCargados = gestDocumentacionService
					.findByIdSolicitud(solicitudDocumentacionPrevia.getId());
		}
		catch (Exception e) {
			regActividadService.altaRegActivError(NOMBRESECCION, e);
		}

	}

	public String gestionarCargaDocumento(FileUploadEvent event) {
		try {
			Documento documento = documentoService.cargaDocumento(event.getFile());
			if (documento != null) {
				GestDocSolicitudDocumentacion gestDocumento = new GestDocSolicitudDocumentacion();
				gestDocumento.setFechaAlta(new Date());
				gestDocumento.setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());
				gestDocumento.setIdSolicitud(solicitudDocumentacionPrevia.getId());
				gestDocumento.setIdDocumento(documento.getId());
				gestDocumento.setNombreFichero(documento.getNombre());
				gestDocumento.setExtension(Utilities.getExtensionTipoContenido(documento.getTipoContenido()));
				if (gestDocumentacionService.save(gestDocumento) != null) {
					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
							"Documento/s subidos con éxito");
				}
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al cargar el documento, inténtelo de nuevo más tarde");
			regActividadService.altaRegActivError(NOMBRESECCION, e);
		}
		listadoDocumentosCargados = gestDocumentacionService.findByIdSolicitud(solicitudDocumentacionPrevia.getId());
		return "/provisionalSolicitud/cargaDocumentos";
	}

	@PostConstruct
	public void init() {
		solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
		listadoDocumentosPrevios = new ArrayList<>();
		listadoDocumentosCargados = new ArrayList<>();
	}

	public void onRowEdit(RowEditEvent event) {
		GestDocSolicitudDocumentacion document = (GestDocSolicitudDocumentacion) event.getObject();
		gestDocumentacionService.save(document);
		FacesMessage msg = new FacesMessage("Nombre de documento modificado con exito", document.getNombreFichero());
		FacesContext.getCurrentInstance().addMessage("msgs", msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Modificación cancelada",
				((GestDocSolicitudDocumentacion) event.getObject()).getNombreFichero());
		FacesContext.getCurrentInstance().addMessage("msgs", msg);
	}

	/**
	 * Eliminación fisica
	 * @param documento documento a eliminar
	 */
	public void eliminarDocumento(GestDocSolicitudDocumentacion gestDocumento) {
		documentoService.delete(gestDocumento.getIdDocumento());
		gestDocumentacionService.delete(gestDocumento);
		listadoDocumentosCargados.remove(gestDocumento);
	}

	public void descargarFichero(Long idDocumento) {
		try {
			file = documentoService.descargaDocumento(idDocumento);
		}
		catch (Exception e) {
			regActividadService.altaRegActivError(NOMBRESECCION, e);
		}
	}

	public String limpiar() {
		solicitudDocumentacionPrevia.setTelefonoInterlocutor("");
		solicitudDocumentacionPrevia.setCategoriaInterlocutor("");
		solicitudDocumentacionPrevia.setCargoInterlocutor("");
		solicitudDocumentacionPrevia.setCorreoCorporativoInterlocutor("");
		solicitudDocumentacionPrevia.setNombreCompletoInterlocutor("");
		return null;
	}

	public String enviarDocumentacionPrevia() {
		try {
			solicitudDocumentacionPrevia.setFechaCumplimentacion(new Date());
			String usuarioProv = solicitudDocumentacionPrevia.getCorreoDestinatario();
			if (solicitudDocumentacionService.transaccSaveInactivaUsuarioProv(solicitudDocumentacionPrevia,
					usuarioProv)) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
						"Solicitud de documentación cumplimentada con éxito. Su sesión ha finalizado.");
				String descripcion = "Solicitud documentación previa cuestionario. Usuario cumplimentación : "
						+ SecurityContextHolder.getContext().getAuthentication().getName();
				// Guardamos la actividad en bbdd
				regActividadService.crearRegistroActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						NOMBRESECCION);

				// Guardamos la notificacion en bbdd
				// TODO usar NotificacionEnum
				notificacionService.crearNotificacion(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						NOMBRESECCION);
			}

		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al finalizar la solicitud, inténtelo de nuevo más tarde");
			regActividadService.altaRegActivError(NOMBRESECCION, e);
		}
		return "/login/logout";

	}

	public void guardarBorrardor() {
		try {
			if (solicitudDocumentacionService.save(solicitudDocumentacionPrevia) != null) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Borrador",
						"El borrador se ha guardado con éxito");
				String descripcion = "Solicitud documentación previa cuestionario. Usuario cumplimentación : "
						+ SecurityContextHolder.getContext().getAuthentication().getName();
				regActividadService.crearRegistroActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						NOMBRESECCION);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al guardar el borrador, inténtelo de nuevo más tarde");
			regActividadService.altaRegActivError(NOMBRESECCION, e);
		}
	}
}
