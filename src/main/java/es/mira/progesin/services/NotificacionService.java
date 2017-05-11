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
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.repositories.INotificacionRepository;

/*********************************************************
 * 
 * Servicio de notificaciones
 * 
 * @author Ezentis
 * 
 *******************************************************/

@Service
public class NotificacionService implements INotificacionService {
    @Autowired
    private INotificacionRepository notificacionRepository;
    
    @Autowired
    private IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
    
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /*********************************************************
     * 
     * Elimina de la base de datos una notificación cuyo id se pasa como parámetro
     * 
     * @param Long
     * 
     *******************************************************/
    
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        notificacionRepository.delete(id);
    }
    
    /*********************************************************
     * 
     * Elimina de la base de datos todas las notificaciones
     * 
     * 
     *******************************************************/
    
    @Override
    @Transactional(readOnly = false)
    public void deleteAll() {
        notificacionRepository.deleteAll();
    }
    
    /*********************************************************
     * 
     * Comprueba si existe en base de datos una notificación cuyo id se pasa como parámetro
     * 
     * @param Long
     * 
     *******************************************************/
    
    @Override
    public boolean exists(Long id) {
        return notificacionRepository.exists(id);
    }
    
    /*********************************************************
     * 
     * Recupera una lista de todas las notificaciones
     * 
     * @return List<Notificacion>
     * 
     *******************************************************/
    
    @Override
    public List<Notificacion> findAll() {
        return notificacionRepository.findAll();
    }
    
    /*********************************************************
     * 
     * Recupera una lista de todas las notificaciones cuya fecha de baja no está rellena
     * 
     * @return List<Notificacion>
     * 
     *******************************************************/
    
    @Override
    public List<Notificacion> findByFechaBajaIsNull() {
        return notificacionRepository.findByFechaBajaIsNull();
    }
    
    /*********************************************************
     * 
     * Recupera de la base de datos una notificación cuya id se pasa como parámetro
     * 
     * @return List<Notificacion>
     * 
     *******************************************************/
    
    @Override
    public Notificacion findOne(Long id) {
        return notificacionRepository.findOne(id);
    }
    
    /*********************************************************
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro
     * 
     * @param String Descripcion
     * @param String Seccion
     * @return Notificacion
     * 
     *******************************************************/
    
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
    
    /*********************************************************
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna a un usuario
     * pasado como parámetro.
     * 
     * @param String Descripcion
     * @param String Seccion
     * @param String Usuario
     * 
     *******************************************************/
    
    @Override
    public void crearNotificacionUsuario(String descripcion, String seccion, String usuario) {
        try {
            Notificacion notificacion = crearNotificacion(descripcion, seccion);
            alertasNotificacionesUsuarioService.grabarMensajeUsuario(notificacion, usuario);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
    /*********************************************************
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna a un rol
     * pasado como parámetro.
     * 
     * @param String Descripcion
     * @param String Seccion
     * @param RoleEnum Rol
     * 
     *******************************************************/
    
    @Override
    public void crearNotificacionRol(String descripcion, String seccion, RoleEnum rol) {
        try {
            Notificacion notificacion = crearNotificacion(descripcion, seccion);
            alertasNotificacionesUsuarioService.grabarMensajeRol(notificacion, rol);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
    }
    
    /*********************************************************
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna a un listado
     * de roles pasado como parámetro.
     * 
     * @param Descripcion String
     * @param Seccion String
     * @param roles List<RoleEnum>
     * 
     *******************************************************/
    
    @Override
    public void crearNotificacionRol(String descripcion, String seccion, List<RoleEnum> roles) {
        try {
            Notificacion notificacion = crearNotificacion(descripcion, seccion);
            alertasNotificacionesUsuarioService.grabarMensajeRol(notificacion, roles);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
    /*********************************************************
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna al equipo de
     * una inspección
     * 
     * @param String Descripcion
     * @param String Seccion
     * @param Inspeccion inspeccion
     * 
     *******************************************************/
    
    @Override
    public void crearNotificacionEquipo(String descripcion, String seccion, Inspeccion inspeccion) {
        try {
            Notificacion notificacion = crearNotificacion(descripcion, seccion);
            alertasNotificacionesUsuarioService.grabarMensajeEquipo(notificacion, inspeccion);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
    }
    
    /*********************************************************
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna al jefe del
     * equipo de una inspección pasada como parámetro.
     * 
     * @param String Descripcion
     * @param String Seccion
     * @param RoleEnum Rol
     * 
     *******************************************************/
    
    @Override
    public void crearNotificacionJefeEquipo(String descripcion, String seccion, Inspeccion inspeccion) {
        try {
            Notificacion notificacion = crearNotificacion(descripcion, seccion);
            alertasNotificacionesUsuarioService.grabarMensajeJefeEquipo(notificacion, inspeccion);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
    }
    
    /*********************************************************
     * 
     * Crea una notificación a partir de una descripción y una sección recibidas como parámetro y la asigna al equipo
     * que se pasa como parámetro.
     * 
     * @param String Descripcion
     * @param String Seccion
     * @param Equipo equipo
     * 
     *******************************************************/
    
    @Override
    public void crearNotificacionEquipo(String descripcion, String seccion, Equipo equipo) {
        try {
            Notificacion notificacion = crearNotificacion(descripcion, seccion);
            alertasNotificacionesUsuarioService.grabarMensajeEquipo(notificacion, equipo);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
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
    
    private void creaCriteria(String usuario, Criteria criteria) {
        
        DetachedCriteria usuarioMensaje = DetachedCriteria.forClass(AlertasNotificacionesUsuario.class, "mensaje");
        usuarioMensaje.add(Restrictions.ilike("mensaje.usuario", usuario, MatchMode.ANYWHERE));
        usuarioMensaje.add(Restrictions.sqlRestriction("TIPO_MENSAJE = '" + TipoMensajeEnum.NOTIFICACION + "'"));
        usuarioMensaje.add(Restrictions.eqProperty("notificacion.idNotificacion", "mensaje.idMensaje"));
        usuarioMensaje.setProjection(Property.forName("mensaje.idMensaje"));
        
        criteria.add(Property.forName("notificacion.idNotificacion").in(usuarioMensaje));
        
    }
}
