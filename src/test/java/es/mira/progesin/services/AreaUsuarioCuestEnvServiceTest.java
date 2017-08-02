/**
 * 
 */
package es.mira.progesin.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.entities.cuestionarios.AreaUsuarioCuestEnv;
import es.mira.progesin.persistence.repositories.IAreaUsuarioCuestEnvRepository;

/**
 * Test para el servicio de AreaUsuarioCuestEnvService.
 * @author EZENTIS
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AreaUsuarioCuestEnvServiceTest {
    
    /**
     * Mock para el Repositorio de áreas/usuario de cuestionarios enviados.
     */
    @Mock
    private IAreaUsuarioCuestEnvRepository areaUsuarioCuestEnvRepository;
    
    /**
     * Instancia de prueba del servicio de AreaUsuarioCuestEnvService.
     */
    @InjectMocks
    private IAreaUsuarioCuestEnvService areaUsuarioCuestEnvService = new AreaUsuarioCuestEnvService();
    
    /**
     * Test method for {@link es.mira.progesin.services.AreaUsuarioCuestEnvService#save(java.util.List)}.
     */
    @Test
    public final void testSave() {
        List<AreaUsuarioCuestEnv> listaAreasUsuarioCuestEnv = new ArrayList<>();
        listaAreasUsuarioCuestEnv.add(mock(AreaUsuarioCuestEnv.class));
        areaUsuarioCuestEnvService.save(listaAreasUsuarioCuestEnv);
        verify(areaUsuarioCuestEnvRepository, times(1)).save(listaAreasUsuarioCuestEnv);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AreaUsuarioCuestEnvService#findByIdCuestionarioEnviado(java.lang.Long)}.
     */
    @Test
    public final void testFindByIdCuestionarioEnviado() {
        areaUsuarioCuestEnvService.findByIdCuestionarioEnviado(1L);
        verify(areaUsuarioCuestEnvRepository, times(1)).findByIdCuestionarioEnviado(1L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AreaUsuarioCuestEnvService#findByIdCuestionarioEnviadoAndUsuarioProv(java.lang.Long, java.lang.String)}.
     */
    @Test
    public final void testFindByIdCuestionarioEnviadoAndUsuarioProv() {
        String userName = "userTest";
        areaUsuarioCuestEnvService.findByIdCuestionarioEnviadoAndUsuarioProv(1L, userName);
        verify(areaUsuarioCuestEnvRepository, times(1)).findByIdCuestionarioEnviadoAndUsernameProv(1L, userName);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AreaUsuarioCuestEnvService#deleteByIdCuestionarioEnviado(java.lang.Long)}.
     */
    @Test
    public final void testDeleteByIdCuestionarioEnviado() {
        areaUsuarioCuestEnvService.deleteByIdCuestionarioEnviado(1L);
        verify(areaUsuarioCuestEnvRepository, times(1)).deleteByIdCuestionarioEnviado(1L);
    }
    
}
