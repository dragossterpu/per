/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

import es.mira.progesin.persistence.entities.Alerta;
import es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario;
import es.mira.progesin.persistence.entities.Equipo;
import es.mira.progesin.persistence.entities.Inspeccion;
import es.mira.progesin.persistence.entities.Miembro;
import es.mira.progesin.persistence.entities.Notificacion;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.entities.enums.TipoMensajeEnum;
import es.mira.progesin.persistence.repositories.IAlertasNotificacionesUsuarioRepository;
import es.mira.progesin.persistence.repositories.IMiembrosRepository;

/**
 * Test para el servicio de alertas y notificaciones de usuario.
 * @author EZENTIS
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AlertasNotificacionesUsuarioServiceTest {
    
    /**
     * Constante usuario logueado.
     */
    private static final String USUARIOLOGUEADO = "usuarioLogueado";
    
    /**
     * Constante descripcion.
     */
    private static final String DESCRIPCIONTEST = "descripcionTest";
    
    /**
     * Constante seccion.
     */
    private static final String SECCIONTEST = "seccionTest";
    
    /**
     * Repositorio de Alertas y notificaciones.
     */
    @Mock
    private IAlertasNotificacionesUsuarioRepository mensajeRepository;
    
    /**
     * Servicio de usuarios.
     */
    @Mock
    private IUserService userService;
    
    /**
     * Servicio de alertas.
     */
    @Mock
    private IAlertaService alertaService;
    
    /**
     * Servicio de notificaciones.
     */
    @Mock
    private INotificacionService notificacionService;
    
    /**
     * Servicio de miembros.
     */
    @Mock
    private IMiembrosRepository miembrosRepository;
    
    /**
     * Instancia de prueba del servicio de notificaciones y alertas de usuario.
     */
    @InjectMocks
    private IAlertasNotificacionesUsuarioService alertasNotificacionesUsuarioServiceMock = new AlertasNotificacionesUsuarioService();
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(AlertasNotificacionesUsuarioService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        AlertasNotificacionesUsuarioService target = new AlertasNotificacionesUsuarioService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertasNotificacionesUsuarioService#delete(java.lang.String, java.lang.Long, es.mira.progesin.persistence.entities.enums.TipoMensajeEnum)}.
     */
    @Test
    public final void testDelete() {
        Long id = 1L;
        TipoMensajeEnum tipo = TipoMensajeEnum.ALERTA;
        AlertasNotificacionesUsuario alertasNotificacionesUsuario = new AlertasNotificacionesUsuario();
        alertasNotificacionesUsuario.setIdMensaje(id);
        alertasNotificacionesUsuario.setTipo(tipo);
        alertasNotificacionesUsuario.setUsuario(USUARIOLOGUEADO);
        when(mensajeRepository.findByUsuarioAndTipoAndIdMensaje(USUARIOLOGUEADO, tipo, id))
                .thenReturn(alertasNotificacionesUsuario);
        
        alertasNotificacionesUsuarioServiceMock.delete(USUARIOLOGUEADO, id, tipo);
        
        verify(mensajeRepository, times(1)).findByUsuarioAndTipoAndIdMensaje(USUARIOLOGUEADO, tipo, id);
        verify(mensajeRepository, times(1)).delete(alertasNotificacionesUsuario);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertasNotificacionesUsuarioService#save(es.mira.progesin.persistence.entities.AlertasNotificacionesUsuario)}.
     */
    @Test
    public final void testSave() {
        Long id = 1L;
        TipoMensajeEnum tipo = TipoMensajeEnum.ALERTA;
        AlertasNotificacionesUsuario alertasNotificacionesUsuario = new AlertasNotificacionesUsuario();
        alertasNotificacionesUsuario.setIdMensaje(id);
        alertasNotificacionesUsuario.setTipo(tipo);
        alertasNotificacionesUsuario.setUsuario(USUARIOLOGUEADO);
        alertasNotificacionesUsuarioServiceMock.save(alertasNotificacionesUsuario);
        verify(mensajeRepository, times(1)).save(alertasNotificacionesUsuario);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertasNotificacionesUsuarioService#findAlertasByUser(java.lang.String)}.
     */
    @Test
    public final void testFindAlertasByUser() {
        AlertasNotificacionesUsuario mensaje1 = new AlertasNotificacionesUsuario();
        mensaje1.setIdMensaje(1L);
        mensaje1.setTipo(TipoMensajeEnum.ALERTA);
        mensaje1.setUsuario(USUARIOLOGUEADO);
        AlertasNotificacionesUsuario mensaje2 = new AlertasNotificacionesUsuario();
        mensaje2.setIdMensaje(2L);
        mensaje2.setTipo(TipoMensajeEnum.ALERTA);
        mensaje2.setUsuario(USUARIOLOGUEADO);
        List<AlertasNotificacionesUsuario> mensajesAlertaTest = new ArrayList<>();
        mensajesAlertaTest.add(mensaje1);
        mensajesAlertaTest.add(mensaje2);
        when(mensajeRepository.findByUsuarioAndTipo(USUARIOLOGUEADO, TipoMensajeEnum.ALERTA))
                .thenReturn(mensajesAlertaTest);
        List<Alerta> alertas = alertasNotificacionesUsuarioServiceMock.findAlertasByUser(USUARIOLOGUEADO);
        verify(mensajeRepository, times(1)).findByUsuarioAndTipo(USUARIOLOGUEADO, TipoMensajeEnum.ALERTA);
        verify(alertaService, times(1)).findOne(1L);
        verify(alertaService, times(1)).findOne(2L);
        assertThat(alertas).isNotNull();
        assertThat(alertas).hasSize(2);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertasNotificacionesUsuarioService#findNotificacionesByUser(java.lang.String)}.
     */
    @Test
    public final void testFindNotificacionesByUser() {
        AlertasNotificacionesUsuario mensaje1 = new AlertasNotificacionesUsuario();
        mensaje1.setIdMensaje(3L);
        mensaje1.setTipo(TipoMensajeEnum.NOTIFICACION);
        mensaje1.setUsuario(USUARIOLOGUEADO);
        AlertasNotificacionesUsuario mensaje2 = new AlertasNotificacionesUsuario();
        mensaje2.setIdMensaje(4L);
        mensaje2.setTipo(TipoMensajeEnum.NOTIFICACION);
        mensaje2.setUsuario(USUARIOLOGUEADO);
        List<AlertasNotificacionesUsuario> mensajesNotificacionesTest = new ArrayList<>();
        mensajesNotificacionesTest.add(mensaje1);
        mensajesNotificacionesTest.add(mensaje2);
        when(mensajeRepository.findByUsuarioAndTipo(USUARIOLOGUEADO, TipoMensajeEnum.NOTIFICACION))
                .thenReturn(mensajesNotificacionesTest);
        List<Notificacion> notificaciones = alertasNotificacionesUsuarioServiceMock
                .findNotificacionesByUser(USUARIOLOGUEADO);
        verify(mensajeRepository, times(1)).findByUsuarioAndTipo(USUARIOLOGUEADO, TipoMensajeEnum.NOTIFICACION);
        verify(notificacionService, times(1)).findOne(3L);
        verify(notificacionService, times(1)).findOne(4L);
        assertThat(notificaciones).isNotNull();
        assertThat(notificaciones).hasSize(2);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertasNotificacionesUsuarioService#grabarMensajeUsuario(java.lang.Object, java.lang.String)}.
     */
    @Test
    public final void testGrabarMensajeUsuario() {
        Alerta mensaje = new Alerta();
        mensaje.setIdAlerta(1L);
        mensaje.setDescripcion(DESCRIPCIONTEST);
        mensaje.setNombreSeccion(SECCIONTEST);
        alertasNotificacionesUsuarioServiceMock.grabarMensajeUsuario(mensaje, "ezentis");
        verify(mensajeRepository, times(1)).save(any(AlertasNotificacionesUsuario.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertasNotificacionesUsuarioService#grabarMensajeJefeEquipo(java.lang.Object, es.mira.progesin.persistence.entities.Inspeccion)}.
     */
    @Test
    public final void testGrabarMensajeJefeEquipo() {
        Alerta mensaje = new Alerta();
        mensaje.setIdAlerta(1L);
        mensaje.setDescripcion(DESCRIPCIONTEST);
        mensaje.setNombreSeccion(SECCIONTEST);
        Inspeccion inspeccion = new Inspeccion();
        Equipo equipo = new Equipo();
        User jefe = new User();
        jefe.setUsername("ezentisJefe");
        equipo.setJefeEquipo(jefe);
        inspeccion.setEquipo(equipo);
        alertasNotificacionesUsuarioServiceMock.grabarMensajeJefeEquipo(mensaje, inspeccion);
        verify(mensajeRepository, times(1)).save(any(AlertasNotificacionesUsuario.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertasNotificacionesUsuarioService#grabarMensajeRol(java.lang.Object, es.mira.progesin.persistence.entities.enums.RoleEnum)}.
     */
    @Test
    public final void testGrabarMensajeRolObjectRoleEnum() {
        Notificacion mensaje = new Notificacion();
        mensaje.setIdNotificacion(1L);
        mensaje.setDescripcion(DESCRIPCIONTEST);
        mensaje.setNombreSeccion(SECCIONTEST);
        RoleEnum rol = RoleEnum.ROLE_EQUIPO_INSPECCIONES;
        User user1 = new User();
        user1.setUsername("ezentis1");
        User user2 = new User();
        user2.setUsername("ezentis2");
        List<User> usuariosRol = new ArrayList<>();
        usuariosRol.add(user1);
        usuariosRol.add(user2);
        when(userService.findByfechaBajaIsNullAndRole(rol)).thenReturn(usuariosRol);
        alertasNotificacionesUsuarioServiceMock.grabarMensajeRol(mensaje, rol);
        verify(mensajeRepository, times(2)).save(any(AlertasNotificacionesUsuario.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertasNotificacionesUsuarioService#grabarMensajeRol(java.lang.Object, java.util.List)}.
     */
    @Test
    public final void testGrabarMensajeRolObjectListOfRoleEnum() {
        Notificacion mensaje = new Notificacion();
        mensaje.setIdNotificacion(1L);
        mensaje.setDescripcion(DESCRIPCIONTEST);
        mensaje.setNombreSeccion(SECCIONTEST);
        RoleEnum rol1 = RoleEnum.ROLE_EQUIPO_INSPECCIONES;
        RoleEnum rol2 = RoleEnum.ROLE_JEFE_INSPECCIONES;
        List<RoleEnum> roles = new ArrayList<>();
        roles.add(rol1);
        roles.add(rol2);
        User user1 = new User();
        user1.setUsername("ezentis1");
        User user2 = new User();
        user2.setUsername("ezentis2");
        List<User> usuariosRol1 = new ArrayList<>();
        usuariosRol1.add(user1);
        List<User> usuariosRol2 = new ArrayList<>();
        usuariosRol2.add(user2);
        
        when(userService.findByfechaBajaIsNullAndRole(rol1)).thenReturn(usuariosRol1);
        when(userService.findByfechaBajaIsNullAndRole(rol2)).thenReturn(usuariosRol2);
        
        alertasNotificacionesUsuarioServiceMock.grabarMensajeRol(mensaje, roles);
        
        verify(mensajeRepository, times(2)).save(any(AlertasNotificacionesUsuario.class));
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertasNotificacionesUsuarioService#findNotificaciones(java.util.List)}.
     */
    @Test
    public final void testFindNotificaciones() {
        List<AlertasNotificacionesUsuario> lista = new ArrayList<>();
        AlertasNotificacionesUsuario alertasNotificacionesUsuario = new AlertasNotificacionesUsuario();
        alertasNotificacionesUsuario.setIdMensaje(1L);
        lista.add(alertasNotificacionesUsuario);
        Notificacion notificacion = mock(Notificacion.class);
        when(notificacionService.findOne(1L)).thenReturn(notificacion);
        
        List<Notificacion> notificaciones = alertasNotificacionesUsuarioServiceMock.findNotificaciones(lista);
        verify(notificacionService, times(1)).findOne(1L);
        assertThat(notificaciones).hasSize(1);
        
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertasNotificacionesUsuarioService#findAlertas(java.util.List)}.
     */
    @Test
    public final void testFindAlertas() {
        List<AlertasNotificacionesUsuario> lista = new ArrayList<>();
        AlertasNotificacionesUsuario alertasNotificacionesUsuario = new AlertasNotificacionesUsuario();
        alertasNotificacionesUsuario.setIdMensaje(1L);
        lista.add(alertasNotificacionesUsuario);
        Alerta alerta = mock(Alerta.class);
        when(alertaService.findOne(1L)).thenReturn(alerta);
        
        List<Alerta> alertas = alertasNotificacionesUsuarioServiceMock.findAlertas(lista);
        verify(alertaService, times(1)).findOne(1L);
        assertThat(alertas).hasSize(1);
    }
    
    /**
     * Test method for
     * {@link es.mira.progesin.services.AlertasNotificacionesUsuarioService#grabarMensajeEquipo(java.lang.Object, es.mira.progesin.persistence.entities.Equipo)}.
     */
    @Test
    public final void testGrabarMensajeEquipoObjectEquipo() {
        Alerta mensaje = mock(Alerta.class);
        Equipo equipo = mock(Equipo.class);
        List<Miembro> miembros = new ArrayList<>();
        Miembro miembro = new Miembro();
        User usuario = new User();
        usuario.setUsername(USUARIOLOGUEADO);
        miembro.setUsuario(usuario);
        miembros.add(miembro);
        
        when(miembrosRepository.findByEquipo(equipo)).thenReturn(miembros);
        
        alertasNotificacionesUsuarioServiceMock.grabarMensajeEquipo(mensaje, equipo);
        verify(miembrosRepository, times(1)).findByEquipo(equipo);
        verify(mensajeRepository, times(1)).save(any(AlertasNotificacionesUsuario.class));
    }
    
}
