package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;

@Service
public class InspeccionesService implements IInspeccionesService {
	@Autowired
	IInspeccionesRepository inspeccionesRepository;

	@Override
	public Iterable<Inspeccion> findAll() {
		return inspeccionesRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public Inspeccion save(Inspeccion inspecciones) {
		return inspeccionesRepository.save(inspecciones);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Inspeccion inspecciones) {
		inspeccionesRepository.delete(inspecciones);
	}

	@Override
	public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroSinSolicitudNoFinalizada(String infoInspeccion) {
		return inspeccionesRepository.buscarNoFinalizadaPorNombreUnidadONumeroSinSolicitudNoFinalizada(infoInspeccion);
	}

	@Override
	public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroSinCuestionarioNoFinalizado(String infoInspeccion) {
		return inspeccionesRepository
				.buscarNoFinalizadaPorNombreUnidadONumeroSinCuestionarioNoFinalizado(infoInspeccion);
	}

	@Override
	public List<Inspeccion> buscarNoFinalizadaPorNombreUnidadONumeroSinSolicitudNoFinalizadaCuestionarioNoFinalizado(
			String infoInspeccion) {
		return inspeccionesRepository
				.buscarNoFinalizadaPorNombreUnidadONumeroSinSolicitudNoFinalizadaCuestionarioNoFinalizado(
						infoInspeccion);
	}

	@Override
	public List<Inspeccion> findByNumeroLike(String numeroInspeccion) {
		return inspeccionesRepository.findByNumeroLike("%" + numeroInspeccion + "%");
	}

	@Override
	public List<Inspeccion> findByNombreUnidadLikeIgnoringCaseAndFechaFinalizacionNull(String nombreUnidad) {
		return inspeccionesRepository.findByNombreUnidadLikeIgnoringCaseAndFechaFinalizacionNull(nombreUnidad);
	}
}
