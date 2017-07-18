/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.repositories.ICuestionarioPersonalizadoRepository;

/**
 * 
 * Test del servicio Modelo de cuestionario personalizado.
 *
 * @author EZENTIS
 */
@RunWith(MockitoJUnitRunner.class)
public class CuestionarioPersonalizadoServiceTest {
    /**
     * Simulación del repositorio de cuestionario personalizado.
     */
    @Mock
    private ICuestionarioPersonalizadoRepository cuestionarioPersonalizadoRepository;
    
    /**
     * Servicio de cuestionario personalizado.
     */
    @InjectMocks
    private ICuestionarioPersonalizadoService cuestionarioPersonalizadoService = new CuestionarioPersonalizadoService();
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
    }
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(CuestionarioPersonalizadoService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        CuestionarioPersonalizadoService target = new CuestionarioPersonalizadoService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioPersonalizadoService#delete(CuestionarioPersonalizado)}.
     */
    @Test
    public void delete() {
        CuestionarioPersonalizado cuestionario = mock(CuestionarioPersonalizado.class);
        
        cuestionarioPersonalizadoService.delete(cuestionario);
        verify(cuestionarioPersonalizadoRepository, times(1)).delete(cuestionario);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioPersonalizadoService#save(CuestionarioPersonalizado)}.
     */
    @Test
    public void save() {
        CuestionarioPersonalizado cuestionario = mock(CuestionarioPersonalizado.class);
        
        cuestionarioPersonalizadoService.save(cuestionario);
        verify(cuestionarioPersonalizadoRepository, times(1)).save(cuestionario);
    }
}
