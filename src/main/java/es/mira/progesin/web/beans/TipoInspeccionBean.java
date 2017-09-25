package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IGuiaService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ITipoInspeccionService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Controlador para la gestión de los tipos de inspección. Alta de tipo de inspección, modificar tipo de inspección,
 * eliminación de tipo de inspección y búsqueda de tipo de inspección.
 *
 * @author EZENTIS
 *
 */
@Setter
@Getter
@Controller("tipoInspeccionBean")
@Scope(FacesViewScope.NAME)
public class TipoInspeccionBean implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable utilizada para alamcenar los tipos de inspección a gestionar.
     * 
     */
    private List<TipoInspeccion> listaTipoInspeccion;
    
    /**
     * Variable utilizada para inyectar el servicio de tipos inspección.
     * 
     */
    @Autowired
    private transient ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Variable utilizada para inyectar el servicio del registro de actividad.
     * 
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Variable utilizada para inyectar el servicio del registro de actividad.
     * 
     */
    @Autowired
    private transient IInspeccionesService inspeccionesService;
    
    /**
     * Variable utilizada para inyectar el servicio de guías.
     * 
     */
    @Autowired
    private transient IGuiaService guiaService;
    
    /**
     * Variable utilizada para inyectar el servicio de las notificaciones.
     * 
     */
    @Autowired
    private transient INotificacionService notificacionesService;
    
    /**
     * Recarga la lista de tipos de inspeccion.
     */
    @PostConstruct
    public void init() {
        listaTipoInspeccion = tipoInspeccionService.buscaTodos();
    }
    
    /**
     * Elimina un tipo de inspección.
     * 
     * @param tipo objeto a eliminar
     */
    public void eliminarTipo(TipoInspeccion tipo) {
        try {
            
            if (inspeccionesService.existeByTipoInspeccion(tipo) || guiaService.existeByTipoInspeccion(tipo)) {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                        "Error. No se puede eliminar el modelo " + tipo.getCodigo()
                                + " al existir elementos que dependen de él",
                        "", "msgs");
            } else {
                tipoInspeccionService.borrarTipo(tipo);
                listaTipoInspeccion.remove(tipo);
                
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO,
                        "Baja. Tipo de inspección eliminada con éxito.", "", null);
                
                String descripcion = "Se ha eliminado el tipo de inspección: " + tipo.getCodigo() + "("
                        + tipo.getDescripcion() + ")";
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                        SeccionesEnum.INSPECCION.getDescripcion());
                
            }
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    Constantes.ERRORMENSAJE
                            + " Se ha producido un error al eliminar el tipo de inspección, inténtelo de nuevo más tarde",
                    "", "msgs");
            // Guardamos los posibles errores en bbdd
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), e);
        }
    }
    
    /**
     * Modificación en caliente desde la tabla de un tipo de inspección.
     * 
     * @param event evento lanzado al confirmar el cambio, lleva incluido el objeto TipoInspeccion
     */
    public void onRowEdit(RowEditEvent event) {
        try {
            TipoInspeccion tipo = (TipoInspeccion) event.getObject();
            tipo.setFechaModificacion(new Date());
            tipo.setUsernameModif(SecurityContextHolder.getContext().getAuthentication().getName());
            
            tipoInspeccionService.guardarTipo(tipo);
            
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO,
                    "Modificación. Tipo de inspección modificado con éxito.", "", null);
            
            String descripcion = "Se ha modificado el tipo de inspección: " + tipo.getCodigo() + "("
                    + tipo.getDescripcion() + ")";
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.INSPECCION.getDescripcion());
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                    Constantes.ERRORMENSAJE
                            + " Se ha producido un error al modificar el tipo de inspección, inténtelo de nuevo más tarde",
                    "", null);
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), e);
        }
    }
    
    /**
     * Crea un tipo de inspección.
     * 
     * @param codigo del tipo
     * @param descripcion del tipo
     */
    public void altaTipo(String codigo, String descripcion) {
        try {
            
            TipoInspeccion tipoNuevo = new TipoInspeccion(codigo, descripcion);
            tipoNuevo.setFechaAlta(new Date());
            tipoNuevo.setUsernameAlta(SecurityContextHolder.getContext().getAuthentication().getName());
            
            tipoInspeccionService.guardarTipo(tipoNuevo);
            listaTipoInspeccion.add(tipoNuevo);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                    "El tipo de inspección ha sido creado con éxito.");
            
            String descripcionTipo = "Se ha dado de alta el tipo de inspección: " + codigo + "(" + descripcion + ")";
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcionTipo, TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.INSPECCION.getDescripcion());
            
            List<RoleEnum> listaRoles = new ArrayList<>();
            listaRoles.add(RoleEnum.ROLE_SERVICIO_APOYO);
            listaRoles.add(RoleEnum.ROLE_EQUIPO_INSPECCIONES);
            
            notificacionesService.crearNotificacionRol(descripcionTipo, SeccionesEnum.INSPECCION.getDescripcion(),
                    listaRoles);
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al dar de alta el tipo de inspección, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.INSPECCION.getDescripcion(), e);
        }
    }
    
}
