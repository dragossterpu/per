/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.util.StreamUtils;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.DocumentoBlob;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.persistence.repositories.IDocumentoRepository;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentoRepository;
import es.mira.progesin.util.FacesUtilities;

/**
 * Test para el servicio de documentos.
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ StreamUtils.class, FacesUtilities.class })
public class DocumentoServiceTest {
    
    /**
     * Mock de prueba de la Factoría de sesiones.
     */
    @Mock
    private SessionFactory sessionFactory;
    
    /**
     * Mock de prueba del Servicio de registro de actividad.
     */
    @Mock
    private IRegistroActividadService registroActividadService;
    
    /**
     * Mock de prueba del Repositorio de documentos.
     */
    @Mock
    private IDocumentoRepository documentoRepository;
    
    /**
     * Mock de prueba del Repositorio de inspecciones.
     */
    @Mock
    private IInspeccionesRepository inspeccionRepository;
    
    /**
     * Mock de prueba del Repositorio de tipo de documento.
     */
    @Mock
    private ITipoDocumentoRepository tipoDocumentoRepository;
    
    /**
     * Instancia de prueba del servicio de documentos.
     */
    @InjectMocks
    private IDocumentoService documentosServiceMock = new DocumentoService();
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(SugerenciaService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        SugerenciaService target = new SugerenciaService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.DocumentoService#delete(es.mira.progesin.persistence.entities.gd.Documento)}.
     */
    @Test
    public final void testDeleteDocumento() {
        Documento doc = mock(Documento.class);
        documentosServiceMock.delete(doc);
        verify(documentoRepository, times(1)).delete(doc);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.DocumentoService#findByFechaBajaIsNull()}.
     */
    @Test
    public final void testFindByFechaBajaIsNull() {
        documentosServiceMock.findByFechaBajaIsNull();
        verify(documentoRepository, times(1)).findByFechaBajaIsNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.DocumentoService#findByFechaBajaIsNotNull()}.
     */
    @Test
    public final void testFindByFechaBajaIsNotNull() {
        documentosServiceMock.findByFechaBajaIsNotNull();
        verify(documentoRepository, times(1)).findByFechaBajaIsNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.DocumentoService#save(java.lang.Iterable)}.
     */
    @Test
    public final void testSaveIterableOfDocumento() {
        Documento doc = mock(Documento.class);
        Iterable<Documento> docs = Arrays.asList(doc);
        documentosServiceMock.save(docs);
        verify(documentoRepository, times(1)).save(docs);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.DocumentoService#save(es.mira.progesin.persistence.entities.gd.Documento)}.
     */
    @Test
    public final void testSaveDocumento() {
        Documento doc = mock(Documento.class);
        documentosServiceMock.save(doc);
        verify(documentoRepository, times(1)).save(doc);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.DocumentoService#descargaDocumento(es.mira.progesin.persistence.entities.gd.Documento)}.
     * @throws SQLException lanza excepción
     * @throws ProgesinException lanza excepción
     */
    @Test
    public final void testDescargaDocumentoDocumento() throws SQLException, ProgesinException {
        byte[] fichero = {};
        DocumentoBlob docBloc = spy(DocumentoBlob.class);
        docBloc.setId(1L);
        docBloc.setFichero(fichero);
        docBloc.setNombreFichero("fichero_test");
        Documento doc = spy(Documento.class);
        doc.setId(3L);
        doc.setFichero(new DocumentoBlob());
        doc.setTipoContenido("tipo_test");
        doc.setFichero(docBloc);
        when(documentoRepository.findById(doc.getId())).thenReturn(doc);
        DefaultStreamedContent flujo = documentosServiceMock.descargaDocumento(doc);
        verify(documentoRepository, times(1)).findById(doc.getId());
        assertThat(flujo).isNotNull();
        assertThat(flujo).isInstanceOf(DefaultStreamedContent.class);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.DocumentoService#descargaDocumento(java.lang.Long)}.
     * @throws SQLException lanza excepción
     * @throws ProgesinException lanza excepción
     */
    @Test
    public final void testDescargaDocumentoLong() throws SQLException, ProgesinException {
        byte[] fichero = {};
        DocumentoBlob docBloc = spy(DocumentoBlob.class);
        docBloc.setId(1L);
        docBloc.setFichero(fichero);
        Documento doc = spy(Documento.class);
        doc.setId(3L);
        doc.setFichero(new DocumentoBlob());
        doc.setTipoContenido("tipo_test");
        doc.setFichero(docBloc);
        doc.setNombre("documento_test");
        when(documentoRepository.findById(doc.getId())).thenReturn(doc);
        DefaultStreamedContent flujo = documentosServiceMock.descargaDocumento(3L);
        verify(documentoRepository, times(1)).findById(doc.getId());
        assertThat(flujo).isNotNull();
        assertThat(flujo).isInstanceOf(DefaultStreamedContent.class);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.DocumentoService#cargaDocumento(org.primefaces.model.UploadedFile, es.mira.progesin.persistence.entities.gd.TipoDocumento, es.mira.progesin.persistence.entities.Inspeccion)}.
     * @throws IOException lanza excepción
     * @throws ProgesinException lanza excepción
     */
    @Test
    public final void testCargaDocumento() throws IOException, ProgesinException {
        PowerMockito.mockStatic(StreamUtils.class);
        Inspeccion inspeccion = null;
        TipoDocumento tipo = mock(TipoDocumento.class);
        
        InputStream inputStream = mock(InputStream.class);
        UploadedFile uploadedFile = mock(UploadedFile.class);
        
        byte[] byteArray = { 1, 0, 1, 0 };
        
        when(uploadedFile.getFileName()).thenReturn("src/test/TestUploadFile.txt");
        when(uploadedFile.getContentType()).thenReturn("application-test/test-stream");
        when(uploadedFile.getInputstream()).thenReturn(inputStream);
        when(StreamUtils.copyToByteArray(uploadedFile.getInputstream())).thenReturn(byteArray);
        
        documentosServiceMock.cargaDocumento(uploadedFile, tipo, inspeccion);
        verify(documentoRepository, times(1)).save(any(Documento.class));
        verify(registroActividadService, times(1)).altaRegActividad(any(String.class), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.DocumentoService#cargaDocumentoSinGuardar(org.primefaces.model.UploadedFile, es.mira.progesin.persistence.entities.gd.TipoDocumento, es.mira.progesin.persistence.entities.Inspeccion)}.
     * @throws IOException excepción lanzada
     * @throws ProgesinException excepción lanzada
     */
    @Test
    public final void testCargaDocumentoSinGuardarInspeccionNullSinException() throws IOException, ProgesinException {
        Documento doc = null;
        PowerMockito.mockStatic(StreamUtils.class);
        Inspeccion inspeccion = null;
        TipoDocumento tipo = mock(TipoDocumento.class);
        
        InputStream inputStream = mock(InputStream.class);
        UploadedFile uploadedFile = mock(UploadedFile.class);
        
        byte[] byteArray = { 1, 0, 1, 0 };
        
        when(uploadedFile.getFileName()).thenReturn("src/test/TestUploadFile.txt");
        when(uploadedFile.getContentType()).thenReturn("application-test/test-stream");
        when(uploadedFile.getInputstream()).thenReturn(inputStream);
        when(StreamUtils.copyToByteArray(uploadedFile.getInputstream())).thenReturn(byteArray);
        
        doc = documentosServiceMock.cargaDocumentoSinGuardar(uploadedFile, tipo, inspeccion);
        
        assertThat(doc).isNotNull();
        assertThat(doc).isInstanceOf(Documento.class);
        assertThat(doc).isEqualTo(documentosServiceMock.cargaDocumentoSinGuardar(uploadedFile, tipo, inspeccion));
        
        verify(registroActividadService, never()).altaRegActividadError(eq(SeccionesEnum.GESTOR.name()),
                any(Exception.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.DocumentoService#cargaDocumentoSinGuardar(org.primefaces.model.UploadedFile, es.mira.progesin.persistence.entities.gd.TipoDocumento, es.mira.progesin.persistence.entities.Inspeccion)}.
     * @throws IOException excepción lanzada
     * @throws ProgesinException excepción lanzada
     */
    @Test(expected = ProgesinException.class)
    public final void testCargaDocumentoSinGuardarInspeccionNotNullException() throws IOException, ProgesinException {
        Documento doc = null;
        PowerMockito.mockStatic(StreamUtils.class);
        Inspeccion inspeccion = mock(Inspeccion.class);
        TipoDocumento tipo = mock(TipoDocumento.class);
        
        InputStream inputStream = mock(InputStream.class);
        UploadedFile uploadedFile = mock(UploadedFile.class);
        
        when(uploadedFile.getFileName()).thenReturn("src/test/TestUploadFile.txt");
        when(uploadedFile.getContentType()).thenReturn("application-test/test-stream");
        when(uploadedFile.getInputstream()).thenReturn(inputStream);
        when(StreamUtils.copyToByteArray(uploadedFile.getInputstream())).thenThrow(IOException.class);
        
        doc = documentosServiceMock.cargaDocumentoSinGuardar(uploadedFile, tipo, inspeccion);
        assertThat(doc).isNull();
        verify(registroActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.GESTOR.name()),
                any(Exception.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.DocumentoService#getCounCriteria(es.mira.progesin.web.beans.DocumentoBusqueda)}.
     */
    @Test
    public final void testGetCounCriteria() {
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.DocumentoService#buscarDocumentoPorCriteria(int, int, java.lang.String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.DocumentoBusqueda)}.
     */
    @Test
    public final void testBuscarDocumentoPorCriteria() {
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.DocumentoService#obtieneNombreFichero(es.mira.progesin.persistence.entities.gd.Documento)}.
     */
    @Test
    public final void testObtieneNombreFichero() {
        DocumentoBlob docBlob = new DocumentoBlob();
        docBlob.setNombreFichero("ficheroTest");
        Documento doc = new Documento();
        doc.setId(2L);
        doc.setFichero(docBlob);
        when(documentoRepository.findById(doc.getId())).thenReturn(doc);
        String nombreFichero = documentosServiceMock.obtieneNombreFichero(doc);
        verify(documentoRepository, times(1)).findById(doc.getId());
        assertThat(nombreFichero).isEqualTo(doc.getFichero().getNombreFichero());
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.DocumentoService#listaTiposDocumento()}.
     */
    @Test
    public final void testListaTiposDocumento() {
        tipoDocumentoRepository.findAll();
        verify(tipoDocumentoRepository, times(1)).findAll();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.DocumentoService#recuperarDocumento(es.mira.progesin.persistence.entities.gd.Documento)}.
     */
    @Test
    public final void testRecuperarDocumento() {
        Documento docTest = new Documento();
        docTest.setFechaBaja(null);
        docTest.setUsernameBaja(null);
        when(documentoRepository.save(docTest)).thenReturn(docTest);
        Documento doc = documentoRepository.save(docTest);
        verify(documentoRepository, times(1)).save(doc);
        assertThat(doc.getFechaBaja()).isNull();
        assertThat(doc.getUsernameBaja()).isNull();
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.DocumentoService#listaInspecciones(es.mira.progesin.persistence.entities.gd.Documento)}.
     */
    @Test
    public final void testListaInspecciones() {
        Documento doc = new Documento();
        doc.setId(3L);
        inspeccionRepository.cargaInspeccionesDocumento(doc.getId());
        verify(inspeccionRepository, times(1)).cargaInspeccionesDocumento(doc.getId());
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.DocumentoService#perteneceACuestionario(es.mira.progesin.persistence.entities.gd.Documento)}.
     */
    @Test
    public final void testPerteneceACuestionario() {
        Documento doc = new Documento();
        doc.setId(3L);
        documentoRepository.perteneceACuestionario(doc.getId());
        verify(documentoRepository, times(1)).perteneceACuestionario(doc.getId());
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.DocumentoService#perteneceASolicitud(es.mira.progesin.persistence.entities.gd.Documento)}.
     */
    @Test
    public final void testPerteneceASolicitud() {
        Documento doc = new Documento();
        doc.setId(3L);
        documentoRepository.perteneceASolicitud(doc.getId());
        verify(documentoRepository, times(1)).perteneceASolicitud(doc.getId());
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.DocumentoService#vaciarPapelera()}.
     */
    @Test
    public final void testVaciarPapelera() {
        documentoRepository.deleteByFechaBajaIsNotNull();
        verify(documentoRepository, times(1)).deleteByFechaBajaIsNotNull();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.DocumentoService#buscaTipoDocumentoPorNombre(java.lang.String)}.
     */
    @Test
    public final void testBuscaTipoDocumentoPorNombre() {
        tipoDocumentoRepository.findByNombre("nombre_test");
        verify(tipoDocumentoRepository, times(1)).findByNombre("nombre_test");
    }
    
}
