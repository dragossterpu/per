package es.mira.progesin.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ExcepcionRollback;
import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IUserRepository;
import es.mira.progesin.web.beans.UserBusqueda;
import lombok.NoArgsConstructor;

/**
 * @author EZENTIS
 * 
 * Sevicio para la clase Usuario
 *
 */
@NoArgsConstructor
@Service
public class UserService implements IUserService {
    
    /**
     * Repositorio de usuarios.
     */
    @Autowired
    private IUserRepository userRepository;
    
    /**
     * Factoría de sesiones.
     */
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * Encriptador de palabras clave.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Formato de fecha.
     */
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * Constructor usado para el test.
     * 
     * @param sessionFact Factoría de sesiones
     */
    public UserService(SessionFactory sessionFact) {
        this.sessionFactory = sessionFact;
    }
    
    /**
     * Elimina el usuario seleccionado de la base de datos.
     * 
     * @param id Username del usuario a eliminar
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(String id) {
        userRepository.delete(id);
    }
    
    /**
     * Comprueba la existencia de un usuario en la base de datos.
     * 
     * @param id Username del usuario a buscar
     * @return Respuesta de la consulta.
     */
    @Override
    public boolean exists(String id) {
        return userRepository.exists(id);
    }
    
    /**
     * Comprueba si existe el usuario provisinal en BBDD.
     * 
     * @param username Usuario a comprobar
     * @throws ExcepcionRollback se lanza si el usuario ya existe
     */
    @Override
    public void comprobarNoExisteUsuarioProvisional(String username) throws ExcepcionRollback {
        if (userRepository.exists(username)) {
            throw new ExcepcionRollback("El usuario con correo " + username + " ya existe en el sistema.");
        }
    }
    
    /**
     * Busca en base de datos un usuario identificado por su username.
     * 
     * @param id Username del usuario a buscar
     * @return User Usuario que correponde a la búsqueda.
     */
    @Override
    public User findOne(String id) {
        return userRepository.findOne(id);
    }
    
    /**
     * Busca en base de datos un usuario identificado por su username ignorando mayúsculas/minúsculas.
     * 
     * @param id Username del usuario a buscar
     * @return Usuario que correponde a la búsqueda.
     */
    @Override
    public User findByUsernameIgnoreCase(String id) {
        return userRepository.findByUsernameIgnoreCase(id);
    }
    
    /**
     * Guarda en base de datos el usuario.
     * 
     * @param entity Usuario a guardar
     * @return Usuario guardado.
     */
    
    @Override
    @Transactional(readOnly = false)
    public User save(User entity) {
        return userRepository.save(entity);
    }
    
    /**
     * Busca una lista de usuarios por su correo o su documento de identidad, ignorando mayúsculas..
     * 
     * @param correo correo del usuario a buscar
     * @param nif documento del usuario a buscar
     * @return resultado de la búsqueda
     * 
     */
    @Override
    public List<User> findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase(String correo, String nif) {
        return userRepository.findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase(correo, nif);
    }
    
    /**
     * Introduce parámetros de búsqueda dentro del criteria.
     * 
     * @param userBusqueda Objeto que contiene los parámetros a introducir
     * @param criteria Criteria que se desea modificar.
     */
    private void creaCriteria(UserBusqueda userBusqueda, Criteria criteria) {
        
        if (userBusqueda.getFechaDesde() != null) {
            criteria.add(Restrictions.ge(Constantes.FECHAALTA, userBusqueda.getFechaDesde()));
        }
        
        if (userBusqueda.getFechaHasta() != null) {
            Date fechaHasta = new Date(userBusqueda.getFechaHasta().getTime() + TimeUnit.DAYS.toMillis(1));
            criteria.add(Restrictions.le(Constantes.FECHAALTA, fechaHasta));
        }
        
        criteriaDatosPersonales(criteria, userBusqueda.getNombre(), userBusqueda.getApellido1(),
                userBusqueda.getApellido2());
        
        if (userBusqueda.getUsername() != null) {
            criteria.add(Restrictions.ilike("username", userBusqueda.getUsername(), MatchMode.ANYWHERE));
        }
        if (userBusqueda.getRole() != null) {
            criteria.add(Restrictions.eq("role", userBusqueda.getRole()));
        }
        if (userBusqueda.getEstado() != null) {
            criteria.add(Restrictions.eq("estado", userBusqueda.getEstado()));
        }
        
        criteriaCuerpoPuesto(criteria, userBusqueda.getCuerpoEstado(), userBusqueda.getPuestoTrabajo());
        criteriaRoleProvisional(criteria);
        
        criteria.add(Restrictions.isNull("fechaBaja"));
        
    }
    
