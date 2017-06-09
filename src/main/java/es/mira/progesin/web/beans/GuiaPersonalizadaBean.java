package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.lazydata.LazyModelGuiasPersonalizadas;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IGuiaPersonalizadaService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.WordGenerator;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean de guías personalizadas.
 * 
 * @author EZENTIS
 * 
 */

@Setter
@Getter
@Controller("guiaPersonalizadaBean")
@Scope("session")

public class GuiaPersonalizadaBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Objeto de tipo GuiaPersonalizada.
     */
    private GuiaPersonalizada guiaPersonalizada;
    
    /**
     * Objeto que contiene los criterios de búsqueda.
     */
    private GuiaBusqueda guiaPersonalizadaBusqueda;
    
    /**
     * Lista de booleanos para controlar la visualización de columnas en la vista.
     */
    private List<Boolean> list;
    
    /**
     * Lista de pasos.
     */
    private List<GuiaPasos> listaPasos;
    
    /**
     * Fichero que contendrá la guía en formato Word para descargar.
     */
    private transient StreamedContent file;
    
    /**
     * LazyModel para la visualización paginada de datos en la vista.
     */
    private LazyModelGuiasPersonalizadas model;
    
    /**
     * Mapa con las inspecciones asociadas a las guías mostradas en la vista.
     */
    private Map<Long, String> mapaInspecciones;
    
    /**
     * Generador de documentos Word.
     */
    @Autowired
    private transient WordGenerator wordGenerator;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Servicio de guías personalizadas.
     */
    @Autowired
    private transient IGuiaPersonalizadaService guiaPersonalizadaService;
    
    /**
     * Busca las guías según los filtros introducidos en el formulario de búsqueda.
     * 
     */
    public void buscarGuia() {
        model.setBusqueda(guiaPersonalizadaBusqueda);
        model.load(0, 20, "fechaCreacion", SortOrder.DESCENDING, null);
        cargaMapaInspecciones();
    }
    
    /**
     * Visualiza la guía personalizada pasada como parámetro redirigiendo a la vista "visualizaGuíaPersonalizada".
     * 
     * @param personalizada Guía a visualizar
     * @return Ruta de la vista donde se visualizará
     * 
     */
    
    public String visualizaGuia(GuiaPersonalizada personalizada) {
        setGuiaPersonalizada(personalizada);
        setListaPasos(guiaPersonalizadaService.listaPasos(personalizada));
        guiaPersonalizada.setInspeccion(guiaPersonalizadaService.listaInspecciones(personalizada));
        return "/guias/visualizaGuiaPersonalizada?faces-redirect=true";
    }
    
    /**
     * Limpia los valores del objeto de búsqueda.
     * 
     */
    
    public void limpiarBusqueda() {
        guiaPersonalizadaBusqueda = new GuiaBusqueda();
        model.setRowCount(0);
    }
    
    /**
     * Anula una guía personalizada pasada como parámetro.
     * 
     * @param personalizada Guía a anular
     * 
     */
    
    public void anular(GuiaPersonalizada personalizada) {
        guiaPersonalizadaService.anular(personalizada);
        
    }
    
    /**
     * 
     * Borra de base de datos una guía personalizada pasada como parámetro.
     * 
     * @param personalizada Guía a eliminar.
     * 
     */
    
    public void eliminar(GuiaPersonalizada personalizada) {
        guiaPersonalizadaService.eliminar(personalizada);
        
    }
    
    /**
     * Inicializa el bean.
     * 
     */
    
    @PostConstruct
    public void init() {
        guiaPersonalizadaBusqueda = new GuiaBusqueda();
        List<Boolean> lista = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            lista.add(Boolean.TRUE);
        }
        setList(lista);
        mapaInspecciones = new LinkedHashMap<>();
        model = new LazyModelGuiasPersonalizadas(guiaPersonalizadaService);
    }
    
    /**
     * 
     * Crea un documento Word a partir de una guía personalizada pasada como parámetro.
     * 
     * @param guia Guía de la que se desea generar el word.
     * 
     */
    
    public void crearDocumentoWordGuia(GuiaPersonalizada guia) {
        try {
            setFile(wordGenerator.crearDocumentoGuia(guia));
        } catch (ProgesinException e) {
            regActividadService.altaRegActividadError(TipoRegistroEnum.ERROR.name(), e);
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    "Se ha producido un error en la generación del documento Word", "", "message");
        }
    }
    
    /**
     * Limpia el menú de búsqueda si se accede a la vista desde el menú lateral.
     * @return ruta siguiente
     * 
     */
    
    public String getFormularioBusqueda() {
        limpiarBusqueda();
        return "/guias/buscaGuiasPersonalizadas?faces-redirect=true";
    }
    
    /**
     * Elimina la fecha de baja de la guía para volver a ponerla activa.
     * 
     * @param guiaActivar Guía a activar
     */
    public void activa(GuiaPersonalizada guiaActivar) {
        try {
            guiaActivar.setFechaAnulacion(null);
            guiaActivar.setUsernameAnulacion(null);
            if (guiaPersonalizadaService.save(guiaActivar) != null) {
                regActividadService.altaRegActividad(
                        "La guía '".concat(guiaActivar.getNombreGuiaPersonalizada().concat("' ha sido activada")),
                        TipoRegistroEnum.BAJA.name(), SeccionesEnum.GUIAS.getDescripcion());
                
            }
        } catch (DataAccessException e) {
            regActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
        }
    }
    
    /**
     * Carga el mapa de inspecciones de los elementos mostrados en la vista.
     */
    private void cargaMapaInspecciones() {
        
        for (GuiaPersonalizada guia : model.getDatasource()) {
            String cadenaInspecciones = "";
            for (Inspeccion inspe : guiaPersonalizadaService.listaInspecciones(guia)) {
                cadenaInspecciones = cadenaInspecciones.concat(inspe.getNumero()).concat("\n");
            }
            mapaInspecciones.put(guia.getId(), cadenaInspecciones);
        }
    }
    
}
