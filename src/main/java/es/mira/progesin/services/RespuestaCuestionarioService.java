package es.mira.progesin.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.Documento;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;

@Service
public class RespuestaCuestionarioService implements IRespuestaCuestionarioService {

	@Autowired
	IRespuestaCuestionarioRepository respuestaRepository;

	@Autowired
	IDocumentoService documentoService;

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

	@Override
	public void saveConDocumento(RespuestaCuestionario respuestaCuestionario, UploadedFile archivoSubido,
			List<Documento> listaDocumentos) throws SQLException, IOException {
		Documento documentoSubido = documentoService.cargaDocumento(archivoSubido);
		listaDocumentos.add(documentoSubido);
		respuestaCuestionario.setDocumentos(listaDocumentos);
		respuestaRepository.flush();
	}

}
