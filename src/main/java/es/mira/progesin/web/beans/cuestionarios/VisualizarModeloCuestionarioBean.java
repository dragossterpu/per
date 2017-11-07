package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.services.IModeloCuestionarioService;
import lombok.Getter;
import lombok.Setter;

/**
 * Visualiza un modelo de cuestionario.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
@Controller("visualizarCuestionarioBean")
@Scope(FacesViewScope.NAME)
public class VisualizarModeloCuestionarioBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Id del modelo a visualizar.
     */
    private Integer idModelo;
    
    /**
     * Modelo a visualizar.
     */
    private ModeloCuestionario modeloCuestionario;
    
    /**
     * Servicio de modelos de cuestionario.
     */
    @Autowired
    private transient IModeloCuestionarioService modeloCuestionarioService;
    
    /**
     * Cargar los datos necesarios para visualizar el modelo.
     */
    public void visualizarModelo() {
        this.modeloCuestionario = modeloCuestionarioService.visualizarModelo(idModelo);
    }
    
}
