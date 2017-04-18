/**
 * 
 */
package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.event.RowEditEvent;

import es.mira.progesin.persistence.entities.TipoEquipo;
import es.mira.progesin.services.EquipoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ITipoEquipoService;
import es.mira.progesin.util.FacesUtilities;

/**
 * @author EZENTIS
 * 
 * Test del servicio Parametros
 *
 */
// @RunWith(MockitoJUnitRunner.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest({ FacesUtilities.class })
public class TipoEquipoBeanTest {
    
    @Mock
    private FacesUtilities facesUtilities;
    
    @Mock
    private ITipoEquipoService tipoEquipoService;
    
    @Mock
    private EquipoService equipoService;
    
    @Mock
    private transient IRegistroActividadService regActividadService;
    
    @InjectMocks
    private TipoEquipoBean tipoEquipoBean;
    
    /**
     * Configuración inicial del test
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
    }
    
    /**
     * Comprobación clase existe
     */
    @Test
    public void type() {
        assertThat(TipoEquipoBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta
     */
    @Test
    public void instantiation() {
        TipoEquipoBean target = new TipoEquipoBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoEquipoBean#tipoEquipoListado()}.
     */
    @Test
    public void tipoEquipoListado() {
        tipoEquipoBean.tipoEquipoListado();
        verify(tipoEquipoService, times(1)).findAll();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoEquipoBean#eliminarTipo(TipoEquipo)}.
     */
    @Test
    public void eliminarTipo() {
        TipoEquipo tEquipo = TipoEquipo.builder().id(1L).codigo("IAPRL")
                .descripcion("Inspecciones Área Prevención de Riesgos Laborales").build();
        List<TipoEquipo> listaTipoEquipo = new ArrayList<>();
        listaTipoEquipo.add(tEquipo);
        tipoEquipoBean.setListaTipoEquipo(listaTipoEquipo);
        tipoEquipoBean.eliminarTipo(tEquipo);
        verify(equipoService, times(1)).existsByTipoEquipo(tEquipo);
        verify(tipoEquipoService, times(1)).delete(tEquipo.getId());
        assertThat(tipoEquipoBean.getListaTipoEquipo().size()).isEqualTo(0);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoEquipoBean#init()}.
     */
    @Test
    public void init() {
        TipoEquipoBean tipoEquipoBeanMock = spy(tipoEquipoBean);
        tipoEquipoBeanMock.init();
        verify(tipoEquipoBeanMock, times(1)).tipoEquipoListado();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoEquipoBean#altaTipo(String, String)}.
     */
    @Test
    public void altaTipo() {
        TipoEquipo tEquipo = TipoEquipo.builder().codigo("TEST").descripcion("Tipo Equipo Test").build();
        tipoEquipoBean.altaTipo("TEST", "Tipo Equipo Test");
        ArgumentCaptor<TipoEquipo> tipoEquipoCaptor = ArgumentCaptor.forClass(TipoEquipo.class);
        when(tipoEquipoService.save(tEquipo)).thenReturn(tEquipo);
        verify(tipoEquipoService, times(1)).save(tipoEquipoCaptor.capture());
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.TipoEquipoBean#onRowEdit(RowEditEvent)}.
     */
    @Test
    public void onRowEdit() {
        TipoEquipo tEquipo = TipoEquipo.builder().codigo("TEST").descripcion("Tipo Equipo Test").build();
        RowEditEvent event = mock(RowEditEvent.class);
        when(event.getObject()).thenReturn(tEquipo);
        tipoEquipoBean.onRowEdit(event);
        ArgumentCaptor<TipoEquipo> tipoEquipoCaptor = ArgumentCaptor.forClass(TipoEquipo.class);
        when(tipoEquipoService.save(tEquipo)).thenReturn(tEquipo);
        verify(tipoEquipoService, times(1)).save(tipoEquipoCaptor.capture());
    }
    
}
