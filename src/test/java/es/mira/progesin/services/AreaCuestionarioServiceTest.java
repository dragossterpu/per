/**
 * 
 */
package es.mira.progesin.services;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.repositories.IAreaCuestionarioRepository;

/**
 * Test para el servicio de AreaCuestionarioService.
 * @author EZENTIS
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AreaCuestionarioServiceTest {
    /**
     * Mock Repositorio de áreas de cuestionario.
     */
    @Mock
    private IAreaCuestionarioRepository areaRepository;
    
    /**
     * Instancia de prueba del servicio de AreaCuestionarioService.
     */
    @InjectMocks
    private IAreaCuestionarioService areaCuestionarioService = new AreaCuestionarioService();
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AreaCuestionarioService#findDistinctByIdCuestionarioOrderByOrdenAsc(java.lang.Integer)}.
     */
    @Test
    public final void testFindDistinctByIdCuestionarioOrderByOrdenAsc() {
        areaCuestionarioService.findDistinctByIdCuestionarioOrderByOrdenAsc(1);
        verify(areaRepository, timeout(1)).findDistinctByIdCuestionarioOrderByOrdenAsc(1);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.AreaCuestionarioService#findByIdIn(java.util.List)}.
     */
    @Test
    public final void testFindByIdIn() {
        List<Long> listaIdAreasElegidas = new ArrayList<>();
        listaIdAreasElegidas.add(1L);
        areaCuestionarioService.findByIdIn(listaIdAreasElegidas);
        verify(areaRepository, times(1)).findByIdIn(listaIdAreasElegidas);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AreaCuestionarioService#findAreaExistenteEnCuestionariosPersonalizados(java.lang.Long)}.
     */
    @Test
    public final void testFindAreaExistenteEnCuestionariosPersonalizados() {
        areaCuestionarioService.findAreaExistenteEnCuestionariosPersonalizados(1L);
        verify(areaRepository, times(1)).findAreaExistenteEnCuestionariosPersonalizados(1L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AreaCuestionarioService#findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(java.lang.Integer)}.
     */
    @Test
    public final void testFindDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc() {
        areaCuestionarioService.findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(1);
        verify(areaRepository, times(1)).findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(1);
    }
    
}
