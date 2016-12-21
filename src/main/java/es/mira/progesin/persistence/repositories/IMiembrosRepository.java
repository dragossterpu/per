package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Miembro;

public interface IMiembrosRepository extends CrudRepository<Miembro, Long> {

	List<Miembro> findByEquipo(Equipo equipo);

}
