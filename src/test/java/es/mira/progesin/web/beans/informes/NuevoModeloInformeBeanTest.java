/**
 * 
 */
package es.mira.progesin.web.beans.informes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.services.INuevoModeloInformeService;
import es.mira.progesin.util.FacesUtilities;

/**
 * 
 * Test del bean Modelo de Informe personalizado.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class })
public class NuevoModeloInformeBeanTest {
    
    /**
     * Mock Servicio de modelo de informe.
     */
    @Mock
    private INuevoModeloInformeService nuevoModeloInformeService;
    
    /**
     * Bean de NuevoModeloInformeBean.
     */
    @InjectMocks
    private NuevoModeloInformeBean nuevoModeloInformeBean;
    
    /**
     * Comprueba que la clase existe.
     */
    @Test
    public void type() {
        assertThat(NuevoModeloInformeBean.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     */
    @Test
    public void instantiation() {
        NuevoModeloInformeBean target = new NuevoModeloInformeBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Inicializa el test.
     */
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.NuevoModeloInformeBean#getFormNuevoModelo()}.
     */
    @Test
    public final void testGetFormNuevoModelo() {
        String ruta = nuevoModeloInformeBean.getFormNuevoModelo();
        assertThat(nuevoModeloInformeBean.getNuevoModelo().getEstandar()).isFalse();
        assertThat(nuevoModeloInformeBean.getListaAreas()).isNotNull();
        assertThat(ruta).isEqualTo("/informes/nuevoModeloInforme?faces-redirect=true");
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.NuevoModeloInformeBean#clonarInforme(es.mira.progesin.persistence.entities.informes.ModeloInforme)}.
     */
    @Test
    public final void testClonarInforme() {
        List<AreaInforme> areas = new ArrayList<>();
        AreaInforme area = mock(AreaInforme.class);
        areas.add(area);
        ModeloInforme modelo = mock(ModeloInforme.class);
        when(nuevoModeloInformeService.clonarListaAreas(modelo)).thenReturn(areas);
        String ruta = nuevoModeloInformeBean.clonarInforme(modelo);
        assertThat(nuevoModeloInformeBean.getNuevoModelo().getEstandar()).isFalse();
        assertThat(nuevoModeloInformeBean.getListaAreas().get(0)).isEqualTo(area);
        assertThat(ruta).isEqualTo("/informes/nuevoModeloInforme?faces-redirect=true");
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.NuevoModeloInformeBean#onFlowProcess(org.primefaces.event.FlowEvent)}.
     */
    @Test
    public final void testOnFlowProcessPasoSubAreas() {
        ArgumentCaptor<String> errorCaptor = ArgumentCaptor.forClass(String.class);
        FlowEvent event = mock(FlowEvent.class);
        when(event.getNewStep()).thenReturn("subareas");
        when(event.getOldStep()).thenReturn("pasoatras");
        nuevoModeloInformeBean.setListaAreas(new ArrayList<>());
        
        nuevoModeloInformeBean.onFlowProcess(event);
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), errorCaptor.capture(), eq(null),
                eq(null));
        
        String paso = nuevoModeloInformeBean.onFlowProcess(event);
        
        assertThat(paso).isEqualTo("pasoatras");
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.NuevoModeloInformeBean#onFlowProcess(org.primefaces.event.FlowEvent)}.
     */
    @Test
    public final void testOnFlowProcessPasoFinalizar() {
        FlowEvent event = mock(FlowEvent.class);
        when(event.getNewStep()).thenReturn("finalizar");
        when(event.getOldStep()).thenReturn("atras");
        nuevoModeloInformeBean.setListaAreas(new ArrayList<>());
        
        AreaInforme area = new AreaInforme();
        List<SubareaInforme> subAreas = new ArrayList<>();
        SubareaInforme subArea = mock(SubareaInforme.class);
        subAreas.add(subArea);
        area.setSubareas(subAreas);
        
        List<AreaInforme> areas = new ArrayList<>();
        areas.add(area);
        nuevoModeloInformeBean.setListaAreas(areas);
        
        nuevoModeloInformeBean.onFlowProcess(event);
        
        String paso = nuevoModeloInformeBean.onFlowProcess(event);
        
        assertThat(paso).isEqualTo("finalizar");
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.NuevoModeloInformeBean#aniadeArea(java.lang.String)}.
     */
    @Test
    public final void testAniadeArea() {
        nuevoModeloInformeBean.setListaAreas(new ArrayList<>());
        nuevoModeloInformeBean.aniadeArea("area_test");
        verify(nuevoModeloInformeService, times(1)).aniadeArea("area_test", nuevoModeloInformeBean.getListaAreas());
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.NuevoModeloInformeBean#aniadeSubarea(es.mira.progesin.persistence.entities.informes.AreaInforme, java.lang.String)}.
     */
    @Test
    public final void testAniadeSubarea() {
        AreaInforme area = mock(AreaInforme.class);
        nuevoModeloInformeBean.aniadeSubarea(area, "subArea_test");
        verify(nuevoModeloInformeService, times(1)).aniadeSubarea(area, "subArea_test");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.NuevoModeloInformeBean#onSelectArea(org.primefaces.event.SelectEvent)}.
     */
    @Test
    public final void testOnSelectArea() {
        SelectEvent event = mock(SelectEvent.class);
        AreaInforme area = mock(AreaInforme.class);
        when(event.getObject()).thenReturn(area);
        nuevoModeloInformeBean.onSelectArea(event);
        assertThat(nuevoModeloInformeBean.getAreaSelect()).isEqualTo(area);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.NuevoModeloInformeBean#onSelectSubArea(org.primefaces.event.SelectEvent)}.
     */
    @Test
    public final void testOnSelectSubArea() {
        SelectEvent event = mock(SelectEvent.class);
        SubareaInforme subArea = mock(SubareaInforme.class);
        when(event.getObject()).thenReturn(subArea);
        nuevoModeloInformeBean.onSelectSubArea(event);
        assertThat(nuevoModeloInformeBean.getSubAreaSelect()).isEqualTo(subArea);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.NuevoModeloInformeBean#borraArea()}.
     */
    @Test
    public final void testBorraArea() {
        List<AreaInforme> areas = new ArrayList<>();
        AreaInforme area = new AreaInforme();
        area.setId(3L);
        area.setDescripcion("descript");
        areas.add(area);
        nuevoModeloInformeBean.setListaAreas(areas);
        
        AreaInforme areaSelect = new AreaInforme();
        areaSelect.setId(3L);
        areaSelect.setDescripcion("descript");
        nuevoModeloInformeBean.setAreaSelect(areaSelect);
        
        nuevoModeloInformeBean.borraArea();
        
        assertThat(nuevoModeloInformeBean.getListaAreas()).hasSize(0);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.NuevoModeloInformeBean#borraSubarea(es.mira.progesin.persistence.entities.informes.AreaInforme)}.
     */
    @Test
    public final void testBorraSubarea() {
        AreaInforme area = new AreaInforme();
        List<SubareaInforme> subAreas = new ArrayList<>();
        SubareaInforme subArea = new SubareaInforme();
        subArea.setId(3L);
        subArea.setDescripcion("subarea_descript");
        subAreas.add(subArea);
        area.setSubareas(subAreas);
        
        SubareaInforme subAreaSelect = new SubareaInforme();
        subAreaSelect.setId(3L);
        subAreaSelect.setDescripcion("subarea_descript");
        nuevoModeloInformeBean.setSubAreaSelect(subAreaSelect);
        
        nuevoModeloInformeBean.borraSubarea(area);
        
        assertThat(subAreas).hasSize(0);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.NuevoModeloInformeBean#grabaInforme()}.
     */
    @Test
    public final void testGrabaInforme() {
        ModeloInforme modelo = mock(ModeloInforme.class);
        nuevoModeloInformeBean.setNuevoModelo(modelo);
        nuevoModeloInformeBean.setListaAreas(new ArrayList<>());
        when(nuevoModeloInformeService.guardaModelo(modelo, nuevoModeloInformeBean.getListaAreas())).thenReturn(modelo);
        nuevoModeloInformeBean.grabaInforme();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Nuevo modelo de informe",
                "Se ha creado el nuevo modelo con éxito");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.NuevoModeloInformeBean#grabaInforme()}.
     */
    @Test
    public final void testGrabaInforme_Error() {
        ModeloInforme modelo = mock(ModeloInforme.class);
        nuevoModeloInformeBean.setNuevoModelo(modelo);
        nuevoModeloInformeBean.setListaAreas(new ArrayList<>());
        when(nuevoModeloInformeService.guardaModelo(modelo, nuevoModeloInformeBean.getListaAreas())).thenReturn(null);
        nuevoModeloInformeBean.grabaInforme();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                "Se ha producido un error al crear el modelo de informe");
    }
    
}
