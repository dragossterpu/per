/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.repositories.IGuiasPasosRepository;
import es.mira.progesin.persistence.repositories.IGuiasRepository;

/**
 * Test para el servicio de guías.
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ SecurityContextHolder.class })
public class GuiaServiceTest {
    
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
     * Mock para el repositorio de pasos de guía.
     */
    @Mock
    private IGuiasPasosRepository pasosRepository;
    
    /**
     * Mock para el repositorio de guías.
     */
    @Mock
    private IGuiasRepository guiaRepository;
    
    /**
     * Instancia de prueba para el servicio de guías.
     */
    @InjectMocks
    private IGuiaService guiaService = new GuiaService();
    
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
        when(authentication.getName()).thenReturn("usuarioLogueado");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.GuiaService#listaPasos(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testListaPasos() {
        Guia guia = mock(Guia.class);
        guiaService.listaPasos(guia);
        verify(pasosRepository).findByIdGuiaOrderByOrdenAsc(guia);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.GuiaService#listaPasosNoNull(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testListaPasosNoNull() {
        Guia guia = mock(Guia.class);
        guiaService.listaPasosNoNull(guia);
        verify(pasosRepository).findByIdGuiaAndFechaBajaIsNullOrderByOrdenAsc(guia);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.GuiaService#guardaGuia(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testGuardaGuia() {
        Guia guia = mock(Guia.class);
        guiaService.guardaGuia(guia);
        verify(guiaRepository).save(guia);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.GuiaService#existePaso(es.mira.progesin.persistence.entities.GuiaPasos)}.
     */
    @Test
    public final void testExistePaso() {
        GuiaPasos paso = new GuiaPasos();
        paso.setId(2L);
        guiaService.existePaso(paso);
        verify(pasosRepository).findPasoExistenteEnGuiasPersonalizadas(paso.getId());
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.GuiaService#eliminar(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testEliminar() {
        Guia guia = mock(Guia.class);
        guiaService.eliminar(guia);
        verify(guiaRepository).delete(guia);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.GuiaService#existeByTipoInspeccion(es.mira.progesin.persistence.entities.TipoInspeccion)}.
     */
    @Test
    public final void testExisteByTipoInspeccion() {
        TipoInspeccion tipoInspeccion = mock(TipoInspeccion.class);
        guiaService.existeByTipoInspeccion(tipoInspeccion);
        verify(guiaRepository).existsByTipoInspeccion(tipoInspeccion);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.GuiaService#findOne(java.lang.Long)}.
     */
    @Test
    public final void testFindOne() {
        guiaService.findOne(2L);
        verify(guiaRepository).findOne(2L);
        
    }
    
}
