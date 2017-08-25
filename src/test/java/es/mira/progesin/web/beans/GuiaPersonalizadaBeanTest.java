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

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.lazydata.LazyModelGuiasPersonalizadas;
import es.mira.progesin.persistence.entities.GuiaPasos;
import es.mira.progesin.persistence.entities.GuiaPersonalizada;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.IGuiaPersonalizadaService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.WordGeneratorGuias;

/**
 * 
 * Test del bean GuiaPersonalizada.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
public class GuiaPersonalizadaBeanTest {
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
     * Servicio de guías personalizadas.
     */
    @Mock
    private IGuiaPersonalizadaService guiaPersonalizadaService;
    
    /**
     * Bean de Guía personalizada.
     */
    @InjectMocks
    private GuiaPersonalizadaBean guiaPersonalizadaBean;
    
    /**
     * Lazy Model para la consulta paginada de guías personalizadas en base de datos.
     */
    private LazyModelGuiasPersonalizadas model;
    
    /**
     * Comprueba que la clase existe.
     */
    @Test
    public void type() {
        assertThat(GuiaPersonalizadaBean.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     */
    @Test
    public void instantiation() {
        GuiaPersonalizadaBean target = new GuiaPersonalizadaBean();
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
        model = new LazyModelGuiasPersonalizadas(guiaPersonalizadaService);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#buscarGuia()}.
     */
    @Test
    public final void testBuscarGuia() {
        List<GuiaPersonalizada> guiasPersonalizadas = new ArrayList<>();
        
        GuiaBusqueda busqueda = new GuiaBusqueda();
        guiaPersonalizadaBean.setModel(model);
        guiaPersonalizadaBean.setGuiaPersonalizadaBusqueda(busqueda);
        when(guiaPersonalizadaService.getCountGuiaCriteria(busqueda)).thenReturn(1);
        when(guiaPersonalizadaService.buscarGuiaPorCriteria(0, Constantes.TAMPAGINA, "fechaCreacion",
                SortOrder.DESCENDING, busqueda)).thenReturn(guiasPersonalizadas);
        GuiaPersonalizada guia = new GuiaPersonalizada();
        guia.setId(1L);
        guiasPersonalizadas.add(guia);
        
        Inspeccion inspeccion1 = new Inspeccion();
        inspeccion1.setId(1L);
        inspeccion1.setAnio(2017);
        Inspeccion inspeccion2 = new Inspeccion();
        inspeccion2.setId(2L);
        inspeccion2.setAnio(2017);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(inspeccion1);
        inspecciones.add(inspeccion2);
        when(guiaPersonalizadaService.listaInspecciones(guia)).thenReturn(inspecciones);
        guiaPersonalizadaBean.setMapaInspecciones(new LinkedHashMap<>());
        
        guiaPersonalizadaBean.buscarGuia();
        verify(guiaPersonalizadaService, times(1)).getCountGuiaCriteria(busqueda);
        verify(guiaPersonalizadaService, times(1)).buscarGuiaPorCriteria(0, Constantes.TAMPAGINA, "fechaCreacion",
                SortOrder.DESCENDING, busqueda);
        verify(guiaPersonalizadaService, times(1)).listaInspecciones(guia);
        assertThat(guiaPersonalizadaBean.getModel().getDatasource()).hasSize(1);
        assertThat(guiaPersonalizadaBean.getModel().getRowCount()).isEqualTo(1);
        assertThat(guiaPersonalizadaBean.getMapaInspecciones()).hasSize(1);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#visualizaGuia(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     */
    @Test
    public final void testVisualizaGuia() {
        GuiaPersonalizada guia = new GuiaPersonalizada();
        guia.setId(1L);
        List<GuiaPasos> pasos = new ArrayList<>();
        GuiaPasos paso1 = mock(GuiaPasos.class);
        GuiaPasos paso2 = mock(GuiaPasos.class);
        pasos.add(paso1);
        pasos.add(paso2);
        
        Inspeccion inspeccion1 = new Inspeccion();
        inspeccion1.setId(1L);
        inspeccion1.setAnio(2017);
        Inspeccion inspeccion2 = new Inspeccion();
        inspeccion2.setId(2L);
        inspeccion2.setAnio(2017);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(inspeccion1);
        inspecciones.add(inspeccion2);
        
        when(guiaPersonalizadaService.findOne(guia.getId())).thenReturn(guia);
        when(guiaPersonalizadaService.listaPasos(guia)).thenReturn(pasos);
        when(guiaPersonalizadaService.listaInspecciones(guia)).thenReturn(inspecciones);
        
        String ruta = guiaPersonalizadaBean.visualizaGuia(guia);
        assertThat(guiaPersonalizadaBean.getGuiaPersonalizada()).isEqualTo(guia);
        assertThat(guiaPersonalizadaBean.getListaPasos()).isEqualTo(pasos);
        assertThat(guiaPersonalizadaBean.getGuiaPersonalizada().getInspeccion()).isEqualTo(inspecciones);
        assertThat(ruta).isEqualTo("/guias/visualizaGuiaPersonalizada?faces-redirect=true");
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#visualizaGuia(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     */
    @Test
    public final void testVisualizaGuiaNull() {
        GuiaPersonalizada guia = new GuiaPersonalizada();
        guia.setId(1L);
        when(guiaPersonalizadaService.findOne(guia.getId())).thenReturn(null);
        
        String redireccion = guiaPersonalizadaBean.visualizaGuia(guia);
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        assertThat(redireccion).isEqualTo(null);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#limpiarBusqueda()}.
     */
    @Test
    public final void testLimpiarBusqueda() {
        guiaPersonalizadaBean.setModel(model);
        guiaPersonalizadaBean.limpiarBusqueda();
        assertThat(guiaPersonalizadaBean.getGuiaPersonalizadaBusqueda()).isNotNull();
        assertThat(guiaPersonalizadaBean.getModel().getRowCount()).isEqualTo(0);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#anular(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     */
    @Test
    public final void testAnular() {
        GuiaPersonalizada guia = new GuiaPersonalizada();
        guia.setId(1L);
        when(guiaPersonalizadaService.findOne(guia.getId())).thenReturn(guia);
        guiaPersonalizadaBean.anular(guia);
        verify(guiaPersonalizadaService, times(1)).anular(guia);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#anular(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     */
    @Test
    public final void testAnularGuiaNull() {
        GuiaPersonalizada guia = new GuiaPersonalizada();
        guia.setId(1L);
        when(guiaPersonalizadaService.findOne(guia.getId())).thenReturn(null);
        guiaPersonalizadaBean.anular(guia);
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#eliminar(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     */
    @Test
    public final void testEliminar() {
        GuiaPersonalizada guia = mock(GuiaPersonalizada.class);
        guiaPersonalizadaBean.eliminar(guia);
        verify(guiaPersonalizadaService, times(1)).eliminar(guia);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#init()}.
     */
    @Test
    public final void testInit() {
        guiaPersonalizadaBean.init();
        assertThat(guiaPersonalizadaBean.getGuiaPersonalizadaBusqueda()).isNotNull();
        assertThat(guiaPersonalizadaBean.getList()).isNotNull();
        assertThat(guiaPersonalizadaBean.getList().size()).isEqualTo(6);
        assertThat(guiaPersonalizadaBean.getMapaInspecciones()).isNotNull();
        assertThat(guiaPersonalizadaBean.getModel()).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#crearDocumentoWordGuia(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     * @throws ProgesinException lanzada
     */
    @Test
    public final void testCrearDocumentoWordGuia() throws ProgesinException {
        GuiaPersonalizada guia = new GuiaPersonalizada();
        guia.setId(1L);
        StreamedContent file = mock(StreamedContent.class);
        when(wordGenerator.crearDocumentoGuia(guia)).thenReturn(file);
        guiaPersonalizadaBean.crearDocumentoWordGuia(guia);
        verify(wordGenerator, times(1)).crearDocumentoGuia(guia);
        assertThat(guiaPersonalizadaBean.getFile()).isEqualTo(file);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#crearDocumentoWordGuia(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     * @throws ProgesinException lanzada
     */
    @Test
    public final void testCrearDocumentoWordGuiaException() throws ProgesinException {
        GuiaPersonalizada guia = mock(GuiaPersonalizada.class);
        when(wordGenerator.crearDocumentoGuia(guia)).thenThrow(ProgesinException.class);
        guiaPersonalizadaBean.crearDocumentoWordGuia(guia);
        verify(wordGenerator, times(1)).crearDocumentoGuia(guia);
        verify(regActividadService, times(1)).altaRegActividadError(eq(TipoRegistroEnum.ERROR.name()),
                any(ProgesinException.class));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                any(String.class));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#getFormularioBusqueda()}.
     */
    @Test
    public final void testGetFormularioBusqueda() {
        guiaPersonalizadaBean.setModel(model);
        String redireccion = guiaPersonalizadaBean.getFormularioBusqueda();
        assertThat(guiaPersonalizadaBean.getGuiaPersonalizadaBusqueda()).isNotNull();
        assertThat(guiaPersonalizadaBean.getModel().getRowCount()).isEqualTo(0);
        assertThat(redireccion).isEqualTo("/guias/buscaGuiasPersonalizadas?faces-redirect=true");
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#activa(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     */
    @Test
    public final void testActiva() {
        GuiaPersonalizada guia = new GuiaPersonalizada();
        guia.setId(1L);
        guia.setNombreGuiaPersonalizada("Guía_test");
        guia.setFechaAnulacion(new Date());
        guia.setUsernameAnulacion("ezentis");
        when(guiaPersonalizadaService.findOne(guia.getId())).thenReturn(guia);
        when(guiaPersonalizadaService.save(guia)).thenReturn(guia);
        guiaPersonalizadaBean.activa(guia);
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.GUIAS.getDescripcion()));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#activa(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     */
    @Test
    public final void testActivaGuiaSaveException() {
        GuiaPersonalizada guia = new GuiaPersonalizada();
        guia.setId(1L);
        guia.setNombreGuiaPersonalizada("Guía_test");
        guia.setFechaAnulacion(new Date());
        guia.setUsernameAnulacion("ezentis");
        when(guiaPersonalizadaService.findOne(guia.getId())).thenReturn(guia);
        when(guiaPersonalizadaService.save(guia)).thenThrow(TransientDataAccessResourceException.class);
        guiaPersonalizadaBean.activa(guia);
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.GUIAS.getDescripcion()),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.GuiaPersonalizadaBean#activa(es.mira.progesin.persistence.entities.GuiaPersonalizada)}.
     */
    @Test
    public final void testActivaGuiaNull() {
        GuiaPersonalizada guia = new GuiaPersonalizada();
        guia.setId(1L);
        when(guiaPersonalizadaService.findOne(guia.getId())).thenReturn(null);
        guiaPersonalizadaBean.activa(guia);
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                any(String.class));
    }
    
}
