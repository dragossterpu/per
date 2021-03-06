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
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.lazydata.LazyModelCuestionarioEnviado;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Municipio;
import es.mira.progesin.persistence.entities.Provincia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.TipoUnidad;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.cuestionarios.ModeloCuestionario;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.IModeloCuestionarioService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ITipoInspeccionService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.util.Utilities;

/**
 * 
 * Test del bean EnvioCuestionarioBean.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class, Utilities.class })
public class CuestionarioEnviadoBeanTest {
    /**
     * Constante user.
     */
    private static final String USUARIOLOGUEADO = "usuarioLogueado";
    
    /**
     * Constante tipo unidad.
     */
    private static final String TIPOUNIDAD = "tipoUnidad";
    
    /**
     * Constante correo envio.
     */
    private static final String CORREOENVIO = "correo_test";
    
    /**
     * Constante Provincia/Ciudad.
     */
    private static final String PROVINCIA = "Toledo";
    
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
     * Mock Servicio de cuestionarios enviados.
     */
    @Mock
    private ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Servicio de modelos de cuestionario.
     */
    @Mock
    private IModeloCuestionarioService modeloCuestionarioService;
    
    /**
     * Mock Servicio de correos electrónicos.
     */
    @Mock
    private ICorreoElectronico correoElectronico;
    
    /**
     * Mock Servicio del registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Mock Servicio de tipos de inspección.
     */
    @Mock
    private ITipoInspeccionService tipoInspeccionService;
    
    /**
     * Objeto que define la tabla de resultados del buscador.
     */
    private LazyModelCuestionarioEnviado model;
    
    /**
     * Simulación de CuestionarioEnviadoBean.
     */
    @InjectMocks
    private CuestionarioEnviadoBean cuestionarioEnviadoBeanMock;
    
    /**
     * Comprueba que la clase existe.
     */
    @Test
    public void type() {
        assertThat(EdicionCuestionarioBean.class).isNotNull();
    }
    
    /**
     * Comprueba que la clase se puede instanciar.
     */
    @Test
    public void instantiation() {
        EdicionCuestionarioBean target = new EdicionCuestionarioBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Inicializa el test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        PowerMockito.mockStatic(Utilities.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USUARIOLOGUEADO);
        model = new LazyModelCuestionarioEnviado(cuestionarioEnvioService);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#buscarCuestionario()}.
     */
    @Test
    public final void testBuscarCuestionario() {
        List<CuestionarioEnvio> cuestionarios = new ArrayList<>();
        cuestionarios.add(mock(CuestionarioEnvio.class));
        CuestionarioEnviadoBusqueda cuestionarioEnviadoBusqueda = new CuestionarioEnviadoBusqueda();
        cuestionarioEnviadoBeanMock.setCuestionarioEnviadoBusqueda(cuestionarioEnviadoBusqueda);
        cuestionarioEnviadoBeanMock.setModel(model);
        when(cuestionarioEnvioService.getCountCuestionarioCriteria(cuestionarioEnviadoBusqueda)).thenReturn(1);
        when(cuestionarioEnvioService.buscarCuestionarioEnviadoCriteria(0, Constantes.TAMPAGINA, "fechaEnvio",
                SortOrder.DESCENDING, cuestionarioEnviadoBusqueda)).thenReturn(cuestionarios);
        cuestionarioEnviadoBeanMock.buscarCuestionario();
        assertThat(cuestionarioEnviadoBeanMock.getModel().getRowCount()).isEqualTo(1);
        assertThat(cuestionarioEnviadoBeanMock.getModel().getDatasource()).hasSize(1);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#getFormBusquedaCuestionarios()}.
     */
    @Test
    public final void testGetFormBusquedaCuestionarios() {
        model = new LazyModelCuestionarioEnviado(cuestionarioEnvioService);
        model.setRowCount(1);
        cuestionarioEnviadoBeanMock.setModel(model);
        String redireccion = cuestionarioEnviadoBeanMock.getFormBusquedaCuestionarios();
        assertThat(cuestionarioEnviadoBeanMock.getCuestionarioEnviadoBusqueda()).isNotNull();
        assertThat(cuestionarioEnviadoBeanMock.getModel().getRowCount()).isEqualTo(0);
        assertThat(redireccion).isEqualTo("/cuestionarios/busquedaCuestionarios?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#limpiar()}.
     */
    @Test
    public final void testLimpiar() {
        model = new LazyModelCuestionarioEnviado(cuestionarioEnvioService);
        model.setRowCount(1);
        cuestionarioEnviadoBeanMock.setModel(model);
        cuestionarioEnviadoBeanMock.limpiar();
        assertThat(cuestionarioEnviadoBeanMock.getCuestionarioEnviadoBusqueda()).isNotNull();
        assertThat(cuestionarioEnviadoBeanMock.getModel().getRowCount()).isEqualTo(0);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#eliminarCuestionario(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio)}.
     */
    @Test
    public final void testEliminarCuestionario() {
        User user = new User();
        user.setUsername(USUARIOLOGUEADO);
        user.setRole(RoleEnum.ROLE_JEFE_INSPECCIONES);
        when(authentication.getPrincipal()).thenReturn(user);
        Equipo equipo = new Equipo();
        equipo.setJefeEquipo(user);
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        inspeccion.setEquipo(equipo);
        TipoUnidad tipoUnidad = new TipoUnidad();
        tipoUnidad.setDescripcion(TIPOUNIDAD);
        inspeccion.setTipoUnidad(tipoUnidad);
        Provincia provincia = new Provincia();
        provincia.setNombre(PROVINCIA);
        Municipio municipio = new Municipio();
        municipio.setName(PROVINCIA);
        municipio.setProvincia(provincia);
        inspeccion.setMunicipio(municipio);
        
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setInspeccion(inspeccion);
        cuestionarioEnvio.setCorreoEnvio(CORREOENVIO);
        Map<String, String> mapa = null;
        cuestionarioEnviadoBeanMock.eliminarCuestionario(cuestionarioEnvio);
        
        assertThat(cuestionarioEnvio.getFechaAnulacion()).isNotNull();
        assertThat(cuestionarioEnvio.getUsernameAnulacion()).isNotNull();
        verify(cuestionarioEnvioService, times(1)).transaccSaveElimUsuariosProv(cuestionarioEnvio);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_INFO), any(String.class), any(String.class),
                eq(null));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()));
        verify(correoElectronico, times(1)).envioCorreo(eq(cuestionarioEnvio.getCorreoEnvio()), any(String.class),
                eq(Constantes.TEMPLATEBAJACUESTIONARIO), eq(mapa));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#eliminarCuestionario(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio)}.
     */
    @Test
    public final void testEliminarCuestionarioRolNoPermitido() {
        User userActual = new User();
        userActual.setUsername(USUARIOLOGUEADO);
        userActual.setRole(RoleEnum.ROLE_GABINETE);
        User jefe = new User();
        jefe.setUsername("jefe_test");
        when(authentication.getPrincipal()).thenReturn(userActual);
        Equipo equipo = new Equipo();
        equipo.setJefeEquipo(jefe);
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        inspeccion.setEquipo(equipo);
        
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setInspeccion(inspeccion);
        cuestionarioEnvio.setCorreoEnvio(CORREOENVIO);
        cuestionarioEnviadoBeanMock.eliminarCuestionario(cuestionarioEnvio);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_WARN), any(String.class), any(String.class),
                eq(null));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#eliminarCuestionario(es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio)}.
     */
    @Test
    public final void testEliminarCuestionarioException() {
        User user = new User();
        user.setUsername(USUARIOLOGUEADO);
        user.setRole(RoleEnum.ROLE_JEFE_INSPECCIONES);
        when(authentication.getPrincipal()).thenReturn(user);
        Equipo equipo = new Equipo();
        equipo.setJefeEquipo(user);
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        inspeccion.setEquipo(equipo);
        
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setInspeccion(inspeccion);
        cuestionarioEnvio.setCorreoEnvio(CORREOENVIO);
        doThrow(TransientDataAccessResourceException.class).when(cuestionarioEnvioService)
                .transaccSaveElimUsuariosProv(cuestionarioEnvio);
        
        cuestionarioEnviadoBeanMock.eliminarCuestionario(cuestionarioEnvio);
        verify(cuestionarioEnvioService, times(1)).transaccSaveElimUsuariosProv(cuestionarioEnvio);
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), eq(TipoRegistroEnum.ERROR.name()),
                any(String.class), eq(null));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#init()}.
     */
    @Test
    public final void testInit() {
        List<TipoInspeccion> tiposInspeccion = new ArrayList<>();
        tiposInspeccion.add(mock(TipoInspeccion.class));
        cuestionarioEnviadoBeanMock.setModel(model);
        when(tipoInspeccionService.buscaTodos()).thenReturn(tiposInspeccion);
        List<ModeloCuestionario> modelosCuestionario = new ArrayList<>();
        when(modeloCuestionarioService.findAll()).thenReturn(modelosCuestionario);
        cuestionarioEnviadoBeanMock.init();
        assertThat(cuestionarioEnviadoBeanMock.getList()).hasSize(15);
        assertThat(cuestionarioEnviadoBeanMock.getCuestionarioEnviadoBusqueda()).isNotNull();
        assertThat(cuestionarioEnviadoBeanMock.getModel()).isNotNull();
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.CuestionarioEnviadoBean#onToggle(org.primefaces.event.ToggleEvent)}.
     */
    @Test
    public final void testOnToggle() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        when(eventMock.getVisibility()).thenReturn(Visibility.HIDDEN);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.FALSE);
        cuestionarioEnviadoBeanMock.setList(listaToogle);
        cuestionarioEnviadoBeanMock.onToggle(eventMock);
        assertThat(listaToogle.get(0)).isFalse();
    }
    
}
