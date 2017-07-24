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
import java.util.Date;
import java.util.HashMap;
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
import org.primefaces.context.RequestContext;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.SolicitudDocumentacionPrevia;
import es.mira.progesin.persistence.entities.TipoInspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.cuestionarios.CuestionarioEnvio;
import es.mira.progesin.persistence.entities.enums.AmbitoInspeccionEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.entities.gd.Documento;
import es.mira.progesin.services.ICuestionarioEnvioService;
import es.mira.progesin.services.IInspeccionesService;
import es.mira.progesin.services.INotificacionService;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.ISolicitudDocumentacionService;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.web.beans.ApplicationBean;

/**
 * 
 * Test del bean EnvioCuestionarioBean.
 *
 * @author EZENTIS
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class, RequestContext.class })
public class EnvioCuestionarioBeanTest {
    /**
     * Constante user.
     */
    private static final String USUARIOLOGUEADO = "usuarioLogueado";
    
    /**
     * Constante correo.
     */
    private static final String CORREO = "correo_test";
    
    /**
     * Constante código inspección.
     */
    private static final String CODIGOINSPECCION = "I.G.P.";
    
    /**
     * Constante correo.
     */
    
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
     * Simulación del RequestContext.
     */
    @Mock
    private RequestContext requestContext;
    
    /**
     * Servicio de inspecciones.
     */
    @Mock
    private IInspeccionesService inspeccionService;
    
    /**
     * Servicio de Solicitud de documentación.
     */
    @Mock
    private ISolicitudDocumentacionService solDocService;
    
    /**
     * Servicio de registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Servicio de usuarios.
     */
    @Mock
    private IUserService userService;
    
    /**
     * Servicio de cuestionarios enviados.
     */
    @Mock
    private ICuestionarioEnvioService cuestionarioEnvioService;
    
    /**
     * Bean de configuración de la aplicación.
     */
    @Mock
    private ApplicationBean applicationBean;
    
    /**
     * Servicio de notificaciones.
     */
    @Mock
    private INotificacionService notificacionService;
    
    /**
     * Simulacion de EnvioCuestionarioBean.
     */
    @InjectMocks
    private EnvioCuestionarioBean envioCuestionarioBean;
    
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
        PowerMockito.mockStatic(RequestContext.class);
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USUARIOLOGUEADO);
        when(RequestContext.getCurrentInstance()).thenReturn(requestContext);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#autocompletarInspeccion(java.lang.String)}.
     */
    @Test
    public final void testAutocompletarInspeccionRolEquipo() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(inspeccion);
        String infoInspeccion = "test_info";
        User user = User.builder().username("pepe").role(RoleEnum.ROLE_EQUIPO_INSPECCIONES).build();
        when(authentication.getPrincipal()).thenReturn(user);
        when(inspeccionService.buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(infoInspeccion, user.getUsername()))
                .thenReturn(inspecciones);
        List<Inspeccion> resultadosBusqueda = envioCuestionarioBean.autocompletarInspeccion(infoInspeccion);
        verify(inspeccionService, times(1)).buscarNoFinalizadaPorNombreUnidadONumeroYJefeEquipo(infoInspeccion,
                user.getUsername());
        assertThat(resultadosBusqueda).isEqualTo(inspecciones);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#autocompletarInspeccion(java.lang.String)}.
     */
    @Test
    public final void testAutocompletarInspeccionRolNoEquipo() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        List<Inspeccion> inspecciones = new ArrayList<>();
        inspecciones.add(inspeccion);
        String infoInspeccion = "test_info";
        User user = User.builder().username("pepe").role(RoleEnum.ROLE_JEFE_INSPECCIONES).build();
        when(authentication.getPrincipal()).thenReturn(user);
        when(inspeccionService.buscarNoFinalizadaPorNombreUnidadONumero(infoInspeccion)).thenReturn(inspecciones);
        List<Inspeccion> resultadosBusqueda = envioCuestionarioBean.autocompletarInspeccion(infoInspeccion);
        verify(inspeccionService, times(1)).buscarNoFinalizadaPorNombreUnidadONumero(infoInspeccion);
        assertThat(resultadosBusqueda).isEqualTo(inspecciones);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#completarDatosSolicitudPrevia()}.
     */
    @Test
    public final void testCompletarDatosSolicitudPrevia() {
        TipoInspeccion tipoInspeccion = new TipoInspeccion();
        tipoInspeccion.setCodigo(CODIGOINSPECCION);
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setTipoInspeccion(tipoInspeccion);
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setInspeccion(inspeccion);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        List<SolicitudDocumentacionPrevia> listaSolicitudes = new ArrayList<>();
        SolicitudDocumentacionPrevia solicitudDocumentacionPrevia = new SolicitudDocumentacionPrevia();
        solicitudDocumentacionPrevia.setCorreoCorporativoInterlocutor("correoTest");
        solicitudDocumentacionPrevia.setNombreCompletoInterlocutor("nombreTest");
        solicitudDocumentacionPrevia.setCargoInterlocutor("cargoTest");
        solicitudDocumentacionPrevia.setFechaLimiteCumplimentar(new Date(1));
        listaSolicitudes.add(solicitudDocumentacionPrevia);
        when(solDocService.findFinalizadasPorInspeccion(inspeccion)).thenReturn(listaSolicitudes);
        
        envioCuestionarioBean.completarDatosSolicitudPrevia();
        verify(solDocService, times(1)).findFinalizadasPorInspeccion(inspeccion);
        assertThat(envioCuestionarioBean.getCuestionarioEnvio().getCorreoEnvio()).isEqualTo("correoTest");
        assertThat(envioCuestionarioBean.getCuestionarioEnvio().getNombreUsuarioEnvio()).isEqualTo("nombreTest");
        assertThat(envioCuestionarioBean.getCuestionarioEnvio().getCargo()).isEqualTo("cargoTest");
        assertThat(envioCuestionarioBean.getCuestionarioEnvio().getFechaLimiteCuestionario()).isEqualTo(new Date(1));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#completarDatosSolicitudPrevia()}.
     */
    @Test
    public final void testCompletarDatosSolicitudPreviaSinSolicitudes() {
        TipoInspeccion tipoInspeccion = new TipoInspeccion();
        tipoInspeccion.setCodigo(CODIGOINSPECCION);
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setTipoInspeccion(tipoInspeccion);
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setInspeccion(inspeccion);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        when(solDocService.findFinalizadasPorInspeccion(inspeccion)).thenReturn(new ArrayList<>());
        
        envioCuestionarioBean.completarDatosSolicitudPrevia();
        verify(solDocService, times(1)).findFinalizadasPorInspeccion(inspeccion);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_WARN), any(String.class), any(String.class),
                eq(null));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#enviarCuestionario()}.
     */
    @Test
    public final void testEnviarCuestionarioExisteCorreo() {
        String correoEnvio = CORREO;
        Inspeccion inspeccion = new Inspeccion();
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setInspeccion(inspeccion);
        cuestionarioEnvio.setCorreoEnvio(CORREO);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        when(solDocService.findNoFinalizadaPorInspeccion(inspeccion)).thenReturn(null);
        when(cuestionarioEnvioService.findNoFinalizadoPorInspeccion(inspeccion)).thenReturn(null);
        when(solDocService.findNoFinalizadaPorCorreoDestinatario(correoEnvio)).thenReturn(null);
        when(cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(correoEnvio)).thenReturn(null);
        when(userService.exists(correoEnvio)).thenReturn(true);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        
        envioCuestionarioBean.enviarCuestionario();
        verify(userService, times(1)).exists(correoEnvio);
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), any(String.class),
                any(String.class));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#enviarCuestionario()}.
     */
    @Test
    public final void testEnviarCuestionarioNoExisteCorreo() {
        String correoEnvio = CORREO;
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setAmbito(AmbitoInspeccionEnum.PN);
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        inspeccion.setEquipo(mock(Equipo.class));
        
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setInspeccion(inspeccion);
        cuestionarioEnvio.setCorreoEnvio(CORREO);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        when(solDocService.findNoFinalizadaPorInspeccion(inspeccion)).thenReturn(null);
        when(cuestionarioEnvioService.findNoFinalizadoPorInspeccion(inspeccion)).thenReturn(null);
        when(solDocService.findNoFinalizadaPorCorreoDestinatario(correoEnvio)).thenReturn(null);
        when(cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(correoEnvio)).thenReturn(null);
        when(userService.exists(correoEnvio)).thenReturn(false);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        
        User user = new User();
        user.setNombre("user_test");
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userService.crearUsuariosProvisionalesCuestionario(eq(correoEnvio), any(String.class))).thenReturn(users);
        
        Map<String, String> mapa = new HashMap<>();
        mapa.put(AmbitoInspeccionEnum.PN.name(), "ambitoPnTest");
        Map<String, Map<String, String>> mapaParametros = new HashMap<>();
        mapaParametros.put("URLPROGESIN", mapa);
        when(applicationBean.getMapaParametros()).thenReturn(mapaParametros);
        
        envioCuestionarioBean.enviarCuestionario();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
        verify(userService, times(1)).exists(correoEnvio);
        verify(userService, times(1)).crearUsuariosProvisionalesCuestionario(eq(correoEnvio), any(String.class));
        verify(applicationBean, times(1)).getMapaParametros();
        verify(cuestionarioEnvioService, times(1)).crearYEnviarCuestionario(eq(users), eq(cuestionarioEnvio),
                any(String.class));
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()));
        verify(notificacionService, times(1)).crearNotificacionRol(any(String.class),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()), eq(RoleEnum.ROLE_JEFE_INSPECCIONES));
        verify(notificacionService, times(1)).crearNotificacionEquipo(any(String.class),
                eq(SeccionesEnum.CUESTIONARIO.getDescripcion()), eq(cuestionarioEnvio.getInspeccion().getEquipo()));
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#enviarCuestionario()}.
     */
    @Test
    public final void testEnviarCuestionarioNoExisteCorreoException() {
        String correoEnvio = CORREO;
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setAmbito(AmbitoInspeccionEnum.PN);
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        inspeccion.setEquipo(mock(Equipo.class));
        
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setInspeccion(inspeccion);
        cuestionarioEnvio.setCorreoEnvio(CORREO);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        when(solDocService.findNoFinalizadaPorInspeccion(inspeccion)).thenReturn(null);
        when(cuestionarioEnvioService.findNoFinalizadoPorInspeccion(inspeccion)).thenReturn(null);
        when(solDocService.findNoFinalizadaPorCorreoDestinatario(correoEnvio)).thenReturn(null);
        when(cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(correoEnvio)).thenReturn(null);
        when(userService.exists(correoEnvio)).thenReturn(false);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        
        User user = new User();
        user.setNombre("user_test");
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userService.crearUsuariosProvisionalesCuestionario(eq(correoEnvio), any(String.class))).thenReturn(users);
        
        Map<String, String> mapa = new HashMap<>();
        mapa.put(AmbitoInspeccionEnum.PN.name(), "ambitoPnTest");
        Map<String, Map<String, String>> mapaParametros = new HashMap<>();
        mapaParametros.put("URLPROGESIN", mapa);
        when(applicationBean.getMapaParametros()).thenReturn(mapaParametros);
        doThrow(TransientDataAccessResourceException.class).when(cuestionarioEnvioService)
                .crearYEnviarCuestionario(eq(users), eq(cuestionarioEnvio), any(String.class));
        
        envioCuestionarioBean.enviarCuestionario();
        verify(userService, times(1)).exists(correoEnvio);
        verify(userService, times(1)).crearUsuariosProvisionalesCuestionario(eq(correoEnvio), any(String.class));
        verify(applicationBean, times(1)).getMapaParametros();
        verify(cuestionarioEnvioService, times(1)).crearYEnviarCuestionario(eq(users), eq(cuestionarioEnvio),
                any(String.class));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.CUESTIONARIO.getDescripcion()),
                any(Exception.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#inspeccionSinTareasPendientes()}.
     */
    @Test
    public final void testInspeccionSinTareasPendientes() {
        Inspeccion inspeccion = mock(Inspeccion.class);
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setInspeccion(inspeccion);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        when(solDocService.findNoFinalizadaPorInspeccion(inspeccion)).thenReturn(null);
        when(cuestionarioEnvioService.findNoFinalizadoPorInspeccion(inspeccion)).thenReturn(null);
        assertThat(envioCuestionarioBean.inspeccionSinTareasPendientes()).isTrue();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#inspeccionSinTareasPendientes()}.
     */
    @Test
    public final void testInspeccionConSolicitudesPendientes() {
        TipoInspeccion tipoInspeccion = new TipoInspeccion();
        tipoInspeccion.setCodigo(CODIGOINSPECCION);
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setTipoInspeccion(tipoInspeccion);
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setInspeccion(inspeccion);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        when(solDocService.findNoFinalizadaPorInspeccion(inspeccion))
                .thenReturn(mock(SolicitudDocumentacionPrevia.class));
        when(cuestionarioEnvioService.findNoFinalizadoPorInspeccion(inspeccion)).thenReturn(null);
        boolean resultado = envioCuestionarioBean.inspeccionSinTareasPendientes();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
        assertThat(resultado).isFalse();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#inspeccionSinTareasPendientes()}.
     */
    @Test
    public final void testInspeccionConCuestionariosPendientes() {
        TipoInspeccion tipoInspeccion = new TipoInspeccion();
        tipoInspeccion.setCodigo(CODIGOINSPECCION);
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setTipoInspeccion(tipoInspeccion);
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setInspeccion(inspeccion);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        when(solDocService.findNoFinalizadaPorInspeccion(inspeccion)).thenReturn(null);
        when(cuestionarioEnvioService.findNoFinalizadoPorInspeccion(inspeccion))
                .thenReturn(mock(CuestionarioEnvio.class));
        boolean resultado = envioCuestionarioBean.inspeccionSinTareasPendientes();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
        assertThat(resultado).isFalse();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#usuarioSinTareasPendientes()}.
     */
    @Test
    public final void testUsuarioSinTareasPendientes() {
        String correoEnvio = CORREO;
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setCorreoEnvio(CORREO);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        when(solDocService.findNoFinalizadaPorCorreoDestinatario(correoEnvio)).thenReturn(null);
        when(cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(correoEnvio)).thenReturn(null);
        assertThat(envioCuestionarioBean.usuarioSinTareasPendientes()).isTrue();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#usuarioSinTareasPendientes()}.
     */
    @Test
    public final void testUsuarioConSolicitudesPendientes() {
        String correoEnvio = CORREO;
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        SolicitudDocumentacionPrevia solicitud = new SolicitudDocumentacionPrevia();
        solicitud.setInspeccion(inspeccion);
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setCorreoEnvio(CORREO);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        
        when(solDocService.findNoFinalizadaPorCorreoDestinatario(correoEnvio)).thenReturn(solicitud);
        when(cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(correoEnvio)).thenReturn(null);
        boolean resultado = envioCuestionarioBean.usuarioSinTareasPendientes();
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
        assertThat(resultado).isFalse();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#usuarioSinTareasPendientes()}.
     */
    @Test
    public final void testUsuarioConCuestionariosPendientes() {
        String correoEnvio = CORREO;
        Inspeccion inspeccion = new Inspeccion();
        inspeccion.setId(1L);
        inspeccion.setAnio(2017);
        CuestionarioEnvio cuestionarioEnvio = new CuestionarioEnvio();
        cuestionarioEnvio.setCorreoEnvio(CORREO);
        cuestionarioEnvio.setInspeccion(inspeccion);
        envioCuestionarioBean.setCuestionarioEnvio(cuestionarioEnvio);
        
        when(solDocService.findNoFinalizadaPorCorreoDestinatario(correoEnvio)).thenReturn(null);
        when(cuestionarioEnvioService.findNoFinalizadoPorCorreoEnvio(correoEnvio)).thenReturn(cuestionarioEnvio);
        boolean resultado = envioCuestionarioBean.usuarioSinTareasPendientes();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
        assertThat(resultado).isFalse();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.cuestionarios.EnvioCuestionarioBean#adjuntarPlantilla()}.
     */
    @Test
    public final void testAdjuntarPlantilla() {
        List<Documento> plantillasSeleccionadas = new ArrayList<>();
        plantillasSeleccionadas.add(mock(Documento.class));
        envioCuestionarioBean.setPlantillasSeleccionadas(plantillasSeleccionadas);
        envioCuestionarioBean.setCuestionarioEnvio(new CuestionarioEnvio());
        envioCuestionarioBean.adjuntarPlantilla();
        assertThat(envioCuestionarioBean.getCuestionarioEnvio().getPlantillas()).isEqualTo(plantillasSeleccionadas);
        
    }
    
}
