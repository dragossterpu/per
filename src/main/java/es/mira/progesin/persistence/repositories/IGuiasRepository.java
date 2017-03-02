package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.Guia;

public interface IGuiasRepository extends CrudRepository<Guia, Long> {

	@Override
	List<Guia> findAll();

}
