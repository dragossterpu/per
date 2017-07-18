package es.mira.progesin.web.beans.informes;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.lazydata.LazyModelInforme;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.services.IInformeService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.IModeloInformePersonalizadoService;
import es.mira.progesin.services.ITipoInspeccionService;
import es.mira.progesin.services.RegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.HtmlDocxGenerator;
import es.mira.progesin.util.HtmlPdfGenerator;
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
@Controller("informeBean")
@Scope("session")
public class InformeBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable utilizada para almacenar el resultado de mostrar una columna o no en la tabla de búsqueda de informes.
     * 
     */
    private List<Boolean> list;
    
    /**
     * Número de columnas de la vista.
     */
    private static final int NUMCOLSTABLA = 8;
    
    /**
     * Formato de fecha.
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * Informe con respuestas.
     */
    private Informe informe;
    
    /**
     * Modelo personalizado del informe seleccionado.
     */
    private ModeloInformePersonalizado modeloInformePersonalizado;
    
    /**
     * Objeto de búsqueda de informes.
     */
    private InformeBusqueda informeBusqueda;
    
    /**
     * LazyModel para la visualización de datos paginados en la vista.
     */
    private LazyModelInforme model;
    
    /**
     * Lista de tipos de inspección.
     */
    private List<TipoInspeccion> listaTiposInspeccion;
    
    /**
     * Lista ordenada de areas del informe.
     */
    private List<AreaInforme> listaAreas;
    
    /**
     * Mapa de respuestas.
     */
    private Map<AreaInforme, List<SubareaInforme>> mapaAreasSubareas;
    
    /**
     * Mapa de areas y subareas del modelo personalizado.
     */
    private Map<SubareaInforme, String[]> mapaRespuestas;
    
    /**
     * Servicio de informes.
     */
    @Autowired
    private transient IInformeService informeService;
    
    /**
     * Servicio de modelos personalizados de informe.
     */
    @Autowired
    private transient IModeloInformePersonalizadoService modeloInformePersonalizadoService;
    
    // /**
    // * Generador de PDFs a partir de código html.
    // */
    // @Autowired
    // private transient HtmlPdfGenerator htmlPdfGenerator;
    
    /**
     * Generador de DOCXs a partir de código html.
     */
    @Autowired
    private transient HtmlDocxGenerator htmlDocxGenerator;
    
    /**
     * Servicio de tipos de inspección.
     */
    @Autowired
    private transient ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Servicio de inspecciones.
     */
    @Autowired
    private transient IInspeccionesService inspeccionService;
    
    /**
     * Servicio del registro de actividad.
     */
    @Autowired
    private transient RegistroActividadService regActividadService;
    
    /**
     * Archivo descargable generado a partir del informe.
     */
    private transient StreamedContent file;
    
    /**
     * Inicializa el bean.
     */
    @PostConstruct
    public void init() {
        setInformeBusqueda(new InformeBusqueda());
        model = new LazyModelInforme(informeService);
        setListaTiposInspeccion(tipoInspeccionService.buscaTodos());
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
        setInformeBusqueda(new InformeBusqueda());
        model.setRowCount(0);
    }
    
    /**
     * Busca los informes según los filtros introducidos en el formulario de búsqueda.
     * 
     * 
     */
    public void buscarInforme() {
        model.setBusqueda(informeBusqueda);
        model.load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
    }
    
    /**
     * Cargar formulario para crear un informe a partir de un modelo personalizado y eligiendo la inspección a la que
     * pertenece.
     * 
     * @param idModeloPersonalizado modelo a partir del que se que crea el informe
     * @return ruta de la vista
     */
    public String getFormCrearInforme(Long idModeloPersonalizado) {
        setInforme(new Informe());
        setModeloInformePersonalizado(
                modeloInformePersonalizadoService.findModeloPersonalizadoCompleto(idModeloPersonalizado));
        generarMapaAreasSubareas();
        return "/informes/crearInforme?faces-redirect=true";
    }
    
    /**
     * Cargar formulario para editar informe.
     * 
     * @param informeId clave informe seleccionado
     * @return ruta de la vista
     */
    public String getFormEditarInforme(Long informeId) {
        cargarInforme(informeId);
        return "/informes/editarInforme?faces-redirect=true";
    }
    
    /**
     * Cargar formulario para visualizar informe.
     * 
     * @param informeId clave informe seleccionado
     * @return ruta de la vista
     */
    public String getFormVisualizarInforme(Long informeId) {
        cargarInforme(informeId);
        return "/informes/visualizarInforme?faces-redirect=true";
    }
    
    /**
     * Carga los datos de las respuestas del informe y el modelo con las areas y subareas.
     * @param id clave del informe
     */
    private void cargarInforme(Long id) {
        setInforme(informeService.findConRespuestas(id));
        setModeloInformePersonalizado(modeloInformePersonalizadoService
                .findModeloPersonalizadoCompleto(informe.getModeloPersonalizado().getId()));
        generarMapaAreasSubareas();
        generarMapaRespuestas();
    }
    
    /**
     * Genera mapa con areas y subareas del informe a partir del modelo personalizado.
     */
    private void generarMapaAreasSubareas() {
        mapaAreasSubareas = new HashMap<>();
        List<SubareaInforme> listaSubareasArea;
        for (SubareaInforme subarea : modeloInformePersonalizado.getSubareas()) {
            listaSubareasArea = mapaAreasSubareas.get(subarea.getArea());
            if (listaSubareasArea == null) {
                listaSubareasArea = new ArrayList<>();
            }
            listaSubareasArea.add(subarea);
            mapaAreasSubareas.put(subarea.getArea(), listaSubareasArea);
        }
        listaAreas = new ArrayList<>(mapaAreasSubareas.keySet());
        
        Collections.sort(listaAreas, (o1, o2) -> Long.compare(o1.getOrden(), o2.getOrden()));
    }
    
    /**
     * Genera mapa con respuestas del informe asociadas a subareas del modelo.
     */
    private void generarMapaRespuestas() {
        mapaRespuestas = new HashMap<>();
        mapaAreasSubareas.forEach(
                (area, subareas) -> subareas.forEach(subarea -> mapaRespuestas.put(subarea, new String[] { "", "" })));
        informe.getRespuestas().forEach(respuesta -> {
            try {
                String[] resp = new String[2];
                resp[0] = new String(respuesta.getTexto(), "UTF-8");
                if (respuesta.getConclusiones() != null) {
                    resp[1] = new String(respuesta.getConclusiones(), "UTF-8");
                }
                mapaRespuestas.put(respuesta.getSubarea(), resp);
            } catch (UnsupportedEncodingException e) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                        "Se ha producido un error en la recuperación del texto");
                regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
            }
        });
    }
    
    /**
     * Crear el informe de una inspección a partir de un modelo personalizado.
     * 
     * @param inspeccion elegida por el usuario en el formulario
     */
    public void crearInforme(Inspeccion inspeccion) {
        try {
            Informe nuevoInforme = Informe.builder().modeloPersonalizado(modeloInformePersonalizado)
                    .inspeccion(inspeccion).build();
            informeService.save(nuevoInforme);
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                    "El informe ha sido creado con éxito.");
        } catch (DataAccessException e) {
            e.printStackTrace();
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al crear el informe");
            regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
        }
    }
    
    /**
     * Guarda el informe actual.
     */
    public void guardarInforme() {
        try {
            setInforme(informeService.saveConRespuestas(informe, mapaRespuestas));
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                    "El informe ha sido guardado con éxito.");
        } catch (DataAccessException e) {
            e.printStackTrace();
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al guardar el informe");
            regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
        }
    }
    
    /**
     * Finalizar y guarda el informe actual.
     */
    public void finalizarInforme() {
        try {
            setInforme(informeService.finalizarSaveConRespuestas(informe, mapaRespuestas));
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Modificación",
                    "El informe ha sido guardado y finalizado con éxito.");
        } catch (DataAccessException e) {
            e.printStackTrace();
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al guardar el informe");
            regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
        }
    }
    
    /**
     * Genera XHTML con los datos del informe.
     * 
     * @return texto completo del informe
     */
    private String generarInformeXHTML() {
        StringBuilder informeFormateado = new StringBuilder();
        informeFormateado.append("<div class=\"ql-snow ql-editor\">");
        AtomicInteger i = new AtomicInteger(0);
        listaAreas.forEach(area -> {
            informeFormateado.append("<h1>" + i.incrementAndGet() + ". ");
            informeFormateado.append(area.getDescripcion());
            informeFormateado.append("</h1>");
            AtomicInteger j = new AtomicInteger(0);
            mapaAreasSubareas.get(area).forEach(subarea -> {
                informeFormateado.append("<h2>" + i.get() + "." + j.incrementAndGet() + ". ");
                informeFormateado.append(subarea.getDescripcion());
                informeFormateado.append("</h2>");
                informeFormateado.append(mapaRespuestas.get(subarea)[0]);
                informeFormateado.append("<h3>Conclusiones y propuestas.</h3>");
                informeFormateado.append(mapaRespuestas.get(subarea)[1]);
            });
        });
        informeFormateado.append("</div>");
        
        // Asegurarse de que es XHTML
        String informeXHTML = Utilities.limpiarHtml(informeFormateado.toString());
        return informeXHTML;
    }
    
    /**
     * Exportar un archivo PDF o DOCX con los datos del informe.
     * 
     * @param tipoArchivo formato al que se exporta el informe
     */
    public void exportarInforme(String tipoArchivo) {
        try {
            String nombreArchivo = String.format("Informe_Inspeccion_%s-%s", informe.getInspeccion().getId(),
                    informe.getInspeccion().getAnio());
            String informeXHTML = generarInformeXHTML();
            String titulo = String.format("Inspección realizada a la %s de la %s de %s",
                    informe.getInspeccion().getTipoUnidad().getDescripcion(),
                    informe.getInspeccion().getAmbito().getDescripcion(),
                    informe.getInspeccion().getMunicipio().getName());
            String fechaFinalizacion = sdf.format(informe.getFechaFinalizacion());
            String autor = informe.getUsernameFinalizacion();
            
            if ("PDF".equals(tipoArchivo)) {
                setFile(HtmlPdfGenerator.generarInformePdf(nombreArchivo, informeXHTML, titulo, fechaFinalizacion,
                        autor));
            } else if ("DOCX".equals(tipoArchivo)) {
                setFile(htmlDocxGenerator.generarInformeDocx(nombreArchivo, informeXHTML, titulo, fechaFinalizacion,
                        autor));
            }
        } catch (ProgesinException e) {
            e.printStackTrace();
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error en la generación del " + tipoArchivo);
            regActividadService.altaRegActividadError(SeccionesEnum.INFORMES.getDescripcion(), e);
        }
    }
    
    /**
     * Devuelve una lista con las inspecciones cuyo número contiene alguno de los caracteres pasados como parámetro. Se
     * usa en el formulario de envío para el autocompletado.
     * 
     * @param infoInspeccion Número de inspección que teclea el usuario en el formulario o nombre de la unidad de la
     * inspección
     * @return lista de inspecciones que contienen algún caracter coincidente con el número introducido
     */
    public List<Inspeccion> autocompletarInspeccion(String infoInspeccion) {
        User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Inspeccion> resultadosBusqueda;
        if (RoleEnum.ROLE_EQUIPO_INSPECCIONES.equals(usuarioActual.getRole())) {
            resultadosBusqueda = inspeccionService.buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(infoInspeccion,
                    usuarioActual.getUsername());
        } else {
            resultadosBusqueda = inspeccionService.buscarNoFinalizadaPorNombreUnidadONumero(infoInspeccion);
        }
        return resultadosBusqueda;
    }
    
    /**
     * Controla las columnas visibles en la lista de resultados del buscador.
     * 
     * @param e checkbox de la columna seleccionada
     */
    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
    
}
