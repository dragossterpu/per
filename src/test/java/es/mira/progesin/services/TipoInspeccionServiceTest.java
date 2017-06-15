package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.repositories.ITipoInspeccionRepository;
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * Test de la implementación del servicio para la gestión de tipos de inspección.
 * 
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
public class TipoInspeccionServiceTest {
    /**
     * Simulación del repositorio de tipos de inspeccion.
     */
    @Mock
    private ITipoInspeccionRepository tipoInspeccionRepository;
    
    /**
     * Mock del application bean.
     */
    @Mock
    private ApplicationBean applicationBean;
    
    /**
     * Servicio de tipos de inspección.
     */
    @InjectMocks
    private transient ITipoInspeccionService tipoInspeccionService = new TipoInspeccionService();
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(TipoInspeccionService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        TipoInspeccionService target = new TipoInspeccionService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.TipoInspeccionService#borrarTipo(TipoInspeccion)}.
     */
    @Test
    public void borrarTipo() {
        TipoInspeccion tipo = mock(TipoInspeccion.class);
        tipoInspeccionService.borrarTipo(tipo);
        verify(tipoInspeccionRepository, times(1)).delete(tipo);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.TipoInspeccionService#guardarTipo(TipoInspeccion)}.
     */
    @Test
    public void guardarTipo() {
        TipoInspeccion tipo = mock(TipoInspeccion.class);
        tipoInspeccionService.guardarTipo(tipo);
        verify(tipoInspeccionRepository, times(1)).save(tipo);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.TipoInspeccionService#existeByCodigoIgnoreCase(String)}.
     */
    @Test
    public void existeByCodigoIgnoreCase() {
        tipoInspeccionService.existeByCodigoIgnoreCase("test");
        verify(tipoInspeccionRepository, times(1)).existsByCodigoIgnoreCase("test");
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.TipoInspeccionService#buscaTodos()}.
     */
    @Test
    public void buscaTodos() {
        tipoInspeccionService.buscaTodos();
        verify(tipoInspeccionRepository, times(1)).findAllByOrderByDescripcionAsc();
    }
    
}
