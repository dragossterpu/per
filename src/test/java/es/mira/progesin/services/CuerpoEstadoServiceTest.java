package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.repositories.ICuerpoEstadoRepository;
import es.mira.progesin.web.beans.ApplicationBean;

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
     * Simulación del application bean.
     */
    @Mock
    private ApplicationBean applicationBean;
    
    /**
     * Servicio de cuerpo de estado.
     */
    @InjectMocks
    private ICuerpoEstadoService service = new CuerpoEstadoService();
    
    /**
     * Comprueba que la clase existe.
     * 
     */
    @Test
    public void type() {
        assertThat(CuerpoEstadoService.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     * 
     */
    @Test
    public void instantiation() {
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
        verify(applicationBean, times(1)).setListaCuerpos(ArgumentMatchers.anyList());
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
     * Test method for {@link es.mira.progesin.services.CuerpoEstadoService#delete(CuerpoEstado)}.
     */
    @Test
    public void delete() {
        CuerpoEstado cuerpo = mock(CuerpoEstado.class);
        service.delete(cuerpo);
        verify(repository, times(1)).delete(cuerpo);
        verify(applicationBean, times(1)).setListaCuerpos(ArgumentMatchers.anyList());
    }
    
}
