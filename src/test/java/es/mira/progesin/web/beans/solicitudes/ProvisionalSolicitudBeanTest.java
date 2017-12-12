/**
 * 
 */
package es.mira.progesin.web.beans.solicitudes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.DocumentacionPrevia;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.gd.ITipoDocumentacionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.PdfGeneratorSolicitudes;
import es.mira.progesin.util.VerificadorExtensiones;
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * Test para ProvisionalSolicitudBean.
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class, RequestContext.class })
public class ProvisionalSolicitudBeanTest {
    
    /**
     * Simulación del securityContext.
     */
    @Mock
    private SecurityContext securityContext;
    
    /**
     * Simulación de la autenticación.
     */
    @Mock
    private Authentication authentication;
    
    /**
     * Mock Bean de datos comunes de la aplicación.
     */
    @Mock
    private ApplicationBean applicationBean;
    
    /**
     * Mock Servicio del registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Mock Servicio de solicitudes de documentación.
     */
    @Mock
    private ISolicitudDocumentacionService solicitudDocumentacionService;
    
    /**
     * Mock Servicio de alertas.
     */
    @Mock
    private IAlertaService alertaService;
    
    /**
     * Mock Servicio de tipos de documentación.
     */
    @Mock
    private ITipoDocumentacionService tipoDocumentacionService;
    
    /**
     * Mock Servicio de documentos.
     */
    @Mock
    private IDocumentoService documentoService;
    
    /**
     * Mock Verificador de extensiones.
     */
    @Mock
    private VerificadorExtensiones verificadorExtensiones;
    
    /**
     * Mock Generador de archivos PDF con la información de la solicitud cumplimentada.
     */
    @Mock
    private PdfGeneratorSolicitudes pdfGenerator;
    
    /**
     * Constante nombre de archivo.
     */
    private static final String NOMBREFILE = "fileName";
    
    /**
     * Constante nombre pdf.
     */
    private static final String NOMBREPDF = "pdf";
    
    /**
     * Constante extensión pdf.
     */
    private static final String EXTENSIONPDF = ".pdf";
    
