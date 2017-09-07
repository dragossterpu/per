package es.mira.progesin.web.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.lazydata.LazyModelAlertas;
import es.mira.progesin.lazydata.LazyModelNotificaciones;
import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.IAlertasNotificacionesUsuarioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Bean para los mensajes (Alertas y notificaciones).
 * 
 * @author EZENTIS
 * 
 */

@Setter
@Getter
@Controller("AlertasNotificacionesUsuarioBean")
@Scope(FacesViewScope.NAME)
public class AlertasNotificacionesUsuarioBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Servicio de alertas y notificaciones.
     */
    @Autowired
    private transient IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
    
    /**
     * Servicio del registro de actividad.
     */
    @Autowired
    private transient IRegistroActividadService regActividad;
    
    /**
     * Servicio de alertas.
     */
    @Autowired
    private transient IAlertaService alertaService;
    
    /**
     * Servicio de notificaciones.
     */
    @Autowired
    private transient INotificacionService notificacionService;
    
    /**
     * LazyModel para la visualización de las alertas.
     */
    private LazyModelAlertas modelAlertas;
    
    /**
     * LazyModel para la visualización de las notificaciones.
     */
    private LazyModelNotificaciones modelNotificaciones;
    
    /**
     * Objeto que se usará para visualizar los registros.
     */
    private Notificacion notificacion;
    
    /**
     * Inicializa el bean.
     */
    
    @PostConstruct
    public void init() {
        setModelAlertas(
                new LazyModelAlertas(alertaService, SecurityContextHolder.getContext().getAuthentication().getName()));
        setModelNotificaciones(new LazyModelNotificaciones(notificacionService,
                SecurityContextHolder.getContext().getAuthentication().getName()));
    }
    
    /**
     * 
     * Elimina, para el usuario logado, la alerta pasada como parámetro.
     * 
     * @param alerta Alerta a eliminar
     * 
     */
    
    public void eliminarAlertas(Alerta alerta) {
        try {
            alertasNotificacionesUsuarioService.delete(SecurityContextHolder.getContext().getAuthentication().getName(),
                    alerta.getIdAlerta(), TipoMensajeEnum.ALERTA);
            String descripcion = "Se ha eliminado la alerta :" + alerta.getDescripcion();
            regActividad.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(), "Alertas");
        } catch (DataAccessException e) {
            regActividad.altaRegActividadError("Alertas", e);
        }
        
    }
    
    /**
     * 
     * Elimina, para el usuario logado, la notificación pasada como parámetro.
     * 
     * @param notif Notificación a eliminar
     * 
     */
    
    public void eliminarNotificacion(Notificacion notif) {
        try {
            alertasNotificacionesUsuarioService.delete(SecurityContextHolder.getContext().getAuthentication().getName(),
                    notif.getIdNotificacion(), TipoMensajeEnum.NOTIFICACION);
            String descripcion = "Se ha eliminado la notificación :" + notif.getDescripcion();
            regActividad.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(), "Notificaciones");
        } catch (DataAccessException e) {
            regActividad.altaRegActividadError("Notificaciones", e);
        }
        
    }
    
    /**
     * Guarda el registro de actividad seleccionado por el usuario en la vista en una variable para que se muestre en un
     * dialog.
     * 
     * 
     */
    
    public void onRowSelect() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg').show();");
    }
}
