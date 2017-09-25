package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean para las notificaciones.
 * @author EZENTIS
 */
@Setter
@Getter
@Component("notificacionesBean")
@Scope(FacesViewScope.NAME)
public class NotificacionesBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Lista de booleanos para controlar la visualziación de columnas en la vista.
     */
    private List<Boolean> list;
    
    /**
     * Servicio de notificaciones.
     */
    @Autowired
    transient INotificacionService notificacionService;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    transient IRegistroActividadService regActividadService;
    
    /**
     * Número de columnas en la vista.
     */
    private int numColListNotif = 4;
    
    /**
     * 
     * Controla las columnas visibles en la lista de resultados del buscador.
     *
     * @param e evento toggle de la vista
     *
     */
    
    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
    
    /**
     * Inicializa el bean.
     *
     */
    
    @PostConstruct
    public void init() {
        
        list = new ArrayList<>();
        for (int i = 0; i <= numColListNotif; i++) {
            list.add(Boolean.TRUE);
        }
    }
    
}
