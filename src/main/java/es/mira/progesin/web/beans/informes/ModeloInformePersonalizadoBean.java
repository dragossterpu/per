package es.mira.progesin.web.beans.informes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.lazydata.LazyModelInformePersonalizado;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.services.IModeloInformePersonalizadoService;
import es.mira.progesin.services.IModeloInformeService;
import es.mira.progesin.util.ExportadorWord;
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
     * Número de columnas de la vista.
     */
    private static final int NUMCOLSTABLA = 6;
    
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
     * Servicio de modelos de informe personalizados.
     */
    @Autowired
    private transient IModeloInformePersonalizadoService informePersonalizadoService;
    
    /**
     * Variable utilizada para inyectar el servicio ExportadorWord.
     * 
     */
    @Autowired
    private transient ExportadorWord exportadorWord;
    
    /**
     * POJO con las opciones de búsqueda.
     */
    private InformePersonalizadoBusqueda informePersonalizadoBusqueda;
    
    /**
     * LazyModel de informes personalizados para hacer la paginación por servidor.
     */
    private LazyModelInformePersonalizado model;
    
    /**
     * Variable utilizada para almacenar el resultado de mostrar una columna o no en la tabla de búsqueda de informes
     * personalizados.
     * 
     */
    private List<Boolean> list;
    
    /**
     * PostConstruct, inicializa el bean.
     * 
     */
    @PostConstruct
    public void init() {
        informePersonalizadoBusqueda = new InformePersonalizadoBusqueda();
        model = new LazyModelInformePersonalizado(informePersonalizadoService);
        setList(new ArrayList<>());
        for (int i = 0; i <= NUMCOLSTABLA; i++) {
            list.add(Boolean.TRUE);
        }
    }
    
    /**
     * Resetea los valores de búsqueda introducidos en el formulario y el resultado de la búsqueda.
     * 
     */
    public void limpiarBusqueda() {
        informePersonalizadoBusqueda = new InformePersonalizadoBusqueda();
        model.setRowCount(0);
    }
    
    /**
     * Busca modelos de informes personalizados según los filtros introducidos en el formulario de búsqueda.
     * 
     * @author EZENTIS
     */
    public void buscarInforme() {
        model.setBusqueda(informePersonalizadoBusqueda);
        model.load(0, Constantes.TAMPAGINA, Constantes.FECHAALTA, SortOrder.DESCENDING, null);
    }
    
    /**
     * Devuelve al formulario de búsqueda de modelos de cuestionario a su estado inicial y borra los resultados de
     * búsquedas anteriores si se navega desde el menú u otra sección.
     * 
     * @author EZENTIS
     * @return siguiente ruta
     */
    public String getFormBusquedaModelosInformePersonalizados() {
        limpiarBusqueda();
        return "/informes/informesPersonalizados?faces-redirect=true";
    }
    
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
     * Previsualizar las opciones seleccionadas del modelo de informe antes de crearlo definitivamente.
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
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "Debe seleccionar al menos un subapartado para el informe", "", null);
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
        if (informePersonalizadoService.guardarInformePersonalizado(nombreInforme, getModeloInforme(),
                getSubareasSeleccionadas()) != null) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Informe personalizado",
                    "Se ha guardado con éxito");
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al guardar el informe");
        }
        
    }
    
    /**
     * Eliminación de un modelo personalizado de informe. Se hace eliminación física si no ha sido usado sino sólo
     * lógica.
     * 
     * @param modeloPersonalizado modelo personalizado a eliminar
     */
    public void eliminarModeloPersonalizado(ModeloInformePersonalizado modeloPersonalizado) {
        if (informePersonalizadoService.eliminarModeloPersonalizado(modeloPersonalizado) == null) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al eliminar el informe personalizado, inténtelo de nuevo más tarde",
                    null);
        }
    }
    
    /**
     * Controla las columnas visibles en la lista de resultados del buscador.
     * 
     * @param e checkbox de la columna seleccionada
     */
    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
    
    /**
     * Método para la exportación de la tabla a Word.
     */
    public void exportDoc() {
        
        exportadorWord.exportDoc("lista_informes_personalizados", false, "busquedaInformePersonalizado:tablaInformes",
                SeccionesEnum.INFORMES);
    }
}
