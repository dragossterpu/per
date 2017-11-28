/**
 * 
 */
package es.mira.progesin.web.beans.informes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.services.IModeloInformeService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.Utilities;

/**
 * 
 * Test del bean Modelo de Informe.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ Utilities.class, FacesUtilities.class })
public class ModeloInformeBeanTest {
    
    /**
     * Mock Servicio de modelos de informe.
     */
    @Mock
    private transient IModeloInformeService modeloInformeService;
    
    /**
     * Bean de Modelo Informe.
     */
    @InjectMocks
    private ModeloInformeBean modeloInformeBean;
    
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
     * Inicializa el test.
     */
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Utilities.class);
        PowerMockito.mockStatic(FacesUtilities.class);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.ModeloInformeBean#init()}.
     */
    @Test
    public final void testInit() {
        ModeloInforme modeloInforme2 = new ModeloInforme();
        modeloInforme2.setId(2L);
        ModeloInforme modeloInforme1 = new ModeloInforme();
        modeloInforme1.setId(1L);
        List<ModeloInforme> modelosInforme = new ArrayList<>();
        modelosInforme.add(modeloInforme2);
        modelosInforme.add(modeloInforme1);
        
        when(modeloInformeService.findAllByFechaBajaIsNull()).thenReturn(modelosInforme);
        modeloInformeBean.init();
        assertThat(modeloInformeBean.getListadoModelosInforme().get(0)).isEqualTo(modeloInforme1);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.ModeloInformeBean#eliminarModelo(es.mira.progesin.persistence.entities.informes.ModeloInforme)}.
     */
    @Test
    public final void testEliminarModeloBajaLogica() {
        ModeloInforme modelo = mock(ModeloInforme.class);
        modelo.setId(2L);
        List<ModeloInforme> modelosInforme = new ArrayList<>();
        modelosInforme.add(modelo);
        modeloInformeBean.setListadoModelosInforme(modelosInforme);
        when(modeloInformeService.eliminarModelo(modelo)).thenReturn(modelo);
        modeloInformeBean.eliminarModelo(modelo);
        assertThat(modeloInformeBean.getListadoModelosInforme().contains(modelo)).isFalse();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.ModeloInformeBean#eliminarModelo(es.mira.progesin.persistence.entities.informes.ModeloInforme)}.
     */
    @Test
    public final void testEliminarModelo() {
        ModeloInforme modelo = mock(ModeloInforme.class);
        List<ModeloInforme> modelosInforme = new ArrayList<>();
        modelosInforme.add(modelo);
        modeloInformeBean.setListadoModelosInforme(modelosInforme);
        when(modeloInformeService.eliminarModelo(modelo)).thenReturn(modelo);
        modeloInformeBean.eliminarModelo(modelo);
        assertThat(modeloInformeBean.getListadoModelosInforme().contains(modelo)).isFalse();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.ModeloInformeBean#eliminarModelo(es.mira.progesin.persistence.entities.informes.ModeloInforme)}.
     */
    @Test
    public final void testEliminarModeloException() {
        ModeloInforme modelo = mock(ModeloInforme.class);
        when(modeloInformeService.eliminarModelo(modelo)).thenReturn(null);
        modeloInformeBean.eliminarModelo(modelo);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), eq(Constantes.ERRORMENSAJE),
                any(String.class), eq(null));
    }
    
}
