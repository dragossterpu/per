package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.RegistroActividad;

/**
 * 
 * Repositorio de operaciones con base de datos para la entidad RegistroActividad
 * 
 * Es un repositorio de tipo CRUD
 * 
 * @author Ezentis
 *
 */
public interface IRegActividadRepository extends CrudRepository<RegistroActividad, Integer> {
    
    /**
     * 
     * Devuelve una lista de nombre de seccion cuyo nombreSeccion incluya la cadena recibida como parámetro
     * 
     * @param info
     * @return List<String>
     * 
     */
    
    @Query("SELECT DISTINCT(nombreSeccion) FROM RegistroActividad WHERE UPPER(nombreSeccion) LIKE UPPER(:info) ORDER BY nombreSeccion ")
    public List<String> buscarPorNombreSeccion(@Param("info") String info);
    
    /**
     * 
     * Devuelve una lista de nombres de usuario cuyo nombre incluya la cadena recibida como parámetro
     * 
     * @param info
     * @return List<String>
     * 
     */
    
    @Query("SELECT DISTINCT(usernameRegActividad) FROM RegistroActividad WHERE UPPER(usernameRegActividad) LIKE UPPER(:info) ORDER BY usernameRegActividad ")
    public List<String> buscarPorUsuarioRegistro(@Param("info") String info);
    
}
