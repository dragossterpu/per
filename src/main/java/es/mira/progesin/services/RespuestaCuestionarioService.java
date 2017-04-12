package es.mira.progesin.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentoRepository;

@Service
public class RespuestaCuestionarioService implements IRespuestaCuestionarioService {
    
    @Autowired
    private IRespuestaCuestionarioRepository respuestaRepository;
    
    @Autowired
    private IDocumentoService documentoService;
    
    @Autowired
    private ITipoDocumentoRepository tipoDocumentoRepository;
    
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
    @Transactional(readOnly = false)
    public void saveConDocumento(RespuestaCuestionario respuestaCuestionario, UploadedFile archivoSubido,
            List<Documento> listaDocumentos) throws SQLException, IOException {
        TipoDocumento tipo = tipoDocumentoRepository.findByNombre("CUESTIONARIO");
        Documento documentoSubido = documentoService.cargaDocumento(archivoSubido, tipo,
                respuestaCuestionario.getRespuestaId().getCuestionarioEnviado().getInspeccion());
        listaDocumentos.add(documentoSubido);
        respuestaCuestionario.setDocumentos(listaDocumentos);
        respuestaRepository.save(respuestaCuestionario);
        respuestaRepository.flush();
    }
    
}
