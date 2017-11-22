package es.mira.progesin.web.beans.informes;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.lazydata.LazyModelInforme;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.InformeEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.RespuestaInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.services.IAreaInformeService;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IInformeService;
import es.mira.progesin.services.IModeloInformeService;
import es.mira.progesin.services.IMunicipioService;
import es.mira.progesin.services.ISubareaInformeService;
import es.mira.progesin.services.RegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.HtmlDocxGenerator;
import es.mira.progesin.util.Utilities;
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
     * Nombre del área a visualizar.
     */
    String nombreAreaVisualizar = "";
    
    /**
     * Lista de municipios.
     */
    private List<Municipio> listaMunicipios;
    
    /**
     * Objeto de búsqueda de informes.
     */
    private InformeBusqueda informeBusqueda;
    
    /**
     * Lista de modelos de informe.
     */
    private List<ModeloInforme> listaModelosInforme;
    
    /**
     * Lista de las áreas disponibles.
     */
    private List<AreaInforme> listaAreas;
    
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
    private List<SelectItem> listaSelectSubAreas;
    
    /**
     * Lista de informes seleccionados.
     */
    private List<Informe> listaInformesSeleccionados;
    
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
     * Servicio del registro de actividad.
     */
    @Autowired
    private transient RegistroActividadService regActividadService;
    
    /**
     * Fichero a descargar.
     */
    private transient StreamedContent file;
    
    /**
     * Servicio de áreas.
     */
    @Autowired
    private transient IAreaInformeService areaInformeService;
    
    /**
     * Servicio de modelos de informe.
     */
    @Autowired
    private transient IModeloInformeService modeloInformeService;
    
    /**
     * Servicio de municipios.
     */
    @Autowired
    private transient IMunicipioService municipioService;
    
    /**
     * Generador de DOCX.
     */
    @Autowired
    private transient HtmlDocxGenerator htmlDocxGenerator;
    
    /**
     * Inicializa el bean.
     */
    @PostConstruct
    public void init() {
        listaEquipos = equipoService.findByFechaBajaIsNull();
        model = new LazyModelInforme(informeService);
        
        listaModelosInforme = modeloInformeService.findAll();
        Collections.sort(listaModelosInforme, (o1, o2) -> o1.getNombre().compareTo(o2.getNombre()));
        
        setInformeBusqueda(model.getBusqueda());
        
        setList(new ArrayList<>());
        for (int i = 0; i <= NUMCOLSTABLA; i++) {
            list.add(Boolean.TRUE);
        }
        
        Utilities.limpiarSesion("informeBuscadorBean");
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
     * Devuelve al formulario de búsqueda de informes resumidos a su estado inicial y borra los resultados de búsquedas
     * anteriores si se navega desde el menú u otra sección.
     * 
     * @return ruta siguiente
     */
    public String getFormBusquedaInformesResumidos() {
        limpiarBusqueda();
        return "/informes/buscaInformes?faces-redirect=true";
    }
    
    /**
     * Borra los resultados de búsquedas anteriores.
     */
    public void limpiarBusqueda() {
        // setProvinciSelec(null);
        // setListaInformesSeleccionados(null);
        // setListaAreas(null);
        // setListaSubareas(null);
        setInformeBusqueda(new InformeBusqueda());
        model.setRowCount(0);
    }
    
    /**
     * Busca los informes según los filtros introducidos en el formulario de búsqueda.
     * 
     * 
     */
    public void buscarInforme() {
        // informeBusqueda.setProvincia(provinciSelec);
        model.setBusqueda(informeBusqueda);
        model.load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
    }
    
    /**
     * Busca los informes según los filtros introducidos en el formulario de búsqueda.
     * 
     * 
     */
    public void buscarInformesAgrupar() {
        informeBusqueda.setEstado(InformeEnum.FINALIZADO);
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
     */
    
    public void cargaListaSubareas() {
        listaSubareas = new ArrayList<>();
        for (AreaInforme area : informeBusqueda.getSelectedAreas()) {
            listaSubareas.addAll(subareaInformeService.findByArea(area));
        }
    }
    
    /**
     * Comprueba que el área recibida como parámetro no tiene seleccionada ninguna de sus subáreas.
     * 
     * @param area Área que se desea comprobar
     * @return Respuesta a la comprobación
     */
    private boolean compruebaAreaVacia(AreaInforme area) {
        List<SubareaInforme> listaSubAreas = informeBusqueda.getSelectedSubAreas();
        ListIterator<SubareaInforme> iterador = listaSubAreas.listIterator();
        boolean areaVacia = informeBusqueda.getSelectedAreas().contains(area);
        while (iterador.hasNext() && areaVacia) {
            SubareaInforme subarea = iterador.next();
            areaVacia = !(listaSubAreas.contains(subarea) && subarea.getArea().equals(area));
        }
        return areaVacia;
    }
    
    /**
     * Comprueba si la respuesta cumple las condiciones para ser exportada.
     * 
     * @param respuesta Respuesta que se desea comprobar
     * @return Respuesta de la comprobación
     */
    
    private boolean cumpleCondiciones(RespuestaInforme respuesta) {
        boolean seleccionada = informeBusqueda.getSelectedSubAreas().contains(respuesta.getSubarea());
        boolean contieneConclusiones = informeBusqueda.getSelectedAreas().contains(respuesta.getSubarea().getArea())
                && respuesta.getSubarea().getDescripcion().toLowerCase().contains("conclusiones");
        
        boolean areaSeleccionadaVacía = compruebaAreaVacia(respuesta.getSubarea().getArea());
        
        return seleccionada || areaSeleccionadaVacía || contieneConclusiones;
    }
    
    /**
     * Exporta los informes seleccionados.
     */
    public void exportar() {
        if (listaInformesSeleccionados != null && !listaInformesSeleccionados.isEmpty()) {
            
            Collections.sort(listaInformesSeleccionados,
                    (o1, o2) -> Long.compare(o1.getInspeccion().getId(), o2.getInspeccion().getId()));
            
            Map<Informe, List<RespuestaInforme>> mapaRespuestas = new HashMap<Informe, List<RespuestaInforme>>();
            
            for (Informe inf : listaInformesSeleccionados) {
                List<RespuestaInforme> listaRespuestasPosibles = informeService.findConRespuestas(inf.getId())
                        .getRespuestas();
                List<RespuestaInforme> listaRespuestas = new ArrayList<>();
                
                if (!informeBusqueda.getSelectedAreas().isEmpty()) {
                    
                    for (RespuestaInforme respuesta : listaRespuestasPosibles) {
                        
                        if (cumpleCondiciones(respuesta)) {
                            listaRespuestas.add(respuesta);
                        }
                    }
                    mapaRespuestas.put(inf, listaRespuestas);
                } else {
                    mapaRespuestas.put(inf, listaRespuestasPosibles);
                }
            }
            
            String informeXHTML = generarXHTML(mapaRespuestas);
            
            String nombreArchivo = "InformeResumido";
            String titulo = "Informes resumidos";
            String fechaFinalizacion = Utilities.getFechaFormateada(new Date(), "MMMM 'de' yyyy");
            User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            
            try {
                setFile(htmlDocxGenerator.generarInformeDocx(nombreArchivo, informeXHTML, titulo, fechaFinalizacion,
                        usuarioActual.getUsername()));
            } catch (ProgesinException e) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                        "Se ha producido un error en la generación del " + "DOCX");
                regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
            }
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "No hay ningún informe seleccionado para exportar");
        }
    }
    
    /**
     * Generador de código XHTML para la exportación.
     * 
     * @param mapa con los los datos a convertir
     * @return Cadena de texto con los datos en formato XHTML
     */
    private String generarXHTML(Map<Informe, List<RespuestaInforme>> mapa) {
        StringBuilder informeFormateado = new StringBuilder();
        informeFormateado.append("<div class=\"ql-editor\">");
        AtomicInteger i = new AtomicInteger(0);
        List<Informe> listaInformes = new ArrayList<>();
        listaInformes.addAll(mapa.keySet());
        
        listaInformes.forEach(informe -> {
            informeFormateado.append("<h2>" + i.incrementAndGet() + ". ");
            
            String titulo = String.format("Informe de la Inspección %s realizada a %s de %s de %s",
                    informe.getInspeccion().getTipoInspeccion().getDescripcion(),
                    informe.getInspeccion().getTipoUnidad().getDescripcion(),
                    informe.getInspeccion().getAmbito().getDescripcion(),
                    informe.getInspeccion().getMunicipio().getProvincia().getNombre()).toUpperCase();
            
            informeFormateado.append(titulo);
            informeFormateado.append("</h2>");
            AtomicInteger j = new AtomicInteger(0);
            AtomicInteger k = new AtomicInteger(0);
            List<RespuestaInforme> listaRespuestas = mapa.get(informe);
            nombreAreaVisualizar = "";
            listaRespuestas.forEach(respuesta -> {
                if (!nombreAreaVisualizar.equals(respuesta.getSubarea().getArea().getDescripcion())) {
                    nombreAreaVisualizar = respuesta.getSubarea().getArea().getDescripcion();
                    informeFormateado.append("<h3>" + i.get() + "." + j.incrementAndGet() + ". ");
                    informeFormateado.append(nombreAreaVisualizar);
                    informeFormateado.append("</h3>");
                    
                }
                
                informeFormateado.append("<h4>" + i.get() + "." + j.get() + "." + k.incrementAndGet() + ". ");
                informeFormateado.append(respuesta.getSubarea().getDescripcion());
                informeFormateado.append("</h4>");
                
                try {
                    if (respuesta.getConclusiones() != null) {
                        informeFormateado.append(new String(respuesta.getConclusiones(), "UTF-8"));
                    } else {
                        informeFormateado.append("Sin conclusiones");
                    }
                } catch (UnsupportedEncodingException e) {
                    FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                            "Se ha producido un error en la recuperación del texto");
                    regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
                }
                informeFormateado.append("</h2>");
                
            });
        });
        
        return Utilities.limpiarHtml(informeFormateado.toString());
    }
    
    /**
     * Carga la lista de áreas cuando cambia el modelo de informe seleccionado.
     */
    public void onChangeModelo() {
        if (informeBusqueda.getModeloInforme() != null) {
            setListaAreas(areaInformeService.findByModeloInformeId(informeBusqueda.getModeloInforme().getId()));
        } else {
            setListaAreas(null);
        }
    }
    
    /**
     * Devuelve una lista de municipios pertenecientes a una provincia. Se utiliza para recargar la lista de municipios
     * dependiendo de la provincia seleccionada.
     */
    public void onChangeProvincia() {
        if (informeBusqueda.getProvincia() != null) {
            setListaMunicipios(municipioService.findByProvincia(informeBusqueda.getProvincia()));
        } else {
            setListaMunicipios(null);
        }
    }
    
}
