package es.mira.progesin.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import es.mira.progesin.services.ITipoInspeccionService;

/**
 * Test del validador de codigo tipo inspeccion.
 * 
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
public class CodigoTipoInspeccionValidatorTest {
    /**
     * Simulación del servicio de tipo de inspeccion.
     */
    @Mock
    private ITipoInspeccionService servicio;
    
    /**
     * Validador de codigo de tipo de inspeccion.
     */
    @InjectMocks
    private CodigoTipoInspeccionValidator validador;
    
    /**
     * Test method for
     * {@link es.mira.progesin.util.CodigoTipoInspeccionValidator#validate(FacesContext, UIComponent, Object)}.
     */
    
    @Test
    public final void validate() {
        FacesContext context = mock(FacesContext.class);
        UIComponent component = mock(UIComponent.class);
        Object value = new String();
        
        when(servicio.existeByCodigoIgnoreCase(value.toString())).thenReturn(false);
        
        validador.validate(context, component, value);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.util.CodigoTipoInspeccionValidator#validate(FacesContext, UIComponent, Object)}.
     */
    @Test(expected = ValidatorException.class)
    public void validate_false() {
        FacesContext context = mock(FacesContext.class);
        UIComponent component = mock(UIComponent.class);
        Object value = new String();
        
        when(servicio.existeByCodigoIgnoreCase(value.toString())).thenReturn(true);
        
        validador.validate(context, component, value);
        
    }
}
