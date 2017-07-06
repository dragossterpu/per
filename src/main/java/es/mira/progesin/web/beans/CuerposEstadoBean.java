package es.mira.progesin.web.beans;

import java.io.Serializable;
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
import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IEmpleoRepository;
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
 * @author EZENTIS
 *
 */

@Setter
@Getter
@Controller("cuerposEstadoBean")
@Scope(FacesViewScope.NAME)
public class CuerposEstadoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable utilizada para almacenar la lista de cuerpos mostrada en la tabla.
     * 
     */
    private List<CuerpoEstado> listaCuerposEstado;
    
    /**
     * Variable utilizada para inyectar el servicio de cuerpos de estado.
     * 
     */
    @Autowired
    private transient ICuerpoEstadoService cuerposEstadoService;
    
    /**
     * Variable utilizada para inyectar el servicio de usuarios.
     * 
     */
    @Autowired
    private transient IUserService userService;
    
    /**
     * Variable utilizada para inyectar el servicio de registro de actividad.
     * 
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Respositorio de empleos.
     */
    @Autowired
    private transient IEmpleoRepository empleoRepository;
    
    /**
     * Eliminación física de un cuerpo del estado.
     * @param cuerpo cuerpo del estado a eliminar
     */
    public void eliminarCuerpo(CuerpoEstado cuerpo) {
        try {
            boolean tieneUsuarios = userService.existsByCuerpoEstado(cuerpo);
            boolean tieneEmpleos = empleoRepository.existsByCuerpo(cuerpo);
            if (tieneUsuarios || tieneEmpleos) {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el cuerpo '"
                        + cuerpo.getDescripcion() + "' al haber usuarios o empleos pertenecientes a dicho cuerpo", "",
                        null);
            } else {
                cuerposEstadoService.delete(cuerpo);
                listaCuerposEstado.remove(cuerpo);
                String user = SecurityContextHolder.getContext().getAuthentication().getName();
                String descripcion = "El usuario " + user + " ha eliminado el cuerpo de estado "
                        + cuerpo.getNombreCorto();
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                        SeccionesEnum.ADMINISTRACION.getDescripcion());
            }
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al intentar borrar un cuerpo, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.ADMINISTRACION.getDescripcion(), e);
        }
    }
    
    /**
     * Alta un nuevo cuerpo del estado.
     * 
     * @param nombreCorto del cuerpo
     * @param descripcionCuerpo del cuerpo
     */
    
    public void altaCuerpo(String nombreCorto, String descripcionCuerpo) {
        try {
            CuerpoEstado cuerpoEstado = new CuerpoEstado();
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            cuerpoEstado.setDescripcion(descripcionCuerpo);
            cuerpoEstado.setNombreCorto(nombreCorto);
            cuerpoEstado.setFechaAlta(new Date());
            cuerpoEstado.setUsernameAlta(user);
            cuerposEstadoService.save(cuerpoEstado);
            
            String descripcion = "El usuario " + user + " ha dado de alta el cuerpo " + nombreCorto;
            regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.ALTA.name(),
                    SeccionesEnum.ADMINISTRACION.getDescripcion());
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                    "El cuerpo ha sido creado con éxito");
            
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al intentar dar de alta el cuerpo, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.ADMINISTRACION.getDescripcion(), e);
        }
    }
    
    /**
     * Modificación de un cuerpo.
     * @param event evento que se recoge al editar la tabla
     */
    public void onRowEdit(RowEditEvent event) {
        CuerpoEstado cuerpoEstado = (CuerpoEstado) event.getObject();
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        
        try {
            cuerpoEstado.setFechaModificacion(new Date());
            cuerpoEstado.setUsernameModif(user);
            cuerposEstadoService.save(cuerpoEstado);
            regActividadService.altaRegActividad("Se ha modificado " + cuerpoEstado,
                    TipoRegistroEnum.MODIFICACION.name(), SeccionesEnum.ADMINISTRACION.getDescripcion());
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Cuerpo modificado",
                    cuerpoEstado.getDescripcion(), null);
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al intentar modificar un cuerpo, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.ADMINISTRACION.getDescripcion(), e);
        }
        
    }
    
    /**
     * Médodo usado para inicializar la lista de cuerpos de estado que se mostrarán en la página.
     */
    @PostConstruct
    public void init() {
        listaCuerposEstado = cuerposEstadoService.findAll();
    }
}
