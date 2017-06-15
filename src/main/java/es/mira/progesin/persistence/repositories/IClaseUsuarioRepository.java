package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.ClaseUsuario;

/**
 * Repositorio para la clase a la que pertenece un usuario.
 * 
 * @author EZENTIS
 *
 */
public interface IClaseUsuarioRepository extends CrudRepository<ClaseUsuario, Long> {
    
    /**
     * Busca todos las clases de usuario dados de alta en la BBDD ordenados por su descripción.
     * 
     * @return lista con todas las clases en BBDD
     */
    List<ClaseUsuario> findAllByOrderByClaseAsc();
}
