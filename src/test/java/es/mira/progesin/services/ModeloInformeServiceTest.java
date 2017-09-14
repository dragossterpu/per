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
import java.util.Map;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.persistence.repositories.IModeloInformeRepository;

/**
 * 
 * Test del servicio Modelo de Informes.
 *
 * @author EZENTIS
 */
@PrepareForTest(SecurityContextHolder.class)
@PowerMockIgnore("javax.security.*")
@RunWith(PowerMockRunner.class)
public class ModeloInformeServiceTest {
    /**
     * Variable utilizada para inyectar el repositorio de modelos de informe.
     * 
     */
    @Mock
    private IModeloInformeRepository modeloInformeRepository;
    
    /**
     * Servicio de áreas de informe.
     */
    @Mock
    private IAreaInformeService areaInformeService;
    
    /**
     * Servicio de subáreas de informe.
     */
    @Mock
    private SubareaInformeService subareaInformeService;
    
    /**
     * Servicio de modelos de informes personalizados.
     */
    @Mock
    private IModeloInformePersonalizadoService modeloInformePersonalizadoService;
    
    /**
     * Servicio de registr de actividad.
     */
    @Mock
    private IRegistroActividadService registroActividadService;
    
    /**
     * Servicio de modelos de informes.
     */
    @InjectMocks
    private IModeloInformeService modeloInformeService = new ModeloInformeService();
    
    /**
     * Literal user_login.
     */
    private static final String USERLOGIN = "user_login";
    
    /**
     * Inicializa el test.
     */
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SecurityContextHolder.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USERLOGIN);
    }
    
    /**
     * Comprobación de que la clase existe.
     */
    @Test
    public void type() {
        assertThat(ModeloInformeService.class).isNotNull();
    }
    
    /**
     * Comprobación de que la clase no es abstracta.
     */
    @Test
    public void instantiation() {
        IModeloInformeService target = new ModeloInformeService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.ModeloInformeService#findAll()}.
     */
    @Test
    public final void testFindAll() {
        modeloInformeService.findAll();
        verify(modeloInformeRepository, times(1)).findAll();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.ModeloInformeService#findDistinctById(java.lang.Long)}.
     */
    @Test
    public final void testFindDistinctById() {
        modeloInformeService.findDistinctById(1L);
        verify(modeloInformeRepository, times(1)).findDistinctById(1L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.ModeloInformeService#eliminarModelo(es.mira.progesin.persistence.entities.informes.ModeloInforme)}.
     */
    @Test
    public final void testEliminarModeloBajaLogica() {
        when(modeloInformePersonalizadoService.existsByModelo(any(ModeloInforme.class))).thenReturn(true);
        
        ArgumentCaptor<ModeloInforme> modeloCaptor = ArgumentCaptor.forClass(ModeloInforme.class);
        
        modeloInformeService.eliminarModelo(new ModeloInforme());
        verify(modeloInformeRepository, times(1)).save(modeloCaptor.capture());
        verify(registroActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.INFORMES.getDescripcion()));
        assertThat(modeloCaptor.getValue().getFechaBaja()).isNotNull();
        assertThat(modeloCaptor.getValue().getUsernameBaja()).isEqualTo(USERLOGIN);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.ModeloInformeService#eliminarModelo(es.mira.progesin.persistence.entities.informes.ModeloInforme)}.
     */
    @Test
    public final void testEliminarModeloBajaFisica() {
        ModeloInforme modeloInforme = new ModeloInforme();
        modeloInforme.setId(1L);
        when(modeloInformePersonalizadoService.existsByModelo(modeloInforme)).thenReturn(false);
        List<AreaInforme> areas = new ArrayList<>();
        when(areaInformeService.findByModeloInformeId(modeloInforme.getId())).thenReturn(areas);
        
        modeloInformeService.eliminarModelo(modeloInforme);
        
        verify(areaInformeService, times(1)).findByModeloInformeId(modeloInforme.getId());
        verify(subareaInformeService, times(1)).deleteByArea(areas);
        verify(areaInformeService, times(1)).deleteByModeloInformeId(modeloInforme.getId());
        verify(modeloInformeRepository, times(1)).delete(modeloInforme);
        verify(registroActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.INFORMES.getDescripcion()));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.ModeloInformeService#findAllByFechaBajaIsNull()}.
     */
    @Test
    public final void testFindAllByFechaBajaIsNull() {
        modeloInformeService.findAllByFechaBajaIsNull();
        verify(modeloInformeRepository, times(1)).findAllByFechaBajaIsNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.ModeloInformeService#visualizarModelo(es.mira.progesin.persistence.entities.informes.ModeloInforme)}.
     */
    @Test
    public final void testVisualizarModelo() {
        ModeloInforme modeloVisualizar = new ModeloInforme();
        modeloVisualizar.setId(1L);
        List<AreaInforme> areas = new ArrayList<>();
        AreaInforme areaInforme = new AreaInforme();
        areaInforme.setId(1L);
        areas.add(areaInforme);
        when(modeloInformeRepository.findOne(1L)).thenReturn(modeloVisualizar);
        when(areaInformeService.findByModeloInformeId(1L)).thenReturn(areas);
        
        ModeloInforme respuesta = modeloInformeService.visualizarModelo(modeloVisualizar);
        verify(modeloInformeRepository, times(1)).findOne(1L);
        assertThat(respuesta.getAreas().get(0).getId()).isEqualTo(1L);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.ModeloInformeService#cargarMapaSubareas(java.util.List)}.
     */
    @Test
    public final void testCargarMapaSubareas() {
        List<AreaInforme> areasVisualizar = new ArrayList<>();
        AreaInforme areaInforme = new AreaInforme();
        areaInforme.setId(1L);
        areasVisualizar.add(areaInforme);
        SubareaInforme subArea = mock(SubareaInforme.class);
        List<SubareaInforme> subAreas = new ArrayList<>();
        subAreas.add(subArea);
        when(subareaInformeService.findByArea(areaInforme)).thenReturn(subAreas);
        Map<AreaInforme, List<SubareaInforme>> mapa = modeloInformeService.cargarMapaSubareas(areasVisualizar);
        assertThat(mapa.get(areaInforme).get(0)).isEqualTo(subArea);
    }
    
}
