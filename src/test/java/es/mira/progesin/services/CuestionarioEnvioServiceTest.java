/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.persistence.repositories.ICuestionarioEnvioRepository;
import es.mira.progesin.persistence.repositories.IDatosTablaGenericaRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.util.ICorreoElectronico;

/**
 * Test del servicio cuestionario enviado.
 *
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
// Evita conflictos con clases del sistema al enlazar los mocks por tipo
@PowerMockIgnore("javax.security.*")
@PrepareForTest(SecurityContextHolder.class)
public class CuestionarioEnvioServiceTest {
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
     * Simulación del repositorio cuestionarios enviados.
     */
    @Mock
    private transient ICuestionarioEnvioRepository cuestionarioEnvioRepository;
    
    /**
     * Simulación del repositorio de respuestas.
     */
    @Mock
    private transient IRespuestaCuestionarioRepository respuestaRepository;
    
    /**
     * Simulación del repositorio de tablas.
     */
    @Mock
    private transient IDatosTablaGenericaRepository datosTablaRepository;
    
    /**
     * Simulación del servicio de usuarios.
     */
    @Mock
    private transient IUserService userService;
    
    /**
     * Simulación del servicio de inspecciones.
     */
    @Mock
    private transient IInspeccionesService inspeccionesService;
    
    /**
     * Simulación objeto correoElectrónico.
     */
    @Mock
    private transient ICorreoElectronico correoElectronico;
    
    /**
     * Simulación de repositorio de preguntas.
     */
    @Mock
    private transient IPreguntaCuestionarioRepository preguntasRepository;
    
    /**
     * Simulación del servicio de áreas/usuario.
     */
    @Mock
    private transient IAreaUsuarioCuestEnvService areaUsuarioCuestEnvService;
    
    /**
     * Servicio de cuestionarios enviados.
     */
    @InjectMocks
    private ICuestionarioEnvioService cuestionarioEnvioService = new CuestionarioEnvioService();
    
    /**
     * Captor de tipo AreaUsuarioCuestEnv.
     */
    @Captor
    ArgumentCaptor<List<AreaUsuarioCuestEnv>> areasUsuarioCuestEnvCaptor;
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SecurityContextHolder.class);
        
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(CuestionarioEnvioService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        CuestionarioEnvioService target = new CuestionarioEnvioService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#crearYEnviarCuestionario(List, CuestionarioEnvio, String)}
     * .
     */
    @Test
    public void crearYEnviarCuestionario() {
        List<User> listadoUsuariosProvisionales = new ArrayList<User>();
        listadoUsuariosProvisionales.add(User.builder().username("usuario").build());
        CuestionarioPersonalizado cuestionarioPersonalizado = CuestionarioPersonalizado.builder().id(1L).build();
        Inspeccion inspeccion = Inspeccion.builder().id(1L).build();
        CuestionarioEnvio cuestionarioEnviado = CuestionarioEnvio.builder().id(1L)
                .cuestionarioPersonalizado(cuestionarioPersonalizado).inspeccion(inspeccion).correoEnvio("correo")
                .motivoCuestionario("motivo").build();
        when(cuestionarioEnvioRepository.save(cuestionarioEnviado)).thenReturn(cuestionarioEnviado);
        String cuerpoCorreo = "cuerpo";
        List<PreguntasCuestionario> preguntasElegidas = new ArrayList<>();
        preguntasElegidas
                .add(PreguntasCuestionario.builder().id(1L).area(AreasCuestionario.builder().id(1L).build()).build());
        preguntasElegidas
                .add(PreguntasCuestionario.builder().id(2L).area(AreasCuestionario.builder().id(2L).build()).build());
        when(preguntasRepository.findPreguntasElegidasCuestionarioPersonalizado(1L)).thenReturn(preguntasElegidas);
        
        cuestionarioEnvioService.crearYEnviarCuestionario(listadoUsuariosProvisionales, cuestionarioEnviado,
                cuerpoCorreo);
        
        verify(userService, times(1)).save(listadoUsuariosProvisionales);
        verify(cuestionarioEnvioRepository, times(1)).save(cuestionarioEnviado);
        verify(areaUsuarioCuestEnvService, times(1)).save(areasUsuarioCuestEnvCaptor.capture());
        assertThat(areasUsuarioCuestEnvCaptor.getValue()).hasSize(2);
        verify(correoElectronico, times(1)).envioCorreo(eq("correo"), any(String.class), any(String.class));
        verify(inspeccionesService, times(1)).cambiarEstado(cuestionarioEnviado.getInspeccion(),
                EstadoInspeccionEnum.F_PEND_RECIBIR_CUESTIONARIO);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuestionarioEnvioService#findNoFinalizadoPorCorreoEnvio(String)}
     * .
     */
    @Test
    public void findNoFinalizadoPorCorreoEnvio() {
        String correo = null;
        
        cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(correo);
        
        verify(cuestionarioEnvioRepository, times(1))
                .findByCorreoEnvioAndFechaFinalizacionIsNullAndFechaAnulacionIsNull(correo);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuestionarioEnvioService#save(CuestionarioEnvio)}.
     */
    @Test
    public void save() {
        CuestionarioEnvio cuestionario = mock(CuestionarioEnvio.class);
        
        cuestionarioEnvioService.save(cuestionario);
        
        verify(cuestionarioEnvioRepository, times(1)).save(cuestionario);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuestionarioEnvioService#transaccSaveConRespuestas(List)}.
     */
    @Test
    public void transaccSaveConRespuestas() {
        List<RespuestaCuestionario> listaRespuestas = new ArrayList<RespuestaCuestionario>();
        listaRespuestas.add(mock(RespuestaCuestionario.class));
        when(respuestaRepository.save(listaRespuestas)).thenReturn(listaRespuestas);
        
        List<RespuestaCuestionario> respuesta = cuestionarioEnvioService.transaccSaveConRespuestas(listaRespuestas);
        
        verify(respuestaRepository, times(1)).save(listaRespuestas);
        verify(datosTablaRepository, times(1)).deleteRespuestasTablaHuerfanas();
        assertThat(respuesta).isEqualTo(listaRespuestas);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#transaccSaveElimUsuariosProv(CuestionarioEnvio)}.
     */
    @Test
    public void transaccSaveElimUsuariosProv() {
        CuestionarioEnvio cuestionario = CuestionarioEnvio.builder().id(1L).correoEnvio("correo@dominio.es").build();
        when(userService.exists(any(String.class))).thenReturn(Boolean.TRUE);
        when(userService.exists("correo9@dominio.es")).thenReturn(Boolean.FALSE);
        
        cuestionarioEnvioService.transaccSaveElimUsuariosProv(cuestionario);
        
        verify(cuestionarioEnvioRepository, times(1)).save(cuestionario);
        verify(userService, times(10)).exists(any(String.class));
        verify(userService, times(9)).delete(any(String.class));
        verify(areaUsuarioCuestEnvService, times(1)).deleteByIdCuestionarioEnviado(cuestionario.getId());
        verify(inspeccionesService, times(1)).cambiarEstado(cuestionario.getInspeccion(),
                EstadoInspeccionEnum.G_PENDIENTE_VISITA_INSPECCION);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#transaccSaveConRespuestasInactivaUsuariosProv(CuestionarioEnvio, List)}
     * .
     */
    @Test
    public void transaccSaveConRespuestasInactivaUsuariosProv() {
        String correoPrincipal = "correo@dominio.es";
        CuestionarioEnvio cuestionario = CuestionarioEnvio.builder().id(1L).correoEnvio(correoPrincipal).build();
        List<RespuestaCuestionario> listaRespuestas = new ArrayList<RespuestaCuestionario>();
        
        cuestionarioEnvioService.transaccSaveConRespuestasInactivaUsuariosProv(cuestionario, listaRespuestas);
        
        verify(respuestaRepository, times(1)).save(listaRespuestas);
        verify(datosTablaRepository, times(1)).deleteRespuestasTablaHuerfanas();
        verify(userService, times(1)).cambiarEstado(correoPrincipal, EstadoEnum.INACTIVO);
        verify(cuestionarioEnvioRepository, times(1)).save(cuestionario);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#transaccSaveActivaUsuariosProv(CuestionarioEnvio)}.
     */
    @Test
    public void transaccSaveActivaUsuariosProv() {
        String correoPrincipal = "correo@dominio.es";
        CuestionarioEnvio cuestionario = CuestionarioEnvio.builder().id(1L).correoEnvio(correoPrincipal).build();
        
        cuestionarioEnvioService.transaccSaveActivaUsuariosProv(cuestionario);
        
        verify(cuestionarioEnvioRepository, times(1)).save(cuestionario);
        verify(userService, times(1)).cambiarEstado(correoPrincipal, EstadoEnum.ACTIVO);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuestionarioEnvioService#findById(Long)}.
     */
    @Test
    public void findById() {
        Long idCuestionarioEnviado = 1L;
        
        cuestionarioEnvioService.findById(idCuestionarioEnviado);
        
        verify(cuestionarioEnvioRepository, times(1)).findOne(idCuestionarioEnviado);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#findNoFinalizadoPorInspeccion(Inspeccion)}.
     */
    @Test
    public void findNoFinalizadoPorInspeccion() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        
        cuestionarioEnvioService.findNoFinalizadoPorInspeccion(inspeccion);
        
        verify(cuestionarioEnvioRepository, times(1))
                .findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndInspeccion(inspeccion);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#existsByCuestionarioPersonalizado(CuestionarioPersonalizado)}
     * .
     */
    @Test
    public void existsByCuestionarioPersonalizado() {
        CuestionarioPersonalizado cuestionario = mock(CuestionarioPersonalizado.class);
        
        cuestionarioEnvioService.existsByCuestionarioPersonalizado(cuestionario);
        
        verify(cuestionarioEnvioRepository, times(1)).existsByCuestionarioPersonalizado(cuestionario);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuestionarioEnvioService#findNoCumplimentados()}.
     */
    @Test
    public void findNoCumplimentados() {
        cuestionarioEnvioService.findNoCumplimentados();
        
        verify(cuestionarioEnvioRepository, times(1))
                .findByFechaAnulacionIsNullAndFechaFinalizacionIsNullAndFechaCumplimentacionIsNull();
    }
    
}
