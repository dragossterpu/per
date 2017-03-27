package es.mira.progesin.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.primefaces.model.UploadedFile;

import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.gd.Documento;

public interface IRespuestaCuestionarioService {

	public RespuestaCuestionario save(RespuestaCuestionario respuesta);

	Iterable<RespuestaCuestionario> save(Iterable<RespuestaCuestionario> entities);

	public void saveConDocumento(RespuestaCuestionario respuestaCuestionario, UploadedFile file,
			List<Documento> listaDocumentos) throws SQLException, IOException;
}
