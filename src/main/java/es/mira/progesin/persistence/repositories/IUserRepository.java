package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.User;

public interface IUserRepository extends CrudRepository<User, String> {
	User findByCorreoIgnoringCaseOrDocIndentidadIgnoringCase(String correo, String docIndentidad);

	User findByCorreo(String correo);

	User findByCorreoOrDocIndentidad(String correo, String docIndentidad);
}
