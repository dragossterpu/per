package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ITipoEquipoService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador para la gestión de los tipos de equipos de inspección. Alta de equipo, modificar equipo, eliminación de
 * equipo y búsqueda de equipo.
 * 
 * @author Ezentis
 *
 */
@Setter
@Getter
@Controller("tipoEquipoBean")
@Scope("session")
public class TipoEquipoBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<TipoEquipo> listaTipoEquipo;
    
    @Autowired
    private transient ITipoEquipoService tipoEquipoService;
    
    @Autowired
    private transient IEquipoService equipoService;
    
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Método que nos lleva al listado de los tipos de equipos. Se llama en la carga de la página
     * 
     * @author Ezentis
     */
    public void tipoEquipoListado() {
        listaTipoEquipo = tipoEquipoService.findAll();
    }
    
    /**
     * Elimina un tipo de equipo
     * 
     * @author Ezentis
     * @param tipo objeto a eliminar
     */
    public void eliminarTipo(TipoEquipo tipo) {
        try {
            if (equipoService.existsByTipoEquipo(tipo)) {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Eliminación abortada",
                        "No se puede eliminar, existen equipos de este tipo", null);
            } else {
                tipoEquipoService.delete(tipo.getId());
                listaTipoEquipo.remove(tipo);
                String descripcion = "Se ha eliminado el tipo de equipo: " + tipo.getCodigo() + "("
                        + tipo.getDescripcion() + ")";
                // Guardamos la actividad en bbdd
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                        SeccionesEnum.ADMINISTRACION.name());
            }
        } catch (Exception e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Error",
                    "Se ha producido un error al eliminar el tipo de equipo, inténtelo de nuevo más tarde", null);
            // Guardamos los posibles errores en bbdd
            regActividadService.altaRegActividadError(SeccionesEnum.ADMINISTRACION.name(), e);
        }
    }
    
    /**
     * Inicializa el bean
     */
    @PostConstruct
    public void init() {
        
        tipoEquipoListado();
        
    }
    
    /**
     * Crea un tipo de equipo
     * 
     * @author Ezentis
     * @param codigo
     * @param descripcion
     */
    public void altaTipo(String codigo, String descripcion) {
        try {
            TipoEquipo tipoEquipoNuevo = new TipoEquipo(null, codigo, descripcion);
            
            tipoEquipoService.save(tipoEquipoNuevo);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                    "El tipo de equipo ha sido creado con éxito");
            
            String descripcionTipo = "Se ha dado de alta el tipo de equipo: " + codigo + "(" + descripcion + ")";
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcionTipo, TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.ADMINISTRACION.name());
            
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
                    "Se ha producido un error al dar de alta el tipo de equipo, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.ADMINISTRACION.name(), e);
        }
        listaTipoEquipo = tipoEquipoService.findAll();
        // TODO generar alerta / notificación
    }
    
    /**
     * Modificación en caliente desde la tabla de un tipo de equipo
     * 
     * @author Ezentis
     * @param event evento lanzado al confirmar el cambio, lleva incluido el objeto TipoEquipo
     */
    public void onRowEdit(RowEditEvent event) {
        try {
            TipoEquipo tipoEquipo = (TipoEquipo) event.getObject();
            
            tipoEquipoService.save(tipoEquipo);
            
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Modificación",
                    "Tipo de equipo modificado con éxito", null);
            
            String descripcionTipo = "Se ha modificado el tipo de equipo: " + tipoEquipo.getCodigo() + "("
                    + tipoEquipo.getDescripcion() + ")";
            // Guardamos la actividad en bbdd
            regActividadService.altaRegActividad(descripcionTipo, TipoRegistroEnum.MODIFICACION.name(),
                    SeccionesEnum.ADMINISTRACION.name());
            
        } catch (Exception e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "Error",
                    "Se ha producido un error al modificar el tipo de equipo, inténtelo de nuevo más tarde", null);
            regActividadService.altaRegActividadError(SeccionesEnum.ADMINISTRACION.name(), e);
        }
    }
    
}
