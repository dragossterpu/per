package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.repositories.ICuerpoEstadoRepository;

@Service
public class CuerpoEstadoService implements ICuerpoEstadoService{

	@Autowired 
	ICuerpoEstadoRepository cuerpoEstadoRepository;
	
	public Iterable<CuerpoEstado> findAll() {
		return cuerpoEstadoRepository.findAll();
	}

}
