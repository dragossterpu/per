package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Inspeccion;

public interface IInspeccionesRepository extends CrudRepository<Inspeccion, Long> {

	List<Inspeccion> findByNumeroLike(String numeroInspeccion);

	List<Inspeccion> findByNombreUnidadLikeIgnoringCaseAndFechaFinalizacionNull(String nombreUnidad);

	@Query("SELECT i FROM Inspeccion i WHERE " + "i.fechaFinalizacion IS NULL "
			+ "AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR i.numero LIKE :infoInspeccion) "
			+ "ORDER BY i.nombreUnidad, i.id DESC")
	List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumero(@Param("infoInspeccion") String infoInspeccion);
}