    /**
     * Añade al criteria los filtros relacionados con los datos personales de un usuario.
     * 
     * @param criteria Criteria
     * @param nombre nombre introducido en el filtro
     * @param apellido1 apellido1 introducido en el filtro
     * @param apellido2 apellido2 introducido en el filtro
     */
    private void criteriaDatosPersonales(Criteria criteria, String nombre, String apellido1, String apellido2) {
        if (nombre != null) {
            criteria.add(Restrictions.ilike("nombre", nombre, MatchMode.ANYWHERE));
        }
        
        if (apellido1 != null) {
            criteria.add(Restrictions.ilike("apellido1", apellido1, MatchMode.ANYWHERE));
        }
        
        if (apellido2 != null) {
            criteria.add(Restrictions.ilike("apellido2", apellido2, MatchMode.ANYWHERE));
        }
    }
    
    /**
     * Añade al criteria el fitro con los roles provisionales para mostrar en los resultados de búsqueda los usuarios
     * provisionales, siempre que el usuario conectado sea un usuario administrador.
     * 
     * @param criteria Criteria
     */
    private void criteriaRoleProvisional(Criteria criteria) {
        User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (RoleEnum.ROLE_ADMIN.equals(usuarioActual.getRole()) == Boolean.FALSE) {
            criteria.add(Restrictions.not(Restrictions.in("role",
                    new RoleEnum[] { RoleEnum.ROLE_PROV_SOLICITUD, RoleEnum.ROLE_PROV_CUESTIONARIO })));
        }
    }
    
