package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.PuestoTrabajo;

public interface IPuestoTrabajoService {
	Iterable<PuestoTrabajo> findAll();
}
