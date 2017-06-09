package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.enums.AdministracionAccionEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.services.IDepartamentoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean controller para la administración de los departamentos. Nuevo departamento, modificar departamento, y eliminar
 * departamento.
 * 
 * @author EZENTIS
 *
 */
@Setter
@Getter
@Controller("departamentosBean")
@Scope(FacesViewScope.NAME)
public class DepartamentoBean implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Variable utilizada para alamcenar los departamentos mostrados.
     * 
     */
    private List<Departamento> listaDepartamentos;
    
    /**
     * Variable utilizada para inyectar el servicio de departamentos.
     * 
     */
    @Autowired
    private transient IDepartamentoService departamentoService;
    
    /**
     * Variable utilizada para inyectar el servicio de departamentos.
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
     * Eliminación de un departamento.
     * @param departamento a eliminar
     */
    public void eliminarDepartamento(Departamento departamento) {
        try {
            boolean tieneUsuarios = userService.existsByDepartamento(departamento);
            if (tieneUsuarios) {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                        "No se puede eliminar el departamento '" + departamento.getDescripcion()
                                + "' al haber usuarios pertenecientes a dicho departamento",
                        "", null);
            } else {
                departamentoService.delete(departamento);
                listaDepartamentos.remove(departamento);
            }
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
                    "Se ha producido un error al eliminar el departamento, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.ADMINISTRACION.name(), e);
        }
    }
    
    /**
     * Alta de un nuevo departamento.
     * 
     * @param departamentoNuevo auwe se da de alta
     */
    public void altaDepartamento(String departamentoNuevo) {
        try {
            Departamento departamento = new Departamento();
            departamento.setDescripcion(departamentoNuevo);
            
            departamentoService.save(departamento, AdministracionAccionEnum.ALTA);
            
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Alta",
                    "El departamento ha sido creado con éxito");
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, "Error",
                    "Se ha producido un error al dar de alta el departamento, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.ADMINISTRACION.name(), e);
        }
    }
    
    /**
     * Modificación de la descripción de un departamento.
     * @param event evento que captura el departamento a editar
     */
    public void onRowEdit(RowEditEvent event) {
        
        try {
            Departamento departamento = (Departamento) event.getObject();
            departamentoService.save(departamento, AdministracionAccionEnum.MODIFICACION);
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Departamento modificado",
                    departamento.getDescripcion(), null);
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                    "Se ha producido un error al intentar modificar un departamento, inténtelo de nuevo más tarde");
            regActividadService.altaRegActividadError(SeccionesEnum.ADMINISTRACION.name(), e);
        }
    }
    
    /**
     * Médodo usado para inicializar la lista de departamentos que se mostrarán en la página.
     */
    @PostConstruct
    public void init() {
        listaDepartamentos = departamentoService.findAll();
    }
    
}
