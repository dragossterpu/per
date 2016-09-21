package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.repositories.IUserRepository;

@Service
public class UserService implements IUserService {
	@Autowired
	IUserRepository userRepository;

	
	
	
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

	@Transactional(readOnly = false)
	public Iterable<User> save(Iterable<User> entities) {
		return userRepository.save(entities);
	}

	@Transactional(readOnly = false)
	public User save(User entity) {
		return userRepository.save(entity);
	}
	@Transactional(readOnly = false)
	public User findByParams(String correo,String nif) {
		return userRepository.findByCorreoIgnoringCaseOrDocIndentidadIgnoringCase(correo,nif );
	}
	@Transactional(readOnly = false)
	public User findByCorreoOrDocIndentidad(String correo,String nif) {
		return userRepository.findByCorreoOrDocIndentidad(correo,nif );
	}
	@Transactional(readOnly = false)
	public User findByCorreo(String correo) {
		return userRepository.findByCorreo(correo);
	}
}
