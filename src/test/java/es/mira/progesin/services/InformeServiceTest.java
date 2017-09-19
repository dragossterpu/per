/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.exceptions.ExcepcionRollback;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.persistence.entities.informes.AsignSubareaInformeUser;
import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.persistence.entities.informes.RespuestaInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.persistence.repositories.IInformeRepository;
import es.mira.progesin.persistence.repositories.IRespuestaInformeRepository;
import es.mira.progesin.persistence.repositories.ISubareaInformeRepository;

/**
 * 
 * Test del servicio Modelo de Informes.
 *
 * @author EZENTIS
 */
@PrepareForTest(SecurityContextHolder.class)
@PowerMockIgnore("javax.security.*")
@RunWith(PowerMockRunner.class)
public class InformeServiceTest {
    
    /**
     * Mock del security context de spring.
     */
    @Mock
    private SecurityContext securityContext;
    
    /**
     * Mock del Authentication de spring.
     */
    @Mock
    private Authentication authentication;
    
    /**
     * Repositorio de informes.
     */
    @Mock
    private IInformeRepository informeRepository;
    
    /**
     * Repositorio respuestas.
     */
    @Mock
    private IRespuestaInformeRepository respuestaInformeRepository;
    
    /**
     * Repositorio de subareas de informe.
     */
    @Mock
    private ISubareaInformeRepository subareaInformeRepository;
    
    /**
     * Servicio de asignación de subáreas de informe a inspectores.
     */
    @Mock
    private IAsignSubareaInformeUserService asignSubareaInformeUserService;
    
    /**
     * Servicio de inspecciones.
     */
    @Mock
    private IInspeccionesService inspeccionService;
    
    /**
     * Servicio de modelos de informes.
     */
    @InjectMocks
    private IInformeService informeService = new InformeService();
    
    /**
     * Literal user_login.
     */
    private static final String USERLOGIN = "user_login";
    
    /**
     * Captor informe.
     */
    @Captor
    private ArgumentCaptor<Informe> informeCaptor;
    
    /**
     * Captor lista respuestas.
     */
    @Captor
    private ArgumentCaptor<List<RespuestaInforme>> listaRespuestasCaptor;
    
    /**
     * Captor informe.
     */
    private User userLogin;
    
