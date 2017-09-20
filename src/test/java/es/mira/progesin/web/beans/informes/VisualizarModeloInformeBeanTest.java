/**
 * 
 */
package es.mira.progesin.web.beans.informes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.services.IModeloInformeService;

/**
 * 
 * Test del bean VisualizarModeloInformeBean.
 *
 * @author EZENTIS
 */

@RunWith(MockitoJUnitRunner.class)
public class VisualizarModeloInformeBeanTest {
    
    /**
     * Servicio de modelos de informe.
     */
    @Mock
    private IModeloInformeService modeloInformeService;
    
    /**
     * Bean de ModeloInformePersonalizado.
     */
    @InjectMocks
    private VisualizarModeloInformeBean visualizarModeloInformeBean;
    
    /**
     * Comprueba que la clase existe.
     */
    @Test
    public void type() {
        assertThat(ModeloInformeBean.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     */
    @Test
    public void instantiation() {
        ModeloInformeBean target = new ModeloInformeBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.VisualizarModeloInformeBean#visualizarModelo()}.
     */
    @Test
    public final void testVisualizarModelo() {
        visualizarModeloInformeBean.setIdModelo(1L);
        ModeloInforme modelo = new ModeloInforme();
        List<AreaInforme> areas = new ArrayList<>();
        modelo.setAreas(new ArrayList<>());
        when(modeloInformeService.visualizarModelo(1L)).thenReturn(modelo);
        
        visualizarModeloInformeBean.visualizarModelo();
        
        verify(modeloInformeService, times(1)).visualizarModelo(1L);
        verify(modeloInformeService, times(1)).cargarMapaSubareas(areas);
    }
    
}
