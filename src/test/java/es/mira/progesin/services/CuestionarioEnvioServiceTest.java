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

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.model.SortOrder;
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
import es.mira.progesin.persistence.repositories.IAreaUsuarioCuestEnvRepository;
import es.mira.progesin.persistence.repositories.ICuestionarioEnvioRepository;
import es.mira.progesin.persistence.repositories.IDatosTablaGenericaRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IUserRepository;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBusqueda;

/**
 * @author EZENTIS
 * 
 * Test del servicio cuestionario enviado
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityContextHolder.class)
public class CuestionarioEnvioServiceTest {
    
    @Mock
    private SecurityContextHolder securityContextHolder;
    
    @Mock
    private SecurityContext securityContext;
    
    @Mock
    private Authentication authentication;
    
    @Mock
    private SessionFactory sessionFactory;
    
    @Mock
    private transient ICuestionarioEnvioRepository cuestionarioEnvioRepository;
    
    @Mock
    private transient IRespuestaCuestionarioRepository respuestaRepository;
    
    @Mock
    private transient IDatosTablaGenericaRepository datosTablaRepository;
    
    @Mock
    private transient IUserRepository userRepository;
    
    @Mock
    private transient IUserService userService;
    
    @Mock
    private transient IAreaUsuarioCuestEnvRepository areaUsuarioCuestEnvRepository;
    
    @Mock
    private transient ICorreoElectronico correoElectronico;
    
    @Mock
    private transient IPreguntaCuestionarioRepository preguntasRepository;
    
    @Mock
    private transient IAreaUsuarioCuestEnvService areaUsuarioCuestEnvService;
    
    @InjectMocks
    private ICuestionarioEnvioService cuestionarioEnvioService = new CuestionarioEnvioService();
    
    @Captor
    ArgumentCaptor<List<AreaUsuarioCuestEnv>> areasUsuarioCuestEnvCaptor;
    
    /**
     * Configuración inicial del test
     */
    @SuppressWarnings("static-access")
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SecurityContextHolder.class);
        
        when(securityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }
    
    /**
     * Comprobación clase existe
     */
    @Test
    public void type() {
        assertThat(CuestionarioEnvioService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta
     */
    @Test
    public void instantiation() {
        CuestionarioEnvioService target = new CuestionarioEnvioService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#crearYEnviarCuestionario(List, CuestionarioEnvio, String)}.
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
        
        verify(userRepository, times(1)).save(listadoUsuariosProvisionales);
        verify(cuestionarioEnvioRepository, times(1)).save(cuestionarioEnviado);
        verify(areaUsuarioCuestEnvRepository, times(1)).save(areasUsuarioCuestEnvCaptor.capture());
        assertThat(areasUsuarioCuestEnvCaptor.getValue()).hasSize(2);
        verify(correoElectronico, times(1)).envioCorreo(eq("correo"), any(String.class), any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#findNoFinalizadoPorCorreoEnvio(String)}.
     */
    @Ignore
    @Test
    public void findNoFinalizadoPorCorreoEnvio() {
        String correo = null;
        
        cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(correo);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#buscarCuestionarioEnviadoCriteria(int, int, String, SortOrder, CuestionarioEnviadoBusqueda)}.
     */
    @Ignore
    @Test
    public void buscarCuestionarioEnviadoCriteria() {
        int first = 0;
        int pageSize = 0;
        String sortField = null;
        SortOrder sortOrder = mock(SortOrder.class);
        CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda = mock(CuestionarioEnviadoBusqueda.class);
        
        cuestionarioEnvioService.buscarCuestionarioEnviadoCriteria(first, pageSize, sortField, sortOrder,
                cuestionarioEnviadoBusqueda);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#getCountCuestionarioCriteria(CuestionarioEnviadoBusqueda)}.
     */
    @Ignore
    @Test
    public void getCountCuestionarioCriteria() {
        CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda = mock(CuestionarioEnviadoBusqueda.class);
        
        cuestionarioEnvioService.getCountCuestionarioCriteria(cuestionarioEnviadoBusqueda);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuestionarioEnvioService#save(CuestionarioEnvio)}.
     */
    @Ignore
    @Test
    public void save() {
        CuestionarioEnvio cuestionario = mock(CuestionarioEnvio.class);
        
        cuestionarioEnvioService.save(cuestionario);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuestionarioEnvioService#transaccSaveConRespuestas(List)}.
     */
    @Ignore
    @Test
    public void transaccSaveConRespuestas() {
        List<RespuestaCuestionario> listaRespuestas = new ArrayList<RespuestaCuestionario>();
        
        cuestionarioEnvioService.transaccSaveConRespuestas(listaRespuestas);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#transaccSaveElimUsuariosProv(CuestionarioEnvio)}.
     */
    @Ignore
    @Test
    public void transaccSaveElimUsuariosProv() {
        CuestionarioEnvio cuestionario = mock(CuestionarioEnvio.class);
        
        cuestionarioEnvioService.transaccSaveElimUsuariosProv(cuestionario);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#transaccSaveConRespuestasInactivaUsuariosProv(CuestionarioEnvio, List)}.
     */
    @Ignore
    @Test
    public void transaccSaveConRespuestasInactivaUsuariosProv() {
        CuestionarioEnvio cuestionario = mock(CuestionarioEnvio.class);
        List<RespuestaCuestionario> listaRespuestas = new ArrayList<RespuestaCuestionario>();
        
        cuestionarioEnvioService.transaccSaveConRespuestasInactivaUsuariosProv(cuestionario, listaRespuestas);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#transaccSaveActivaUsuariosProv(CuestionarioEnvio)}.
     */
    @Ignore
    @Test
    public void transaccSaveActivaUsuariosProv() {
        CuestionarioEnvio cuestionario = mock(CuestionarioEnvio.class);
        
        cuestionarioEnvioService.transaccSaveActivaUsuariosProv(cuestionario);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuestionarioEnvioService#findById(Long)}.
     */
    @Ignore
    @Test
    public void findById() {
        Long idCuestionarioEnviado = null;
        
        cuestionarioEnvioService.findById(idCuestionarioEnviado);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#findNoFinalizadoPorInspeccion(Inspeccion)}.
     */
    @Ignore
    @Test
    public void findNoFinalizadoPorInspeccion() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        
        cuestionarioEnvioService.findNoFinalizadoPorInspeccion(inspeccion);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioEnvioService#existsByCuestionarioPersonalizado(CuestionarioPersonalizado)}.
     */
    @Ignore
    @Test
    public void existsByCuestionarioPersonalizado() {
        CuestionarioPersonalizado cuestionario = mock(CuestionarioPersonalizado.class);
        
        cuestionarioEnvioService.existsByCuestionarioPersonalizado(cuestionario);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuestionarioEnvioService#findNoCumplimentados()}.
     */
    @Ignore
    @Test
    public void findNoCumplimentados() {
        cuestionarioEnvioService.findNoCumplimentados();
    }
    
}
