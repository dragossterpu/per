/**
 * 
 */
package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DynamicMenuModel;

/**
 * Test para NavegacionBean.
 * @author EZENTIS
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class NavegacionBeanTest {
    /**
     * Simulación de NavegacionBean.
     */
    @InjectMocks
    private NavegacionBean navegacionBean;
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.NavegacionBean#init()}.
     */
    @Test
    public final void testInit() {
        navegacionBean.init();
        DefaultMenuItem defaultMenuItem = (DefaultMenuItem) navegacionBean.getCaminoMigas().getElements().get(0);
        assertThat(defaultMenuItem.getIcon()).isEqualTo("ui-icon-home");
        assertThat(defaultMenuItem.getUrl()).isEqualTo("/index.xhtml");
        assertThat(defaultMenuItem.getTitle()).isEqualTo("Menú Principal");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.NavegacionBean#iniciarCamino()}.
     */
    @Test
    public final void testIniciarCamino() {
        navegacionBean.iniciarCamino();
        DefaultMenuItem defaultMenuItem = (DefaultMenuItem) navegacionBean.getCaminoMigas().getElements().get(0);
        assertThat(defaultMenuItem.getIcon()).isEqualTo("ui-icon-home");
        assertThat(defaultMenuItem.getUrl()).isEqualTo("/index.xhtml");
        assertThat(defaultMenuItem.getTitle()).isEqualTo("Menú Principal");
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.NavegacionBean#adelante(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testAdelante() {
        DefaultMenuItem inicio = new DefaultMenuItem();
        inicio.setUrl("/prueba1.xhtml");
        inicio.setIcon("ui-icon1-home");
        inicio.setTitle("Menú Prueba1");
        DynamicMenuModel caminoMigas = new DynamicMenuModel();
        caminoMigas.addElement(inicio);
        navegacionBean.setCaminoMigas(caminoMigas);
        
        navegacionBean.adelante("aaa", "prueba2.xhtml");
        DefaultMenuItem defaultMenuItem = (DefaultMenuItem) navegacionBean.getCaminoMigas().getElements().get(1);
        
        assertThat(defaultMenuItem.getUrl()).isEqualTo("prueba2.xhtml");
        assertThat(defaultMenuItem.getValue()).isEqualTo("aaa");
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.NavegacionBean#recomenzar(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testRecomenzar() {
        navegacionBean.recomenzar("bbb", "prueba3.xhtml");
        DefaultMenuItem defaultMenuItem = (DefaultMenuItem) navegacionBean.getCaminoMigas().getElements().get(0);
        
        assertThat(defaultMenuItem.getIcon()).isEqualTo("ui-icon-home");
        assertThat(defaultMenuItem.getUrl()).isEqualTo("/index.xhtml");
        assertThat(defaultMenuItem.getTitle()).isEqualTo("Menú Principal");
        
        DefaultMenuItem defaultMenuItem2 = (DefaultMenuItem) navegacionBean.getCaminoMigas().getElements().get(1);
        
        assertThat(defaultMenuItem2.getUrl()).isEqualTo("prueba3.xhtml");
        assertThat(defaultMenuItem2.getValue()).isEqualTo("bbb");
    }
    
}
