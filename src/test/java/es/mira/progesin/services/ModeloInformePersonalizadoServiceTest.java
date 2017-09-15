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

import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.persistence.repositories.IModeloInformePersonalizadoRepository;

/**
 * 
 * Test del servicio Modelo de Informes.
 *
 * @author EZENTIS
 */
@PrepareForTest(SecurityContextHolder.class)
@PowerMockIgnore("javax.security.*")
@RunWith(PowerMockRunner.class)
public class ModeloInformePersonalizadoServiceTest {
    
    /**
     * Mock Repositorio de modelos de informes personalizados.
     */
    @Mock
    private IModeloInformePersonalizadoRepository informePersonalizadoRepositoy;
    
    /**
     * Mock de informes.
     */
    @Mock
    private IInformeService informeService;
    
    /**
     * Mock del registro de actividad.
     */
    @Mock
    private IRegistroActividadService registroActivadService;
    
    /**
     * Servicio de modelos de informes.
     */
    @InjectMocks
    private IModeloInformePersonalizadoService modeloInformePersonalizdoService = new ModeloInformePersonalizadoService();
    
    /**
     * Captor ModeloInformePersonalizado.
     */
    @Captor
    private ArgumentCaptor<ModeloInformePersonalizado> modeloInformePersonalizadoCaptor;
    
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
        assertThat(ModeloInformePersonalizadoService.class).isNotNull();
    }
    
    /**
     * Comprobación de que la clase no es abstracta.
     */
    @Test
    public void instantiation() {
        IModeloInformePersonalizadoService target = new ModeloInformePersonalizadoService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.ModeloInformePersonalizadoService#findModeloPersonalizadoCompleto(java.lang.Long)}.
     */
    @Test
    public final void testFindModeloPersonalizadoCompleto() {
        modeloInformePersonalizdoService.findModeloPersonalizadoCompleto(1L);
        verify(informePersonalizadoRepositoy, times(1)).findById(1L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.ModeloInformePersonalizadoService#guardarInformePersonalizado(java.lang.String, es.mira.progesin.persistence.entities.informes.ModeloInforme, java.util.Map)}.
     */
    @Test
    public final void testGuardarInformePersonalizado() {
        ModeloInforme modelo = new ModeloInforme();
        
        List<AreaInforme> areas = new ArrayList<>();
        AreaInforme area = new AreaInforme();
        areas.add(area);
        modelo.setAreas(areas);
        
        Map<AreaInforme, SubareaInforme[]> subareasSeleccionadas = new HashMap<>();
        SubareaInforme subArea = new SubareaInforme();
        SubareaInforme[] subareasInforme = new SubareaInforme[1];
        subareasInforme[0] = subArea;
        subareasSeleccionadas.put(area, subareasInforme);
        
        modeloInformePersonalizdoService.guardarInformePersonalizado("informeTest", modelo, subareasSeleccionadas);
        verify(informePersonalizadoRepositoy, times(1)).save(modeloInformePersonalizadoCaptor.capture());
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.ModeloInformePersonalizadoService#buscarInformePersonalizadoCriteria(int, int, java.lang.String, org.primefaces.model.SortOrder, es.mira.progesin.web.beans.informes.InformePersonalizadoBusqueda)}.
     */
    @Test
    public final void testBuscarInformePersonalizadoCriteria() {
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.ModeloInformePersonalizadoService#getCountInformePersonalizadoCriteria(es.mira.progesin.web.beans.informes.InformePersonalizadoBusqueda)}.
     */
    @Test
    public final void testGetCountInformePersonalizadoCriteria() {
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.ModeloInformePersonalizadoService#eliminarModeloPersonalizado(es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado)}.
     */
    @Test
    public final void testEliminarModeloPersonalizadoBajaLogica() {
        when(informeService.existsByModeloPersonalizado(any(ModeloInformePersonalizado.class))).thenReturn(true);
        modeloInformePersonalizdoService.eliminarModeloPersonalizado(new ModeloInformePersonalizado());
        verify(informePersonalizadoRepositoy, times(1)).save(modeloInformePersonalizadoCaptor.capture());
        assertThat(modeloInformePersonalizadoCaptor.getValue().getFechaBaja()).isNotNull();
        assertThat(modeloInformePersonalizadoCaptor.getValue().getUsernameBaja()).isEqualTo(USERLOGIN);
        verify(registroActivadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.INFORMES.getDescripcion()));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.ModeloInformePersonalizadoService#eliminarModeloPersonalizado(es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado)}.
     */
    @Test
    public final void testEliminarModeloPersonalizadoBajaFisica() {
        ModeloInformePersonalizado modeloPersonalizado = new ModeloInformePersonalizado();
        modeloPersonalizado.setId(1L);
        when(informeService.existsByModeloPersonalizado(modeloPersonalizado)).thenReturn(false);
        modeloInformePersonalizdoService.eliminarModeloPersonalizado(modeloPersonalizado);
        verify(informePersonalizadoRepositoy, times(1)).delete(modeloPersonalizado.getId());
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.ModeloInformePersonalizadoService#existsByModelo(es.mira.progesin.persistence.entities.informes.ModeloInforme)}.
     */
    @Test
    public final void testExistsByModelo() {
        ModeloInforme modelo = mock(ModeloInforme.class);
        modeloInformePersonalizdoService.existsByModelo(modelo);
        verify(informePersonalizadoRepositoy, times(1)).existsByModeloInforme(modelo);
    }
    
}
