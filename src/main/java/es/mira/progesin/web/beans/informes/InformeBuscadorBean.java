package es.mira.progesin.web.beans.informes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.lazydata.LazyModelInforme;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.services.IAreaInformeService;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IInformeService;
import es.mira.progesin.services.ISubareaInformeService;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean para la edición de informes.
 *
 * @author EZENTIS
 */
@Setter
@Getter
@Controller("informeBuscadorBean")
@Scope("session")
public class InformeBuscadorBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable utilizada para almacenar el resultado de mostrar una columna o no en la tabla de búsqueda de informes.
     * 
     */
    private List<Boolean> list;
    
    /**
     * Número de columnas de la vista.
     */
    private static final int NUMCOLSTABLA = 12;
    
    /**
     * Variable utilizada para almacenar el valor de la provincia seleccionada.
     * 
     */
    private Provincia provinciSelec;
    
    /**
     * Objeto de búsqueda de informes.
     */
    private InformeBusqueda informeBusqueda;
    
    /**
     * LazyModel para la visualización de datos paginados en la vista.
     */
    private LazyModelInforme model;
    
    /**
     * Lista de subáreas.
     */
    private List<SubareaInforme> listaSubareas;
    
    /**
     * Lista de Subareas seleccionadas.
     */
    List<SelectItem> listaSelectSubAreas; // TODO Revisar
    
    /**
     * Lista de informes seleccionados.
     */
    List<Informe> listaInformesSeleccionados;
    
    /**
     * Lista de equipos.
     */
    private List<Equipo> listaEquipos;
    
    /**
     * Servicio de informes.
     */
    @Autowired
    private transient IInformeService informeService;
    
    /**
     * Servicio de equipos.
     */
    @Autowired
    private transient IEquipoService equipoService;
    
    /**
     * Servicio de subáreas.
     */
    @Autowired
    private transient ISubareaInformeService subareaInformeService;
    
    /**
     * Servicio de áreas.
     */
    @Autowired
    private transient IAreaInformeService areaInformeService;
    
    /**
     * Inicializa el bean.
     */
    @PostConstruct
    public void init() {
        listaEquipos = equipoService.findByFechaBajaIsNull();
        model = new LazyModelInforme(informeService);
        listaSelectSubAreas = cargaListaSubareas();
        
        setInformeBusqueda(model.getBusqueda());
        
        setList(new ArrayList<>());
        for (int i = 0; i <= NUMCOLSTABLA; i++) {
            list.add(Boolean.TRUE);
        }
    }
    
    /**
     * Devuelve al formulario de búsqueda de informes a su estado inicial y borra los resultados de búsquedas anteriores
     * si se navega desde el menú u otra sección.
     * 
     * @return ruta siguiente
     */
    public String getFormBusquedaInformes() {
        limpiarBusqueda();
        return "/informes/informes?faces-redirect=true";
    }
    
    /**
     * Borra los resultados de búsquedas anteriores.
     */
    public void limpiarBusqueda() {
        setProvinciSelec(null);
        setInformeBusqueda(new InformeBusqueda());
        model.setRowCount(0);
    }
    
    /**
     * Busca los informes según los filtros introducidos en el formulario de búsqueda.
     * 
     * 
     */
    public void buscarInforme() {
        informeBusqueda.setProvincia(provinciSelec);
        model.setBusqueda(informeBusqueda);
        model.load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
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
     * Carga la lista de subáreas.
     * 
     * @return Lista de elementos seleccionables generada a partir de la lista de subáreas.
     */
    private List<SelectItem> cargaListaSubareas() {
        
        List<SelectItem> listaSelect = new ArrayList<SelectItem>();
        
        List<AreaInforme> listaAreas = areaInformeService.findAll();
        
        // for (AreaInforme area : listaAreas) {
        // SelectItemGroup nuevoGrupo = new SelectItemGroup(area.getDescripcion());
        // List<SubareaInforme> lista = subareaInformeService.findByArea(area);
        // SelectItem[] elementos = new SelectItem[lista.size()];
        // for (int i = 0; i < lista.size(); i++) {
        // elementos[i] = new SelectItem(lista.get(i).getId().toString(), lista.get(i).getDescripcion()); // TODO
        // // Revisar
        // }
        // nuevoGrupo.setSelectItems(elementos);
        // listaSelect.add(nuevoGrupo);
        // }
        for (AreaInforme area : listaAreas) {
            String pre = area.getDescripcion().concat(": ");
            List<SubareaInforme> lista = subareaInformeService.findByArea(area);
            for (int i = 0; i < lista.size(); i++) {
                listaSelect.add(new SelectItem(lista.get(i).getId(), pre.concat(lista.get(i).getDescripcion()))); // TODO
                // Revisar
            }
        }
        
        return listaSelect;
    }
    
    // private List<SelectItem> cargaListaSubareas() {
    //
    // List<SelectItem> listaSelect = new ArrayList<SelectItem>();
    //
    // List<SubareaInforme> listaAreas = subareaInformeService.findAll();
    //
    // for (SubareaInforme area : listaAreas) {
    // listaSelect.add(new SelectItem(area.getId().toString(), area.getDescripcion()));
    // }
    // return listaSelect;
    // }
    
}
