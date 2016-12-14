package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.repositories.ITipoEquiposRepository;

@Service
public class TipoEquipoService implements ITipoEquipoService {
	@Autowired
	ITipoEquiposRepository tipoEquipoaRepository;

	@Override
	public Iterable<TipoEquipo> findAll() {
		return tipoEquipoaRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		tipoEquipoaRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public TipoEquipo save(TipoEquipo entity) {
		return tipoEquipoaRepository.save(entity);
	}
}
