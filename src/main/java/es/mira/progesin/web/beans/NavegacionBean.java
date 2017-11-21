package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

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
 * y éste pueda navegar hacia atrás a un menú superior hasta llegar al index.
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
    
    /**
     * Variable para almacenar el camino de migas.
     */
    private DynamicMenuModel caminoMigas;
    
    /**
     * PostConstruct, inicializa el bean.
     * 
     */
    @PostConstruct
    public void init() {
        iniciarCamino();
    }
    
    /**
     * Crea el camino de migas de pan con la página principal como primer elemento del menú.
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
     * Busca si el paso ya está en el camino, de ser así lo borra junto con los que estén detrás, después se añade como
     * último paso al camino.
     * 
     * @param nombre de la vista
     * @param ruta de la vista
     */
    public void adelante(String nombre, String ruta) {
        List<MenuElement> elementos = caminoMigas.getElements();
        DefaultMenuItem e = null;
        ListIterator<MenuElement> it = elementos.listIterator();
        boolean yaEsta = false;
        while (it.hasNext() && !yaEsta) {
            e = (DefaultMenuItem) it.next();
            yaEsta = nombre.equals(e.getValue());
        }
        if (yaEsta) {
            elementos.subList(it.nextIndex(), elementos.size()).clear();
        } else {
            DefaultMenuItem nuevo = new DefaultMenuItem();
            nuevo.setUrl(ruta);
            nuevo.setValue(nombre);
            caminoMigas.addElement(nuevo);
        }
    }
    
    /**
     * Recomienza el camino al acceder a una opción del menú.
     * 
     * @param nombre de la vista
     * @param ruta de la vista
     */
    public void recomenzar(String nombre, String ruta) {
        iniciarCamino();
        adelante(nombre, ruta);
    }
    
}
