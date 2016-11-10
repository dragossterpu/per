package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;

public interface IUserRepository extends CrudRepository<User, String> {
	User findByCorreoIgnoringCaseOrDocIndentidadIgnoringCase(String correo, String docIndentidad);

	User findByCorreo(String correo);

	User findByCorreoOrDocIndentidad(String correo, String docIndentidad);

	List<User> findByCuerpoEstado(CuerpoEstado cuerpo);

	List<User> findByfechaBajaIsNullAndRoleNotIn(List<RoleEnum> rolesProv);

}
