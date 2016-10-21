package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.Inspecciones;


public interface IInspeccionesService {

	public Inspecciones save(Inspecciones inspecciones);

	public Iterable<Inspecciones> findAll();
	
	public void delete(Inspecciones inspecciones);
}
