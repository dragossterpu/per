package es.mira.progesin.web.beans.solicitudes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.web.beans.ApplicationBean;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean controlador de las operaciones relacionadas con los tipos de documentación necesarios para las solicitudes de
 * documentación previas al envio de cuestionarios. Nuevo tipo de documentación, modificar tipo de documentación,
 * eliminar el tipo de documentación, búsqueda de tipo de documentación
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.gd.TipoDocumentacion
 */
@Setter
@Getter
@Component("tipoDocumentacionBean")
@Scope("view")
public class TipoDocumentacionBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Lista de booleanos para controlar la visualización de columnas en la vista.
     */
    private List<Boolean> list;
    
    /**
     * Lista de tipos de documentación para mostrar en tablas.
     * 
     */
    private List<TipoDocumentacion> listaTipoDocumentacion;
    
    /**
     * Lista de extensiones string para mostrar en tablas.
     * 
     */
    private List<String> listaExtensionesPosibles;
    
    /**
     * Variable que almacena la descripción de un tipo de documentación que se crea por primera vez.
     * 
     */
    private String descripcionNuevo;
    
    /**
     * Variable que almacena el nombre de un tipo de documentación que se crea por primera vez.
     * 
     */
    private String nombreNuevo;
    
    /**
     * Lista de extensiones nuevas.
     * 
     */
    private List<String> extensionesNuevo;
    
    /**
     * Variable utilizada para almacenar el valor del ámbito de una inspección.
     * 
     */
    private AmbitoInspeccionEnum ambitoNuevo;
    
    /**
     * Variable utilizada para inyectar el ApplicationBean.
     * 
     */
    @Autowired
    private transient ApplicationBean applicationBean;
    
    /**
     * Variable utilizada para inyectar el servicio de tipo de documentación.
     * 
     */
    @Autowired
    private transient ITipoDocumentacionService tipoDocumentacionService;
    
    /**
     * Variable utilizada para inyectar el servicio del registro de actividad.
     * 
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Variable utilizada para inyectar el servicio de notificaciones.
     * 
     */
    @Autowired
    private transient INotificacionService notificacionService;
    
    /**
     * Muestra el listado de tipos de documentación disponibles. Se llama desde el menu lateral.
     * 
     * @return vista documentacionPrevia
     */
    public String tipoDocumentacionListado() {
        listaTipoDocumentacion = tipoDocumentacionService.findAll();
        return "/documentacionPrevia/documentacionPrevia";
    }
    
    /**
     * Elimina un tipo de documentación. Elimina el tipo de documentación y actualiza la lista de la vista
     * documentacionPrevia.
     * 
     * @param documentacion objeto tipo de documentacion a eliminar
     */
    public void eliminarDocumentacion(TipoDocumentacion documentacion) {
        try {
            tipoDocumentacionService.delete(documentacion.getId());
            this.listaTipoDocumentacion = null;
            listaTipoDocumentacion = tipoDocumentacionService.findAll();
            
            String descripcion = "Se ha eliminado un tipo de documentación. Nombre: " + documentacion.getNombre();
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                    SeccionesEnum.DOCUMENTACION.getDescripcion());
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al intentar borrar la documentación, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.ADMINISTRACION.getDescripcion(), e);
        }
    }
    
    /**
     * PostConstruct, inicializa el bean.
     * 
     */
    @PostConstruct
    public void init() {
        listaTipoDocumentacion = tipoDocumentacionService.findAll();
        Map<String, String> mapaExtensiones = applicationBean.getMapaParametros().get("extensiones");
        setListaExtensionesPosibles(new ArrayList<>(mapaExtensiones.keySet()));
        list = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            list.add(Boolean.TRUE);
        }
    }
    
    /**
     * Da de alta un nuevo tipo de documentación. Recupera del formulario altaTipoDocumentacion los campos descripción,
     * nombre y extensión, y muestra una ventana flotante con el mensaje resultado de la operación.
     * 
     */
    public void altaTipo() {
        TipoDocumentacion documentacion = new TipoDocumentacion();
        documentacion.setDescripcion(descripcionNuevo);
        documentacion.setNombre(nombreNuevo);
        documentacion.setExtensiones(extensionesNuevo);
        documentacion.setAmbito(ambitoNuevo);
        try {
            tipoDocumentacionService.save(documentacion);
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                    "El tipo de documentación ha sido creado con éxito");
            String descripcion = "Se ha dado de alta un nuevo tipo de documentación. Nombre: "
                    + documentacion.getNombre();
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.DOCUMENTACION.getDescripcion());
            
            notificacionService.crearNotificacionRol(descripcion, SeccionesEnum.DOCUMENTACION.getDescripcion(),
                    RoleEnum.ROLE_SERVICIO_APOYO);
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
                    "Se ha producido un error al dar de alta la documentación, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.getDescripcion(), e);
        }
        listaTipoDocumentacion = tipoDocumentacionService.findAll();
    }
    
    /**
     * Guarda las modificaciones realizadas en caliente a un registro de la lista y cambia su estado a no editable.
     * 
     * @param event evento disparado al pulsar el botón modificar edición
     */
    public void onRowEdit(RowEditEvent event) {
        try {
            TipoDocumentacion tipoDoc = (TipoDocumentacion) event.getObject();
            tipoDocumentacionService.save(tipoDoc);
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Documentación modificada",
                    tipoDoc.getDescripcion(), "msgs");
            
            String descripcion = "Se ha modificado el tipo de documentación. Nombre: " + tipoDoc.getNombre();
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.DOCUMENTACION.getDescripcion());
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al intentar editar la documentación, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.ADMINISTRACION.getDescripcion(), e);
        }
    }
    
    /**
     * Controla las columnas visibles en la lista de resultados del buscador.
     * 
     * @param e Evento toggle
     * 
     */
    
    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
    
}
