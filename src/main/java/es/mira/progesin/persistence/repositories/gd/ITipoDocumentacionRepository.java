package es.mira.progesin.persistence.repositories.gd;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.gd.TipoDocumentacion;

public interface ITipoDocumentacionRepository extends CrudRepository<TipoDocumentacion, Long> {
	@Override
	List<TipoDocumentacion> findAll();

	List<TipoDocumentacion> findByAmbito(AmbitoInspeccionEnum ambito);

}
