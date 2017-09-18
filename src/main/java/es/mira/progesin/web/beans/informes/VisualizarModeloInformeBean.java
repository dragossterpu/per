package es.mira.progesin.web.beans.informes;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.services.IModeloInformeService;
import lombok.Getter;
import lombok.Setter;

/**
 * Visualiza un modelo de cuestionario.
 * 
 * @author EZENTIS
 */
@Setter
@Getter
@Controller("visualizarInformeBean")
@Scope(FacesViewScope.NAME)
public class VisualizarModeloInformeBean {
    
    /**
     * Id del modelo a visualizar.
     */
    private Long idModelo;
    
    /**
     * Modelo a visualizar.
     */
    private ModeloInforme modelo;
    
    /**
     * Mapa con la correspondencias Área-subárea.
     */
    private Map<AreaInforme, List<SubareaInforme>> mapaAreasSubareas;
    
    /**
     * Servicio de modelos de informe.
     */
    @Autowired
    private transient IModeloInformeService modeloInformeService;
    
    /**
     * Cargar los datos necesarios para visualizar el modelo.
     */
    public void visualizarModelo() {
        this.modelo = modeloInformeService.visualizarModelo(idModelo);
        mapaAreasSubareas = modeloInformeService.cargarMapaSubareas(modelo.getAreas());
    }
    
}
