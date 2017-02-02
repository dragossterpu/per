package es.mira.progesin.persistence.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.TipoEquipo;

public interface IEquipoRepository extends CrudRepository<Equipo, Long> {


	@Query("select case when count(e)>0 then true else false end from Equipo e where e.tipoEquipo = :tipo")
	boolean existsByTipoEquipo(@Param("tipo") TipoEquipo tipo);

	// boolean exists(Integer id);

	// void delete(Integer id);

	// Equipo findOne(Integer id);

	
}
