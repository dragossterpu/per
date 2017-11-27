package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.lazydata.LazyModelRegistro;
import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean para la gestión del registro de actividad.
 * 
 * @author EZENTIS
 *
 */

@Setter
@Getter
@Controller("regActividadBean")
@Scope("session")
public class RegActividadBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Lista de booleanos para controlar la visualización de columnas en la vista.
     */
    private List<Boolean> list;
    
    /**
     * Objeto que se usará para visualizar los registros.
     */
    private RegistroActividad error;
    
    /**
     * Número de columnas en la vista.
     */
    private Integer numColListRegActividad = 5;
    
    /**
     * Objeto que contendrá el parámetros de búsqueda.
     */
    private RegActividadBusqueda regActividadBusqueda;
    
    /**
     * LazyModel para la visualización paginada de datos en la vista.
     */
    private LazyModelRegistro model;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    transient IRegistroActividadService regActividadService;
    
    /**
     * 
     * Busca en el registro de actividad según los criterios elegidos por el usuario en la vista y carga los resultados
     * en una lista para su visualización.
     * 
     */
    
    public void buscarRegActividad() {
        model.setBusqueda(regActividadBusqueda);
        model.load(0, Constantes.TAMPAGINA, Constantes.FECHAALTA, SortOrder.DESCENDING, null);
        
    }
    
    /**
     * Controla las columnas visibles en la lista de resultados del buscador.
     * 
     * @param e Evento toggle
     * 
     */
    
    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
    
    /**
     * Limpia los parámetros de búsqueda y resultado si se accede a la página desde el menú lateral.
     * @return ruta siguiente
     * 
     */
    
    public String getFormularioRegActividad() {
        limpiarBusqueda();
        return "/administracion/registro/registro?faces-redirect=true";
    }
    
    /**
     * Limpia los parámetros de búsqueda y el resultado.
     * 
     */
    
    public void limpiarBusqueda() {
        regActividadBusqueda = new RegActividadBusqueda();
        model.setRowCount(0);
    }
    
    /**
     * Inicializa el bean.
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
        
        Utilities.limpiarSesion("regActividadBean");
    }
    
    /**
     * Devuelve una lista con las nombre de usuario que contengan la cadena de texto que se recibe como parámetro.
     * 
     * @param infoUsuario Usuario por el que se hace la consulta
     * @return Listado de resultados
     * 
     */
    
    public List<String> autocompletarUsuario(String infoUsuario) {
        return regActividadService.buscarPorUsuarioRegistro("%" + infoUsuario + "%");
    }
    
    /**
     * Guarda el registro la notificación seleccionada por el usuario en la vista en una variable para que se muestre en
     * un dialog.
     * 
     * 
     */
    
    public void onRowSelect() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg').show();");
        
    }
    
}
