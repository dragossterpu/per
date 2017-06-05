/**
 * 
 */
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

import es.mira.progesin.persistence.entities.Sugerencia;
import es.mira.progesin.persistence.repositories.ISugerenciaRepository;

/**
 * Test del servicio Sugerencias.
 * 
 * @author EZENTIS
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class SugerenciaServiceTest {
    
    /**
     * Mock del repositorio de sugerencias.
     */
    @Mock
    private ISugerenciaRepository sugerenciaRepositoryMock;
    
    /**
     * Instancia de prueba del servicio de equipos.
     */
    @InjectMocks
    private ISugerenciaService sugerenciaService = new SugerenciaService();
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(SugerenciaService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        SugerenciaService target = new SugerenciaService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.SugerenciaService#delete(java.lang.Integer)}.
     */
    @Test
    public void testDelete() {
        sugerenciaService.delete(2);
        verify(sugerenciaRepositoryMock, times(1)).delete(2);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.SugerenciaService#findAll()}.
     */
    @Test
    public void testFindAll() {
        sugerenciaService.findAll();
        verify(sugerenciaRepositoryMock, times(1)).findAll();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.SugerenciaService#findOne(java.lang.Integer)}.
     */
    @Test
    public void testFindOne() {
        sugerenciaService.findOne(3);
        verify(sugerenciaRepositoryMock, times(1)).findOne(3);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.SugerenciaService#save(es.mira.progesin.persistence.entities.Sugerencia)}.
     */
    @Test
    public void testSave() {
        Sugerencia sugerencia = mock(Sugerencia.class);
        sugerenciaService.save(sugerencia);
        verify(sugerenciaRepositoryMock, times(1)).save(sugerencia);
    }
    
}
