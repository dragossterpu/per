package es.mira.progesin.web.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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

/******************************************************
 * 
 * Bean para los mensajes (Alertas y notificaciones)
 * @author EZENTIS
 * 
 ******************************************************/

@Setter
@Getter
@Controller("AlertasNotificacionesUsuarioBean")
@Scope(FacesViewScope.NAME)
public class AlertasNotificacionesUsuarioBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private transient IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
    
    @Autowired
    private transient IRegistroActividadService regActividad;
    
    @Autowired
    private transient IAlertaService alertaService;
    
    @Autowired
    private transient INotificacionService notificacionService;
    
    private LazyModelAlertas modelAlertas;
    
    private LazyModelNotificaciones modelNotificaciones;
    
    /******************************************************
     * 
     * Inicializa el bean
     * 
     ******************************************************/
    
    @PostConstruct
    public void init() {
        setModelAlertas(
                new LazyModelAlertas(alertaService, SecurityContextHolder.getContext().getAuthentication().getName()));
        setModelNotificaciones(new LazyModelNotificaciones(notificacionService,
                SecurityContextHolder.getContext().getAuthentication().getName()));
    }
    
    /******************************************************
     * 
     * Elimina, para el usuario logado, la alerta pasada como parámetro
     * 
     * @param alerta a eliminar
     * 
     ******************************************************/
    
    public void eliminarAlertas(Alerta alerta) {
        try {
            alertasNotificacionesUsuarioService.delete(SecurityContextHolder.getContext().getAuthentication().getName(),
                    alerta.getIdAlerta(), TipoMensajeEnum.ALERTA);
            String descripcion = "Se ha eliminado la alerta :" + alerta.getDescripcion();
            regActividad.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(), "Alertas");
        } catch (Exception e) {
            regActividad.altaRegActividadError("Alertas", e);
        }
        
    }
    
    /******************************************************
     * 
     * Elimina, para el usuario logado, la notificación pasada como parámetro
     * 
     * @param notificacion a eliminar
     * 
     ******************************************************/
    
    public void eliminarNotificacion(Notificacion notificacion) {
        try {
            alertasNotificacionesUsuarioService.delete(SecurityContextHolder.getContext().getAuthentication().getName(),
                    notificacion.getIdNotificacion(), TipoMensajeEnum.NOTIFICACION);
            String descripcion = "Se ha eliminado la notificación :" + notificacion.getDescripcion();
            regActividad.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(), "Notificaciones");
        } catch (Exception e) {
            regActividad.altaRegActividadError("Notificaciones", e);
        }
        
    }
    
}
