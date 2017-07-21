/**
 * 
 */
package es.mira.progesin.web.beans.cuestionarios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.ConfiguracionRespuestasCuestionarioId;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.cuestionarios.PreguntasCuestionario;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.enums.TiposRespuestasPersonalizables;
import es.mira.progesin.persistence.repositories.IConfiguracionRespuestasCuestionarioRepository;
import es.mira.progesin.persistence.repositories.IPreguntaCuestionarioRepository;
import es.mira.progesin.services.IAreaCuestionarioService;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.DataTableView;
import es.mira.progesin.util.FacesUtilities;

/**
 * 
 * Test del bean ModificarModeloCuestionarioBean.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class, ModificarModeloCuestionarioBean.class })
public class ModificarModeloCuestionarioBeanTest {
    /**
     * Constante nombre de área.
     */
    private static final String NOMBREAREATEST = "area_test";
    
    /**
     * Constante pregunta.
     */
    private static final String PREGUNTATEST = "pregunta_test";
    
    /**
     * Constante user.
     */
    private static final String USUARIOLOGUEADO = "usuarioLogueado";
    
    /**
     * Constante pasi tipo respuestas.
     */
    private static final String PASOTIPORESPUESTAS = "tipoRespuestas";
    
    /**
     * Constante paso finalizar.
     */
    private static final String PASOFINALIZAR = "finalizar";
    
    /**
     * Constante valor test.
     */
    private static final String VALORTEST = "valor_test";
    
    /**
     * Constante valor fila.
     */
    private static final String VALORFILATEST = "valorFila_test";
    
    /**
     * Constante valor fila.
     */
    private static final String INPUT = "INPUT";
    
    /**
     * Constante valor matriz input.
     */
    private static final String SECCIONMATRIZINPUT = "MATRIZINPUT";
    
    /**
     * Constante valor valoresFila_test.
     */
    private static final String VALORESFILATEST = "valoresFila_test";
    
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
     * Mock de repositorio de areas de cuestionario.
     */
    @Mock
    private IAreaCuestionarioService areaCuestionarioService;
    
    /**
     * Mock de repositorio de preguntas.
     */
    @Mock
    private IPreguntaCuestionarioRepository preguntasCuestionarioRepository;
    
    /**
     * Mock de servicio de modelos de cuestionario.
     */
    @Mock
    private IModeloCuestionarioService modeloCuestionarioService;
    
    /**
     * Mock de servicio de registro de actividad.
     */
    @Mock
    private IRegistroActividadService registroActividadService;
    
    /**
     * Mock de repositorio de configuración de preguntas.
     */
    @Mock
    private IConfiguracionRespuestasCuestionarioRepository configuracionRespuestasCuestionarioRepository;
    
    /**
     * Mock de DataTableViews.
     */
    @Mock
    private DataTableView datosTabla;
    
    /**
     * Simulacion de ModificarModeloCuestionarioBean.
     */
    @InjectMocks
    private ModificarModeloCuestionarioBean modificarModeloCuestionarioBean;
    
    /**
     * Comprueba que la clase existe.
     */
    @Test
    public void type() {
        assertThat(ModificarModeloCuestionarioBean.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     */
    @Test
    public void instantiation() {
        ModificarModeloCuestionarioBean target = new ModificarModeloCuestionarioBean();
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
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USUARIOLOGUEADO);
        PowerMockito.whenNew(DataTableView.class).withNoArguments().thenReturn(datosTabla);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#editarModelo(es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario)}.
     */
    @Test
    public final void testEditarModelo() {
        ModeloCuestionario modelo = new ModeloCuestionario();
        modelo.setId(1);
        
        List<AreasCuestionario> listaAreasCuestionario = new ArrayList<>();
        AreasCuestionario area1 = new AreasCuestionario();
        area1.setId(1L);
        area1.setIdCuestionario(1);
        area1.setArea("area1");
        listaAreasCuestionario.add(area1);
        List<PreguntasCuestionario> preguntas = new ArrayList<>();
        PreguntasCuestionario pregunta1 = new PreguntasCuestionario();
        pregunta1.setId(1L);
        pregunta1.setArea(area1);
        pregunta1.setPregunta("pregunta1_area1");
        PreguntasCuestionario pregunta2 = new PreguntasCuestionario();
        pregunta2.setId(2L);
        pregunta2.setArea(area1);
        pregunta2.setPregunta("pregunta2_area1");
        preguntas.add(pregunta1);
        preguntas.add(pregunta2);
        List<String> tiposPregunta = new ArrayList<>();
        tiposPregunta.add(INPUT);
        tiposPregunta.add("RADIO");
        
        when(areaCuestionarioService.findDistinctByIdCuestionarioOrderByOrdenAsc(modelo.getId()))
                .thenReturn(listaAreasCuestionario);
        
        when(areaCuestionarioService.findDistinctByIdCuestionarioAndFechaBajaIsNullOrderByOrdenAsc(modelo.getId()))
                .thenReturn(listaAreasCuestionario);
        
        when(preguntasCuestionarioRepository.findByAreaAndFechaBajaIsNullOrderByOrdenAsc(area1)).thenReturn(preguntas);
        
        when(configuracionRespuestasCuestionarioRepository.findAllDistinctTipoRespuestaOrderByTipoRespuestaAsc())
                .thenReturn(tiposPregunta);
        
        String redireccion = modificarModeloCuestionarioBean.editarModelo(modelo);
        
        assertThat(modificarModeloCuestionarioBean.isEsNuevoModelo()).isFalse();
        assertThat(modificarModeloCuestionarioBean.getModeloCuestionario()).isEqualTo(modelo);
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionario()).isEqualTo(listaAreasCuestionario);
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionarioVisualizar())
                .isEqualTo(listaAreasCuestionario);
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionarioVisualizar().get(0).getPreguntas())
                .isEqualTo(preguntas);
        
        assertThat(modificarModeloCuestionarioBean.getListaTipoPreguntas()).isEqualTo(tiposPregunta);
        assertThat(modificarModeloCuestionarioBean.getListaTipoPreguntasFinal()).isNotNull();
        assertThat(modificarModeloCuestionarioBean.getListaTipoPreguntasPick().getSource()).isEqualTo(tiposPregunta);
        assertThat(modificarModeloCuestionarioBean.getListaTipoPreguntasPick().getTarget()).isNotNull();
        
        assertThat(modificarModeloCuestionarioBean.getListadoValoresNuevaRespuesta()).isNotNull();
        assertThat(modificarModeloCuestionarioBean.getListadoValoresFila()).isNotNull();
        assertThat(modificarModeloCuestionarioBean.getListaTiposPersonalizables())
                .isEqualTo(Arrays.asList(TiposRespuestasPersonalizables.values()));
        assertThat(modificarModeloCuestionarioBean.getTipoPersonalizado()).isEmpty();
        
        assertThat(redireccion).isEqualTo("/cuestionarios/modificarModeloCuestionario?faces-redirect=true");
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#nuevoModelo()}.
     */
    @Test
    public final void testNuevoModelo() {
        List<String> tiposPregunta = new ArrayList<>();
        tiposPregunta.add(INPUT);
        tiposPregunta.add("RADIO");
        
        when(configuracionRespuestasCuestionarioRepository.findAllDistinctTipoRespuestaOrderByTipoRespuestaAsc())
                .thenReturn(tiposPregunta);
        
        String redireccion = modificarModeloCuestionarioBean.nuevoModelo();
        assertThat(modificarModeloCuestionarioBean.isEsNuevoModelo()).isTrue();
        assertThat(modificarModeloCuestionarioBean.getModeloCuestionario()).isNotNull();
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionario()).isNotNull();
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionarioVisualizar()).isNotNull();
        assertThat(modificarModeloCuestionarioBean.getListaTipoPreguntas()).isEqualTo(tiposPregunta);
        assertThat(modificarModeloCuestionarioBean.getListaTipoPreguntasFinal()).isNotNull();
        assertThat(modificarModeloCuestionarioBean.getListaTipoPreguntasPick().getSource()).isNotNull();
        assertThat(modificarModeloCuestionarioBean.getListaTipoPreguntasPick().getSource())
                .isEqualTo(modificarModeloCuestionarioBean.getListaTipoPreguntas());
        assertThat(modificarModeloCuestionarioBean.getListadoValoresNuevaRespuesta()).isNotNull();
        assertThat(modificarModeloCuestionarioBean.getListadoValoresFila()).isNotNull();
        assertThat(modificarModeloCuestionarioBean.getTipoPersonalizado()).isEmpty();
        assertThat(redireccion).isEqualTo("/cuestionarios/modificarModeloCuestionario?faces-redirect=true");
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#aniadeArea(java.lang.String)}.
     */
    @Test
    public final void testAniadeArea() {
        String nombreArea = NOMBREAREATEST;
        ModeloCuestionario modeloCuestionario = new ModeloCuestionario();
        modeloCuestionario.setId(1);
        modificarModeloCuestionarioBean.setModeloCuestionario(modeloCuestionario);
        modificarModeloCuestionarioBean.setListaAreasCuestionario(new ArrayList<>());
        modificarModeloCuestionarioBean.setListaAreasCuestionarioVisualizar(new ArrayList<>());
        modificarModeloCuestionarioBean.aniadeArea(nombreArea);
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionario().get(0).getArea()).isEqualTo(nombreArea);
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionario().get(0).getIdCuestionario())
                .isEqualTo(modeloCuestionario.getId());
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionarioVisualizar().get(0).getArea())
                .isEqualTo(nombreArea);
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionarioVisualizar().get(0).getIdCuestionario())
                .isEqualTo(modeloCuestionario.getId());
        assertThat(modificarModeloCuestionarioBean.getModeloCuestionario().getAreas())
                .isEqualTo(modificarModeloCuestionarioBean.getListaAreasCuestionario());
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#onSelectArea(org.primefaces.event.SelectEvent)}.
     */
    @Test
    public final void testOnSelectArea() {
        SelectEvent event = mock(SelectEvent.class);
        AreasCuestionario area = mock(AreasCuestionario.class);
        when(event.getObject()).thenReturn(area);
        modificarModeloCuestionarioBean.onSelectArea(event);
        assertThat(modificarModeloCuestionarioBean.getAreaSelec()).isEqualTo(area);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#borraArea()}.
     */
    @Test
    public final void testBorraAreaUsadaBajaLogica() {
        AreasCuestionario area = new AreasCuestionario();
        area.setId(1L);
        area.setArea(NOMBREAREATEST);
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        List<AreasCuestionario> areasVisualizar = new ArrayList<>();
        areasVisualizar.add(area);
        modificarModeloCuestionarioBean.setAreaSelec(area);
        modificarModeloCuestionarioBean.setListaAreasCuestionario(areas);
        modificarModeloCuestionarioBean.setModeloCuestionario(new ModeloCuestionario());
        modificarModeloCuestionarioBean.setListaAreasCuestionarioVisualizar(areasVisualizar);
        when(areaCuestionarioService.findAreaExistenteEnCuestionariosPersonalizados(1L)).thenReturn(area);
        
        modificarModeloCuestionarioBean.borraArea();
        verify(areaCuestionarioService, timeout(1)).findAreaExistenteEnCuestionariosPersonalizados(1L);
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionario().contains(area)).isTrue();
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionarioVisualizar().contains(area)).isFalse();
        assertThat(modificarModeloCuestionarioBean.getModeloCuestionario().getAreas().get(0).getFechaBaja())
                .isNotNull();
        assertThat(modificarModeloCuestionarioBean.getModeloCuestionario().getAreas().get(0).getUsernameBaja())
                .isNotNull();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#borraArea()}.
     */
    @Test
    public final void testBorraAreaUsadaBajaFisica() {
        AreasCuestionario area = new AreasCuestionario();
        area.setId(1L);
        area.setArea(NOMBREAREATEST);
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        List<AreasCuestionario> areasVisualizar = new ArrayList<>();
        areasVisualizar.add(area);
        modificarModeloCuestionarioBean.setAreaSelec(area);
        modificarModeloCuestionarioBean.setListaAreasCuestionario(areas);
        modificarModeloCuestionarioBean.setModeloCuestionario(new ModeloCuestionario());
        modificarModeloCuestionarioBean.setListaAreasCuestionarioVisualizar(areasVisualizar);
        when(areaCuestionarioService.findAreaExistenteEnCuestionariosPersonalizados(1L)).thenReturn(null);
        
        modificarModeloCuestionarioBean.borraArea();
        verify(areaCuestionarioService, timeout(1)).findAreaExistenteEnCuestionariosPersonalizados(1L);
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionario().contains(area)).isFalse();
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionarioVisualizar().contains(area)).isFalse();
        assertThat(modificarModeloCuestionarioBean.getModeloCuestionario().getAreas())
                .isEqualTo(modificarModeloCuestionarioBean.getListaAreasCuestionario());
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#borraArea()}.
     */
    @Test
    public final void testBorraAreaSinUsar() {
        AreasCuestionario area = new AreasCuestionario();
        area.setArea("area_test");
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        List<AreasCuestionario> areasVisualizar = new ArrayList<>();
        areasVisualizar.add(area);
        modificarModeloCuestionarioBean.setAreaSelec(area);
        modificarModeloCuestionarioBean.setListaAreasCuestionario(areas);
        modificarModeloCuestionarioBean.setModeloCuestionario(new ModeloCuestionario());
        modificarModeloCuestionarioBean.setListaAreasCuestionarioVisualizar(areasVisualizar);
        
        modificarModeloCuestionarioBean.borraArea();
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionario().contains(area)).isFalse();
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionarioVisualizar().contains(area)).isFalse();
        assertThat(modificarModeloCuestionarioBean.getModeloCuestionario().getAreas())
                .isEqualTo(modificarModeloCuestionarioBean.getListaAreasCuestionario());
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#aniadePregunta(es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testAniadePreguntaModificarArea() {
        AreasCuestionario area = new AreasCuestionario();
        area.setArea(NOMBREAREATEST);
        area.setId(1L);
        area.setPreguntas(new ArrayList<>());
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        modificarModeloCuestionarioBean.setListaAreasCuestionario(areas);
        PreguntasCuestionario pregunta1 = new PreguntasCuestionario();
        pregunta1.setArea(area);
        pregunta1.setPregunta(PREGUNTATEST);
        pregunta1.setTipoRespuesta(INPUT);
        
        String preguntaNueva = "pregunta_test_nueva";
        String tipoNueva = "TEXTAREA";
        
        modificarModeloCuestionarioBean.aniadePregunta(area, preguntaNueva, tipoNueva);
        assertThat(area.getPreguntas().get(0).getPregunta()).isEqualTo(preguntaNueva);
        assertThat(area.getPreguntas().get(0).getTipoRespuesta()).isEqualTo(tipoNueva);
        assertThat(
                modificarModeloCuestionarioBean.getListaAreasCuestionario().get(0).getPreguntas().get(0).getPregunta())
                        .isEqualTo(preguntaNueva);
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionario().get(0).getPreguntas().get(0)
                .getTipoRespuesta()).isEqualTo(tipoNueva);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#aniadePregunta(es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testAniadePreguntaNuevoArea() {
        AreasCuestionario area = new AreasCuestionario();
        area.setArea(NOMBREAREATEST);
        area.setPreguntas(new ArrayList<>());
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        modificarModeloCuestionarioBean.setListaAreasCuestionario(areas);
        PreguntasCuestionario pregunta1 = new PreguntasCuestionario();
        pregunta1.setArea(area);
        pregunta1.setPregunta(PREGUNTATEST);
        pregunta1.setTipoRespuesta(INPUT);
        
        String preguntaNueva = "pregunta_test_nueva";
        String tipoNueva = "TEXTAREA";
        
        modificarModeloCuestionarioBean.aniadePregunta(area, preguntaNueva, tipoNueva);
        assertThat(area.getPreguntas().get(0).getPregunta()).isEqualTo(preguntaNueva);
        assertThat(area.getPreguntas().get(0).getTipoRespuesta()).isEqualTo(tipoNueva);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#aniadePregunta(es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testAniadePreguntaVacia() {
        String preguntaNueva = "";
        String tipoNueva = "TEXTAREA";
        
        modificarModeloCuestionarioBean.aniadePregunta(mock(AreasCuestionario.class), preguntaNueva, tipoNueva);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), eq(null), eq(null));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#aniadePregunta(es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testAniadeTipoPreguntaVacia() {
        String preguntaNueva = PREGUNTATEST;
        String tipoNueva = "";
        
        modificarModeloCuestionarioBean.aniadePregunta(mock(AreasCuestionario.class), preguntaNueva, tipoNueva);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), eq(null), eq(null));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#onSelectPregunta(org.primefaces.event.SelectEvent)}.
     */
    @Test
    public final void testOnSelectPregunta() {
        SelectEvent event = mock(SelectEvent.class);
        PreguntasCuestionario pregunta = mock(PreguntasCuestionario.class);
        when(event.getObject()).thenReturn(pregunta);
        modificarModeloCuestionarioBean.onSelectPregunta(event);
        assertThat(modificarModeloCuestionarioBean.getPreguntaSelec()).isEqualTo(pregunta);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#borraPregunta(es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario)}.
     */
    @Test
    public final void testBorraPreguntaBajaLogica() {
        AreasCuestionario area = new AreasCuestionario();
        area.setArea(NOMBREAREATEST);
        area.setId(1L);
        PreguntasCuestionario pregunta1 = new PreguntasCuestionario();
        pregunta1.setId(2L);
        pregunta1.setArea(area);
        pregunta1.setPregunta(PREGUNTATEST);
        pregunta1.setTipoRespuesta(INPUT);
        List<PreguntasCuestionario> preguntas = new ArrayList<>();
        preguntas.add(pregunta1);
        area.setPreguntas(preguntas);
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        modificarModeloCuestionarioBean.setPreguntaSelec(pregunta1);
        
        modificarModeloCuestionarioBean.setListaAreasCuestionario(areas);
        
        when(preguntasCuestionarioRepository.findPreguntaExistenteEnCuestionariosPersonalizados(2L))
                .thenReturn(pregunta1);
        
        modificarModeloCuestionarioBean.borraPregunta(area);
        
        verify(preguntasCuestionarioRepository, times(1)).findPreguntaExistenteEnCuestionariosPersonalizados(2L);
        assertThat(area.getPreguntas()).isEmpty();
        assertThat(modificarModeloCuestionarioBean.getPreguntaSelec().getFechaBaja()).isNotNull();
        assertThat(modificarModeloCuestionarioBean.getPreguntaSelec().getUsernameBaja()).isNotNull();
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#borraPregunta(es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario)}.
     */
    @Test
    public final void testBorraPreguntaBajaFisica() {
        AreasCuestionario area = new AreasCuestionario();
        area.setArea(NOMBREAREATEST);
        area.setId(1L);
        PreguntasCuestionario pregunta1 = new PreguntasCuestionario();
        pregunta1.setId(2L);
        pregunta1.setArea(area);
        pregunta1.setPregunta(PREGUNTATEST);
        pregunta1.setTipoRespuesta(INPUT);
        List<PreguntasCuestionario> preguntas = new ArrayList<>();
        preguntas.add(pregunta1);
        area.setPreguntas(preguntas);
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        modificarModeloCuestionarioBean.setPreguntaSelec(pregunta1);
        
        modificarModeloCuestionarioBean.setListaAreasCuestionario(areas);
        
        when(preguntasCuestionarioRepository.findPreguntaExistenteEnCuestionariosPersonalizados(2L)).thenReturn(null);
        
        modificarModeloCuestionarioBean.borraPregunta(area);
        
        verify(preguntasCuestionarioRepository, times(1)).findPreguntaExistenteEnCuestionariosPersonalizados(2L);
        assertThat(area.getPreguntas()).isEmpty();
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionario().get(0).getPreguntas()).hasSize(0);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#borraPregunta(es.mira.progesin.persistence.entities.cuestionarios.AreasCuestionario)}.
     */
    @Test
    public final void testBorraPreguntaNueva() {
        AreasCuestionario area = new AreasCuestionario();
        area.setArea(NOMBREAREATEST);
        PreguntasCuestionario pregunta1 = new PreguntasCuestionario();
        pregunta1.setArea(area);
        pregunta1.setPregunta(PREGUNTATEST);
        pregunta1.setTipoRespuesta(INPUT);
        List<PreguntasCuestionario> preguntas = new ArrayList<>();
        preguntas.add(pregunta1);
        area.setPreguntas(preguntas);
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        modificarModeloCuestionarioBean.setPreguntaSelec(pregunta1);
        
        modificarModeloCuestionarioBean.setListaAreasCuestionario(areas);
        
        modificarModeloCuestionarioBean.borraPregunta(area);
        
        assertThat(area.getPreguntas()).isEmpty();
        assertThat(modificarModeloCuestionarioBean.getListaAreasCuestionario().get(0).getPreguntas()).hasSize(0);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#onFlowProcess(org.primefaces.event.FlowEvent)}.
     */
    @Test
    public final void testOnFlowProcessStepTipoRespuestasSinAreas() {
        FlowEvent event = mock(FlowEvent.class);
        when(event.getNewStep()).thenReturn(PASOTIPORESPUESTAS);
        when(event.getOldStep()).thenReturn(PASOTIPORESPUESTAS);
        modificarModeloCuestionarioBean.setListaAreasCuestionario(new ArrayList<>());
        
        String siguientePaso = modificarModeloCuestionarioBean.onFlowProcess(event);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), eq(null), eq(null));
        assertThat(siguientePaso).isEqualTo(PASOTIPORESPUESTAS);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#onFlowProcess(org.primefaces.event.FlowEvent)}.
     */
    @Test
    public final void testOnFlowProcessStepPreguntas() {
        AreasCuestionario area = new AreasCuestionario();
        area.setArea(NOMBREAREATEST);
        PreguntasCuestionario pregunta1 = new PreguntasCuestionario();
        pregunta1.setArea(area);
        pregunta1.setPregunta(PREGUNTATEST);
        pregunta1.setTipoRespuesta(INPUT);
        List<PreguntasCuestionario> preguntas = new ArrayList<>();
        preguntas.add(pregunta1);
        area.setPreguntas(preguntas);
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        modificarModeloCuestionarioBean.setListaAreasCuestionarioVisualizar(areas);
        FlowEvent event = mock(FlowEvent.class);
        when(event.getNewStep()).thenReturn("cualquier cosa", "preguntas", "lo que sea", PASOFINALIZAR);
        modificarModeloCuestionarioBean.setListaAreasCuestionario(new ArrayList<>());
        
        String siguientePaso = modificarModeloCuestionarioBean.onFlowProcess(event);
        
        assertThat(siguientePaso).isEqualTo(PASOFINALIZAR);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#onFlowProcess(org.primefaces.event.FlowEvent)}.
     */
    @Test
    public final void testOnFlowProcessStepFinalizar() {
        AreasCuestionario area = new AreasCuestionario();
        area.setArea(NOMBREAREATEST);
        PreguntasCuestionario pregunta1 = new PreguntasCuestionario();
        pregunta1.setArea(area);
        pregunta1.setPregunta(PREGUNTATEST);
        pregunta1.setTipoRespuesta(INPUT);
        List<PreguntasCuestionario> preguntas = new ArrayList<>();
        preguntas.add(pregunta1);
        area.setPreguntas(preguntas);
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        modificarModeloCuestionarioBean.setListaAreasCuestionarioVisualizar(areas);
        modificarModeloCuestionarioBean.setListaAreasCuestionario(areas);
        modificarModeloCuestionarioBean.setModeloCuestionario(new ModeloCuestionario());
        
        FlowEvent event = mock(FlowEvent.class);
        when(event.getNewStep()).thenReturn("algo", "lo que sea", PASOFINALIZAR, "ok");
        
        String siguientePaso = modificarModeloCuestionarioBean.onFlowProcess(event);
        assertThat(modificarModeloCuestionarioBean.getModeloCuestionario().getAreas())
                .isEqualTo(modificarModeloCuestionarioBean.getListaAreasCuestionario());
        assertThat(siguientePaso).isEqualTo("ok");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#onFlowProcess(org.primefaces.event.FlowEvent)}.
     */
    @Test
    public final void testOnFlowProcessStepFinalizarAreaSinPreguntas() {
        AreasCuestionario area = new AreasCuestionario();
        area.setArea(NOMBREAREATEST);
        area.setPreguntas(new ArrayList<>());
        List<AreasCuestionario> areas = new ArrayList<>();
        areas.add(area);
        modificarModeloCuestionarioBean.setListaAreasCuestionarioVisualizar(areas);
        modificarModeloCuestionarioBean.setListaAreasCuestionario(areas);
        modificarModeloCuestionarioBean.setModeloCuestionario(new ModeloCuestionario());
        
        FlowEvent event = mock(FlowEvent.class);
        when(event.getNewStep()).thenReturn("algo", "lo que sea", PASOFINALIZAR);
        when(event.getOldStep()).thenReturn(PASOFINALIZAR);
        
        String siguientePaso = modificarModeloCuestionarioBean.onFlowProcess(event);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), eq(null), eq(null));
        assertThat(siguientePaso).isEqualTo(PASOFINALIZAR);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#getValoresTipoRespuesta(java.lang.String)}.
     */
    @Test
    public final void testGetValoresTipoRespuesta() {
        List<String> valores = new ArrayList<>();
        valores.add("");
        when(configuracionRespuestasCuestionarioRepository.findValoresPorSeccion("")).thenReturn(valores);
        List<String> listaValores = modificarModeloCuestionarioBean.getValoresTipoRespuesta("");
        verify(configuracionRespuestasCuestionarioRepository, times(1)).findValoresPorSeccion("");
        assertThat(listaValores).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#aniadeValor(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testAniadeValorRADIO() {
        String valor = VALORTEST;
        String tipoRespuesta = "RADIO";
        modificarModeloCuestionarioBean.setListadoValoresNuevaRespuesta(new ArrayList<>());
        modificarModeloCuestionarioBean.aniadeValor(valor, tipoRespuesta);
        assertThat(modificarModeloCuestionarioBean.getListadoValoresNuevaRespuesta().get(0)).isEqualTo(valor);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#aniadeValor(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testAniadeValorNoRADIO() {
        String valor = VALORTEST;
        String tipoRespuesta = "tipo_test";
        modificarModeloCuestionarioBean.setListadoValoresNuevaRespuesta(new ArrayList<>());
        for (int i = 0; i < 20; i++) {
            modificarModeloCuestionarioBean.getListadoValoresNuevaRespuesta().add(String.valueOf(i));
        }
        modificarModeloCuestionarioBean.aniadeValor(valor, tipoRespuesta);
        assertThat(modificarModeloCuestionarioBean.getListadoValoresNuevaRespuesta().size()).isEqualTo(20);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#borraValor()}.
     */
    @Test
    public final void testBorraValor() {
        List<String> valores = new ArrayList<>();
        valores.add(VALORTEST);
        modificarModeloCuestionarioBean.setListadoValoresNuevaRespuesta(valores);
        modificarModeloCuestionarioBean.setValorSeleccionado(VALORTEST);
        modificarModeloCuestionarioBean.borraValor();
        assertThat(modificarModeloCuestionarioBean.getListadoValoresNuevaRespuesta().contains(VALORTEST)).isFalse();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#onSelectValor(org.primefaces.event.SelectEvent)}.
     */
    @Test
    public final void testOnSelectValor() {
        SelectEvent event = mock(SelectEvent.class);
        when(event.getObject()).thenReturn(VALORTEST);
        modificarModeloCuestionarioBean.onSelectValor(event);
        assertThat(modificarModeloCuestionarioBean.getValorSeleccionado()).isEqualTo(VALORTEST);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#aniadeValorFila(java.lang.String)}.
     */
    @Test
    public final void testAniadeValorFila() {
        String valor = VALORFILATEST;
        modificarModeloCuestionarioBean.setListadoValoresFila(new ArrayList<>());
        modificarModeloCuestionarioBean.aniadeValorFila(valor);
        assertThat(modificarModeloCuestionarioBean.getListadoValoresFila()).contains(VALORFILATEST);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#borraValorFila()}.
     */
    @Test
    public final void testBorraValorFila() {
        String valor = VALORFILATEST;
        modificarModeloCuestionarioBean.setValorSeleccionado(valor);
        List<String> valores = new ArrayList<>();
        valores.add(valor);
        modificarModeloCuestionarioBean.setListadoValoresFila(valores);
        modificarModeloCuestionarioBean.borraValorFila();
        assertThat(modificarModeloCuestionarioBean.getListadoValoresFila().contains(VALORFILATEST)).isFalse();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#guardaTipoRespuesta(java.lang.String)}.
     */
    @Test
    public final void testGuardaTipoRespuestaVacia() {
        String nombreTipoPregunta = "";
        modificarModeloCuestionarioBean.guardaTipoRespuesta(nombreTipoPregunta);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), eq(null), eq(null));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#guardaTipoRespuesta(java.lang.String)}.
     */
    @Test
    public final void testGuardaTipoRespuestaValoresRespuestaIsEmpty() {
        String nombreTipoPregunta = INPUT;
        modificarModeloCuestionarioBean.setListadoValoresNuevaRespuesta(new ArrayList<>());
        modificarModeloCuestionarioBean.guardaTipoRespuesta(nombreTipoPregunta);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), eq(null), eq(null));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#guardaTipoRespuesta(java.lang.String)}.
     */
    @Test
    public final void testGuardaTipoRespuestaMatrizIncompleta() {
        String nombreTipoPregunta = INPUT;
        List<String> valores = new ArrayList<>();
        valores.add(VALORTEST);
        modificarModeloCuestionarioBean.setListadoValoresNuevaRespuesta(valores);
        modificarModeloCuestionarioBean.setTipoPersonalizado(Constantes.TIPORESPUESTAMATRIZ);
        modificarModeloCuestionarioBean.setListadoValoresFila(new ArrayList<>());
        
        modificarModeloCuestionarioBean.guardaTipoRespuesta(nombreTipoPregunta);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), eq(null), eq(null));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#guardaTipoRespuesta(java.lang.String)}.
     */
    @Test
    public final void testGuardaTipoRespuesta() {
        String nombreTipoPregunta = INPUT;
        List<String> valoresNuevaRespuesta = new ArrayList<>();
        valoresNuevaRespuesta.add(VALORTEST);
        modificarModeloCuestionarioBean.setListadoValoresNuevaRespuesta(valoresNuevaRespuesta);
        modificarModeloCuestionarioBean.setTipoPersonalizado(Constantes.TIPORESPUESTAMATRIZ);
        List<String> valoresNuevaFila = new ArrayList<>();
        valoresNuevaFila.add(VALORESFILATEST);
        modificarModeloCuestionarioBean.setListadoValoresFila(valoresNuevaFila);
        
        List<ConfiguracionRespuestasCuestionario> tipoRespuestaNuevo = new ArrayList<>();
        
        ConfiguracionRespuestasCuestionarioId cId = new ConfiguracionRespuestasCuestionarioId();
        cId.setClave("campo01");
        cId.setValor(VALORTEST);
        cId.setSeccion(SECCIONMATRIZINPUT);
        ConfiguracionRespuestasCuestionario c = new ConfiguracionRespuestasCuestionario();
        c.setConfig(cId);
        tipoRespuestaNuevo.add(c);
        
        ConfiguracionRespuestasCuestionarioId cIdFila = new ConfiguracionRespuestasCuestionarioId();
        cIdFila.setClave("nombreFila01");
        cIdFila.setValor(VALORESFILATEST);
        cIdFila.setSeccion(SECCIONMATRIZINPUT);
        ConfiguracionRespuestasCuestionario cFila = new ConfiguracionRespuestasCuestionario();
        cFila.setConfig(cIdFila);
        tipoRespuestaNuevo.add(cFila);
        
        List<String> tipoPreguntas = new ArrayList<>();
        tipoPreguntas.add(SECCIONMATRIZINPUT);
        when(configuracionRespuestasCuestionarioRepository.findAllDistinctTipoRespuestaOrderByTipoRespuestaAsc())
                .thenReturn(tipoPreguntas);
        modificarModeloCuestionarioBean.setListaTipoPreguntasPick(new DualListModel<>());
        
        modificarModeloCuestionarioBean.guardaTipoRespuesta(nombreTipoPregunta);
        verify(configuracionRespuestasCuestionarioRepository, times(1)).save(tipoRespuestaNuevo);
        assertThat(modificarModeloCuestionarioBean.getListaTipoPreguntasPick().getSource()).isEqualTo(tipoPreguntas);
        assertThat(modificarModeloCuestionarioBean.getListadoValoresNuevaRespuesta()).isEmpty();
        assertThat(modificarModeloCuestionarioBean.getTipoPersonalizado()).isEqualTo("");
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#guardaTipoRespuesta(java.lang.String)}.
     */
    @Test
    public final void testGuardaTipoRespuestaException() {
        String nombreTipoPregunta = INPUT;
        List<String> valoresNuevaRespuesta = new ArrayList<>();
        valoresNuevaRespuesta.add(VALORTEST);
        modificarModeloCuestionarioBean.setListadoValoresNuevaRespuesta(valoresNuevaRespuesta);
        modificarModeloCuestionarioBean.setTipoPersonalizado(Constantes.TIPORESPUESTAMATRIZ);
        List<String> valoresNuevaFila = new ArrayList<>();
        valoresNuevaFila.add(VALORESFILATEST);
        modificarModeloCuestionarioBean.setListadoValoresFila(valoresNuevaFila);
        
        List<ConfiguracionRespuestasCuestionario> tipoRespuestaNuevo = new ArrayList<>();
        
        ConfiguracionRespuestasCuestionarioId cId = new ConfiguracionRespuestasCuestionarioId();
        cId.setClave("campo01");
        cId.setValor(VALORTEST);
        cId.setSeccion(SECCIONMATRIZINPUT);
        ConfiguracionRespuestasCuestionario c = new ConfiguracionRespuestasCuestionario();
        c.setConfig(cId);
        tipoRespuestaNuevo.add(c);
        
        ConfiguracionRespuestasCuestionarioId cIdFila = new ConfiguracionRespuestasCuestionarioId();
        cIdFila.setClave("nombreFila01");
        cIdFila.setValor(VALORESFILATEST);
        cIdFila.setSeccion(SECCIONMATRIZINPUT);
        ConfiguracionRespuestasCuestionario cFila = new ConfiguracionRespuestasCuestionario();
        cFila.setConfig(cIdFila);
        tipoRespuestaNuevo.add(cFila);
        
        List<String> tipoPreguntas = new ArrayList<>();
        tipoPreguntas.add(SECCIONMATRIZINPUT);
        when(configuracionRespuestasCuestionarioRepository.save(tipoRespuestaNuevo))
                .thenThrow(TransientDataAccessResourceException.class);
        
        modificarModeloCuestionarioBean.guardaTipoRespuesta(nombreTipoPregunta);
        verify(configuracionRespuestasCuestionarioRepository, times(1)).save(tipoRespuestaNuevo);
        verify(registroActividadService, times(1)).altaRegActividadError(
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()), any(TransientDataAccessResourceException.class));
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#guardaCuestionario()}.
     */
    @Test
    public final void testGuardaCuestionario() {
        ModeloCuestionario modeloCuestionario = new ModeloCuestionario();
        modeloCuestionario.setId(1);
        modeloCuestionario.setDescripcion("descripcion_test");
        modificarModeloCuestionarioBean.setModeloCuestionario(modeloCuestionario);
        
        modificarModeloCuestionarioBean.guardaCuestionario();
        verify(modeloCuestionarioService, times(1)).save(modeloCuestionario);
        verify(registroActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.CUESTIONARIO.getDescripcion()));
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#guardaCuestionario()}.
     */
    @Test
    public final void testGuardaCuestionarioException() {
        ModeloCuestionario modeloCuestionario = mock(ModeloCuestionario.class);
        modificarModeloCuestionarioBean.setModeloCuestionario(modeloCuestionario);
        when(modeloCuestionarioService.save(modeloCuestionario)).thenThrow(TransientDataAccessResourceException.class);
        
        modificarModeloCuestionarioBean.guardaCuestionario();
        verify(modeloCuestionarioService, times(1)).save(modeloCuestionario);
        verify(registroActividadService, times(1)).altaRegActividadError(
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()), any(TransientDataAccessResourceException.class));
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#guardaNuevoCuestionario()}.
     */
    @Test
    public final void testGuardaNuevoCuestionario() {
        ModeloCuestionario modeloCuestionario = new ModeloCuestionario();
        modeloCuestionario.setId(1);
        modeloCuestionario.setDescripcion("descripcion_test");
        modificarModeloCuestionarioBean.setModeloCuestionario(modeloCuestionario);
        modificarModeloCuestionarioBean.setListaAreasCuestionario(new ArrayList<>());
        
        modificarModeloCuestionarioBean.guardaNuevoCuestionario();
        verify(modeloCuestionarioService, times(1)).save(modeloCuestionario);
        verify(registroActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()));
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#guardaNuevoCuestionario()}.
     */
    @Test
    public final void testGuardaNuevoCuestionarioException() {
        ModeloCuestionario modeloCuestionario = new ModeloCuestionario();
        modificarModeloCuestionarioBean.setModeloCuestionario(modeloCuestionario);
        modificarModeloCuestionarioBean.setListaAreasCuestionario(new ArrayList<>());
        when(modeloCuestionarioService.save(modeloCuestionario)).thenThrow(TransientDataAccessResourceException.class);
        
        modificarModeloCuestionarioBean.guardaNuevoCuestionario();
        verify(modeloCuestionarioService, times(1)).save(modeloCuestionario);
        verify(registroActividadService, times(1)).altaRegActividadError(
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()), any(TransientDataAccessResourceException.class));
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#onSelectTipo(org.primefaces.event.SelectEvent)}.
     */
    @Test
    public final void testOnSelectTipoTabla() {
        modificarModeloCuestionarioBean.setDatosTabla(datosTabla);
        List<ConfiguracionRespuestasCuestionario> valoresColumnas = new ArrayList<>();
        SelectEvent event = mock(SelectEvent.class);
        String tipo = "TABLANUEVA";
        when(event.getObject()).thenReturn(tipo);
        when(configuracionRespuestasCuestionarioRepository.findByConfigSeccionOrderByConfigClaveAsc(tipo))
                .thenReturn(valoresColumnas);
        
        modificarModeloCuestionarioBean.onSelectTipo(event);
        verify(datosTabla, times(1)).crearTabla(valoresColumnas);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.ModificarModeloCuestionarioBean#onSelectTipo(org.primefaces.event.SelectEvent)}.
     */
    @Test
    public final void testOnSelectTipoMatriz() {
        modificarModeloCuestionarioBean.setDatosTabla(datosTabla);
        List<ConfiguracionRespuestasCuestionario> valoresColumnas = new ArrayList<>();
        SelectEvent event = mock(SelectEvent.class);
        String tipo = "MATRIZNUEVA";
        
        when(event.getObject()).thenReturn(tipo);
        when(configuracionRespuestasCuestionarioRepository.findByConfigSeccionOrderByConfigClaveAsc(tipo))
                .thenReturn(new ArrayList<>());
        modificarModeloCuestionarioBean.onSelectTipo(event);
        verify(datosTabla, times(1)).crearMatriz(valoresColumnas);
    }
    
}
