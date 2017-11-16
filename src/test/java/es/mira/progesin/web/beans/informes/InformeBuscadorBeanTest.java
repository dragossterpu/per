/**
 * 
 */
package es.mira.progesin.web.beans.informes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
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
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.lazydata.LazyModelInforme;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.services.IAreaInformeService;
import es.mira.progesin.services.IEquipoService;
import es.mira.progesin.services.IInformeService;

/**
 * 
 * Test del bean VisualizarModeloInformeBean.
 *
 * @author EZENTIS
 */

@RunWith(MockitoJUnitRunner.class)
public class InformeBuscadorBeanTest {
    
    /**
     * Servicio de informes.
     */
    @Mock
    private IInformeService informeService;
    
    /**
     * Servicio de equipos.
     */
    @Mock
    private transient IEquipoService equipoService;
    
    /**
     * Servicio de áreas.
     */
    @Mock
    private transient IAreaInformeService areaInformeService;
    
    /**
     * Bean de ModeloInformePersonalizado.
     */
    @InjectMocks
    private InformeBuscadorBean informeBuscadorBean;
    
    /**
     * Comprueba que la clase existe.
     */
    @Test
    public void type() {
        assertThat(InformeBuscadorBean.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     */
    @Test
    public void instantiation() {
        InformeBuscadorBean target = new InformeBuscadorBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBuscadorBean#init()}.
     */
    @Test
    public final void testInit() {
        InformeBusqueda informeBusqueda = new InformeBusqueda();
        informeBusqueda.setAmbitoInspeccion(AmbitoInspeccionEnum.PN);
        List<Equipo> lista = new ArrayList<>();
        lista.add(Equipo.builder().id(1L).build());
        when(equipoService.findByFechaBajaIsNull()).thenReturn(lista);
        
        List<AreaInforme> listaAreas = new ArrayList<>();
        listaAreas.add(AreaInforme.builder().id(1L).build());
        when(areaInformeService.findAll()).thenReturn(listaAreas);
        
        informeBuscadorBean.init();
        assertThat(informeBuscadorBean.getModel()).isNotNull();
        assertThat(informeBuscadorBean.getInformeBusqueda()).isNotNull();
        assertThat(informeBuscadorBean.getList()).containsOnly(true);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBuscadorBean#getFormBusquedaInformes()}.
     */
    @Test
    public final void testGetFormBusquedaInformes() {
        LazyModelInforme model = new LazyModelInforme(informeService);
        model.setRowCount(15);
        informeBuscadorBean.setModel(model);
        String ruta = informeBuscadorBean.getFormBusquedaInformes();
        assertThat(informeBuscadorBean.getInformeBusqueda()).isNotNull();
        assertThat(informeBuscadorBean.getModel().getRowCount()).isEqualTo(0);
        assertThat(ruta).isEqualTo("/informes/informes?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBuscadorBean#limpiarBusqueda()}.
     */
    @Test
    public final void testLimpiarBusqueda() {
        LazyModelInforme model = new LazyModelInforme(informeService);
        model.setRowCount(15);
        informeBuscadorBean.setModel(model);
        informeBuscadorBean.limpiarBusqueda();
        assertThat(informeBuscadorBean.getInformeBusqueda()).isNotNull();
        assertThat(informeBuscadorBean.getModel().getRowCount()).isEqualTo(0);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBuscadorBean#buscarInforme()}.
     */
    @Test
    public final void testBuscarInforme() {
        InformeBusqueda busqueda = mock(InformeBusqueda.class);
        LazyModelInforme modelo = mock(LazyModelInforme.class);
        informeBuscadorBean.setModel(modelo);
        informeBuscadorBean.setInformeBusqueda(busqueda);
        informeBuscadorBean.buscarInforme();
        verify(modelo, times(1)).setBusqueda(busqueda);
        verify(modelo, times(1)).load(0, Constantes.TAMPAGINA, Constantes.FECHAALTA, SortOrder.DESCENDING, null);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.InformeBuscadorBean#onToggle(org.primefaces.event.ToggleEvent)}.
     */
    @Test
    public final void testOnToggle_False() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        when(eventMock.getVisibility()).thenReturn(Visibility.HIDDEN);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.FALSE);
        informeBuscadorBean.setList(listaToogle);
        informeBuscadorBean.onToggle(eventMock);
        assertThat(listaToogle.get(0)).isFalse();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.informes.InformeBuscadorBean#onToggle(org.primefaces.event.ToggleEvent)}.
     */
    @Test
    public final void testOnToggle_True() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        when(eventMock.getVisibility()).thenReturn(Visibility.VISIBLE);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.TRUE);
        informeBuscadorBean.setList(listaToogle);
        informeBuscadorBean.onToggle(eventMock);
        assertThat(listaToogle.get(0)).isTrue();
    }
    
}
