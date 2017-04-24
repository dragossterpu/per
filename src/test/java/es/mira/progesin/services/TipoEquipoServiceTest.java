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

import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.persistence.repositories.ITipoEquiposRepository;

/**
 * @author EZENTIS
 * 
 * Test del servicio de TipoEquipo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TipoEquipoServiceTest {
    
    @Mock
    private ITipoEquiposRepository tipoEquipoRepository;
    
    @InjectMocks
    private TipoEquipoService tipoEquipoService;
    
    /**
     * Comprobación clase existe
     */
    @Test
    public void type() {
        assertThat(TipoEquipoService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta
     */
    @Test
    public void instantiation() {
        TipoEquipoService target = new TipoEquipoService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.TipoEquipoService#findAll()}.
     */
    @Test
    public void findAll() {
        tipoEquipoService.findAll();
        verify(tipoEquipoRepository, times(1)).findAll();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.TipoEquipoService#delete(Long)}.
     */
    @Test
    public void delete() {
        tipoEquipoService.delete(1L);
        verify(tipoEquipoRepository, times(1)).delete(1L);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.TipoEquipoService#save(TipoEquipo)}.
     */
    @Test
    public void save() {
        TipoEquipo tEquipo = mock(TipoEquipo.class);
        tipoEquipoService.save(tEquipo);
        verify(tipoEquipoRepository, times(1)).save(tEquipo);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.TipoEquipoService#findByCodigoIgnoreCase(String)}.
     */
    @Test
    public void findByCodigoIgnoreCase() {
        tipoEquipoService.findByCodigoIgnoreCase("codigo");
        verify(tipoEquipoRepository, times(1)).findByCodigoIgnoreCase("codigo");
    }
    
}
