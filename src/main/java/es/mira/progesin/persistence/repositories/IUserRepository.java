package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.User;

public interface IUserRepository extends CrudRepository<User, String>{
}
