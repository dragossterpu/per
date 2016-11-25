package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Miembros;

public interface IMiembrosRepository extends CrudRepository<Miembros, String> {

	List<Miembros> findByIdMiembros(Long idMiembros);

}
