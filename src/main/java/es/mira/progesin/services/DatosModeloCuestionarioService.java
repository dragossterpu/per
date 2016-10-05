package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.DatosModeloCuestionario;
import es.mira.progesin.persistence.entities.ModeloCuestionario;
import es.mira.progesin.persistence.repositories.IDatosModeloCuestionarioRepository;

@Service
public class DatosModeloCuestionarioService implements IDatosModeloCuestionarioService {

	@Autowired
	IDatosModeloCuestionarioRepository datosModeloCuestionarioRepository;

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		datosModeloCuestionarioRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Iterable<DatosModeloCuestionario> entities) {
		datosModeloCuestionarioRepository.delete(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(DatosModeloCuestionario entity) {
		datosModeloCuestionarioRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		datosModeloCuestionarioRepository.deleteAll();
	}

	@Override
	public boolean exists(Long id) {
		return datosModeloCuestionarioRepository.exists(id);
	}

	@Override
	public Iterable<DatosModeloCuestionario> findAll() {
		return datosModeloCuestionarioRepository.findAll();
	}

	@Override
	public Iterable<DatosModeloCuestionario> findAll(Iterable<Long> ids) {
		return datosModeloCuestionarioRepository.findAll(ids);
	}

	@Override
	public DatosModeloCuestionario findOne(Long id) {
		return datosModeloCuestionarioRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Iterable<DatosModeloCuestionario> save(Iterable<DatosModeloCuestionario> entities) {
		return datosModeloCuestionarioRepository.save(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public DatosModeloCuestionario save(DatosModeloCuestionario entity) {
		return datosModeloCuestionarioRepository.save(entity);
	}

	@Override
	public List<DatosModeloCuestionario> findByModeloCuestionario(ModeloCuestionario modeloCuestionario) {
		return datosModeloCuestionarioRepository.findByModeloCuestionario(modeloCuestionario);
	}

}
