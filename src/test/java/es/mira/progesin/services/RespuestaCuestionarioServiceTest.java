/**
 * 
 */
package es.mira.progesin.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.primefaces.model.UploadedFile;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionarioId;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;

/**
 * Test para el servicio de RespuestaCuestionarioService.
 * @author EZENTIS
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RespuestaCuestionarioServiceTest {
    /**
     * Mock Repositorio de respuestas de cuestionario.
     */
    @Mock
    private IRespuestaCuestionarioRepository respuestaRepository;
    
    /**
     * Mock Servicio de documentos.
     */
    @Mock
    private IDocumentoService documentoService;
    
    /**
     * Instancia de prueba del servicio de RespuestaCuestionario.
     */
    @InjectMocks
    private IRespuestaCuestionarioService respuestaCuestionarioService = new RespuestaCuestionarioService();
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.RespuestaCuestionarioService#saveConDocumento(es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario, org.primefaces.model.UploadedFile)}.
     * @throws ProgesinException lanzada
     */
    @Test
    public final void testSaveConDocumento() throws ProgesinException {
        Inspeccion inspeccion = mock(Inspeccion.class);
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setInspeccion(inspeccion);
        RespuestaCuestionarioId respuestaId = new RespuestaCuestionarioId();
        respuestaId.setCuestionarioEnviado(cuestionarioEnviado);
        RespuestaCuestionario respuesta = new RespuestaCuestionario();
        respuesta.setRespuestaId(respuestaId);
        respuesta.setDocumentos(new ArrayList<>());
        UploadedFile archivoSubido = mock(UploadedFile.class);
        respuestaCuestionarioService.saveConDocumento(respuesta, archivoSubido);
        verify(respuestaRepository, times(1)).save(respuesta);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.RespuestaCuestionarioService#eliminarDocumentoRespuesta(es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario, es.mira.progesin.persistence.entities.gd.Documento)}.
     */
    @Test
    public final void testEliminarDocumentoRespuesta() {
        RespuestaCuestionario respueta = mock(RespuestaCuestionario.class);
        Documento documento = mock(Documento.class);
        respuestaCuestionarioService.eliminarDocumentoRespuesta(respueta, documento);
        verify(respuestaRepository, times(1)).save(respueta);
        verify(documentoService, times(1)).delete(documento);
    }
    
}