    /**
     * Añade al criteria los filtros relacionados el puesto de trabajo y el cuerpo de los filtros.
     * 
     * @param criteria Criteria
     * @param cuerpo cuerpo introducido en el filtro
     * @param puesto puesto introducido en el filtro
     */
    private void criteriaCuerpoPuesto(Criteria criteria, CuerpoEstado cuerpo, PuestoTrabajo puesto) {
        criteria.createAlias("usuario.cuerpoEstado", "cuerpoEstado", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("usuario.puestoTrabajo", "puestoTrabajo", JoinType.LEFT_OUTER_JOIN);
        
        if (cuerpo != null) {
            criteria.add(Restrictions.eq("cuerpoEstado", cuerpo));
        }
        if (puesto != null) {
            criteria.add(Restrictions.eq("puestoTrabajo", puesto));
        }
    }
    
    /**
     * Recupera un listado de usuarios que no hayan sido de baja y que cuyo rol no esté en el listado que se recibe como
     * parámetro.
     * 
     * @param rolesProv listado de roles a los que no queremos que pertenezcan los usuarios
     * @return resultado de la búsqueda
     */
    @Override
    public List<User> findByfechaBajaIsNullAndRoleNotIn(List<RoleEnum> rolesProv) {
        return userRepository.findByfechaBajaIsNullAndRoleNotIn(rolesProv);
    }
    
    /**
     * Cambia el estado del usuario.
     * 
     * @param username Username del usuario al que se desea cambiar el estado
     * @param estado Estado que se desea asignar al usuario
     */
    @Override
    public void cambiarEstado(String username, EstadoEnum estado) {
        User user = userRepository.findOne(username);
        user.setEstado(estado);
        userRepository.save(user);
    }
    
    /**
     * Recupera un listado de usuarios que no hayan sido de baja y que cuyo rol corresponda al que se recibe como
     * parámetro.
     * 
     * @param rol al que no queremos que pertenezcan los usuarios
     * @return resultado de la búsqueda
     */
    @Override
    public List<User> findByfechaBajaIsNullAndRole(RoleEnum rol) {
        return userRepository.findByfechaBajaIsNullAndRole(rol);
    }
    
    /**
     * Buscar todos aquellos usuarios que no son jefe de algún equipo o miembros de este equipo.
     * 
     * @return resultado de la búsqueda
     */
    @Override
    public List<User> buscarUserSinEquipo() {
        return userRepository.buscarNoMiembro();
    }
    
    /**
     * Crea usuario provisionales.
     * 
     * @param correoPrincipal Correo electrónico del usuario principal
     * @param password Palabra clave
     * @return Lista de usuarios Lista de usuarios creados
     */
    @Override
    public List<User> crearUsuariosProvisionalesCuestionario(String correoPrincipal, String password) {
        List<User> listaUsuarios = new ArrayList<>();
        // Usuario principal
        String passwordEncoded = passwordEncoder.encode(password);
        User user = new User(correoPrincipal, passwordEncoded, RoleEnum.ROLE_PROV_CUESTIONARIO);
        listaUsuarios.add(user);
        String cuerpoCorreo = correoPrincipal.substring(0, correoPrincipal.indexOf('@'));
        String restoCorreo = correoPrincipal.substring(correoPrincipal.lastIndexOf('@'));
        // Todos los usuarios creados a partir del principal tendrán la misma contraseña que el principal
        for (int i = 1; i < 10; i++) {
            listaUsuarios.add(new User(cuerpoCorreo + i + restoCorreo, passwordEncoded, RoleEnum.ROLE_PROV_CUESTIONARIO,
                    correoPrincipal));
        }
        return listaUsuarios;
    }
    
    /**
     * Devuelve una lista de usuarios en función de los criterios de búsqueda recibidos como parámetro. El listado se
     * devuelve paginado.
     * 
     * @param first Primer elemento del listado
     * @param pageSize Número máximo de registros recuperados
     * @param sortField Campo por el que sed realiza la ordenación del listado
     * @param sortOrder Sentido de la ordenación
     * @param userBusqueda Objeto que contiene los parámetros de búsqueda
     * 
     * @return Listado resultante de la búsqueda
     */
    
    @Override
    public List<User> buscarUsuarioCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            UserBusqueda userBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class, "usuario");
        creaCriteria(userBusqueda, criteria);
        criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);
        
        if (sortField != null) {
            if (SortOrder.ASCENDING.equals(sortOrder)) {
                criteria.addOrder(Order.asc(sortField));
            } else {
                criteria.addOrder(Order.desc(sortField));
            }
        } else {
            criteria.addOrder(Order.desc(Constantes.FECHAALTA));
        }
        
        @SuppressWarnings("unchecked")
        List<User> listado = criteria.list();
        session.close();
        
        return listado;
    }
    
    /**
     * Devuelve el número total de registros resultado de la búsqueda.
     * 
     * @param userBusqueda Objeto que contiene los parámetros de búsqueda
     * @return número de registros resultantes de la búsqueda
     */
    @Override
    public int contarRegistros(UserBusqueda userBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class, "usuario");
        creaCriteria(userBusqueda, criteria);
        criteria.setProjection(Projections.rowCount());
        Long cnt = (Long) criteria.uniqueResult();
        session.close();
        
        return Math.toIntExact(cnt);
    }
    
    /**
     * Guarda en BBDD un listado de usuarios provisionales.
     * 
     * @param listadoUsuariosProvisionales listardo de usuarios provisionales
     * @return lista de usuarios grabados
     * 
     */
    @Override
    public List<User> save(List<User> listadoUsuariosProvisionales) {
        return (List<User>) userRepository.save(listadoUsuariosProvisionales);
    }
    
    /**
     * Comprueba la existencia de usuarios que tengan asignado un cuerpo de estado.
     * 
     * @param cuerpo cuerpo que se desea verificar
     * @return resultado de la consulta
     */
    @Override
    public boolean existsByCuerpoEstado(CuerpoEstado cuerpo) {
        return userRepository.existsByCuerpoEstado(cuerpo);
    }
    
    /**
     * Comprueba la existencia de usuarios que tengan asignado un puesto de trabajo.
     * 
     * @param puesto puesto de trabajo que se desea verificar
     * @return resultado de la consulta
     */
    @Override
    public boolean existsByPuestoTrabajo(PuestoTrabajo puesto) {
        return userRepository.existsByPuestoTrabajo(puesto);
    }
    
    /**
     * Comprueba la existencia de usuarios que tengan asignado un departamento.
     * 
     * @param departamento departamento que se desea verificar
     * @return resultado de la consulta
     */
    @Override
    public boolean existsByDepartamento(Departamento departamento) {
        return userRepository.existsByDepartamento(departamento);
    }
    
    /**
     * Devuelve una lista de usuarios que pertenecen al equipo recibido como parámetro.
     * 
     * @param equipo Equipo del que se desean extraer sus usuarios
     * @return Lista de usuarios.
     */
    @Override
    public List<User> usuariosEquipo(Equipo equipo) {
        return userRepository.usuariosEnEquipo(equipo);
    }
    
    /**
     * Buscar todos aquellos usuarios con rol 'ROLE_EQUIPO_INSPECCIONES' que no son miembros del equipo pasado por
     * parámetro ni jefe de ningun equipo.
     * @param idEquipo equipo al que no pertenecen
     * 
     * @return resultado de la búsqueda
     */
    @Override
    public List<User> buscarNoMiembroEquipoNoJefe(Long idEquipo) {
        return userRepository.buscarPosibleMiembroEquipoNoJefe(idEquipo);
    }
    
    /**
     * Obtiene los usuarios provisionales que comparten un correo electrónico pasado como parámetro.
     * 
     * @param correo Correo por el que se buscará
     * @return Usuarios resultantes
     */
    @Override
    public List<User> listaUsuariosProvisionalesCorreo(String correo) {
        return userRepository.usuariosProvisionalesCorreo(correo);
    }
    
}
