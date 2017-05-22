/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.primefaces.model.SortOrder;

import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.repositories.ICuestionarioPersonalizadoRepository;
import es.mira.progesin.web.beans.cuestionarios.CuestionarioPersonalizadoBusqueda;

/**
 * @author EZENTIS
 * 
 * Test del servicio Modelo de cuestionario personalizado
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CuestionarioPersonalizadoServiceTest {
    
    @Mock
    ICuestionarioPersonalizadoRepository cuestionarioPersonalizadoRepository;
    
    @Mock
    private SessionFactory sessionFactory;
    
    @InjectMocks
    private ICuestionarioPersonalizadoService cuestionarioPersonalizadoService = new CuestionarioPersonalizadoService();
    
    /**
     * Configuración inicial del test
     */
    @Before
    public void setUp() {
    }
    
    /**
     * Comprobación clase existe
     */
    @Test
    public void type() {
        assertThat(CuestionarioPersonalizadoService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta
     */
    @Test
    public void instantiation() {
        CuestionarioPersonalizadoService target = new CuestionarioPersonalizadoService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioPersonalizadoService#delete(CuestionarioPersonalizado)}.
     */
    @Test
    public void delete() {
        CuestionarioPersonalizado cuestionario = mock(CuestionarioPersonalizado.class);
        
        cuestionarioPersonalizadoService.delete(cuestionario);
        
        verify(cuestionarioPersonalizadoRepository, times(1)).delete(cuestionario);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioPersonalizadoService#save(CuestionarioPersonalizado)}.
     */
    @Test
    public void save() {
        CuestionarioPersonalizado cuestionario = mock(CuestionarioPersonalizado.class);
        
        cuestionarioPersonalizadoService.save(cuestionario);
        
        verify(cuestionarioPersonalizadoRepository, times(1)).save(cuestionario);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioPersonalizadoService#buscarCuestionarioPersonalizadoCriteria(int, int, String, SortOrder, CuestionarioPersonalizadoBusqueda)}.
     */
    @Ignore
    @Test
    public void buscarCuestionarioPersonalizadoCriteria() {
        // int first = 0;
        // int pageSize = 0;
        // String sortField = null;
        // SortOrder sortOrder = mock(SortOrder.class);
        // CuestionarioPersonalizadoBusqueda cuestionarioBusqueda = mock(CuestionarioPersonalizadoBusqueda.class);
        //
        // cuestionarioPersonalizadoService.buscarCuestionarioPersonalizadoCriteria(first, pageSize, sortField,
        // sortOrder,
        // cuestionarioBusqueda);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.CuestionarioPersonalizadoService#getCountCuestionarioCriteria(CuestionarioPersonalizadoBusqueda)}.
     */
    @Ignore
    @Test
    public void getCountCuestionarioCriteria() {
        // CuestionarioPersonalizadoBusqueda cuestionarioBusqueda = mock(CuestionarioPersonalizadoBusqueda.class);
        //
        // cuestionarioPersonalizadoService.getCountCuestionarioCriteria(cuestionarioBusqueda);
    }
    
}