    /**
     * Simulación del Gestor Documental.
     */
    @InjectMocks
    private ProvisionalSolicitudBean provisionalSolicitudBean;
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuarioLogueado");
    }
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(ProvisionalSolicitudBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        ProvisionalSolicitudBean target = new ProvisionalSolicitudBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#visualizarSolicitud()}.
     */
    @Test
    public final void testVisualizarSolicitud() {
        ArgumentCaptor<String> correoCaptor = ArgumentCaptor.forClass(String.class);
        SolicitudDocumentacionPrevia solicitud = new SolicitudDocumentacionPrevia();
        solicitud.setId(1L);
        when(solicitudDocumentacionService.findEnviadaNoFinalizadaPorCorreoDestinatario(correoCaptor.capture()))
                .thenReturn(solicitud);
        when(tipoDocumentacionService.findByIdSolicitud(1L)).thenReturn(new ArrayList<>());
        
        String redireccion = provisionalSolicitudBean.visualizarSolicitud();
        
        verify(solicitudDocumentacionService, times(1))
                .findEnviadaNoFinalizadaPorCorreoDestinatario(correoCaptor.capture());
        verify(tipoDocumentacionService, times(1)).findByIdSolicitud(1L);
        assertThat(redireccion).isEqualTo("/provisionalSolicitud/provisionalSolicitud?faces-redirect=true");
        assertThat(provisionalSolicitudBean.getSolicitudDocumentacionPrevia()).isEqualTo(solicitud);
        assertThat(provisionalSolicitudBean.getListadoDocumentosPrevios()).isNotNull();
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#gestionarCargaDocumento(org.primefaces.event.FileUploadEvent)}.
     * @throws ProgesinException lanzada
     */
    @Test
    public final void testGestionarCargaDocumento() throws ProgesinException {
        ArgumentCaptor<TipoDocumento> tipoDoc = ArgumentCaptor.forClass(TipoDocumento.class);
        FileUploadEvent event = mock(FileUploadEvent.class);
        UploadedFile archivo = mock(UploadedFile.class);
        when(event.getFile()).thenReturn(archivo);
        when(archivo.getFileName()).thenReturn(NOMBREFILE);
        when(archivo.getContentType()).thenReturn(NOMBREPDF);
        when(verificadorExtensiones.extensionCorrecta(archivo)).thenReturn(true);
        
        Map<String, String> extensiones = new HashMap<>();
        extensiones.put(NOMBREPDF, EXTENSIONPDF);
        provisionalSolicitudBean.setExtensiones(extensiones);
        
        List<DocumentacionPrevia> listDocumentacionPrevia = new ArrayList<>();
        DocumentacionPrevia documentacionPrevia = new DocumentacionPrevia();
        documentacionPrevia.setNombre(NOMBREFILE);
        List<String> listaExtensiones = new ArrayList<>();
        listaExtensiones.add(EXTENSIONPDF);
        documentacionPrevia.setExtensiones(listaExtensiones);
        listDocumentacionPrevia.add(documentacionPrevia);
        
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
        solicitudDocumentacionPrevia.setInspeccion(mock(Inspeccion.class));
        solicitudDocumentacionPrevia.setDocumentos(new ArrayList<>());
        provisionalSolicitudBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        
        when(documentoService.cargaDocumento(eq(archivo), tipoDoc.capture(),
                eq(solicitudDocumentacionPrevia.getInspeccion()))).thenReturn(mock(Documento.class));
        
        when(solicitudDocumentacionService.save(provisionalSolicitudBean.getSolicitudDocumentacionPrevia()))
                .thenReturn(provisionalSolicitudBean.getSolicitudDocumentacionPrevia());
        
        provisionalSolicitudBean.setListadoDocumentosPrevios(listDocumentacionPrevia);
        
        provisionalSolicitudBean.gestionarCargaDocumento(event);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_INFO), any(String.class), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#gestionarCargaDocumento(org.primefaces.event.FileUploadEvent)}.
     */
    @Test
    public final void testGestionarCargaDocumentoExtensionIncorrecta() {
        FileUploadEvent event = mock(FileUploadEvent.class);
        UploadedFile archivo = mock(UploadedFile.class);
        when(event.getFile()).thenReturn(archivo);
        when(archivo.getFileName()).thenReturn(NOMBREFILE);
        when(archivo.getContentType()).thenReturn(NOMBREPDF);
        when(verificadorExtensiones.extensionCorrecta(archivo)).thenReturn(false);
        
        provisionalSolicitudBean.gestionarCargaDocumento(event);
        
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#gestionarCargaDocumento(org.primefaces.event.FileUploadEvent)}.
     */
    @Test
    public final void testGestionarCargaDocumentoNoEsDocumentacion() {
        FileUploadEvent event = mock(FileUploadEvent.class);
        UploadedFile archivo = mock(UploadedFile.class);
        when(event.getFile()).thenReturn(archivo);
        when(archivo.getFileName()).thenReturn("otroNombre");
        when(archivo.getContentType()).thenReturn(NOMBREPDF);
        when(verificadorExtensiones.extensionCorrecta(archivo)).thenReturn(true);
        
        Map<String, String> extensiones = new HashMap<>();
        extensiones.put(NOMBREPDF, EXTENSIONPDF);
        provisionalSolicitudBean.setExtensiones(extensiones);
        
        List<DocumentacionPrevia> listDocumentacionPrevia = new ArrayList<>();
        DocumentacionPrevia documentacionPrevia = new DocumentacionPrevia();
        documentacionPrevia.setNombre(NOMBREFILE);
        List<String> listaExtensiones = new ArrayList<>();
        listaExtensiones.add(EXTENSIONPDF);
        documentacionPrevia.setExtensiones(listaExtensiones);
        listDocumentacionPrevia.add(documentacionPrevia);
        
        provisionalSolicitudBean.setListadoDocumentosPrevios(listDocumentacionPrevia);
        
        provisionalSolicitudBean.gestionarCargaDocumento(event);
        
        verify(solicitudDocumentacionService, times(0)).save(any(SolicitudDocumentacionPrevia.class));
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#gestionarCargaDocumento(org.primefaces.event.FileUploadEvent)}.
     * @throws ProgesinException lanzada
     */
    @Test
    public final void testGestionarCargaDocumentoException() throws ProgesinException {
        ArgumentCaptor<TipoDocumento> tipoDoc = ArgumentCaptor.forClass(TipoDocumento.class);
        FileUploadEvent event = mock(FileUploadEvent.class);
        UploadedFile archivo = mock(UploadedFile.class);
        when(event.getFile()).thenReturn(archivo);
        when(archivo.getFileName()).thenReturn(NOMBREFILE);
        when(archivo.getContentType()).thenReturn(NOMBREPDF);
        when(verificadorExtensiones.extensionCorrecta(archivo)).thenReturn(true);
        
        Map<String, String> extensiones = new HashMap<>();
        extensiones.put(NOMBREPDF, EXTENSIONPDF);
        provisionalSolicitudBean.setExtensiones(extensiones);
        
        List<DocumentacionPrevia> listDocumentacionPrevia = new ArrayList<>();
        DocumentacionPrevia documentacionPrevia = new DocumentacionPrevia();
        documentacionPrevia.setNombre(NOMBREFILE);
        List<String> listaExtensiones = new ArrayList<>();
        listaExtensiones.add(EXTENSIONPDF);
        documentacionPrevia.setExtensiones(listaExtensiones);
        listDocumentacionPrevia.add(documentacionPrevia);
        
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
        solicitudDocumentacionPrevia.setInspeccion(mock(Inspeccion.class));
        solicitudDocumentacionPrevia.setDocumentos(new ArrayList<>());
        provisionalSolicitudBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        
        when(documentoService.cargaDocumento(eq(archivo), tipoDoc.capture(),
                eq(solicitudDocumentacionPrevia.getInspeccion())))
                        .thenThrow(TransientDataAccessResourceException.class);
        
        provisionalSolicitudBean.setListadoDocumentosPrevios(listDocumentacionPrevia);
        
        provisionalSolicitudBean.gestionarCargaDocumento(event);
        
        verify(solicitudDocumentacionService, times(0)).save(any(SolicitudDocumentacionPrevia.class));
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#init()}.
     */
    @Test
    public final void testInit() {
        Map<String, Map<String, String>> mapa = new HashMap<>();
        Map<String, String> mapaExtensiones = new HashMap<>();
        mapaExtensiones.put("url", "url");
        mapa.put("extensiones", mapaExtensiones);
        
        when(applicationBean.getMapaParametros()).thenReturn(mapa);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#eliminarDocumento(es.mira.progesin.persistence.entities.gd.Documento)}.
     */
    @Test
    public final void testEliminarDocumento() {
        Documento doc = mock(Documento.class);
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = mock(SolicitudDocumentacionPrevia.class);
        provisionalSolicitudBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        provisionalSolicitudBean.eliminarDocumento(doc);
        verify(solicitudDocumentacionService, times(1)).eliminarDocumentoSolicitud(solicitudDocumentacionPrevia, doc);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#eliminarDocumento(es.mira.progesin.persistence.entities.gd.Documento)}.
     */
    @Test
    public final void testEliminarDocumentoException() {
        Documento doc = mock(Documento.class);
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = mock(SolicitudDocumentacionPrevia.class);
        provisionalSolicitudBean.setSolicitudDocumentacionPrevia(solicitudDocumentacionPrevia);
        when(solicitudDocumentacionService.eliminarDocumentoSolicitud(solicitudDocumentacionPrevia, doc))
                .thenThrow(TransientDataAccessResourceException.class);
        provisionalSolicitudBean.eliminarDocumento(doc);
        verify(solicitudDocumentacionService, times(1)).eliminarDocumentoSolicitud(solicitudDocumentacionPrevia, doc);
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#descargarFichero(java.lang.Long)}.
     * @throws ProgesinException lanzada
     */
    @Test
    public final void testDescargarFichero() throws ProgesinException {
        DefaultStreamedContent fich = mock(DefaultStreamedContent.class);
        when(documentoService.descargaDocumento(1L)).thenReturn(fich);
        provisionalSolicitudBean.descargarFichero(1L);
        assertThat(provisionalSolicitudBean.getFile()).isEqualTo(fich);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#descargarFichero(java.lang.Long)}.
     * @throws ProgesinException lanzada
     */
    @Test
    public final void testDescargarFicheroException() throws ProgesinException {
        when(documentoService.descargaDocumento(1L)).thenThrow(ProgesinException.class);
        provisionalSolicitudBean.descargarFichero(1L);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                eq(null));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(ProgesinException.class));
        assertThat(provisionalSolicitudBean.getFile()).isNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#enviarDocumentacionPrevia()}.
     */
    @Test
    public final void testEnviarDocumentacionPrevia() {
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        SolicitudDocumentacionPrevia solicitudDocPrevia = new SolicitudDocumentacionPrevia();
        solicitudDocPrevia.setInspeccion(inspeccion);
        solicitudDocPrevia.setCorreoDestinatario("correoTest");
        provisionalSolicitudBean.setSolicitudDocumentacionPrevia(solicitudDocPrevia);
        
        provisionalSolicitudBean.enviarDocumentacionPrevia();
        
        verify(solicitudDocumentacionService, times(1)).transaccSaveInactivaUsuarioProv(eq(solicitudDocPrevia),
                eq(solicitudDocPrevia.getCorreoDestinatario()));
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
        
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.DOCUMENTACION.getDescripcion()));
        verify(alertaService, times(1)).crearAlertaJefeEquipo(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(String.class), eq(solicitudDocPrevia.getInspeccion()));
        verify(alertaService, times(1)).crearAlertaRol(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(String.class), eq(RoleEnum.ROLE_SERVICIO_APOYO));
        assertThat(provisionalSolicitudBean.getSolicitudDocumentacionPrevia().getFechaCumplimentacion()).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#enviarDocumentacionPrevia()}.
     */
    @Test
    public final void testEnviarDocumentacionPreviaException() {
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        SolicitudDocumentacionPrevia solicitudDocPrevia = new SolicitudDocumentacionPrevia();
        solicitudDocPrevia.setInspeccion(inspeccion);
        solicitudDocPrevia.setCorreoDestinatario("correoTest");
        provisionalSolicitudBean.setSolicitudDocumentacionPrevia(solicitudDocPrevia);
        doThrow(TransientDataAccessResourceException.class).when(solicitudDocumentacionService)
                .transaccSaveInactivaUsuarioProv(solicitudDocPrevia, solicitudDocPrevia.getCorreoDestinatario());
        provisionalSolicitudBean.enviarDocumentacionPrevia();
        
        verify(solicitudDocumentacionService, times(1)).transaccSaveInactivaUsuarioProv(eq(solicitudDocPrevia),
                eq(solicitudDocPrevia.getCorreoDestinatario()));
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#guardarBorrardor()}.
     */
    @Test
    public final void testGuardarBorrardor() {
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        SolicitudDocumentacionPrevia solicitudDocPrevia = new SolicitudDocumentacionPrevia();
        solicitudDocPrevia.setInspeccion(inspeccion);
        provisionalSolicitudBean.setSolicitudDocumentacionPrevia(solicitudDocPrevia);
        provisionalSolicitudBean.guardarBorrardor();
        verify(solicitudDocumentacionService, times(1)).save(solicitudDocPrevia);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#guardarBorrardor()}.
     */
    @Test
    public final void testGuardarBorrardorException() {
        SolicitudDocumentacionPrevia solicitudDocPrevia = mock(SolicitudDocumentacionPrevia.class);
        provisionalSolicitudBean.setSolicitudDocumentacionPrevia(solicitudDocPrevia);
        when(solicitudDocumentacionService.save(solicitudDocPrevia))
                .thenThrow(TransientDataAccessResourceException.class);
        provisionalSolicitudBean.guardarBorrardor();
        verify(solicitudDocumentacionService, times(1)).save(solicitudDocPrevia);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#imprimirPdf()}.
     * @throws ProgesinException lanzada
     */
    @Test
    public final void testImprimirPdf() throws ProgesinException {
        SolicitudDocumentacionPrevia solicitud = mock(SolicitudDocumentacionPrevia.class);
        provisionalSolicitudBean.setSolicitudDocumentacionPrevia(solicitud);
        List<DocumentacionPrevia> listDoc = new ArrayList<>();
        provisionalSolicitudBean.setListadoDocumentosPrevios(listDoc);
        
        provisionalSolicitudBean.imprimirPdf();
        
        verify(pdfGenerator, times(1)).exportarPdf();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.solicitudes.ProvisionalSolicitudBean#imprimirPdf()}.
     * @throws ProgesinException lanzada
     */
    @Test
    public final void testImprimirPdfException() throws ProgesinException {
        SolicitudDocumentacionPrevia solicitud = mock(SolicitudDocumentacionPrevia.class);
        provisionalSolicitudBean.setSolicitudDocumentacionPrevia(solicitud);
        List<DocumentacionPrevia> listDoc = new ArrayList<>();
        provisionalSolicitudBean.setListadoDocumentosPrevios(listDoc);
        when(pdfGenerator.exportarPdf()).thenThrow(ProgesinException.class);
        
        provisionalSolicitudBean.imprimirPdf();
        
        verify(pdfGenerator, times(1)).exportarPdf();
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.DOCUMENTACION.getDescripcion()),
                any(ProgesinException.class));
    }
    
}
