package es.mira.progesin.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Departamento;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RolEquipoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IMiembrosRepository;
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
    
    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private IMiembrosRepository miembroRepository;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @Override
    @Transactional(readOnly = false)
    public void delete(String id) {
        userRepository.delete(id);
    }
    
    @Override
    public boolean exists(String id) {
        return userRepository.exists(id);
    }
    
    @Override
    public User findOne(String id) {
        return userRepository.findOne(id);
    }
    
    @Override
    public User findByUsernameIgnoreCase(String id) {
        return userRepository.findByUsernameIgnoreCase(id);
    }
    
    @Override
    @Transactional(readOnly = false)
    public User save(User entity) {
        return userRepository.save(entity);
    }
    
    @Override
    public User findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase(String correo, String nif) {
        return userRepository.findByCorreoIgnoreCaseOrDocIdentidadIgnoreCase(correo, nif);
    }
    
    private void creaCriteria(UserBusqueda userBusqueda, Criteria criteria) {
        
        criteria.createAlias("usuario.cuerpoEstado", "cuerpoEstado");
        criteria.createAlias("usuario.puestoTrabajo", "puestoTrabajo");
        
        if (userBusqueda.getFechaDesde() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(this_.fecha_alta) >= '" + sdf.format(userBusqueda.getFechaDesde()) + "'"));
        }
        if (userBusqueda.getFechaHasta() != null) {
            /**
             * Hace falta truncar la fecha para recuperar todos los registros de ese día sin importar la hora, sino
             * compara con 0:00:00
             */
            criteria.add(Restrictions
                    .sqlRestriction("TRUNC(this_.fecha_alta) <= '" + sdf.format(userBusqueda.getFechaHasta()) + "'"));
        }
        
        if (userBusqueda.getNombre() != null && !userBusqueda.getNombre().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(Constantes.COMPARADORSINACENTOS, "nombre", userBusqueda.getNombre())));
        }
        if (userBusqueda.getApellido1() != null && !userBusqueda.getApellido1().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(Constantes.COMPARADORSINACENTOS, "PRIM_APELLIDO", userBusqueda.getApellido1())));
        }
        if (userBusqueda.getApellido2() != null && !userBusqueda.getApellido2().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(Constantes.COMPARADORSINACENTOS, "SEGUNDO_APELLIDO", userBusqueda.getApellido2())));
        }
        if (userBusqueda.getUsername() != null && !userBusqueda.getUsername().isEmpty()) {
            criteria.add(Restrictions.sqlRestriction(
                    String.format(Constantes.COMPARADORSINACENTOS, "USERNAME", userBusqueda.getUsername())));
        }
        if (userBusqueda.getCuerpoEstado() != null) {
            criteria.add(Restrictions.eq("cuerpoEstado", userBusqueda.getCuerpoEstado()));
        }
        if (userBusqueda.getPuestoTrabajo() != null) {
            criteria.add(Restrictions.eq("puestoTrabajo", userBusqueda.getPuestoTrabajo()));
        }
        if (userBusqueda.getRole() != null) {
            criteria.add(Restrictions.eq("role", userBusqueda.getRole()));
        }
        if (userBusqueda.getEstado() != null) {
            criteria.add(Restrictions.eq("estado", userBusqueda.getEstado()));
        }
        
        User usuarioActual = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (RoleEnum.ROLE_ADMIN.equals(usuarioActual.getRole()) == Boolean.FALSE) {
            criteria.add(Restrictions.not(Restrictions.in("role",
                    new RoleEnum[] { RoleEnum.ROLE_PROV_SOLICITUD, RoleEnum.ROLE_PROV_CUESTIONARIO })));
        }
        
        criteria.add(Restrictions.isNull("fechaBaja"));
        
    }
    
    @Override
    public List<User> findByCuerpoEstado(CuerpoEstado cuerpo) {
        return userRepository.findByCuerpoEstado(cuerpo);
    }
    
    @Override
    public List<User> findByfechaBajaIsNullAndRoleNotIn(List<RoleEnum> rolesProv) {
        return userRepository.findByfechaBajaIsNullAndRoleNotIn(rolesProv);
    }
    
    @Override
    public void cambiarEstado(String username, EstadoEnum estado) {
        User user = userRepository.findOne(username);
        user.setEstado(estado);
        userRepository.save(user);
    }
    
    @Override
    public List<User> findByfechaBajaIsNullAndRole(RoleEnum rol) {
        return userRepository.findByfechaBajaIsNullAndRole(rol);
    }
    
    @Override
    public List<User> buscarNoJefeNoMiembroEquipo(Equipo equipo) {
        return userRepository.buscarNoJefeNoMiembroEquipo(equipo);
    }
    
    @Override
    public boolean esJefeEquipo(String username) {
        return miembroRepository.existsByUsernameAndPosicion(username, RolEquipoEnum.JEFE_EQUIPO);
    }
    
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
    
    @Override
    public List<User> findByPuestoTrabajo(PuestoTrabajo puesto) {
        return userRepository.findByPuestoTrabajo(puesto);
    }
    
    @Override
    public List<User> findByDepartamento(Departamento departamento) {
        return userRepository.findByDepartamento(departamento);
    }
    
    @Override
    public List<User> buscarUsuarioCriteria(int first, int pageSize, String sortField, SortOrder sortOrder,
            UserBusqueda userBusqueda) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class, "usuario");
        creaCriteria(userBusqueda, criteria);
        criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);
        
        if (sortField != null && sortOrder.equals(SortOrder.ASCENDING)) {
            criteria.addOrder(Order.asc(sortField));
        } else if (sortField != null && sortOrder.equals(SortOrder.DESCENDING)) {
            criteria.addOrder(Order.desc(sortField));
        }
        
        @SuppressWarnings("unchecked")
        List<User> listado = criteria.list();
        session.close();
        
        return listado;
    }
    
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
    
}
