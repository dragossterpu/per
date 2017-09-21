package es.mira.progesin.web.beans.solicitudes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.CorreoException;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.util.PdfGeneratorSolicitudes;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador de las operaciones relacionadas con las solicitudes de documentación previas al envio de cuestionarios.
 * Creación de solicitudes, validación por parte de apoyo, envío a la unidad en cuestión, cumplimentación por parte de
 * ésta y finalización de las mismas.
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
    
    /**
     * Constante con el valor de la descripción.
     */
    private static final String DESCRIPCION = "Solicitud documentación previa cuestionario para la inspección ";
    
    /**
     * Solicitud de documentación previa.
     */
    private SolicitudDocumentacionPrevia solicitudDocumentacionPrevia;
    
    /**
     * Copia de la fecha límite de envío.
     */
    private Date backupFechaLimiteEnvio;
    
    /**
     * Casilla para saltar pestaña de selección de tipos de documentación en formulario crearsolicitud.
     */
    private boolean skip;
    
    /**
     * Objeto que almacena el documento descargable.
     */
    private transient StreamedContent file;
    
    /**
     * Lista de documentos.
     */
    private List<DocumentacionPrevia> listadoDocumentosPrevios;
    
    /**
     * Lista de tipos de documentos.
     */
    private List<TipoDocumentacion> listadoDocumentos;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
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
     * Servicio de Solicitudes de Documentación.
     */
    @Autowired
    private transient ISolicitudDocumentacionService solicitudDocumentacionService;
    
    /**
     * Servicio de inspecciones.
     */
    @Autowired
    private transient IInspeccionesService inspeccionesService;
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private transient IUserService userService;
    
    /**
     * Servicio de documentos.
     */
    @Autowired
    private transient IDocumentoService documentoService;
    
    /**
     * Encriptador del palabras claves.
     */
    @Autowired
    private transient PasswordEncoder passwordEncoder;
    
    /**
     * Clase para el manejo de correos electrónicos.
     */
    @Autowired
    private transient ICorreoElectronico correoElectronico;
    
    /**
     * Generador del pdf de la solicitud.
     */
    @Autowired
    private transient PdfGeneratorSolicitudes pdfGenerator;
    
    /**
     * Constante de.
     */
    private static final String DE = " de ";
    
    /**
     * Permite al equipo de apoyo validar la solicitud de documentación.
     * 
     */
    public void validacionApoyo() {
        try {
            
            solicitudDocumentacionPrevia.setFechaValidApoyo(new Date());
            solicitudDocumentacionPrevia
                    .setUsernameValidApoyo(SecurityContextHolder.getContext().getAuthentication().getName());
            
            solicitudDocumentacionService.save(solicitudDocumentacionPrevia);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Validación",
                    "Se ha validado con éxito la solicitud de documentación");
            
            String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero()
                    + " validada por apoyo";
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.DOCUMENTACION.getDescripcion());
            
            alertaService.crearAlertaJefeEquipo(SeccionesEnum.DOCUMENTACION.getDescripcion(), descripcion,
                    solicitudDocumentacionPrevia.getInspeccion());
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al validar apoyo la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
    }
    
    /**
     * Permite al jefe del equipo de apoyo validar la solicitud de documentación.
     */
    public void validacionJefeEquipo() {
        try {
            
            solicitudDocumentacionPrevia.setFechaValidJefeEquipo(new Date());
            solicitudDocumentacionPrevia
                    .setUsernameValidJefeEquipo(SecurityContextHolder.getContext().getAuthentication().getName());
            
            solicitudDocumentacionService.save(solicitudDocumentacionPrevia);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Validación",
                    "Se ha validado con éxito la solicitud de documentación");
            
            String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero()
                    + " validada por jefe equipo";
            
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.DOCUMENTACION.getDescripcion());
            
            alertaService.crearAlertaRol(SeccionesEnum.DOCUMENTACION.getDescripcion(), descripcion,
                    RoleEnum.ROLE_JEFE_INSPECCIONES);
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al validar el jefe del equipo la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
    }
    
    /**
     * Permite descargar el fichero seleccionado. Utilizado para documentos previos subidos por el interlocutor de una
     * unidad al cumplimentar una solicitud.
     * 
     * @author EZENTIS
     * @param idDocumento clave del documento a descargar
     */
    public void descargarFichero(Long idDocumento) {
        setFile(null);
        try {
            setFile(documentoService.descargaDocumento(idDocumento));
        } catch (ProgesinException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "ERROR", e.getMessage());
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
    }
    
    /**
     * Elimina la solicitud de documentación previa. Se hace eliminación física si no ha sido enviada aún sino sólo
     * lógica junto a la eliminación física del usuario provisional. Además desde la interfaz las solicitudes
     * finalizadas no se pueden eliminar.
     * 
     * @param solicitud a eliminar
     */
    public void eliminarSolicitud(SolicitudDocumentacionPrevia solicitud) {
        try {
            User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<RoleEnum> rolesAdmitidos = new ArrayList<>();
            rolesAdmitidos.add(RoleEnum.ROLE_JEFE_INSPECCIONES);
            rolesAdmitidos.add(RoleEnum.ROLE_SERVICIO_APOYO);
            rolesAdmitidos.add(RoleEnum.ROLE_ADMIN);
            if (solicitud.getFechaBaja() == null && rolesAdmitidos.contains(usuarioActual.getRole())) {
                
                // Si no ha sido enviada se trata como un borrador y se hace eliminación física
                if (solicitud.getFechaEnvio() == null) {
                    solicitudDocumentacionService.transaccDeleteElimDocPrevia(solicitud.getId());
                    
                    FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Eliminación",
                            "Se ha eliminado con éxito la solicitud de documentación", null);
                } else {
                    // Enviada pero no finalizada, existe usuario provisional
                    solicitud.setFechaBaja(new Date());
                    solicitud.setUsernameBaja(usuarioActual.getUsername());
                    String usuarioProv = solicitud.getCorreoDestinatario();
                    
                    solicitudDocumentacionService.transaccSaveElimUsuarioProv(solicitud, usuarioProv);
                    
                    FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Baja",
                            "Se ha dado de baja con éxito la solicitud de documentación", null);
                    
                    String descripcion = DESCRIPCION + solicitud.getInspeccion().getNumero();
                    
                    regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                            SeccionesEnum.DOCUMENTACION.getDescripcion());
                    
                    if (solicitud.getFechaFinalizacion() == null) {
                        String asunto = "Baja Solicitud " + solicitud.getInspeccion().getTipoUnidad().getDescripcion()
                                + DE + solicitud.getInspeccion().getNombreUnidad() + " ("
                                + solicitud.getInspeccion().getMunicipio().getProvincia().getNombre()
                                + "). Número de expediente " + solicitud.getInspeccion().getNumero() + ".";
                        
                        Map<String, String> paramPlantilla = null;
                        correoElectronico.envioCorreo(usuarioProv, asunto, Constantes.TEMPLATEBAJASOLICITUD,
                                paramPlantilla);
                    }
                }
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_WARN, "Eliminación abortada",
                        "Ya ha sido anulada con anterioridad o no tiene permisos para realizar esta acción", null);
            }
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al eliminar la solicitud, inténtelo de nuevo más tarde", null);
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
    }
    
    /**
     * Modifica los datos de la solicitud de documentación previa. En caso de que la fecha límite de envío por parte de
     * la unidad sea alterada, se notifica por correo electrónico dicho cambio.
     * 
     */
    public void modificarSolicitud() {
        try {
            String backupFechaLimite = Utilities.getFechaFormateada(backupFechaLimiteEnvio, "dd/MM/yyyy");
            String fechaLimite = Utilities.getFechaFormateada(solicitudDocumentacionPrevia.getFechaLimiteEnvio(),
                    "dd/MM/yyyy");
            solicitudDocumentacionService.save(solicitudDocumentacionPrevia);
            String mensajeCorreoEnviado = "";
            // Avisar al destinatario si la fecha limite para la solicitud ha cambiado
            if (solicitudDocumentacionPrevia.getFechaEnvio() != null && !backupFechaLimite.equals(fechaLimite)) {
                
                String asunto = "Modificación plazo envío documentación previa a cuestionario "
                        + solicitudDocumentacionPrevia.getInspeccion().getTipoUnidad().getDescripcion() + " de "
                        + solicitudDocumentacionPrevia.getInspeccion().getNombreUnidad() + " ("
                        + solicitudDocumentacionPrevia.getInspeccion().getMunicipio().getProvincia().getNombre()
                        + "Número de expediente " + solicitudDocumentacionPrevia.getInspeccion().getNumero() + ".";
                
                Map<String, String> paramPlantilla = new HashMap<>();
                paramPlantilla.put("fechaAnterior", backupFechaLimite);
                paramPlantilla.put("fechaActual", fechaLimite);
                
                correoElectronico.envioCorreo(solicitudDocumentacionPrevia.getCorreoDestinatario(), asunto,
                        Constantes.TEMPLATEMODIFICACIONFECHASOLICITUD, paramPlantilla);
                
                mensajeCorreoEnviado = ". Se ha comunicado al destinatario de la unidad el cambio de fecha.";
            }
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                    "La solicitud de documentación ha sido modificada con éxito" + mensajeCorreoEnviado);
            
            String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero();
            
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.DOCUMENTACION.getDescripcion());
            
        } catch (DataAccessException e1) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al modificar la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e1);
        } catch (CorreoException e2) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    Constantes.FALLOCORREO);
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e2);
        }
    }
    
    /**
     * Permite al jefe de equipo de inspecciones validar la solicitud de documentación y enviarla y dar de alta un
     * usuario provisional para que algún miembro de la unidad a inspeccionar la cumplimente.
     * 
     */
    public void enviarSolicitud() {
        try {
            String correoDestinatario = solicitudDocumentacionPrevia.getCorreoDestinatario();
            if (userService.exists(correoDestinatario)) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Envío abortado",
                        "El usuario con correo " + correoDestinatario + " ya existe en el sistema.");
            } else {
                String password = Utilities.getPassword();
                
                solicitudDocumentacionPrevia.setFechaEnvio(new Date());
                solicitudDocumentacionPrevia
                        .setUsernameEnvio(SecurityContextHolder.getContext().getAuthentication().getName());
                
                User usuarioProv = new User(solicitudDocumentacionPrevia.getCorreoDestinatario(),
                        passwordEncoder.encode(password), RoleEnum.ROLE_PROV_SOLICITUD);
                
                solicitudDocumentacionService.transaccSaveCreaUsuarioProv(solicitudDocumentacionPrevia, usuarioProv,
                        password);
                
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Envío",
                        "Se ha enviado con éxito la solicitud de documentación");
                
                String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero()
                        + " enviada";
                
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                        SeccionesEnum.DOCUMENTACION.getDescripcion());
                
                notificacionService.crearNotificacionRol(descripcion, SeccionesEnum.DOCUMENTACION.getDescripcion(),
                        RoleEnum.ROLE_SERVICIO_APOYO);
                notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.DOCUMENTACION.getDescripcion(),
                        solicitudDocumentacionPrevia.getInspeccion().getEquipo());
            }
        } catch (DataAccessException | CorreoException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al enviar la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
    }
    
    /**
     * Permite al jefe del equipo finalizar una solicitud de documentación ya cumplimentada, después de revisar la
     * documentación adjuntada por la unidad que se va a inspeccionar. Adicionalmente elimina el usuario provisinal que
     * se usó para llevarla a cabo puesto que ya no se va a usar más.
     * 
     */
    public void finalizarSolicitud() {
        try {
            solicitudDocumentacionPrevia.setFechaFinalizacion(new Date());
            solicitudDocumentacionPrevia.setFechaNoConforme(null);
            String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
            solicitudDocumentacionPrevia.setUsuarioFinalizacion(usuarioActual);
            String usuarioProv = solicitudDocumentacionPrevia.getCorreoDestinatario();
            
            solicitudDocumentacionService.transaccSaveElimUsuarioProv(solicitudDocumentacionPrevia, usuarioProv);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Finalización",
                    "Se ha finalizado con éxito la solicitud de documentación");
            
            String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero() + " finalizada";
            
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.DOCUMENTACION.getDescripcion());
            
            notificacionService.crearNotificacionRol(descripcion, SeccionesEnum.DOCUMENTACION.getDescripcion(),
                    RoleEnum.ROLE_SERVICIO_APOYO);
            notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.DOCUMENTACION.getDescripcion(),
                    solicitudDocumentacionPrevia.getInspeccion().getEquipo());
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al finalizar la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
    }
    
    /**
     * Permite al jefe de equipo declarar no conforme una solicitud de documentación ya cumplimentada, después de
     * revisar la documentación adjuntada por la unidad que se va a inspeccionar. Para ello elimina la fecha de
     * cumplimentación y reenvia la solicitud al destinatario de la unidad con el motivo de dicha no conformidad.
     * Adicionalmente reactiva el usuario provisinal que se usó para llevarla a cabo.
     * 
     * 
     * @param motivosNoConforme texto introducido por el usuario
     */
    public void noConformeSolicitud(String motivosNoConforme) {
        try {
            
            solicitudDocumentacionPrevia.setFechaCumplimentacion(null);
            solicitudDocumentacionPrevia.setFechaNoConforme(new Date());
            String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
            solicitudDocumentacionPrevia.setUsuarioNoConforme(usuarioActual);
            String usuarioProv = solicitudDocumentacionPrevia.getCorreoDestinatario();
            
            solicitudDocumentacionService.transaccSaveActivaUsuarioProv(solicitudDocumentacionPrevia, usuarioProv);
            
            String asunto = "No conformidad documentación previa a cuestionario "
                    + solicitudDocumentacionPrevia.getInspeccion().getTipoUnidad().getDescripcion() + " de "
                    + solicitudDocumentacionPrevia.getInspeccion().getNombreUnidad() + " ("
                    + solicitudDocumentacionPrevia.getInspeccion().getMunicipio().getProvincia().getNombre()
                    + "Número de expediente " + solicitudDocumentacionPrevia.getInspeccion().getNumero() + ".";
            
            Map<String, String> paramPlantilla = new HashMap<>();
            paramPlantilla.put("textoNoConformidad", motivosNoConforme);
            
            correoElectronico.envioCorreo(solicitudDocumentacionPrevia.getCorreoDestinatario(), asunto,
                    Constantes.TEMPLATENOCONFORMESOLICITUD, paramPlantilla);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "No Conforme",
                    "Declarada no conforme con éxito la solicitud de documentación. El destinatario de la unidad será notificado y reactivado su acceso al sistema");
            
            String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero()
                    + " declarada no conforme";
            
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.DOCUMENTACION.getDescripcion());
            
        } catch (DataAccessException e1) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al declarar no conforme la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e1);
        } catch (CorreoException e2) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    Constantes.FALLOCORREO);
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e2);
        }
    }
    
    /**
     * Devuelve una lista con las inspecciones cuyo nombre de unidad o número contienen alguno de los caracteres pasado
     * como parámetro. Se usa en los formularios de creación y modificación para el autocompletado.
     * 
     * @param infoInspeccion texto con parte del nombre de unidad o el número de la inspección que teclea el usuario en
     * los formularios de creación y modificación
     * @return Devuelve la lista de inspecciones que contienen algún caracter coincidente con el texto introducido
     */
    public List<Inspeccion> autocompletarInspeccion(String infoInspeccion) {
        return inspeccionesService.buscarNoFinalizadaPorNombreUnidadONumero(infoInspeccion);
    }
    
    /**
     * Imprime la vista en formato PDF.
     * 
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
