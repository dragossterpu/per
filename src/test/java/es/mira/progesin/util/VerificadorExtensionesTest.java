/**
 * 
 */
package es.mira.progesin.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import es.mira.progesin.services.IRegistroActividadService;

/**
 * Test para el verificador de extensiones.
 * 
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
public class VerificadorExtensionesTest {
    
    /**
     * Mock para el registro de actividad.
     */
    @Mock
    private IRegistroActividadService registroActividadService;
    
    /**
     * Objeto verificador.
     */
    @InjectMocks
    private VerificadorExtensiones verificadorMock;
    
    /**
     * Test method for
     * {@link es.mira.progesin.util.VerificadorExtensiones#extensionCorrecta(org.primefaces.model.UploadedFile)}.
     */
    @Test
    public final void testExtensionCorrectaVerificada() {
        FileUploadEvent event = mock(FileUploadEvent.class);
        UploadedFile uploadedFile = mock(UploadedFile.class);
        
        when(event.getFile()).thenReturn(uploadedFile);
        when(uploadedFile.getFileName()).thenReturn("fileName_test.avi");
        boolean extensionCorrecta = verificadorMock.extensionCorrecta(uploadedFile);
        assertThat(extensionCorrecta).isTrue();
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.util.VerificadorExtensiones#extensionCorrecta(org.primefaces.model.UploadedFile)}.
     * @throws IOException Excepciones producidas
     */
    @Test
    public final void testExtensionCorrectaNoVerificada() throws IOException {
        FileUploadEvent event = mock(FileUploadEvent.class);
        InputStream stream = mock(InputStream.class);
        
        UploadedFile uploadedFile = mock(UploadedFile.class);
        
        when(event.getFile()).thenReturn(uploadedFile);
        when(uploadedFile.getFileName()).thenReturn("fileName_test.pdf");
        when(uploadedFile.getInputstream()).thenReturn(stream);
        when(uploadedFile.getContentType()).thenReturn("application/octet-stream");
        
        boolean extensionCorrecta = verificadorMock.extensionCorrecta(uploadedFile);
        assertThat(extensionCorrecta).isTrue();
        
    }
    
}
