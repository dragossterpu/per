
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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.repositories.IModeloCuestionarioRepository;

/**
 * 
 * Test del servicio Modelo Cuestionario.
 *
 * @author EZENTIS
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityContextHolder.class)
public class ModeloCuestionarioServiceTest {
    /**
     * Simulación del repositorio de modelos de cuestionario.
     */
    @Mock
    private IModeloCuestionarioRepository modeloCuestionarioRepository;
    
    /**
     * Servicio de modelos de cuestionario.
     */
    @InjectMocks
    private IModeloCuestionarioService modeloCuestionarioService = new ModeloCuestionarioService();
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
    }
    
    /**
     * Comprobación de que la clase existe.
     */
    @Test
    public void type() {
        assertThat(ModeloCuestionarioService.class).isNotNull();
    }
    
    /**
     * Comprobación de que la clase no es abstracta.
     */
    @Test
    public void instantiation() {
        ModeloCuestionarioService target = new ModeloCuestionarioService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.ModeloCuestionarioService#save(ModeloCuestionario)}.
     */
    @Test
    public void save() {
        ModeloCuestionario modeloCuestionario = mock(ModeloCuestionario.class);
        modeloCuestionarioService.save(modeloCuestionario);
        verify(modeloCuestionarioRepository, times(1)).save(modeloCuestionario);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.ModeloCuestionarioService#findAll()}.
     */
    @Test
    public void findAllByFechaBajaIsNull() {
        modeloCuestionarioService.findAllByFechaBajaIsNull();
        verify(modeloCuestionarioRepository, times(1)).findAllByFechaBajaIsNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.ModeloCuestionarioService#findOne(Integer)}.
     */
    @Test
    public void findOne() {
        modeloCuestionarioService.findOne(2);
        verify(modeloCuestionarioRepository, times(1)).findOne(2);
    }
    
}
