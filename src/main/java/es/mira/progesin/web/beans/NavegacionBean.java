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
        caminoMigas = new DefaultMenuModel();
        DefaultMenuItem inicio = new DefaultMenuItem();
        inicio.setUrl("");
        inicio.setIcon("ui-icon-home");
        inicio.setTitle("Menú Principal");
        caminoMigas.addElement(inicio);
    }
}
