/**
 * 
 */
package es.mira.progesin.web.beans.cuestionarios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.junit.Before;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.persistence.entities.DatosTablaGenerica;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.RespuestaCuestionarioId;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IRespuestaCuestionarioRepository;
import es.mira.progesin.services.IDocumentoService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.DataTableView;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.PdfGeneratorCuestionarios;
import es.mira.progesin.util.WordGenerator;

/**
 * 
 * Test del bean VisualizarCuestionario.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class, VisualizarCuestionario.class })
public class VisualizarCuestionarioTest {
    /**
     * Constante user.
     */
    private static final String USUARIOLOGUEADO = "usuarioLogueado";
    
    /**
     * Constante area.
     */
    private static final String AREA1 = "area1";
    
    /**
     * Constante pregunta.
     */
    private static final String PREGUNTA1 = "pregunta1";
    
    /**
     * Simulación del securityContext.
     */
    @Mock
    private SecurityContext securityContext;
    
    /**
     * Simulación de la autenticación.
     */
    @Mock
    private Authentication authentication;
    
    /**
     * Mock de Repositorio de tipos de respuestas de cuestionario.
     */
    @Mock
    private IConfiguracionRespuestasCuestionarioRepository configuracionRespuestaRepository;
    
    /**
     * Mock de Repositorio de respuestas de cuestionario.
     */
    @Mock
    private IRespuestaCuestionarioRepository respuestaRepository;
    
    /**
     * Mock de Repositorio de preguntas.
     */
    @Mock
    private IPreguntaCuestionarioRepository preguntasRepository;
    
    /**
     * Mock de Servicio de registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Mock de Servicio de documentos.
     */
    @Mock
    private IDocumentoService documentoService;
    
    /**
     * Mock de Generador de words.
     */
    @Mock
    private WordGenerator wordGenerator;
    
    /**
     * Mock de Generador de PDF.
     */
    @Mock
    private PdfGeneratorCuestionarios pdfGenerator;
    
    /**
     * Simulacion de VisualizarCuestionario.
     */
    @InjectMocks
    private VisualizarCuestionario visualizarCuestionario;
    
    /**
     * Mock de DataTableViews.
     */
    @Mock
    private DataTableView datosTablaMock;
    
    /**
     * Captor de tipo List<ConfiguracionRespuestasCuestionario>.
     */
    @Captor
    private ArgumentCaptor<List<ConfiguracionRespuestasCuestionario>> listaConfigRespuestasCaptor;
    
    /**
     * Comprueba que la clase existe.
     */
    @Test
    public void type() {
        assertThat(VisualizarCuestionario.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     */
    @Test
    public void instantiation() {
        VisualizarCuestionario target = new VisualizarCuestionario();
        assertThat(target).isNotNull();
    }
    
    /**
     * Inicializa el test.
     * @throws Exception lanzada
     */
    
    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        PowerMockito.whenNew(DataTableView.class).withNoArguments().thenReturn(datosTablaMock);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USUARIOLOGUEADO);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#visualizarVacio(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado)}.
     */
    @Test
    public final void testVisualizarVacioUserNoProvisionalSinVisualizarRespuestasMostrarTodas() {
        CuestionarioPersonalizado cuestionario = new CuestionarioPersonalizado();
        cuestionario.setId(1L);
        AreasCuestionario area = new AreasCuestionario();
        area.setId(1L);
        area.setArea(AREA1);
        PreguntasCuestionario pregunta = new PreguntasCuestionario();
        pregunta.setArea(area);
        pregunta.setPregunta(PREGUNTA1);
        List<PreguntasCuestionario> preguntas = new ArrayList<>();
        preguntas.add(pregunta);
        Map<Long, AreasCuestionario> mapaAreasVisualizarUsuario = new HashMap<>();
        mapaAreasVisualizarUsuario.put(1L, area);
        visualizarCuestionario.setMapaAreasVisualizarUsuario(mapaAreasVisualizarUsuario);
        when(preguntasRepository.findPreguntasElegidasCuestionarioPersonalizado(1L)).thenReturn(preguntas);
        when(preguntasRepository.findPreguntasElegidasCuestionarioPersonalizadoAndAreaIn(1L,
                new ArrayList<>(mapaAreasVisualizarUsuario.keySet()))).thenReturn(preguntas);
        
        Map<AreasCuestionario, List<PreguntasCuestionario>> mapaAreaPreguntas = new HashMap<>();
        mapaAreaPreguntas.put(area, preguntas);
        visualizarCuestionario.setMapaAreaPreguntas(mapaAreaPreguntas);
        
        pregunta.setTipoRespuesta("TABLAtipoRespuesta");
        ConfiguracionRespuestasCuestionario configRespuesta = mock(ConfiguracionRespuestasCuestionario.class);
        
        List<ConfiguracionRespuestasCuestionario> valoresColumnas = new ArrayList<>();
        valoresColumnas.add(configRespuesta);
        when(configuracionRespuestaRepository.findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta()))
                .thenReturn(valoresColumnas);
        ConfiguracionRespuestasCuestionario configuracionRespuestasCuestionario = mock(
                ConfiguracionRespuestasCuestionario.class);
        
        List<ConfiguracionRespuestasCuestionario> list = new ArrayList<>();
        list.add(configuracionRespuestasCuestionario);
        when(configuracionRespuestaRepository.findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta()))
                .thenReturn(list);
        
        String redireccion = visualizarCuestionario.visualizarVacio(cuestionario);
        verify(preguntasRepository, times(1)).findPreguntasElegidasCuestionarioPersonalizado(cuestionario.getId());
        verify(configuracionRespuestaRepository, times(1))
                .findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta());
        verify(datosTablaMock, times(1)).crearTabla(listaConfigRespuestasCaptor.capture());
        assertThat(visualizarCuestionario.getMapaRespuestasTabla().get(pregunta)).isEqualTo(datosTablaMock);
        
        assertThat(visualizarCuestionario.getCuestionarioEnviado()).isNull();
        assertThat(visualizarCuestionario.getMapaRespuestasTabla()).isNotNull();
        assertThat(visualizarCuestionario.getMapaRespuestas()).isNotNull();
        assertThat(visualizarCuestionario.getMapaDocumentos()).isNotNull();
        assertThat(visualizarCuestionario.getMapaAreaPreguntas()).isEqualTo(mapaAreaPreguntas);
        assertThat(visualizarCuestionario.getAreas()).isEqualTo(new ArrayList<>(mapaAreaPreguntas.keySet()));
        assertThat(redireccion).isEqualTo("/cuestionarios/responderCuestionario?faces-redirect=true");
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#visualizarVacio(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado)}.
     */
    @Test
    public final void testVisualizarVacioUserProvisionalSinVisualizarRespuestasMostrarTodas() {
        visualizarCuestionario.setEsUsuarioProvisional(true);
        visualizarCuestionario.setCuestionarioEnviado(mock(CuestionarioEnvio.class));
        CuestionarioPersonalizado cuestionario = new CuestionarioPersonalizado();
        cuestionario.setId(1L);
        AreasCuestionario area = new AreasCuestionario();
        area.setId(1L);
        area.setArea(AREA1);
        PreguntasCuestionario pregunta = new PreguntasCuestionario();
        pregunta.setArea(area);
        pregunta.setPregunta(PREGUNTA1);
        List<PreguntasCuestionario> preguntas = new ArrayList<>();
        preguntas.add(pregunta);
        Map<Long, AreasCuestionario> mapaAreasVisualizarUsuario = new HashMap<>();
        mapaAreasVisualizarUsuario.put(1L, area);
        visualizarCuestionario.setMapaAreasVisualizarUsuario(mapaAreasVisualizarUsuario);
        when(preguntasRepository.findPreguntasElegidasCuestionarioPersonalizado(1L)).thenReturn(preguntas);
        when(preguntasRepository.findPreguntasElegidasCuestionarioPersonalizadoAndAreaIn(1L,
                new ArrayList<>(mapaAreasVisualizarUsuario.keySet()))).thenReturn(preguntas);
        
        Map<AreasCuestionario, List<PreguntasCuestionario>> mapaAreaPreguntas = new HashMap<>();
        mapaAreaPreguntas.put(area, preguntas);
        visualizarCuestionario.setMapaAreaPreguntas(mapaAreaPreguntas);
        
        pregunta.setTipoRespuesta("MATRIZtipoRespuesta");
        ConfiguracionRespuestasCuestionario configRespuesta = mock(ConfiguracionRespuestasCuestionario.class);
        
        List<ConfiguracionRespuestasCuestionario> valoresColumnas = new ArrayList<>();
        valoresColumnas.add(configRespuesta);
        when(configuracionRespuestaRepository.findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta()))
                .thenReturn(valoresColumnas);
        ConfiguracionRespuestasCuestionario configuracionRespuestasCuestionario = mock(
                ConfiguracionRespuestasCuestionario.class);
        
        List<ConfiguracionRespuestasCuestionario> list = new ArrayList<>();
        list.add(configuracionRespuestasCuestionario);
        when(configuracionRespuestaRepository.findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta()))
                .thenReturn(list);
        
        String redireccion = visualizarCuestionario.visualizarVacio(cuestionario);
        verify(preguntasRepository, times(1)).findPreguntasElegidasCuestionarioPersonalizadoAndAreaIn(
                cuestionario.getId(), new ArrayList<Long>(mapaAreasVisualizarUsuario.keySet()));
        verify(configuracionRespuestaRepository, times(1))
                .findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta());
        verify(datosTablaMock, times(1)).crearMatriz(listaConfigRespuestasCaptor.capture());
        assertThat(visualizarCuestionario.getMapaRespuestasTabla().get(pregunta)).isEqualTo(datosTablaMock);
        
        assertThat(visualizarCuestionario.getCuestionarioEnviado()).isNull();
        assertThat(visualizarCuestionario.getMapaRespuestasTabla()).isNotNull();
        assertThat(visualizarCuestionario.getMapaRespuestas()).isNotNull();
        assertThat(visualizarCuestionario.getMapaDocumentos()).isNotNull();
        assertThat(visualizarCuestionario.getMapaAreaPreguntas()).isEqualTo(mapaAreaPreguntas);
        assertThat(visualizarCuestionario.getAreas()).isEqualTo(new ArrayList<>(mapaAreaPreguntas.keySet()));
        assertThat(redireccion).isEqualTo("/cuestionarios/responderCuestionario?faces-redirect=true");
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#visualizarRespuestasCuestionario(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio)}.
     */
    @Test
    public final void testVisualizarRespuestasCuestionarioUserNoProvisional() {
        CuestionarioPersonalizado cuestionario = new CuestionarioPersonalizado();
        cuestionario.setId(1L);
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionario.setId(1L);
        cuestionarioEnviado.setCuestionarioPersonalizado(cuestionario);
        visualizarCuestionario.setCuestionarioEnviado(cuestionarioEnviado);
        
        List<Documento> plantillas = new ArrayList<>();
        plantillas.add(mock(Documento.class));
        when(documentoService.findPlantillas(cuestionario.getId())).thenReturn(plantillas);
        
        AreasCuestionario area = new AreasCuestionario();
        area.setId(1L);
        area.setArea(AREA1);
        PreguntasCuestionario pregunta = new PreguntasCuestionario();
        pregunta.setArea(area);
        pregunta.setPregunta(PREGUNTA1);
        List<PreguntasCuestionario> preguntas = new ArrayList<>();
        preguntas.add(pregunta);
        Map<Long, AreasCuestionario> mapaAreasVisualizarUsuario = new HashMap<>();
        mapaAreasVisualizarUsuario.put(1L, area);
        visualizarCuestionario.setMapaAreasVisualizarUsuario(mapaAreasVisualizarUsuario);
        when(preguntasRepository.findPreguntasElegidasCuestionarioPersonalizado(1L)).thenReturn(preguntas);
        when(preguntasRepository.findPreguntasElegidasCuestionarioPersonalizadoAndAreaIn(1L,
                new ArrayList<>(mapaAreasVisualizarUsuario.keySet()))).thenReturn(preguntas);
        
        Map<AreasCuestionario, List<PreguntasCuestionario>> mapaAreaPreguntas = new HashMap<>();
        mapaAreaPreguntas.put(area, preguntas);
        visualizarCuestionario.setMapaAreaPreguntas(mapaAreaPreguntas);
        
        pregunta.setTipoRespuesta("MATRIZtipoRespuesta");
        ConfiguracionRespuestasCuestionario configRespuesta = mock(ConfiguracionRespuestasCuestionario.class);
        
        List<ConfiguracionRespuestasCuestionario> valoresColumnas = new ArrayList<>();
        valoresColumnas.add(configRespuesta);
        when(configuracionRespuestaRepository.findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta()))
                .thenReturn(valoresColumnas);
        ConfiguracionRespuestasCuestionario configuracionRespuestasCuestionario = mock(
                ConfiguracionRespuestasCuestionario.class);
        
        List<ConfiguracionRespuestasCuestionario> list = new ArrayList<>();
        list.add(configuracionRespuestasCuestionario);
        when(configuracionRespuestaRepository.findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta()))
                .thenReturn(list);
        
        RespuestaCuestionarioId respuestaId = new RespuestaCuestionarioId();
        respuestaId.setCuestionarioEnviado(cuestionarioEnviado);
        respuestaId.setPregunta(pregunta);
        RespuestaCuestionario respuesta = new RespuestaCuestionario();
        respuesta.setRespuestaId(respuestaId);
        respuesta.setRespuestaTablaMatriz(new ArrayList<>());
        List<RespuestaCuestionario> respuestas = new ArrayList<>();
        respuestas.add(respuesta);
        when(respuestaRepository
                .findDistinctByRespuestaIdCuestionarioEnviadoAndFechaValidacionIsNullAndRespuestaIdPreguntaAreaIn(
                        cuestionarioEnviado, null)).thenReturn(respuestas);
        when(respuestaRepository.findDistinctByRespuestaIdCuestionarioEnviado(cuestionarioEnviado))
                .thenReturn(respuestas);
        
        String redireccion = visualizarCuestionario.visualizarRespuestasCuestionario(cuestionarioEnviado);
        verify(respuestaRepository, times(1)).findDistinctByRespuestaIdCuestionarioEnviado(cuestionarioEnviado);
        
        verify(preguntasRepository, times(1)).findPreguntasElegidasCuestionarioPersonalizado(1L);
        verify(configuracionRespuestaRepository, times(1))
                .findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta());
        verify(datosTablaMock, times(1)).crearMatriz(listaConfigRespuestasCaptor.capture());
        assertThat(visualizarCuestionario.getMapaRespuestasTabla().get(pregunta)).isEqualTo(datosTablaMock);
        
        assertThat(visualizarCuestionario.getCuestionarioEnviado()).isNotNull();
        assertThat(visualizarCuestionario.getMapaRespuestasTabla()).isNotNull();
        assertThat(visualizarCuestionario.getMapaRespuestas()).isNotNull();
        assertThat(visualizarCuestionario.getMapaDocumentos()).isNotNull();
        assertThat(visualizarCuestionario.getMapaAreaPreguntas()).isEqualTo(mapaAreaPreguntas);
        assertThat(visualizarCuestionario.getAreas()).isEqualTo(new ArrayList<>(mapaAreaPreguntas.keySet()));
        assertThat(redireccion).isEqualTo("/cuestionarios/validarCuestionario?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#visualizarRespuestasCuestionario(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio)}.
     */
    @Test
    public final void testVisualizarRespuestasCuestionarioUserProvisional() {
        visualizarCuestionario.setEsUsuarioProvisional(true);
        CuestionarioPersonalizado cuestionario = new CuestionarioPersonalizado();
        cuestionario.setId(1L);
        CuestionarioEnvio cuestionarioEnviado = new CuestionarioEnvio();
        cuestionario.setId(1L);
        cuestionarioEnviado.setCuestionarioPersonalizado(cuestionario);
        visualizarCuestionario.setCuestionarioEnviado(cuestionarioEnviado);
        
        List<Documento> plantillas = new ArrayList<>();
        plantillas.add(mock(Documento.class));
        when(documentoService.findPlantillas(cuestionario.getId())).thenReturn(plantillas);
        
        AreasCuestionario area = new AreasCuestionario();
        area.setId(1L);
        area.setArea(AREA1);
        PreguntasCuestionario pregunta = new PreguntasCuestionario();
        pregunta.setArea(area);
        pregunta.setPregunta(PREGUNTA1);
        List<PreguntasCuestionario> preguntas = new ArrayList<>();
        preguntas.add(pregunta);
        Map<Long, AreasCuestionario> mapaAreasVisualizarUsuario = new HashMap<>();
        mapaAreasVisualizarUsuario.put(1L, area);
        visualizarCuestionario.setMapaAreasVisualizarUsuario(mapaAreasVisualizarUsuario);
        when(preguntasRepository.findPreguntasElegidasCuestionarioPersonalizado(1L)).thenReturn(preguntas);
        when(preguntasRepository.findPreguntasElegidasCuestionarioPersonalizadoAndAreaIn(1L,
                new ArrayList<>(mapaAreasVisualizarUsuario.keySet()))).thenReturn(preguntas);
        
        Map<AreasCuestionario, List<PreguntasCuestionario>> mapaAreaPreguntas = new HashMap<>();
        mapaAreaPreguntas.put(area, preguntas);
        visualizarCuestionario.setMapaAreaPreguntas(mapaAreaPreguntas);
        
        pregunta.setTipoRespuesta("MATRIZtipoRespuesta");
        ConfiguracionRespuestasCuestionario configRespuesta = mock(ConfiguracionRespuestasCuestionario.class);
        
        List<ConfiguracionRespuestasCuestionario> valoresColumnas = new ArrayList<>();
        valoresColumnas.add(configRespuesta);
        when(configuracionRespuestaRepository.findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta()))
                .thenReturn(valoresColumnas);
        ConfiguracionRespuestasCuestionario configuracionRespuestasCuestionario = mock(
                ConfiguracionRespuestasCuestionario.class);
        
        List<ConfiguracionRespuestasCuestionario> list = new ArrayList<>();
        list.add(configuracionRespuestasCuestionario);
        when(configuracionRespuestaRepository.findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta()))
                .thenReturn(list);
        
        RespuestaCuestionarioId respuestaId = new RespuestaCuestionarioId();
        respuestaId.setCuestionarioEnviado(cuestionarioEnviado);
        respuestaId.setPregunta(pregunta);
        RespuestaCuestionario respuesta = new RespuestaCuestionario();
        respuesta.setRespuestaId(respuestaId);
        respuesta.setRespuestaTablaMatriz(new ArrayList<>());
        List<RespuestaCuestionario> respuestas = new ArrayList<>();
        respuestas.add(respuesta);
        when(respuestaRepository
                .findDistinctByRespuestaIdCuestionarioEnviadoAndFechaValidacionIsNullAndRespuestaIdPreguntaAreaIn(
                        cuestionarioEnviado, null)).thenReturn(respuestas);
        when(respuestaRepository.findDistinctByRespuestaIdCuestionarioEnviado(cuestionarioEnviado))
                .thenReturn(respuestas);
        
        String redireccion = visualizarCuestionario.visualizarRespuestasCuestionario(cuestionarioEnviado);
        verify(respuestaRepository, times(1))
                .findDistinctByRespuestaIdCuestionarioEnviadoAndFechaValidacionIsNullAndRespuestaIdPreguntaAreaIn(
                        cuestionarioEnviado, null);
        
        verify(preguntasRepository, times(1)).findPreguntasElegidasCuestionarioPersonalizadoAndAreaIn(
                cuestionario.getId(), new ArrayList<Long>(mapaAreasVisualizarUsuario.keySet()));
        verify(configuracionRespuestaRepository, times(1))
                .findByConfigSeccionOrderByConfigClaveAsc(pregunta.getTipoRespuesta());
        verify(datosTablaMock, times(1)).crearMatriz(listaConfigRespuestasCaptor.capture());
        assertThat(visualizarCuestionario.getMapaRespuestasTabla().get(pregunta)).isEqualTo(datosTablaMock);
        
        assertThat(visualizarCuestionario.getCuestionarioEnviado()).isNotNull();
        assertThat(visualizarCuestionario.getMapaRespuestasTabla()).isNotNull();
        assertThat(visualizarCuestionario.getMapaRespuestas()).isNotNull();
        assertThat(visualizarCuestionario.getMapaDocumentos()).isNotNull();
        assertThat(visualizarCuestionario.getMapaAreaPreguntas()).isEqualTo(mapaAreaPreguntas);
        assertThat(visualizarCuestionario.getAreas()).isEqualTo(new ArrayList<>(mapaAreaPreguntas.keySet()));
        assertThat(redireccion).isEqualTo("/cuestionarios/validarCuestionario?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#getValoresTipoRespuesta(java.lang.String)}.
     */
    @Test
    public final void testGetValoresTipoRespuestaCasoTabla() {
        String tipo = "";
        List<String> tiposRespuestaTest = new ArrayList<>();
        tiposRespuestaTest.add("valorTest");
        when(configuracionRespuestaRepository.findValoresPorSeccion(tipo)).thenReturn(tiposRespuestaTest);
        List<String> tipos = visualizarCuestionario.getValoresTipoRespuesta(tipo);
        verify(configuracionRespuestaRepository, times(1)).findValoresPorSeccion(tipo);
        assertThat(tipos).isEqualTo(tiposRespuestaTest);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#construirTipoRespuestaTablaMatrizConDatos()}.
     */
    @Test
    public final void testConstruirTipoRespuestaTablaMatrizConDatos() {
        Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla = new HashMap<>();
        PreguntasCuestionario pregunta = mock(PreguntasCuestionario.class);
        mapaRespuestasTabla.put(pregunta, datosTablaMock);
        visualizarCuestionario.setMapaRespuestasTabla(mapaRespuestasTabla);
        
        Map<PreguntasCuestionario, List<DatosTablaGenerica>> mapaRespuestasTablaAux = new HashMap<>();
        List<DatosTablaGenerica> listaDatos = new ArrayList<>();
        DatosTablaGenerica datosTablaGenerica = mock(DatosTablaGenerica.class);
        listaDatos.add(datosTablaGenerica);
        mapaRespuestasTablaAux.put(pregunta, listaDatos);
        visualizarCuestionario.setMapaRespuestasTablaAux(mapaRespuestasTablaAux);
        
        visualizarCuestionario.construirTipoRespuestaTablaMatrizConDatos();
        
        verify(datosTablaMock, times(1)).crearTablaMatriConDatos(listaDatos);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#aniadirFilaRespuestaTabla(es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario)}.
     */
    @Test
    public final void testAniadirFilaRespuestaTabla() {
        Map<PreguntasCuestionario, DataTableView> mapaRespuestasTabla = new HashMap<>();
        PreguntasCuestionario pregunta = mock(PreguntasCuestionario.class);
        mapaRespuestasTabla.put(pregunta, datosTablaMock);
        visualizarCuestionario.setMapaRespuestasTabla(mapaRespuestasTabla);
        visualizarCuestionario.aniadirFilaRespuestaTabla(pregunta);
        verify(datosTablaMock, times(1)).crearFilaVacia();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#descargarFichero(es.mira.progesin.persistence.entities.gd.Documento)}.
     * @throws ProgesinException lanzada.
     */
    @Test
    public final void testDescargarFichero() throws ProgesinException {
        Documento doc = mock(Documento.class);
        DefaultStreamedContent docDescargado = mock(DefaultStreamedContent.class);
        when(documentoService.descargaDocumento(doc)).thenReturn(docDescargado);
        
        visualizarCuestionario.descargarFichero(doc);
        verify(documentoService, times(1)).descargaDocumento(doc);
        assertThat(visualizarCuestionario.getFile()).isEqualTo(docDescargado);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#descargarFichero(es.mira.progesin.persistence.entities.gd.Documento)}.
     * @throws ProgesinException lanzada.
     */
    @Test
    public final void testDescargarFicheroException() throws ProgesinException {
        Documento doc = mock(Documento.class);
        doThrow(ProgesinException.class).when(documentoService).descargaDocumento(doc);
        
        visualizarCuestionario.descargarFichero(doc);
        verify(documentoService, times(1)).descargaDocumento(doc);
        assertThat(visualizarCuestionario.getFile()).isNull();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(Constantes.ERRORMENSAJE),
                any(String.class));
        regActividadService.altaRegActividadError(any(String.class), any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#crearDocumentoWordCuestionarioPersonalizado(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado)}.
     * @throws ProgesinException lanzada.
     */
    @Test
    public final void testCrearDocumentoWordCuestionarioPersonalizado() throws ProgesinException {
        CuestionarioPersonalizado cuestionarioPersonalizado = mock(CuestionarioPersonalizado.class);
        StreamedContent doc = mock(StreamedContent.class);
        when(wordGenerator.crearDocumentoCuestionarioPersonalizado(cuestionarioPersonalizado)).thenReturn(doc);
        
        visualizarCuestionario.crearDocumentoWordCuestionarioPersonalizado(cuestionarioPersonalizado);
        verify(wordGenerator, times(1)).crearDocumentoCuestionarioPersonalizado(cuestionarioPersonalizado);
        assertThat(visualizarCuestionario.getFile()).isEqualTo(doc);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#crearDocumentoWordCuestionarioPersonalizado(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioPersonalizado)}.
     * @throws ProgesinException lanzada.
     */
    @Test
    public final void testCrearDocumentoWordCuestionarioPersonalizadoProgesinException() throws ProgesinException {
        CuestionarioPersonalizado cuestionarioPersonalizado = mock(CuestionarioPersonalizado.class);
        doThrow(ProgesinException.class).when(wordGenerator)
                .crearDocumentoCuestionarioPersonalizado(cuestionarioPersonalizado);
        
        visualizarCuestionario.crearDocumentoWordCuestionarioPersonalizado(cuestionarioPersonalizado);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(Constantes.ERRORMENSAJE),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(any(String.class), any(ProgesinException.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#crearPdfCuestionarioEnviado(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio)}.
     * @throws ProgesinException lanzada
     */
    @Test
    public final void testCrearPdfCuestionarioEnviado() throws ProgesinException {
        CuestionarioEnvio cuestionarioEnviado = mock(CuestionarioEnvio.class);
        
        visualizarCuestionario.crearPdfCuestionarioEnviado(cuestionarioEnviado);
        verify(pdfGenerator, times(1)).exportarPdf();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#crearPdfCuestionarioEnviado(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio)}.
     * @throws ProgesinException lanzada
     */
    @Test
    public final void testCrearPdfCuestionarioEnviadoProgesinException() throws ProgesinException {
        CuestionarioEnvio cuestionarioEnviado = mock(CuestionarioEnvio.class);
        doThrow(ProgesinException.class).when(pdfGenerator).exportarPdf();
        
        visualizarCuestionario.crearPdfCuestionarioEnviado(cuestionarioEnviado);
        verify(pdfGenerator, times(1)).exportarPdf();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(Constantes.ERRORMENSAJE),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(any(String.class), any(ProgesinException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#init()}.
     */
    @Test
    public final void testInit() {
        User user = new User();
        user.setRole(RoleEnum.ROLE_JEFE_INSPECCIONES);
        when(authentication.getPrincipal()).thenReturn(user);
        visualizarCuestionario.init();
        assertThat(visualizarCuestionario.getUsuarioActual()).isEqualTo(user);
        assertThat(visualizarCuestionario.isEsUsuarioProvisional()).isFalse();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.VisualizarCuestionario#generarMapaAreasVisualizarUsuario()}.
     */
    @Test
    public final void testGenerarMapaAreasVisualizarUsuario() {
        AreasCuestionario area = new AreasCuestionario();
        area.setId(1L);
        area.setArea("area_test");
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        visualizarCuestionario.setListaAreasVisualizarUsuario(areas);
        visualizarCuestionario.generarMapaAreasVisualizarUsuario();
        assertThat(visualizarCuestionario.getMapaAreasVisualizarUsuario().get(1L)).isEqualTo(area);
    }
    
}
