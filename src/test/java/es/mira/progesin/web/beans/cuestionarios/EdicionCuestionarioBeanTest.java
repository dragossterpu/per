/**
 * 
 */
package es.mira.progesin.web.beans.cuestionarios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import org.primefaces.context.RequestContext;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.services.IAreaCuestionarioService;
import es.mira.progesin.services.ICuestionarioPersonalizadoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;

/**
 * 
 * Test del bean EdicionCuestionarioBean.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class, RequestContext.class })
public class EdicionCuestionarioBeanTest {
    /**
     * Constante user.
     */
    private static final String USUARIOLOGUEADO = "usuarioLogueado";
    
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
     * Simulación del RequestContext.
     */
    @Mock
    private RequestContext requestContext;
    
    /**
     * Mock de repositorio de areas de cuestionario.
     */
    @Mock
    private IAreaCuestionarioService areaCuestionarioService;
    
    /**
     * Mock de repositorio de preguntas de cuestionario.
     */
    @Mock
    private IPreguntaCuestionarioRepository pregunaCuestionarioRepository;
    
    /**
     * Mock de ICuestionarioPersonalizadoService.
     */
    @Mock
    private ICuestionarioPersonalizadoService cuestionarioPersonalizadoService;
    
    /**
     * Mock de IRegistroActividadService.
     */
    @Mock
    private IRegistroActividadService registroActividadService;
    
    /**
     * Simulacion de EdicionCuestionarioBean.
     */
    @InjectMocks
    private EdicionCuestionarioBean edicionCuestionarioBean;
    
    /**
     * Captor de tipo CuestionarioPersonalizado.
     */
    @Captor
    private ArgumentCaptor<CuestionarioPersonalizado> cuestionarioPersonalizadoCaptor;
    
    /**
     * Comprueba que la clase existe.
     */
    @Test
    public void type() {
        assertThat(EdicionCuestionarioBean.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     */
    @Test
    public void instantiation() {
        EdicionCuestionarioBean target = new EdicionCuestionarioBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Inicializa el test.
     */
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(RequestContext.class);
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USUARIOLOGUEADO);
        when(RequestContext.getCurrentInstance()).thenReturn(requestContext);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EdicionCuestionarioBean#editarCuestionario(es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario)}.
     */
    @Test
    public final void testEditarCuestionario() {
        ModeloCuestionario modCuestionario = new ModeloCuestionario();
        modCuestionario.setId(1);
        AreasCuestionario area = new AreasCuestionario();
        area.setId(1L);
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        when(areaCuestionarioService
                .findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(modCuestionario.getId()))
                        .thenReturn(areas);
        
        PreguntasCuestionario pregunta = mock(PreguntasCuestionario.class);
        List<PreguntasCuestionario> preguntas = new ArrayList<>();
        preguntas.add(pregunta);
        
        when(pregunaCuestionarioRepository.findByAreaAndFechaBajaIsNullOrderByOrdenAsc(area)).thenReturn(preguntas);
        
        String redireccion = edicionCuestionarioBean.editarCuestionario(modCuestionario);
        
        verify(areaCuestionarioService, times(1))
                .findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(modCuestionario.getId());
        verify(pregunaCuestionarioRepository, times(1)).findByAreaAndFechaBajaIsNullOrderByOrdenAsc(area);
        assertThat(edicionCuestionarioBean.getListaAreasCuestionario().get(0).getPreguntas().get(0))
                .isEqualTo(pregunta);
        assertThat(redireccion).isEqualTo("/cuestionarios/editarCuestionario?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EdicionCuestionarioBean#previsualizarFormulario()}.
     */
    @Test
    public final void testPrevisualizarFormulario() {
        AreasCuestionario area = new AreasCuestionario();
        area.setId(1L);
        area.setArea("area_test");
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        edicionCuestionarioBean.setListaAreasCuestionario(areas);
        
        PreguntasCuestionario pregunta = mock(PreguntasCuestionario.class);
        PreguntasCuestionario preguntas[] = { pregunta };
        
        Map<AreasCuestionario, PreguntasCuestionario[]> preguntasSelecciondas = new HashMap<>();
        preguntasSelecciondas.put(area, preguntas);
        
        edicionCuestionarioBean.setPreguntasSelecciondas(preguntasSelecciondas);
        
        String redireccion = edicionCuestionarioBean.previsualizarFormulario();
        assertThat(redireccion).isEqualTo("/cuestionarios/previsualizarCuestionario?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EdicionCuestionarioBean#previsualizarFormulario()}.
     */
    @Test
    public final void testPrevisualizarFormularioSinAreas() {
        edicionCuestionarioBean.setListaAreasCuestionario(new ArrayList<>());
        
        String redireccion = edicionCuestionarioBean.previsualizarFormulario();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                any(String.class));
        assertThat(redireccion).isNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EdicionCuestionarioBean#guardarFormulario(java.lang.String)}.
     */
    @Test
    public final void testGuardarFormulario() {
        String nombreCuestionario = "cuestionario_test";
        ModeloCuestionario modCuestionario = new ModeloCuestionario();
        modCuestionario.setId(1);
        modCuestionario.setDescripcion("modelo_test");
        edicionCuestionarioBean.setModeloCuestionario(modCuestionario);
        AreasCuestionario area = new AreasCuestionario();
        area.setId(1L);
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        edicionCuestionarioBean.setListaAreasCuestionario(areas);
        
        PreguntasCuestionario pregunta = mock(PreguntasCuestionario.class);
        PreguntasCuestionario preguntas[] = { pregunta };
        
        Map<AreasCuestionario, PreguntasCuestionario[]> preguntasSelecciondas = new HashMap<>();
        preguntasSelecciondas.put(area, preguntas);
        
        edicionCuestionarioBean.setPreguntasSelecciondas(preguntasSelecciondas);
        
        edicionCuestionarioBean.guardarFormulario(nombreCuestionario);
        verify(cuestionarioPersonalizadoService, times(1)).save(cuestionarioPersonalizadoCaptor.capture());
        registroActividadService.altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()));
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()), any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EdicionCuestionarioBean#guardarFormulario(java.lang.String)}.
     */
    @Test
    public final void testGuardarFormularioException() {
        String nombreCuestionario = "cuestionario_test";
        ModeloCuestionario modCuestionario = new ModeloCuestionario();
        modCuestionario.setId(1);
        edicionCuestionarioBean.setModeloCuestionario(modCuestionario);
        AreasCuestionario area = new AreasCuestionario();
        area.setId(1L);
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        edicionCuestionarioBean.setListaAreasCuestionario(areas);
        
        PreguntasCuestionario pregunta = mock(PreguntasCuestionario.class);
        PreguntasCuestionario preguntas[] = { pregunta };
        
        Map<AreasCuestionario, PreguntasCuestionario[]> preguntasSelecciondas = new HashMap<>();
        preguntasSelecciondas.put(area, preguntas);
        edicionCuestionarioBean.setPreguntasSelecciondas(preguntasSelecciondas);
        when(cuestionarioPersonalizadoService.save(cuestionarioPersonalizadoCaptor.capture()))
                .thenThrow(TransientDataAccessResourceException.class);
        
        edicionCuestionarioBean.guardarFormulario(nombreCuestionario);
        verify(cuestionarioPersonalizadoService, times(1)).save(cuestionarioPersonalizadoCaptor.capture());
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class));
        verify(registroActividadService, times(1)).altaRegActividadError(
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()), any(TransientDataAccessResourceException.class));
        
    }
    
}
