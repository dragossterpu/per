package es.mira.progesin.web.beans.informes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.INuevoModeloInformeService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean para la creación de un nuevo modelo de informe.
 * 
 * @author EZENTIS
 *
 */

@Setter
@Getter
@Controller("nuevoModeloInformeBean")
@Scope("session")

public class NuevoModeloInformeBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Nuevo modelo a crear.
     */
    private ModeloInforme nuevoModelo;
    
    /**
     * Lista de áreas de modelo.
     */
    private List<AreaInforme> listaAreas;
    
    /**
     * Área seleccionada en la vista.
     */
    private AreaInforme areaSelect;
    
    /**
     * Subárea seleccionada en la vista.
     */
    private SubareaInforme subAreaSelect;
    
    /**
     * Servicio de modelo de informe.
     */
    @Autowired
    private INuevoModeloInformeService nuevoModeloInformeService;
    
    /**
     * Servicio de notificaciones.
     */
    @Autowired
    private INotificacionService notificacionesService;
    
    /**
     * Inicializa el objeto y redirige a la vista de creación de nuevo modelo de informe.
     * 
     * @return Ruta de la vista del nuevo modelo
     */
    public String getFormNuevoModelo() {
        nuevoModelo = new ModeloInforme();
        nuevoModelo.setEstandar(false);
        listaAreas = new ArrayList<>();
        return "/informes/nuevoModeloInforme?faces-redirect=true";
    }
    
    /**
     * Inicia la copia de un modelo de informe ya existente.
     * 
     * @param modelo Modelo que se desea copiar
     * @return Ruta de la vista del formulario de edición
     */
    public String clonarInforme(ModeloInforme modelo) {
        nuevoModelo = new ModeloInforme();
        nuevoModelo.setEstandar(false);
        listaAreas = nuevoModeloInformeService.clonarListaAreas(modelo);
        return "/informes/nuevoModeloInforme?faces-redirect=true";
    }
    
    /**
     * Método para el control del funcionamiento de las pestañas en el formulario de alta.
     * 
     * @param event Evento de flujo
     * @return Pestaña a la que se desplazará
     */
    public String onFlowProcess(FlowEvent event) {
        boolean correcto = true;
        String textoError = "";
        
        if ("subareas".equals(event.getNewStep())) {
            if (listaAreas.isEmpty()) {
                correcto = false;
                textoError = "Debe asignar algún área al modelo para poder pasar a la siguiente pantalla";
            }
            
        } else if ("finalizar".equals(event.getNewStep())) {
            
            for (AreaInforme area : listaAreas) {
                List<SubareaInforme> lista = area.getSubareas();
                if (lista.isEmpty()) {
                    correcto = false;
                    textoError = "Debe asignar subáreas a todas las áreas para poder pasar a la siguiente pantalla";
                }
            }
        }
        
        String siguentePaso;
        if (correcto) {
            siguentePaso = event.getNewStep();
        } else {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, textoError, null, null);
            siguentePaso = event.getOldStep();
        }
        return siguentePaso;
    }
    
    /**
     * Añade un área al modelo.
     * 
     * @param area Area que se añade.
     */
    public void aniadeArea(String area) {
        nuevoModeloInformeService.aniadeArea(area, listaAreas);
    }
    
    /**
     * Añade un subarea al área.
     * 
     * @param area Área a la que se añadirá el subárea.
     * @param subArea Subárea a añadir.
     */
    public void aniadeSubarea(AreaInforme area, String subArea) {
        nuevoModeloInformeService.aniadeSubarea(area, subArea);
    }
    
    /**
     * Selecciona un área respondiendo a un evento en la vista.
     * 
     * @param event Evento de selección.
     */
    public void onSelectArea(SelectEvent event) {
        areaSelect = (AreaInforme) event.getObject();
    }
    
    /**
     * Selecciona un subárea respondiendo a un evento en la vista.
     * 
     * @param event Evento de selección.
     */
    public void onSelectSubArea(SelectEvent event) {
        subAreaSelect = (SubareaInforme) event.getObject();
    }
    
    /**
     * Borra el área seleccionada.
     */
    public void borraArea() {
        listaAreas.removeIf(a -> a.getDescripcion().equals(areaSelect.getDescripcion()));
    }
    
    /**
     * Borra el subárea seleccionada.
     * 
     * @param area Área de la que se eliminará el subárea.
     */
    public void borraSubarea(AreaInforme area) {
        List<SubareaInforme> listado = area.getSubareas();
        listado.removeIf(a -> a.getDescripcion().equals(subAreaSelect.getDescripcion()));
        area.setSubareas(listado);
    }
    
    /**
     * Graba el modelo creado.
     */
    public void grabaInforme() {
        if (nuevoModeloInformeService.guardaModelo(nuevoModelo, listaAreas) != null) {
            String mensaje = "Se ha creado un nuevo modelo de informe: ".concat(nuevoModelo.getNombre());
            notificacionesService.crearNotificacionRol(mensaje, SeccionesEnum.INFORMES.getDescripcion(),
                    RoleEnum.ROLE_EQUIPO_INSPECCIONES);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Nuevo modelo de informe",
                    "Se ha creado el nuevo modelo con éxito");
            
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al crear el modelo de informe");
        }
        
    }
    
}
