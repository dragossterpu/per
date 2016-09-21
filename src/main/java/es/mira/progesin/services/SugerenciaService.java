package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.Sugerencia;
import es.mira.progesin.persistence.repositories.IAlertaRepository;
import es.mira.progesin.persistence.repositories.ISugerenciaRepository;


@Service
public class SugerenciaService implements ISugerenciaService {
	@Autowired
	ISugerenciaRepository sugerenciaRepository;

	@Transactional(readOnly = false)
	public void delete(Integer id) {
		sugerenciaRepository.delete(id);
	}


	public  Iterable<Sugerencia> findAll() {
		return sugerenciaRepository.findAll();
	}


	@Transactional(readOnly = false)
	public Sugerencia save(Sugerencia entity) {
		return sugerenciaRepository.save(entity);
	}

}
