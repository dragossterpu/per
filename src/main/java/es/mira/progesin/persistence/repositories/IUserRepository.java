package es.mira.progesin.persistence.repositories;

import es.mira.progesin.persistence.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<User, String>{
}
