package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.lazydata.LazyModelRegistro;
import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.services.IRegistroActividadService;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Bean para la gestión del registro de actividad.
 * 
 * @author Ezentis
 *
 */

@Setter
@Getter
@Controller("regActividadBean")
@Scope("session")
public class RegActividadBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<Boolean> list;
    
    private RegistroActividad error;
    
    private Integer numColListRegActividad = 5;
    
    private RegActividadBusqueda regActividadBusqueda;
    
    private String vieneDe;
    
    private LazyModelRegistro model;
    
    @Autowired
    transient IRegistroActividadService regActividadService;
    
    /**
     * 
     * Busca en el registro de actividad según los criterios elegidos por el usuario en la vista y carga los resultados
     * en una lista para su visualización
     * 
     */
    
    public void buscarRegActividad() {
        model.setBusqueda(regActividadBusqueda);
        model.load(0, 20, "fechaAlta", SortOrder.DESCENDING, null);
        
    }
    
    /**
     * 
     * Controla las columnas visibles en la lista de resultados del buscador
     * 
     * @param e Evento toggle
     * 
     */
    
    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
    
    /**
     * 
     * Limpia los parámetros de búsqueda y resultado si se accede a la página desde el menú lateral
     * 
     * 
     */
    
    public void getFormularioRegActividad() {
        if ("menu".equalsIgnoreCase(this.vieneDe)) {
            limpiarBusqueda();
            this.vieneDe = null;
        }
        
    }
    
    /**
     * 
     * Limpia los parámetros de búsqueda y el resultado
     * 
     */
    
    public void limpiarBusqueda() {
        regActividadBusqueda.resetValues();
        model.setRowCount(0);
    }
    
    /**
     * 
     * Inicializa el bean
     * 
     */
    @PostConstruct
    public void init() {
        regActividadBusqueda = new RegActividadBusqueda();
        list = new ArrayList<>();
        for (int i = 0; i <= numColListRegActividad; i++) {
            list.add(Boolean.TRUE);
        }
        model = new LazyModelRegistro(regActividadService);
    }
    
    /**
     * 
     * Devuelve una lista con las secciones cuyo nombre contenga la cadena de texto que se recibe como parámetro
     * 
     * @param infoSeccion
     * @return List<String>
     * 
     */
    
    public List<String> autocompletarSeccion(String infoSeccion) {
        return regActividadService.buscarPorNombreSeccion("%" + infoSeccion + "%");
    }
    
    /**
     * 
     * Devuelve una lista con las nombre de usuario que contengan la cadena de texto que se recibe como parámetro
     * 
     * @param infoUsuario
     * @return List<String>
     * 
     */
    
    public List<String> autocompletarUsuario(String infoUsuario) {
        return regActividadService.buscarPorUsuarioRegistro("%" + infoUsuario + "%");
    }
    
    /**
     * 
     * Guarda el registro de actividad seleccionado por el usuario en la vista en una variable para que se muestre en un
     * dialog
     * 
     * @param event
     * 
     */
    
    public void onRowSelect(SelectEvent event) {
        setError((RegistroActividad) event.getObject());
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg').show();");
        
    }
    
}
