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
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.lazydata.LazyModelSolicitudes;
import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.GestDocSolicitudDocumentacion;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.ITipoInspeccionService;
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
     * Constante.
     */
    private static final String ASUNTO = "Asunto: ";
    
    /**
     * Parámetro para controlar desde dónde se accede a la vista.
     */
    private String vieneDe;
    
    /**
     * Lista de booleanos para controlar la visibilidad de las columnas en la vista.
     */
    private List<Boolean> listaColumnToggler;
    
    /**
     * Solicitud de documentación previa.
     */
    private SolicitudDocumentacionPrevia solicitudDocumentacionPrevia;
    
    /**
     * Copia de la fecha límite de envío.
     */
    private Date backupFechaLimiteEnvio;
    
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
     * Listado de documentos cargados.
     */
    private List<GestDocSolicitudDocumentacion> listadoDocumentosCargados;
    
    /**
     * Lista de documentos seleccionados.
     */
    private List<TipoDocumentacion> documentosSeleccionados;
    
    /**
     * Lista de tipos de documentos.
     */
    private List<TipoDocumentacion> listadoDocumentos;
    
    /**
     * Formato de fecha.
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * Mapa con los parámetros de vista.
     */
    private Map<String, String> parametrosVistaSolicitud;
    
    /**
     * Mapa con los datos de apoyo.
     */
    private Map<String, String> datosApoyo;
    
    /**
     * LazyModel para la visualización de datos paginados en la vista.
     */
    private LazyModelSolicitudes model;
    
    /**
     * Lista de tipos de inspección.
     */
    private List<TipoInspeccion> listaTiposInspeccion;
    
    /**
     * Objeto de búsqueda de solicitudes.
     */
    private SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda;
    
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
     * Servicio de tipo de documentación.
     */
    @Autowired
    private transient ITipoDocumentacionService tipoDocumentacionService;
    
    /**
     * Servicio de inspecciones.
     */
    @Autowired
    private transient IInspeccionesService inspeccionesService;
    
    /**
     * Servicio de gestor de documentación de solicitudes.
     */
    @Autowired
    private transient IGestDocSolicitudDocumentacionService gestDocumentacionService;
    
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
     * Bean de configuración de la aplicación.
     */
    @Autowired
    private transient ApplicationBean applicationBean;
    
    /**
     * Generador de PDF.
     */
    @Autowired
    private transient PdfGenerator pdfGenerator;
    
    /**
     * Servicio de Cuestionarios enviados.
     */
    @Autowired
    private transient ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Servicio de tipos de inspección.
     */
    @Autowired
    private transient ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Crea una solicitud de documentación en base a los datos introducidos en el formulario de la vista crearSolicitud.
     * 
     */
    public void crearSolicitud() {
        
        try {
            // Comprobar que la inspeccion o el usuario no tengan solicitudes o cuestionarios sin finalizar
            if (inspeccionSinTareasPendientes() && usuarioSinTareasPendientes()) {
                solicitudDocumentacionService.transaccSaveAltaDocumentos(solicitudDocumentacionPrevia,
                        documentosSeleccionados);
                
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                        "La solicitud de documentación ha sido creada con éxito");
                
                String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero();
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                        SeccionesEnum.DOCUMENTACION.name());
            }
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al crear la solicitud, inténtelo de nuevo más tarde");
            // Guardamos los posibles errores en bbdd
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
        }
    }
    
    /**
     * Obtener los datos del jefe del equipo de apoyo. Se encuentran almacenados en la tabla parametros para que puedan
     * ser modificados por el DBA
     * 
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
     * Pasa los datos de la solicitud que queremos modificar al formulario de modificación para que cambien los valores
     * que quieran.
     * 
     * @param solicitud recuperada del formulario
     * @return vista modificarSolicitud
     */
    public String getFormModificarSolicitud(SolicitudDocumentacionPrevia solicitud) {
        solicitudDocumentacionPrevia = solicitud;
        backupFechaLimiteEnvio = solicitud.getFechaLimiteEnvio();
        return "/solicitudesPrevia/modificarSolicitud?faces-redirect=true";
    }
    
    /**
     * Permite visualizar una solicitud creada, muestra su información y dependiendo del estado en que se encuentre
     * permite pasar al siguiente estado si se tiene el rol adecuado. Posibles estados: alta, validada por apoyo,
     * validada por jefe equipo, enviada, cumplimentada, no conforme y finalizada
     * 
     * @param solicitud a mostrar
     * @return vista vistaSolicitud
     */
    public String visualizarSolicitud(SolicitudDocumentacionPrevia solicitud) {
        try {
            setListadoDocumentosCargados(gestDocumentacionService.findByIdSolicitud(solicitud.getId()));
            setListadoDocumentosPrevios(tipoDocumentacionService.findByIdSolicitud(solicitud.getId()));
            setSolicitudDocumentacionPrevia(solicitud);
            return "/solicitudesPrevia/vistaSolicitud?faces-redirect=true";
        } catch (Exception e) {
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
            return null;
        }
        
    }
    
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
                    SeccionesEnum.DOCUMENTACION.name());
            
            alertaService.crearAlertaJefeEquipo(SeccionesEnum.DOCUMENTACION.name(), descripcion,
                    solicitudDocumentacionPrevia.getInspeccion());
            
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al validar apoyo la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
        }
    }
    
    /**
     * Permite al jefe del equipo de apoyo validar la solicitud de documentación
     * 
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
                    SeccionesEnum.DOCUMENTACION.name());
            
            alertaService.crearAlertaRol(SeccionesEnum.DOCUMENTACION.name(), descripcion,
                    RoleEnum.ROLE_JEFE_INSPECCIONES);
            
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al validar el jefe del equipo la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
        }
    }
    
    /**
     * Carga el formulario para crear una solicitud.
     * 
     */
    public void getFormularioCrearSolicitud() {
        documentosSeleccionados = new ArrayList<>();
        solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
        datosApoyo();
        skip = false;
    }
    
    /**
     * PostConstruct, inicializa el bean.
     * 
     */
    @PostConstruct
    public void init() {
        setSolicitudDocPreviaBusqueda(new SolicitudDocPreviaBusqueda());
        datosApoyo = applicationBean.getMapaParametros().get("datosApoyo");
        model = new LazyModelSolicitudes(solicitudDocumentacionService);
        setListaTiposInspeccion(tipoInspeccionService.buscaTodos());
    }
    
    /**
     * Permite descargar el fichero seleccionado. Utilizado para documentos previos subidos por el interlocutor de una
     * unidad al cumplimentar una solicitud.
     * 
     * @author EZENTIS
     * @param idDocumento clave del documento a descargar
     */
    public void descargarFichero(Long idDocumento) {
        try {
            setFile(documentoService.descargaDocumento(idDocumento));
        } catch (Exception e) {
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
        }
    }
    
    /**
     * Gestiona la transición entre pestañas en el formulario de creación de solicitudes. En caso de pasar a la pestaña
     * de documentación se carga aquella que vaya asociada al ámbito seleccionado en la pestaña anterior, y se controla
     * que se escoja al menos un documento o se cornfirme que no se desea ninguno para esta solicitud antes de pasar a
     * una pestaña posterior.
     * 
     * @author EZENTIS
     * @param event info de la pestaña actual y la siguente que se solicita
     * @return nombre de la siguente pestaña a mostrar
     */
    public String onFlowProcess(FlowEvent event) {
        
        if ("general".equals(event.getOldStep()) && "documentacion".equals(event.getNewStep())) {
            if (inspeccionSinTareasPendientes() == Boolean.FALSE || usuarioSinTareasPendientes() == Boolean.FALSE) {
                return event.getOldStep();
            } else {
                AmbitoInspeccionEnum ambito = solicitudDocumentacionPrevia.getInspeccion().getAmbito();
                if (AmbitoInspeccionEnum.OTROS.equals(ambito)) {
                    setListadoDocumentos(tipoDocumentacionService.findAll());
                } else {
                    setListadoDocumentos(tipoDocumentacionService.findByAmbito(ambito));
                }
            }
        } else if ("documentacion".equals(event.getOldStep()) && "apoyo".equals(event.getNewStep())
                && documentosSeleccionados.isEmpty() && skip == Boolean.FALSE) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "Debe elegir uno o más documentos o confirmar que no desea ninguno", "", "");
            return event.getOldStep();
        }
        return event.getNewStep();
    }
    
    /**
     * Método para cambiar los campos que se muestran en la tabla de resultados del buscador.
     * 
     * @param e ToggleEvent evento que lanza el método
     */
    public void onToggle(ToggleEvent e) {
        listaColumnToggler.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
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
                            SeccionesEnum.DOCUMENTACION.name());
                }
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_WARN, "Eliminación abortada",
                        "Ya ha sido anulada con anterioridad o no tiene permisos para realizar esta acción", null);
            }
        } catch (Exception e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al eliminar la solicitud, inténtelo de nuevo más tarde", null);
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
        }
    }
    
    /**
     * Modifica los datos de la solicitud de documentación previa. En caso de que la fecha límite de envío por parte de
     * la unidad sea alterada, se notifica por correo electrónico dicho cambio.
     * 
     */
    public void modificarSolicitud() {
        try {
            solicitudDocumentacionService.save(solicitudDocumentacionPrevia);
            String mensajeCorreoEnviado = "";
            // Avisar al destinatario si la fecha limite para la solicitud ha cambiado
            if (solicitudDocumentacionPrevia.getFechaEnvio() != null
                    && !backupFechaLimiteEnvio.equals(solicitudDocumentacionPrevia.getFechaLimiteEnvio())) {
                StringBuilder asunto = new StringBuilder(DESCRIPCION)
                        .append(solicitudDocumentacionPrevia.getInspeccion().getNumero());
                StringBuilder textoAutomatico = new StringBuilder(
                        "\r\n \r\nEl plazo del que disponía para enviar la documentación previa conectándose a la aplicación PROGESIN ha sido modificado.")
                                .append("\r\n \r\nFecha límite de envío anterior: ")
                                .append(sdf.format(backupFechaLimiteEnvio)).append("\r\nFecha límite de envío nueva: ")
                                .append(sdf.format(solicitudDocumentacionPrevia.getFechaLimiteEnvio()))
                                .append("\r\n \r\nMuchas gracias y un saludo.");
                String cuerpo = ASUNTO + solicitudDocumentacionPrevia.getAsunto() + textoAutomatico;
                
                correoElectronico.envioCorreo(solicitudDocumentacionPrevia.getCorreoDestinatario(), asunto.toString(),
                        cuerpo);
                mensajeCorreoEnviado = ". Se ha comunicado al destinatario de la unidad el cambio de fecha.";
            }
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                    "La solicitud de documentación ha sido modificada con éxito" + mensajeCorreoEnviado);
            
            String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero();
            
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.DOCUMENTACION.name());
            
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al modificar la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
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
                
                solicitudDocumentacionService.transaccSaveCreaUsuarioProv(solicitudDocumentacionPrevia, usuarioProv);
                
                StringBuilder asunto = new StringBuilder(DESCRIPCION)
                        .append(solicitudDocumentacionPrevia.getInspeccion().getNumero());
                StringBuilder textoAutomatico = new StringBuilder(
                        "\r\n \r\nPara cumplimentar la solicitud de documentación previa debe conectarse a la aplicación PROGESIN. El enlace de acceso a la aplicación es ")
                                .append(applicationBean.getMapaParametros().get("URLPROGESIN")
                                        .get(solicitudDocumentacionPrevia.getInspeccion().getAmbito().name()))
                                .append(", su usuario de acceso es su correo electrónico y la contraseña es ")
                                .append(password)
                                .append(". \r\n \r\nUna vez enviada la solicitud cumplimentada su usuario quedará inactivo. \r\n \r\n")
                                .append("Muchas gracias y un saludo.");
                String cuerpo = ASUNTO + solicitudDocumentacionPrevia.getAsunto() + textoAutomatico;
                correoElectronico.envioCorreo(solicitudDocumentacionPrevia.getCorreoDestinatario(), asunto.toString(),
                        cuerpo);
                
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Envío",
                        "Se ha enviado con éxito la solicitud de documentación");
                
                String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero()
                        + " enviada";
                
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                        SeccionesEnum.DOCUMENTACION.name());
                
                List<RoleEnum> listRoles = new ArrayList<>();
                listRoles.add(RoleEnum.ROLE_SERVICIO_APOYO);
                listRoles.add(RoleEnum.ROLE_EQUIPO_INSPECCIONES);
                notificacionService.crearNotificacionRol(descripcion, SeccionesEnum.DOCUMENTACION.name(), listRoles);
            }
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al enviar la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
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
            
            String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero() + "finalizada";
            
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.DOCUMENTACION.name());
            
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al finalizar la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
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
            
            StringBuilder asunto = new StringBuilder(DESCRIPCION)
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
            String cuerpo = ASUNTO + solicitudDocumentacionPrevia.getAsunto() + textoAutomatico;
            
            correoElectronico.envioCorreo(solicitudDocumentacionPrevia.getCorreoDestinatario(), asunto.toString(),
                    cuerpo);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "No Conforme",
                    "Declarada no conforme con éxito la solicitud de documentación. El destinatario de la unidad será notificado y reactivado su acceso al sistema");
            
            String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero()
                    + " declarada no conforme";
            
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.DOCUMENTACION.name());
            
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al declarar no conforme la solicitud, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
        }
    }
    
    /**
     * Devuelve al formulario de búsqueda de solicitudes de documentación previa a su estado inicial y borra los
     * resultados de búsquedas anteriores si se navega desde el menú u otra sección.
     * 
     * 
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
     * 
     */
    public void limpiarBusqueda() {
        solicitudDocPreviaBusqueda.resetValues();
        model.setRowCount(0);
    }
    
    /**
     * Busca las solicitudes de documentación previa según los filtros introducidos en el formulario de búsqueda.
     * 
     * 
     */
    public void buscarSolicitudDocPrevia() {
        
        model.setBusqueda(solicitudDocPreviaBusqueda);
        model.load(0, 20, "fechaAlta", SortOrder.DESCENDING, null);
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
            setFile(pdfGenerator.imprimirSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia,
                    listadoDocumentosPrevios));
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error en la generación del PDF");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
        }
    }
    
    /**
     * Comprueba si no existen solicitudes o cuestionarios sin finalizar asociados a la inspeccion de esta solicitud.
     * 
     * @return boolean Respuesta de la comprobación
     */
    private boolean inspeccionSinTareasPendientes() {
        Inspeccion inspeccion = solicitudDocumentacionPrevia.getInspeccion();
        SolicitudDocumentacionPrevia solicitudPendiente = solicitudDocumentacionService
                .findNoFinalizadaPorInspeccion(inspeccion);
        if (solicitudPendiente != null) {
            String mensaje = "No se puede crear la solicitud ya que existe otra solicitud en curso para esta inspección. "
                    + "Debe finalizarla o anularla antes de proseguir.";
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, mensaje, "", null);
        }
        CuestionarioEnvio cuestionarioPendiente = cuestionarioEnvioService.findNoFinalizadoPorInspeccion(inspeccion);
        if (cuestionarioPendiente != null) {
            String mensaje = "No se puede crear la solicitud ya que existe un cuestionario en curso para esta inspección. "
                    + "Debe finalizarlo o anularlo antes de proseguir.";
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, mensaje, "", null);
        }
        return solicitudPendiente == null && cuestionarioPendiente == null;
    }
    
    /**
     * Comprueba si no existen solicitudes o cuestionarios sin finalizar asignados al correo electrónico elegido para
     * esta solicitud.
     * 
     * @return boolean Respuesta de la comprobación
     */
    private boolean usuarioSinTareasPendientes() {
        
        String correoDestinatario = solicitudDocumentacionPrevia.getCorreoDestinatario();
        SolicitudDocumentacionPrevia solicitudPendiente = solicitudDocumentacionService
                .findNoFinalizadaPorCorreoDestinatario(correoDestinatario);
        
        if (solicitudPendiente != null) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "No se puede crear una solicitud para el destinatario con correo " + correoDestinatario
                            + ", ya existe otra solicitud en curso para la inspeccion "
                            + solicitudPendiente.getInspeccion().getNumero()
                            + ". Debe finalizarla o anularla antes de proseguir.",
                    "", null);
        }
        CuestionarioEnvio cuestionarioPendiente = cuestionarioEnvioService
                .findNoFinalizadoPorCorreoEnvio(correoDestinatario);
        if (cuestionarioPendiente != null) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "No se puede crear una solicitud para el destinatario con correo " + correoDestinatario
                            + ", ya existe un cuestionario en curso para la inspeccion "
                            + cuestionarioPendiente.getInspeccion().getNumero()
                            + ". Debe finalizarlo o anularlo antes de proseguir.",
                    "", null);
        }
        return solicitudPendiente == null && cuestionarioPendiente == null;
    }
    
}
