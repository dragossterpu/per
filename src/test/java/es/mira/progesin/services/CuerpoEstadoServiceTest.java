package es.mira.progesin.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
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
 * @author EZENTIS
 * 
 * Test del servicio de cuerpos de estado
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class CuerpoEstadoServiceTest {
    
    @Mock
    private ICuerpoEstadoRepository repository;
    
    @InjectMocks
    private CuerpoEstadoService service = new CuerpoEstadoService();
    
    /**
     * Comprueba que la clase existe
     * @throws Exception
     */
    @Test
    public void type() throws Exception {
        assertThat(CuerpoEstadoService.class, notNullValue());
    }
    
    /**
     * Comprueba que la clase se puede instanciar
     * @throws Exception
     */
    @Test
    public void instantiation() throws Exception {
        CuerpoEstadoService target = new CuerpoEstadoService();
        assertThat(target, notNullValue());
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuerpoEstadoService#findAll()}.
     */
    @Test
    public void findAll() {
        service.findAll();
        verify(repository, times(1)).findAllByOrderByIdAsc();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuerpoEstadoService#save()}.
     */
    @Test
    public void save() {
        CuerpoEstado cuerpo = mock(CuerpoEstado.class);
        service.save(cuerpo);
        verify(repository, times(1)).save(cuerpo);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuerpoEstadoService#findByFechaBajaIsNull()}.
     */
    @Test
    public void findByFechaBajaIsNull() {
        service.findByFechaBajaIsNull();
        verify(repository, times(1)).findByFechaBajaIsNullOrderByIdAsc();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.CuerpoEstadoService#existeByNombreCortoIgnoreCaseAndIdNotIn()}.
     */
    @Test
    public void existeByNombreCortoIgnoreCaseAndIdNotIn() {
        String nombreCorto = null;
        int id = 0;
        boolean actual = service.existeByNombreCortoIgnoreCaseAndIdNotIn(nombreCorto, id);
        boolean expected = false;
        assertThat(actual, is(equalTo(expected)));
    }
    
}
