package es.mira.progesin.lazydata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.model.SortOrder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.web.beans.InspeccionBusqueda;

/**
 * Test del LazyModel de Inspecciones.
 * 
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
public class LazyModelInspeccionTest {
    
    /**
     * Clase donde se hacen las pruebas.
     */
    @InjectMocks
    LazyModelInspeccion modelo;
    
    // @Before
    // public void setUp() {
    // List<Inspeccion> datasource = new ArrayList<>();
    // Inspeccion ins = Inspeccion.builder().id(1L).build();
    // datasource.add(ins);
    // }
    
    /**
     * Test method for {@link es.mira.progesin.lazydata.LazyModelInspeccion#LazyModelInspeccion(IInspeccionesService)}.
     */
    
    @Test
    public final void testLazyModelInspeccion() {
        IInspeccionesService inspeccionesService = mock(IInspeccionesService.class);
        modelo = new LazyModelInspeccion(inspeccionesService);
        
        assertThat(modelo.getInspeccionesService()).isEqualTo(inspeccionesService);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.lazydata.LazyModelInspeccion#getRowData(String)}.
     */
    
    @Test
    public final void testGetRowDataString() {
        List<Inspeccion> data = new ArrayList<>();
        Inspeccion ins = Inspeccion.builder().id(1L).build();
        data.add(ins);
        modelo.setDatasource(data);
        
        modelo.getRowData("1");
        
        assertThat(modelo.getRowData("1")).isEqualTo(ins);
    }
    
    /**
     * Test method for {@link es.mira.progesin.lazydata.LazyModelInspeccion#getRowKey(Inspeccion)}.
     */
    
    @Test
    public final void testGetRowKeyInspeccion() {
        Inspeccion ins = Inspeccion.builder().id(1L).build();
        
        modelo.getRowKey(ins);
        
        assertThat(modelo.getRowKey(ins)).isEqualTo(1L);
    }
    
    /**
     * Test method for {@link es.mira.progesin.lazydata.LazyModelInspeccion#load(int, int, List, java.util.Map)}.
     */
    
    @Test
    public final void testLoad_busquedaNull() {
        modelo.setBusqueda(null);
        
        modelo.load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
        
        assertThat(modelo.getRowCount()).isEqualTo(0);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.lazydata.LazyModelInspeccion#load(int, int, List, java.util.Map)}.
     */
    
    @Test
    public final void testLoad_busquedaNoNull() {
        IInspeccionesService inspeccionesService = mock(IInspeccionesService.class);
        InspeccionBusqueda insBusqueda = mock(InspeccionBusqueda.class);
        insBusqueda.setAsociar(true);
        when(inspeccionesService.getCountInspeccionCriteria(insBusqueda)).thenReturn(1);
        modelo.setInspeccionesService(inspeccionesService);
        modelo.setBusqueda(insBusqueda);
        
        modelo.load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
        
        assertThat(modelo.getRowCount()).isEqualTo(1);
        
    }
}
