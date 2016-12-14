package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembros;

public interface IMiembrosRepository extends CrudRepository<Miembros, Long> {

	List<Miembros> findByEquipo(Equipo equipo);

}
