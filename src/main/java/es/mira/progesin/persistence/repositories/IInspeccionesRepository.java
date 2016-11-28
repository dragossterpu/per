package es.mira.progesin.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.mira.progesin.persistence.entities.Inspeccion;

public interface IInspeccionesRepository extends JpaRepository<Inspeccion, Long> {

	List<Inspeccion> findByNumeroLike(String numeroInspeccion);

	List<Inspeccion> findByNombreUnidadLikeIgnoringCaseAndFechaFinalizacionNull(String nombreUnidad);

	@Query("SELECT i FROM Inspeccion i WHERE NOT EXISTS (SELECT s FROM SolicitudDocumentacionPrevia s WHERE s.inspeccion = i.id AND s.fechaFinalizacion IS NULL) "
			+ "AND i.fechaFinalizacion IS NULL "
			+ "AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR i.numero LIKE :infoInspeccion) "
			+ "ORDER BY i.nombreUnidad, i.id DESC")
	List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroSinSolicitudNoFinalizada(
			@Param("infoInspeccion") String infoInspeccion);

	@Query("SELECT i FROM Inspeccion i WHERE NOT EXISTS (SELECT s FROM SolicitudDocumentacionPrevia s WHERE s.inspeccion = i.id AND s.fechaFinalizacion IS NULL) "
			// TODO descomentar cuando tenga campo fechaFinalizacion
			// + "AND NOT EXISTS (SELECT c FROM CuestionarioEnvio c WHERE c.inspeccion = i.id AND c.fechaFinalizacion IS
			// NULL)"
			+ "AND i.fechaFinalizacion IS NULL "
			+ "AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR i.numero LIKE :infoInspeccion) "
			+ "ORDER BY i.nombreUnidad, i.id DESC")
	List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroSinSolicitudNoFinalizadaCuestionarioNoFinalizado(
			@Param("infoInspeccion") String infoInspeccion);

	@Query("SELECT i FROM Inspeccion i WHERE i.fechaFinalizacion IS NULL "
			+ "AND (upper(i.nombreUnidad) LIKE upper(:infoInspeccion) OR i.numero LIKE :infoInspeccion) "
			+ "ORDER BY i.nombreUnidad, i.id DESC")
	List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumero(@Param("infoInspeccion") String infoInspeccion);

	@Query(value = "select * from inspecciones i where i.fecha_finalizacion is null "
			+ "and (i.numero like %?1% or upper(i.nombre_unidad) like upper(%?1%)) "
			+ "and not exists (select 1 from cuestionarios_enviados c where c.id_inspeccion = i.id and c.fecha_finalizacion is null)"
			+ "order by nombre_unidad, id desc", nativeQuery = true)
	List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroSinCuestionarioNoFinalizado(String infoInspeccion);

}
