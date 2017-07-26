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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;

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
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionarioId;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;

/**
 * 
 * Test del bean EnvioCuestionarioBean.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
public class CuestionarioEnviadoBeanValidarTest {
    /**
     * Constante user.
     */
    private static final String USUARIOLOGUEADO = "usuarioLogueado";
    
    /**
     * Constante correo envio.
     */
    private static final String CORREOENVIO = "correo_test";
    
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
     * Constante pregunta1.
     */
    private static final String PREGUNTA1 = "pregunta_test1";
    
    /**
     * Constante pregunta2.
     */
    private static final String PREGUNTA2 = "pregunta_test2";
    
    /**
     * Constante pregunta1.
     */
    private static final String RESPUESTA1 = "respuesta_test1";
    
    /**
     * Constante pregunta1.
     */
    private static final String RESPUESTA2 = "respuesta_test2";
    
    /**
     * Mock Repositorio de respuestas de cuestionario.
     */
    @Mock
    private IRespuestaCuestionarioRepository respuestaRepository;
    
    /**
     * Captor de tipo AreaUsuarioCuestEnv.
     */
    @Captor
    private ArgumentCaptor<List<RespuestaCuestionario>> listaRespuestasValidadas;
    
    /**
     * Mock Bean para mostrar formulario del cuestionario.
     */
    @Mock
    private VisualizarCuestionario visualizarCuestionario;
    
    /**
     * Mock Servicio del registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Simulación de CuestionarioEnviadoBean.
     */
    @InjectMocks
    private CuestionarioEnviadoBean cuestionarioEnviadoBeanMock;
    
    /**
     * Mock Servicio de notificaciones.
     */
    @Mock
    private INotificacionService notificacionService;
    
    /**
     * Mock Servicio de cuestionarios enviados.
     */
    @Mock
    private ICuestionarioEnvioService cuestionarioEnvioService;
    
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
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#validarRespuestas()}.
     */
    @Test
    public final void testValidarRespuestasFinalizado() {
        List<RoleEnum> rolesANotificar = new ArrayList<>();
        rolesANotificar.add(RoleEnum.ROLE_SERVICIO_APOYO);
        rolesANotificar.add(RoleEnum.ROLE_JEFE_INSPECCIONES);
        
        Equipo equipo = mock(Equipo.class);
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        inspeccion.setAmbito(AmbitoInspeccionEnum.GC);
        inspeccion.setEquipo(equipo);
        
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setInspeccion(inspeccion);
        cuestionarioEnviado.setCorreoEnvio(CORREOENVIO);
        cuestionarioEnviado.setFechaEnvio(new Date(1L));
        cuestionarioEnviado.setFechaCumplimentacion(new Date(86400403L));
        
        when(visualizarCuestionario.getCuestionarioEnviado()).thenReturn(cuestionarioEnviado);
        
        List<RespuestaCuestionario> listaRespuestasTotales = new ArrayList<>();
        
        PreguntasCuestionario pregunta1 = new PreguntasCuestionario();
        pregunta1.setId(1L);
        pregunta1.setPregunta(PREGUNTA1);
        RespuestaCuestionarioId respuestaId1 = new RespuestaCuestionarioId();
        respuestaId1.setCuestionarioEnviado(cuestionarioEnviado);
        respuestaId1.setPregunta(pregunta1);
        RespuestaCuestionario respuesta1 = new RespuestaCuestionario();
        respuesta1.setRespuestaId(respuestaId1);
        respuesta1.setRespuestaTexto(RESPUESTA1);
        
        PreguntasCuestionario pregunta2 = new PreguntasCuestionario();
        pregunta2.setId(2L);
        pregunta2.setPregunta(PREGUNTA2);
        RespuestaCuestionarioId respuestaId2 = new RespuestaCuestionarioId();
        respuestaId2.setPregunta(pregunta2);
        respuestaId2.setCuestionarioEnviado(cuestionarioEnviado);
        RespuestaCuestionario respuesta2 = new RespuestaCuestionario();
        respuesta2.setRespuestaId(respuestaId2);
        respuesta2.setRespuestaTexto(RESPUESTA2);
        
        listaRespuestasTotales.add(respuesta1);
        listaRespuestasTotales.add(respuesta2);
        
        when(visualizarCuestionario.getListaRespuestas()).thenReturn(listaRespuestasTotales);
        
        Map<PreguntasCuestionario, Boolean> mapaValidacionRespuestas = new HashMap<>();
        mapaValidacionRespuestas.put(pregunta1, true);
        mapaValidacionRespuestas.put(pregunta2, true);
        
        when(visualizarCuestionario.getMapaValidacionRespuestas()).thenReturn(mapaValidacionRespuestas);
        
        cuestionarioEnviadoBeanMock.validarRespuestas();
        verify(respuestaRepository, times(1)).save(listaRespuestasValidadas.capture());
        verify(respuestaRepository, times(1)).flush();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), eq("Validación"),
                any(String.class));
        verify(cuestionarioEnvioService, times(1)).transaccSaveElimUsuariosProv(cuestionarioEnviado);
        assertThat(cuestionarioEnviado.getUsernameFinalizacion()).isNotNull();
        assertThat(cuestionarioEnviado.getFechaFinalizacion()).isNotNull();
        assertThat(cuestionarioEnviado.getFechaNoConforme()).isNull();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), eq("Finalización"),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.CUESTIONARIO.getDescripcion()));
        verify(notificacionService, times(1)).crearNotificacionRol(any(String.class),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()), eq(rolesANotificar));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()), eq(cuestionarioEnviado.getInspeccion().getEquipo()));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#validarRespuestas()}.
     */
    @Test
    public final void testValidarRespuestasValidadoParcialmente() {
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        inspeccion.setAmbito(AmbitoInspeccionEnum.GC);
        
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setInspeccion(inspeccion);
        cuestionarioEnviado.setCorreoEnvio(CORREOENVIO);
        cuestionarioEnviado.setFechaEnvio(new Date(1L));
        cuestionarioEnviado.setFechaCumplimentacion(new Date(86400403L));
        
        when(visualizarCuestionario.getCuestionarioEnviado()).thenReturn(cuestionarioEnviado);
        
        List<RespuestaCuestionario> listaRespuestasTotales = new ArrayList<>();
        
        PreguntasCuestionario pregunta1 = new PreguntasCuestionario();
        pregunta1.setId(1L);
        pregunta1.setPregunta(PREGUNTA1);
        RespuestaCuestionarioId respuestaId1 = new RespuestaCuestionarioId();
        respuestaId1.setCuestionarioEnviado(cuestionarioEnviado);
        respuestaId1.setPregunta(pregunta1);
        RespuestaCuestionario respuesta1 = new RespuestaCuestionario();
        respuesta1.setRespuestaId(respuestaId1);
        respuesta1.setRespuestaTexto(RESPUESTA1);
        
        PreguntasCuestionario pregunta2 = new PreguntasCuestionario();
        pregunta2.setId(2L);
        pregunta2.setPregunta(PREGUNTA2);
        RespuestaCuestionarioId respuestaId2 = new RespuestaCuestionarioId();
        respuestaId2.setPregunta(pregunta2);
        respuestaId2.setCuestionarioEnviado(cuestionarioEnviado);
        RespuestaCuestionario respuesta2 = new RespuestaCuestionario();
        respuesta2.setRespuestaId(respuestaId2);
        respuesta2.setRespuestaTexto(RESPUESTA2);
        
        listaRespuestasTotales.add(respuesta1);
        listaRespuestasTotales.add(respuesta2);
        
        when(visualizarCuestionario.getListaRespuestas()).thenReturn(listaRespuestasTotales);
        
        Map<PreguntasCuestionario, Boolean> mapaValidacionRespuestas = new HashMap<>();
        mapaValidacionRespuestas.put(pregunta1, true);
        mapaValidacionRespuestas.put(pregunta2, false);
        
        when(visualizarCuestionario.getMapaValidacionRespuestas()).thenReturn(mapaValidacionRespuestas);
        
        cuestionarioEnviadoBeanMock.validarRespuestas();
        verify(respuestaRepository, times(1)).save(listaRespuestasValidadas.capture());
        verify(respuestaRepository, times(1)).flush();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), eq("Validación"),
                any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#validarRespuestas()}.
     */
    @Test
    public final void testValidarRespuestasSinPreguntasValidadas() {
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        inspeccion.setAmbito(AmbitoInspeccionEnum.GC);
        
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setInspeccion(inspeccion);
        cuestionarioEnviado.setCorreoEnvio(CORREOENVIO);
        cuestionarioEnviado.setFechaEnvio(new Date(1L));
        cuestionarioEnviado.setFechaCumplimentacion(new Date(86400403L));
        
        when(visualizarCuestionario.getCuestionarioEnviado()).thenReturn(cuestionarioEnviado);
        
        List<RespuestaCuestionario> listaRespuestasTotales = new ArrayList<>();
        
        PreguntasCuestionario pregunta1 = new PreguntasCuestionario();
        pregunta1.setId(1L);
        pregunta1.setPregunta(PREGUNTA1);
        RespuestaCuestionarioId respuestaId1 = new RespuestaCuestionarioId();
        respuestaId1.setCuestionarioEnviado(cuestionarioEnviado);
        respuestaId1.setPregunta(pregunta1);
        RespuestaCuestionario respuesta1 = new RespuestaCuestionario();
        respuesta1.setRespuestaId(respuestaId1);
        respuesta1.setRespuestaTexto(RESPUESTA1);
        
        PreguntasCuestionario pregunta2 = new PreguntasCuestionario();
        pregunta2.setId(2L);
        pregunta2.setPregunta(PREGUNTA2);
        RespuestaCuestionarioId respuestaId2 = new RespuestaCuestionarioId();
        respuestaId2.setPregunta(pregunta2);
        respuestaId2.setCuestionarioEnviado(cuestionarioEnviado);
        RespuestaCuestionario respuesta2 = new RespuestaCuestionario();
        respuesta2.setRespuestaId(respuestaId2);
        respuesta2.setRespuestaTexto(RESPUESTA2);
        
        listaRespuestasTotales.add(respuesta1);
        listaRespuestasTotales.add(respuesta2);
        
        when(visualizarCuestionario.getListaRespuestas()).thenReturn(listaRespuestasTotales);
        
        Map<PreguntasCuestionario, Boolean> mapaValidacionRespuestas = new HashMap<>();
        mapaValidacionRespuestas.put(pregunta1, false);
        mapaValidacionRespuestas.put(pregunta2, false);
        
        when(visualizarCuestionario.getMapaValidacionRespuestas()).thenReturn(mapaValidacionRespuestas);
        
        cuestionarioEnviadoBeanMock.validarRespuestas();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#validarRespuestas()}.
     */
    @Test
    public final void testValidarRespuestasDataException() {
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        inspeccion.setAmbito(AmbitoInspeccionEnum.GC);
        
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionarioEnviado.setInspeccion(inspeccion);
        cuestionarioEnviado.setCorreoEnvio(CORREOENVIO);
        cuestionarioEnviado.setFechaEnvio(new Date(1L));
        cuestionarioEnviado.setFechaCumplimentacion(new Date(86400403L));
        
        when(visualizarCuestionario.getCuestionarioEnviado()).thenReturn(cuestionarioEnviado);
        
        List<RespuestaCuestionario> listaRespuestasTotales = new ArrayList<>();
        
        PreguntasCuestionario pregunta1 = new PreguntasCuestionario();
        pregunta1.setId(1L);
        pregunta1.setPregunta(PREGUNTA1);
        RespuestaCuestionarioId respuestaId1 = new RespuestaCuestionarioId();
        respuestaId1.setCuestionarioEnviado(cuestionarioEnviado);
        respuestaId1.setPregunta(pregunta1);
        RespuestaCuestionario respuesta1 = new RespuestaCuestionario();
        respuesta1.setRespuestaId(respuestaId1);
        respuesta1.setRespuestaTexto(RESPUESTA1);
        
        PreguntasCuestionario pregunta2 = new PreguntasCuestionario();
        pregunta2.setId(2L);
        pregunta2.setPregunta(PREGUNTA2);
        RespuestaCuestionarioId respuestaId2 = new RespuestaCuestionarioId();
        respuestaId2.setPregunta(pregunta2);
        respuestaId2.setCuestionarioEnviado(cuestionarioEnviado);
        RespuestaCuestionario respuesta2 = new RespuestaCuestionario();
        respuesta2.setRespuestaId(respuestaId2);
        respuesta2.setRespuestaTexto(RESPUESTA2);
        
        listaRespuestasTotales.add(respuesta1);
        listaRespuestasTotales.add(respuesta2);
        
        when(visualizarCuestionario.getListaRespuestas()).thenReturn(listaRespuestasTotales);
        
        Map<PreguntasCuestionario, Boolean> mapaValidacionRespuestas = new HashMap<>();
        mapaValidacionRespuestas.put(pregunta1, true);
        mapaValidacionRespuestas.put(pregunta2, true);
        
        when(visualizarCuestionario.getMapaValidacionRespuestas()).thenReturn(mapaValidacionRespuestas);
        doThrow(TransientDataAccessResourceException.class).when(respuestaRepository)
                .save(listaRespuestasValidadas.capture());
        
        cuestionarioEnviadoBeanMock.validarRespuestas();
        verify(respuestaRepository, times(1)).save(listaRespuestasValidadas.capture());
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
}
