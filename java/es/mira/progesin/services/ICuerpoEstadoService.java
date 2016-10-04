package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.CuerpoEstado;

public interface ICuerpoEstadoService {
	Iterable<CuerpoEstado> findAll();
}