    /**
     * Inicializa el test.
     */
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USERLOGIN);
        userLogin = new User();
        userLogin.setUsername(USERLOGIN);
        when(authentication.getPrincipal()).thenReturn(userLogin);
    }
    
    /**
     * Comprobación de que la clase existe.
     */
    @Test
    public void type() {
        assertThat(InformeService.class).isNotNull();
    }
    
    /**
     * Comprobación de que la clase no es abstracta.
     */
    @Test
    public void instantiation() {
        IInformeService target = new InformeService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InformeService#save(es.mira.progesin.persistence.entities.informes.Informe)}.
     */
    @Test
    public final void testSave() {
        Informe informe = mock(Informe.class);
        informeService.save(informe);
        verify(informeRepository, timeout(1)).save(informe);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InformeService#guardarInforme(es.mira.progesin.persistence.entities.informes.Informe, java.util.Map, java.util.Map)}.
     */
    @Test
    public final void testGuardarInforme() {
        Informe informe = new Informe();
        informe.setId(1L);
        when(informeRepository.findById(1L)).thenReturn(informe);
        Map<SubareaInforme, String[]> mapaRespuestas = new HashMap<>();
        Map<SubareaInforme, String> mapaAsignaciones = new HashMap<>();
        
        String[] respuestas = new String[2];
        respuestas[0] = "respuestaTest";
        respuestas[1] = "conclusionesTest";
        SubareaInforme subareaInforme = new SubareaInforme();
        subareaInforme.setId(1L);
        mapaRespuestas.put(subareaInforme, respuestas);
        mapaAsignaciones.put(subareaInforme, USERLOGIN);
        when(subareaInformeRepository.findOne(1L)).thenReturn(subareaInforme);
        
        informeService.desasignarInforme(informe, mapaRespuestas, mapaAsignaciones);
        
        verify(respuestaInformeRepository, times(1)).save(listaRespuestasCaptor.capture());
        verify(respuestaInformeRepository, times(1)).findByInforme(informeCaptor.capture());
        verify(informeRepository, times(1)).save(informeCaptor.capture());
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InformeService#desasignarInforme(es.mira.progesin.persistence.entities.informes.Informe, java.util.Map, java.util.Map)}.
     */
    @Test
    public final void testDesasignarInforme() {
        Informe informe = new Informe();
        informe.setId(1L);
        when(informeRepository.findById(1L)).thenReturn(informe);
        Map<SubareaInforme, String[]> mapaRespuestas = new HashMap<>();
        Map<SubareaInforme, String> mapaAsignaciones = new HashMap<>();
        
        String[] respuestas = new String[2];
        respuestas[0] = "respuestaTest";
        respuestas[1] = "conclusionesTest";
        SubareaInforme subareaInforme = new SubareaInforme();
        subareaInforme.setId(1L);
        mapaRespuestas.put(subareaInforme, respuestas);
        mapaAsignaciones.put(subareaInforme, USERLOGIN);
        when(subareaInformeRepository.findOne(1L)).thenReturn(subareaInforme);
        
        informeService.desasignarInforme(informe, mapaRespuestas, mapaAsignaciones);
        
        verify(respuestaInformeRepository, times(1)).save(listaRespuestasCaptor.capture());
        verify(respuestaInformeRepository, times(1)).findByInforme(informeCaptor.capture());
        verify(asignSubareaInformeUserService, times(1)).deleteByInformeAndUser(informeCaptor.capture(), eq(userLogin));
        verify(informeRepository, times(1)).save(informeCaptor.capture());
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InformeService#finalizarInforme(es.mira.progesin.persistence.entities.informes.Informe, java.util.Map, java.util.Map)}.
     */
    @Test
    public final void testFinalizarInforme() {
        Informe informe = new Informe();
        informe.setId(1L);
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        informe.setInspeccion(inspeccion);
        when(informeRepository.findById(1L)).thenReturn(informe);
        Map<SubareaInforme, String[]> mapaRespuestas = new HashMap<>();
        Map<SubareaInforme, String> mapaAsignaciones = new HashMap<>();
        
        String[] respuestas = new String[2];
        respuestas[0] = "respuestaTest";
        respuestas[1] = "conclusionesTest";
        SubareaInforme subareaInforme = new SubareaInforme();
        subareaInforme.setId(1L);
        mapaRespuestas.put(subareaInforme, respuestas);
        mapaAsignaciones.put(subareaInforme, USERLOGIN);
        
        when(subareaInformeRepository.findOne(1L)).thenReturn(subareaInforme);
        when(subareaInformeRepository.buscaSubareasSinResponder(1L)).thenReturn(0L);
        when(inspeccionService.findInspeccionById(1L)).thenReturn(inspeccion);
        ArgumentCaptor<Inspeccion> inspeccionCaptor = ArgumentCaptor.forClass(Inspeccion.class);
        
        informeService.finalizarInforme(informe, mapaRespuestas, mapaAsignaciones);
        
        verify(respuestaInformeRepository, times(1)).save(listaRespuestasCaptor.capture());
        verify(respuestaInformeRepository, times(1)).findByInforme(informeCaptor.capture());
        verify(asignSubareaInformeUserService, times(1)).deleteByInforme(informeCaptor.capture());
        verify(informeRepository, times(1)).save(informeCaptor.capture());
        assertThat(informeCaptor.getValue().getFechaFinalizacion()).isNotNull();
        verify(inspeccionService, times(1)).save(inspeccionCaptor.capture());
        assertThat(inspeccionCaptor.getValue().getEstadoInspeccion())
                .isEqualTo(EstadoInspeccionEnum.J_INFORME_REALIZADO);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.InformeService#findConRespuestas(java.lang.Long)}.
     */
    @Test
    public final void testFindConRespuestas() {
        informeService.findConRespuestas(1L);
        verify(informeRepository, times(1)).findById(1L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InformeService#existsByModeloPersonalizado(es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado)}.
     */
    @Test
    public final void testExistsByModeloPersonalizado() {
        ModeloInformePersonalizado informePersonalizado = mock(ModeloInformePersonalizado.class);
        informeService.existsByModeloPersonalizado(informePersonalizado);
        verify(informeRepository, timeout(1)).existsByModeloPersonalizado(informePersonalizado);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InformeService#asignarSubarea(es.mira.progesin.persistence.entities.informes.SubareaInforme, es.mira.progesin.persistence.entities.informes.Informe)}.
     */
    @Test
    public final void testAsignarSubarea() {
        SubareaInforme subAreaInforme = new SubareaInforme();
        subAreaInforme.setId(2L);
        Informe informe = new Informe();
        informe.setId(1L);
        
        ArgumentCaptor<AsignSubareaInformeUser> asignSubareaInformeUserCaptor = ArgumentCaptor
                .forClass(AsignSubareaInformeUser.class);
        
        when(asignSubareaInformeUserService.findBySubareaAndInforme(subAreaInforme, informe)).thenReturn(null);
        when(subareaInformeRepository.findOne(subAreaInforme.getId())).thenReturn(subAreaInforme);
        when(informeRepository.findOne(informe.getId())).thenReturn(informe);
        
        informeService.asignarSubarea(subAreaInforme, informe);
        
        verify(asignSubareaInformeUserService, times(1)).save(asignSubareaInformeUserCaptor.capture());
        assertThat(asignSubareaInformeUserCaptor.getValue().getSubarea()).isEqualTo(subAreaInforme);
        assertThat(asignSubareaInformeUserCaptor.getValue().getInforme()).isEqualTo(informe);
        assertThat(asignSubareaInformeUserCaptor.getValue().getUser()).isEqualTo(userLogin);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.InformeService#buscaSubareasSinResponder(java.lang.Long)}.
     */
    @Test
    public final void testBuscaSubareasSinResponder() {
        informeService.buscaSubareasSinResponder(1L);
        verify(subareaInformeRepository, times(1)).buscaSubareasSinResponder(1L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InformeService#crearInforme(es.mira.progesin.persistence.entities.Inspeccion, es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado)}.
     * @throws ExcepcionRollback excepción RollBack
     */
    @Test
    public final void testCrearInforme() throws ExcepcionRollback {
        Inspeccion inspecc = new Inspeccion();
        ModeloInformePersonalizado modeloPersonalizado = mock(ModeloInformePersonalizado.class);
        informeService.crearInforme(inspecc, modeloPersonalizado);
        verify(inspeccionService, timeout(1)).save(inspecc);
        verify(informeRepository, timeout(1)).save(informeCaptor.capture());
        assertThat(informeCaptor.getValue().getModeloPersonalizado()).isEqualTo(modeloPersonalizado);
        assertThat(informeCaptor.getValue().getInspeccion().getEstadoInspeccion())
                .isEqualTo(EstadoInspeccionEnum.I_ELABORACION_INFORME);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.InformeService#existsByInspeccionAndFechaBajaIsNull(es.mira.progesin.persistence.entities.Inspeccion)}.
     * @throws ExcepcionRollback excepción RollBack
     */
    @Test
    public final void testExistsByInspeccionAndFechaBajaIsNull() throws ExcepcionRollback {
        Inspeccion inspeccion = mock(Inspeccion.class);
        informeService.existsByInspeccionAndFechaBajaIsNull(inspeccion);
        informeRepository.existsByInspeccionAndFechaBajaIsNull(inspeccion);
        
    }
    
}
