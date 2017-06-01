package es.mira.progesin.web.beans.cuestionarios;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.services.IModeloCuestionarioService;
import lombok.Getter;
import lombok.Setter;

/**
 * Muestra la lista de modelos de cuestionarios cargados en el sistema.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
@Controller("modelosCuestionarioBean")
@Scope(FacesViewScope.NAME)
public class ModelosCuestionarioBean {
    
    /**
     * Listado con los modelos existentes.
     */
    private List<ModeloCuestionario> listadoCuestionarios;
    
    /**
     * Servicio de modelos de cuestionario.
     */
    @Autowired
    private IModeloCuestionarioService modeloCuestionarioService;
    
    /**
     * Carga la lista con todos los modelos de cuestionario de la bdd.
     * 
     * @author EZENTIS
     */
    @PostConstruct
    public void init() {
        setListadoCuestionarios(modeloCuestionarioService.findAll());
        Collections.sort(listadoCuestionarios, (o1, o2) -> Long.compare(o1.getId(), o2.getId()));
    }
    
}
