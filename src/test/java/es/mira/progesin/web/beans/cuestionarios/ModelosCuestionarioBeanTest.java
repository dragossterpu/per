/**
 * 
 */
package es.mira.progesin.web.beans.cuestionarios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.util.Utilities;
import es.mira.progesin.web.beans.GuiaBean;

/**
 * 
 * Test del bean Modelos de Cuestionario.
 *
 * @author EZENTIS
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ Utilities.class })
public class ModelosCuestionarioBeanTest {
    
    /**
     * Simulación del servicio de modelos de cuestionario.
     */
    @Mock
    private IModeloCuestionarioService modeloCuestionarioService;
    
    /**
     * Instancia de prueba del bean de modelos de cuestionario.
     */
    @InjectMocks
    private ModelosCuestionarioBean modelosCuestionarioBean;
    
    /**
     * Comprueba que la clase existe.
     */
    @Test
    public void type() {
        assertThat(GuiaBean.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     */
    @Test
    public void instantiation() {
        GuiaBean target = new GuiaBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Inicializa el test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Utilities.class);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ModelosCuestionarioBean#init()}.
     */
    @Test
    public final void testInit() {
        ModeloCuestionario modelo = mock(ModeloCuestionario.class);
        List<ModeloCuestionario> listadoCuestionarios = new ArrayList<>();
        listadoCuestionarios.add(modelo);
        when(modeloCuestionarioService.findAllByFechaBajaIsNull()).thenReturn(listadoCuestionarios);
        modelosCuestionarioBean.setListadoCuestionarios(new ArrayList<>());
        modelosCuestionarioBean.init();
        
        verify(modeloCuestionarioService, times(1)).findAllByFechaBajaIsNull();
        assertThat(modelosCuestionarioBean.getListadoCuestionarios()).hasSize(1);
    }
    
}
