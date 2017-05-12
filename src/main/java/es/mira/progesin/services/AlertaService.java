package es.mira.progesin.services;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.repositories.IAlertaRepository;
import es.mira.progesin.util.ICorreoElectronico;

/**********************************************************************************
 * 
 * Implementación del servicio de alertas
 * 
 * @author Ezentis
 * 
 **********************************************************************************/

@Service
public class AlertaService implements IAlertaService {
    @Autowired
    private IAlertaRepository alertaRepository;
    
    @Autowired
    private IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
    
    @Autowired
    private ICorreoElectronico correo;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IMiembroService miembroService;
    
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**********************************************************************************
     * 
     * Elimina de la base de datos una alerta cuyo id se recibe como parámetro
     * 
     * @param Long
     * 
     **********************************************************************************/
    
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        alertaRepository.delete(id);
    }
    
    /**********************************************************************************
     * 
     * Elimina de la base de datos todas las alertas
     * 
     * 
     **********************************************************************************/
    
    @Override
    @Transactional(readOnly = false)
    public void deleteAll() {
        alertaRepository.deleteAll();
    }
    
    /**********************************************************************************
     * 
     * Comprueba si existe en de base de datos una alerta cuyo id se pasa como parámetro
     * 
     * @param Long
     * 
     * 
     **********************************************************************************/
    
    @Override
    public boolean exists(Long id) {
        return alertaRepository.exists(id);
    }
    
    /**********************************************************************************
     * 
     * Busca en base de datos todas las alertas que no hayan sido dadas de baja
     * 
     **********************************************************************************/
    
    @Override
    public List<Alerta> findByFechaBajaIsNull() {
        return alertaRepository.findByFechaBajaIsNull();
    }
    
    /**********************************************************************************
     * 
     * Busca en base de datos las alertas cuyos id se recibe como parámetro
     * 
     * @param ids Iterable<Long>
     * @return Iterable<Alerta>
     * 
     **********************************************************************************/
    
    public Iterable<Alerta> findAll(Iterable<Long> ids) {
        return alertaRepository.findAll(ids);
    }
    
    /**********************************************************************************
     * 
     * Busca en base de datos las alerta cuyo id se recibe como parámetro
     * 
     * @param Long
     * 
     **********************************************************************************/
    
    @Override
    public Alerta findOne(Long id) {
        return alertaRepository.findOne(id);
    }
    
    /**********************************************************************************
     * 
     * Crea una alerta a partir de la sección y la descripción que se reciben como parámetros
     * 
     * @param String seccion
     * @param String descripcion
     * @return Alerta
     * 
     **********************************************************************************/
    
    private Alerta crearAlerta(String seccion, String descripcion) {
        try {
            Alerta alerta = new Alerta();
            alerta.setDescripcion(descripcion);
            alerta.setNombreSeccion(seccion);
            return alertaRepository.save(alerta);
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        return null;
    }
    
    /**********************************************************************************
     * 
     * Crea una alerta y se asigna a u usuario. Se crea a partir de la sección, la descripción y el usuario que se
     * reciben como parámetros
     * 
     * @param String seccion
     * @param String descripcion
     * @param String usuario
     * 
     **********************************************************************************/
    
    @Override
    public void crearAlertaUsuario(String seccion, String descripcion, String usuario) {
        try {
            Alerta alerta = crearAlerta(seccion, descripcion);
            User usu = userService.findOne(usuario);
            alertasNotificacionesUsuarioService.grabarMensajeUsuario(alerta, usuario);
            correo.envioCorreo(usu.getCorreo(), "Nueva alerta PROGESIN",
                    "Se ha generado una nueva alerta en la aplicacion PROGESIN:\n " + descripcion);
            
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
    /**********************************************************************************
     * 
     * Crea una alerta y se asigna a un rol. Se crea a partir de la sección, la descripción y el rol que se reciben como
     * parámetros
     * 
     * @param String seccion
     * @param String descripcion
     * @param RoleEnum rol
     * 
     **********************************************************************************/
    
    @Override
    public void crearAlertaRol(String seccion, String descripcion, RoleEnum rol) {
        try {
            List<User> usuariosRol = userService.findByfechaBajaIsNullAndRole(rol);
            for (User usuario : usuariosRol) {
                crearAlertaUsuario(seccion, descripcion, usuario.getUsername());
            }
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
    /**********************************************************************************
     * 
     * Crea una alerta y se asigna a varios roles. Se crea a partir de la sección, la descripción y la lista de roles
     * que se reciben como parámetros
     * 
     * @param seccion String
     * @param descripcion String
     * @param roles List<RoleEnum>
     * 
     **********************************************************************************/
    
    @Override
    public void crearAlertaRol(String seccion, String descripcion, List<RoleEnum> roles) {
        for (RoleEnum rol : roles) {
            crearAlertaRol(seccion, descripcion, rol);
        }
        
    }
    
    /**********************************************************************************
     * 
     * Crea una alerta y se asigna a un equipo de inspección. Se crea a partir de la sección, la descripción y la
     * inspección que se reciben como parámetros
     * 
     * @param String seccion
     * @param String descripcion
     * @param Inspeccion inspeccion
     * 
     **********************************************************************************/
    
    @Override
    public void crearAlertaEquipo(String seccion, String descripcion, Inspeccion inspeccion) {
        try {
            List<Miembro> miembrosEquipo = miembroService.findByEquipo(inspeccion.getEquipo());
            for (Miembro miembro : miembrosEquipo) {
                crearAlertaUsuario(seccion, descripcion, miembro.getUsername());
            }
            
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
    /**********************************************************************************
     * 
     * Crea una alerta y se asigna al jefe de un equipo asignado a una inspección. Se crea a partir de la sección, la
     * descripción y la inspección que se reciben como parámetros
     * 
     * @param String seccion
     * @param String descripcion
     * @param Inspeccion inspeccion
     * 
     **********************************************************************************/
    
    @Override
    public void crearAlertaJefeEquipo(String seccion, String descripcion, Inspeccion inspeccion) {
        try {
            crearAlertaUsuario(seccion, descripcion, inspeccion.getEquipo().getJefeEquipo());
            
        } catch (Exception e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
    @Override
    public List<Alerta> buscarPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            String usuario) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Alerta.class, "alerta");
        creaCriteria(usuario, criteria);
        
        criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);
        
        if (sortField != null && sortOrder.equals(SortOrder.ASCENDING)) {
            criteria.addOrder(Order.asc(sortField));
        } else if (sortField != null && sortOrder.equals(SortOrder.DESCENDING)) {
            criteria.addOrder(Order.desc(sortField));
        } else if (sortField == null) {
            criteria.addOrder(Order.desc("idAlerta"));
        }
        
        @SuppressWarnings("unchecked")
        List<Alerta> listado = criteria.list();
        session.close();
        
        return listado;
    }
    
    @Override
    public int getCounCriteria(String usuario) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Alerta.class, "alerta");
        creaCriteria(usuario, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    private void creaCriteria(String usuario, Criteria criteria) {
        
        DetachedCriteria usuarioMensaje = DetachedCriteria.forClass(AlertasNotificacionesUsuario.class, "mensaje");
        usuarioMensaje.add(Restrictions.ilike("mensaje.usuario", usuario, MatchMode.ANYWHERE));
        usuarioMensaje.add(Restrictions.sqlRestriction("TIPO_MENSAJE = '" + TipoMensajeEnum.ALERTA + "'"));
        usuarioMensaje.setProjection(Property.forName("mensaje.idMensaje"));
        
        criteria.add(Property.forName("alerta.idAlerta").in(usuarioMensaje));
        
    }
    
}
