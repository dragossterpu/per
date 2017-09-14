
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.repositories.IModeloCuestionarioRepository;

/**
 * 
 * Test del servicio Modelo Cuestionario.
 *
 * @author EZENTIS
 */
@RunWith(MockitoJUnitRunner.class)
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
     * Test method for {@link es.mira.progesin.services.ModeloCuestionarioService#findAllByFechaBajaIsNull()}.
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
