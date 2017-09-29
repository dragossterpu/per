/**
 * 
 */
package es.mira.progesin.web.beans.cuestionarios;

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
import javax.faces.component.UIComponent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionarioId;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.IAreaCuestionarioService;
import es.mira.progesin.services.IAreaUsuarioCuestEnvService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.IRespuestaCuestionarioService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.DataTableView;
import es.mira.progesin.util.FacesUtilities;

/**
 * 
 * Test del bean ResponderCuestionarioBean.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
public class ResponderCuestionarioBeanTest {
    /**
     * Constante user.
     */
    private static final String USUARIOLOGUEADO = "usuarioLogueado";
    
    /**
     * Constante correo principal.
     */
    private static final String CORREOPRINCIPAL = "correoPrincipal";
    
    /**
     * Constante preguntaTest.
     */
    private static final String PREGUNTATEST = "preguntaTest";
    
    /**
     * Constante respuestaTest.
     */
    private static final String RESPUESTATEST = "respuestaTest";
    
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
     * Visualizar cuestionario.
     */
    @Mock
    private VisualizarCuestionario visualizarCuestionario;
    
    /**
     * Servicio de respuestas.
     */
    @Mock
    private IRespuestaCuestionarioService respuestaService;
    
    /**
     * Servicio de registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Servicio de alertas.
     */
    @Mock
    private IAlertaService alertaService;
    
    /**
     * Servicio de areas/usuario de cuestionarios enviados.
     */
    @Mock
    private IAreaUsuarioCuestEnvService areaUsuarioCuestEnvService;
    
    /**
     * Servicio de areas cuestionario.
     */
    @Mock
    private IAreaCuestionarioService areaCuestionarioService;
    
    /**
     * Servicio de usuarios.
     */
    @Mock
    private IUserService userService;
    
    /**
     * Simulacion de ResponderCuestionarioBean.
     */
    @InjectMocks
    private ResponderCuestionarioBean responderCuestionarioBean;
    
    /**
     * Captor de tipo List<RespuestaCuestionario>.
     */
    @Captor
    private ArgumentCaptor<List<RespuestaCuestionario>> listaRespuestaCuestionarioCaptor;
    
    /**
     * Captor de tipo Inspeccion.
     */
    @Captor
    private ArgumentCaptor<Inspeccion> inspeccionCaptor;
    
    /**
     * Captor de tipo <List<RespuestaCuestionario>.
     */
    @Captor
    private ArgumentCaptor<List<RespuestaCuestionario>> listaRespuestasCaptor;
    
    /**
     * Captor de tipo RespuestaCuestionario.
     */
    @Captor
    private ArgumentCaptor<RespuestaCuestionario> respuestaCaptor;
    
    /**
     * Captor de tipo UploadedFile.
     */
    @Captor
    private ArgumentCaptor<UploadedFile> archivoSubidoCaptor;
    
    /**
     * Comprueba que la clase existe.
     */
    @Test
    public void type() {
        assertThat(VisualizarCuestionario.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     */
    @Test
    public void instantiation() {
        VisualizarCuestionario target = new VisualizarCuestionario();
        assertThat(target).isNotNull();
    }
    
    /**
     * Inicializa el test.
     */
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USUARIOLOGUEADO);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#guardarBorrador()}.
     */
    @Test
    public final void testGuardarBorrador() {
        List<RespuestaCuestionario> listaRespuestaCuestionario = new ArrayList<>();
        RespuestaCuestionario respuesta = new RespuestaCuestionario();
        AreasCuestionario area = new AreasCuestionario();
        area.setId(1L);
        PreguntasCuestionario pregunta = new PreguntasCuestionario();
        pregunta.setId(1L);
        pregunta.setArea(area);
        pregunta.setPregunta("pregunta1");
        pregunta.setTipoRespuesta("ADJUNTO");
        RespuestaCuestionarioId respuestaId = new RespuestaCuestionarioId();
        respuestaId.setCuestionarioEnviado(mock(CuestionarioEnvio.class));
        respuestaId.setPregunta(pregunta);
        respuesta.setRespuestaId(respuestaId);
        respuesta.setRespuestaTablaMatriz(new ArrayList<>());
        listaRespuestaCuestionario.add(respuesta);
        
        Map<PreguntasCuestionario, String> mapaRespuestas = new HashMap<>();
        mapaRespuestas.put(pregunta, "resp");
        when(visualizarCuestionario.getMapaRespuestas()).thenReturn(mapaRespuestas);
        when(respuestaService.transaccSaveConRespuestas(listaRespuestaCuestionarioCaptor.capture()))
                .thenReturn(listaRespuestaCuestionario);
        
        Map<Long, String> mapaAreaUsuarioCuestEnv = new HashMap<>();
        mapaAreaUsuarioCuestEnv.put(1L, USUARIOLOGUEADO);
        responderCuestionarioBean.setMapaAreaUsuarioCuestEnv(mapaAreaUsuarioCuestEnv);
        responderCuestionarioBean.setCuestionarioEnviado(new CuestionarioEnvio());
        
        List<Documento> documetos = new ArrayList<>();
        documetos.add(mock(Documento.class));
        Map<PreguntasCuestionario, List<Documento>> mapaDocumentos = new HashMap<>();
        mapaDocumentos.put(pregunta, documetos);
        when(visualizarCuestionario.getMapaDocumentos()).thenReturn(mapaDocumentos);
        
        when(visualizarCuestionario.getMapaRespuestasTablaAux()).thenReturn(new HashMap<>());
        responderCuestionarioBean.guardarBorrador();
        
        verify(visualizarCuestionario, times(1)).getMapaRespuestas();
        verify(visualizarCuestionario, times(1)).getMapaRespuestasTabla();
        verify(respuestaService, times(1)).transaccSaveConRespuestas(listaRespuestaCuestionarioCaptor.capture());
        verify(visualizarCuestionario, times(1)).getMapaDocumentos();
        verify(visualizarCuestionario, times(1)).construirTipoRespuestaTablaMatrizConDatos();
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#guardarBorrador()}.
     */
    @Test
    public final void testGuardarBorradorException() {
        List<RespuestaCuestionario> listaRespuestaCuestionario = new ArrayList<>();
        RespuestaCuestionario respuesta = new RespuestaCuestionario();
        AreasCuestionario area = new AreasCuestionario();
        area.setId(1L);
        PreguntasCuestionario pregunta = new PreguntasCuestionario();
        pregunta.setId(1L);
        pregunta.setArea(area);
        pregunta.setPregunta("pregunta1");
        pregunta.setTipoRespuesta("TABLA");
        RespuestaCuestionarioId respuestaId = new RespuestaCuestionarioId();
        respuestaId.setCuestionarioEnviado(mock(CuestionarioEnvio.class));
        respuestaId.setPregunta(pregunta);
        respuesta.setRespuestaId(respuestaId);
        respuesta.setRespuestaTablaMatriz(new ArrayList<>());
        listaRespuestaCuestionario.add(respuesta);
        
        Map<PreguntasCuestionario, String> mapaRespuestas = new HashMap<>();
        mapaRespuestas.put(pregunta, "resp");
        when(visualizarCuestionario.getMapaRespuestas()).thenReturn(mapaRespuestas);
        when(respuestaService.transaccSaveConRespuestas(listaRespuestaCuestionarioCaptor.capture()))
                .thenThrow(TransientDataAccessResourceException.class);
        
        Map<Long, String> mapaAreaUsuarioCuestEnv = new HashMap<>();
        mapaAreaUsuarioCuestEnv.put(1L, USUARIOLOGUEADO);
        responderCuestionarioBean.setMapaAreaUsuarioCuestEnv(mapaAreaUsuarioCuestEnv);
        responderCuestionarioBean.setCuestionarioEnviado(new CuestionarioEnvio());
        
        responderCuestionarioBean.guardarBorrador();
        
        verify(visualizarCuestionario, times(1)).getMapaRespuestas();
        verify(respuestaService, times(1)).transaccSaveConRespuestas(listaRespuestaCuestionarioCaptor.capture());
        verify(visualizarCuestionario, times(1)).getMapaRespuestasTabla();
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#enviarCuestionario()}.
     */
    @Test
    public final void testEnviarCuestionarioControlaTodasLasAreas() {
        List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv = new ArrayList<>();
        AreaUsuarioCuestEnv areaUsuario = new AreaUsuarioCuestEnv();
        areaUsuario.setUsernameProv(USUARIOLOGUEADO);
        listaAreasUsuarioCuestEnv.add(areaUsuario);
        responderCuestionarioBean.setListaAreasUsuarioCuestEnv(listaAreasUsuarioCuestEnv);
        CuestionarioEnvio cuestionario = new CuestionarioEnvio();
        cuestionario.setInspeccion(mock(Inspeccion.class));
        responderCuestionarioBean.setCuestionarioEnviado(cuestionario);
        
        responderCuestionarioBean.enviarCuestionario();
        
        verify(respuestaService, times(1)).transaccSaveConRespuestasInactivaUsuariosProv(
                eq(responderCuestionarioBean.getCuestionarioEnviado()), listaRespuestasCaptor.capture());
        
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class), any());
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()));
        verify(alertaService, times(1)).crearAlertaRol(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(String.class), eq(RoleEnum.ROLE_SERVICIO_APOYO));
        verify(alertaService, times(1)).crearAlertaEquipo(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(String.class), inspeccionCaptor.capture());
        assertThat(responderCuestionarioBean.getCuestionarioEnviado().getFechaCumplimentacion()).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#enviarCuestionario()}.
     */
    @Test
    public final void testEnviarCuestionarioNoControlaTodasLasAreas() {
        List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv = new ArrayList<>();
        AreaUsuarioCuestEnv areaUsuario = new AreaUsuarioCuestEnv();
        areaUsuario.setUsernameProv("otro");
        listaAreasUsuarioCuestEnv.add(areaUsuario);
        responderCuestionarioBean.setListaAreasUsuarioCuestEnv(listaAreasUsuarioCuestEnv);
        responderCuestionarioBean.enviarCuestionario();
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#enviarCuestionario()}.
     */
    @Test
    public final void testEnviarCuestionarioControlaTodasLasAreasException() {
        List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv = new ArrayList<>();
        AreaUsuarioCuestEnv areaUsuario = new AreaUsuarioCuestEnv();
        areaUsuario.setUsernameProv(USUARIOLOGUEADO);
        listaAreasUsuarioCuestEnv.add(areaUsuario);
        responderCuestionarioBean.setListaAreasUsuarioCuestEnv(listaAreasUsuarioCuestEnv);
        CuestionarioEnvio cuestionario = new CuestionarioEnvio();
        cuestionario.setInspeccion(mock(Inspeccion.class));
        responderCuestionarioBean.setCuestionarioEnviado(cuestionario);
        doThrow(TransientDataAccessResourceException.class).when(respuestaService)
                .transaccSaveConRespuestasInactivaUsuariosProv(eq(responderCuestionarioBean.getCuestionarioEnviado()),
                        listaRespuestasCaptor.capture());
        responderCuestionarioBean.enviarCuestionario();
        
        verify(respuestaService, times(1)).transaccSaveConRespuestasInactivaUsuariosProv(
                eq(responderCuestionarioBean.getCuestionarioEnviado()), listaRespuestasCaptor.capture());
        
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#eliminarFilaRespuestaTabla(es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario)}.
     */
    @Test
    public final void testEliminarFilaRespuestaTabla() {
        PreguntasCuestionario pregunta = new PreguntasCuestionario();
        pregunta.setId(1L);
        DataTableView datos = mock(DataTableView.class);
        Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla = new HashMap<>();
        mapaRespuestasTabla.put(pregunta, datos);
        when(visualizarCuestionario.getMapaRespuestasTabla()).thenReturn(mapaRespuestasTabla);
        responderCuestionarioBean.eliminarFilaRespuestaTabla(pregunta);
        verify(datos, times(1)).eliminarFila();
        verify(visualizarCuestionario, times(1)).setMapaRespuestasTabla(mapaRespuestasTabla);
        assertThat(mapaRespuestasTabla.get(pregunta)).isEqualTo(datos);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#subirFichero(org.primefaces.event.FileUploadEvent)}.
     * @throws ProgesinException lanzada.
     */
    @Test
    public final void testSubirFichero() throws ProgesinException {
        FileUploadEvent event = mock(FileUploadEvent.class);
        UploadedFile archivoSubido = mock(UploadedFile.class);
        UIComponent componente = mock(UIComponent.class);
        PreguntasCuestionario pregunta = new PreguntasCuestionario();
        pregunta.setPregunta(PREGUNTATEST);
        Map<String, Object> map = new HashMap<>();
        map.put("pregunta", pregunta);
        when(event.getFile()).thenReturn(archivoSubido);
        when(respuestaService.esExtensionCorrecta(archivoSubido)).thenReturn(true);
        when(event.getComponent()).thenReturn(componente);
        when(componente.getAttributes()).thenReturn(map);
        CuestionarioEnvio cuestionarioEnvio = mock(CuestionarioEnvio.class);
        responderCuestionarioBean.setCuestionarioEnviado(cuestionarioEnvio);
        List<Documento> documentos = new ArrayList<>();
        documentos.add(mock(Documento.class));
        Map<PreguntasCuestionario, List<Documento>> mapaDocumentos = new HashMap<>();
        mapaDocumentos.put(pregunta, documentos);
        when(visualizarCuestionario.getMapaDocumentos()).thenReturn(mapaDocumentos);
        Map<PreguntasCuestionario, String> mapaRespuestas = new HashMap<>();
        mapaRespuestas.put(pregunta, RESPUESTATEST);
        when(visualizarCuestionario.getMapaRespuestas()).thenReturn(mapaRespuestas);
        RespuestaCuestionario respuestaCuestionario = new RespuestaCuestionario();
        respuestaCuestionario.setDocumentos(documentos);
        when(respuestaService.saveConDocumento(respuestaCaptor.capture(), eq(archivoSubido)))
                .thenReturn(respuestaCuestionario);
        
        responderCuestionarioBean.subirFichero(event);
        
        verify(respuestaService, times(1)).esExtensionCorrecta(archivoSubido);
        verify(visualizarCuestionario, times(1)).getMapaRespuestas();
        verify(visualizarCuestionario, times(2)).getMapaDocumentos();
        verify(respuestaService, times(1)).saveConDocumento(respuestaCaptor.capture(), archivoSubidoCaptor.capture());
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#subirFichero(org.primefaces.event.FileUploadEvent)}.
     */
    @Test
    public final void testSubirFicheroIncorrecto() {
        FileUploadEvent event = mock(FileUploadEvent.class);
        UploadedFile archivoSubido = mock(UploadedFile.class);
        when(event.getFile()).thenReturn(archivoSubido);
        when(respuestaService.esExtensionCorrecta(archivoSubido)).thenReturn(false);
        
        responderCuestionarioBean.subirFichero(event);
        
        verify(respuestaService, times(1)).esExtensionCorrecta(archivoSubido);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#subirFichero(org.primefaces.event.FileUploadEvent)}.
     * @throws ProgesinException lanzada.
     */
    @Test
    public final void testSubirFicheroException() throws ProgesinException {
        FileUploadEvent event = mock(FileUploadEvent.class);
        UploadedFile archivoSubido = mock(UploadedFile.class);
        UIComponent componente = mock(UIComponent.class);
        PreguntasCuestionario pregunta = new PreguntasCuestionario();
        pregunta.setPregunta(PREGUNTATEST);
        Map<String, Object> map = new HashMap<>();
        map.put("pregunta", pregunta);
        when(event.getFile()).thenReturn(archivoSubido);
        when(respuestaService.esExtensionCorrecta(archivoSubido)).thenReturn(true);
        when(event.getComponent()).thenReturn(componente);
        when(componente.getAttributes()).thenReturn(map);
        CuestionarioEnvio cuestionarioEnvio = mock(CuestionarioEnvio.class);
        responderCuestionarioBean.setCuestionarioEnviado(cuestionarioEnvio);
        List<Documento> documentos = new ArrayList<>();
        documentos.add(mock(Documento.class));
        Map<PreguntasCuestionario, List<Documento>> mapaDocumentos = new HashMap<>();
        mapaDocumentos.put(pregunta, documentos);
        when(visualizarCuestionario.getMapaDocumentos()).thenReturn(mapaDocumentos);
        Map<PreguntasCuestionario, String> mapaRespuestas = new HashMap<>();
        mapaRespuestas.put(pregunta, RESPUESTATEST);
        when(visualizarCuestionario.getMapaRespuestas()).thenReturn(mapaRespuestas);
        RespuestaCuestionario respuestaCuestionario = new RespuestaCuestionario();
        respuestaCuestionario.setDocumentos(documentos);
        when(respuestaService.saveConDocumento(respuestaCaptor.capture(), eq(archivoSubido)))
                .thenThrow(TransientDataAccessResourceException.class);
        
        responderCuestionarioBean.subirFichero(event);
        
        verify(respuestaService, times(1)).esExtensionCorrecta(archivoSubido);
        verify(visualizarCuestionario, times(1)).getMapaRespuestas();
        verify(respuestaService, times(1)).saveConDocumento(respuestaCaptor.capture(), archivoSubidoCaptor.capture());
        verify(visualizarCuestionario, times(1)).getMapaDocumentos();
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#eliminarDocumento(es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario, es.mira.progesin.persistence.entities.gd.Documento)}.
     */
    @Test
    public final void testEliminarDocumento() {
        PreguntasCuestionario pregunta = new PreguntasCuestionario();
        pregunta.setPregunta(PREGUNTATEST);
        List<Documento> documentos = new ArrayList<>();
        Documento documento = new Documento();
        documento.setId(1L);
        documentos.add(documento);
        Map<PreguntasCuestionario, List<Documento>> mapaDocumentos = new HashMap<>();
        mapaDocumentos.put(pregunta, documentos);
        when(visualizarCuestionario.getMapaDocumentos()).thenReturn(mapaDocumentos);
        Map<PreguntasCuestionario, String> mapaRespuestas = new HashMap<>();
        mapaRespuestas.put(pregunta, RESPUESTATEST);
        when(visualizarCuestionario.getMapaRespuestas()).thenReturn(mapaRespuestas);
        RespuestaCuestionario respuestaCuestionario = new RespuestaCuestionario();
        respuestaCuestionario.setDocumentos(documentos);
        when(respuestaService.eliminarDocumentoRespuesta(respuestaCaptor.capture(), eq(documento)))
                .thenReturn(respuestaCuestionario);
        responderCuestionarioBean.setCuestionarioEnviado(mock(CuestionarioEnvio.class));
        
        responderCuestionarioBean.eliminarDocumento(pregunta, documento);
        
        verify(respuestaService, times(1)).eliminarDocumentoRespuesta(respuestaCaptor.capture(), eq(documento));
        verify(visualizarCuestionario, times(2)).getMapaDocumentos();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#eliminarDocumento(es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario, es.mira.progesin.persistence.entities.gd.Documento)}.
     */
    @Test
    public final void testEliminarDocumentoException() {
        PreguntasCuestionario pregunta = new PreguntasCuestionario();
        pregunta.setPregunta(PREGUNTATEST);
        List<Documento> documentos = new ArrayList<>();
        Documento documento = new Documento();
        documento.setId(1L);
        documentos.add(documento);
        Map<PreguntasCuestionario, List<Documento>> mapaDocumentos = new HashMap<>();
        mapaDocumentos.put(pregunta, documentos);
        when(visualizarCuestionario.getMapaDocumentos()).thenReturn(mapaDocumentos);
        Map<PreguntasCuestionario, String> mapaRespuestas = new HashMap<>();
        mapaRespuestas.put(pregunta, RESPUESTATEST);
        when(visualizarCuestionario.getMapaRespuestas()).thenReturn(mapaRespuestas);
        RespuestaCuestionario respuestaCuestionario = new RespuestaCuestionario();
        respuestaCuestionario.setDocumentos(documentos);
        when(respuestaService.eliminarDocumentoRespuesta(respuestaCaptor.capture(), eq(documento)))
                .thenThrow(TransientDataAccessResourceException.class);
        responderCuestionarioBean.setCuestionarioEnviado(mock(CuestionarioEnvio.class));
        
        responderCuestionarioBean.eliminarDocumento(pregunta, documento);
        
        verify(respuestaService, times(1)).eliminarDocumentoRespuesta(respuestaCaptor.capture(), eq(documento));
        verify(visualizarCuestionario, times(1)).getMapaDocumentos();
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#init()}.
     */
    @Test
    public final void testInitUsuarioPrincipal() {
        String correoUser = CORREOPRINCIPAL;
        User user = new User();
        user.setRole(RoleEnum.ROLE_PROV_CUESTIONARIO);
        user.setCorreo(correoUser);
        user.setUsername(correoUser);
        when(authentication.getPrincipal()).thenReturn(user);
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setId(1L);
        cuestionarioEnviado.setCorreoEnvio("usernameProv");
        when(respuestaService.buscaCuestionarioAResponder(correoUser)).thenReturn(cuestionarioEnviado);
        List<User> usuariosProv = new ArrayList<>();
        usuariosProv.add(mock(User.class));
        when(userService.crearUsuariosProvisionalesCuestionario(eq(cuestionarioEnviado.getCorreoEnvio()),
                any(String.class))).thenReturn(usuariosProv);
        List<AreaUsuarioCuestEnv> listaAreasUsuario = new ArrayList<>();
        AreaUsuarioCuestEnv areasUsuario = new AreaUsuarioCuestEnv();
        areasUsuario.setIdArea(1L);
        areasUsuario.setIdCuestionarioEnviado(1L);
        areasUsuario.setUsernameProv("usernameProv");
        listaAreasUsuario.add(areasUsuario);
        when(areaUsuarioCuestEnvService.findByIdCuestionarioEnviado(cuestionarioEnviado.getId()))
                .thenReturn(listaAreasUsuario);
        
        responderCuestionarioBean.init();
        
        verify(respuestaService, times(1)).buscaCuestionarioAResponder(correoUser);
        verify(userService, times(1)).listaUsuariosProvisionalesCorreo(cuestionarioEnviado.getCorreoEnvio());
        verify(areaUsuarioCuestEnvService, times(1)).findByIdCuestionarioEnviado(eq(cuestionarioEnviado.getId()));
        verify(visualizarCuestionario, times(1)).setListaAreasVisualizarUsuario(areaCuestionarioService
                .findByIdIn(new ArrayList<>(responderCuestionarioBean.getMapaAreaUsuarioCuestEnv().keySet())));
        verify(visualizarCuestionario, times(1)).generarMapaAreasVisualizarUsuario();
        verify(visualizarCuestionario, times(1)).visualizarRespuestasCuestionario(cuestionarioEnviado);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#init()}.
     */
    @Test
    public final void testInitUsuarioNoPrincipal() {
        String correoUser = CORREOPRINCIPAL;
        User user = new User();
        user.setRole(RoleEnum.ROLE_PROV_CUESTIONARIO);
        user.setCorreo(correoUser);
        user.setUsername("user1");
        when(authentication.getPrincipal()).thenReturn(user);
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setId(1L);
        when(respuestaService.buscaCuestionarioAResponder(correoUser)).thenReturn(cuestionarioEnviado);
        List<User> usuariosProv = new ArrayList<>();
        usuariosProv.add(mock(User.class));
        when(userService.crearUsuariosProvisionalesCuestionario(eq(cuestionarioEnviado.getCorreoEnvio()),
                any(String.class))).thenReturn(usuariosProv);
        List<AreaUsuarioCuestEnv> listaAreasUsuario = new ArrayList<>();
        AreaUsuarioCuestEnv areasUsuario = new AreaUsuarioCuestEnv();
        areasUsuario.setIdArea(1L);
        areasUsuario.setIdCuestionarioEnviado(1L);
        areasUsuario.setUsernameProv("usernameProv");
        listaAreasUsuario.add(areasUsuario);
        when(areaUsuarioCuestEnvService.findByIdCuestionarioEnviadoAndUsuarioProv(cuestionarioEnviado.getId(),
                user.getUsername())).thenReturn(listaAreasUsuario);
        
        responderCuestionarioBean.init();
        
        verify(respuestaService, times(1)).buscaCuestionarioAResponder(correoUser);
        verify(userService, times(1)).listaUsuariosProvisionalesCorreo(cuestionarioEnviado.getCorreoEnvio());
        verify(areaUsuarioCuestEnvService, times(1))
                .findByIdCuestionarioEnviadoAndUsuarioProv(cuestionarioEnviado.getId(), user.getUsername());
        verify(visualizarCuestionario, times(1)).setListaAreasVisualizarUsuario(areaCuestionarioService
                .findByIdIn(new ArrayList<>(responderCuestionarioBean.getMapaAreaUsuarioCuestEnv().keySet())));
        verify(visualizarCuestionario, times(1)).generarMapaAreasVisualizarUsuario();
        verify(visualizarCuestionario, times(1)).visualizarRespuestasCuestionario(cuestionarioEnviado);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#asignarAreas()}.
     */
    @Test
    public final void testAsignarAreas() {
        String userName = "userTest";
        List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv = new ArrayList<>();
        AreaUsuarioCuestEnv areaUsuarioCuestEnv = new AreaUsuarioCuestEnv();
        areaUsuarioCuestEnv.setIdArea(1L);
        areaUsuarioCuestEnv.setUsernameProv(userName);
        listaAreasUsuarioCuestEnv.add(areaUsuarioCuestEnv);
        responderCuestionarioBean.setListaAreasUsuarioCuestEnv(listaAreasUsuarioCuestEnv);
        when(areaUsuarioCuestEnvService.save(listaAreasUsuarioCuestEnv)).thenReturn(listaAreasUsuarioCuestEnv);
        
        responderCuestionarioBean.asignarAreas();
        
        verify(areaUsuarioCuestEnvService, times(1)).asignarAreasUsuarioYActivar(listaAreasUsuarioCuestEnv);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#asignarAreas()}.
     */
    @Test
    public final void testAsignarAreasException() {
        String userName = "userTest";
        List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv = new ArrayList<>();
        AreaUsuarioCuestEnv areaUsuarioCuestEnv = new AreaUsuarioCuestEnv();
        areaUsuarioCuestEnv.setIdArea(1L);
        areaUsuarioCuestEnv.setUsernameProv(userName);
        listaAreasUsuarioCuestEnv.add(areaUsuarioCuestEnv);
        responderCuestionarioBean.setListaAreasUsuarioCuestEnv(listaAreasUsuarioCuestEnv);
        doThrow(TransientDataAccessResourceException.class).when(areaUsuarioCuestEnvService)
                .asignarAreasUsuarioYActivar(listaAreasUsuarioCuestEnv);
        
        responderCuestionarioBean.asignarAreas();
        
        verify(areaUsuarioCuestEnvService, times(1)).asignarAreasUsuarioYActivar(listaAreasUsuarioCuestEnv);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#guardarYAsignarAreasAlPrincipal()}.
     */
    @Test
    public final void testGuardarYAsignarAreasAlPrincipal() {
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setCorreoEnvio("correoTest");
        responderCuestionarioBean.setCuestionarioEnviado(cuestionarioEnviado);
        
        List<RespuestaCuestionario> listaRespuestas = new ArrayList<>();
        
        List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv = new ArrayList<>();
        AreaUsuarioCuestEnv areaUsuarioCuestEnv = new AreaUsuarioCuestEnv();
        areaUsuarioCuestEnv.setIdArea(1L);
        areaUsuarioCuestEnv.setUsernameProv(USUARIOLOGUEADO);
        listaAreasUsuarioCuestEnv.add(areaUsuarioCuestEnv);
        responderCuestionarioBean.setListaAreasUsuarioCuestEnv(listaAreasUsuarioCuestEnv);
        when(areaUsuarioCuestEnvService.save(listaAreasUsuarioCuestEnv)).thenReturn(listaAreasUsuarioCuestEnv);
        
        responderCuestionarioBean.guardarYAsignarAreasAlPrincipal();
        
        verify(respuestaService, times(1)).guardarRespuestasYAsignarAreasPrincipal(listaRespuestas, USUARIOLOGUEADO,
                cuestionarioEnviado.getCorreoEnvio(), listaAreasUsuarioCuestEnv);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class), eq("dialogMessageReasignar"));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#guardarYAsignarAreasAlPrincipal()}.
     */
    @Test
    public final void testGuardarYAsignarAreasAlPrincipalExcepcion() {
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setCorreoEnvio("correoTest");
        responderCuestionarioBean.setCuestionarioEnviado(cuestionarioEnviado);
        
        List<RespuestaCuestionario> listaRespuestas = new ArrayList<>();
        
        List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv = new ArrayList<>();
        AreaUsuarioCuestEnv areaUsuarioCuestEnv = new AreaUsuarioCuestEnv();
        areaUsuarioCuestEnv.setIdArea(1L);
        areaUsuarioCuestEnv.setUsernameProv(USUARIOLOGUEADO);
        listaAreasUsuarioCuestEnv.add(areaUsuarioCuestEnv);
        responderCuestionarioBean.setListaAreasUsuarioCuestEnv(listaAreasUsuarioCuestEnv);
        
        when(respuestaService.guardarRespuestasYAsignarAreasPrincipal(listaRespuestas, USUARIOLOGUEADO,
                cuestionarioEnviado.getCorreoEnvio(), listaAreasUsuarioCuestEnv))
                        .thenThrow(TransientDataAccessResourceException.class);
        
        responderCuestionarioBean.guardarYAsignarAreasAlPrincipal();
        
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#generarMapaAreaUsuarioCuestEnv()}.
     */
    @Test
    public final void testGenerarMapaAreaUsuarioCuestEnv() {
        List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv = new ArrayList<>();
        AreaUsuarioCuestEnv areaUsuarioCuestEnv = new AreaUsuarioCuestEnv();
        areaUsuarioCuestEnv.setIdArea(1L);
        areaUsuarioCuestEnv.setUsernameProv("userTest");
        listaAreasUsuarioCuestEnv.add(areaUsuarioCuestEnv);
        responderCuestionarioBean.setListaAreasUsuarioCuestEnv(listaAreasUsuarioCuestEnv);
        responderCuestionarioBean.setMapaAreaUsuarioCuestEnv(new HashMap<>());
        
        responderCuestionarioBean.generarMapaAreaUsuarioCuestEnv();
        assertThat(responderCuestionarioBean.getMapaAreaUsuarioCuestEnv().get(areaUsuarioCuestEnv.getIdArea()))
                .isEqualTo(areaUsuarioCuestEnv.getUsernameProv());
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#descargarPlantilla(es.mira.progesin.persistence.entities.gd.Documento)}.
     * @throws ProgesinException lanzada.
     */
    @Test
    public final void testDescargarPlantilla() throws ProgesinException {
        DefaultStreamedContent doc = mock(DefaultStreamedContent.class);
        Documento plantilla = new Documento();
        plantilla.setId(1L);
        when(respuestaService.descargarPlantilla(plantilla.getId())).thenReturn(doc);
        
        responderCuestionarioBean.descargarPlantilla(plantilla);
        verify(respuestaService, times(1)).descargarPlantilla(plantilla.getId());
        assertThat(doc).isEqualTo(responderCuestionarioBean.getFile());
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ResponderCuestionarioBean#descargarPlantilla(es.mira.progesin.persistence.entities.gd.Documento)}.
     * @throws ProgesinException lanzada.
     */
    @Test
    public final void testDescargarPlantillaException() throws ProgesinException {
        Documento plantilla = new Documento();
        plantilla.setId(1L);
        when(respuestaService.descargarPlantilla(plantilla.getId())).thenThrow(ProgesinException.class);
        
        responderCuestionarioBean.descargarPlantilla(plantilla);
        verify(respuestaService, times(1)).descargarPlantilla(plantilla.getId());
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(ProgesinException.class));
        
    }
    
}
