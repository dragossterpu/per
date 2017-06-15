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
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * Test del servicio de TipoEquipo.
 * 
 * @author EZENTIS
 */
@RunWith(MockitoJUnitRunner.class)
public class TipoEquipoServiceTest {
    
    /**
     * Mock del repositorio de equipos.
     */
    @Mock
    private ITipoEquiposRepository tipoEquipoRepository;
    
    /**
     * Mock del application bean.
     */
    @Mock
    private ApplicationBean applicationBean;
    
    /**
     * Instancia de prueba del servicio de tipos de equipo.
     */
    @InjectMocks
    private ITipoEquipoService tipoEquipoService = new TipoEquipoService();
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(TipoEquipoService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
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
        verify(tipoEquipoRepository, times(1)).findAllByOrderByIdAsc();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.TipoEquipoService#delete(TipoEquipo)}.
     */
    @Test
    public void delete() {
        TipoEquipo tEquipo = mock(TipoEquipo.class);
        tipoEquipoService.delete(tEquipo);
        verify(tipoEquipoRepository, times(1)).delete(tEquipo);
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
