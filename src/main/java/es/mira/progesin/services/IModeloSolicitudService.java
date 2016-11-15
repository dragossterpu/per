package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.ModeloSolicitud;

public interface IModeloSolicitudService {

	List<ModeloSolicitud> findAll();

	ModeloSolicitud save(ModeloSolicitud modeloSolicitud);

	void delete(Integer id);

}
