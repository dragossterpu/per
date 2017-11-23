package es.mira.progesin.web.beans.solicitudes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.FlowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.web.beans.ApplicationBean;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador de las operaciones relacionadas con la creación de solicitudes de documentación previas al envio de
 * cuestionarios.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia
 */
@Setter
@Getter
@Controller("solicitudDocPreviaCreacionBean")
@Scope("view")
public class SolicitudDocPreviaCreacionBean implements Serializable {
    
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
     * Casilla para saltar pestaña de selección de tipos de documentación en formulario crearsolicitud.
     */
    private boolean skip;
    
    /**
     * Lista de documentos seleccionados.
     */
    private List<TipoDocumentacion> documentosSeleccionados;
    
    /**
     * Lista de tipos de documentos.
     */
    private List<TipoDocumentacion> listadoDocumentos;
    
    /**
     * Mapa con los datos de apoyo.
     */
    private Map<String, String> datosApoyo;
    
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
     * Servicio de Cuestionarios enviados.
     */
    @Autowired
    private transient ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Crea una solicitud de documentación en base a los datos introducidos en el formulario de la vista crearSolicitud.
     * 
     */
    public void crearSolicitud() {
        
        try {
            // Comprobar que la inspeccion o el usuario no tengan solicitudes o cuestionarios sin finalizar
            if (inspeccionSinTareasPendientes() && usuarioSinTareasPendientes()) {
                
                StringBuilder asunto = new StringBuilder("Comunicación Inspección ")
                        .append(solicitudDocumentacionPrevia.getInspeccion().getTipoInspeccion().getDescripcion())
                        .append(" ")
                        .append(solicitudDocumentacionPrevia.getInspeccion().getTipoUnidad().getDescripcion())
                        .append(" de ").append(solicitudDocumentacionPrevia.getInspeccion().getNombreUnidad())
                        .append(" (")
                        .append(solicitudDocumentacionPrevia.getInspeccion().getMunicipio().getProvincia().getNombre())
                        .append(")");
                solicitudDocumentacionPrevia.setAsunto(asunto.toString());
                
                solicitudDocumentacionService.transaccSaveAltaDocumentos(solicitudDocumentacionPrevia,
                        documentosSeleccionados);
                
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                        "La solicitud de documentación ha sido creada con éxito");
                
                String descripcion = DESCRIPCION + solicitudDocumentacionPrevia.getInspeccion().getNumero();
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                        SeccionesEnum.DOCUMENTACION.getDescripcion());
            }
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al crear la solicitud, inténtelo de nuevo más tarde");
            // Guardamos los posibles errores en bbdd
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
    }
    
    /**
     * Obtener los datos del servicio de apoyo. Se encuentran almacenados en la tabla parametros para que puedan ser
     * modificados por el DBA
     * 
     * @see #getFormularioCrearSolicitud()
     * @see es.mira.progesin.persistence.entities.Parametro
     */
    private void datosApoyo() {
        solicitudDocumentacionPrevia.setApoyoCorreo(datosApoyo.get("ApoyoCorreo"));
        solicitudDocumentacionPrevia.setApoyoPuesto(datosApoyo.get("ApoyoPuesto"));
        solicitudDocumentacionPrevia.setApoyoTelefono(datosApoyo.get("ApoyoTelefono"));
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
        datosApoyo = applicationBean.getMapaParametros().get("datosApoyo");
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
        String siguientePaso = event.getNewStep();
        if ("general".equals(event.getOldStep()) && "documentacion".equals(event.getNewStep())) {
            if (inspeccionSinTareasPendientes() && usuarioSinTareasPendientes()) {
                AmbitoInspeccionEnum ambito = solicitudDocumentacionPrevia.getInspeccion().getAmbito();
                setListadoDocumentos(tipoDocumentacionService.findByAmbito(ambito));
            } else {
                siguientePaso = event.getOldStep();
            }
        } else if ("documentacion".equals(event.getOldStep()) && "apoyo".equals(event.getNewStep())
                && documentosSeleccionados.isEmpty() && skip == Boolean.FALSE) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "Debe elegir uno o más documentos o confirmar que no desea ninguno", "", "");
            siguientePaso = event.getOldStep();
        }
        return siguientePaso;
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
