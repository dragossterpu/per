package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.RegActividad;
import es.mira.progesin.persistence.repositories.IRegActividadRepository;

@Service
public class RegActividadService implements IRegActividadService {
	@Autowired
	IRegActividadRepository regActividadRepository;

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		regActividadRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		regActividadRepository.deleteAll();
	}

	@Override
	public boolean exists(Integer id) {
		return regActividadRepository.exists(id);
	}

	@Override
	public Iterable<RegActividad> findAll() {
		return regActividadRepository.findAll();
	}

	public Iterable<RegActividad> findAll(Iterable<Integer> ids) {
		return regActividadRepository.findAll(ids);
	}

	@Override
	public RegActividad findOne(Integer id) {
		return regActividadRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Iterable<RegActividad> save(Iterable<RegActividad> entities) {
		return regActividadRepository.save(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public RegActividad save(RegActividad entity) {
		return regActividadRepository.save(entity);
	}

}
