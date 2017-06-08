package es.mira.progesin.services;

import java.util.List;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;

/**
 * Implementación del servicio de respuestas de cuestionario.
 * @author EZENTIS
 *
 */
@Service
public class RespuestaCuestionarioService implements IRespuestaCuestionarioService {
    
    /**
     * Repositorio de respuestas de cuestionario.
     */
    @Autowired
    private IRespuestaCuestionarioRepository respuestaRepository;
    
    /**
     * Servicio de documentos.
     */
    @Autowired
    private IDocumentoService documentoService;
    
    /**
     * Guarda la respuesta de un cuestionario de tipo ADJUNTO y la lista de documentos en BBDD.
     * 
     * @param respuestaCuestionario respuesta que se quiere grabar
     * @param archivoSubido fichero que se quiere cargar
     * @param listaDocumentos listado de documentos que ya tenía la respuesta
     * @return respuesta actualizada
     * @throws ProgesinException posible excepción
     */
    
    @Override
    @Transactional(readOnly = false)
    public RespuestaCuestionario saveConDocumento(RespuestaCuestionario respuestaCuestionario,
            UploadedFile archivoSubido, List<Documento> listaDocumentos) throws ProgesinException {
        TipoDocumento tipo = TipoDocumento.builder().id(6L).build();
        Documento documentoSubido = documentoService.cargaDocumento(archivoSubido, tipo,
                respuestaCuestionario.getRespuestaId().getCuestionarioEnviado().getInspeccion());
        listaDocumentos.add(documentoSubido);
        respuestaCuestionario.setDocumentos(listaDocumentos);
        return respuestaRepository.save(respuestaCuestionario);
    }
    
    /**
     * Elimina de BBDD la respuesta y el documento pasados como parámetros.
     * 
     * @param respueta respuesta a eliminar
     * @param documento documento a eliminar
     * @return respuesta actualizada
     */
    @Override
    @Transactional(readOnly = false)
    public RespuestaCuestionario eliminarDocumentoRespuesta(RespuestaCuestionario respueta, Documento documento) {
        RespuestaCuestionario respuestaActualizada = respuestaRepository.save(respueta);
        documentoService.delete(documento);
        return respuestaActualizada;
    }
    
}
