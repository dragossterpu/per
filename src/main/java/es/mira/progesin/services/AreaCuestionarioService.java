package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.repositories.IAreaCuestionarioRepository;

@Service
public class AreaCuestionarioService implements IAreaCuestionarioService {

	@Autowired
	IAreaCuestionarioRepository areaRepository;

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		areaRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Iterable<AreasCuestionario> entities) {
		areaRepository.delete(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(AreasCuestionario entity) {
		areaRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		areaRepository.deleteAll();
	}

	@Override
	public boolean exists(Long id) {
		return areaRepository.exists(id);
	}

	@Override
	public Iterable<AreasCuestionario> findAll() {
		return areaRepository.findAll();
	}

	@Override
	public Iterable<AreasCuestionario> findAll(Iterable<Long> ids) {
		return areaRepository.findAll(ids);
	}

	@Override
	public AreasCuestionario findOne(Long id) {
		return areaRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Iterable<AreasCuestionario> save(Iterable<AreasCuestionario> entities) {
		return areaRepository.save(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public AreasCuestionario save(AreasCuestionario entity) {
		return areaRepository.save(entity);
	}

	@Override
	public List<AreasCuestionario> findDistinctByIdCuestionario(Integer idCuestionario) {
		return areaRepository.findDistinctByIdCuestionario(idCuestionario);
	}

}
