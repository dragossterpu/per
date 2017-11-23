package es.mira.progesin.web.beans.solicitudes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.lazydata.LazyModelSolicitudes;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.Utilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador del buscador de solicitudes de documentación previas al envio de cuestionarios.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia
 */
@Setter
@Getter
@Controller("solicitudDocPreviaBuscadorBean")
@Scope("session")
public class SolicitudDocPreviaBuscadorBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Lista de booleanos para controlar la visibilidad de las columnas en la vista.
     */
    private List<Boolean> listaColumnToggler;
    
    /**
     * Número de columnas de la vista.
     */
    private static final int NUMCOLSTABLA = 12;
    
    /**
     * LazyModel para la visualización de datos paginados en la vista.
     */
    private LazyModelSolicitudes model;
    
    /**
     * Objeto de búsqueda de solicitudes.
     */
    private SolicitudDocPreviaBusqueda solicitudDocPreviaBusqueda;
    
    /**
     * Servicio de Solicitudes de Documentación.
     */
    @Autowired
    private transient ISolicitudDocumentacionService solicitudDocumentacionService;
    
    /**
     * Servicio de tipo de documentación.
     */
    @Autowired
    private transient ITipoDocumentacionService tipoDocumentacionService;
    
    /**
     * Variable utilizada para inyectar el servicio ExportadorWord.
     */
    @Autowired
    private transient SolicitudDocPreviaBean solicitudDocPreviaBean;
    
    /**
     * PostConstruct, inicializa el bean.
     * 
     */
    @PostConstruct
    public void init() {
        setSolicitudDocPreviaBusqueda(new SolicitudDocPreviaBusqueda());
        model = new LazyModelSolicitudes(solicitudDocumentacionService);
        setListaColumnToggler(new ArrayList<>());
        for (int i = 0; i <= NUMCOLSTABLA; i++) {
            listaColumnToggler.add(Boolean.TRUE);
        }
        Utilities.limpiarSesion(Arrays.asList("solicitudDocPreviaBuscadorBean", "solicitudDocPreviaBean"));
    }
    
    /**
     * Método para cambiar los campos que se muestran en la tabla de resultados del buscador.
     * 
     * @param e ToggleEvent evento que lanza el método
     */
    public void onToggle(ToggleEvent e) {
        listaColumnToggler.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
    
    /**
     * Devuelve al formulario de búsqueda de solicitudes de documentación previa a su estado inicial y borra los
     * resultados de búsquedas anteriores si se navega desde el menú u otra sección.
     * @return ruta siguiente
     * 
     * 
     */
    public String getFormBusquedaSolicitudes() {
        limpiarBusqueda();
        return "/solicitudesPrevia/busquedaSolicitudesDocPrevia?faces-redirect=true";
    }
    
    /**
     * Pasa los datos de la solicitud que queremos modificar al formulario de modificación para que cambien los valores
     * que quieran.
     * 
     * @param solicitud recuperada del formulario
     * @return vista modificarSolicitud
     */
    public String getFormModificarSolicitud(SolicitudDocumentacionPrevia solicitud) {
        
        SolicitudDocumentacionPrevia solic = solicitudDocumentacionService.findOne(solicitud.getId());
        String redireccion = null;
        if (solic != null) {
            solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(solic);
            solicitudDocPreviaBean.setBackupFechaLimiteEnvio(solic.getFechaLimiteEnvio());
            redireccion = "/solicitudesPrevia/modificarSolicitud?faces-redirect=true";
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Visualización",
                    "Se ha producido un error al acceder a la solicitud. La solicitud no existe");
        }
        return redireccion;
    }
    
    /**
     * Permite visualizar una solicitud creada, muestra su información y dependiendo del estado en que se encuentre
     * permite pasar al siguiente estado si se tiene el rol adecuado. Posibles estados: alta, validada por apoyo,
     * validada por jefe equipo, enviada, cumplimentada, no conforme y finalizada
     * 
     * @param solicitud a mostrar
     * @return vista vistaSolicitud
     */
    public String visualizarSolicitud(SolicitudDocumentacionPrevia solicitud) {
        
        SolicitudDocumentacionPrevia solic = solicitudDocumentacionService.findOne(solicitud.getId());
        String redireccion = null;
        if (solic != null) {
            solicitudDocPreviaBean
                    .setListadoDocumentosPrevios(tipoDocumentacionService.findByIdSolicitud(solic.getId()));
            solicitudDocPreviaBean.setSolicitudDocumentacionPrevia(
                    solicitudDocumentacionService.findByIdConDocumentos(solic.getId()));
            redireccion = "/solicitudesPrevia/vistaSolicitud?faces-redirect=true";
        } else {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Edición",
                    "Se ha producido un error al acceder a la solicitud. La solicitud no existe");
        }
        return redireccion;
    }
    
    /**
     * Borra los resultados de búsquedas anteriores.
     * 
     * 
     */
    public void limpiarBusqueda() {
        solicitudDocPreviaBusqueda = new SolicitudDocPreviaBusqueda();
        model.setRowCount(0);
    }
    
    /**
     * Busca las solicitudes de documentación previa según los filtros introducidos en el formulario de búsqueda.
     * 
     * 
     */
    public void buscarSolicitudDocPrevia() {
        model.setBusqueda(solicitudDocPreviaBusqueda);
        model.load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
    }
}
