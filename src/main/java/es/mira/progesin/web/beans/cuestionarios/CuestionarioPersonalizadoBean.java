package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.lazydata.LazyModelCuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.ICuestionarioPersonalizadoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * Gestiona el buscador de cuestionarios personalizados. A través de él se puede ver las preguntas del mismo, acceder al
 * formulario para enviarlo a una unidad en el marco de una inspección determinada o eliminarlo lógica o físicamente en
 * caso de que no haya sido enviado aún.
 *
 * @author EZENTIS
 */
@Setter
@Getter
@Controller("cuestionarioPersonalizadoBean")
@Scope("session")
public class CuestionarioPersonalizadoBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * POJO con las opciones de búsqueda.
     */
    private CuestionarioPersonalizadoBusqueda cuestionarioBusqueda;
    
    /**
     * Servicio de cuestionarios personalizados.
     */
    @Autowired
    private transient ICuestionarioPersonalizadoService cuestionarioPersonalizadoService;
    
    /**
     * Servicio de cuestionarios enviados.
     */
    @Autowired
    private transient ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Bean de cuestionarios enviados.
     */
    @Autowired
    private EnvioCuestionarioBean envioCuestionarioBean;
    
    /**
     * Servicio de registro de actividad.
     */
    @Autowired
    transient IRegistroActividadService regActividadService;
    
    /**
     * LazyModel de cuestionarios personalizados para hacer la paginación por servidor.
     */
    private LazyModelCuestionarioPersonalizado model;
    
    /**
     * Busca modelos de cuestionario personalizados según los filtros introducidos en el formulario de búsqueda.
     * 
     * @author EZENTIS
     */
    public void buscarCuestionario() {
        
        model.setBusqueda(cuestionarioBusqueda);
        model.load(0, Constantes.TAMPAGINA, "fechaCreacion", SortOrder.DESCENDING, null);
    }
    
    /**
     * Devuelve al formulario de búsqueda de modelos de cuestionario a su estado inicial y borra los resultados de
     * búsquedas anteriores si se navega desde el menú u otra sección.
     * 
     * @author EZENTIS
     * @return siguiente ruta
     */
    public String getFormBusquedaModelosCuestionario() {
        limpiar();
        return "/cuestionarios/busquedaModelosCuestionarios?faces-redirect=true";
    }
    
    /**
     * Resetea los valores de búsqueda introducidos en el formulario y el resultado de la búsqueda.
     * 
     * @author EZENTIS
     */
    public void limpiar() {
        cuestionarioBusqueda = new CuestionarioPersonalizadoBusqueda();
        model.setRowCount(0);
        
    }
    
    /**
     * Elimina un cuestionario. Si ya ha sido enviado alguna vez se realiza baja lógica en caso contrario, eliminación
     * física.
     * 
     * @author EZENTIS
     * @param cuestionario Cuestionario a eliminar
     */
    public void eliminarCuestionario(CuestionarioPersonalizado cuestionario) {
        try {
            User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<RoleEnum> rolesAdmitidos = new ArrayList<>();
            rolesAdmitidos.add(RoleEnum.ROLE_JEFE_INSPECCIONES);
            rolesAdmitidos.add(RoleEnum.ROLE_ADMIN);
            
            if (cuestionario.getFechaBaja() == null && rolesAdmitidos.contains(usuarioActual.getRole())) {
                if (cuestionarioEnvioService.existsByCuestionarioPersonalizado(cuestionario)) {
                    cuestionario.setFechaBaja(new Date());
                    cuestionario.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
                    cuestionarioPersonalizadoService.save(cuestionario);
                } else {
                    cuestionarioPersonalizadoService.delete(cuestionario);
                }
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Eliminación",
                        "Cuestionario personalizado eliminado con éxito", null);
                
                String descripcion = "Se elimina el cuestionario :" + cuestionario.getNombreCuestionario();
                
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                        SeccionesEnum.CUESTIONARIO.getDescripcion());
                
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_WARN, "Eliminación abortada",
                        "Ya ha sido anulada con anterioridad o no tiene permisos para realizar esta acción", null);
            }
        } catch (DataAccessException e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al eliminar el cuestionario personalizado, inténtelo de nuevo más tarde",
                    null);
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.name(), e);
        }
        
    }
    
    /**
     * Muestra la pantalla de envío del cuestionario personalizado.
     *
     * @author EZENTIS
     * @param cuestionario Cuestionario a enviar
     * @return Nombre de la vista del formulario de envío
     */
    public String mostrarFormularioEnvio(CuestionarioPersonalizado cuestionario) {
        String rutaVista = null;
        if (cuestionario.getFechaBaja() == null) {
            CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
            cuestionarioEnvio.setCuestionarioPersonalizado(cuestionario);
            Inspeccion inspeccion = new Inspeccion();
            cuestionarioEnvio.setInspeccion(inspeccion);
            envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
            rutaVista = "/cuestionarios/enviarCuestionario?faces-redirect=true";
        } else {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_WARN, "Acción no permitida",
                    "No puede enviar un nuevo cuestionario de un modelo personalizado anulado", null);
        }
        return rutaVista;
    }
    
    /**
     * PostConstruct, inicializa el bean.
     * 
     * @author EZENTIS
     */
    @PostConstruct
    public void init() {
        cuestionarioBusqueda = new CuestionarioPersonalizadoBusqueda();
        model = new LazyModelCuestionarioPersonalizado(cuestionarioPersonalizadoService);
    }
    
}
