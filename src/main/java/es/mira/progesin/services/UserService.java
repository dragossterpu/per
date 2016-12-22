package es.mira.progesin.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IUserRepository;
import es.mira.progesin.web.beans.UserBusqueda;

@Service
public class UserService implements IUserService {
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Obligado por sonar
	private static final String FECHA_ALTA = "fechaAlta";

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


	@Override
	@Transactional(readOnly = false)
	public void delete(String id) {
		userRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Iterable<User> entities) {
		userRepository.delete(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(User entity) {
		userRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		userRepository.deleteAll();
	}

	@Override
	public boolean exists(String id) {
		return userRepository.exists(id);
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Iterable<User> findAll(Iterable<String> ids) {
		return userRepository.findAll(ids);
	}

	@Override
	public User findOne(String id) {
		return userRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Iterable<User> save(Iterable<User> entities) {
		return userRepository.save(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public User save(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public User findByParams(String correo, String nif) {
		return userRepository.findByCorreoIgnoringCaseOrDocIndentidadIgnoringCase(correo, nif);
	}

	@Override
	public User findByCorreoOrDocIndentidad(String correo, String nif) {
		return userRepository.findByCorreoOrDocIndentidad(correo, nif);
	}

	@Override
	public User findByCorreo(String correo) {
		return userRepository.findByCorreo(correo);
	}

	@Override
	public List<User> buscarUsuarioCriteria(UserBusqueda userBusqueda) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(User.class);

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
			criteria.add(Restrictions.sqlRestriction("upper(convert(replace(nombre, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('" + userBusqueda.getNombre()+"', ' ', '') || '%', 'US7ASCII'))"));
		}
		if (userBusqueda.getApellido1() != null && !userBusqueda.getApellido1().isEmpty()) {
			criteria.add(Restrictions.sqlRestriction("upper(convert(replace(PRIM_APELLIDO, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('" + userBusqueda.getApellido1()+"', ' ', '') || '%', 'US7ASCII'))"));
		}
		if (userBusqueda.getApellido2() != null && !userBusqueda.getApellido2().isEmpty()) {
			criteria.add(Restrictions.sqlRestriction("upper(convert(replace(SEGUNDO_APELLIDO, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('" + userBusqueda.getApellido2()+"', ' ', '') || '%', 'US7ASCII'))"));
		}
		if (userBusqueda.getUsername() != null && !userBusqueda.getUsername().isEmpty()) {
			criteria.add(Restrictions.sqlRestriction("upper(convert(replace(USERNAME, ' ', ''), 'US7ASCII')) LIKE upper(convert('%' || replace('" + userBusqueda.getUsername()+"', ' ', '') || '%', 'US7ASCII'))"));
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

		criteria.add(Restrictions.isNull("fechaBaja"));
		criteria.addOrder(Order.desc(FECHA_ALTA));

		@SuppressWarnings("unchecked")
		List<User> listaUsuarios = criteria.list();
		session.close();

		return listaUsuarios;
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
	public List<User> crearUsuariosProvisionalesCuestionario(String correoPrincipal, String password) {
		List<User> listaUsuarios = new ArrayList<>();
		// Usuario principal
		String passwordEncoded = passwordEncoder.encode(password);
		User user = new User(correoPrincipal, passwordEncoded, RoleEnum.PROV_CUESTIONARIO);
		listaUsuarios.add(user);
		String cuerpoCorreo = correoPrincipal.substring(0, correoPrincipal.indexOf('@'));
		String restoCorreo = correoPrincipal.substring(correoPrincipal.lastIndexOf('@'));
		// Todos los usuarios creados a partir del principal tendrán la misma contraseña que el principal
		for (int i = 1; i < 10; i++) {
			listaUsuarios.add(new User(cuerpoCorreo + i + restoCorreo, passwordEncoded, RoleEnum.PROV_CUESTIONARIO,
					correoPrincipal));
		}
		System.out.println(listaUsuarios);
		return listaUsuarios;
	}
}
