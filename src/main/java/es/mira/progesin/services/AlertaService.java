package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.repositories.IAlertaRepository;
import es.mira.progesin.persistence.repositories.IUserRepository;
import es.mira.progesin.services.IAlertaService;

/**
 * Implementation for Alertas Manager
 * @author STAD -EZENTIS
 * 
 */


@Service
public class AlertaService implements IAlertaService {
	@Autowired
	IAlertaRepository alertaRepository;

	@Transactional(readOnly = false)
	public void delete(String id) {
		alertaRepository.delete(id);
	}


	@Transactional(readOnly = false)
	public void deleteAll() {
		alertaRepository.deleteAll();
	}

	public boolean exists(String id) {
		return alertaRepository.exists(id);
	}

	public  Iterable<Alerta> findAll() {
		return alertaRepository.findAll();
	}

	public Iterable<Alerta> findAll(Iterable<String> ids) {
		return alertaRepository.findAll(ids);
	}

	public Alerta findOne(String id) {
		return alertaRepository.findOne(id);
	}

	@Transactional(readOnly = false)
	public Iterable<Alerta> save(Iterable<Alerta> entities) {
		return alertaRepository.save(entities);
	}

	@Transactional(readOnly = false)
	public Alerta save(Alerta entity) {
		return alertaRepository.save(entity);
	}

}
