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
import es.mira.progesin.services.ICuerpoEstadoService;
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
    
    /**
     * Eliminación lógica (se pone fecha de baja) de un cuerpo del estado
     * @param cuerpo cuerpo del estado a eliminar
     */
    public void eliminarCuerpo(CuerpoEstado cuerpo) {
        if (existenUsuariosCuerpo(cuerpo)) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, "No se puede eliminar el cuerpo '"
                    + cuerpo.getDescripcion() + "' al haber usuarios pertenecientes a dicho cuerpo", "", "msgs");
        } else {
            cuerpo.setFechaBaja(new Date());
            cuerpo.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
            cuerposEstadoService.save(cuerpo);
            listaCuerposEstado.remove(cuerpo);
        }
    }
    
    public boolean existenUsuariosCuerpo(CuerpoEstado cuerpo) {
        boolean tieneUsuarios = false;
        List<User> usuarios = userService.findByCuerpoEstado(cuerpo);
        if (usuarios != null && usuarios.isEmpty() == Boolean.FALSE) {
            tieneUsuarios = true;
        }
        return tieneUsuarios;
    }
    
    /**
     * Alta un nuevo cuerpo del estado
     */
    public void altaCuerpo() {
        CuerpoEstado cuerpoEstado = new CuerpoEstado();
        cuerpoEstado.setDescripcion(cuerpoNuevo);
        cuerpoEstado.setNombreCorto(nombreCortoNuevo);
        try {
            if (cuerposEstadoService.save(cuerpoEstado) != null) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                        "El cuerpo ha sido creado con éxito");
            }
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
                    "Se ha producido un error al dar de alta el cuerpo, inténtelo de nuevo más tarde");
            // TODO log de errores
        }
        // TODO generar alerta / notificación
    }
    
    /**
     * Modificación de la descripción de un cuerpo
     * @param event
     */
    public void onRowEdit(RowEditEvent event) {
        CuerpoEstado cuerpoEstado = (CuerpoEstado) event.getObject();
        cuerposEstadoService.save(cuerpoEstado);
        FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Cuerpo modificado",
                cuerpoEstado.getDescripcion(), "msgs");
    }
    
    /**
     * Médodo usado para inicializar la lista de cuerpos de estado que se mostrarán en la página
     */
    @PostConstruct
    public void init() {
        listaCuerposEstado = cuerposEstadoService.findByFechaBajaIsNull();
    }
}
