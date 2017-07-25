package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.exceptions.CorreoException;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.util.Utilities;
import es.mira.progesin.web.beans.ApplicationBean;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean asociado a la pantalla de envío de cuestionarios. Genera un cuestionario enviado a partir de un cuestionario
 * personalizado seleccionado por el usuario, da de alta los usuarios provisionales para que lo cumplimenten y les
 * notifica sus credenciales por correo electrónico.
 * 
 * @author EZENTIS
 *
 */
@Setter
@Getter
@Controller("envioCuestionarioBean")
@Scope("session")
public class EnvioCuestionarioBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Objeto para el envío de cuestionarios.
     */
    private CuestionarioEnvio cuestionarioEnvio;
    
    /**
     * Listado de las plantillas anexas al cuestionario.
     */
    private List<Documento> listaPlantillas;
    
    /**
     * Listado de las plantillas seleccionadas.
     */
    private List<Documento> plantillasSeleccionadas;
    
    /**
     * Servicio de inspecciones.
     */
    @Autowired
    private transient IInspeccionesService inspeccionService;
    
    /**
     * Servicio de Solicitud de documentación.
     */
    @Autowired
    private transient ISolicitudDocumentacionService solDocService;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Servicio de usuarios.
     */
    @Autowired
    private transient IUserService userService;
    
    /**
     * Encriptador de palabra clave.
     */
    @Autowired
    private transient PasswordEncoder passwordEncoder;
    
    /**
     * Servicio de cuestionarios enviados.
     */
    @Autowired
    private transient ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Bean de configuración de la aplicación.
     */
    @Autowired
    private ApplicationBean applicationBean;
    
    /**
     * Servicio de correo electrónico.
     */
    @Autowired
    private transient ICorreoElectronico correoElectronico;
    
    /**
     * Servicio de notificaciones.
     */
    @Autowired
    private transient INotificacionService notificacionService;
    
    /**
     * Repositorio de preguntas cuestionario.
     */
    @Autowired
    private transient IPreguntaCuestionarioRepository preguntasRepository;
    
    /**
     * Repositorio de configuración de respuestas.
     */
    @Autowired
    private transient IConfiguracionRespuestasCuestionarioRepository configRespuestas;
    
    /**
     * Devuelve una lista con las inspecciones cuyo número contiene alguno de los caracteres pasados como parámetro. Se
     * usa en el formulario de envío para el autocompletado.
     * 
     * @author EZENTIS
     * @param infoInspeccion Número de inspección que teclea el usuario en el formulario de envío o nombre de la unidad
     * de la inspección
     * @return lista de inspecciones que contienen algún caracter coincidente con el número introducido
     */
    public List<Inspeccion> autocompletarInspeccion(String infoInspeccion) {
        User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Inspeccion> resultadosBusqueda;
        if (RoleEnum.ROLE_EQUIPO_INSPECCIONES.equals(usuarioActual.getRole())) {
            resultadosBusqueda = inspeccionService.buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(infoInspeccion,
                    usuarioActual.getUsername());
        } else {
            resultadosBusqueda = inspeccionService.buscarNoFinalizadaPorNombreUnidadONumero(infoInspeccion);
        }
        return resultadosBusqueda;
    }
    
    /**
     * Completa los datos del formulario (correo, nombre, cargo, fecha límite) si el tipo de inspección asociada es de
     * tipo General Periódica y tiene una solicitud de documentación previa finalizada.
     * 
     * @author EZENTIS
     */
    public void completarDatosSolicitudPrevia() {
        if ("I.G.P.".equals(cuestionarioEnvio.getInspeccion().getTipoInspeccion().getCodigo())) {
            List<SolicitudDocumentacionPrevia> listaSolicitudes = solDocService
                    .findFinalizadasPorInspeccion(this.cuestionarioEnvio.getInspeccion());
            if (listaSolicitudes != null && listaSolicitudes.isEmpty() == Boolean.FALSE) {
                // Como está ordenado en orden descendente por fecha finalización, recupero la más reciente
                SolicitudDocumentacionPrevia solDocPrevia = listaSolicitudes.get(0);
                this.cuestionarioEnvio.setCorreoEnvio(solDocPrevia.getCorreoCorporativoInterlocutor());
                this.cuestionarioEnvio.setNombreUsuarioEnvio(solDocPrevia.getNombreCompletoInterlocutor());
                this.cuestionarioEnvio.setCargo(solDocPrevia.getCargoInterlocutor());
                this.cuestionarioEnvio.setFechaLimiteCuestionario(solDocPrevia.getFechaLimiteCumplimentar());
            } else {
                String mensaje = "No existe o no ha sido finalizada la solicitud de documentación previa para esta inspección general periódica. Debería tramitarla antes de enviar el cuestionario.";
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_WARN, mensaje, "", null);
            }
        }
    }
    
    /**
     * Guarda los datos introducidos en el formulario de envío en BBDD. Además crea 10 usuarios provisionales para el
     * destinatario del correo. Se envía un correo electrónico informando de la URL de acceso a la aplicación y la
     * contraseña de los 10 usuarios provisionales.
     * 
     * @author EZENTIS
     */
    public void enviarCuestionario() {
        try {
            // Comprobar que la inspeccion o el usuario no tengan solicitudes o cuestionarios sin finalizar
            if (inspeccionSinTareasPendientes() && usuarioSinTareasPendientes()) {
                String password = Utilities.getPassword();
                String correoEnvio = cuestionarioEnvio.getCorreoEnvio();
                if (userService.exists(correoEnvio)) {
                    FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Envío abortado",
                            "El usuario con correo " + correoEnvio + " ya existe en el sistema.");
                } else {
                    List<User> listaUsuariosProvisionales = userService
                            .crearUsuariosProvisionalesCuestionario(correoEnvio, password);
                    cuestionarioEnvioService.crearYEnviarCuestionario(listaUsuariosProvisionales, cuestionarioEnvio,
                            getCuerpoCorreo(password, listaUsuariosProvisionales));
                    
                    FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "",
                            "El cuestionario se ha enviado con éxito");
                    
                    String descripcion = "Se ha enviado el cuestionario de la inspección: "
                            + cuestionarioEnvio.getInspeccion().getNumero() + " correctamente.";
                    // Guardamos la actividad en bbdd
                    regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                            SeccionesEnum.CUESTIONARIO.getDescripcion());
                    
                    notificacionService.crearNotificacionRol(descripcion, SeccionesEnum.CUESTIONARIO.getDescripcion(),
                            RoleEnum.ROLE_JEFE_INSPECCIONES);
                    
                    notificacionService.crearNotificacionEquipo(descripcion,
                            SeccionesEnum.CUESTIONARIO.getDescripcion(), cuestionarioEnvio.getInspeccion().getEquipo());
                    
                }
            }
        } catch (DataAccessException | CorreoException e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "Se ha produdico un error en el envio del cuestionario", "", null);
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
        }
    }
    
    /**
     * Construye el correo.
     * 
     * @param password Password de entrada a la aplicación para los usuarios provisionales
     * @param usuarios Lista de usuarios provisionales.
     * @return Cuerpo del correo
     */
    private String getCuerpoCorreo(String password, List<User> usuarios) {
        String urlAcceso = applicationBean.getMapaParametros().get("URLPROGESIN")
                .get(cuestionarioEnvio.getInspeccion().getAmbito().name());
        
        StringBuilder textoAutomatico = new StringBuilder();
        textoAutomatico
                .append("\r\n \r\nPara responder el cuestionario debe conectarse a la aplicación PROGESIN. El enlace de acceso a la aplicación es '")
                .append(urlAcceso).append("'.\r\nLos usuarios de acceso a la aplicación son: \r\n\r\n");
        
        StringBuilder usuariosProvisionales = new StringBuilder();
        for (User usuario : usuarios) {
            usuariosProvisionales.append(usuario.getUsername()).append("\r\n");
        }
        textoAutomatico.append(usuariosProvisionales);
        textoAutomatico.append("\r\nLa contraseña de acceso para todos los usuarios es: ").append(password)
                .append(".\r\nSólo el usuario principal (").append(cuestionarioEnvio.getCorreoEnvio())
                .append(") podrá enviar el cuestionario, el resto de ")
                .append("usuarios solamente podrá guardar el cuestionario como borrador.")
                .append("\r\n \r\nUna vez enviado el cuestionario todos los usuarios quedarán inactivos. \r\n \r\n")
                .append("Muchas gracias y un saludo.");
        
        return textoAutomatico.toString();
    }
    
    /**
     * Comprueba si no existen solicitudes o cuestionarios sin finalizar asociados a la inspeccion de esta solicitud.
     * 
     * @author EZENTIS
     * @return boolean
     */
    public boolean inspeccionSinTareasPendientes() {
        Inspeccion inspeccion = cuestionarioEnvio.getInspeccion();
        boolean respuesta = true;
        SolicitudDocumentacionPrevia solicitudPendiente = solDocService.findNoFinalizadaPorInspeccion(inspeccion);
        if (solicitudPendiente != null) {
            String mensaje = "No se puede enviar el cuestionario ya que existe una solicitud en curso para esta inspección. "
                    + "Debe finalizarla o anularla antes de proseguir.";
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, mensaje, "", null);
            respuesta = false;
        }
        CuestionarioEnvio cuestionarioPendiente = cuestionarioEnvioService.findNoFinalizadoPorInspeccion(inspeccion);
        if (cuestionarioPendiente != null) {
            String mensaje = "No se puede enviar el cuestionario ya que existe otro cuestionario en curso para esta inspección. "
                    + "Debe finalizarlo o anularlo antes de proseguir.";
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, mensaje, "", null);
            respuesta = false;
        }
        return respuesta;
    }
    
    /**
     * Comprueba si no existen solicitudes o cuestionarios sin finalizar asignados al correo electrónico elegido para
     * esta solicitud.
     * 
     * @author EZENTIS
     * @return boolean
     */
    public boolean usuarioSinTareasPendientes() {
        String correoEnvio = cuestionarioEnvio.getCorreoEnvio();
        boolean respuesta = true;
        SolicitudDocumentacionPrevia solicitudPendiente = solDocService
                .findNoFinalizadaPorCorreoDestinatario(correoEnvio);
        if (solicitudPendiente != null) {
            
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "No se puede enviar el cuestionario al destinatario con correo " + correoEnvio
                            + ", ya existe una solicitud en curso para la inspeccion "
                            + solicitudPendiente.getInspeccion().getNumero()
                            + ". Debe finalizarla o anularla antes de proseguir.",
                    "", null);
            respuesta = false;
        }
        CuestionarioEnvio cuestionarioPendiente = cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(correoEnvio);
        if (cuestionarioPendiente != null) {
            
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "No se puede enviar el cuestionario al destinatario con correo " + correoEnvio
                            + ", ya existe otro cuestionario en curso para la inspeccion "
                            + cuestionarioPendiente.getInspeccion().getNumero()
                            + ". Debe finalizarlo o anularlo antes de proseguir.",
                    "", null);
            respuesta = false;
        }
        return respuesta;
    }
    
    /**
     * Adjunta las plantillas seleccionadas al cuestionario enviado.
     */
    public void adjuntarPlantilla() {
        cuestionarioEnvio.setPlantillas(plantillasSeleccionadas);
    }
}
