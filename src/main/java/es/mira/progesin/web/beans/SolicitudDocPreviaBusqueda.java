package es.mira.progesin.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SolicitudDocPreviaEnum;
import es.mira.progesin.services.IUserService;
import lombok.Getter;
import lombok.Setter;

/**
 * Controlador búsqueda de documentación necesaria para las solicitudes de documentación previas al envío de
 * cuestionarios. Reseteo de campos de búsqueda de documentación previa al envío de cuestionarios.
 * 
 * @author EZENTIS
 * @see es.mira.progesin.persistence.entities.gd.TipoDocumentacion
 */

@Setter
@Getter
@Controller("solicitudDocPreviaBusqueda")
@Scope("session")
public class SolicitudDocPreviaBusqueda implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Date fechaDesde;
    
    private Date fechaHasta;
    
    private SolicitudDocPreviaEnum estado;
    
    private User usuarioCreacion;
    
    private String numeroInspeccion;
    
    private TipoInspeccion tipoInspeccion;
    
    private AmbitoInspeccionEnum ambitoInspeccion;
    
    private String nombreUnidad;
    
    @Autowired
    private transient IUserService userService;
    
    private List<User> listaUsuarios;
    
    private List<TipoInspeccion> listaTiposInspeccion;
    
    @PersistenceContext
    private transient EntityManager em;
    
    /**
     * Carga datos necesarios para mostrar las opciones de los desplegables
     */
    @PostConstruct
    public void init() {
        setListaUsuarios(userService.findByfechaBajaIsNullAndRoleNotIn(RoleEnum.getRolesProv()));
        TypedQuery<TipoInspeccion> query = em.createNamedQuery("TipoInspeccion.findAll", TipoInspeccion.class);
        setListaTiposInspeccion(query.getResultList());
    }
    
    /**
     * Limpia valores seleccionados en anteriores búsquedas
     * 
     * @author EZENTIS
     */
    public void resetValues() {
        setFechaDesde(null);
        setFechaHasta(null);
        setEstado(null);
        setUsuarioCreacion(null);
        setNumeroInspeccion(null);
        setTipoInspeccion(null);
        setAmbitoInspeccion(null);
        setNombreUnidad(null);
    }
    
}
