package es.mira.progesin.web.beans.solicitudes;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.PdfGeneratorSolicitudes;
import es.mira.progesin.util.VerificadorExtensiones;
import es.mira.progesin.web.beans.ApplicationBean;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador de operaciones relacionadas con las solicitudes de documentación previas al envio de cuestionarios por
 * parte de los usuarios provisionales. Cumplimentación y envío de las mismas por parte de la unidad en cuestión.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia
 */
@Setter
@Getter
@Controller("provisionalSolicitudBean")
@Scope("session")
public class ProvisionalSolicitudBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Bean de datos comunes de la aplicación.
     */
    @Autowired
    private transient ApplicationBean applicationBean;
    
    /**
     * Servicio del registro de actividad.
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Servicio de solicitudes de documentación.
     */
    @Autowired
    private transient ISolicitudDocumentacionService solicitudDocumentacionService;
    
    /**
     * Servicio de notificaciones.
     */
    @Autowired
    private transient INotificacionService notificacionService;
    
    /**
     * Servicio de alertas.
     */
    @Autowired
    private transient IAlertaService alertaService;
    
    /**
     * Servicio de tipos de documentación.
     */
    @Autowired
    private transient ITipoDocumentacionService tipoDocumentacionService;
    
    /**
     * Servicio de documentos.
     */
    @Autowired
    private transient IDocumentoService documentoService;
    
    /**
     * Lista de tipos de documentación asociados a la solicitud.
     */
    private List<DocumentacionPrevia> listadoDocumentosPrevios;
    
    /**
     * lista de archivos de plantillas presentes en el sistema.
     */
    private transient List<Documento> listaPlantillas;
    
    /**
     * Solicitud que se muestra al usuario provisional para ser cumplimentada.
     */
    private SolicitudDocumentacionPrevia solicitudDocumentacionPrevia;
    
    /**
     * Archivo siendo subido o descargado.
     */
    private transient StreamedContent file;
    
    /**
     * Mapa con las extensiones de archivo aceptadas en el sistema.
     */
    private Map<String, String> extensiones;
    
    /**
     * Verificador de extensiones.
     */
    @Autowired
    private transient VerificadorExtensiones verificadorExtensiones;
    
    /**
     * Generador del pdf de la solicitud.
     */
    @Autowired
    private PdfGeneratorSolicitudes pdfGenerator;
    
    /**
     * Carga los datos relativos a la solicitud de documentación previa en curso para el usuario provisional logueado
     * con su correo como username.
     * @return ruta siguiente
     */
    public String visualizarSolicitud() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        solicitudDocumentacionPrevia = solicitudDocumentacionService
                .findEnviadaNoFinalizadaPorCorreoDestinatario(correo);
        listadoDocumentosPrevios = tipoDocumentacionService.findByIdSolicitud(solicitudDocumentacionPrevia.getId());
        if ("true".equals(solicitudDocumentacionPrevia.getDescargaPlantillas())) {
            listaPlantillas = documentoService.buscaNombreTipoDocumento("PLANTILLA SOLICITUD");
        }
        return "/provisionalSolicitud/provisionalSolicitud?faces-redirect=true";
    }
    
    /**
     * Comprueba si un archivo se corresponde con alguno de los documentos solicitados tanto en nombre como en
     * extensión.
     * 
     * @param archivo subido
     * @return booleano si o no
     */
    private boolean esDocumentacionPrevia(UploadedFile archivo) {
        String nombreArchivo = archivo.getFileName();
        String extensionArchivo = extensiones.get(archivo.getContentType());
        boolean respuesta = false;
        for (DocumentacionPrevia dp : listadoDocumentosPrevios) {
            if (nombreArchivo.toLowerCase().startsWith(dp.getNombre().toLowerCase())
                    && dp.getExtensiones().contains(extensionArchivo)) {
                respuesta = true;
                break;
            }
        }
        return respuesta;
    }
    
    /**
     * Guarda un archivo subido por el usuario como documento de la solicitud, tras validar que no es un archivo
     * corrupto y que encaja con alguno de los solicitados.
     * 
     * @param event lanzado desde el formulario
     * @return ruta de la vista
     */
    public String gestionarCargaDocumento(FileUploadEvent event) {
        try {
            UploadedFile archivo = event.getFile();
            // 9 es el id del tipodocumento para "DOCUMENTACIÓN SALIDA IPSS"
            TipoDocumento tipo = TipoDocumento.builder().id(9L).build();
            if (verificadorExtensiones.extensionCorrecta(archivo)) {
                if (esDocumentacionPrevia(archivo)) {
                    Documento documento = documentoService.cargaDocumento(archivo, tipo,
                            solicitudDocumentacionPrevia.getInspeccion());
                    solicitudDocumentacionPrevia.getDocumentos().add(documento);
                    solicitudDocumentacionPrevia = solicitudDocumentacionService.save(solicitudDocumentacionPrevia);
                    
                    FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Alta",
                            "Documento/s subidos con éxito", "msgs");
                } else {
                    FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Carga de archivos",
                            "El archivo " + archivo.getFileName()
                                    + " no es válido, el nombre o la extensión no se corresponde con alguno de los documentos solicitados.",
                            "msgs");
                }
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Carga de archivos",
                        "La extensión del archivo '" + event.getFile().getFileName()
                                + "' no corresponde a su tipo real",
                        "msgs");
            }
        } catch (DataAccessException | ProgesinException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al cargar el documento, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
        return "/provisionalSolicitud/cargaDocumentos";
    }
    
    /**
     * PostConstruct, inicializa el bean.
     */
    @PostConstruct
    public void init() {
        Map<String, String> parametrosExtensiones = applicationBean.getMapaParametros().get("extensiones");
        extensiones = new HashMap<>();
        for (Entry<String, String> p : parametrosExtensiones.entrySet()) {
            // Invierto orden para buscar por contentType y obtener extension
            extensiones.put(p.getValue(), p.getKey());
        }
        visualizarSolicitud();
        
    }
    
    /**
     * Eliminación fisica de un documento cargado.
     * 
     * @param documento documento a eliminar
     */
    public void eliminarDocumento(Documento documento) {
        try {
            
            solicitudDocumentacionPrevia = solicitudDocumentacionService
                    .eliminarDocumentoSolicitud(solicitudDocumentacionPrevia, documento);
        } catch (DataAccessException e) {
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
    }
    
    /**
     * Descarga de un documento subido por el usuario provisional o una plantilla.
     * 
     * @param idDocumento seleccionado
     */
    public void descargarFichero(Long idDocumento) {
        setFile(null);
        try {
            setFile(documentoService.descargaDocumento(idDocumento));
        } catch (ProgesinException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    e.getMessage());
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
    }
    
    /**
     * Envia la solicitud de documentación previa cumplimentada por el usuario provisional, guarda registro del hecho y
     * alerta al jefe del equipo que gestiona la inspección y al servicio de apoyo.
     * 
     */
    public void enviarDocumentacionPrevia() {
        try {
            solicitudDocumentacionPrevia.setFechaCumplimentacion(new Date());
            String usuarioProv = solicitudDocumentacionPrevia.getCorreoDestinatario();
            
            solicitudDocumentacionService.transaccSaveInactivaUsuarioProv(solicitudDocumentacionPrevia, usuarioProv);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Cumplimentacion",
                    "Solicitud de documentación cumplimentada con éxito.");
            String descripcion = "Solicitud documentación previa cuestionario para la inspección "
                    + solicitudDocumentacionPrevia.getInspeccion().getNumero() + " cumplimentada";
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.DOCUMENTACION.getDescripcion());
            // Guardamos la alerta en bbdd
            alertaService.crearAlertaJefeEquipo(SeccionesEnum.DOCUMENTACION.getDescripcion(), descripcion,
                    solicitudDocumentacionPrevia.getInspeccion());
            
            alertaService.crearAlertaRol(SeccionesEnum.DOCUMENTACION.getDescripcion(), descripcion,
                    RoleEnum.ROLE_SERVICIO_APOYO);
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al finalizar la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
        
    }
    
    /**
     * Guarda el estado actual de la solicitud sin validar su grado de cumplimentación.
     */
    public void guardarBorrardor() {
        try {
            solicitudDocumentacionService.save(solicitudDocumentacionPrevia);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Borrador",
                    "El borrador se ha guardado con éxito");
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al guardar el borrador, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
    }
    
    /**
     * Imprime la vista en formato PDF.
     */
    public void imprimirPdf() {
        try {
            pdfGenerator.setSolicitudDocPrevia(solicitudDocumentacionPrevia);
            pdfGenerator.setListadoDocumentosSolicitud(listadoDocumentosPrevios);
            setFile(pdfGenerator.exportarPdf());
        } catch (ProgesinException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error en la generación del PDF");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
    }
}
