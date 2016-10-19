package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.CuestionarioPersonalizado;
import es.mira.progesin.persistence.repositories.ICuestionarioPersonalizadoRepository;

@Service
public class CuestionarioPersonalizadoService implements ICuestionarioPersonalizadoService {

	@Autowired
	ICuestionarioPersonalizadoRepository cuestionarioPersRep;

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		cuestionarioPersRep.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Iterable<CuestionarioPersonalizado> entities) {
		cuestionarioPersRep.delete(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(CuestionarioPersonalizado entity) {
		cuestionarioPersRep.delete(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAll() {
		cuestionarioPersRep.deleteAll();
	}

	@Override
	public boolean exists(Long id) {
		return cuestionarioPersRep.exists(id);
	}

	@Override
	public Iterable<CuestionarioPersonalizado> findAll() {
		return cuestionarioPersRep.findAll();
	}

	@Override
	public Iterable<CuestionarioPersonalizado> findAll(Iterable<Long> ids) {
		return cuestionarioPersRep.findAll(ids);
	}

	@Override
	public CuestionarioPersonalizado findOne(Long id) {
		return cuestionarioPersRep.findOne(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Iterable<CuestionarioPersonalizado> save(Iterable<CuestionarioPersonalizado> entities) {
		return cuestionarioPersRep.save(entities);
	}

	@Override
	@Transactional(readOnly = false)
	public CuestionarioPersonalizado save(CuestionarioPersonalizado entity) {
		return cuestionarioPersRep.save(entity);
	}

}
