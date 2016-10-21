package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.PreEnvioCuest;

public interface IPreEnvioCuestionarioRepository extends CrudRepository<PreEnvioCuest, Integer> {
	@Override
	List<PreEnvioCuest> findAll();

}
