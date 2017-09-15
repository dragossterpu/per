package es.mira.progesin.web.beans.informes;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.services.IModeloInformeService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Muestra la lista de modelos de informes cargados en el sistema.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
@Controller("modelosInformeBean")
@Scope(FacesViewScope.NAME)
public class ModeloInformeBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Modelo de informe.
     */
    private ModeloInforme modelo;
    
    /**
     * Mapa con la correspondencias Área-subárea.
     */
    private Map<AreaInforme, List<SubareaInforme>> mapaAreasSubareas;
    
    /**
     * Listado con los modelos existentes.
     */
    private List<ModeloInforme> listadoModelosInforme;
    
    /**
     * Servicio de modelos de informe.
     */
    @Autowired
    private transient IModeloInformeService modeloInformeService;
    
    /**
     * Carga la lista con todos los modelos de informe de la bdd.
     */
    @PostConstruct
    public void init() {
        setListadoModelosInforme(modeloInformeService.findAllByFechaBajaIsNull());
        Collections.sort(listadoModelosInforme, (o1, o2) -> Long.compare(o1.getId(), o2.getId()));
    }
    
    /**
     * Elimina el modelo que se recibe como parámetro.
     * 
     * @param modeloEliminar Modelo a eliminar
     */
    public void eliminarModelo(ModeloInforme modeloEliminar) {
        if (modeloInformeService.eliminarModelo(modeloEliminar) != null) {
            listadoModelosInforme.remove(modeloEliminar);
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
    public String visualizarModelo(ModeloInforme modeloVisualizar) {
        this.modelo = modeloInformeService.visualizarModelo(modeloVisualizar);
        mapaAreasSubareas = modeloInformeService.cargarMapaSubareas(modelo.getAreas());
        
        return "/informes/visualizarModeloInforme?faces-redirect=true";
    }
    
}
