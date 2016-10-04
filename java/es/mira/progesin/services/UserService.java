package es.mira.progesin.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.repositories.IUserRepository;
import es.mira.progesin.web.beans.UserBusqueda;

@Service
public class UserService implements IUserService {
	@Autowired
	IUserRepository userRepository;

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional(readOnly = false)
	public void delete(String id) {
		userRepository.delete(id);
	}

	@Transactional(readOnly = false)
	public void delete(Iterable<User> entities) {
		userRepository.delete(entities);
	}

	@Transactional(readOnly = false)
	public void delete(User entity) {
		userRepository.delete(entity);
	}

	@Transactional(readOnly = false)
	public void deleteAll() {
		userRepository.deleteAll();
	}

	public boolean exists(String id) {
		return userRepository.exists(id);
	}

	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	public Iterable<User> findAll(Iterable<String> ids) {
		return userRepository.findAll(ids);
	}

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

	public User findByParams(String correo, String nif) {
		return userRepository.findByCorreoIgnoringCaseOrDocIndentidadIgnoringCase(correo, nif);
	}

	public User findByCorreoOrDocIndentidad(String correo, String nif) {
		return userRepository.findByCorreoOrDocIndentidad(correo, nif);
	}

	public User findByCorreo(String correo) {
		return userRepository.findByCorreo(correo);
	}

	@Override
	public List<User> buscarUsuarioCriteria(UserBusqueda userBusqueda) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(User.class);

		if (userBusqueda.getFechaDesde() != null) {
			criteria.add(Restrictions.ge("fechaAlta", userBusqueda.getFechaDesde()));
		}
		if (userBusqueda.getFechaHasta() != null) {
			criteria.add(Restrictions.lt("fechaAlta", userBusqueda.getFechaHasta()));
		}
		if (userBusqueda.getNombre() != null && !userBusqueda.getNombre().isEmpty()) {
			criteria.add(Restrictions.ilike("nombre", userBusqueda.getNombre(), MatchMode.ANYWHERE));
		}
		if (userBusqueda.getApellido1() != null && !userBusqueda.getApellido1().isEmpty()) {
			criteria.add(Restrictions.ilike("apellido1", userBusqueda.getApellido1(), MatchMode.ANYWHERE));
		}
		if (userBusqueda.getUsername() != null && !userBusqueda.getUsername().isEmpty()) {
			criteria.add(Restrictions.ilike("username", userBusqueda.getUsername(), MatchMode.ANYWHERE));
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
		criteria.addOrder(Order.desc("fechaAlta"));

		@SuppressWarnings("unchecked")
		List<User> listaUsuarios = (List<User>) criteria.list();
		session.close();

		return listaUsuarios;
	}

}
