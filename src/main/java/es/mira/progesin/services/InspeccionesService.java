package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import es.mira.progesin.persistence.entities.Inspecciones;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;

@Service
public class InspeccionesService implements IInspeccionesService {
	@Autowired
	IInspeccionesRepository inspeccionesRepository;

	@Override
	public Iterable<Inspecciones> findAll() {
		return inspeccionesRepository.findAll();
	}


	@Override
	public Inspecciones save(Inspecciones inspecciones) {
		return inspeccionesRepository.save(inspecciones);
		
	}

	@Override
	public void delete(Inspecciones inspecciones) {
		inspeccionesRepository.delete(inspecciones);
		
	}
}

