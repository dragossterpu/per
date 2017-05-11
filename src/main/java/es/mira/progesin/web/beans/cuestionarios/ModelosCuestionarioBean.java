package es.mira.progesin.web.beans.cuestionarios;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.services.IRegistroActividadService;
import lombok.Getter;
import lombok.Setter;

/**
 * Muestra la lista de modelos de cuestionarios cargados en el sistema.
 * 
 * @author Ezentis
 */
@Setter
@Getter
@Controller("modelosCuestionarioBean")
@Scope("session")
public class ModelosCuestionarioBean {
    
    private List<ModeloCuestionario> listadoCuestionarios;
    
    @Autowired
    private IModeloCuestionarioService modeloCuestionarioService;
    
    @Autowired
    private IDocumentoService documentoService;
    
    @Autowired
    private IRegistroActividadService regActividadService;
    
    /**
     * PostConstruct, inicializa el bean
     * 
     * @author EZENTIS
     */
    @PostConstruct
    public void init() {
        cargarModelos();
    }
    
    /**
     * Carga la lista con todos los modelos de cuestionario de la bdd.
     * 
     * @author Ezentis
     */
    public void cargarModelos() {
        setListadoCuestionarios(modeloCuestionarioService.findAll());
    }
    
}
