package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.Inspeccion;

public interface IInspeccionesService {

	public Inspeccion save(Inspeccion inspecciones);

	public Iterable<Inspeccion> findAll();

	public void delete(Inspeccion inspecciones);

	public List<Inspeccion> findByNombreUnidadLikeIgnoringCaseAndFechaFinalizacionNull(String nombreUnidad);

	public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroSinSolicitudNoFinalizada(String infoInspeccion);

	public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroSinSolicitudNoFinalizadaCuestionarioNoFinalizado(
			String infoInspeccion);

}
