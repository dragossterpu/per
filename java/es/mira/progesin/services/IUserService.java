package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.web.beans.UserBusqueda;

public interface IUserService {
	void delete(String id);

	void delete(Iterable<User> entities);

	void delete(User entity);

	void deleteAll();

	boolean exists(String id);

	Iterable<User> findAll();

	Iterable<User> findAll(Iterable<String> ids);

	User findOne(String id);

	Iterable<User> save(Iterable<User> entities);

	User save(User entity);

	User findByParams(String nif, String correo);

	User findByCorreo(String correo);

	User findByCorreoOrDocIndentidad(String nif, String correo);

	List<User> buscarUsuarioCriteria(UserBusqueda userBusqueda);
}