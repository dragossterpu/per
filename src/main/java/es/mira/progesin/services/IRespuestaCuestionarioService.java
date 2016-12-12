package es.mira.progesin.services;

import java.util.List;

import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;

public interface IRespuestaCuestionarioService {

	public void guardarRespuestas(List<RespuestaCuestionario> listaRespuestas);

}
