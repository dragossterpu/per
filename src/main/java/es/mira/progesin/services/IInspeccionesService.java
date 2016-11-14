package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.Inspeccion;


public interface IInspeccionesService {

	public Inspeccion save(Inspeccion inspecciones);

	public Iterable<Inspeccion> findAll();
	
	public void delete(Inspeccion inspecciones);
}
