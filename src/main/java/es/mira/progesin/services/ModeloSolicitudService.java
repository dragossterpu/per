package es.mira.progesin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.ModeloSolicitud;
import es.mira.progesin.persistence.repositories.IModeloSolicitudRepository;

@Service
public class ModeloSolicitudService implements IModeloSolicitudService {

	@Autowired
	IModeloSolicitudRepository modeloSolicitudRepository;

	@Override
	public List<ModeloSolicitud> findAll() {
		return (List<ModeloSolicitud>) modeloSolicitudRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public ModeloSolicitud save(ModeloSolicitud modeloSolicitud) {
		return modeloSolicitudRepository.save(modeloSolicitud);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		modeloSolicitudRepository.delete(id);
	}
}
