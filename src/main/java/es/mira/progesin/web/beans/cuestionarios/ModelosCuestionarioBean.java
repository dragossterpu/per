package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.util.FacesUtilities;
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
@Scope("session")
public class ModelosCuestionarioBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Modelo de cuestionario.
     */
    private ModeloCuestionario modeloCuestionario;
    
    /**
     * Listado con los modelos existentes.
     */
    private List<ModeloCuestionario> listadoCuestionarios;
    
    /**
     * Servicio de modelos de cuestionario.
     */
    @Autowired
    private transient IModeloCuestionarioService modeloCuestionarioService;
    
    /**
     * Carga la lista con todos los modelos de cuestionario de la bdd.
     */
    @PostConstruct
    public void init() {
        setListadoCuestionarios(modeloCuestionarioService.findAllByFechaBajaIsNull());
        Collections.sort(listadoCuestionarios, (o1, o2) -> Long.compare(o1.getId(), o2.getId()));
    }
    
    /**
     * Elimina el modelo pasado como parámetro.
     * 
     * @param modelo Modelo a eliminar.
     */
    public void eliminarModelo(ModeloCuestionario modelo) {
        if (modeloCuestionarioService.eliminarModelo(modelo) != null) {
            listadoCuestionarios.remove(modelo);
        } else {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al eliminar el modelo, inténtelo de nuevo más tarde", null);
        }
    }
    
    /**
     * Visualiza el modelo recibido como parámetro.
     * 
     * @param modeloVisualizar Modelo a visualizar
     * @return ruta de la vista visualizarModeloInforme
     */
    public String visualizarModelo(ModeloCuestionario modeloVisualizar) {
        this.modeloCuestionario = modeloCuestionarioService.visualizarModelo(modeloVisualizar);
        
        return "/cuestionarios/visualizarModeloCuestionario?faces-redirect=true";
    }
}
