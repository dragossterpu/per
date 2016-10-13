package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.ModeloGuia;
import es.mira.progesin.persistence.repositories.IModeloGuiaRepository;

@Service
public class ModeloGuiaService implements IModeloGuiaService {
	@Autowired
	IModeloGuiaRepository modeloGuiaRepository;

	@Override
	@Transactional(readOnly = false)
	public void save(ModeloGuia modeloGuia) {
		modeloGuiaRepository.save(modeloGuia);
	}

	@Override
	public Iterable<ModeloGuia> findAll() {
		return modeloGuiaRepository.findAll();
	}

}
