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

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.repositories.ICuerpoEstadoRepository;

/**
 * 
 * Test del servicio de cuerpos de estado.
 * 
 * @author EZENTIS
 * 
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class CuerpoEstadoServiceTest {
    /**
     * Simulación de un repositorio de Cuerpo de estado.
     */
    @Mock
    private ICuerpoEstadoRepository repository;
    
    /**
     * Servicio de cuerpo de estado.
     */
    @InjectMocks
    private ICuerpoEstadoService service = new CuerpoEstadoService();
    
    /**
     * Comprueba que la clase existe.
     * 
     * @throws Exception Excepción lanzada
     */
    @Test
    public void type() throws Exception {
        assertThat(CuerpoEstadoService.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     * 
     * @throws Exception Excepción lanzada
     */
    @Test
    public void instantiation() throws Exception {
        CuerpoEstadoService target = new CuerpoEstadoService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuerpoEstadoService#findAll()}.
     */
    @Test
    public void findAll() {
        service.findAll();
        verify(repository, times(1)).findAll();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuerpoEstadoService#save(CuerpoEstado)}.
     */
    @Test
    public void save() {
        CuerpoEstado cuerpo = mock(CuerpoEstado.class);
        service.save(cuerpo);
        verify(repository, times(1)).save(cuerpo);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuerpoEstadoService#existeByNombreCortoIgnoreCaseAndIdNotIn(String, int)}.
     */
    @Test
    public void existeByNombreCortoIgnoreCaseAndIdNotIn() {
        String nombreCorto = null;
        int id = 0;
        boolean actual = service.existeByNombreCortoIgnoreCaseAndIdNotIn(nombreCorto, id);
        assertThat(actual).isEqualTo(false);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuerpoEstadoService#delete(Integer)}.
     */
    @Test
    public void delete() {
        service.delete(1);
        verify(repository, times(1)).delete(1);
    }
    
}
