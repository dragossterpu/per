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
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.services.IDepartamentoService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Controller("departamentosBean")
@Scope(FacesViewScope.NAME)
public class DepartamentoBean implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private List<Departamento> listaDepartamentos;
    
    private String departamentoNuevo;
    
    @Autowired
    private transient IDepartamentoService departamentoService;
    
    @Autowired
    private transient IUserService userService;
    
    /**
     * Eliminación lógica (se pone fecha de baja) de un departamento
     * @param departamento a eliminar
     */
    public void eliminarDepartamento(Departamento departamento) {
        if (existenUsuariosDepartamento(departamento)) {
            FacesUtilities.setMensajeInformativo(
                    FacesMessage.SEVERITY_ERROR, "No se puede eliminar el departamento '"
                            + departamento.getDescripcion() + "' al haber usuarios pertenecientes a dicho departamento",
                    "", "msgs");
        } else {
            departamento.setFechaBaja(new Date());
            departamento.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
            departamentoService.save(departamento);
            listaDepartamentos.remove(departamento);
        }
    }
    
    public boolean existenUsuariosDepartamento(Departamento departamento) {
        boolean tieneUsuarios = false;
        List<User> usuarios = userService.findByDepartamento(departamento);
        if (usuarios != null && usuarios.isEmpty() == Boolean.FALSE) {
            tieneUsuarios = true;
        }
        return tieneUsuarios;
    }
    
    /**
     * Alta de un nuevo departamento
     */
    public void altaDepartamento() {
        Departamento departamento = new Departamento();
        departamento.setDescripcion(departamentoNuevo);
        try {
            if (departamentoService.save(departamento) != null) {
                FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                        "El departamento ha sido creado con éxito");
            }
        } catch (Exception e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
                    "Se ha producido un error al dar de alta el departamento, inténtelo de nuevo más tarde");
            // TODO log de errores
        }
        // TODO generar alerta / notificación
    }
    
    /**
     * Modificación de la descripción de un departamento
     * @param event
     */
    public void onRowEdit(RowEditEvent event) {
        Departamento departamento = (Departamento) event.getObject();
        departamentoService.save(departamento);
        FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Departamento modificado",
                departamento.getDescripcion(), "msgs");
    }
    
    /**
     * Médodo usado para inicializar la lista de departamentos que se mostrarán en la página
     */
    @PostConstruct
    public void init() {
        listaDepartamentos = departamentoService.findByFechaBajaIsNull();
    }
    
}
