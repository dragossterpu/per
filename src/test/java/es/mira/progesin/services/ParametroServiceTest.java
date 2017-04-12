/**
 * 
 */
package es.mira.progesin.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import es.mira.progesin.persistence.entities.Parametro;
import es.mira.progesin.persistence.entities.ParametroId;
import es.mira.progesin.persistence.repositories.IParametrosRepository;

/**
 * @author EZENTIS
 * 
 * Test del servicio Parametros
 *
 */
@RunWith(SpringRunner.class)
public class ParametroServiceTest {
    
    private IParametrosRepository paramRepository;
    
    private ParametroService target;
    
    /**
     * Configurar test
     */
    @Before
    public void setUp() {
        paramRepository = Mockito.mock(IParametrosRepository.class);
        target = new ParametroService(paramRepository);
        
        List<String> secciones = new ArrayList<>();
        secciones.add("extensiones");
        
        List<Parametro> parametros = new ArrayList<>();
        parametros.add(new Parametro(new ParametroId("extensiones", "DOC", "application/msword")));
        parametros.add(new Parametro(new ParametroId("extensiones", "BMP", "image/bmp")));
        
        when(paramRepository.findSecciones()).thenReturn(secciones);
        when(paramRepository.findParamByParamSeccion("extensiones")).thenReturn(parametros);
    }
    
    /**
     * Comprobación clase existe
     */
    @Test
    public void type() {
        assertThat(ParametroService.class, notNullValue());
    }
    
    /**
     * Comprobación clase no abstracta
     */
    @Test
    public void instantiation() {
        ParametroService target = new ParametroService(null);
        assertThat(target, notNullValue());
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.ParametroService#getMapaParametros()}.
     */
    @Test
    public void getMapaParametros() {
        
        Map<String, Map<String, String>> actual = target.getMapaParametros();
        
        Map<String, String> paramsSeccion = new HashMap<>();
        paramsSeccion.put("DOC", "application/msword");
        paramsSeccion.put("BMP", "image/bmp");
        
        Map<String, Map<String, String>> expected = new HashMap<>();
        expected.put("extensiones", paramsSeccion);
        
        assertThat(actual, is(equalTo(expected)));
    }
    
}
