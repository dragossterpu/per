package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.CorreoException;
import es.mira.progesin.exceptions.ExcepcionRollback;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.Utilities;
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
     * Servicio de cuestionarios enviados.
     */
    @Autowired
    private transient ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Servicio de notificaciones.
     */
    @Autowired
    private transient INotificacionService notificacionService;
    
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
            String password = Utilities.getPassword();
            String correoEnvio = cuestionarioEnvio.getCorreoEnvio();
            List<User> listaUsuariosProvisionales = userService.crearUsuariosProvisionalesCuestionario(correoEnvio,
                    password);
            
            StringBuilder usuariosProvisionales = new StringBuilder();
            listaUsuariosProvisionales.forEach(userPorv -> {
                usuariosProvisionales.append(userPorv.getUsername()).append("<br/>");
            });
            
            Map<String, String> paramPlantilla = new HashMap<>();
            paramPlantilla.put("textoEnvioCuestionario", cuestionarioEnvio.getMotivoCuestionario());
            paramPlantilla.put("correosProvisionales", usuariosProvisionales.toString());
            paramPlantilla.put("password", password);
            paramPlantilla.put("correoPrincipal", listaUsuariosProvisionales.get(0).getUsername());
            paramPlantilla.put("fechaLimite",
                    Utilities.getFechaFormateada(cuestionarioEnvio.getFechaLimiteCuestionario(), "dd/MM/yyyy"));
            cuestionarioEnvioService.crearYEnviarCuestionario(listaUsuariosProvisionales, cuestionarioEnvio,
                    paramPlantilla);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "",
                    "El cuestionario se ha enviado con éxito");
            
            String descripcion = "Se ha enviado el cuestionario de la inspección: "
                    + cuestionarioEnvio.getInspeccion().getNumero() + " correctamente.";
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.CUESTIONARIO.getDescripcion());
            
            List<RoleEnum> rolesNotif = new ArrayList<>();
            rolesNotif.add(RoleEnum.ROLE_JEFE_INSPECCIONES);
            rolesNotif.add(RoleEnum.ROLE_SERVICIO_APOYO);
            notificacionService.crearNotificacionRol(descripcion, SeccionesEnum.CUESTIONARIO.getDescripcion(),
                    rolesNotif);
            
            notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.CUESTIONARIO.getDescripcion(),
                    cuestionarioEnvio.getInspeccion().getEquipo());
            
        } catch (DataAccessException | CorreoException | ExcepcionRollback e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, e.getMessage(), Constantes.FALLOCORREO,
                    null);
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
        }
    }
    
    /**
     * Adjunta las plantillas seleccionadas al cuestionario enviado.
     * 
     * @return url de la página donde se adjuntan las plantillas.
     */
    public String adjuntarPlantilla() {
        cuestionarioEnvio.setPlantillas(plantillasSeleccionadas);
        return "/cuestionarios/enviarCuestionario?faces-redirect=true";
    }
}
