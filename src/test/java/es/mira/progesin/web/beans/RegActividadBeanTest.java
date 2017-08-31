/**
 * 
 */
package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.lazydata.LazyModelRegistro;
import es.mira.progesin.persistence.entities.RegistroActividad;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.util.FacesUtilities;

/**
 * Test para el controlador RegActividadBean.
 * 
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class, RequestContext.class })
public class RegActividadBeanTest {
    /**
     * Simulación del securityContext.
     */
    @Mock
    private SecurityContext securityContext;
    
    /**
     * Simulación del RequestContext.
     */
    @Mock
    private RequestContext requestContext;
    
    /**
     * Simulación de la autenticación.
     */
    @Mock
    private Authentication authentication;
    
    /**
     * Simulación Lazy Model para la consulta paginada de documentos en base de datos.
     */
    
    private LazyModelRegistro model;
    
    /**
     * Mock del servicio de registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Mock del servicio de registro de actividad.
     */
    @InjectMocks
    private RegActividadBean regActividadBeanMock;
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        PowerMockito.mockStatic(RequestContext.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuarioLogueado");
        when(RequestContext.getCurrentInstance()).thenReturn(requestContext);
        
    }
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(GestorDocumentalBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        GestorDocumentalBean target = new GestorDocumentalBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.RegActividadBean#buscarRegActividad()}.
     */
    @Test
    public final void testBuscarRegActividad() {
        List<RegistroActividad> listaRegistros = new ArrayList<>();
        RegistroActividad reg = mock(RegistroActividad.class);
        listaRegistros.add(reg);
        
        RegActividadBusqueda regBusqueda = mock(RegActividadBusqueda.class);
        model = new LazyModelRegistro(regActividadService);
        regActividadBeanMock.setRegActividadBusqueda(regBusqueda);
        regActividadBeanMock.setModel(model);
        
        when(regActividadService.getCounCriteria(any(RegActividadBusqueda.class))).thenReturn(listaRegistros.size());
        when(regActividadService.buscarRegActividadCriteria(0, Constantes.TAMPAGINA, "fechaAlta", SortOrder.DESCENDING,
                regBusqueda)).thenReturn(listaRegistros);
        
        regActividadBeanMock.buscarRegActividad();
        
        verify(regActividadService, times(1)).getCounCriteria(any(RegActividadBusqueda.class));
        verify(regActividadService, times(1)).buscarRegActividadCriteria(0, Constantes.TAMPAGINA, "fechaAlta",
                SortOrder.DESCENDING, regBusqueda);
        assertThat(model.getRowCount()).isEqualTo(1);
        assertThat(model.getDatasource()).hasSize(1);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.RegActividadBean#onToggle(org.primefaces.event.ToggleEvent)}.
     */
    @Test
    public final void testOnToggle() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.FALSE);
        regActividadBeanMock.setList(listaToogle);
        regActividadBeanMock.onToggle(eventMock);
        assertThat(listaToogle.get(0)).isFalse();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.RegActividadBean#getFormularioRegActividad()}.
     */
    @Test
    public final void testGetFormularioRegActividad() {
        model = new LazyModelRegistro(regActividadService);
        model.setRowCount(2);
        regActividadBeanMock.setModel(model);
        String ruta = regActividadBeanMock.getFormularioRegActividad();
        assertThat(regActividadBeanMock.getModel().getRowCount()).isEqualTo(0);
        assertThat(ruta).isEqualTo("/administracion/registro/registro?faces-redirect=true");
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.RegActividadBean#limpiarBusqueda()}.
     */
    @Test
    public final void testLimpiarBusqueda() {
        model = new LazyModelRegistro(regActividadService);
        model.setRowCount(2);
        regActividadBeanMock.setModel(model);
        regActividadBeanMock.limpiarBusqueda();
        assertThat(regActividadBeanMock.getModel().getRowCount()).isEqualTo(0);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.RegActividadBean#init()}.
     */
    @Test
    public final void testInit() {
        regActividadBeanMock.init();
        assertThat(regActividadBeanMock.getRegActividadBusqueda()).isNotNull();
        assertThat(regActividadBeanMock.getList()).isNotNull();
        assertThat(regActividadBeanMock.getList().size()).isEqualTo(6);
        assertThat(regActividadBeanMock.getModel()).isNotNull();
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.RegActividadBean#autocompletarUsuario(java.lang.String)}.
     */
    @Test
    public final void testAutocompletarUsuario() {
        String infoUser = "zen";
        List<String> users = new ArrayList<>();
        users.add("ezentis");
        when(regActividadService.buscarPorUsuarioRegistro(any(String.class))).thenReturn(users);
        List<String> usuarios = regActividadBeanMock.autocompletarUsuario(infoUser);
        
        verify(regActividadService, times(1)).buscarPorUsuarioRegistro(any(String.class));
        assertThat(usuarios).hasSize(1);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.RegActividadBean#onRowSelect()}.
     */
    @Test
    public final void testOnRowSelect() {
        regActividadBeanMock.onRowSelect();
        verify(requestContext, times(1)).execute(any(String.class));
    }
    
}
