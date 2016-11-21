package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
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

	List<User> findByCuerpoEstado(CuerpoEstado cuerpo);

	List<User> findByfechaBajaIsNullAndRoleNotIn(List<RoleEnum> rolesProv);

	void cambiarEstado(String usuarioProv, EstadoEnum estado);

}
