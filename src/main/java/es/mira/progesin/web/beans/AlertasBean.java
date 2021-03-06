package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.services.IAlertasNotificacionesUsuarioService;
import es.mira.progesin.services.IRegistroActividadService;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Bean para las alertas.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
@Controller("alertasBean")
@Scope(FacesViewScope.NAME)
public class AlertasBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Servicio de alertas y notificaciones.
     */
    @Autowired
    transient IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    transient IRegistroActividadService regActividad;
    
    /**
     * Lista de las alertas.
     */
    private List<Alerta> listaAlertas = new ArrayList<>();
    
    /**
     * Lista de booleanos para el controlar las columnas visibles en la vista.
     */
    private List<Boolean> list;
    
    /**
     * Número de columnas de la vista.
     */
    private int numColListAlert = 5;
    
    /**
     * 
     * Inicializa el listado de alertas para el usuario logado.
     * 
     */
    
    private void initList() {
        listaAlertas = alertasNotificacionesUsuarioService
                .findAlertasByUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    
    /**
     * 
     * Controla las columnas visibles en la lista de resultados del buscador.
     * 
     * @param e Evento ToggleEvent de la vista
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
        
        initList();
        list = new ArrayList<>();
        for (int i = 0; i <= numColListAlert; i++) {
            list.add(Boolean.TRUE);
        }
    }
    
}
