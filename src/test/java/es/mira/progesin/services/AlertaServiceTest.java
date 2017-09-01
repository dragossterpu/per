/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.TransientDataAccessResourceException;

import es.mira.progesin.exceptions.CorreoException;
import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.repositories.IAlertaRepository;
import es.mira.progesin.util.ICorreoElectronico;

/**
 * Test para el servicio de alertas.
 * @author EZENTIS
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AlertaServiceTest {
    
    /**
     * Mock Repositorio de alertas.
     */
    @Mock
    private IAlertaRepository alertaRepository;
    
    /**
     * Mock Servicio de alertas y notificaciones de usuario.
     */
    @Mock
    private IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioService;
    
    /**
     * Mock Servicio de correo electrónico.
     */
    @Mock
    private ICorreoElectronico correo;
    
    /**
     * Mock Servicio de usuario.
     */
    @Mock
    private IUserService userService;
    
    /**
     * Mock Servicio del registro de actividad.
     */
    @Mock
    private IRegistroActividadService registroActividadService;
    
    /**
     * Instancia de prueba del servicio de alertas.
     */
    @InjectMocks
    private IAlertaService alertaServiceMock = new AlertaService();
    
    /**
     * Constante descripción.
     */
    private static final String DESCRIPCIONALERTA = "alerat_test";
    
    /**
     * Constante usuario 1.
     */
    private static final String USUARIO1 = "ezentis1";
    
    /**
     * Constante usuario 2.
     */
    private static final String USUARIO2 = "ezentis2";
    
    /**
     * Constante correo usuario 1.
     */
    private static final String CORREOUSUARIO1 = "ezentis1@ezentis.com";
    
    /**
     * Constante correo usuario 2.
     */
    private static final String CORREOUSUARIO2 = "ezentis2@ezentis.com";
    
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
        AlertaService target = new AlertaService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.AlertaService#delete(java.lang.Long)}.
     */
    @Test
    public final void testDelete() {
        alertaServiceMock.delete(2L);
        verify(alertaRepository, times(1)).delete(2L);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.AlertaService#deleteAll()}.
     */
    @Test
    public final void testDeleteAll() {
        alertaServiceMock.deleteAll();
        verify(alertaRepository, times(1)).deleteAll();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.AlertaService#exists(java.lang.Long)}.
     */
    @Test
    public final void testExists() {
        alertaServiceMock.exists(3L);
        verify(alertaRepository, times(1)).exists(3L);
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.AlertaService#findByFechaBajaIsNull()}.
     */
    @Test
    public final void testFindByFechaBajaIsNull() {
        alertaServiceMock.findByFechaBajaIsNull();
        verify(alertaRepository, times(1)).findByFechaBajaIsNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.AlertaService#findOne(java.lang.Long)}.
     */
    @Test
    public final void testFindOne() {
        alertaServiceMock.findOne(1L);
        verify(alertaRepository, times(1)).findOne(1L);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertaService#crearAlertaRol(java.lang.String, java.lang.String, es.mira.progesin.persistence.entities.enums.RoleEnum)}.
     */
    @Test
    public final void testCrearAlertaRolStringStringRoleEnum() {
        String seccion = SeccionesEnum.ADMINISTRACION.getDescripcion();
        String descripcion = DESCRIPCIONALERTA;
        RoleEnum rol = RoleEnum.ROLE_EQUIPO_INSPECCIONES;
        List<User> usuariosRol = new ArrayList<>();
        User usuario1 = new User();
        usuario1.setUsername(USUARIO1);
        usuario1.setCorreo(CORREOUSUARIO1);
        User usuario2 = new User();
        usuario2.setUsername(USUARIO2);
        usuario2.setCorreo(CORREOUSUARIO2);
        usuariosRol.add(usuario1);
        usuariosRol.add(usuario2);
        List<String> listaCorreos = new ArrayList<>();
        listaCorreos.add(CORREOUSUARIO1);
        listaCorreos.add(CORREOUSUARIO2);
        Alerta alertaSinId = Alerta.builder().descripcion(descripcion).nombreSeccion(seccion).build();
        Alerta alertaGuardada = Alerta.builder().descripcion(descripcion).nombreSeccion(seccion).idAlerta(2L).build();
        
        when(alertaRepository.save(alertaSinId)).thenReturn(alertaGuardada);
        when(userService.findByfechaBajaIsNullAndRole(rol)).thenReturn(usuariosRol);
        
        alertaServiceMock.crearAlertaRol(seccion, descripcion, rol);
        
        verify(alertaRepository, times(1)).save(alertaSinId);
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeUsuario(alertaGuardada,
                usuario1.getUsername());
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeUsuario(alertaGuardada,
                usuario2.getUsername());
        
        verify(correo, times(1)).envioCorreo(eq(String.join(", ", listaCorreos)), any(String.class), any(String.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertaService#crearAlertaRol(java.lang.String, java.lang.String, es.mira.progesin.persistence.entities.enums.RoleEnum)}.
     */
    @Test
    public final void testCrearAlertaRolStringStringRoleEnumException() {
        String seccion = SeccionesEnum.ADMINISTRACION.getDescripcion();
        String descripcion = DESCRIPCIONALERTA;
        RoleEnum rol = RoleEnum.ROLE_EQUIPO_INSPECCIONES;
        List<User> usuariosRol = new ArrayList<>();
        User usuario1 = new User();
        usuario1.setUsername(USUARIO1);
        usuario1.setCorreo(CORREOUSUARIO1);
        User usuario2 = new User();
        usuario2.setUsername(USUARIO2);
        usuario2.setCorreo(CORREOUSUARIO2);
        usuariosRol.add(usuario1);
        usuariosRol.add(usuario2);
        List<String> listaCorreos = new ArrayList<>();
        listaCorreos.add(CORREOUSUARIO1);
        listaCorreos.add(CORREOUSUARIO2);
        Alerta alertaSinId = Alerta.builder().descripcion(descripcion).nombreSeccion(seccion).build();
        Alerta alertaGuardada = Alerta.builder().descripcion(descripcion).nombreSeccion(seccion).idAlerta(2L).build();
        
        when(alertaRepository.save(alertaSinId)).thenReturn(alertaGuardada);
        when(userService.findByfechaBajaIsNullAndRole(rol)).thenReturn(usuariosRol);
        doThrow(CorreoException.class).when(correo).envioCorreo(eq(String.join(", ", listaCorreos)), any(String.class),
                any(String.class));
        
        alertaServiceMock.crearAlertaRol(seccion, descripcion, rol);
        
        verify(alertaRepository, times(1)).save(alertaSinId);
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeUsuario(alertaGuardada,
                usuario1.getUsername());
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeUsuario(alertaGuardada,
                usuario2.getUsername());
        
        verify(correo, times(1)).envioCorreo(eq(String.join(", ", listaCorreos)), any(String.class), any(String.class));
        verify(registroActividadService, times(1)).altaRegActividadError(any(String.class), any(CorreoException.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertaService#crearAlertaRol(java.lang.String, java.lang.String, java.util.List)}.
     */
    @Test
    public final void testCrearAlertaRolStringStringListOfRoleEnum() {
        String seccion = SeccionesEnum.ADMINISTRACION.getDescripcion();
        String descripcion = DESCRIPCIONALERTA;
        List<RoleEnum> roles = new ArrayList<>();
        RoleEnum rol1 = RoleEnum.ROLE_EQUIPO_INSPECCIONES;
        RoleEnum rol2 = RoleEnum.ROLE_SERVICIO_APOYO;
        roles.add(rol1);
        roles.add(rol2);
        List<User> usuariosRol1 = new ArrayList<>();
        User usuario1 = new User();
        usuario1.setUsername(USUARIO1);
        usuario1.setCorreo(CORREOUSUARIO1);
        User usuario2 = new User();
        usuario2.setUsername(USUARIO2);
        usuario2.setCorreo(CORREOUSUARIO2);
        usuariosRol1.add(usuario1);
        usuariosRol1.add(usuario2);
        List<User> usuariosRol2 = new ArrayList<>();
        User usuario3 = new User();
        usuario3.setUsername("ezentis3");
        usuario3.setCorreo("ezentis3@ezentis.com");
        User usuario4 = new User();
        usuario4.setUsername("ezentis4");
        usuario4.setCorreo("ezentis4@ezentis.com");
        usuariosRol2.add(usuario3);
        usuariosRol2.add(usuario4);
        List<String> listaCorreos1 = new ArrayList<>();
        listaCorreos1.add(CORREOUSUARIO1);
        listaCorreos1.add(CORREOUSUARIO2);
        List<String> listaCorreos2 = new ArrayList<>();
        listaCorreos2.add("ezentis3@ezentis.com");
        listaCorreos2.add("ezentis4@ezentis.com");
        
        Alerta alertaSinId = Alerta.builder().descripcion(descripcion).nombreSeccion(seccion).build();
        Alerta alertaGuardada = Alerta.builder().descripcion(descripcion).nombreSeccion(seccion).idAlerta(1L).build();
        
        when(alertaRepository.save(alertaSinId)).thenReturn(alertaGuardada);
        when(userService.findByfechaBajaIsNullAndRole(rol1)).thenReturn(usuariosRol1);
        when(userService.findByfechaBajaIsNullAndRole(rol2)).thenReturn(usuariosRol2);
        
        alertaServiceMock.crearAlertaRol(seccion, descripcion, roles);
        
        verify(alertaRepository, times(2)).save(alertaSinId);
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeUsuario(alertaGuardada,
                usuario1.getUsername());
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeUsuario(alertaGuardada,
                usuario2.getUsername());
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeUsuario(alertaGuardada,
                usuario3.getUsername());
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeUsuario(alertaGuardada,
                usuario4.getUsername());
        
        verify(correo, times(1)).envioCorreo(eq(String.join(", ", listaCorreos1)), any(String.class),
                any(String.class));
        verify(correo, times(1)).envioCorreo(eq(String.join(", ", listaCorreos2)), any(String.class),
                any(String.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertaService#crearAlertaEquipo(java.lang.String, java.lang.String, es.mira.progesin.persistence.entities.Inspeccion)}.
     */
    @Test
    public final void testCrearAlertaEquipo() {
        String seccion = SeccionesEnum.ADMINISTRACION.getDescripcion();
        String descripcion = DESCRIPCIONALERTA;
        List<User> usuarios = new ArrayList<>();
        User usuario1 = new User();
        usuario1.setUsername(USUARIO1);
        usuario1.setCorreo(CORREOUSUARIO1);
        User usuario2 = new User();
        usuario2.setUsername(USUARIO2);
        usuario2.setCorreo(CORREOUSUARIO2);
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        Inspeccion inspeccion = new Inspeccion();
        Equipo equipo = mock(Equipo.class);
        inspeccion.setEquipo(equipo);
        List<String> listaCorreos = new ArrayList<>();
        listaCorreos.add(CORREOUSUARIO1);
        listaCorreos.add(CORREOUSUARIO2);
        
        Alerta alertaSinId1 = Alerta.builder().descripcion(descripcion).nombreSeccion(seccion).build();
        Alerta alertaGuardada = Alerta.builder().descripcion(descripcion).nombreSeccion(seccion).idAlerta(1L).build();
        
        when(userService.usuariosEquipo(equipo)).thenReturn(usuarios);
        when(alertaRepository.save(alertaSinId1)).thenReturn(alertaGuardada);
        
        alertaServiceMock.crearAlertaEquipo(seccion, descripcion, inspeccion);
        
        verify(userService, times(1)).usuariosEquipo(inspeccion.getEquipo());
        verify(alertaRepository, times(1)).save(alertaSinId1);
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeUsuario(alertaGuardada,
                usuario1.getUsername());
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeUsuario(alertaGuardada,
                usuario2.getUsername());
        verify(correo, times(1)).envioCorreo(eq(String.join(", ", listaCorreos)), any(String.class), any(String.class));
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertaService#crearAlertaEquipo(java.lang.String, java.lang.String, es.mira.progesin.persistence.entities.Inspeccion)}.
     */
    @Test
    public final void testCrearAlertaEquipoException() {
        String seccion = SeccionesEnum.ADMINISTRACION.getDescripcion();
        String descripcion = DESCRIPCIONALERTA;
        List<User> usuarios = new ArrayList<>();
        User usuario1 = new User();
        usuario1.setUsername(USUARIO1);
        usuario1.setCorreo(CORREOUSUARIO1);
        User usuario2 = new User();
        usuario2.setUsername(USUARIO2);
        usuario2.setCorreo(CORREOUSUARIO2);
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        Inspeccion inspeccion = new Inspeccion();
        Equipo equipo = mock(Equipo.class);
        inspeccion.setEquipo(equipo);
        List<String> listaCorreos = new ArrayList<>();
        listaCorreos.add(CORREOUSUARIO1);
        listaCorreos.add(CORREOUSUARIO2);
        Alerta alertaSinId1 = Alerta.builder().descripcion(descripcion).nombreSeccion(seccion).build();
        
        when(userService.usuariosEquipo(equipo)).thenReturn(usuarios);
        when(alertaRepository.save(alertaSinId1)).thenThrow(TransientDataAccessResourceException.class);
        
        alertaServiceMock.crearAlertaEquipo(seccion, descripcion, inspeccion);
        
        verify(userService, times(1)).usuariosEquipo(inspeccion.getEquipo());
        verify(alertaRepository, times(1)).save(alertaSinId1);
        
        verify(registroActividadService, times(1)).altaRegActividadError(any(String.class),
                any(TransientDataAccessResourceException.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertaService#crearAlertaJefeEquipo(java.lang.String, java.lang.String, es.mira.progesin.persistence.entities.Inspeccion)}.
     */
    @Test
    public final void testCrearAlertaJefeEquipo() {
        String seccion = SeccionesEnum.ADMINISTRACION.getDescripcion();
        String descripcion = DESCRIPCIONALERTA;
        List<User> usuarios = new ArrayList<>();
        User usuario1jefe = new User();
        usuario1jefe.setUsername(USUARIO1);
        usuario1jefe.setCorreo(CORREOUSUARIO1);
        User usuario2 = new User();
        usuario2.setUsername(USUARIO2);
        usuario2.setCorreo(CORREOUSUARIO2);
        usuarios.add(usuario1jefe);
        usuarios.add(usuario2);
        Inspeccion inspeccion = new Inspeccion();
        Equipo equipo = new Equipo();
        equipo.setJefeEquipo(usuario1jefe);
        inspeccion.setEquipo(equipo);
        List<String> listaCorreos = new ArrayList<>();
        listaCorreos.add(CORREOUSUARIO1);
        listaCorreos.add(CORREOUSUARIO2);
        
        Alerta alertaSinId1 = Alerta.builder().descripcion(descripcion).nombreSeccion(seccion).build();
        Alerta alertaGuardada = Alerta.builder().descripcion(descripcion).nombreSeccion(seccion).idAlerta(1L).build();
        
        when(userService.findOne(inspeccion.getEquipo().getJefeEquipo().getUsername())).thenReturn(usuario1jefe);
        when(alertaRepository.save(alertaSinId1)).thenReturn(alertaGuardada);
        
        alertaServiceMock.crearAlertaJefeEquipo(seccion, descripcion, inspeccion);
        
        verify(userService, times(1)).findOne(inspeccion.getEquipo().getJefeEquipo().getUsername());
        verify(alertaRepository, times(1)).save(alertaSinId1);
        verify(alertasNotificacionesUsuarioService, times(1)).grabarMensajeUsuario(alertaGuardada,
                usuario1jefe.getUsername());
        verify(correo, times(1)).envioCorreo(eq(usuario1jefe.getCorreo()), any(String.class), any(String.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertaService#crearAlertaJefeEquipo(java.lang.String, java.lang.String, es.mira.progesin.persistence.entities.Inspeccion)}.
     */
    @Test
    public final void testCrearAlertaJefeEquipoException() {
        String seccion = SeccionesEnum.ADMINISTRACION.getDescripcion();
        String descripcion = DESCRIPCIONALERTA;
        List<User> usuarios = new ArrayList<>();
        User usuario1jefe = new User();
        usuario1jefe.setUsername(USUARIO1);
        usuario1jefe.setCorreo(CORREOUSUARIO1);
        User usuario2 = new User();
        usuario2.setUsername(USUARIO2);
        usuario2.setCorreo(CORREOUSUARIO2);
        usuarios.add(usuario1jefe);
        usuarios.add(usuario2);
        Inspeccion inspeccion = new Inspeccion();
        Equipo equipo = new Equipo();
        equipo.setJefeEquipo(usuario1jefe);
        inspeccion.setEquipo(equipo);
        List<String> listaCorreos = new ArrayList<>();
        listaCorreos.add(CORREOUSUARIO1);
        listaCorreos.add(CORREOUSUARIO2);
        
        Alerta alertaSinId1 = Alerta.builder().descripcion(descripcion).nombreSeccion(seccion).build();
        when(userService.findOne(inspeccion.getEquipo().getJefeEquipo().getUsername())).thenReturn(usuario1jefe);
        when(alertaRepository.save(alertaSinId1)).thenThrow(TransientDataAccessResourceException.class);
        
        alertaServiceMock.crearAlertaJefeEquipo(seccion, descripcion, inspeccion);
        
        verify(userService, times(1)).findOne(inspeccion.getEquipo().getJefeEquipo().getUsername());
        verify(alertaRepository, times(1)).save(alertaSinId1);
        
        verify(registroActividadService, times(1)).altaRegActividadError(any(String.class),
                any(TransientDataAccessResourceException.class));
    }
    
}
