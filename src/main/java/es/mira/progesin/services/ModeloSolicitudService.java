package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Documento;
import es.mira.progesin.persistence.entities.ModeloSolicitud;
import es.mira.progesin.persistence.repositories.IModeloSolicitudRepository;

@Service
public class ModeloSolicitudService implements IModeloSolicitudService {

	@Autowired
	IModeloSolicitudRepository modeloSolicitudRepository;

	@Autowired
	IDocumentoService documentoService;

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
	public ModeloSolicitud transaccSaveGuardaDoc(ModeloSolicitud modeloSolicitud, UploadedFile ficheroNuevo) {
		Documento documento = documentoService.cargaDocumento(ficheroNuevo);
		modeloSolicitud.setIdDocumento(documento.getId());
		return modeloSolicitudRepository.save(modeloSolicitud);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		modeloSolicitudRepository.delete(id);
	}
}
