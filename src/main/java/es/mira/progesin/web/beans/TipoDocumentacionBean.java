package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.mail.MessagingException;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador de las operaciones relacionadas con los tipos de documentación necesarios para las solicitudes de
 * documentación previas al envio de cuestionarios. Nuevo tipo de documentación, modificar tipo de documentación,
 * eliminar el tipo de documentación, búsqueda de tipo de documentación
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.gd.TipoDocumentacion
 */
@Setter
@Getter
@Component("tipoDocumentacionBean")
@Scope(FacesViewScope.NAME)
public class TipoDocumentacionBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<TipoDocumentacion> listaTipoDocumentacion;
    
    private List<String> listaExtensionesPosibles;
    
    private String descripcionNuevo;
    
    private String nombreNuevo;
    
    private List<String> extensionesNuevo;
    
    private AmbitoInspeccionEnum ambitoNuevo;
    
    @Autowired
    transient ApplicationBean applicationBean;
    
    @Autowired
    transient ITipoDocumentacionService tipoDocumentacionService;
    
    @Autowired
    transient IRegistroActividadService regActividadService;
    
    @Autowired
    transient INotificacionService notificacionService;
    
    /**
     * Muestra el listado de tipos de documentación disponibles. Se llama desde el menu lateral
     * 
     * @author EZENTIS
     * @return vista documentacionPrevia
     */
    public String tipoDocumentacionListado() {
        listaTipoDocumentacion = tipoDocumentacionService.findAll();
        return "/documentacionPrevia/documentacionPrevia";
    }
    
    /**
     * Elimina un tipo de documentación. Elimina el tipo de documentación y actualiza la lista de la vista
     * documentacionPrevia
     * 
     * @author EZENTIS
     * @param documentacion objeto tipo de documentacion a eliminar
     */
    public void eliminarDocumentacion(TipoDocumentacion documentacion) {
        tipoDocumentacionService.delete(documentacion.getId());
        this.listaTipoDocumentacion = null;
        listaTipoDocumentacion = tipoDocumentacionService.findAll();
        
        String descripcion = "Se ha eliminado un tipo de documentación. Nombre: " + documentacion.getNombre();
        // Guardamos la actividad en bbdd
        regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                SeccionesEnum.DOCUMENTACION.name());
    }
    
    @PostConstruct
    public void init() throws MessagingException {
        listaTipoDocumentacion = tipoDocumentacionService.findAll();
        Map<String, String> mapaExtensiones = applicationBean.getMapaParametros().get("extensiones");
        setListaExtensionesPosibles(new ArrayList<>(mapaExtensiones.keySet()));
    }
    
    /**
     * Da de alta un nuevo tipo de documentación. Recupera del formulario altaTipoDocumentacion los campos descripción,
     * nombre y extensión, y muestra una ventana flotante con el mensaje resultado de la operación.
     * 
     * @author EZENTIS
     */
    public void altaTipo() {
        TipoDocumentacion documentacion = new TipoDocumentacion();
        documentacion.setDescripcion(descripcionNuevo);
        documentacion.setNombre(nombreNuevo);
        documentacion.setExtensiones(extensionesNuevo);
        documentacion.setAmbito(ambitoNuevo);
        try {
            if (tipoDocumentacionService.save(documentacion) != null) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                        "El tipo de documentación ha sido creado con éxito");
                String descripcion = "Se ha dado de alta un nuevo tipo de documentación. Nombre: "
                        + documentacion.getNombre();
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                        SeccionesEnum.DOCUMENTACION.name());
            }
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
                    "Se ha producido un error al dar de alta la documentación, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.DOCUMENTACION.name(), e);
        }
        listaTipoDocumentacion = tipoDocumentacionService.findAll();
    }
    
    /**
     * Guarda las modificaciones realizadas en caliente a un registro de la lista y cambia su estado a no editable
     * 
     * @author EZENTIS
     * @param event evento disparado al pulsar el botón modificar edición
     */
    public void onRowEdit(RowEditEvent event) {
        TipoDocumentacion tipoDoc = (TipoDocumentacion) event.getObject();
        tipoDocumentacionService.save(tipoDoc);
        FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Documentación modificada",
                tipoDoc.getDescripcion(), "msgs");
        
        String descripcion = "Se ha modificado el tipo de documentación. Nombre: " + tipoDoc.getNombre();
        // Guardamos la actividad en bbdd
        regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                SeccionesEnum.DOCUMENTACION.name());
    }
    
}
