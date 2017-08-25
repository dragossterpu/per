/**
 * 
 */
package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.lazydata.LazyModelGuias;
import es.mira.progesin.persistence.entities.Guia;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IGuiaPersonalizadaService;
import es.mira.progesin.services.IGuiaService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ITipoInspeccionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.WordGeneratorGuias;

/**
 * 
 * Test del bean Guia.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
public class GuiaBeanTest {
    
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
     * Generador de documentos Word.
     */
    @Mock
    private WordGeneratorGuias wordGenerator;
    
    /**
     * Servicio de registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Servicio de tipo de inspección.
     */
    @Mock
    private ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Simulación Servicio de guías.
     */
    @Mock
    private IGuiaService guiaService;
    
    /**
     * Simulación Servicio de guías.
     */
    @Mock
    private IGuiaPersonalizadaService guiaPersonalizadaService;
    
    /**
     * Simulación Servicio de Inspecciones.
     */
    @Mock
    private IInspeccionesService inspeccionesService;
    
    /**
     * Bean de Guía personalizada.
     */
    @InjectMocks
    private GuiaBean guiaBean;
    
    /**
     * Lazy Model para la consulta paginada de guías en base de datos.
     */
    private LazyModelGuias model;
    
    /**
     * Constante nombre guía.
     */
    private static final String NOMBREGUIA = "guia_test";
    
    /**
     * Comprueba que la clase existe.
     */
    @Test
    public void type() {
        assertThat(GuiaBean.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     */
    @Test
    public void instantiation() {
        GuiaBean target = new GuiaBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Inicializa el test.
     */
    
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuarioLogueado");
        model = new LazyModelGuias(guiaService);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#buscarGuia()}.
     */
    @Test
    public final void testBuscarGuia() {
        List<Guia> guias = new ArrayList<>();
        Guia guia = new Guia();
        guia.setId(1L);
        guias.add(guia);
        GuiaBusqueda busqueda = new GuiaBusqueda();
        guiaBean.setModel(model);
        guiaBean.setBusqueda(busqueda);
        when(guiaService.getCounCriteria(busqueda)).thenReturn(1);
        when(guiaService.buscarGuiaPorCriteria(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, busqueda))
                .thenReturn(guias);
        
        guiaBean.buscarGuia();
        verify(guiaService, times(1)).getCounCriteria(busqueda);
        verify(guiaService, times(1)).buscarGuiaPorCriteria(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING,
                busqueda);
        assertThat(guiaBean.getModel().getDatasource()).hasSize(1);
        assertThat(guiaBean.getModel().getRowCount()).isEqualTo(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaBean#visualizaGuia(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testVisualizaGuia() {
        Guia guia = new Guia();
        guia.setId(1L);
        List<GuiaPasos> pasos = new ArrayList<>();
        when(guiaService.findOne(guia.getId())).thenReturn(guia);
        when(guiaService.listaPasos(guia)).thenReturn(pasos);
        String ruta = guiaBean.visualizaGuia(guia);
        assertThat(guiaBean.getGuia()).isEqualTo(guia);
        assertThat(guiaBean.getListaPasos()).isEqualTo(pasos);
        assertThat(ruta).isEqualTo("/guias/visualizaGuia?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaBean#visualizaGuia(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testVisualizaGuiaIsNull() {
        Guia guia = new Guia();
        guia.setId(1L);
        when(guiaService.findOne(guia.getId())).thenReturn(null);
        String ruta = guiaBean.visualizaGuia(guia);
        assertThat(ruta).isNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#limpiarBusqueda()}.
     */
    @Test
    public final void testLimpiarBusqueda() {
        guiaBean.setModel(model);
        guiaBean.limpiarBusqueda();
        assertThat(guiaBean.getBusqueda()).isNotNull();
        assertThat(guiaBean.getModel().getRowCount()).isEqualTo(0);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#nuevaGuia()}.
     */
    @Test
    public final void testNuevaGuia() {
        String redireccion = guiaBean.nuevaGuia();
        assertThat(guiaBean.isAlta()).isTrue();
        assertThat(guiaBean.getGuia()).isNotNull();
        assertThat(guiaBean.getListaPasos()).isNotNull();
        assertThat(guiaBean.getListaPasosGrabar()).isNotNull();
        assertThat(redireccion).isEqualTo("/guias/editarGuia?faces-redirect=true");
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaBean#editaGuia(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testEditaGuia() {
        Guia guia = new Guia();
        guia.setId(1L);
        when(guiaService.findOne(guia.getId())).thenReturn(guia);
        
        String redireccion = guiaBean.editaGuia(guia);
        assertThat(guiaBean.isAlta()).isFalse();
        assertThat(guiaBean.getGuia()).isEqualTo(guia);
        assertThat(guiaBean.getListaPasos()).isNotNull();
        assertThat(guiaBean.getListaPasosGrabar()).isNotNull();
        assertThat(redireccion).isEqualTo("/guias/editarGuia?faces-redirect=true");
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaBean#editaGuia(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testEditaGuiaNoExiste() {
        Guia guia = new Guia();
        guia.setId(1L);
        when(guiaService.findOne(guia.getId())).thenReturn(null);
        
        String redireccion = guiaBean.editaGuia(guia);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        assertThat(redireccion).isNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#aniadePaso(java.lang.String)}.
     */
    @Test
    public final void testAniadePaso() {
        Guia guia = new Guia();
        guia.setId(1L);
        String paso = "pasoTest";
        guiaBean.setGuia(guia);
        guiaBean.setListaPasos(new ArrayList<>());
        guiaBean.setListaPasosGrabar(new ArrayList<>());
        
        guiaBean.aniadePaso(paso);
        assertThat(guiaBean.getListaPasos()).hasSize(1);
        assertThat(guiaBean.getListaPasosGrabar()).hasSize(1);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#borraPaso()}.
     */
    @Test
    public final void testBorraPaso() {
        GuiaPasos paso1 = new GuiaPasos();
        paso1.setId(1L);
        paso1.setPaso("pasoTest1");
        GuiaPasos paso2 = new GuiaPasos();
        paso2.setId(2L);
        paso2.setPaso("pasoTest2");
        guiaBean.setPasoSeleccionado(paso1);
        List<GuiaPasos> pasosGrabar = new ArrayList<>();
        pasosGrabar.add(paso1);
        pasosGrabar.add(paso2);
        List<GuiaPasos> pasos = new ArrayList<>();
        pasos.add(paso1);
        pasos.add(paso2);
        guiaBean.setListaPasosGrabar(pasosGrabar);
        guiaBean.setListaPasos(pasos);
        when(guiaService.existePaso(guiaBean.getPasoSeleccionado())).thenReturn(true);
        
        guiaBean.borraPaso();
        assertThat(guiaBean.getPasoSeleccionado().getFechaBaja()).isNotNull();
        assertThat(guiaBean.getPasoSeleccionado().getUsernameBaja()).isNotNull();
        assertThat(guiaBean.getListaPasosGrabar().get(0)).isEqualTo(guiaBean.getPasoSeleccionado());
        
        assertThat(guiaBean.getListaPasos()).hasSize(1);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#borraPaso()}.
     */
    @Test
    public final void testBorraPasoNoExiste() {
        GuiaPasos paso1 = new GuiaPasos();
        paso1.setId(1L);
        paso1.setPaso("pasoTest1");
        GuiaPasos paso2 = new GuiaPasos();
        paso2.setId(2L);
        paso2.setPaso("pasoTest2");
        guiaBean.setPasoSeleccionado(paso1);
        List<GuiaPasos> pasosGrabar = new ArrayList<>();
        pasosGrabar.add(paso1);
        pasosGrabar.add(paso2);
        List<GuiaPasos> pasos = new ArrayList<>();
        pasos.add(paso1);
        pasos.add(paso2);
        guiaBean.setListaPasosGrabar(pasosGrabar);
        guiaBean.setListaPasos(pasos);
        when(guiaService.existePaso(guiaBean.getPasoSeleccionado())).thenReturn(false);
        
        guiaBean.borraPaso();
        assertThat(guiaBean.getListaPasos()).hasSize(1);
        assertThat(guiaBean.getListaPasosGrabar()).hasSize(1);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#borraPaso()}.
     */
    @Test
    public final void testBorraPasoIdNull() {
        GuiaPasos paso1 = new GuiaPasos();
        paso1.setPaso("pasoTest1");
        paso1.setId(null);
        GuiaPasos paso2 = new GuiaPasos();
        paso2.setId(2L);
        paso2.setPaso("pasoTest2");
        guiaBean.setPasoSeleccionado(paso1);
        List<GuiaPasos> pasosGrabar = new ArrayList<>();
        pasosGrabar.add(paso1);
        pasosGrabar.add(paso2);
        List<GuiaPasos> pasos = new ArrayList<>();
        pasos.add(paso1);
        pasos.add(paso2);
        guiaBean.setListaPasosGrabar(pasosGrabar);
        guiaBean.setListaPasos(pasos);
        when(guiaService.existePaso(guiaBean.getPasoSeleccionado())).thenReturn(false);
        
        guiaBean.borraPaso();
        assertThat(guiaBean.getListaPasos()).hasSize(1);
        assertThat(guiaBean.getListaPasosGrabar()).hasSize(1);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#onSelectPaso(org.primefaces.event.SelectEvent)}.
     */
    @Test
    public final void testOnSelectPaso() {
        GuiaPasos paso = mock(GuiaPasos.class);
        SelectEvent event = mock(SelectEvent.class);
        when(event.getObject()).thenReturn(paso);
        guiaBean.onSelectPaso(event);
        assertThat(guiaBean.getPasoSeleccionado()).isEqualTo(paso);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#init()}.
     */
    @Test
    public final void testInit() {
        TipoInspeccion tipo1 = mock(TipoInspeccion.class);
        TipoInspeccion tipo2 = mock(TipoInspeccion.class);
        List<TipoInspeccion> tipos = new ArrayList<>();
        tipos.add(tipo1);
        tipos.add(tipo2);
        when(tipoInspeccionService.buscaTodos()).thenReturn(tipos);
        guiaBean.init();
        assertThat(guiaBean.getBusqueda()).isNotNull();
        assertThat(guiaBean.getList()).isNotNull();
        assertThat(guiaBean.getList().size()).isEqualTo(5);
        assertThat(guiaBean.getModel()).isNotNull();
        assertThat(guiaBean.getListaTiposInspeccion()).hasSize(2);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaBean#crearDocumentoWordGuia(es.mira.progesin.persistence.entities.Guia)}.
     * @throws ProgesinException lanzada
     */
    @Test
    public final void testCrearDocumentoWordGuia() throws ProgesinException {
        Guia guia = new Guia();
        guia.setId(1L);
        StreamedContent file = mock(StreamedContent.class);
        when(wordGenerator.crearDocumentoGuia(guia)).thenReturn(file);
        guiaBean.crearDocumentoWordGuia(guia);
        verify(wordGenerator, times(1)).crearDocumentoGuia(guia);
        assertThat(guiaBean.getFile()).isEqualTo(file);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaBean#crearDocumentoWordGuia(es.mira.progesin.persistence.entities.Guia)}.
     * @throws ProgesinException lanzada
     */
    @Test
    public final void testCrearDocumentoWordGuiaException() throws ProgesinException {
        Guia guia = mock(Guia.class);
        when(wordGenerator.crearDocumentoGuia(guia)).thenThrow(ProgesinException.class);
        guiaBean.crearDocumentoWordGuia(guia);
        verify(wordGenerator, times(1)).crearDocumentoGuia(guia);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(TipoRegistroEnum.ERROR.name()),
                any(ProgesinException.class));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#guardaGuia()}.
     */
    @Test
    public final void testGuardaGuiaAlta() {
        Guia guia = new Guia();
        guia.setId(2L);
        guia.setNombre(NOMBREGUIA);
        guiaBean.setGuia(guia);
        List<GuiaPasos> pasos = new ArrayList<>();
        GuiaPasos paso1 = new GuiaPasos();
        paso1.setId(1L);
        paso1.setPaso("paso1");
        pasos.add(paso1);
        guiaBean.setListaPasos(pasos);
        List<GuiaPasos> pasosGrabar = new ArrayList<>();
        GuiaPasos pasoGrabar1 = new GuiaPasos();
        pasoGrabar1.setId(1L);
        pasoGrabar1.setPaso("pasoGrabar1");
        GuiaPasos pasoGrabar2 = new GuiaPasos();
        pasoGrabar2.setId(2L);
        pasoGrabar2.setPaso("pasoGrabar2");
        pasosGrabar.add(pasoGrabar1);
        pasosGrabar.add(pasoGrabar2);
        guiaBean.setListaPasosGrabar(pasosGrabar);
        when(guiaService.guardaGuia(guiaBean.getGuia())).thenReturn(guiaBean.getGuia());
        guiaBean.setAlta(true);
        guiaBean.guardaGuia();
        verify(guiaService, times(1)).guardaGuia(guia);
        assertThat(guiaBean.getListaPasosGrabar()).isEqualTo(guiaBean.getGuia().getPasos());
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), eq(TipoRegistroEnum.ALTA.name()),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.GUIAS.getDescripcion()));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#guardaGuia()}.
     */
    @Test
    public final void testGuardaGuiaModificacion() {
        Guia guia = new Guia();
        guia.setId(2L);
        guia.setNombre(NOMBREGUIA);
        guiaBean.setGuia(guia);
        List<GuiaPasos> pasos = new ArrayList<>();
        GuiaPasos paso1 = new GuiaPasos();
        paso1.setId(1L);
        paso1.setPaso("paso1");
        pasos.add(paso1);
        guiaBean.setListaPasos(pasos);
        List<GuiaPasos> pasosGrabar = new ArrayList<>();
        GuiaPasos pasoGrabar1 = new GuiaPasos();
        pasoGrabar1.setId(1L);
        pasoGrabar1.setPaso("pasoGrabar1");
        GuiaPasos pasoGrabar2 = new GuiaPasos();
        pasoGrabar2.setId(2L);
        pasoGrabar2.setPaso("pasoGrabar2");
        pasosGrabar.add(pasoGrabar1);
        pasosGrabar.add(pasoGrabar2);
        guiaBean.setListaPasosGrabar(pasosGrabar);
        when(guiaService.guardaGuia(guiaBean.getGuia())).thenReturn(guiaBean.getGuia());
        guiaBean.setAlta(false);
        guiaBean.guardaGuia();
        verify(guiaService, times(1)).guardaGuia(guia);
        assertThat(guiaBean.getListaPasosGrabar()).isEqualTo(guiaBean.getGuia().getPasos());
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO),
                eq(TipoRegistroEnum.MODIFICACION.name()), any(String.class));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.GUIAS.getDescripcion()));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#guardaGuia()}.
     */
    @Test
    public final void testGuardaGuiaNombreNull() {
        Guia guia = new Guia();
        guia.setId(2L);
        guia.setNombre("");
        guiaBean.setGuia(guia);
        guiaBean.guardaGuia();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), eq(Constantes.ERRORMENSAJE),
                any(String.class), eq(null));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#guardaGuia()}.
     */
    @Test
    public final void testGuardaGuiaSinPasos() {
        Guia guia = new Guia();
        guia.setId(2L);
        guia.setNombre(NOMBREGUIA);
        guiaBean.setGuia(guia);
        guiaBean.setListaPasos(new ArrayList<>());
        guiaBean.guardaGuia();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), eq(Constantes.ERRORMENSAJE),
                any(String.class), eq(null));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#guardaGuia()}.
     */
    @Test
    public final void testGuardaGuiaAltaException() {
        Guia guia = new Guia();
        guia.setId(2L);
        guia.setNombre(NOMBREGUIA);
        guiaBean.setGuia(guia);
        List<GuiaPasos> pasos = new ArrayList<>();
        GuiaPasos paso1 = new GuiaPasos();
        paso1.setId(1L);
        paso1.setPaso("paso1");
        pasos.add(paso1);
        guiaBean.setListaPasos(pasos);
        List<GuiaPasos> pasosGrabar = new ArrayList<>();
        GuiaPasos pasoGrabar1 = new GuiaPasos();
        pasoGrabar1.setId(1L);
        pasoGrabar1.setPaso("pasoGrabar1");
        GuiaPasos pasoGrabar2 = new GuiaPasos();
        pasoGrabar2.setId(2L);
        pasoGrabar2.setPaso("pasoGrabar2");
        pasosGrabar.add(pasoGrabar1);
        pasosGrabar.add(pasoGrabar2);
        guiaBean.setListaPasosGrabar(pasosGrabar);
        when(guiaService.guardaGuia(guiaBean.getGuia())).thenThrow(TransientDataAccessResourceException.class);
        guiaBean.setAlta(true);
        guiaBean.guardaGuia();
        verify(guiaService, times(1)).guardaGuia(guia);
        assertThat(guiaBean.getListaPasosGrabar()).isEqualTo(guiaBean.getGuia().getPasos());
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(TipoRegistroEnum.ERROR.name()),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#getFormularioBusqueda()}.
     */
    @Test
    public final void testGetFormularioBusqueda() {
        guiaBean.setModel(model);
        String redireccion = guiaBean.getFormularioBusqueda();
        assertThat(guiaBean.getBusqueda()).isNotNull();
        assertThat(guiaBean.getModel().getRowCount()).isEqualTo(0);
        assertThat(redireccion).isEqualTo("/guias/buscaGuias?faces-redirect=true");
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaBean#creaPersonalizada(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testCreaPersonalizada() {
        Guia guia = new Guia();
        guia.setId(1L);
        List<GuiaPasos> pasos = new ArrayList<>();
        GuiaPasos paso1 = mock(GuiaPasos.class);
        GuiaPasos paso2 = mock(GuiaPasos.class);
        pasos.add(paso1);
        pasos.add(paso2);
        
        when(guiaService.findOne(guia.getId())).thenReturn(guia);
        when(guiaService.listaPasosNoNull(guia)).thenReturn(pasos);
        String redireccion = guiaBean.creaPersonalizada(guia);
        
        assertThat(guiaBean.isAlta()).isFalse();
        assertThat(guiaBean.getGuia()).isEqualTo(guia);
        assertThat(guiaBean.getListaPasosSeleccionados()).isNotNull();
        assertThat(guiaBean.getListaInspecciones()).isNotNull();
        assertThat(guiaBean.getListaPasos()).isEqualTo(pasos);
        assertThat(redireccion).isEqualTo("/guias/personalizarGuia?faces-redirect=true");
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaBean#creaPersonalizada(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testCreaPersonalizadaGuiaIsNull() {
        Guia guia = new Guia();
        guia.setId(1L);
        when(guiaService.findOne(guia.getId())).thenReturn(null);
        
        String redireccion = guiaBean.creaPersonalizada(guia);
        assertThat(redireccion).isNull();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#guardarPersonalizada(java.lang.String)}.
     */
    @Test
    public final void testGuardarPersonalizada() {
        String nombreGuia = NOMBREGUIA;
        Guia guia = new Guia();
        guia.setId(1L);
        guiaBean.setGuia(guia);
        List<GuiaPasos> pasos = new ArrayList<>();
        GuiaPasos paso1 = mock(GuiaPasos.class);
        GuiaPasos paso2 = mock(GuiaPasos.class);
        pasos.add(paso1);
        pasos.add(paso2);
        guiaBean.setListaPasosSeleccionados(pasos);
        List<Inspeccion> inspecciones = new ArrayList<>();
        Inspeccion i1 = mock(Inspeccion.class);
        inspecciones.add(i1);
        guiaBean.setListaInspecciones(inspecciones);
        
        guiaBean.guardarPersonalizada(nombreGuia);
        verify(guiaPersonalizadaService, times(1)).save(any(GuiaPersonalizada.class));
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO),
                eq(SeccionesEnum.GUIAS.getDescripcion()), any(String.class));
        assertThat(guiaBean.getListaInspecciones()).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#guardarPersonalizada(java.lang.String)}.
     */
    @Test
    public final void testGuardarPersonalizadaNombreVacioSinPasos() {
        String nombreGuia = "";
        Guia guia = new Guia();
        guia.setId(1L);
        guiaBean.setGuia(guia);
        guiaBean.setListaPasosSeleccionados(new ArrayList<>());
        
        guiaBean.guardarPersonalizada(nombreGuia);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#guardarPersonalizada(java.lang.String)}.
     */
    @Test
    public final void testGuardarPersonalizadaDataAccessException() {
        String nombreGuia = NOMBREGUIA;
        Guia guia = new Guia();
        guia.setId(1L);
        guiaBean.setGuia(guia);
        List<GuiaPasos> pasos = new ArrayList<>();
        GuiaPasos paso1 = mock(GuiaPasos.class);
        GuiaPasos paso2 = mock(GuiaPasos.class);
        pasos.add(paso1);
        pasos.add(paso2);
        guiaBean.setListaPasosSeleccionados(pasos);
        List<Inspeccion> inspecciones = new ArrayList<>();
        Inspeccion i1 = mock(Inspeccion.class);
        inspecciones.add(i1);
        guiaBean.setListaInspecciones(inspecciones);
        when(guiaPersonalizadaService.save(any(GuiaPersonalizada.class)))
                .thenThrow(TransientDataAccessResourceException.class);
        
        guiaBean.guardarPersonalizada(nombreGuia);
        verify(guiaPersonalizadaService, times(1)).save(any(GuiaPersonalizada.class));
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.GUIAS.getDescripcion()),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#autocompletarInspeccion(java.lang.String)}.
     */
    @Test
    public final void testAutocompletarInspeccion() {
        guiaBean.autocompletarInspeccion("");
        verify(inspeccionesService, times(1)).buscarNoFinalizadaPorNombreUnidadONumero("");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#anular(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testAnularExcepcionDataAccess() {
        Guia guia = new Guia();
        guia.setId(1L);
        guia.setNombre(NOMBREGUIA);
        when(guiaService.findOne(guia.getId())).thenReturn(guia);
        when(guiaService.guardaGuia(guia)).thenThrow(TransientDataAccessResourceException.class);
        guiaBean.anular(guia);
        verify(guiaService, times(1)).guardaGuia(guia);
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.GUIAS.getDescripcion()),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#anular(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testAnularGuiaIsNull() {
        Guia guia = new Guia();
        guia.setId(1L);
        guia.setNombre(NOMBREGUIA);
        when(guiaService.findOne(guia.getId())).thenReturn(null);
        guiaBean.anular(guia);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#anular(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testAnular() {
        Guia guia = new Guia();
        guia.setId(1L);
        guia.setNombre(NOMBREGUIA);
        when(guiaService.findOne(guia.getId())).thenReturn(guia);
        guiaBean.anular(guia);
        verify(guiaService, times(1)).guardaGuia(guia);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.GUIAS.getDescripcion()));
        assertThat(guia.getFechaAnulacion()).isNotNull();
        assertThat(guia.getUsernameAnulacion()).isNotNull();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#eliminar(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testEliminarBajaLogica() {
        Guia guia = new Guia();
        guia.setId(1L);
        guia.setNombre(NOMBREGUIA);
        when(guiaPersonalizadaService.buscarPorModeloGuia(guia)).thenReturn(true);
        guiaBean.eliminar(guia);
        
        verify(guiaService, times(1)).guardaGuia(guia);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.GUIAS.getDescripcion()));
        assertThat(guia.getFechaBaja()).isNotNull();
        assertThat(guia.getUsernameBaja()).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#eliminar(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testEliminarDataAccessException() {
        Guia guia = new Guia();
        guia.setId(1L);
        guia.setNombre(NOMBREGUIA);
        when(guiaPersonalizadaService.buscarPorModeloGuia(guia)).thenReturn(false);
        doThrow(TransientDataAccessResourceException.class).when(guiaService).eliminar(guia);
        guiaBean.eliminar(guia);
        
        verify(guiaService, times(1)).eliminar(guia);
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.GUIAS.getDescripcion()),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#eliminar(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testEliminarBajaFisica() {
        Guia guia = new Guia();
        guia.setId(1L);
        guia.setNombre(NOMBREGUIA);
        when(guiaPersonalizadaService.buscarPorModeloGuia(guia)).thenReturn(false);
        guiaBean.eliminar(guia);
        
        verify(guiaService, times(1)).eliminar(guia);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.GUIAS.getDescripcion()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#activa(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testActiva() {
        Guia guia = new Guia();
        guia.setId(1L);
        guia.setNombre(NOMBREGUIA);
        guia.setFechaAnulacion(new Date());
        guia.setUsernameAnulacion("ezentis");
        when(guiaService.findOne(guia.getId())).thenReturn(guia);
        guiaBean.activa(guia);
        
        verify(guiaService, times(1)).guardaGuia(guia);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.GUIAS.getDescripcion()));
        assertThat(guia.getFechaAnulacion()).isNull();
        assertThat(guia.getUsernameAnulacion()).isNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#activa(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testActivaGuiaIsNull() {
        Guia guia = new Guia();
        guia.setId(1L);
        guia.setNombre(NOMBREGUIA);
        when(guiaService.findOne(guia.getId())).thenReturn(null);
        
        guiaBean.activa(guia);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaBean#activa(es.mira.progesin.persistence.entities.Guia)}.
     */
    @Test
    public final void testActivaDataAccessException() {
        Guia guia = new Guia();
        guia.setId(1L);
        guia.setNombre(NOMBREGUIA);
        guia.setFechaAnulacion(new Date());
        guia.setUsernameAnulacion("ezentis");
        when(guiaService.findOne(guia.getId())).thenReturn(guia);
        when(guiaService.guardaGuia(guia)).thenThrow(TransientDataAccessResourceException.class);
        
        guiaBean.activa(guia);
        
        verify(guiaService, times(1)).guardaGuia(guia);
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.GUIAS.getDescripcion()),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaBean#asignarNuevaInspeccion(es.mira.progesin.persistence.entities.Inspeccion)}.
     */
    @Test
    public final void testAsignarNuevaInspeccion() {
        guiaBean.setListaInspecciones(new ArrayList<>());
        Inspeccion inspeccion = mock(Inspeccion.class);
        guiaBean.asignarNuevaInspeccion(inspeccion);
        assertThat(guiaBean.getListaInspecciones()).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaBean#desAsociarInspeccion(es.mira.progesin.persistence.entities.Inspeccion)}.
     */
    @Test
    public final void testDesAsociarInspeccion() {
        Inspeccion inspeccion1 = mock(Inspeccion.class);
        Inspeccion inspeccion2 = mock(Inspeccion.class);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(inspeccion1);
        inspecciones.add(inspeccion2);
        guiaBean.setListaInspecciones(inspecciones);
        guiaBean.desAsociarInspeccion(inspeccion1);
        assertThat(guiaBean.getListaInspecciones()).hasSize(1);
    }
    
}
