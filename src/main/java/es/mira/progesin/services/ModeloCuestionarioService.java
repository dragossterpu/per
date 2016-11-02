package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.ModeloCuestionario;
import es.mira.progesin.persistence.entities.PreEnvioCuest;
import es.mira.progesin.persistence.repositories.IModeloCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IPreEnvioCuestionarioRepository;

@Service
public class ModeloCuestionarioService implements IModeloCuestionarioService {
	@Autowired
	IModeloCuestionarioRepository modeloCuestionarioRepository;

	@Autowired
	IPreEnvioCuestionarioRepository preEnvioCuestiRepository;

	@Override
	@Transactional(readOnly = false)
	public void save(ModeloCuestionario modeloCuestionario) {
		modeloCuestionarioRepository.save(modeloCuestionario);
	}

	@Override
	public Iterable<ModeloCuestionario> findAll() {
		return modeloCuestionarioRepository.findAll();
	}

	@Override
	public ModeloCuestionario findOne(Integer id) {
		return modeloCuestionarioRepository.findOne(id);
	}

	@Override
	public List<PreEnvioCuest> findAllPre() {
		return preEnvioCuestiRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public void savePre(PreEnvioCuest preEnvioCuest) {
		preEnvioCuestiRepository.save(preEnvioCuest);
	}

	@Override
	@Transactional(readOnly = false)
	public PreEnvioCuest savePreAlta(PreEnvioCuest preEnvioCuest) {
		return preEnvioCuestiRepository.save(preEnvioCuest);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		preEnvioCuestiRepository.delete(id);
	}
}