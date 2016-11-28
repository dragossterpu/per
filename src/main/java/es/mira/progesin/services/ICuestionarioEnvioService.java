package es.mira.progesin.services;

import java.io.Serializable;

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;

public interface ICuestionarioEnvioService extends Serializable {
	CuestionarioEnvio findByInspeccion(Inspeccion inspeccion);

	void enviarCuestionarioService(User user, CuestionarioEnvio cuestionarioEnvio);

	CuestionarioEnvio findByCorreoEnvioAndFechaFinalizacionIsNull(String correo);
}
