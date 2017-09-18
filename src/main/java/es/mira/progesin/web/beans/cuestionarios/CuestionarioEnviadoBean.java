package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.CorreoException;
import es.mira.progesin.lazydata.LazyModelCuestionarioEnviado;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IRespuestaCuestionarioService;
import es.mira.progesin.services.ITipoInspeccionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador de las operaciones relacionadas con los cuestionarios enviados. Búsqueda de cuestionarios enviados,
 * validación de sus respuestas por parte del equipo de inspecciones, reenvío a la unidad en cuestión en caso de no
 * conformidad, finalización del cuestionario cuando todas las respuestas estén validadas y, eventualmente, anulación de
 * cuestionarios.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
@Controller("cuestionarioEnviadoBean")
@Scope("session")
public class CuestionarioEnviadoBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constante para mensaje repetitivo.
     */
    private static final String DESCRIPCION = "Cuestionario para la inspección ";
    
    /**
     * Servicio de respuestas de cuestionario.
     */
    @Autowired
    private transient IRespuestaCuestionarioService respuestaService;
    
    /**
     * Objeto con parámetros de búsqueda de cuestionarios enviados.
     */
    private CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda;
    
    /**
     * Bean para mostrar formulario del cuestionario.
     */
    @Autowired
    private VisualizarCuestionario visualizarCuestionario;
    
    /**
     * Variable auxiliar para validar modificaciones de la fecha limite de cumplimentacion del cuestionario.
     */
    private Date backupFechaLimiteCuestionario;
    
    /**
     * Variable utilizada para almacenar el resultado de mostrar una columna o no en la tabla de búsqueda de
     * inspecciones.
     * 
     */
    private List<Boolean> list;
    
    /**
     * Servicio de cuestionarios enviados.
     */
    @Autowired
    private transient ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Bean para el envio de cuestionarios.
     */
    @Autowired
    private EnvioCuestionarioBean envioCuestionarioBean;
    
    /**
     * Servicio de correos electrónicos.
     */
    @Autowired
    private transient ICorreoElectronico correoElectronico;
    
    /**
     * Servicio del registro de actividad.
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Servicio de notificaciones.
     */
    @Autowired
    private transient INotificacionService notificacionService;
    
    /**
     * Servicio de tipos de inspección.
     */
    @Autowired
    private transient ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Objeto que define la tabla de resultados del buscador.
     */
    private LazyModelCuestionarioEnviado model;
    
    /**
     * Número de columnas de la vista.
     */
    private static final int NUMCOLSTABLA = 14;
    
    /**
     * Busca un cuestionario enviado a partir de los parámetros seleccionados por el usuario en el formulario.
     */
    public void buscarCuestionario() {
        model.setBusqueda(cuestionarioEnviadoBusqueda);
        model.load(0, Constantes.TAMPAGINA, "fechaEnvio", SortOrder.DESCENDING, null);
        
    }
    
    /**
     * Devuelve al formulario de búsqueda de modelos de cuestionario a su estado inicial y borra los resultados de
     * búsquedas anteriores si se navega desde el menú u otra sección.
     * @return ruta siguiente
     */
    public String getFormBusquedaCuestionarios() {
        limpiar();
        return "/cuestionarios/busquedaCuestionarios?faces-redirect=true";
    }
    
    /**
     * Resetea los valores de búsqueda introducidos en el formulario y el resultado de la búsqueda.
     */
    public void limpiar() {
        cuestionarioEnviadoBusqueda = new CuestionarioEnviadoBusqueda();
        model.setRowCount(0);
    }
    
    /**
     * Elimina un cuestionario seleccionado en los resultados de la búsqueda.
     * 
     * @param cuestionario a eliminar
     */
    public void eliminarCuestionario(CuestionarioEnvio cuestionario) {
        try {
            
            User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<RoleEnum> rolesAdmitidos = new ArrayList<>();
            rolesAdmitidos.add(RoleEnum.ROLE_JEFE_INSPECCIONES);
            rolesAdmitidos.add(RoleEnum.ROLE_ADMIN);
            if (cuestionario.getFechaAnulacion() == null
                    && (rolesAdmitidos.contains(usuarioActual.getRole()) || usuarioActual.getUsername()
                            .equals(cuestionario.getInspeccion().getEquipo().getJefeEquipo().getUsername()))) {
                cuestionario.setUsernameAnulacion(usuarioActual.getUsername());
                cuestionario.setFechaAnulacion(new Date());
                cuestionarioEnvioService.transaccSaveElimUsuariosProv(cuestionario);
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Eliminación",
                        "Se ha eliminado con éxito el cuestionario", null);
                
                String descripcion = DESCRIPCION + cuestionario.getInspeccion().getNumero();
                
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                        SeccionesEnum.CUESTIONARIO.getDescripcion());
                
                if (cuestionario.getFechaFinalizacion() == null) {
                    
                    String asunto = "Baja cuestionario " + cuestionario.getInspeccion().getTipoUnidad().getDescripcion()
                            + " de " + cuestionario.getInspeccion().getNombreUnidad() + " ("
                            + cuestionario.getInspeccion().getMunicipio().getProvincia().getNombre()
                            + "). Número de expediente " + cuestionario.getInspeccion().getNumero() + ".";
                    
                    Map<String, String> mapa = null;
                    correoElectronico.envioCorreo(cuestionario.getCorreoEnvio(), asunto,
                            Constantes.TEMPLATEBAJACUESTIONARIO, mapa);
                }
                
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_WARN, "Eliminación abortada",
                        "Ya ha sido anulado con anterioridad o no tiene permisos para realizar esta acción", null);
            }
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al eliminar el cuestionario, inténtelo de nuevo más tarde", null);
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
        }
    }
    
    /**
     * PostConstruct, inicializa el bean.
     */
    @PostConstruct
    public void init() {
        setList(new ArrayList<>());
        for (int i = 0; i <= NUMCOLSTABLA; i++) {
            list.add(Boolean.TRUE);
        }
        setCuestionarioEnviadoBusqueda(new CuestionarioEnviadoBusqueda());
        setModel(new LazyModelCuestionarioEnviado(cuestionarioEnvioService));
    }
    
    /**
     * Guarda fecha validación de las respuestas de las preguntas marcadas por el usuario. En caso de haberse completado
     * la validación de todas las respuestas se da por finalizado el cuestionario.
     */
    public void validarRespuestas() {
        try {
            String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
            Date fechaActual = new Date();
            CuestionarioEnvio cuestionario = visualizarCuestionario.getCuestionarioEnviado();
            List<RespuestaCuestionario> listaRespuestasTotales = visualizarCuestionario.getListaRespuestas();
            List<RespuestaCuestionario> listaRespuestasValidadas = new ArrayList<>();
            List<RespuestaCuestionario> listaRespuestasValidadasAnteriormente = new ArrayList<>();
            Map<PreguntasCuestionario, Boolean> mapaValidacionRespuestas = visualizarCuestionario
                    .getMapaValidacionRespuestas();
            
            listaRespuestasTotales.forEach(respuesta -> {
                if (mapaValidacionRespuestas.get(respuesta.getRespuestaId().getPregunta())) {
                    if (respuesta.getFechaValidacion() == null) {
                        respuesta.setUsernameValidacion(usuarioActual);
                        respuesta.setFechaValidacion(fechaActual);
                        listaRespuestasValidadas.add(respuesta);
                    } else {
                        listaRespuestasValidadasAnteriormente.add(respuesta);
                    }
                }
            });
            
            if (listaRespuestasValidadas.isEmpty() == Boolean.FALSE) {
                respuestaService.transaccSaveConRespuestas(listaRespuestasValidadas);
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Validación",
                        "Se ha validado con éxito las respuestas");
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Validación abortada",
                        "Debe marcar al menos una respuesta", null);
            }
            
            if (listaRespuestasValidadas.size() + listaRespuestasValidadasAnteriormente.size() == listaRespuestasTotales
                    .size()) {
                cuestionario.setUsernameFinalizacion(usuarioActual);
                cuestionario.setFechaFinalizacion(fechaActual);
                cuestionario.setFechaNoConforme(null);
                cuestionarioEnvioService.transaccSaveElimUsuariosProv(cuestionario);
                
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Finalización",
                        "Cuestionario finalizado con éxito, todas sus respuestas han sido validadas");
                
                String descripcion = DESCRIPCION + cuestionario.getInspeccion().getNumero() + " finalizado";
                
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                        SeccionesEnum.CUESTIONARIO.getDescripcion());
                
                List<RoleEnum> rolesANotificar = new ArrayList<>();
                rolesANotificar.add(RoleEnum.ROLE_SERVICIO_APOYO);
                rolesANotificar.add(RoleEnum.ROLE_JEFE_INSPECCIONES);
                notificacionService.crearNotificacionRol(descripcion, SeccionesEnum.CUESTIONARIO.getDescripcion(),
                        rolesANotificar);
                notificacionService.crearNotificacionEquipo(descripcion, SeccionesEnum.CUESTIONARIO.getDescripcion(),
                        cuestionario.getInspeccion().getEquipo());
            }
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al validar las respuestas, inténtelo de nuevo más tarde.");
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
        }
        
    }
    
    /**
     * Permite al jefe de equipo declarar no conforme un cuestionario enviado ya cumplimentada, después de revisar las
     * respuestas y la documentación adjuntada por la unidad que se va a inspeccionar. Para ello se elimina la fecha de
     * cumplimentación y reenvia el cuestionario al destinatario de la unidad con el motivo de dicha no conformidad.
     * Adicionalmente reactiva los usuarios provisinales que se usaron para llevarlo a cabo.
     * 
     * @param motivosNoConforme texto introducido por el usuario
     */
    public void noConformeCuestionario(String motivosNoConforme) {
        try {
            CuestionarioEnvio cuestionario = visualizarCuestionario.getCuestionarioEnviado();
            cuestionario.setFechaCumplimentacion(null);
            cuestionario.setFechaNoConforme(new Date());
            String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
            cuestionario.setUsernameNoConforme(usuarioActual);
            cuestionarioEnvioService.transaccSaveActivaUsuariosProv(cuestionario);
            
            String asunto = "No validación del cuestionario  "
                    + cuestionario.getInspeccion().getTipoUnidad().getDescripcion() + " de "
                    + cuestionario.getInspeccion().getNombreUnidad() + "("
                    + cuestionario.getInspeccion().getMunicipio().getProvincia().getNombre()
                    + "). Número de expediente " + cuestionario.getInspeccion().getNumero() + ".";
            Map<String, String> paramPlantilla = new HashMap<>();
            paramPlantilla.put("textoNoValidacion", motivosNoConforme);
            
            correoElectronico.envioCorreo(cuestionario.getCorreoEnvio(), asunto,
                    Constantes.TEMPLATENOCONFORMECUESTIONARIO, paramPlantilla);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "No Conforme",
                    "Declarado no conforme con éxito el cuestionario. El destinatario de la unidad será notificado y reactivado su acceso al sistema");
            
            regActividadService.altaRegActividad(asunto, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.CUESTIONARIO.getDescripcion());
            
            notificacionService.crearNotificacionRol(asunto, SeccionesEnum.CUESTIONARIO.getDescripcion(),
                    RoleEnum.ROLE_SERVICIO_APOYO);
            
            notificacionService.crearNotificacionEquipo(asunto, SeccionesEnum.CUESTIONARIO.getDescripcion(),
                    cuestionario.getInspeccion().getEquipo());
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al declarar no conforme el cuestionario, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e);
        } catch (CorreoException e2) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    Constantes.FALLOCORREO);
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e2);
        }
    }
    
    /**
     * Pasa los datos de envio del cuestionario que queremos modificar al formulario de envio para que cambien los
     * valores que quieran. En este caso sólo la fecha límite de cumplimentación y envio por parte de la unidad.
     * 
     * @param cuestionario recuperado del formulario
     * @return vista enviarCuestionario
     * 
     */
    public String getFormModificarCuestionario(CuestionarioEnvio cuestionario) {
        envioCuestionarioBean.setCuestionarioEnvio(cuestionario);
        backupFechaLimiteCuestionario = cuestionario.getFechaLimiteCuestionario();
        return "/cuestionarios/enviarCuestionario?faces-redirect=true";
    }
    
    /**
     * Modifica los datos de envio de un cuestionario. En caso de que la fecha límite de envío por parte de la unidad
     * sea alterada, se notifica por correo electrónico dicho cambio.
     */
    public void modificarCuestionario() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            CuestionarioEnvio cuestionario = envioCuestionarioBean.getCuestionarioEnvio();
            cuestionarioEnvioService.save(cuestionario);
            String mensajeCorreoEnviado = "";
            // Avisar al destinatario si la fecha limite para la solicitud ha cambiado
            if (cuestionario.getFechaEnvio() != null && !sdf.format(backupFechaLimiteCuestionario)
                    .equals(sdf.format(cuestionario.getFechaLimiteCuestionario()))) {
                
                String asunto = "Modificación plazo envío cuestionario  "
                        + cuestionario.getInspeccion().getTipoUnidad().getDescripcion() + " de "
                        + cuestionario.getInspeccion().getNombreUnidad() + "("
                        + cuestionario.getInspeccion().getMunicipio().getProvincia().getNombre()
                        + "). Número de expediente " + cuestionario.getInspeccion().getNumero() + ".";
                Map<String, String> paramPlantilla = new HashMap<>();
                paramPlantilla.put("fechaAnterior", sdf.format(backupFechaLimiteCuestionario));
                paramPlantilla.put("fechaActual", sdf.format(cuestionario.getFechaLimiteCuestionario()));
                correoElectronico.envioCorreo(cuestionario.getCorreoEnvio(), asunto,
                        Constantes.TEMPLATEMODIFICACIONFECHACUESTIONARIO, paramPlantilla);
                
                mensajeCorreoEnviado = ". Se ha comunicado al destinatario de la unidad el cambio de fecha.";
            }
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                    "El cuestionario ha sido modificado con éxito" + mensajeCorreoEnviado);
            
            String descripcion = DESCRIPCION + cuestionario.getInspeccion().getNumero();
            
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.CUESTIONARIO.getDescripcion());
            
        } catch (DataAccessException e1) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al modificar el cuestionario, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e1);
        } catch (CorreoException e2) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    Constantes.FALLOCORREO);
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.getDescripcion(), e2);
        }
    }
    
    /**
     * Controla las columnas visibles en la lista de resultados del buscador.
     * 
     * @param e checkbox de la columna seleccionada
     */
    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
}
