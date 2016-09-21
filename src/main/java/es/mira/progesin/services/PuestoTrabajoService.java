package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.repositories.IPuestoTrabajoRepository;

@Service
public class PuestoTrabajoService implements IPuestoTrabajoService{

	@Autowired 
	IPuestoTrabajoRepository puestoTrabajoRepository;
	
	public Iterable<PuestoTrabajo> findAll() {
		return puestoTrabajoRepository.findAll();
	}
	
}
