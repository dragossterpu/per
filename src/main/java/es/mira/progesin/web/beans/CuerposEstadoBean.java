package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.ICuerpoEstadoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean para la administración de los cuerpos de estado. Nuevo cuerpo de estado, modificar cuerpo de estado, y eliminar
 * cuerpo de estado.
 * 
 * @author Ezentis
 *
 */

@Setter
@Getter
@Controller("cuerposEstadoBean")
@Scope(FacesViewScope.NAME)
public class CuerposEstadoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<CuerpoEstado> listaCuerposEstado;
    
    private String cuerpoNuevo;
    
    private String nombreCortoNuevo;
    
    @Autowired
    private transient ICuerpoEstadoService cuerposEstadoService;
    
    @Autowired
    private transient IUserService userService;
    
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Eliminación lógica (se pone fecha de baja) de un cuerpo del estado
     * @param cuerpo cuerpo del estado a eliminar
     */
    public void eliminarCuerpo(CuerpoEstado cuerpo) {
        List<User> usuariosCuerpo = userService.findByCuerpoEstado(cuerpo);
        if (usuariosCuerpo != null && !usuariosCuerpo.isEmpty()) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el cuerpo '"
                    + cuerpo.getDescripcion() + "' al haber usuarios pertenecientes a dicho cuerpo", "", "msgs");
        } else {
            cuerpo.setFechaBaja(new Date());
            cuerpo.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
            cuerposEstadoService.save(cuerpo);
            listaCuerposEstado.remove(cuerpo);
            
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            String descripcion = "El usuario " + user + " ha eliminado la inspección " + cuerpo.getNombreCorto();
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                    SeccionesEnum.ADMINISTRACION.name());
        }
    }
    
    /**
     * Alta un nuevo cuerpo del estado
     */
    public void altaCuerpo() {
        try {
            CuerpoEstado cuerpoEstado = new CuerpoEstado();
            boolean existeCuerpo = cuerposEstadoService.existeByNombreCortoIgnoreCase(nombreCortoNuevo);
            
            if (existeCuerpo) {
                
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                        "El nombre corto ya está siendo utilizado por otro cuerpo.", "", "nombre_corto");
            } else {
                String user = SecurityContextHolder.getContext().getAuthentication().getName();
                cuerpoEstado.setDescripcion(cuerpoNuevo);
                cuerpoEstado.setNombreCorto(nombreCortoNuevo);
                cuerpoEstado.setFechaAlta(new Date());
                cuerpoEstado.setUsernameAlta(user);
                cuerposEstadoService.save(cuerpoEstado);
                
                String descripcion = "El usuario " + user + " ha dado de alta el cuerpo " + nombreCortoNuevo;
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                        SeccionesEnum.ADMINISTRACION.name());
                
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                        "El cuerpo ha sido creado con éxito");
                
            }
            
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
                    "Se ha producido un error al dar de alta el cuerpo, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.ADMINISTRACION.name(), e);
        }
    }
    
    /**
     * Modificación de la descripción de un cuerpo
     * @param event
     */
    public void onRowEdit(RowEditEvent event) {
        CuerpoEstado cuerpoEstado = (CuerpoEstado) event.getObject();
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        
        try {
            cuerpoEstado.setFechaModificacion(new Date());
            cuerpoEstado.setUsernameModif(user);
            cuerposEstadoService.save(cuerpoEstado);
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Cuerpo modificado",
                    cuerpoEstado.getDescripcion(), "msgs");
        } catch (Exception e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Acción no permitida", e.toString(),
                    "msgs");
        }
        
    }
    
    /**
     * Médodo usado para inicializar la lista de cuerpos de estado que se mostrarán en la página
     */
    @PostConstruct
    public void init() {
        listaCuerposEstado = cuerposEstadoService.findByFechaBajaIsNull();
    }
}
