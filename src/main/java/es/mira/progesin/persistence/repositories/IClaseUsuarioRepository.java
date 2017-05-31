package es.mira.progesin.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.ClaseUsuario;

/**
 * Repositorio para la clase a la que pertenece un usuario.
 * 
 * @author EZENTIS
 *
 */
public interface IClaseUsuarioRepository extends CrudRepository<ClaseUsuario, Long> {
    
}
