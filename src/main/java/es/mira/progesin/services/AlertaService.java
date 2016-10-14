package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.repositories.IAlertaRepository;

@Service
public class AlertaService implements IAlertaService {
	@Autowired
	IAlertaRepository alertaRepository;

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		alertaRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		alertaRepository.deleteAll();
	}

	@Override
	public boolean exists(Integer id) {
		return alertaRepository.exists(id);
	}

	@Override
	public List<Alerta> findByFechaBajaIsNull() {
		return alertaRepository.findByFechaBajaIsNull();
	}

	public Iterable<Alerta> findAll(Iterable<Integer> ids) {
		return alertaRepository.findAll(ids);
	}

	@Override
	public Alerta findOne(Integer id) {
		return alertaRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Alerta save(Alerta entity) {
		return alertaRepository.save(entity);
	}

}
