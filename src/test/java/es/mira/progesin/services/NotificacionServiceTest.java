/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.repositories.INotificacionRepository;

/**
 * Test para el servicio de notificaciones.
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ SecurityContextHolder.class })
public class NotificacionServiceTest {
    /**
     * Constante descripción.
     */
    private static final String DESCRIPCIONOTIFICACION = "notificacion_test";
    
    /**
     * Constante usuario logueado.
     */
    private static final String USUARIOLOGUEADO = "usuarioLogueado";
    
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
     * Mock Repositorio de notificaciones.
     */
    @Mock
    private INotificacionRepository notificacionRepository;
    
    /**
     * Mock Servicio de notificaciones y notificaciones de usuario.
     */
    @Mock
    private IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
    
    /**
     * Mock Servicio del registro de actividad.
     */
    @Mock
    private IRegistroActividadService registroActividadService;
    
    /**
     * Instancia de prueba del servicio de notificaciones.
     */
    @InjectMocks
    private INotificacionService notificacionServiceMock = new NotificacionService();
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USUARIOLOGUEADO);
    }
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(AlertaService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        NotificacionService target = new NotificacionService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.NotificacionService#findOne(java.lang.Long)}.
     */
    @Test
    public final void testFindOne() {
        notificacionServiceMock.findOne(1L);
        verify(notificacionRepository, times(1)).findOne(1L);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.NotificacionService#crearNotificacionRol(java.lang.String, java.lang.String, es.mira.progesin.persistence.entities.enums.RoleEnum)}.
     */
    @Test
    public final void testCrearNotificacionRolStringStringRoleEnum() {
        String seccion = SeccionesEnum.ADMINISTRACION.name();
        RoleEnum rol = RoleEnum.ROLE_EQUIPO_INSPECCIONES;
        
        Notificacion notificacionGuardada = Notificacion.builder().descripcion(DESCRIPCIONOTIFICACION)
                .fechaAlta(new java.util.Date()).usernameNotificacion(USUARIOLOGUEADO).nombreSeccion(seccion)
                .idNotificacion(2L).build();
        
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionGuardada);
        notificacionServiceMock.crearNotificacionRol(DESCRIPCIONOTIFICACION, seccion, rol);
        
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeRol(notificacionGuardada, rol);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.NotificacionService#crearNotificacionRol(java.lang.String, java.lang.String, es.mira.progesin.persistence.entities.enums.RoleEnum)}.
     */
    @Test
    public final void testCrearNotificacionRolStringStringRoleEnumException() {
        String seccion = SeccionesEnum.ADMINISTRACION.name();
        RoleEnum rol = RoleEnum.ROLE_EQUIPO_INSPECCIONES;
        
        when(notificacionRepository.save(any(Notificacion.class)))
                .thenThrow(TransientDataAccessResourceException.class);
        notificacionServiceMock.crearNotificacionRol(DESCRIPCIONOTIFICACION, seccion, rol);
        
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
        verify(registroActividadService, times(1)).altaRegActividadError(eq(seccion),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.NotificacionService#crearNotificacionRol(java.lang.String, java.lang.String, java.util.List)}.
     */
    @Test
    public final void testCrearNotificacionRolStringStringListOfRoleEnum() {
        String seccion = SeccionesEnum.ADMINISTRACION.name();
        RoleEnum rol1 = RoleEnum.ROLE_EQUIPO_INSPECCIONES;
        RoleEnum rol2 = RoleEnum.ROLE_JEFE_INSPECCIONES;
        List<RoleEnum> listRoles = new ArrayList<>();
        listRoles.add(rol1);
        listRoles.add(rol2);
        
        Notificacion notificacionGuardada = Notificacion.builder().descripcion(DESCRIPCIONOTIFICACION)
                .fechaAlta(new java.util.Date()).usernameNotificacion(USUARIOLOGUEADO).nombreSeccion(seccion)
                .idNotificacion(2L).build();
        
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionGuardada);
        notificacionServiceMock.crearNotificacionRol(DESCRIPCIONOTIFICACION, seccion, listRoles);
        
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeRol(notificacionGuardada, listRoles);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.NotificacionService#crearNotificacionRol(java.lang.String, java.lang.String, java.util.List)}.
     */
    @Test
    public final void testCrearNotificacionRolStringStringListOfRoleEnumException() {
        String seccion = SeccionesEnum.ADMINISTRACION.name();
        RoleEnum rol1 = RoleEnum.ROLE_EQUIPO_INSPECCIONES;
        RoleEnum rol2 = RoleEnum.ROLE_JEFE_INSPECCIONES;
        List<RoleEnum> listRoles = new ArrayList<>();
        listRoles.add(rol1);
        listRoles.add(rol2);
        
        when(notificacionRepository.save(any(Notificacion.class)))
                .thenThrow(TransientDataAccessResourceException.class);
        notificacionServiceMock.crearNotificacionRol(DESCRIPCIONOTIFICACION, seccion, listRoles);
        
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
        verify(registroActividadService, times(1)).altaRegActividadError(eq(seccion),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.NotificacionService#crearNotificacionJefeEquipo(java.lang.String, java.lang.String, es.mira.progesin.persistence.entities.Inspeccion)}.
     */
    @Test
    public final void testCrearNotificacionJefeEquipo() {
        String seccion = SeccionesEnum.ADMINISTRACION.name();
        Inspeccion inspeccion = mock(Inspeccion.class);
        
        Notificacion notificacionGuardada = Notificacion.builder().descripcion(DESCRIPCIONOTIFICACION)
                .fechaAlta(new java.util.Date()).usernameNotificacion(USUARIOLOGUEADO).nombreSeccion(seccion)
                .idNotificacion(2L).build();
        
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionGuardada);
        notificacionServiceMock.crearNotificacionJefeEquipo(DESCRIPCIONOTIFICACION, seccion, inspeccion);
        
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeJefeEquipo(notificacionGuardada, inspeccion);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.NotificacionService#crearNotificacionJefeEquipo(java.lang.String, java.lang.String, es.mira.progesin.persistence.entities.Inspeccion)}.
     */
    @Test
    public final void testCrearNotificacionJefeEquipoException() {
        String seccion = SeccionesEnum.ADMINISTRACION.name();
        Inspeccion inspeccion = mock(Inspeccion.class);
        
        when(notificacionRepository.save(any(Notificacion.class)))
                .thenThrow(TransientDataAccessResourceException.class);
        notificacionServiceMock.crearNotificacionJefeEquipo(DESCRIPCIONOTIFICACION, seccion, inspeccion);
        
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
        verify(registroActividadService, times(1)).altaRegActividadError(eq(seccion),
                any(TransientDataAccessResourceException.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.NotificacionService#crearNotificacionEquipo(java.lang.String, java.lang.String, es.mira.progesin.persistence.entities.Equipo)}.
     */
    @Test
    public final void testCrearNotificacionEquipo() {
        String seccion = SeccionesEnum.ADMINISTRACION.name();
        Equipo equipo = mock(Equipo.class);
        
        Notificacion notificacionGuardada = Notificacion.builder().descripcion(DESCRIPCIONOTIFICACION)
                .fechaAlta(new java.util.Date()).usernameNotificacion(USUARIOLOGUEADO).nombreSeccion(seccion)
                .idNotificacion(2L).build();
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionGuardada);
        notificacionServiceMock.crearNotificacionEquipo(DESCRIPCIONOTIFICACION, seccion, equipo);
        
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeEquipo(notificacionGuardada, equipo);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.NotificacionService#crearNotificacionEquipo(java.lang.String, java.lang.String, es.mira.progesin.persistence.entities.Equipo)}.
     */
    @Test
    public final void testCrearNotificacionEquipoException() {
        String seccion = SeccionesEnum.ADMINISTRACION.name();
        Equipo equipo = mock(Equipo.class);
        
        when(notificacionRepository.save(any(Notificacion.class)))
                .thenThrow(TransientDataAccessResourceException.class);
        notificacionServiceMock.crearNotificacionEquipo(DESCRIPCIONOTIFICACION, seccion, equipo);
        
        verify(notificacionRepository, times(1)).save(any(Notificacion.class));
        verify(registroActividadService, times(1)).altaRegActividadError(eq(seccion),
                any(TransientDataAccessResourceException.class));
    }
    
}
