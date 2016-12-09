package es.mira.progesin.persistence.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.RegistroActividad;

public interface IRegActividadRepository extends CrudRepository<RegistroActividad, Integer> {
	
	@Query("SELECT DISTINCT(nombreSeccion) FROM RegistroActividad WHERE UPPER(nombreSeccion) LIKE UPPER(:info) ORDER BY nombreSeccion ")
	public List<String> buscarPorNombreSeccion(@Param("info") String info);
	
	@Query("SELECT DISTINCT(usernameRegActividad) FROM RegistroActividad WHERE UPPER(usernameRegActividad) LIKE UPPER(:info) ORDER BY usernameRegActividad ")
	public List<String> buscarPorUsuarioRegistro(@Param("info") String info);
	
	
	
}
