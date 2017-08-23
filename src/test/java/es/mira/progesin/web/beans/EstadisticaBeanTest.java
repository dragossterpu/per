/**
 * 
 */
package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.PieChartModel;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.services.IEstadisticaService;
import es.mira.progesin.services.ITipoInspeccionService;
import es.mira.progesin.util.FacesUtilities;

/**
 * Test para GestorDocumentalBean.
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, EstadisticaBean.class, ImageIO.class, File.class })
public class EstadisticaBeanTest {
    
    /**
     * Mock Servicio de estadistica.
     */
    @Mock
    private IEstadisticaService estadisticaService;
    
    /**
     * Captor InspeccionBusqueda.
     */
    @Captor
    private ArgumentCaptor<InspeccionBusqueda> filtroCaptor;
    
    /**
     * Mock ITipoInspeccionService.
     */
    @Mock
    private ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Captor del tipo ByteArrayInputStream.
     */
    @Captor
    private ArgumentCaptor<ByteArrayInputStream> byteArrayCaptor;
    
    /**
     * Constante out.
     */
    private static final String OUT = "out";
    
    /**
     * Constante PNG.
     */
    private static final String PNG = ".png";
    
    /**
     * Simulación de EstadisticaBean.
     */
    @InjectMocks
    private EstadisticaBean estadisticaBean;
    
    /**
     * Configuración inicial del test.
     * @throws Exception lanzada.
     */
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ImageIO.class);
        PowerMockito.mockStatic(File.class);
        PowerMockito.mockStatic(FacesUtilities.class);
    }
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(EstadisticaBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        EstadisticaBean target = new EstadisticaBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EstadisticaBean#init()}.
     */
    @Test
    public final void testInit() {
        List<TipoInspeccion> tiposIns = new ArrayList<>();
        tiposIns.add(mock(TipoInspeccion.class));
        when(tipoInspeccionService.buscaTodos()).thenReturn(tiposIns);
        Map<EstadoInspeccionEnum, Integer> mapaResultados = new EnumMap<>(EstadoInspeccionEnum.class);
        mapaResultados.put(EstadoInspeccionEnum.M_FINALIZADA, 2);
        mapaResultados.put(EstadoInspeccionEnum.A_SIN_INICIAR, 1);
        when(estadisticaService.obtenerEstadisticas(estadisticaBean.getFiltro())).thenReturn(mapaResultados);
        when(estadisticaService.obtenerTotal(mapaResultados)).thenReturn(3);
        List<EstadoInspeccionEnum> listaEstados = Arrays.stream(EstadoInspeccionEnum.values())
                .collect(Collectors.toList());
        
        estadisticaBean.setListaEstados(listaEstados);
        
        estadisticaBean.init();
        assertThat(estadisticaBean.getFiltro()).isNotNull();
        assertThat(estadisticaBean.getTotal()).isEqualTo(0);
        assertThat(estadisticaBean.getGrafica()).isNotNull();
        assertThat(estadisticaBean.getListaEstados()).hasSize(15);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EstadisticaBean#getEstadisticas()}.
     */
    @Test
    @Ignore
    public final void testGetEstadisticas() {
        Map<EstadoInspeccionEnum, Integer> mapaResultados = new EnumMap<>(EstadoInspeccionEnum.class);
        mapaResultados.put(EstadoInspeccionEnum.M_FINALIZADA, 2);
        mapaResultados.put(EstadoInspeccionEnum.A_SIN_INICIAR, 1);
        when(estadisticaService.obtenerEstadisticas(estadisticaBean.getFiltro())).thenReturn(mapaResultados);
        when(estadisticaService.obtenerTotal(mapaResultados)).thenReturn(3);
        List<EstadoInspeccionEnum> listaEstados = Arrays.stream(EstadoInspeccionEnum.values())
                .collect(Collectors.toList());
        
        estadisticaBean.setListaEstados(listaEstados);
        
        String redireccion = estadisticaBean.getEstadisticas();
        assertThat(estadisticaBean.getFiltro()).isNotNull();
        assertThat(estadisticaBean.getTotal()).isEqualTo(0);
        assertThat(estadisticaBean.getGrafica()).isNotNull();
        
        assertThat(redireccion).isEqualTo("/estadisticas/estadisticas?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EstadisticaBean#limpiarBusqueda()}.
     */
    @Test
    @Ignore
    public final void testLimpiarBusqueda() {
        Map<EstadoInspeccionEnum, Integer> mapaResultados = new EnumMap<>(EstadoInspeccionEnum.class);
        mapaResultados.put(EstadoInspeccionEnum.M_FINALIZADA, 2);
        mapaResultados.put(EstadoInspeccionEnum.A_SIN_INICIAR, 1);
        when(estadisticaService.obtenerEstadisticas(estadisticaBean.getFiltro())).thenReturn(mapaResultados);
        when(estadisticaService.obtenerTotal(mapaResultados)).thenReturn(3);
        List<EstadoInspeccionEnum> listaEstados = Arrays.stream(EstadoInspeccionEnum.values())
                .collect(Collectors.toList());
        estadisticaBean.setListaEstados(listaEstados);
        
        estadisticaBean.limpiarBusqueda();
        assertThat(estadisticaBean.getFiltro()).isNotNull();
        assertThat(estadisticaBean.getTotal()).isEqualTo(0);
        assertThat(estadisticaBean.getGrafica()).isNotNull();
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.EstadisticaBean#filtrar(es.mira.progesin.persistence.entities.TipoInspeccion, es.mira.progesin.persistence.entities.Provincia, java.util.Date, java.util.Date)}.
     * @throws Exception lanzada.
     */
    @Test
    public final void testFiltrar() throws Exception {
        estadisticaBean.setFiltro(new InspeccionBusqueda());
        Map<EstadoInspeccionEnum, Integer> mapaResultados = new EnumMap<>(EstadoInspeccionEnum.class);
        mapaResultados.put(EstadoInspeccionEnum.M_FINALIZADA, 2);
        mapaResultados.put(EstadoInspeccionEnum.A_SIN_INICIAR, 1);
        when(estadisticaService.obtenerEstadisticas(estadisticaBean.getFiltro())).thenReturn(mapaResultados);
        when(estadisticaService.obtenerTotal(mapaResultados)).thenReturn(3);
        List<EstadoInspeccionEnum> listaEstados = Arrays.stream(EstadoInspeccionEnum.values())
                .collect(Collectors.toList());
        estadisticaBean.setListaEstados(listaEstados);
        
        estadisticaBean.filtrar(mock(TipoInspeccion.class), mock(Provincia.class), mock(Date.class), mock(Date.class));
        assertThat(estadisticaBean.getFiltro().getTipoInspeccion()).isNotNull();
        assertThat(estadisticaBean.getFiltro().getProvincia()).isNotNull();
        assertThat(estadisticaBean.getFiltro().getFechaDesde()).isNotNull();
        assertThat(estadisticaBean.getFiltro().getFechaHasta()).isNotNull();
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.EstadisticaBean#filtraPDF(es.mira.progesin.persistence.entities.TipoInspeccion, es.mira.progesin.persistence.entities.Provincia, java.util.Date, java.util.Date, java.lang.String)}.
     * @throws IOException lanzada.
     * @throws ProgesinException lanzada.
     */
    @Test
    public final void testFiltraPDF() throws IOException, ProgesinException {
        
        List<EstadoInspeccionEnum> estadosSeleccionados = new ArrayList<>();
        estadosSeleccionados.add(EstadoInspeccionEnum.E_PEND_ENVIAR_CUESTIONARIO);
        estadisticaBean.setEstadosSeleccionados(estadosSeleccionados);
        File fileImg = mock(File.class);
        BufferedImage renderedImage = mock(BufferedImage.class);
        when(ImageIO.read(byteArrayCaptor.capture())).thenReturn(renderedImage);
        when(File.createTempFile(OUT, PNG)).thenReturn(fileImg);
        
        StreamedContent file = mock(StreamedContent.class);
        when(estadisticaService.exportar(filtroCaptor.capture(), eq(estadosSeleccionados), eq(fileImg)))
                .thenReturn(file);
        estadisticaBean.filtraPDF(mock(TipoInspeccion.class), mock(Provincia.class), mock(Date.class), mock(Date.class),
                "imagen,Test");
        PowerMockito.verifyStatic(times(1));
        ImageIO.read(byteArrayCaptor.capture());
        PowerMockito.verifyStatic(times(1));
        File.createTempFile(OUT, PNG);
        PowerMockito.verifyStatic(times(1));
        ImageIO.write(any(RenderedImage.class), eq("png"), any(File.class));
        verify(estadisticaService, times(1)).exportar(any(InspeccionBusqueda.class), eq(estadosSeleccionados),
                any(File.class));
        assertThat(estadisticaBean.getFile()).isEqualTo(file);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.EstadisticaBean#filtraPDF(es.mira.progesin.persistence.entities.TipoInspeccion, es.mira.progesin.persistence.entities.Provincia, java.util.Date, java.util.Date, java.lang.String)}.
     * @throws IOException lanzada.
     * @throws ProgesinException lanzada.
     */
    @Test
    public final void testFiltraPDFestadosNoSeleccionados() throws IOException, ProgesinException {
        
        List<EstadoInspeccionEnum> estadosSeleccionados = new ArrayList<>();
        estadisticaBean.setEstadosSeleccionados(estadosSeleccionados);
        File fileImg = mock(File.class);
        BufferedImage renderedImage = mock(BufferedImage.class);
        when(ImageIO.read(byteArrayCaptor.capture())).thenReturn(renderedImage);
        when(File.createTempFile(OUT, PNG)).thenReturn(fileImg);
        
        estadisticaBean.filtraPDF(mock(TipoInspeccion.class), mock(Provincia.class), mock(Date.class), mock(Date.class),
                "imagen,Test");
        
        PowerMockito.verifyStatic(times(1));
        ImageIO.read(byteArrayCaptor.capture());
        PowerMockito.verifyStatic(times(1));
        File.createTempFile(OUT, PNG);
        PowerMockito.verifyStatic(times(1));
        ImageIO.write(any(RenderedImage.class), eq("png"), any(File.class));
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.EstadisticaBean#getGrafica()}.
     */
    @Test
    public final void testGetGrafica() {
        PieChartModel grafTest = mock(PieChartModel.class);
        estadisticaBean.setGrafica(grafTest);
        PieChartModel grafica = estadisticaBean.getGrafica();
        assertThat(grafica).isEqualTo(grafTest);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.EstadisticaBean#verDetalles(es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum)}.
     */
    @Test
    public final void testVerDetalles() {
        estadisticaBean.setFiltro(mock(InspeccionBusqueda.class));
        EstadoInspeccionEnum estado = EstadoInspeccionEnum.E_PEND_ENVIAR_CUESTIONARIO;
        estadisticaBean.verDetalles(estado);
        verify(estadisticaService, times(1)).verListaEstado(filtroCaptor.capture(), eq(estado));
    }
    
}
