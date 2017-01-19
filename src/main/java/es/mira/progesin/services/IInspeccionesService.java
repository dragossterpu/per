package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Inspeccion;

public interface IInspeccionesService {

	public Inspeccion save(Inspeccion inspecciones);

	public Iterable<Inspeccion> findAll();

	public void delete(Inspeccion inspecciones);

	/**
	 * Busca inspecciones no finalizadas por nombre de unidad o número que no tengan ya un cuestionario asociado sin
	 * finalizar
	 */
	public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroSinCuestionarioNoFinalizado(String infoInspeccion);

	public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroSinSolicitudNoFinalizada(String infoInspeccion);

	public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroSinSolicitudNoFinalizadaCuestionarioNoFinalizado(
			String infoInspeccion);

	List<Inspeccion> findByNumeroLike(String numeroInspeccion);

	List<Inspeccion> findByNombreUnidadLikeIgnoringCaseAndFechaFinalizacionNull(String nombreUnidad);

}
