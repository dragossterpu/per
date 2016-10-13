package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.ModeloGuia;

public interface IModeloGuiaService {

	public void save(ModeloGuia modeloGuia);

	public Iterable<ModeloGuia> findAll();
}
