package es.mira.progesin.web.beans.informes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.services.IModeloInformePersonalizadoService;
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
@Controller("informePersonalizadoBean")
@Scope("session")
public class ModeloInformePersonalizadoBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Modelo a partir del cual se crea el personalizado.
     */
    private ModeloInforme modeloInforme;
    
    /**
     * Mapa que relaciona las áreas con sus respectivas preguntas.
     */
    private Map<AreaInforme, SubareaInforme[]> subareasSeleccionadas;
    
    /**
     * Servicio de modelos de informe.
     */
    @Autowired
    private transient IModeloInformeService modeloInformeService;
    
    /**
     * Servicio de modelos de informe.
     */
    @Autowired
    private transient IModeloInformePersonalizadoService informePersonalizadoService;
    
       
    /**
     * Cargar formulario para crear informe personalizado a partir de un modelo.
     * 
     * @param idModelo modelo a partir del que se que crea el informe personalizado
     * @return ruta de la vista
     */
    public String crearModeloInformePersonalizado(Long idModelo) {
        setModeloInforme(modeloInformeService.findDistinctById(idModelo));
        setSubareasSeleccionadas(new HashMap<>());
        return "/informes/personalizarModeloInforme?faces-redirect=true";
    }
    
    /**
    * Previsuaizar las opciones seleccioneadas del modelo de informe antes de crearlo definitivamente.
    * 
    * @return ruta de la vista
    */
   public String previsualizarCreacionInformePersonalizado() {
       String rutaVista = null;
       boolean haySubareaSeleccionada = false;
       int cont = 0;
       while (haySubareaSeleccionada == Boolean.FALSE && cont < modeloInforme.getAreas().size()) {
           Object[] preg = subareasSeleccionadas.get(modeloInforme.getAreas().get(cont));
           if (preg.length > 0) {
               haySubareaSeleccionada = true;
           }
           cont++;
       }
       
       if (haySubareaSeleccionada == Boolean.FALSE) {
           FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Debe seleccionar al menos un subapartado para el informe",
                   "",null);
       } else {
           rutaVista = "/informes/previsualizarCreacionInformePersonalizado?faces-redirect=true";
       }
       return rutaVista;
    }
   
   /**
    * Guarda el informe personalizado en BBDD.
    * 
    * @param nombreInforme Nombre del informe
    */
   public void guardarInformePersonalizado(String nombreInforme) {
       RequestContext.getCurrentInstance().execute("PF('informeDialog').hide()");
       if (informePersonalizadoService.guardarInformePersonalizado(nombreInforme, getModeloInforme(), getSubareasSeleccionadas()) != null) {
           FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Informe personalizado",
                   "Se ha guardado con éxito");
       } else {
           FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                   "Se ha producido un error al guardar el informe");
       }
       
   }
    
   
  
}
