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
import es.mira.progesin.util.PdfGenerator;
import es.mira.progesin.util.VerificadorExtensiones;
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
     * lista de archivos de plantillas presentes en el sistema por ambito de la solicitud.
     */
    private transient List<Entry<String, String>> listaPlantillasAmbito;
    
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
     * Generador de archivos PDF con la información de la solicitud cumplimentada.
     */
    @Autowired
    private transient PdfGenerator pdfGenerator;
    
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
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
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
    }
    
    /**
     * Eliminación fisica de un documento cargado.
     * 
     * @param documento documento a eliminar
     */
    public void eliminarDocumento(Documento documento) {
        try {
            solicitudDocumentacionPrevia.getDocumentos().remove(documento);
            solicitudDocumentacionPrevia = solicitudDocumentacionService
                    .eliminarDocumentoSolicitud(solicitudDocumentacionPrevia, documento);
        } catch (DataAccessException e) {
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
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
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR", e.getMessage());
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
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
                    SeccionesEnum.DOCUMENTACION.name());
            // Guardamos la alerta en bbdd
            alertaService.crearAlertaJefeEquipo(SeccionesEnum.DOCUMENTACION.name(), descripcion,
                    solicitudDocumentacionPrevia.getInspeccion());
            
            alertaService.crearAlertaRol(SeccionesEnum.DOCUMENTACION.name(), descripcion, RoleEnum.ROLE_SERVICIO_APOYO);
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al finalizar la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
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
            String descripcion = "Solicitud documentación previa cuestionario para la inspección "
                    + solicitudDocumentacionPrevia.getInspeccion().getNumero();
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.DOCUMENTACION.name());
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al guardar el borrador, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
        }
    }
    
    /**
     * Carga las plantillas existentes para el ámbito de la solicitud en curso para el usuario provisional actual.
     */
    public void plantillasAmbitoSolicitud() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        solicitudDocumentacionPrevia = solicitudDocumentacionService
                .findEnviadaNoFinalizadaPorCorreoDestinatario(correo);
        if ("true".equals(solicitudDocumentacionPrevia.getDescargaPlantillas())) {
            String ambito = solicitudDocumentacionPrevia.getInspeccion().getAmbito().name();
            setListaPlantillasAmbito(new ArrayList<>());
            if ("GC".equals(ambito) || "PN".equals(ambito)) {
                Map<String, String> mapaPlantillas = applicationBean.getMapaParametros().get("plantillas" + ambito);
                if (mapaPlantillas != null) {
                    listaPlantillasAmbito.addAll(mapaPlantillas.entrySet());
                }
            } else {
                // OTROS se muestran todas las de GC y PN
                Map<String, String> mapaPlantillasGC = applicationBean.getMapaParametros().get("plantillasGC");
                Map<String, String> mapaPlantillasPN = applicationBean.getMapaParametros().get("plantillasPN");
                if (mapaPlantillasGC != null) {
                    listaPlantillasAmbito.addAll(mapaPlantillasGC.entrySet());
                }
                if (mapaPlantillasPN != null) {
                    listaPlantillasAmbito.addAll(mapaPlantillasPN.entrySet());
                }
            }
        }
    }
    
    /**
     * Imprime la vista en formato PDF.
     */
    public void imprimirPdf() {
        try {
            setFile(pdfGenerator.imprimirSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia,
                    listadoDocumentosPrevios));
        } catch (ProgesinException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error en la generación del PDF");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
        }
    }
}
