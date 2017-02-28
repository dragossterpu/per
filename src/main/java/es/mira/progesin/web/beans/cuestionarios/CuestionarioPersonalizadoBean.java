package es.mira.progesin.web.beans.cuestionarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IAreaCuestionarioService;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.ICuestionarioPersonalizadoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author EZENTIS Esta clase contiene todos los métodos necesarios para el tratamiento de los cuestionarios creados a
 * partir de un modelo
 *
 */
@Setter
@Getter
@Controller("cuestionarioPersonalizadoBean")
@Scope("session")
public class CuestionarioPersonalizadoBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private CuestionarioPersonalizadoBusqueda cuestionarioBusqueda;
    
    private List<CuestionarioPersonalizado> listaCuestionarioPersonalizado;
    
    private String vieneDe;
    
    @Autowired
    private transient ICuestionarioPersonalizadoService cuestionarioPersonalizadoService;
    
    @Autowired
    private transient ICuestionarioEnvioService cuestionarioEnvioService;
    
    @Autowired
    private transient IAreaCuestionarioService areaService;
    
    @Autowired
    private EnvioCuestionarioBean envioCuestionarioBean;
    
    @Autowired
    transient IRegistroActividadService regActividadService;
    
    /**
     * Busca modelos de cuestionario personalizados según los filtros introducidos en el formulario de búsqueda.
     * 
     * @author EZENTIS
     */
    public void buscarCuestionario() {
        listaCuestionarioPersonalizado = cuestionarioPersonalizadoService
                .buscarCuestionarioPersonalizadoCriteria(cuestionarioBusqueda);
    }
    
    /**
     * Devuelve al formulario de búsqueda de modelos de cuestionario a su estado inicial y borra los resultados de
     * búsquedas anteriores si se navega desde el menú u otra sección.
     * 
     * @author EZENTIS
     */
    public void getFormBusquedaModelosCuestionario() {
        
        if ("menu".equalsIgnoreCase(this.vieneDe)) {
            limpiar();
            this.vieneDe = null;
        }
        
    }
    
    /**
     * Resetea los valores de búsqueda introducidos en el formulario y el resultado de la búsqueda
     * 
     * @author EZENTIS
     */
    public void limpiar() {
        
        cuestionarioBusqueda.limpiar();
        listaCuestionarioPersonalizado = null;
        
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
            rolesAdmitidos.add(RoleEnum.JEFE_INSPECCIONES);
            rolesAdmitidos.add(RoleEnum.ADMIN);
            
            if (cuestionario.getFechaBaja() == null && rolesAdmitidos.contains(usuarioActual.getRole())) {
                if (cuestionarioEnvioService.findByCuestionarioPersonalizado(cuestionario) != null) {
                    cuestionario.setFechaBaja(new Date());
                    cuestionario.setUsernameBaja(SecurityContextHolder.getContext().getAuthentication().getName());
                    cuestionarioPersonalizadoService.save(cuestionario);
                } else {
                    cuestionarioPersonalizadoService.delete(cuestionario);
                }
                listaCuestionarioPersonalizado.remove(cuestionario);
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_INFO, "Eliminación",
                        "Cuestionario personalizado eliminado con éxito", null);
                
                String descripcion = "Se elimina el cuestionario :" + cuestionario.getNombreCuestionario();
                
                regActividadService.altaRegActividad(descripcion, TipoRegistroEnum.BAJA.name(),
                        SeccionesEnum.CUESTIONARIO.getDescripcion());
                
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dialogCierre').show();");
                
            } else {
                FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_WARN, "Eliminación abortada",
                        "Ya ha sido anulada con anterioridad o no tiene permisos para realizar esta acción", null);
            }
        } catch (Exception e) {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                    "Se ha producido un error al eliminar el cuestionario personalizado, inténtelo de nuevo más tarde",
                    null);
            regActividadService.altaRegActividadError(SeccionesEnum.CUESTIONARIO.name(), e);
        }
        
    }
    
    /**
     * Muestra la pantalla de envío del cuestionario
     *
     * @author EZENTIS
     * @param cuestionario Cuestionario a enviar
     * @return Nombre de la vista del formulario de envío
     */
    public String mostrarFormularioEnvio(CuestionarioPersonalizado cuestionario) {
        if (cuestionario.getFechaBaja() == null) {
            CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
            cuestionarioEnvio.setCuestionarioPersonalizado(cuestionario);
            Inspeccion inspeccion = new Inspeccion();
            cuestionarioEnvio.setInspeccion(inspeccion);
            envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
            return "/cuestionarios/enviarCuestionario?faces-redirect=true";
        } else {
            FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_WARN, "Acción no permitida",
                    "No puede enviar un nuevo cuestionario de un modelo personalizado anulado", null);
            return null;
        }
    }
    
    @PostConstruct
    public void init() {
        cuestionarioBusqueda = new CuestionarioPersonalizadoBusqueda();
    }
    
}
