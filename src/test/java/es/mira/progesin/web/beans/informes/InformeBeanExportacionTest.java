/**
 * 
 */
package es.mira.progesin.web.beans.informes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.TipoUnidad;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.informes.AreaInforme;
import es.mira.progesin.persistence.entities.informes.Informe;
import es.mira.progesin.persistence.entities.informes.ModeloInformePersonalizado;
import es.mira.progesin.persistence.entities.informes.RespuestaInforme;
import es.mira.progesin.persistence.entities.informes.SubareaInforme;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.HtmlDocxGenerator;
import es.mira.progesin.util.HtmlPdfGenerator;
import es.mira.progesin.util.Utilities;

/**
 * Test del bean de informes.
 * 
 * @author EZENTIS
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ Utilities.class, FacesUtilities.class })
public class InformeBeanExportacionTest {
    
    /**
     * Generador de PDFs a partir de código html.
     */
    @Mock
    private transient HtmlPdfGenerator htmlPdfGenerator;
    
    /**
     * Generador de DOCXs a partir de código html.
     */
    @Mock
    private transient HtmlDocxGenerator htmlDocxGenerator;
    
    /**
     * Bean de informes.
     */
    @InjectMocks
    private InformeBean informeBean;
    
    /**
     * Literal areaTest.
     */
    private static final String AREATEST = "areaTest";
    
    /**
     * Literal respuestaTest.
     */
    private static final String RESPUESTATEST = "respuestaTest";
    
    /**
     * Literal conclusionesTest.
     */
    private static final String CONCLUSIONESTEST = "conclusionesTest";
    
    /**
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Utilities.class);
        PowerMockito.mockStatic(FacesUtilities.class);
        
        when(Utilities.getFechaFormateada(any(Date.class), any(String.class))).thenCallRealMethod();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBean#exportarInforme(java.lang.String)}.
     * @throws ProgesinException error
     */
    @Test
    public void testExportarInformePdf() throws ProgesinException {
        Long idModeloPersonalizado = 1L;
        AreaInforme area = AreaInforme.builder().id(1L).descripcion(AREATEST).build();
        SubareaInforme subarea = SubareaInforme.builder().descripcion("subareaTest").area(area).build();
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(subarea);
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(idModeloPersonalizado)
                .subareas(subareas).build();
        informeBean.setModeloInformePersonalizado(modeloPersonalizado);
        List<RespuestaInforme> respuestas = new ArrayList<>();
        respuestas.add(RespuestaInforme.builder().subarea(subarea).texto(RESPUESTATEST.getBytes())
                .conclusiones(CONCLUSIONESTEST.getBytes()).build());
        TipoInspeccion tipoInspeccion = TipoInspeccion.builder().descripcion("tipoInspeccionTest").build();
        TipoUnidad tipoUnidad = TipoUnidad.builder().id(1L).descripcion("tipoUnidadTest").build();
        Provincia provincia = new Provincia();
        provincia.setNombre("provinciaTest");
        Municipio municipio = Municipio.builder().id(1L).name("municipioTest").provincia(provincia).build();
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).tipoInspeccion(tipoInspeccion)
                .tipoUnidad(tipoUnidad).ambito(AmbitoInspeccionEnum.GC).municipio(municipio).equipo(mock(Equipo.class))
                .build();
        Informe informe = Informe.builder().id(1L).inspeccion(inspeccion).modeloPersonalizado(modeloPersonalizado)
                .respuestas(respuestas).fechaFinalizacion(new Date()).usernameFinalizacion("usuario_test").build();
        informeBean.setInforme(informe);
        
        List<AreaInforme> listaAreas = new ArrayList<>();
        listaAreas.add(area);
        informeBean.setListaAreas(listaAreas);
        
        Map<AreaInforme, List<SubareaInforme>> mapaAreasSubareas = new HashMap<>();
        mapaAreasSubareas.put(area, subareas);
        informeBean.setMapaAreasSubareas(mapaAreasSubareas);
        
        Map<SubareaInforme, String[]> mapaRespuestas = new HashMap<>();
        Map<SubareaInforme, String> mapaAsignaciones = new HashMap<>();
        
        String[] respuesta = new String[2];
        respuesta[0] = RESPUESTATEST;
        respuesta[1] = CONCLUSIONESTEST;
        mapaRespuestas.put(subarea, respuesta);
        informeBean.setMapaRespuestas(mapaRespuestas);
        informeBean.setMapaAsignaciones(mapaAsignaciones);
        
        when(Utilities.limpiarHtml(any(String.class))).thenCallRealMethod();
        when(htmlPdfGenerator.generarInformePdf(any(String.class), any(String.class), any(String.class),
                any(String.class), any(String.class))).thenReturn(mock(DefaultStreamedContent.class));
        
        StreamedContent file = informeBean.exportarInforme("PDF", "completo");
        verifyStatic(Utilities.class, times(1));
        Utilities.limpiarHtml(any(String.class));
        verify(htmlPdfGenerator, times(1)).generarInformePdf(any(String.class), any(String.class), any(String.class),
                any(String.class), any(String.class));
        assertThat(file).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.informes.InformeBean#exportarInforme(java.lang.String)}.
     * @throws ProgesinException error
     */
    @Test
    public void testExportarInformeDocx() throws ProgesinException {
        Long idModeloPersonalizado = 1L;
        AreaInforme area = AreaInforme.builder().id(1L).descripcion(AREATEST).build();
        SubareaInforme subarea = SubareaInforme.builder().descripcion("subareaTest").area(area).build();
        List<SubareaInforme> subareas = new ArrayList<>();
        subareas.add(subarea);
        ModeloInformePersonalizado modeloPersonalizado = ModeloInformePersonalizado.builder().id(idModeloPersonalizado)
                .subareas(subareas).build();
        informeBean.setModeloInformePersonalizado(modeloPersonalizado);
        List<RespuestaInforme> respuestas = new ArrayList<>();
        respuestas.add(RespuestaInforme.builder().subarea(subarea).texto(RESPUESTATEST.getBytes())
                .conclusiones(CONCLUSIONESTEST.getBytes()).build());
        TipoInspeccion tipoInspeccion = TipoInspeccion.builder().descripcion("tipoInspeccionTest").build();
        TipoUnidad tipoUnidad = TipoUnidad.builder().id(1L).descripcion("tipoUnidadTest").build();
        Provincia provincia = new Provincia();
        provincia.setNombre("provinciaTest");
        Municipio municipio = Municipio.builder().id(1L).name("municipioTest").provincia(provincia).build();
        Inspeccion inspeccion = Inspeccion.builder().id(1L).anio(2017).tipoInspeccion(tipoInspeccion)
                .tipoUnidad(tipoUnidad).ambito(AmbitoInspeccionEnum.GC).municipio(municipio).equipo(mock(Equipo.class))
                .build();
        Informe informe = Informe.builder().id(1L).inspeccion(inspeccion).modeloPersonalizado(modeloPersonalizado)
                .respuestas(respuestas).fechaFinalizacion(new Date()).usernameFinalizacion("usuario_test").build();
        informeBean.setInforme(informe);
        
        List<AreaInforme> listaAreas = new ArrayList<>();
        listaAreas.add(area);
        informeBean.setListaAreas(listaAreas);
        
        Map<AreaInforme, List<SubareaInforme>> mapaAreasSubareas = new HashMap<>();
        mapaAreasSubareas.put(area, subareas);
        informeBean.setMapaAreasSubareas(mapaAreasSubareas);
        
        Map<SubareaInforme, String[]> mapaRespuestas = new HashMap<>();
        Map<SubareaInforme, String> mapaAsignaciones = new HashMap<>();
        
        String[] respuesta = new String[2];
        respuesta[0] = RESPUESTATEST;
        respuesta[1] = CONCLUSIONESTEST;
        mapaRespuestas.put(subarea, respuesta);
        informeBean.setMapaRespuestas(mapaRespuestas);
        informeBean.setMapaAsignaciones(mapaAsignaciones);
        
        when(Utilities.limpiarHtml(any(String.class))).thenCallRealMethod();
        when(htmlDocxGenerator.generarInformeDocx(any(String.class), any(String.class), any(String.class),
                any(String.class), any(String.class))).thenReturn(mock(DefaultStreamedContent.class));
        
        StreamedContent file = informeBean.exportarInforme("DOCX", "conclusiones");
        verifyStatic(Utilities.class, times(1));
        Utilities.limpiarHtml(any(String.class));
        verify(htmlDocxGenerator, times(1)).generarInformeDocx(any(String.class), any(String.class), any(String.class),
                any(String.class), any(String.class));
        assertThat(file).isNotNull();
    }
    
}
