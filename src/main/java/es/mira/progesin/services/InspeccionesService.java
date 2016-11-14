package es.mira.progesin.services;

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
	public Inspeccion save(Inspeccion inspecciones) {
		return inspeccionesRepository.save(inspecciones);
		
	}

	@Override
	public void delete(Inspeccion inspecciones) {
		inspeccionesRepository.delete(inspecciones);
		
	}
}

