package es.mira.progesin.services;

import javax.annotation.ManagedBean;
import javax.faces.bean.ApplicationScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.mira.progesin.persistence.entities.Sugerencia;
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
		return sugerenciaRepository.findByFechaBajaIsNull();
	}

	public  Sugerencia findOne(Integer id) {
		return sugerenciaRepository.findOne(id);
	}
	@Transactional(readOnly = false)
	public Sugerencia save(Sugerencia entity) {
		return sugerenciaRepository.save(entity);
	}

}
