package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
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
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.ProgesinException;
import es.mira.progesin.lazydata.LazyModelInspeccion;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.EquipoService;
import es.mira.progesin.services.IAlertaService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.IMunicipioService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.MiembroService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.Utilities;

/**
 * Test del bean de inspecciones.
 * 
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)

@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class, RequestContext.class, Utilities.class })
public class InspeccionBeanTest {
    
    /**
     * Mock del security context de spring.
     */
    @Mock
    private SecurityContext securityContext;
    
    /**
     * Simulación del RequestContext.
     */
    @Mock
    private RequestContext requestContext;
    
    /**
     * Mock del Authentication de spring.
     */
    @Mock
    private Authentication authentication;
    
    /**
     * Mock del servicio de inspecciones.
     */
    @Mock
    private IInspeccionesService inspeccionesService;
    
    /**
     * Mock del servicio de municipios.
     */
    @Mock
    private IMunicipioService municipioService;
    
    /**
     * Mock del servicio del registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Mock del servicio de las alertas.
     */
    @Mock
    private IAlertaService alertaService;
    
    /**
     * Mock del servicio de las notificaciones.
     */
    @Mock
    private INotificacionService notificacionesService;
    
    /**
     * Objeto en el que se injectan los mocks.
     */
    @InjectMocks
    private InspeccionBean inspeccionBean;
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        PowerMockito.mockStatic(RequestContext.class);
        PowerMockito.mockStatic(Utilities.class);
        
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("ezentis");
        when(RequestContext.getCurrentInstance()).thenReturn(requestContext);
        
        inspeccionBean.setEquipoService(mock(EquipoService.class));
        inspeccionBean.setMiembroService(mock(MiembroService.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#buscarInspeccion()}.
     */
    @Test
    public final void testBuscarInspeccion() {
        
        InspeccionBusqueda insBusqueda = new InspeccionBusqueda();
        LazyModelInspeccion model = mock(LazyModelInspeccion.class);
        // Provincia provinciSelec = mock(Provincia.class);
        
        inspeccionBean.setInspeccionBusqueda(insBusqueda);
        inspeccionBean.setModel(model);
        
        inspeccionBean.buscarInspeccion();
        
        verify(model, times(1)).load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#visualizaInspeccion(Inspeccion)}.
     */
    @Test
    public final void testVisualizaInspeccion() {
        Inspeccion ins = mock(Inspeccion.class);
        inspeccionBean.visualizaInspeccion(ins);
        verify(inspeccionesService, times(1)).findInspeccionById(ins.getId());
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#getAsociarInspecciones()}.
     */
    @Test
    public final void testGetAsociarInspecciones() {
        
        InspeccionBusqueda insBusqueda = new InspeccionBusqueda();
        Inspeccion ins = mock(Inspeccion.class);
        LazyModelInspeccion model = mock(LazyModelInspeccion.class);
        
        List<Inspeccion> inspeccionesAsignadasActuales = new ArrayList<>();
        Inspeccion insAsig = spy(Inspeccion.class);
        inspeccionesAsignadasActuales.add(insAsig);
        
        insBusqueda.setInspeccionModif(ins);
        insBusqueda.setAsociar(true);
        
        inspeccionBean.setInspeccionBusqueda(insBusqueda);
        inspeccionBean.setModel(model);
        inspeccionBean.setInspeccionesAsignadasActuales(inspeccionesAsignadasActuales);
        
        inspeccionBean.getAsociarInspecciones();
        
        verify(model, times(1)).load(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING, null);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#asociarInspecciones()}.
     */
    @Test
    public final void testAsociarInspecciones_alta() {
        Inspeccion ins = mock(Inspeccion.class);
        List<Inspeccion> inspeccionesAsignadasActuales = new ArrayList<>();
        InspeccionBusqueda insBusqueda = new InspeccionBusqueda();
        
        inspeccionBean.setInspeccion(ins);
        inspeccionBean.setInspeccionesAsignadasActuales(inspeccionesAsignadasActuales);
        inspeccionBean.setInspeccionBusqueda(insBusqueda);
        inspeccionBean.setVieneDe("asociarAlta");
        
        String rutaSiguiente = inspeccionBean.asociarInspecciones();
        
        assertThat(rutaSiguiente).isEqualTo("/inspecciones/altaInspeccion?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#asociarInspecciones()}.
     */
    @Test
    public final void testAsociarInspecciones_editar() {
        Inspeccion ins = mock(Inspeccion.class);
        List<Inspeccion> inspeccionesAsignadasActuales = new ArrayList<>();
        InspeccionBusqueda insBusqueda = new InspeccionBusqueda();
        
        inspeccionBean.setInspeccion(ins);
        inspeccionBean.setInspeccionesAsignadasActuales(inspeccionesAsignadasActuales);
        inspeccionBean.setInspeccionBusqueda(insBusqueda);
        inspeccionBean.setVieneDe("asociarEditar");
        
        String rutaSiguiente = inspeccionBean.asociarInspecciones();
        
        assertThat(rutaSiguiente).isEqualTo("/inspecciones/modificarInspeccion?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#limpiarBusqueda()}.
     */
    @Test
    public final void testLimpiarBusqueda() {
        LazyModelInspeccion model = mock(LazyModelInspeccion.class);
        inspeccionBean.setModel(model);
        inspeccionBean.limpiarBusqueda();
        
        assertThat(inspeccionBean.getModel().getRowCount()).isEqualTo(0);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#nuevaInspeccion()}.
     */
    @Test
    public final void testNuevaInspeccion() {
        when(authentication.getPrincipal()).thenReturn(mock(User.class));
        
        String ruta = inspeccionBean.nuevaInspeccion();
        assertThat(ruta).isEqualTo("/inspecciones/altaInspeccion?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#altaInspeccion()}.
     */
    
    @Test
    public final void testAltaInspeccion() {
        List<Inspeccion> inspeccionesAsignadasActuales = new ArrayList<>();
        // Inspeccion ins = mock(Inspeccion.class);
        Inspeccion inspeccion = Inspeccion.builder().build();
        inspeccion.setInspecciones(inspeccionesAsignadasActuales);
        inspeccionBean.setInspeccion(inspeccion);
        ArgumentCaptor<Inspeccion> inspGuardada = ArgumentCaptor.forClass(Inspeccion.class);
        
        inspeccionBean.altaInspeccion();
        
        verify(inspeccionesService, times(1)).save(inspGuardada.capture());
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.INSPECCION.getDescripcion()));
        verify(alertaService, times(1)).crearAlertaEquipo(eq(SeccionesEnum.INSPECCION.getDescripcion()),
                any(String.class), eq(inspGuardada.getValue()));
        assertThat(inspGuardada.getValue().getEstadoInspeccion()).isEqualTo(EstadoInspeccionEnum.A_SIN_INICIAR);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#getFormModificarInspeccion(Inspeccion)}.
     */
    
    @Test
    public final void testGetFormModificarInspeccion() {
        Provincia prov = new Provincia();
        Municipio muni = Municipio.builder().id(10L).provincia(prov).build();
        Inspeccion ins = Inspeccion.builder().municipio(muni).build();
        List<Municipio> lista = new ArrayList<>();
        inspeccionBean.setInspeccion(ins);
        inspeccionBean.setListaMunicipios(lista);
        
        String ruta = inspeccionBean.getFormModificarInspeccion(ins);
        
        assertThat(ruta).isEqualTo("/inspecciones/modificarInspeccion?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#modificarInspeccion()}.
     * @throws ProgesinException error
     */
    
    @Test
    public final void testModificarInspeccion() throws ProgesinException {
        Equipo equipo = Equipo.builder().id(10L).build();
        Inspeccion insNoMod = Inspeccion.builder().id(1L).anio(2017).equipo(equipo)
                .estadoInspeccion(EstadoInspeccionEnum.A_SIN_INICIAR).build();
        Inspeccion ins = Inspeccion.builder().id(1L).anio(2017).equipo(equipo)
                .estadoInspeccion(EstadoInspeccionEnum.M_FINALIZADA).build();
        List<Inspeccion> inspeccionesAsignadasActuales = new ArrayList<>();
        LazyModelInspeccion model = mock(LazyModelInspeccion.class);
        when(inspeccionesService.findInspeccionById(1L)).thenReturn(insNoMod);
        when(inspeccionesService.listaInspeccionesAsociadas(ins)).thenReturn(inspeccionesAsignadasActuales);
        when(Utilities.camposModificados(insNoMod, ins))
                .thenReturn("estadoInspeccion (Antes 'A_SIN_INICIAR' ahora modificado a 'M_FINALIZADA')\n\r");
        
        inspeccionBean.setInspeccion(ins);
        inspeccionBean.setModel(model);
        
        inspeccionBean.modificarInspeccion();
        
        verify(inspeccionesService, times(1)).save(inspeccionBean.getInspeccion());
        verify(regActividadService, times(1)).altaRegActividad(contains("Inspección 1/2017 modificada"),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.INSPECCION.getDescripcion()));
        verify(notificacionesService, times(1)).crearNotificacionEquipo(contains("Inspección 1/2017 modificada"),
                eq(SeccionesEnum.INSPECCION.getDescripcion()), eq(equipo));
        verify(notificacionesService, times(1)).crearNotificacionRol(contains("Inspección 1/2017 modificada"),
                eq(SeccionesEnum.INSPECCION.getDescripcion()), eq(RoleEnum.ROLE_SERVICIO_APOYO));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#init()}.
     */
    @Test
    public final void testInit() {
        inspeccionBean.init();
        assertThat(inspeccionBean.getInspeccionBusqueda()).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#getFormularioBusqueda()}.
     */
    @Test
    public final void testGetFormularioBusqueda() {
        LazyModelInspeccion model = mock(LazyModelInspeccion.class);
        inspeccionBean.setModel(model);
        String ruta = inspeccionBean.getFormularioBusqueda();
        
        assertThat(ruta).isEqualTo("/inspecciones/inspecciones?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#eliminar(Inspeccion)}.
     */
    
    @Test
    public final void testEliminar() {
        
        Equipo equipo = Equipo.builder().id(10L).build();
        Inspeccion ins = Inspeccion.builder().id(10L).anio(2017).equipo(equipo).build();
        
        inspeccionBean.eliminar(ins);
        
        verify(inspeccionesService, times(1)).save(ins);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#nuevoMunicipio(String, Provincia)}.
     */
    @Test
    public final void testNuevoMunicipio() {
        Provincia prov = mock(Provincia.class);
        when(municipioService.existeByNameIgnoreCaseAndProvincia("prueba", prov)).thenReturn(false);
        inspeccionBean.setListaMunicipios(new ArrayList<>());
        inspeccionBean.setInspeccion(Inspeccion.builder().id(10L).anio(2017).build());
        
        inspeccionBean.nuevoMunicipio("prueba", prov);
        
        verify(municipioService, times(1)).crearMunicipio("prueba", prov);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#onChangeProvincia(Provincia)}.
     */
    @Test
    public final void testOnChangeProvincia() {
        Provincia prov = mock(Provincia.class);
        inspeccionBean.onChangeProvincia(prov);
        verify(municipioService, times(1)).findByProvincia(prov);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.InspeccionBean#onRowUnSelected(org.primefaces.event.UnselectEvent)}.
     */
    @Test
    public final void testOnRowUnSelected() {
        Inspeccion ins = Inspeccion.builder().id(10L).anio(2017).build();
        UnselectEvent event = mock(UnselectEvent.class);
        List<Inspeccion> inspeccionesAsignadasActuales = new ArrayList<>();
        inspeccionesAsignadasActuales.add(ins);
        InspeccionBusqueda inspeccionBusqueda = new InspeccionBusqueda();
        inspeccionBusqueda.setSelectedAll(true);
        inspeccionBean.setInspeccionBusqueda(inspeccionBusqueda);
        when(event.getObject()).thenReturn(ins);
        
        inspeccionBean.setInspeccionesAsignadasActuales(inspeccionesAsignadasActuales);
        
        inspeccionBean.onRowUnSelected(event);
        
        assertThat(inspeccionBean.getInspeccionesAsignadasActuales().size()).isEqualTo(0);
        assertThat(inspeccionBean.getInspeccionBusqueda().isSelectedAll()).isFalse();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.InspeccionBean#onRowSelected(org.primefaces.event.SelectEvent)}.
     */
    @Test
    public final void testOnRowSelected() {
        Inspeccion ins = Inspeccion.builder().id(10L).anio(2017).build();
        SelectEvent event = mock(SelectEvent.class);
        List<Inspeccion> inspeccionesAsignadasActuales = new ArrayList<>();
        LazyModelInspeccion model = mock(LazyModelInspeccion.class);
        inspeccionBean.setModel(model);
        when(event.getObject()).thenReturn(ins);
        
        inspeccionBean.setInspeccionesAsignadasActuales(inspeccionesAsignadasActuales);
        
        inspeccionBean.onRowSelected(event);
        
        assertThat(inspeccionBean.getInspeccionesAsignadasActuales().size()).isEqualTo(1);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#onToggleSelect()}.
     */
    @Test
    public final void testOnToggleSelect() {
        LazyModelInspeccion model = mock(LazyModelInspeccion.class);
        inspeccionBean.setModel(model);
        InspeccionBusqueda inspeccionBusqueda = new InspeccionBusqueda();
        inspeccionBusqueda.setSelectedAll(true);
        inspeccionBean.setInspeccionBusqueda(inspeccionBusqueda);
        
        inspeccionBean.onToggleSelect();
        
        assertThat(inspeccionBean.getInspeccionBusqueda().isSelectedAll()).isFalse();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#paginator()}.
     */
    @Test
    public final void testPaginator() {
        Inspeccion ins = Inspeccion.builder().id(10L).anio(2017).build();
        List<Inspeccion> inspeccionesAsignadasActuales = new ArrayList<>();
        inspeccionesAsignadasActuales.add(ins);
        inspeccionBean.setInspeccionesAsignadasActuales(inspeccionesAsignadasActuales);
        inspeccionBean.setInspeccionBusqueda(new InspeccionBusqueda());
        
        inspeccionBean.paginator();
        
        assertThat(inspeccionBean.getInspeccionBusqueda().getInspeccionesSeleccionadas().size()).isEqualTo(1);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#desAsociarInspeccion(Inspeccion)}.
     */
    @Test
    public final void testDesAsociarInspeccion() {
        Inspeccion ins = Inspeccion.builder().id(10L).anio(2017).build();
        List<Inspeccion> inspeccionesAsignadasActuales = new ArrayList<>();
        inspeccionesAsignadasActuales.add(ins);
        inspeccionBean.setInspeccionesAsignadasActuales(inspeccionesAsignadasActuales);
        inspeccionBean.setInspeccionBusqueda(new InspeccionBusqueda());
        
        inspeccionBean.desAsociarInspeccion(ins);
        
        assertThat(inspeccionBean.getInspeccionBusqueda().getInspeccionesSeleccionadas().size()).isEqualTo(0);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#onToggle(ToggleEvent)}.
     */
    @Test
    public final void testOnToggle() {
        
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        when(eventMock.getVisibility()).thenReturn(Visibility.HIDDEN);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.FALSE);
        inspeccionBean.setList(listaToogle);
        inspeccionBean.onToggle(eventMock);
        assertThat(listaToogle.get(0)).isFalse();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.InspeccionBean#recuperarBusqueda()}.
     */
    @Test
    public final void testRecuperarBusqueda() {
        LazyModelInspeccion model = mock(LazyModelInspeccion.class);
        inspeccionBean.setModel(model);
        
        String ruta = inspeccionBean.recuperarBusqueda();
        
        assertThat(ruta).isEqualTo("/inspecciones/inspecciones?faces-redirect=true");
    }
    
}
