package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.services.IPuestoTrabajoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean controller para la administración de los puestos de trabajo.
 * @author EZENTIS
 *
 */

@Setter
@Getter
@Controller("puestosTrabajoBean")
@Scope(FacesViewScope.NAME)
public class PuestoTrabajoBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable utilizada para alamcenar los puestos a gestionar.
     * 
     */
    private List<PuestoTrabajo> listaPuestosTrabajo;
    
    /**
     * Variable utilizada para inyectar el servicio de puestos de trabajo.
     * 
     */
    @Autowired
    private transient IPuestoTrabajoService puestoTrabajoService;
    
    /**
     * Variable utilizada para inyectar el servicio de usuarios.
     * 
     */
    @Autowired
    private transient IUserService userService;
    
    /**
     * Variable utilizada para inyectar el servicio de regitro de actividad.
     * 
     */
    @Autowired
    private transient IRegistroActividadService regActividadService;
    
    /**
     * Eliminación lógica (se pone fecha de baja) de un puesto.
     * 
     * @param puesto de trabajo a eliminar
     */
    public void eliminarPuesto(PuestoTrabajo puesto) {
        if (existenUsuariosCuerpo(puesto)) {
            FacesContext.getCurrentInstance().addMessage("msgs",
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "No se puede eliminar el puesto de trabajo '"
                                    + puesto.getDescripcion() + "' al haber usuarios pertenecientes a dicho puesto",
                            ""));
        } else {
            puesto.setFechaBaja(new Date());
            puesto.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
            puestoTrabajoService.save(puesto);
            listaPuestosTrabajo.remove(puesto);
        }
    }
    
    /**
     * Método que comprueba que no hay usuarios asignados al puesto que se desea eliminar.
     * 
     * @param puesto a comprobar
     * @return Devuelve true si existen usuarios asignados al puesto de trabajo, false en caso contrario
     */
    
    public boolean existenUsuariosCuerpo(PuestoTrabajo puesto) {
        boolean tieneUsuarios = false;
        List<User> usuarios = userService.findByPuestoTrabajo(puesto);
        if (usuarios != null && usuarios.isEmpty() == Boolean.FALSE) {
            tieneUsuarios = true;
        }
        return tieneUsuarios;
    }
    
    /**
     * Alta un nuevo puesto de trabajo.
     * @param puestoNuevo nombre del nuevo puesto
     */
    public void altaPuesto(String puestoNuevo) {
        PuestoTrabajo puesto = new PuestoTrabajo();
        puesto.setDescripcion(puestoNuevo);
        try {
            if (puestoTrabajoService.save(puesto) != null) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                        "El puesto de trabajo ha sido creado con éxito");
            }
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
                    "Se ha producido un error al dar de alta el puesto de trabajo, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.ADMINISTRACION.name(), e);
        }
        // TODO generar alerta / notificación
    }
    
    /**
     * Modificación de la descripción de un puesto de trabajo.
     * @param event evento del que se obtiene el puesto a editar
     */
    public void onRowEdit(RowEditEvent event) {
        PuestoTrabajo puesto = (PuestoTrabajo) event.getObject();
        puestoTrabajoService.save(puesto);
        FacesMessage msg = new FacesMessage("Puesto de trabajo modificado", puesto.getDescripcion());
        FacesContext.getCurrentInstance().addMessage("msgs", msg);
    }
    
    /**
     * Cancela la edición de un puesto de trabajo.
     * @param event para obtener la descripción del mensaje
     */
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Modificación cancelada",
                ((PuestoTrabajo) event.getObject()).getDescripcion());
        FacesContext.getCurrentInstance().addMessage("msgs", msg);
    }
    
    /**
     * Médodo usado para inicializar la lista de cuerpos de estado que se mostrarán en la página.
     */
    @PostConstruct
    public void init() {
        listaPuestosTrabajo = puestoTrabajoService.findByFechaBajaIsNull();
    }
    
}
