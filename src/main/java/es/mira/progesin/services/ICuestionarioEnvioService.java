package es.mira.progesin.services;

import java.io.Serializable;
import java.util.List;

import es.mira.progesin.model.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBusqueda;

public interface ICuestionarioEnvioService extends Serializable {
	CuestionarioEnvio findByInspeccion(Inspeccion inspeccion);

	void enviarCuestionarioService(List<User> user, CuestionarioEnvio cuestionarioEnvio);

	CuestionarioEnvio findByCorreoEnvioAndFechaFinalizacionIsNull(String correo);

	List<CuestionarioEnvio> buscarCuestionarioEnviadoCriteria(CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda);

	void save(CuestionarioEnvio cuestionario);

	void saveAll(CuestionarioEnvio cuestionario, List<RespuestaCuestionario> listaRespuestas,
			List<DatosTablaGenerica> listaDatosTablaSave);

	boolean transaccSaveElimUsuariosProv(CuestionarioEnvio cuestionario);

	boolean transaccSaveInactivaUsuariosProv(CuestionarioEnvio cuestionario,
			List<RespuestaCuestionario> listaRespuestas, List<DatosTablaGenerica> listaDatosTablaSave);

	boolean transaccSaveActivaUsuariosProv(CuestionarioEnvio cuestionario);

}
