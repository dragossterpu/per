package es.mira.progesin.services;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;

public interface ICuestionarioEnvioService {
	CuestionarioEnvio findByInspeccion(Inspeccion inspeccion);

	public boolean enviarCuestionarioService();
}
