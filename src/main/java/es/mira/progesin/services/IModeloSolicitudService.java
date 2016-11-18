package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.UploadedFile;

import es.mira.progesin.persistence.entities.ModeloSolicitud;

public interface IModeloSolicitudService {

	List<ModeloSolicitud> findAll();

	ModeloSolicitud save(ModeloSolicitud modeloSolicitud);

	void delete(Integer id);

	ModeloSolicitud transaccSaveGuardaDoc(ModeloSolicitud modeloSolicitud, UploadedFile ficheroNuevo);

}
