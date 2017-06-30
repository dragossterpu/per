package es.mira.progesin.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.exceptions.CorreoException;
import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.repositories.IAlertaRepository;
import es.mira.progesin.util.ICorreoElectronico;

/**
 * 
 * Implementación del servicio de alertas.
 * 
 * @author EZENTIS
 * 
 */

@Service
public class AlertaService implements IAlertaService {
    /**
     * Repositorio de alertas.
     */
    @Autowired
    private IAlertaRepository alertaRepository;
    
    /**
     * Servicio de alertas y notificaciones de usuario.
     */
    @Autowired
    private IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
    
    /**
     * Servicio de correo electrónico.
     */
    @Autowired
    private ICorreoElectronico correo;
    
    /**
     * Servicio de usuario.
     */
    @Autowired
    private IUserService userService;
    
    /**
     * Servicio del registro de actividad.
     */
    @Autowired
    private IRegistroActividadService registroActividadService;
    
    /**
     * Factoría de sesiones.
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Servicio para usar los métodos usados junto con criteria.
     */
    @Autowired
    private ICriteriaService criteriaService;
    
    /**
     * 
     * Elimina de la base de datos una alerta cuyo id se recibe como parámetro.
     * 
     * @param id de la alerta a eliminar
     * 
     */
    
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        alertaRepository.delete(id);
    }
    
    /**
     * 
     * Elimina de la base de datos todas las alertas.
     * 
     * 
     */
    
    @Override
    @Transactional(readOnly = false)
    public void deleteAll() {
        alertaRepository.deleteAll();
    }
    
    /**
     * 
     * Comprueba si existe en de base de datos una alerta cuyo id se pasa como parámetro.
     * 
     * @param id de la alerta a buscar en base de datos
     * @return booleano con el valor de la consulta
     *
     */
    @Override
    public boolean exists(Long id) {
        return alertaRepository.exists(id);
    }
    
    /**
     * 
     * Busca en base de datos todas las alertas que no hayan sido dadas de baja.
     * 
     * @return Lista de las alertas cuya fecha de baja es nulo
     * 
     */
    @Override
    public List<Alerta> findByFechaBajaIsNull() {
        return alertaRepository.findByFechaBajaIsNull();
    }
    
    /**
     * 
     * Busca en base de datos las alerta cuyo id se recibe como parámetro.
     * 
     * @param id de la alerta a localizar en base de datos
     * @return alerta que corresponde a la búsqueda
     * 
     */
    @Override
    public Alerta findOne(Long id) {
        return alertaRepository.findOne(id);
    }
    
    /**
     * Crea la alerta con los valores recibidos como parámetros.
     * 
     * @param seccion Sección en la que se produce la alerta.
     * @param descripcion Descripción de la alerta.
     * @return Alerta creada.
     */
    private Alerta crearAlerta(String seccion, String descripcion) {
        Alerta alerta = new Alerta();
        alerta.setDescripcion(descripcion);
        alerta.setNombreSeccion(seccion);
        return alertaRepository.save(alerta);
    }
    
    /**
     * 
     * Crea una alerta y se asigna a u usuario. Se crea a partir de la sección, la descripción y el usuario que se
     * reciben como parámetros.
     * 
     * @param seccion Sección para la que se crea la alerta
     * @param descripcion Descripción de la alerta
     * @param usuario Usuario al que se envía la alerta
     * 
     */
    @Override
    public void crearAlertaUsuario(String seccion, String descripcion, User usuario) {
        try {
            Alerta alerta = crearAlerta(seccion, descripcion);
            alertasNotificacionesUsuarioService.grabarMensajeUsuario(alerta, usuario.getUsername());
            correo.envioCorreo(usuario.getCorreo(), "Nueva alerta PROGESIN",
                    "Se ha generado una nueva alerta en la aplicacion PROGESIN:\n " + descripcion);
            
        } catch (DataAccessException | CorreoException e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
    /**
     * 
     * Crea una alerta y se asigna a una lista de usuarios.
     * 
     * @param seccion Sección para la que se crea la alerta
     * @param descripcion Descripción de la alerta
     * @param listaUsuarios Listado de usuarios a los que se debe enviar la alerta
     * 
     */
    @Override
    public void crearAlertaMultiplesUsuarios(String seccion, String descripcion, List<User> listaUsuarios) {
        try {
            List<String> listaCorreos = new ArrayList<>();
            Alerta alerta = crearAlerta(seccion, descripcion);
            
            for (User usuario : listaUsuarios) {
                alertasNotificacionesUsuarioService.grabarMensajeUsuario(alerta, usuario.getUsername());
                listaCorreos.add(usuario.getCorreo());
            }
            correo.envioCorreo(listaCorreos, "Nueva alerta PROGESIN",
                    "Se ha generado una nueva alerta en la aplicacion PROGESIN:\n " + descripcion);
            
        } catch (DataAccessException | CorreoException e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
    }
    
    /**
     * 
     * Crea una alerta y se asigna a un rol. Se crea a partir de la sección, la descripción y el rol que se reciben como
     * parámetros.
     * 
     * @param seccion para la que se crea la alerta
     * @param descripcion de la alerta
     * @param rol Se enviará la alerta a todos los usuarios cuyo rol corresponda al pasado como parámetro
     * 
     */
    @Override
    public void crearAlertaRol(String seccion, String descripcion, RoleEnum rol) {
        try {
            List<User> usuariosRol = userService.findByfechaBajaIsNullAndRole(rol);
            crearAlertaMultiplesUsuarios(seccion, descripcion, usuariosRol);
        } catch (DataAccessException e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
    /**
     * 
     * Crea una alerta y se asigna a varios roles. Se crea a partir de la sección, la descripción y la lista de roles
     * que se reciben como parámetros.
     * 
     * @param seccion para la que se crea la alerta
     * @param descripcion de la alerta
     * @param roles Se enviará la alerta a todos los usuarios cuyo rol esté contenido en la lista de roles que se recibe
     * como parámetro
     * 
     */
    @Override
    public void crearAlertaRol(String seccion, String descripcion, List<RoleEnum> roles) {
        for (RoleEnum rol : roles) {
            crearAlertaRol(seccion, descripcion, rol);
        }
        
    }
    
    /**
     * 
     * Crea una alerta y se asigna a un equipo de inspección. Se crea a partir de la sección, la descripción y la
     * inspección que se reciben como parámetros.
     * 
     * @param seccion para la que se crea la alerta
     * @param descripcion de la alerta
     * @param inspeccion Se enviará la alerta a todos los miembros del equipo que tiene asignada esta inspección
     * 
     */
    @Override
    public void crearAlertaEquipo(String seccion, String descripcion, Inspeccion inspeccion) {
        try {
            List<User> listausuarios = userService.usuariosEquipo(inspeccion.getEquipo());
            crearAlertaMultiplesUsuarios(seccion, descripcion, listausuarios);
        } catch (DataAccessException e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
    /**
     * 
     * Crea una alerta y se asigna al jefe de un equipo asignado a una inspección. Se crea a partir de la sección, la
     * descripción y la inspección que se reciben como parámetros
     * 
     * @param seccion para la que se crea la alerta
     * @param descripcion de la alerta
     * @param inspeccion se envia la alerta al jefe del equipo que tiene asignada la inspección recibida como parámetro
     * 
     */
    @Override
    public void crearAlertaJefeEquipo(String seccion, String descripcion, Inspeccion inspeccion) {
        try {
            User usuario = userService.findOne(inspeccion.getEquipo().getJefeEquipo().getUsername());
            crearAlertaUsuario(seccion, descripcion, usuario);
            
        } catch (DataAccessException e) {
            registroActividadService.altaRegActividadError(seccion, e);
        }
        
    }
    
    /**
     * 
     * Busca en la base de datos por los campos pasados como parámetro. La búsqueda permite paginación.
     * 
     * @param first Primer elemento a recuperar del listado.
     * @param pageSize Número máximo de resultados a recuperar
     * @param sortField Campo por el cual se ordenarán los resultados
     * @param sortOrder Sentido de la ordenación (Ascendente/descendente)
     * @param usuario Usuario para el cual se buscan sus alertas
     * @return Listado de las alertas que resulten de la búsqueda
     */
    @Override
    public List<Alerta> buscarPorCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            String usuario) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Alerta.class, "alerta");
        creaCriteria(usuario, criteria);
        
        criteriaService.prepararPaginacionOrdenCriteria(criteria, first, pageSize, sortField, sortOrder, "idAlerta");
        
        @SuppressWarnings("unchecked")
        List<Alerta> listado = criteria.list();
        session.close();
        
        return listado;
    }
    
    /**
     * Devuelve el número de alertas existentes en base de datos para el usuario pasado como parámetro.
     * 
     * @param usuario Usuario para el cual se buscan las alertas
     * @return integer correspondiente al número total de las alertas contenidas en base de datos para el usuario
     */
    @Override
    public int getCounCriteria(String usuario) {
        Session session = sessionFactory.openSession();
        Criteria criteriaAlerta = session.createCriteria(Alerta.class, "alerta");
        creaCriteria(usuario, criteriaAlerta);
        criteriaAlerta.setProjection(Projections.rowCount());
        Long cnt = (Long) criteriaAlerta.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    /**
     * Añade los parámetros de búsqueda al criteria.
     * 
     * @param usuario Usuario para el que se realiza la búsqueda.
     * @param criteria Criteria al que se le añadirán los parámetros.
     */
    private void creaCriteria(String usuario, Criteria criteria) {
        
        DetachedCriteria mensaje = DetachedCriteria.forClass(AlertasNotificacionesUsuario.class, "mensaje");
        mensaje.add(Restrictions.ilike("mensaje.usuario", usuario, MatchMode.ANYWHERE));
        mensaje.add(Restrictions.eq("mensaje.tipo", TipoMensajeEnum.ALERTA));
        mensaje.setProjection(Property.forName("mensaje.idMensaje"));
        
        criteria.add(Property.forName("alerta.idAlerta").in(mensaje));
        
    }
    
}
