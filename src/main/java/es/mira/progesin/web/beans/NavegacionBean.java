package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DynamicMenuModel;
import org.primefaces.model.menu.MenuElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Bean que mantiene un contról de las páginas que se visitan de modo que siempre se sepa dónde se encuentra el usuario
 * y éste pueda navegar hacia atrás a un menú superior hasta llegar al index
 * 
 * @author EZENTIS
 * 
 */
@Component("navegacionBean")
@Scope("session")
@Getter
@Setter
@NoArgsConstructor
public class NavegacionBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private DynamicMenuModel caminoMigas;
    
    /**
     * PostConstruct, inicializa el bean
     * 
     * @author EZENTIS
     */
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
        caminoMigas = new DynamicMenuModel();
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
        List<MenuElement> elementos = caminoMigas.getElements();
        DefaultMenuItem ultimo = (DefaultMenuItem) elementos.get(elementos.size() - 1);
        if (nombre.equals(ultimo.getValue()) == Boolean.FALSE) {
            DefaultMenuItem nuevo = new DefaultMenuItem();
            nuevo.setUrl(ruta);
            nuevo.setValue(nombre);
            caminoMigas.addElement(nuevo);
        }
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
        caminoMigas.getElements().remove(caminoMigas.getElements().size() - 1);
    }
}
