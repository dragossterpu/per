/**
 * 
 */
package es.mira.progesin.web.beans.informes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.lazydata.LazyModelInformePersonalizado;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInforme;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.services.IModeloInformePersonalizadoService;
import es.mira.progesin.services.IModeloInformeService;
import es.mira.progesin.util.FacesUtilities;

/**
 * 
 * Test del bean Modelo de Informe personalizado.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, RequestContext.class })
public class ModeloInformePersonalizadoBeanTest {
    /**
     * Número de columnas de la vista.
     */
    private static final String NOMBREINFORME = "informeTest";
    
    /**
     * Simulación del RequestContext.
     */
    @Mock
    private RequestContext requestContext;
    
    /**
     * Mock Servicio de modelos de informe.
     */
    @Mock
    private transient IModeloInformeService modeloInformeService;
    
    /**
     * Mock Servicio de modelos de informe personalizados.
     */
    @Mock
    private transient IModeloInformePersonalizadoService informePersonalizadoService;
    
    /**
     * Bean de ModeloInformePersonalizado.
     */
    @InjectMocks
    private ModeloInformePersonalizadoBean modeloInformePersonalizadoBean;
    
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
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(RequestContext.class);
        when(RequestContext.getCurrentInstance()).thenReturn(requestContext);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.ModeloInformePersonalizadoBean#init()}.
     */
    @Test
    public final void testInit() {
        modeloInformePersonalizadoBean.init();
        assertThat(modeloInformePersonalizadoBean.getInformePersonalizadoBusqueda()).isNotNull();
        assertThat(modeloInformePersonalizadoBean.getModel()).isNotNull();
        assertThat(modeloInformePersonalizadoBean.getList()).containsOnly(true);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.ModeloInformePersonalizadoBean#limpiarBusqueda()}.
     */
    @Test
    public final void testLimpiarBusqueda() {
        LazyModelInformePersonalizado model = new LazyModelInformePersonalizado(informePersonalizadoService);
        model.setRowCount(15);
        modeloInformePersonalizadoBean.setModel(model);
        modeloInformePersonalizadoBean.limpiarBusqueda();
        assertThat(modeloInformePersonalizadoBean.getInformePersonalizadoBusqueda()).isNotNull();
        assertThat(modeloInformePersonalizadoBean.getModel().getRowCount()).isEqualTo(0);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.ModeloInformePersonalizadoBean#buscarInforme()}.
     */
    @Test
    public final void testBuscarInforme() {
        InformePersonalizadoBusqueda busqueda = mock(InformePersonalizadoBusqueda.class);
        LazyModelInformePersonalizado modelo = mock(LazyModelInformePersonalizado.class);
        modeloInformePersonalizadoBean.setModel(modelo);
        modeloInformePersonalizadoBean.setInformePersonalizadoBusqueda(busqueda);
        modeloInformePersonalizadoBean.buscarInforme();
        verify(modelo, times(1)).setBusqueda(busqueda);
        verify(modelo, times(1)).load(0, Constantes.TAMPAGINA, Constantes.FECHAALTA, SortOrder.DESCENDING, null);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.ModeloInformePersonalizadoBean#getFormBusquedaModelosInformePersonalizados()}.
     */
    @Test
    public final void testGetFormBusquedaModelosInformePersonalizados() {
        LazyModelInformePersonalizado model = new LazyModelInformePersonalizado(informePersonalizadoService);
        model.setRowCount(15);
        modeloInformePersonalizadoBean.setModel(model);
        String ruta = modeloInformePersonalizadoBean.getFormBusquedaModelosInformePersonalizados();
        assertThat(modeloInformePersonalizadoBean.getInformePersonalizadoBusqueda()).isNotNull();
        assertThat(modeloInformePersonalizadoBean.getModel().getRowCount()).isEqualTo(0);
        assertThat(ruta).isEqualTo("/informes/informesPersonalizados?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.ModeloInformePersonalizadoBean#crearModeloInformePersonalizado(java.lang.Long)}.
     */
    @Test
    public final void testCrearModeloInformePersonalizado() {
        ModeloInforme modelo = mock(ModeloInforme.class);
        when(modeloInformeService.findDistinctById(1L)).thenReturn(modelo);
        String ruta = modeloInformePersonalizadoBean.crearModeloInformePersonalizado(1L);
        assertThat(modeloInformePersonalizadoBean.getModeloInforme()).isEqualTo(modelo);
        assertThat(modeloInformePersonalizadoBean.getSubareasSeleccionadas()).isNotNull();
        assertThat(ruta).isEqualTo("/informes/personalizarModeloInforme?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.ModeloInformePersonalizadoBean#previsualizarCreacionInformePersonalizado()}.
     */
    @Test
    public final void testPrevisualizarCreacionInformePersonalizadoHaySubAreaSeleccionada() {
        AreaInforme area = mock(AreaInforme.class);
        List<AreaInforme> areas = new ArrayList<>();
        areas.add(area);
        ModeloInforme modelo = new ModeloInforme();
        modelo.setAreas(areas);
        modeloInformePersonalizadoBean.setModeloInforme(modelo);
        SubareaInforme[] subareas = new SubareaInforme[1];
        subareas[0] = mock(SubareaInforme.class);
        Map<AreaInforme, SubareaInforme[]> subareasSeleccionadas = new HashMap<>();
        subareasSeleccionadas.put(area, subareas);
        modeloInformePersonalizadoBean.setSubareasSeleccionadas(subareasSeleccionadas);
        String ruta = modeloInformePersonalizadoBean.previsualizarCreacionInformePersonalizado();
        assertThat(ruta).isEqualTo("/informes/previsualizarCreacionInformePersonalizado?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.ModeloInformePersonalizadoBean#previsualizarCreacionInformePersonalizado()}.
     */
    @Test
    public final void testPrevisualizarCreacionInformePersonalizadoNoHaySubAreaSeleccionada() {
        ModeloInforme modelo = new ModeloInforme();
        modelo.setAreas(new ArrayList<>());
        modeloInformePersonalizadoBean.setModeloInforme(modelo);
        modeloInformePersonalizadoBean.setSubareasSeleccionadas(new HashMap<>());
        
        String ruta = modeloInformePersonalizadoBean.previsualizarCreacionInformePersonalizado();
        
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR,
                "Debe seleccionar al menos un subapartado para el informe", "", null);
        assertThat(ruta).isNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.ModeloInformePersonalizadoBean#guardarInformePersonalizado(java.lang.String)}.
     */
    @Test
    public final void testGuardarInformePersonalizado() {
        modeloInformePersonalizadoBean.setModeloInforme(mock(ModeloInforme.class));
        modeloInformePersonalizadoBean.setSubareasSeleccionadas(new HashMap<>());
        when(informePersonalizadoService.guardarInformePersonalizado(NOMBREINFORME,
                modeloInformePersonalizadoBean.getModeloInforme(),
                modeloInformePersonalizadoBean.getSubareasSeleccionadas()))
                        .thenReturn(mock(ModeloInformePersonalizado.class));
        
        modeloInformePersonalizadoBean.guardarInformePersonalizado(NOMBREINFORME);
        
        verify(requestContext, times(1)).execute("PF('informeDialog').hide()");
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_INFO, "Informe personalizado",
                "Se ha guardado con éxito");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.ModeloInformePersonalizadoBean#guardarInformePersonalizado(java.lang.String)}.
     */
    @Test
    public final void testGuardarInformePersonalizadoError() {
        modeloInformePersonalizadoBean.setModeloInforme(mock(ModeloInforme.class));
        modeloInformePersonalizadoBean.setSubareasSeleccionadas(new HashMap<>());
        when(informePersonalizadoService.guardarInformePersonalizado(NOMBREINFORME,
                modeloInformePersonalizadoBean.getModeloInforme(),
                modeloInformePersonalizadoBean.getSubareasSeleccionadas())).thenReturn(null);
        
        modeloInformePersonalizadoBean.guardarInformePersonalizado(NOMBREINFORME);
        
        verify(requestContext).execute("PF('informeDialog').hide()");
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(FacesMessage.SEVERITY_ERROR, TipoRegistroEnum.ERROR.name(),
                "Se ha producido un error al guardar el informe");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.ModeloInformePersonalizadoBean#eliminarModeloPersonalizado(es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado)}.
     */
    @Test
    public final void testEliminarModeloPersonalizado() {
        ModeloInformePersonalizado modeloPersonalizado = mock(ModeloInformePersonalizado.class);
        when(informePersonalizadoService.eliminarModeloPersonalizado(modeloPersonalizado)).thenReturn(null);
        modeloInformePersonalizadoBean.eliminarModeloPersonalizado(modeloPersonalizado);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(FacesMessage.SEVERITY_ERROR, Constantes.ERRORMENSAJE,
                "Se ha producido un error al eliminar el informe personalizado, inténtelo de nuevo más tarde", null);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.ModeloInformePersonalizadoBean#onToggle(org.primefaces.event.ToggleEvent)}.
     */
    @Test
    public final void testOnToggle_False() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        when(eventMock.getVisibility()).thenReturn(Visibility.HIDDEN);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.FALSE);
        modeloInformePersonalizadoBean.setList(listaToogle);
        modeloInformePersonalizadoBean.onToggle(eventMock);
        assertThat(listaToogle.get(0)).isFalse();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.ModeloInformePersonalizadoBean#onToggle(org.primefaces.event.ToggleEvent)}.
     */
    @Test
    public final void testOnToggle_True() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        when(eventMock.getVisibility()).thenReturn(Visibility.VISIBLE);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.TRUE);
        modeloInformePersonalizadoBean.setList(listaToogle);
        modeloInformePersonalizadoBean.onToggle(eventMock);
        assertThat(listaToogle.get(0)).isTrue();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.ModeloInformePersonalizadoBean#visualizarInforme(java.lang.Long)}.
     */
    @Test
    public final void testVisualizarInforme() {
        ModeloInformePersonalizado modeloPersonalizado = new ModeloInformePersonalizado();
        modeloPersonalizado.setId(1L);
        
        AreaInforme areaModelo = new AreaInforme();
        areaModelo.setId(4L);
        List<SubareaInforme> subAreas = new ArrayList<>();
        SubareaInforme subArea1Modelo = new SubareaInforme();
        subArea1Modelo.setId(1L);
        subArea1Modelo.setArea(areaModelo);
        SubareaInforme subArea2Modelo = new SubareaInforme();
        subArea2Modelo.setId(2L);
        subArea2Modelo.setArea(areaModelo);
        subAreas.add(subArea2Modelo);
        subAreas.add(subArea1Modelo);
        
        AreaInforme area = new AreaInforme();
        area.setId(4L);
        SubareaInforme subArea1 = new SubareaInforme();
        subArea1.setId(1L);
        subArea1.setArea(area);
        SubareaInforme subArea2 = new SubareaInforme();
        subArea2.setId(2L);
        subArea2.setArea(area);
        modeloPersonalizado.setSubareas(subAreas);
        modeloInformePersonalizadoBean.setModeloPersonalizado(modeloPersonalizado);
        when(informePersonalizadoService.findModeloPersonalizadoCompleto(1L)).thenReturn(modeloPersonalizado);
        
        modeloInformePersonalizadoBean.visualizarInforme(1L);
        
        assertThat(modeloInformePersonalizadoBean.getListaAreas().get(0)).isEqualTo(area);
        assertThat(modeloInformePersonalizadoBean.getMapaAreasSubareas().get(area)).isEqualTo(subAreas);
        
    }
    
}
