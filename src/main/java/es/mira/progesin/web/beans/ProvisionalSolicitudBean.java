package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Documento;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.enums.EstadoRegActividadEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.gd.IGestDocSolicitudDocumentacionService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.PdfGenerator;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Controller("provisionalSolicitudBean")
@Scope("session")
public class ProvisionalSolicitudBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String NOMBRESECCION = "Acceso a la solicitud de documentación";

	private static final String ERROR = "Error";

	@Autowired
	private transient ApplicationBean applicationBean;

	@Autowired
	private transient IRegistroActividadService regActividadService;

	@Autowired
	private transient ISolicitudDocumentacionService solicitudDocumentacionService;

	@Autowired
	private transient INotificacionService notificacionService;

	@Autowired
	private transient ITipoDocumentacionService tipoDocumentacionService;

	@Autowired
	private transient IGestDocSolicitudDocumentacionService gestDocumentacionService;

	@Autowired
	private transient IDocumentoService documentoService;

	private transient List<DocumentacionPrevia> listadoDocumentosPrevios = new ArrayList<>();

	private List<GestDocSolicitudDocumentacion> listadoDocumentosCargados = new ArrayList<>();

	private List<Entry<String, String>> listaPlantillasAmbito;

	private SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();

	private transient StreamedContent file;

	private Map<String, String> extensiones;

	private String vieneDe;

	@Autowired
	private PdfGenerator pdfGenerator;

	public void visualizarSolicitud() {
		if ("menu".equalsIgnoreCase(this.vieneDe)) {
			this.vieneDe = null;
			String correo = SecurityContextHolder.getContext().getAuthentication().getName();
			// ñapa
			try {
				solicitudDocumentacionPrevia = solicitudDocumentacionService
						.findByFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndCorreoDestinatario(correo);
				listadoDocumentosPrevios = tipoDocumentacionService
						.findByIdSolicitud(solicitudDocumentacionPrevia.getId());
				listadoDocumentosCargados = gestDocumentacionService
						.findByIdSolicitud(solicitudDocumentacionPrevia.getId());
			}
			catch (Exception e) {
				regActividadService.altaRegActividadError(NOMBRESECCION, e);
			}
		}
	}

	private boolean esDocumentacionPrevia(UploadedFile archivo) {
		String nombreArchivo = archivo.getFileName();
		String extensionArchivo = extensiones.get(archivo.getContentType());
		for (DocumentacionPrevia dp : listadoDocumentosPrevios) {
			if (nombreArchivo.startsWith(dp.getNombre()))
				for (String ext : dp.getExtensiones()) {
					if (extensionArchivo.equals(ext))
						return true;
				}
		}
		return false;
	}

	public String gestionarCargaDocumento(FileUploadEvent event) {
		try {
			UploadedFile archivo = event.getFile();
			if (documentoService.extensionCorrecta(archivo)) {
				if (esDocumentacionPrevia(archivo)) {
					Documento documento = documentoService.cargaDocumento(archivo);
					if (documento != null) {
						GestDocSolicitudDocumentacion gestDocumento = new GestDocSolicitudDocumentacion();
						gestDocumento.setFechaAlta(new Date());
						gestDocumento.setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());
						gestDocumento.setIdSolicitud(solicitudDocumentacionPrevia.getId());
						gestDocumento.setIdDocumento(documento.getId());
						gestDocumento.setNombreFichero(documento.getNombre());
						gestDocumento.setExtension(extensiones.get(documento.getTipoContenido()));
						if (gestDocumentacionService.save(gestDocumento) != null) {
							FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
									"Documento/s subidos con éxito");
						}
					}
				}
				else {
					FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Carga de archivos",
							"El archivo " + archivo.getFileName()
									+ " no es válido, el nombre o la extensión no se corresponde con alguno de los documentos solicitados.");
				}
			}
			else {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Carga de archivos",
						"La extensión del archivo '" + event.getFile().getFileName()
								+ "' no corresponde a su tipo real");
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al cargar el documento, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
		listadoDocumentosCargados = gestDocumentacionService.findByIdSolicitud(solicitudDocumentacionPrevia.getId());
		return "/provisionalSolicitud/cargaDocumentos";
	}

	@PostConstruct
	public void init() {
		solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
		listadoDocumentosPrevios = new ArrayList<>();
		listadoDocumentosCargados = new ArrayList<>();
		Map<String, String> parametrosExtensiones = applicationBean.getMapaParametros().get("extensiones");
		extensiones = new HashMap<>();
		for (Entry<String, String> p : parametrosExtensiones.entrySet()) {
			// Invierto orden para buscar por contentType y obtener extension
			extensiones.put(p.getValue(), p.getKey());
		}
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
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
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

	public void enviarDocumentacionPrevia() {
		try {
			solicitudDocumentacionPrevia.setFechaCumplimentacion(new Date());
			String usuarioProv = solicitudDocumentacionPrevia.getCorreoDestinatario();
			if (solicitudDocumentacionService.transaccSaveInactivaUsuarioProv(solicitudDocumentacionPrevia,
					usuarioProv)) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Cumplimentacion",
						"Solicitud de documentación cumplimentada con éxito.");
				String descripcion = "Solicitud documentación previa cuestionario para la inspección "
						+ solicitudDocumentacionPrevia.getInspeccion().getNumero() + " cumplimentada";
				// Guardamos la actividad en bbdd
				regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						NOMBRESECCION);
				// Guardamos la notificacion en bbdd
				notificacionService.crearNotificacionRol(descripcion, NOMBRESECCION, RoleEnum.ADMIN);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al finalizar la solicitud, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}

	}

	public void guardarBorrardor() {
		try {
			if (solicitudDocumentacionService.save(solicitudDocumentacionPrevia) != null) {
				FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Borrador",
						"El borrador se ha guardado con éxito");
				String descripcion = "Solicitud documentación previa cuestionario para la inspección "
						+ solicitudDocumentacionPrevia.getInspeccion().getNumero();
				regActividadService.altaRegActividad(descripcion, EstadoRegActividadEnum.MODIFICACION.name(),
						NOMBRESECCION);
			}
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error al guardar el borrador, inténtelo de nuevo más tarde");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
	}

	public void plantillasAmbitoSolicitud() {
		String correo = SecurityContextHolder.getContext().getAuthentication().getName();
		try {
			solicitudDocumentacionPrevia = solicitudDocumentacionService
					.findByFechaFinalizacionIsNullAndFechaEnvioIsNotNullAndCorreoDestinatario(correo);
			if ("true".equals(solicitudDocumentacionPrevia.getDescargaPlantillas())) {
				String ambito = solicitudDocumentacionPrevia.getInspeccion().getAmbito().name();
				if ("GC".equals(ambito) || "PN".equals(ambito)) {
					listaPlantillasAmbito = new ArrayList<>(
							applicationBean.getMapaParametros().get("plantillas" + ambito).entrySet());
				}
				else {
					// OTROS se muestran todas las de GC y PN
					listaPlantillasAmbito = new ArrayList<>(
							applicationBean.getMapaParametros().get("plantillasGC").entrySet());
					listaPlantillasAmbito.addAll(applicationBean.getMapaParametros().get("plantillasPN").entrySet());
				}
			}
		}
		catch (Exception e) {
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
	}

	/**
	 * Imprime la vista en formato PDF
	 * 
	 * @author EZENTIS
	 */
	public void imprimirPdf() {
		try {
			setFile(pdfGenerator.imprimirSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia,
					listadoDocumentosPrevios));
		}
		catch (Exception e) {
			FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, ERROR,
					"Se ha producido un error en la generación del PDF");
			regActividadService.altaRegActividadError(NOMBRESECCION, e);
		}
	}
}
