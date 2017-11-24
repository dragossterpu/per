package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.lazydata.LazyModelGuiasPersonalizadas;
import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IGuiaPersonalizadaService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.Utilities;
import es.mira.progesin.util.WordGeneratorGuias;
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
     * Lista de modelos de guía.
     */
    private List<Guia> listaModelos;
    
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
    private transient WordGeneratorGuias wordGenerator;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Servicio de guías personalizadas.
     */
    @Autowired
    private IGuiaPersonalizadaService guiaPersonalizadaService;
    
    /**
     * Busca las guías según los filtros introducidos en el formulario de búsqueda.
     * 
     */
    public void buscarGuia() {
        model.setBusqueda(guiaPersonalizadaBusqueda);
        model.load(0, Constantes.TAMPAGINA, "fechaCreacion", SortOrder.DESCENDING, null);
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
        GuiaPersonalizada guiaAux = guiaPersonalizadaService.findOne(personalizada.getId());
        String redireccion = null;
        if (guiaAux != null) {
            setGuiaPersonalizada(guiaAux);
            setListaPasos(guiaPersonalizadaService.listaPasos(guiaAux));
            guiaPersonalizada.setInspeccion(guiaPersonalizadaService.listaInspecciones(guiaAux));
            redireccion = "/guias/visualizaGuiaPersonalizada?faces-redirect=true";
        } else {
            noExisteGuiaMensaje();
        }
        return redireccion;
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
        GuiaPersonalizada guiaAux = guiaPersonalizadaService.findOne(personalizada.getId());
        if (guiaAux != null) {
            guiaPersonalizadaService.anular(guiaAux);
            regActividadService.altaRegActividad(
                    "La guía personalizada'".concat(guiaAux.getNombreGuiaPersonalizada().concat("' ha sido anulada")),
                    TipoRegistroEnum.MODIFICACION.name(), SeccionesEnum.GUIAS.getDescripcion());
        } else {
            noExisteGuiaMensaje();
        }
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
        regActividadService.altaRegActividad(
                "La guía personalizada'"
                        .concat(personalizada.getNombreGuiaPersonalizada().concat("' ha sido eliminada")),
                TipoRegistroEnum.BAJA.name(), SeccionesEnum.GUIAS.getDescripcion());
        
    }
    
    /**
     * Inicializa el bean.
     * 
     */
    @PostConstruct
    public void init() {
        listaModelos = guiaPersonalizadaService.listadoModelos();
        guiaPersonalizadaBusqueda = new GuiaBusqueda();
        List<Boolean> lista = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            lista.add(Boolean.TRUE);
        }
        setList(lista);
        mapaInspecciones = new LinkedHashMap<>();
        model = new LazyModelGuiasPersonalizadas(guiaPersonalizadaService);
        
        Utilities.limpiarSesion("guiaPersonalizadaBean");
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
        GuiaPersonalizada guiaAux = guiaPersonalizadaService.findOne(guiaActivar.getId());
        if (guiaAux != null) {
            try {
                guiaAux.setFechaAnulacion(null);
                guiaAux.setUsernameAnulacion(null);
                guiaPersonalizadaService.save(guiaAux);
                regActividadService.altaRegActividad(
                        "La guía personalizada'"
                                .concat(guiaAux.getNombreGuiaPersonalizada().concat("' ha sido activada")),
                        TipoRegistroEnum.MODIFICACION.name(), SeccionesEnum.GUIAS.getDescripcion());
                
            } catch (DataAccessException e) {
                regActividadService.altaRegActividadError(SeccionesEnum.GUIAS.getDescripcion(), e);
            }
        } else {
            noExisteGuiaMensaje();
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
    
    /**
     * Controla las columnas visibles en la lista de resultados del buscador.
     * 
     * @param e checkbox de la columna seleccionada
     */
    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
    
    /**
     * Muestra mensaje de error cuando una guía personalizada no existe.
     */
    private void noExisteGuiaMensaje() {
        FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Guías personalizadas",
                "Se ha producido un error en el acceso a la guía. La guía solicitada ya no existe");
    }
}
