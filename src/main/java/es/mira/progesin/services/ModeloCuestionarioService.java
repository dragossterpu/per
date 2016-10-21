package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.ModeloCuestionario;
import es.mira.progesin.persistence.entities.PreEnvioCuestionario;
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
	public List<PreEnvioCuestionario> findAllPre() {
		return preEnvioCuestiRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public void savePre(PreEnvioCuestionario preEnvioCuestionario) {
		preEnvioCuestiRepository.save(preEnvioCuestionario);
	}
}
