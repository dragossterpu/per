/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.primefaces.model.SortOrder;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.util.PdfGenerator;
import es.mira.progesin.web.beans.InspeccionBusqueda;

/**
 * Test para el servicio de estadísticas.
 * @author EZENTIS.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class EstadisticaServiceTest {
    
    /**
     * Simulación de PdfGenerator.
     */
    @Mock
    private PdfGenerator generadorPDF;
    
    /**
     * Simulación de la autenticación.
     */
    @Mock
    private IInspeccionesService inspeccionesService;
    
    /**
     * Captor del tipo Map<EstadoInspeccionEnum, Integer>.
     */
    @Captor
    private ArgumentCaptor<Map<EstadoInspeccionEnum, List<Inspeccion>>> mapaEstadisticas;
    
    /**
     * Instancia de prueba para el servicio de guías.
     */
    @InjectMocks
    private IEstadisticaService estadisticasService = new EstadisticaService();
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(EstadisticaService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        EstadisticaService target = new EstadisticaService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.EstadisticaService#obtenerEstadisticas(es.mira.progesin.web.beans.InspeccionBusqueda)}.
     */
    @Test
    public final void testObtenerEstadisticas() {
        LocalDate localDate = LocalDate.of(2017, 6, 28);
        Date fechaDesde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        InspeccionBusqueda filtro = new InspeccionBusqueda();
        filtro.setFechaDesde(fechaDesde);
        filtro.setFechaHasta(fechaHasta);
        List<Inspeccion> inspecciones = new ArrayList<>();
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setEstadoInspeccion(EstadoInspeccionEnum.A_SIN_INICIAR);
        inspecciones.add(inspeccion);
        when(inspeccionesService.buscarInspeccionPorCriteria(0, 0, "id", SortOrder.ASCENDING, filtro))
                .thenReturn(inspecciones);
        
        estadisticasService.obtenerEstadisticas(filtro);
        
        verify(inspeccionesService, times(1)).buscarInspeccionPorCriteria(0, 0, "id", SortOrder.ASCENDING, filtro);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.EstadisticaService#verListaEstado(es.mira.progesin.web.beans.InspeccionBusqueda, es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum)}.
     */
    @Test
    public final void testVerListaEstado() {
        LocalDate localDate = LocalDate.of(2017, 6, 28);
        Date fechaDesde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        InspeccionBusqueda filtro = new InspeccionBusqueda();
        filtro.setFechaDesde(fechaDesde);
        filtro.setFechaHasta(fechaHasta);
        EstadoInspeccionEnum estado = EstadoInspeccionEnum.A_SIN_INICIAR;
        estadisticasService.verListaEstado(filtro, estado);
        verify(inspeccionesService, times(1)).buscarInspeccionPorCriteria(0, 0, "id", SortOrder.ASCENDING, filtro);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.EstadisticaService#exportar(es.mira.progesin.web.beans.InspeccionBusqueda, java.util.List, java.io.File)}.
     * @throws ProgesinException lanzada.
     */
    @Test
    public final void testExportar() throws ProgesinException {
        LocalDate localDate = LocalDate.of(2017, 6, 28);
        Date fechaDesde = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaHasta = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        InspeccionBusqueda filtro = new InspeccionBusqueda();
        filtro.setFechaDesde(fechaDesde);
        filtro.setFechaHasta(fechaHasta);
        EstadoInspeccionEnum estado = EstadoInspeccionEnum.A_SIN_INICIAR;
        List<EstadoInspeccionEnum> estados = new ArrayList<>();
        estados.add(estado);
        File fileImg = mock(File.class);
        estadisticasService.exportar(filtro, estados, fileImg);
        verify(inspeccionesService, times(1)).buscarInspeccionPorCriteria(0, 0, "id", SortOrder.ASCENDING, filtro);
        verify(generadorPDF, times(1)).generarInformeEstadisticas(mapaEstadisticas.capture(), eq(filtro), eq(fileImg));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.EstadisticaService#obtenerTotal(java.util.Map)}.
     */
    @Test
    public final void testObtenerTotal() {
        Map<EstadoInspeccionEnum, Integer> estadistica = new EnumMap<>(EstadoInspeccionEnum.class);
        estadistica.put(EstadoInspeccionEnum.A_SIN_INICIAR, 2);
        int total = estadisticasService.obtenerTotal(estadistica);
        assertThat(total).isEqualTo(2);
    }
    
}
