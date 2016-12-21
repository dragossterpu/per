package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;

public interface IRespuestaCuestionarioService {

	public RespuestaCuestionario save(RespuestaCuestionario respuesta);

	Iterable<RespuestaCuestionario> save(Iterable<RespuestaCuestionario> entities);
}
