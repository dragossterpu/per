package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.entities.Parametro;
import es.mira.progesin.persistence.entities.ParametroId;
import es.mira.progesin.persistence.repositories.IParametrosRepository;

/**
 * 
 * Test del servicio Parametros.
 *
 * @author EZENTIS
 */
@RunWith(MockitoJUnitRunner.class)
public class ParametroServiceTest {
    
    /**
     * Literal para la clave del mapa de parámetros.
     */
    private static final String EXTENSIONES = "extensiones";
    
    /**
     * Simulación del repositorio de parámetros.
     */
    @Mock
    private IParametrosRepository paramRepository;
    
    /**
     * Servicio de parámetros.
     */
    @InjectMocks
    private IParametroService parametroService = new ParametroService();
    
    /**
     * Comprobación de que la clase existe.
     */
    @Test
    public void type() {
        assertThat(ParametroService.class).isNotNull();
    }
    
    /**
     * Comprobación de que la clase no es abstracta.
     */
    @Test
    public void instantiation() {
        ParametroService target = new ParametroService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.ParametroService#getMapaParametros()}.
     */
    @Test
    public void getMapaParametros() {
        
        List<String> secciones = new ArrayList<>();
        secciones.add(EXTENSIONES);
        
        List<Parametro> parametros = new ArrayList<>();
        parametros.add(new Parametro(new ParametroId(EXTENSIONES, "DOC", "application/msword")));
        parametros.add(new Parametro(new ParametroId(EXTENSIONES, "BMP", "image/bmp")));
        
        when(paramRepository.findSecciones()).thenReturn(secciones);
        when(paramRepository.findParamByParamSeccion(EXTENSIONES)).thenReturn(parametros);
        
        Map<String, Map<String, String>> actual = parametroService.getMapaParametros();
        
        Map<String, String> paramsSeccion = new HashMap<>();
        paramsSeccion.put("DOC", "application/msword");
        paramsSeccion.put("BMP", "image/bmp");
        
        Map<String, Map<String, String>> expected = new HashMap<>();
        expected.put(EXTENSIONES, paramsSeccion);
        
        assertThat(actual).isEqualTo(expected);
    }
    
}
