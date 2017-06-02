package es.mira.progesin.services;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.repositories.INotificacionRepository;

/**
 * 
 * Implementación del servicio de gestión de notificaciones.
 * 
 * @author EZENTIS
 * 
 */

@Service
public class NotificacionService implements INotificacionService {
    
    /**
     * Variable utilizada para inyectar el repositorio de notificaciones.
     * 
     */
    @Autowired
    private INotificacionRepository notificacionRepository;
    
    /**
     * Variable utilizada para inyectar el repositorio de alertas y notificaciones de usuario.
     * 
     */
    @Autowired
    private IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
    
    /**
     * Variable utilizada para inyectar el repositorio de registro de acividad.
     * 
     */
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    /**
     * Variable utilizada para inyectar la sesión de spring.
     * 
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * 
     * Recupera de la base de datos una notificación cuya id se pasa como parámetro.
     * 
     * @param id Identificador de la notificación a buscar
     * @return Notificación que corresponde al id recibido
     * 
     */
    @Override
    public Notificacion findOne(Long id) {
        return notificacionRepository.findOne(id);
    }
    
    /**
     * 
     * Recupera de la base de datos una notificación cuya id se pasa como parámetro.
     * 
     * @param descripcion Identificador de la notificación a buscar
     * @param seccion de mensajes
     * @return Notificación que corresponde al id recibido
     * 
     */
    private Notificacion crearNotificacion(String descripcion, String seccion) {
        try {
            Notificacion notificacion = new Notificacion();
            notificacion.setUsernameNotificacion(SecurityContextHolder.getContext().getAuthentication().getName());
            notificacion.setNombreSeccion(seccion);
            notificacion.setFechaAlta(new Date());
            notificacion.setDescripcion(descripcion);
            
            return notificacionRepository.save(notificacion);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        return null;
        
    }
    
    /**
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna a un usuario
     * pasado como parámetro.
     * 
     * @param descripcion Descripción de la notificación
     * @param seccion Sección sobre la que se hace la notificación
     * @param usuario Usuario al que se le dirige la notificación
     * 
     */
    @Override
    public void crearNotificacionUsuario(String descripcion, String seccion, String usuario) {
        try {
            Notificacion notificacion = crearNotificacion(descripcion, seccion);
            alertasNotificacionesUsuarioService.grabarMensajeUsuario(notificacion, usuario);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
    /**
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna a un rol
     * pasado como parámetro.
     * 
     * @param descripcion Descripción de la notificación
     * @param seccion Sección sobre la que se hace la notificación
     * @param rol Rol al que se le dirige la notificación
     * 
     */
    @Override
    public void crearNotificacionRol(String descripcion, String seccion, RoleEnum rol) {
        try {
            Notificacion notificacion = crearNotificacion(descripcion, seccion);
            alertasNotificacionesUsuarioService.grabarMensajeRol(notificacion, rol);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
    }
    
    /**
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna a un listado
     * de roles pasado como parámetro.
     * 
     * @param descripcion Descripción de la notificación
     * @param seccion Sección sobre la que se hace la notificación
     * @param roles Lista de roles a los que se dirige la notificación
     * 
     */
    @Override
    public void crearNotificacionRol(String descripcion, String seccion, List<RoleEnum> roles) {
        try {
            Notificacion notificacion = crearNotificacion(descripcion, seccion);
            alertasNotificacionesUsuarioService.grabarMensajeRol(notificacion, roles);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
    /**
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna al jefe del
     * equipo de una inspección pasada como parámetro.
     * 
     * @param descripcion Descripción de la notificación
     * @param seccion Sección sobre la que se hace la notificación
     * @param inspeccion Inspección a cuyo jefe de equipo se le dirige la notificación
     * 
     */
    @Override
    public void crearNotificacionJefeEquipo(String descripcion, String seccion, Inspeccion inspeccion) {
        try {
            Notificacion notificacion = crearNotificacion(descripcion, seccion);
            alertasNotificacionesUsuarioService.grabarMensajeJefeEquipo(notificacion, inspeccion);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
    }
    
    /**
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna al equipo
     * que se pasa como parámetro.
     * 
     * @param descripcion Descripción de la notificación
     * @param seccion Sección sobre la que se hace la notificación
     * @param equipo Equipo al que se le dirige la notificación
     * 
     */
    @Override
    public void crearNotificacionEquipo(String descripcion, String seccion, Equipo equipo) {
        try {
            Notificacion notificacion = crearNotificacion(descripcion, seccion);
            alertasNotificacionesUsuarioService.grabarMensajeEquipo(notificacion, equipo);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
    /**
     * Devuelve un listado de notificaciones asignadas a un usuario. El resultado se devuelve paginado.
     * 
     * @param first Primer registro del listado
     * @param pageSize Número máximo de registros del listado
     * @param sortField Campo de ordenación
     * @param sortOrder Sentido de la ordenación
     * @param usuario Usuario para el que se buscan las notificaciones
     * @return Listado de notificaciones
     */
    @Override
    public List<Notificacion> buscarPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            String usuario) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Notificacion.class, "notificacion");
        creaCriteria(usuario, criteria);
        
        criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);
        
        if (sortField != null && sortOrder.equals(SortOrder.ASCENDING)) {
            criteria.addOrder(Order.asc(sortField));
        } else if (sortField != null && sortOrder.equals(SortOrder.DESCENDING)) {
            criteria.addOrder(Order.desc(sortField));
        } else if (sortField == null) {
            criteria.addOrder(Order.desc("idNotificacion"));
        }
        
        @SuppressWarnings("unchecked")
        List<Notificacion> listado = criteria.list();
        session.close();
        
        return listado;
    }
    
    /**
     * Devuelve el número total de notificaciones asignadas a un usuario.
     * 
     * @param usuario Usuario del que se desean recuperar las notificaciones
     * @return Número de registros coincidentes con la búsqueda
     */
    @Override
    public int getCounCriteria(String usuario) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Notificacion.class, "notificacion");
        creaCriteria(usuario, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    /**
     * Crea la consulta criteria.
     * 
     * @param usuario Usuario del que se desean recuperar las notificaciones
     * @param criteria consulta criteria
     */
    private void creaCriteria(String usuario, Criteria criteria) {
        
        DetachedCriteria usuarioMensaje = DetachedCriteria.forClass(AlertasNotificacionesUsuario.class, "mensaje");
        usuarioMensaje.add(Restrictions.ilike("mensaje.usuario", usuario, MatchMode.ANYWHERE));
        usuarioMensaje.add(Restrictions.eq("mensaje.tipo", TipoMensajeEnum.NOTIFICACION));
        usuarioMensaje.add(Restrictions.eqProperty("notificacion.idNotificacion", "mensaje.idMensaje"));
        usuarioMensaje.setProjection(Property.forName("mensaje.idMensaje"));
        
        criteria.add(Property.forName("notificacion.idNotificacion").in(usuarioMensaje));
        
    }
}
