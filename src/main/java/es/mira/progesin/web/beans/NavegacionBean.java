package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

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
        DefaultMenuItem e;
        int tam = elementos.size();
        for (int i = 0; i < tam; i++) {
            e = (DefaultMenuItem) elementos.get(i);
            if (nombre.equals(e.getValue())) {
                elementos.subList(i, tam).clear();
                break;
            }
        }
        DefaultMenuItem nuevo = new DefaultMenuItem();
        nuevo.setUrl(ruta);
        nuevo.setValue(nombre);
        caminoMigas.addElement(nuevo);
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
        limpiarBeans();
    }
    
    /**
     * Elimina los bean de la sesión.
     */
    private void limpiarBeans() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> mapaSesion = context.getExternalContext().getSessionMap();
        for (String cabecera : mapaSesion.keySet()) {
            String ubicacion = mapaSesion.get(cabecera).getClass().getPackage().toString().toLowerCase();
            if (ubicacion.contains("bean")) {
                mapaSesion.remove("cabecera");
            }
        }
        
    }
    
}
