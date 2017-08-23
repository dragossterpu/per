package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.Visibility;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.lazydata.LazyModelDocumentos;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.entities.gd.TipoDocumento;
import es.mira.progesin.persistence.repositories.gd.ITipoDocumentoRepository;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.VerificadorExtensiones;

/**
 * Test para GestorDocumentalBean.
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class, RequestContext.class })
public class GestorDocumentalBeanTest {
    /**
     * Constante descripcion.
     */
    private static final String DESCRIPCION = "descripcionTest";
    
    /**
     * Constante nombre de documento.
     */
    private static final String NOMBREDOC = "nombreDocTest";
    
    /**
     * Constante materia indexada.
     */
    private static final String MATERIAINDEX = "materiaIndexadaTest";
    
    /**
     * Simulación del securityContext.
     */
    @Mock
    private SecurityContext securityContext;
    
    /**
     * Simulación del RequestContext.
     */
    @Mock
    private RequestContext requestContext;
    
    /**
     * Simulación de la autenticación.
     */
    @Mock
    private Authentication authentication;
    
    /**
     * Mock de prueba del Verificador de extensiones.
     */
    @Mock
    private VerificadorExtensiones verificadorExtensiones;
    
    /**
     * Mock de prueba del Servicio de documentos.
     */
    @Mock
    private IDocumentoService documentoService;
    
    /**
     * Mock de prueba del Servicio de registro de actividad.
     */
    @Mock
    private IRegistroActividadService registroActividadService;
    
    /**
     * Mock de prueba del Servicio de inspecciones.
     */
    @Mock
    private IInspeccionesService inspeccionesService;
    
    /**
     * Mock de prueba del Repositorio de tipos de documento.
     */
    @Mock
    private ITipoDocumentoRepository tipoDocumentoRepository;
    
    /**
     * Simulación Lazy Model para la consulta paginada de documentos en base de datos.
     */
    @Mock
    private LazyModelDocumentos model;
    
    /**
     * Simulación del Gestor Documental.
     */
    @InjectMocks
    private GestorDocumentalBean gestorDocumentalBeanMock;
    
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
        assertThat(GestorDocumentalBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        GestorDocumentalBean target = new GestorDocumentalBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GestorDocumentalBean#init()}.
     */
    @Test
    public final void testInit() {
        gestorDocumentalBeanMock.init();
        assertThat(gestorDocumentalBeanMock.getDocumentoBusqueda()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getList()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getList().size()).isEqualTo(6);
        assertThat(gestorDocumentalBeanMock.getModel()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getMapaInspecciones()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getMapaEdicion()).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GestorDocumentalBean#resetBusqueda()}.
     */
    @Test
    public final void testResetBusqueda() {
        model = new LazyModelDocumentos(documentoService);
        String ruta = gestorDocumentalBeanMock.resetBusqueda();
        assertThat(gestorDocumentalBeanMock.getDocumentoBusqueda()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getModel().getRowCount()).isEqualTo(0);
        assertThat(gestorDocumentalBeanMock.getListaInspecciones()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getNombreDoc()).isEqualTo("");
        assertThat(gestorDocumentalBeanMock.getDocumentoBusqueda().isEliminado()).isFalse();
        assertThat(ruta).isEqualTo("/gestorDocumental/buscarDocumento?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GestorDocumentalBean#resetBusquedaEliminados()}.
     */
    @Test
    public final void testResetBusquedaEliminados() {
        Inspeccion insp1 = new Inspeccion();
        Inspeccion insp2 = new Inspeccion();
        
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(insp1);
        inspecciones.add(insp2);
        
        Documento doc = new Documento();
        List<Documento> documentos = new ArrayList<>();
        documentos.add(doc);
        model = new LazyModelDocumentos(documentoService);
        model.setDatasource(documentos);
        gestorDocumentalBeanMock.setDocumentoBusqueda(new DocumentoBusqueda());
        
        String ruta = gestorDocumentalBeanMock.resetBusquedaEliminados();
        assertThat(gestorDocumentalBeanMock.getDocumentoBusqueda()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getListaInspecciones()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getNombreDoc()).isEqualTo("");
        assertThat(gestorDocumentalBeanMock.getDocumentoBusqueda().isEliminado()).isTrue();
        assertThat(ruta).isEqualTo("/administracion/papelera/papelera?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#onToggle(org.primefaces.event.ToggleEvent)}.
     */
    @Test
    public final void testOnToggle_False() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        when(eventMock.getVisibility()).thenReturn(Visibility.HIDDEN);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.FALSE);
        gestorDocumentalBeanMock.setList(listaToogle);
        gestorDocumentalBeanMock.onToggle(eventMock);
        assertThat(listaToogle.get(0)).isFalse();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#onToggle(org.primefaces.event.ToggleEvent)}.
     */
    @Test
    public final void testOnToggle_True() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        when(eventMock.getVisibility()).thenReturn(Visibility.VISIBLE);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.TRUE);
        gestorDocumentalBeanMock.setList(listaToogle);
        gestorDocumentalBeanMock.onToggle(eventMock);
        assertThat(listaToogle.get(0)).isTrue();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GestorDocumentalBean#recargaLista()}.
     */
    @Test
    public final void testRecargaLista() {
        DocumentoBusqueda busqueda = new DocumentoBusqueda();
        gestorDocumentalBeanMock.setDocumentoBusqueda(busqueda);
        gestorDocumentalBeanMock.recargaLista();
        assertThat(gestorDocumentalBeanMock.getDocumentoBusqueda().isEliminado()).isFalse();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GestorDocumentalBean#recargaListaEliminados()}.
     */
    @Test
    public final void testRecargaListaEliminados() {
        DocumentoBusqueda busqueda = new DocumentoBusqueda();
        gestorDocumentalBeanMock.setDocumentoBusqueda(busqueda);
        gestorDocumentalBeanMock.recargaListaEliminados();
        assertThat(gestorDocumentalBeanMock.getDocumentoBusqueda().isEliminado()).isTrue();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#descargarFichero(es.mira.progesin.persistence.entities.gd.Documento)}
     * . .
     * @throws ProgesinException excepción lanzada
     */
    @Test
    public final void testDescargarFichero() throws ProgesinException {
        Documento doc1 = new Documento();
        doc1.setId(2L);
        DefaultStreamedContent defaultStreamedContent = mock(DefaultStreamedContent.class);
        when(documentoService.findOne(doc1.getId())).thenReturn(doc1);
        when(documentoService.descargaDocumento(doc1)).thenReturn(defaultStreamedContent);
        gestorDocumentalBeanMock.descargarFichero(doc1);
        
        assertThat(gestorDocumentalBeanMock.getFile()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getFile()).isInstanceOf(DefaultStreamedContent.class);
        verify(registroActividadService, never()).altaRegActividadError(eq(SeccionesEnum.GESTOR.getDescripcion()),
                any(ProgesinException.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#descargarFichero(es.mira.progesin.persistence.entities.gd.Documento)}
     * . .
     * @throws ProgesinException excepción lanzada
     */
    @Test()
    public final void testDescargarFicheroException() throws ProgesinException {
        Documento doc1 = new Documento();
        doc1.setId(2L);
        ProgesinException progesinException = new ProgesinException(new Exception(""));
        when(documentoService.findOne(doc1.getId())).thenReturn(doc1);
        
        when(documentoService.descargaDocumento(doc1)).thenThrow(progesinException);
        gestorDocumentalBeanMock.descargarFichero(doc1);
        assertThat(gestorDocumentalBeanMock.getFile()).isNull();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        verify(registroActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.GESTOR.getDescripcion()),
                any(ProgesinException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#descargarFichero(es.mira.progesin.persistence.entities.gd.Documento)}.
     */
    @Test()
    public final void testDescargarFicheroNoExiste() {
        Documento doc1 = new Documento();
        doc1.setId(2L);
        when(documentoService.findOne(doc1.getId())).thenReturn(null);
        gestorDocumentalBeanMock.descargarFichero(doc1);
        assertThat(gestorDocumentalBeanMock.getFile()).isNull();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#cargaFichero(org.primefaces.event.FileUploadEvent)}. .
     * @throws ProgesinException posible excepción
     */
    @Test()
    public final void testCargaFichero() throws ProgesinException {
        Documento doc = mock(Documento.class);
        TipoDocumento tipoDocTest = mock(TipoDocumento.class);
        FileUploadEvent event = mock(FileUploadEvent.class);
        UploadedFile uploadedFile = mock(UploadedFile.class);
        
        when(tipoDocumentoRepository.findByNombre("OTROS")).thenReturn(tipoDocTest);
        when(event.getFile()).thenReturn(uploadedFile);
        when(uploadedFile.getFileName()).thenReturn("fileName_test");
        when(verificadorExtensiones.extensionCorrecta(uploadedFile)).thenReturn(true);
        when(documentoService.cargaDocumentoSinGuardar(uploadedFile, tipoDocTest, null)).thenReturn(doc);
        
        gestorDocumentalBeanMock.cargaFichero(event);
        
        assertThat(gestorDocumentalBeanMock.getDocumento()).isEqualTo(doc);
        verify(registroActividadService, never()).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ERROR.name()),
                eq(SeccionesEnum.GESTOR.getDescripcion()));
        verify(registroActividadService, never()).altaRegActividadError(eq(SeccionesEnum.GESTOR.getDescripcion()),
                any(ProgesinException.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#cargaFichero(org.primefaces.event.FileUploadEvent)}.
     */
    @Test()
    public final void testCargaFicheroExtensionIncorrecta() {
        TipoDocumento tipoDocTest = mock(TipoDocumento.class);
        FileUploadEvent event = mock(FileUploadEvent.class);
        UploadedFile uploadedFile = mock(UploadedFile.class);
        
        when(tipoDocumentoRepository.findByNombre("OTROS")).thenReturn(tipoDocTest);
        when(event.getFile()).thenReturn(uploadedFile);
        when(uploadedFile.getFileName()).thenReturn("fileName_test");
        when(verificadorExtensiones.extensionCorrecta(uploadedFile)).thenReturn(false);
        
        gestorDocumentalBeanMock.cargaFichero(event);
        assertThat(gestorDocumentalBeanMock.getDocumento()).isNull();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        verify(registroActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.ERROR.name()), eq(SeccionesEnum.GESTOR.getDescripcion()));
        verify(registroActividadService, never()).altaRegActividadError(eq(SeccionesEnum.GESTOR.getDescripcion()),
                any(ProgesinException.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#cargaFichero(org.primefaces.event.FileUploadEvent)}.
     * @throws ProgesinException posible excepción
     */
    @Test()
    public final void testCargaFicheroException() throws ProgesinException {
        TipoDocumento tipoDocTest = mock(TipoDocumento.class);
        FileUploadEvent event = mock(FileUploadEvent.class);
        UploadedFile uploadedFile = mock(UploadedFile.class);
        
        when(tipoDocumentoRepository.findByNombre("OTROS")).thenReturn(tipoDocTest);
        when(event.getFile()).thenReturn(uploadedFile);
        when(uploadedFile.getFileName()).thenReturn("fileName_test");
        when(verificadorExtensiones.extensionCorrecta(uploadedFile)).thenReturn(true);
        when(documentoService.cargaDocumentoSinGuardar(uploadedFile, tipoDocTest, null))
                .thenThrow(ProgesinException.class);
        
        gestorDocumentalBeanMock.cargaFichero(event);
        
        assertThat(gestorDocumentalBeanMock.getDocumento()).isNull();
        
        verify(registroActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.GESTOR.getDescripcion()),
                any(ProgesinException.class));
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#eliminarDocumento(es.mira.progesin.persistence.entities.gd.Documento)}
     * . .
     */
    @Test
    public final void testEliminarDocumento() {
        gestorDocumentalBeanMock.setDocumentoBusqueda(new DocumentoBusqueda());
        Documento doc = spy(Documento.class);
        doc.setId(2L);
        doc.setNombre(NOMBREDOC);
        when(documentoService.save(doc)).thenReturn(doc);
        gestorDocumentalBeanMock.eliminarDocumento(doc);
        assertThat(doc.getFechaBaja()).isNotNull();
        assertThat(doc.getUsernameBaja()).isEqualTo("usuarioLogueado");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GestorDocumentalBean#limpiarBusqueda()}.
     */
    @Test
    public final void testLimpiarBusqueda() {
        gestorDocumentalBeanMock.limpiarBusqueda();
        assertThat(gestorDocumentalBeanMock.getDocumentoBusqueda()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getModel().getRowCount()).isEqualTo(0);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GestorDocumentalBean#buscaDocumento()}.
     */
    @Test
    public final void testBuscaDocumentoAdmin() {
        Inspeccion insp1 = spy(Inspeccion.class);
        insp1.setId(1L);
        insp1.setAnio(2018);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(insp1);
        User user = User.builder().username("administrador").role(RoleEnum.ROLE_ADMIN).build();
        Documento doc1 = spy(Documento.class);
        doc1.setId(1L);
        Documento doc2 = spy(Documento.class);
        doc2.setId(2L);
        List<Documento> documentos = new ArrayList<>();
        documentos.add(doc1);
        documentos.add(doc2);
        
        DocumentoBusqueda docBusqueda = new DocumentoBusqueda();
        gestorDocumentalBeanMock.setDocumentoBusqueda(docBusqueda);
        Map<Long, String> mapaInspecciones = new LinkedHashMap<>();
        Map<Long, Boolean> mapaEdicion = new HashMap<>();
        gestorDocumentalBeanMock.setMapaInspecciones(mapaInspecciones);
        gestorDocumentalBeanMock.setMapaEdicion(mapaEdicion);
        
        when(model.getDatasource()).thenReturn(documentos);
        when(documentoService.listaInspecciones(doc1)).thenReturn(inspecciones);
        when(model.load(0, 20, "fechaAlta", SortOrder.DESCENDING, null)).thenReturn(documentos);
        when(documentoService.perteneceACuestionario(doc1)).thenReturn(1L);
        when(documentoService.perteneceASolicitud(doc1)).thenReturn(3L);
        when(authentication.getPrincipal()).thenReturn(user);
        
        gestorDocumentalBeanMock.buscaDocumento();
        
        assertThat(gestorDocumentalBeanMock.getDocumentoBusqueda()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getListaInspecciones()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getListaInspecciones().size()).isEqualTo(0);
        assertThat(gestorDocumentalBeanMock.getNombreDoc()).isEqualTo("");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GestorDocumentalBean#buscaDocumento()}.
     */
    @Test
    public final void testBuscaDocumentoNoAdmin() {
        Inspeccion insp1 = spy(Inspeccion.class);
        insp1.setId(1L);
        insp1.setAnio(2018);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(insp1);
        User user = User.builder().username("usuario").role(RoleEnum.ROLE_EQUIPO_INSPECCIONES).build();
        Documento doc1 = spy(Documento.class);
        doc1.setId(1L);
        Documento doc2 = spy(Documento.class);
        doc2.setId(2L);
        List<Documento> documentos = new ArrayList<>();
        documentos.add(doc1);
        documentos.add(doc2);
        
        DocumentoBusqueda docBusqueda = new DocumentoBusqueda();
        gestorDocumentalBeanMock.setDocumentoBusqueda(docBusqueda);
        Map<Long, String> mapaInspecciones = new LinkedHashMap<>();
        Map<Long, Boolean> mapaEdicion = new HashMap<>();
        gestorDocumentalBeanMock.setMapaInspecciones(mapaInspecciones);
        gestorDocumentalBeanMock.setMapaEdicion(mapaEdicion);
        
        when(model.getDatasource()).thenReturn(documentos);
        when(documentoService.listaInspecciones(doc1)).thenReturn(inspecciones);
        when(model.load(0, 20, "fechaAlta", SortOrder.DESCENDING, null)).thenReturn(documentos);
        when(documentoService.perteneceACuestionario(doc1)).thenReturn(1L);
        when(documentoService.perteneceASolicitud(doc1)).thenReturn(3L);
        when(authentication.getPrincipal()).thenReturn(user);
        
        gestorDocumentalBeanMock.buscaDocumento();
        
        assertThat(gestorDocumentalBeanMock.getDocumentoBusqueda()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getListaInspecciones()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getListaInspecciones().size()).isEqualTo(0);
        assertThat(gestorDocumentalBeanMock.getNombreDoc()).isEqualTo("");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GestorDocumentalBean#nuevoDocumento()}.
     */
    @Test
    public final void testNuevoDocumento() {
        gestorDocumentalBeanMock.nuevoDocumento();
        assertThat(gestorDocumentalBeanMock.getDocumento()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getDocumento()).isInstanceOf(Documento.class);
        assertThat(gestorDocumentalBeanMock.getListaInspecciones()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getListaInspecciones()).isInstanceOf(List.class);
        assertThat(gestorDocumentalBeanMock.getNombreDoc()).isEqualTo("");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GestorDocumentalBean#autocompletarInspeccion(java.lang.String)}
     * . .
     */
    @Test
    public final void testAutocompletarInspeccionAdministrador() {
        Inspeccion i = mock(Inspeccion.class);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(i);
        User user = User.builder().username("admin").role(RoleEnum.ROLE_ADMIN).build();
        when(inspeccionesService.buscarPorNombreUnidadONumero("test")).thenReturn(inspecciones);
        when(authentication.getPrincipal()).thenReturn(user);
        
        gestorDocumentalBeanMock.autocompletarInspeccion("nombre");
        verify(inspeccionesService, times(1)).buscarPorNombreUnidadONumero("nombre");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#autocompletarInspeccion(java.lang.String)}.
     * 
     */
    @Test
    public final void testAutocompletarInspeccionNoAdministrador() {
        Inspeccion i = mock(Inspeccion.class);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(i);
        User user = User.builder().username("user").role(RoleEnum.ROLE_EQUIPO_INSPECCIONES).build();
        when(inspeccionesService.buscarPorNombreUnidadONumero("nombre_test")).thenReturn(inspecciones);
        when(authentication.getPrincipal()).thenReturn(user);
        
        gestorDocumentalBeanMock.autocompletarInspeccion("nombre_test");
        verify(inspeccionesService, times(1)).buscarPorNombreUnidadONumeroUsuario("nombre_test", user);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#creaDocumento(java.lang.String, es.mira.progesin.persistence.entities.gd.TipoDocumento, java.lang.String, java.lang.String)}
     * . .
     */
    @Test
    public final void testCreaDocumento() {
        String nombreDocTest = NOMBREDOC;
        TipoDocumento tipoDocTest = mock(TipoDocumento.class);
        String descripcionTest = DESCRIPCION;
        String materiaIndexadaTest = MATERIAINDEX;
        gestorDocumentalBeanMock.setNombreDoc(NOMBREDOC);
        
        Documento doc = new Documento();
        gestorDocumentalBeanMock.setDocumento(doc);
        when(documentoService.save(doc)).thenReturn(doc);
        
        DocumentoBusqueda busqueda = new DocumentoBusqueda();
        gestorDocumentalBeanMock.setDocumentoBusqueda(busqueda);
        List<Documento> documentos = new ArrayList<>();
        when(model.getDatasource()).thenReturn(documentos);
        when(model.load(0, 20, "fechaAlta", SortOrder.DESCENDING, null)).thenReturn(documentos);
        PowerMockito.mockStatic(RequestContext.class);
        when(RequestContext.getCurrentInstance()).thenReturn(requestContext);
        doNothing().when(requestContext).reset("formAlta:asociado");
        gestorDocumentalBeanMock.creaDocumento(NOMBREDOC, tipoDocTest, descripcionTest, materiaIndexadaTest);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO),
                eq(SeccionesEnum.GESTOR.getDescripcion()), any(String.class));
        assertThat(gestorDocumentalBeanMock.getDocumento().getNombre()).isEqualTo(nombreDocTest);
        assertThat(gestorDocumentalBeanMock.getDocumento().getTipoDocumento()).isEqualTo(tipoDocTest);
        assertThat(gestorDocumentalBeanMock.getDocumento().getDescripcion()).isEqualTo(descripcionTest);
        assertThat(gestorDocumentalBeanMock.getDocumento().getMateriaIndexada()).isEqualTo(materiaIndexadaTest);
        assertThat(gestorDocumentalBeanMock.getDocumento().getFechaBaja()).isNull();
        assertThat(gestorDocumentalBeanMock.getNombreDoc()).isEqualTo("");
        assertThat(gestorDocumentalBeanMock.getListaInspecciones()).isNotNull();
        assertThat(gestorDocumentalBeanMock.getListaInspecciones()).isEmpty();
        assertThat(gestorDocumentalBeanMock.getDocumento()).isEqualTo(doc);
        
        verify(registroActividadService, never()).altaRegActividadError(eq(SeccionesEnum.GESTOR.getDescripcion()),
                any(DataAccessException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#creaDocumento(java.lang.String, es.mira.progesin.persistence.entities.gd.TipoDocumento, java.lang.String, java.lang.String)}
     * . .
     */
    @Test
    public final void testCreaDocumentoException() {
        String nombreDocTest = NOMBREDOC;
        TipoDocumento tipoDocTest = mock(TipoDocumento.class);
        String descripcionTest = DESCRIPCION;
        String materiaIndexadaTest = MATERIAINDEX;
        gestorDocumentalBeanMock.setNombreDoc("nombreTest");
        
        Documento doc = new Documento();
        gestorDocumentalBeanMock.setDocumento(doc);
        when(documentoService.save(doc)).thenThrow(TransientDataAccessResourceException.class);
        gestorDocumentalBeanMock.creaDocumento(nombreDocTest, tipoDocTest, descripcionTest, materiaIndexadaTest);
        
        verify(registroActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.GESTOR.getDescripcion()),
                any(DataAccessException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#creaDocumento(java.lang.String, es.mira.progesin.persistence.entities.gd.TipoDocumento, java.lang.String, java.lang.String)}
     * . .
     */
    @Test
    public final void testCreaDocumentoIncorrecto() {
        String nombreDocTest = NOMBREDOC;
        TipoDocumento tipoDocTest = null;
        String descripcionTest = DESCRIPCION;
        String materiaIndexadaTest = MATERIAINDEX;
        gestorDocumentalBeanMock.setNombreDoc("nombreTest");
        gestorDocumentalBeanMock.creaDocumento(nombreDocTest, tipoDocTest, descripcionTest, materiaIndexadaTest);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
    }
    
    /***
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#creaDocumento(java.lang.String, es.mira.progesin.persistence.entities.gd.TipoDocumento, java.lang.String, java.lang.String)}.
     */
    
    @Test
    public final void testCreaDocumentoSinNombre() {
        String nombreDocTest = "";
        TipoDocumento tipoDocTest = mock(TipoDocumento.class);
        String descripcionTest = DESCRIPCION;
        String materiaIndexadaTest = MATERIAINDEX;
        gestorDocumentalBeanMock.setNombreDoc("nombreTest");
        gestorDocumentalBeanMock.creaDocumento(nombreDocTest, tipoDocTest, descripcionTest, materiaIndexadaTest);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
    }
    
    /***
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#creaDocumento(java.lang.String, es.mira.progesin.persistence.entities.gd.TipoDocumento, java.lang.String, java.lang.String)}.
     */
    
    @Test
    public final void testCreaDocumentoFicheroSinNombre() {
        String nombreDocTest = NOMBREDOC;
        TipoDocumento tipoDocTest = mock(TipoDocumento.class);
        String descripcionTest = DESCRIPCION;
        String materiaIndexadaTest = MATERIAINDEX;
        gestorDocumentalBeanMock.setNombreDoc("");
        gestorDocumentalBeanMock.creaDocumento(nombreDocTest, tipoDocTest, descripcionTest, materiaIndexadaTest);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#editarDocumento(es.mira.progesin.persistence.entities.gd.Documento)}
     * . .
     */
    @Test
    public final void testEditarDocumento() {
        List<Inspeccion> inspecciones = new ArrayList<>();
        Inspeccion i1 = mock(Inspeccion.class);
        inspecciones.add(i1);
        Documento docTest = new Documento();
        docTest.setId(2L);
        when(documentoService.findOne(docTest.getId())).thenReturn(docTest);
        when(documentoService.obtieneNombreFichero(docTest)).thenReturn("nombreFichero_Test");
        when(documentoService.listaInspecciones(docTest)).thenReturn(inspecciones);
        String ruta = gestorDocumentalBeanMock.editarDocumento(docTest);
        assertThat(gestorDocumentalBeanMock.getDocumento()).isEqualTo(docTest);
        assertThat(gestorDocumentalBeanMock.getNombreDoc()).isEqualTo("nombreFichero_Test");
        assertThat(gestorDocumentalBeanMock.getDocumento().getInspeccion()).isEqualTo(inspecciones);
        assertThat(ruta).isEqualTo("/gestorDocumental/editarDocumento?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#editarDocumento(es.mira.progesin.persistence.entities.gd.Documento)}
     * . .
     */
    @Test
    public final void testEditarDocumentoNoExiste() {
        List<Inspeccion> inspecciones = new ArrayList<>();
        Inspeccion i1 = mock(Inspeccion.class);
        inspecciones.add(i1);
        Documento docTest = new Documento();
        docTest.setId(2L);
        when(documentoService.findOne(docTest.getId())).thenReturn(null);
        String ruta = gestorDocumentalBeanMock.editarDocumento(docTest);
        assertThat(gestorDocumentalBeanMock.getDocumento()).isNull();
        assertThat(ruta).isNull();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GestorDocumentalBean#modificaDocumento()}. .
     */
    @Test
    public final void testModificaDocumento() {
        DocumentoBusqueda busqueda = new DocumentoBusqueda();
        gestorDocumentalBeanMock.setDocumentoBusqueda(busqueda);
        
        Documento docTest = new Documento();
        docTest.setId(2L);
        gestorDocumentalBeanMock.setDocumento(docTest);
        when(documentoService.save(docTest)).thenReturn(docTest);
        gestorDocumentalBeanMock.modificaDocumento();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO),
                eq(SeccionesEnum.GESTOR.getDescripcion()), any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GestorDocumentalBean#modificaDocumento()}. .
     */
    @Test
    public final void testModificaDocumentoException() {
        Documento docTest = new Documento();
        docTest.setId(2L);
        gestorDocumentalBeanMock.setDocumento(docTest);
        when(documentoService.save(docTest)).thenThrow(TransientDataAccessResourceException.class);
        gestorDocumentalBeanMock.modificaDocumento();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR),
                eq(SeccionesEnum.GESTOR.getDescripcion()), any(String.class));
        verify(registroActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.GESTOR.getDescripcion()),
                any(DataAccessException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#asignarNuevaInspeccion(es.mira.progesin.persistence.entities.Inspeccion)}
     * . .
     */
    @Test
    public final void testAsignarNuevaInspeccion_SinInspecciones() {
        
        Documento docTest = new Documento();
        gestorDocumentalBeanMock.setDocumento(docTest);
        Inspeccion insParametro = new Inspeccion();
        insParametro.setId(2L);
        gestorDocumentalBeanMock.asignarNuevaInspeccion(insParametro);
        assertThat(gestorDocumentalBeanMock.getDocumento().getInspeccion()).hasSize(1);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#asignarNuevaInspeccion(es.mira.progesin.persistence.entities.Inspeccion)}
     * . .
     */
    @Test
    public final void testAsignarNuevaInspeccion_Inspeccion() {
        Inspeccion i1 = new Inspeccion();
        i1.setId(1L);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(i1);
        
        Documento docTest = new Documento();
        docTest.setInspeccion(inspecciones);
        gestorDocumentalBeanMock.setDocumento(docTest);
        Inspeccion insParametro = new Inspeccion();
        insParametro.setId(2L);
        gestorDocumentalBeanMock.asignarNuevaInspeccion(insParametro);
        assertThat(gestorDocumentalBeanMock.getDocumento().getInspeccion()).hasSize(2);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#asignarNuevaInspeccion(es.mira.progesin.persistence.entities.Inspeccion)}
     * . .
     */
    @Test
    public final void testAsignarNuevaInspeccion_InspeccionNull() {
        
        List<Inspeccion> inspecciones = new ArrayList<>();
        
        Documento docTest = new Documento();
        docTest.setInspeccion(inspecciones);
        gestorDocumentalBeanMock.setDocumento(docTest);
        Inspeccion insParametro = null;
        gestorDocumentalBeanMock.asignarNuevaInspeccion(insParametro);
        assertThat(gestorDocumentalBeanMock.getDocumento().getInspeccion()).hasSize(0);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#asignarNuevaInspeccion(es.mira.progesin.persistence.entities.Inspeccion)}
     * . .
     */
    @Test
    public final void testAsignarNuevaInspeccionExistente_InspeccionNull() {
        Inspeccion i1 = new Inspeccion();
        i1.setId(1L);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(i1);
        
        Documento docTest = new Documento();
        docTest.setInspeccion(inspecciones);
        gestorDocumentalBeanMock.setDocumento(docTest);
        Inspeccion insParametro = null;
        gestorDocumentalBeanMock.asignarNuevaInspeccion(insParametro);
        assertThat(gestorDocumentalBeanMock.getDocumento().getInspeccion()).hasSize(1);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#asignarNuevaInspeccion(es.mira.progesin.persistence.entities.Inspeccion)}
     * . .
     */
    @Test
    public final void testAsignarNuevaInspeccionExistente() {
        Inspeccion i1 = new Inspeccion();
        i1.setId(1L);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(i1);
        
        Documento docTest = new Documento();
        docTest.setInspeccion(inspecciones);
        gestorDocumentalBeanMock.setDocumento(docTest);
        Inspeccion insParametro = new Inspeccion();
        insParametro.setId(1L);
        gestorDocumentalBeanMock.asignarNuevaInspeccion(insParametro);
        assertThat(gestorDocumentalBeanMock.getDocumento().getInspeccion()).hasSize(1);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#desAsociarInspeccion(es.mira.progesin.persistence.entities.Inspeccion)}
     * . .
     */
    @Test
    public final void testDesAsociarInspeccion() {
        Inspeccion i1 = new Inspeccion();
        i1.setId(1L);
        Inspeccion i2 = new Inspeccion();
        i2.setId(2L);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(i1);
        inspecciones.add(i2);
        Documento doc = new Documento();
        doc.setInspeccion(inspecciones);
        gestorDocumentalBeanMock.setDocumento(doc);
        gestorDocumentalBeanMock.setListaInspecciones(inspecciones);
        gestorDocumentalBeanMock.desAsociarInspeccion(i1);
        assertThat(gestorDocumentalBeanMock.getDocumento().getInspeccion()).hasSize(1);
        assertThat(gestorDocumentalBeanMock.getListaInspecciones()).hasSize(1);
        assertThat(gestorDocumentalBeanMock.getListaInspecciones())
                .isEqualTo(gestorDocumentalBeanMock.getDocumento().getInspeccion());
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#recuperarDocumento(es.mira.progesin.persistence.entities.gd.Documento)}
     * . .
     */
    @Test
    public final void testRecuperarDocumento() {
        Documento documento = new Documento();
        documento.setNombre(NOMBREDOC);
        gestorDocumentalBeanMock.recuperarDocumento(documento);
        verify(documentoService, times(1)).recuperarDocumento(documento);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#borrarDocumento(es.mira.progesin.persistence.entities.gd.Documento)}
     * . .
     */
    @Test
    public final void testBorrarDocumento() {
        Documento documento = new Documento();
        documento.setNombre("documento_test");
        List<Inspeccion> inspecciones = new ArrayList<>();
        Inspeccion i1 = new Inspeccion();
        i1.setId(1L);
        Inspeccion i2 = new Inspeccion();
        i2.setId(2L);
        inspecciones.add(i1);
        inspecciones.add(i2);
        documento.setInspeccion(inspecciones);
        gestorDocumentalBeanMock.borrarDocumento(documento);
        verify(documentoService, times(1)).delete(documento);
        verify(registroActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.GESTOR.getDescripcion()));
        verify(registroActividadService, never()).altaRegActividadError(eq(SeccionesEnum.GESTOR.getDescripcion()),
                any(DataAccessException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GestorDocumentalBean#borrarDocumento(es.mira.progesin.persistence.entities.gd.Documento)}
     * .
     */
    @Test
    public final void testBorrarDocumentoException() {
        Documento documento = new Documento();
        documento.setNombre("documento_test");
        List<Inspeccion> inspecciones = new ArrayList<>();
        Inspeccion i1 = new Inspeccion();
        i1.setId(1L);
        Inspeccion i2 = new Inspeccion();
        i2.setId(2L);
        inspecciones.add(i1);
        inspecciones.add(i2);
        documento.setInspeccion(inspecciones);
        doThrow(TransientDataAccessResourceException.class).when(documentoService).delete(documento);
        gestorDocumentalBeanMock.borrarDocumento(documento);
        verify(documentoService, times(1)).delete(documento);
        verify(registroActividadService, never()).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.GESTOR.getDescripcion()));
        verify(registroActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.GESTOR.getDescripcion()),
                any(DataAccessException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GestorDocumentalBean#vaciarPapelera()}.
     */
    @Test
    public final void testVaciarPapelera() {
        gestorDocumentalBeanMock.vaciarPapelera();
        verify(documentoService, times(1)).vaciarPapelera();
    }
    
}
