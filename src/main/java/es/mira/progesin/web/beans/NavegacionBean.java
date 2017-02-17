package es.mira.progesin.web.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component("navegacionBean")
@Scope("session")
@Getter
@Setter
@NoArgsConstructor
public class NavegacionBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private MenuModel caminoMigas;
    
    @PostConstruct
    public void init() {
        iniciarCamino();
    }
    
    /**
     * Crea el camino de migas de pan con la página principal como primer elemento del menú
     * 
     * @author EZENTIS
     * 
     */
    public void iniciarCamino() {
        caminoMigas = new DefaultMenuModel();
        DefaultMenuItem inicio = new DefaultMenuItem();
        inicio.setUrl("/index.xhtml");
        inicio.setIcon("ui-icon-home");
        inicio.setTitle("Menú Principal");
        caminoMigas.addElement(inicio);
    }
    
    /**
     * Añade un paso más al camino
     * 
     * @author EZENTIS
     * @param nombre de la vista
     * @param ruta de la vista
     */
    public void adelante(String nombre, String ruta) {
        DefaultMenuItem nuevo = new DefaultMenuItem();
        nuevo.setUrl(ruta);
        nuevo.setValue(nombre);
        caminoMigas.addElement(nuevo);
    }
    
    /**
     * Recomienza el camino al acceder a una opción del menú
     * 
     * @author EZENTIS
     * @param nombre de la vista
     * @param ruta de la vista
     */
    public void recomenzar(String nombre, String ruta) {
        iniciarCamino();
        adelante(nombre, ruta);
    }
    
    /**
     * Elimina el último paso al pulsar un botón volver/cerrar en una vista
     * 
     * @author EZENTIS
     */
    public void atras() {
        caminoMigas.getElements().remove(caminoMigas.getElements().size());
    }
}
