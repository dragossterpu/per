package es.mira.progesin.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import es.mira.progesin.services.ICuerpoEstadoService;

/**
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
public class CuerpoEstadoValidatorTest {
    
    @Mock
    private transient ICuerpoEstadoService cuerposEstadoService;
    
    @InjectMocks
    private CuerpoEstadoValidator validador;
    
    /**
     * Test method for {@link es.mira.progesin.util.CuerpoEstadoValidator#validate()}.
     */
    @Test(expected = Exception.class)
    public void validate() {
        
        FacesContext context = mock(FacesContext.class);
        UIComponent component = mock(UIComponent.class);
        Object value = mock(Map.class);
        @SuppressWarnings("unchecked")
        
        Map<String, Object> mapa = mock(Map.class);
        
        when(component.getAttributes()).thenReturn(mapa);
        when(mapa.get("actualNombreCorto")).thenReturn(1);
        when(value.toString()).thenReturn("Test");
        when(cuerposEstadoService.existeByNombreCortoIgnoreCaseAndIdNotIn("Test", 1)).thenReturn(true);
        
        validador.validate(context, component, value);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.util.CuerpoEstadoValidator#validate()}.
     */
    @Test
    public void validate_false() {
        
        FacesContext context = mock(FacesContext.class);
        UIComponent component = mock(UIComponent.class);
        Object value = mock(Map.class);
        @SuppressWarnings("unchecked")
        
        Map<String, Object> mapa = mock(Map.class);
        
        when(component.getAttributes()).thenReturn(mapa);
        when(mapa.get("actualNombreCorto")).thenReturn(1);
        when(value.toString()).thenReturn("Test");
        when(cuerposEstadoService.existeByNombreCortoIgnoreCaseAndIdNotIn("Test", 1)).thenReturn(false);
        
        validador.validate(context, component, value);
        
    }
}
