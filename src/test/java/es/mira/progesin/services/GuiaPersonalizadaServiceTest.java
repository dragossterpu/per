/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.repositories.IGuiaPersonalizadaRepository;
import es.mira.progesin.persistence.repositories.IGuiasPasosRepository;
import es.mira.progesin.persistence.repositories.IInspeccionesRepository;

/**
 * Test para el servicio de guías personalizadas.
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ SecurityContextHolder.class })
public class GuiaPersonalizadaServiceTest {
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
     * Repositorio de guías personalizadas.
     */
    
    @Mock
    private IGuiaPersonalizadaRepository guiaPersonalizadaRepository;
    
    /**
     * Repositorio de pasos de guía.
     */
    @Mock
    private IGuiasPasosRepository pasosRepository;
    
    /**
     * Repositorio de inspecciones.
     */
    @Mock
    private IInspeccionesRepository inspeccionRepository;
    
    /**
     * Instancia de prueba para el servicio de guías personalizadas.
     */
    @InjectMocks
    private IGuiaPersonalizadaService guiaPersonalizadaService = new GuiaPersonalizadaService();
    
    /**
     * Constante usuario login.
     */
    private static final String USUARIOLOGUEADO = "usuarioLogueado";
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(GuiaService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        GuiaService target = new GuiaService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USUARIOLOGUEADO);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.GuiaPersonalizadaService#eliminar(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     */
    @Test
    public final void testEliminar() {
        GuiaPersonalizada guia = mock(GuiaPersonalizada.class);
        guiaPersonalizadaService.eliminar(guia);
        verify(guiaPersonalizadaRepository).delete(guia);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.GuiaPersonalizadaService#save(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     */
    @Test
    public final void testSave() {
        GuiaPersonalizada guia = mock(GuiaPersonalizada.class);
        guiaPersonalizadaService.save(guia);
        verify(guiaPersonalizadaRepository).save(guia);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.GuiaPersonalizadaService#listaPasos(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     */
    @Test
    public final void testListaPasos() {
        GuiaPersonalizada guia = new GuiaPersonalizada();
        guia.setId(1L);
        guiaPersonalizadaService.listaPasos(guia);
        verify(pasosRepository).findPasosElegidosGuiaPersonalizada(guia.getId());
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.GuiaPersonalizadaService#anular(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     */
    @Test
    public final void testAnular() {
        GuiaPersonalizada guia = new GuiaPersonalizada();
        guia.setId(1L);
        guiaPersonalizadaService.anular(guia);
        assertThat(guia.getFechaAnulacion()).isNotNull();
        assertThat(guia.getUsernameAnulacion()).isEqualTo(USUARIOLOGUEADO);
        verify(guiaPersonalizadaRepository).save(guia);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.GuiaPersonalizadaService#buscarPorModeloGuia(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testBuscarPorModeloGuia() {
        Guia guia = mock(Guia.class);
        when(guiaPersonalizadaRepository.existsByGuia(guia)).thenReturn(true);
        boolean existe = guiaPersonalizadaService.buscarPorModeloGuia(guia);
        assertThat(existe).isTrue();
        verify(guiaPersonalizadaRepository).existsByGuia(guia);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.GuiaPersonalizadaService#listaInspecciones(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     */
    @Test
    public final void testListaInspecciones() {
        GuiaPersonalizada guiaPers = new GuiaPersonalizada();
        guiaPers.setId(1L);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(mock(Inspeccion.class));
        when(inspeccionRepository.cargaInspeccionesGuia(guiaPers.getId())).thenReturn(inspecciones);
        List<Inspeccion> insps = guiaPersonalizadaService.listaInspecciones(guiaPers);
        assertThat(insps).hasSize(1);
        verify(inspeccionRepository).cargaInspeccionesGuia(guiaPers.getId());
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.GuiaPersonalizadaService#findOne(java.lang.Long)}.
     */
    @Test
    public final void testFindOne() {
        guiaPersonalizadaService.findOne(1L);
        verify(guiaPersonalizadaRepository).findOne(1L);
        
    }
    
}
