package es.mira.progesin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;

@Service
public class RespuestaCuestionarioService implements IRespuestaCuestionarioService {

	@Autowired
	IRespuestaCuestionarioRepository respuestaRepository;

	@Override
	@Transactional(readOnly = false)
	public RespuestaCuestionario save(RespuestaCuestionario respuesta) {
		return respuestaRepository.save(respuesta);
	}

	@Override
	@Transactional(readOnly = false)
	public Iterable<RespuestaCuestionario> save(Iterable<RespuestaCuestionario> entities) {
		return respuestaRepository.save(entities);
	}

}
