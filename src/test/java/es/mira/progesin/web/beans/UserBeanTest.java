package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import org.primefaces.event.ToggleEvent;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.exceptions.CorreoException;
import es.mira.progesin.lazydata.LazyModelUsuarios;
import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.Empleo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;
import es.mira.progesin.persistence.repositories.IEmpleoRepository;
import es.mira.progesin.services.IRegistroActividadService;
import es.mira.progesin.services.UserService;
import es.mira.progesin.util.FacesUtilities;
import es.mira.progesin.util.ICorreoElectronico;
import es.mira.progesin.util.Utilities;

/**
 * @author EZENTIS
 * 
 * Test del contoller UserBean
 */
@RunWith(PowerMockRunner.class)
// Evita conflictos con clases del sistema al enlazar los mocks por tipo
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class, Utilities.class })
public class UserBeanTest {
    
    /**
     * Mock del security context de spring.
     */
    @Mock
    private SecurityContext securityContext;
    
    /**
     * Mock del Authentication de spring.
     */
    @Mock
    private Authentication authentication;
    
    /**
     * Mock del servicio de usuarios.
     */
    @Mock
    private UserService userService;
    
    /**
     * Mock del password encoder.
     */
    @Mock
    private PasswordEncoder passwordEncoderMock;
    
    /**
     * Mock del correo electrónico.
     */
    @Mock
    private ICorreoElectronico correoElectronico;
    
    /**
     * Mock del registro de actividad.
     */
    @Mock
    private IRegistroActividadService regActividadService;
    
    /**
     * Mock del repositorio de empleos.
     */
    @Mock
    private IEmpleoRepository empleoRepository;
    
    /**
     * Clase a probar donde se inyectan los mocks.
     */
    @InjectMocks
    private UserBean userBean;
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("ezentis");
        
    }
    
    /**
     * Comprobación de que la clase existe.
     */
    @Test
    public void type() {
        assertThat(UserBean.class).isNotNull();
    }
    
    /**
     * Comprobación de clase no abstracta.
     */
    @Test
    public void instantiation() {
        UserBean target = new UserBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#getUserPerfil()}.
     */
    @Test
    public void getUserPerfil() {
        userBean.getUserPerfil();
        verify(userService, times(1)).findOne(any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#nuevoUsuario()}.
     */
    @Test
    public void nuevoUsuario() {
        userBean.nuevoUsuario();
        
        assertThat(userBean.getPuestoTrabajoSeleccionado()).isNull();
        assertThat(userBean.getCuerpoEstadoSeleccionado()).isNull();
        assertThat(userBean.getEmpleoSeleccionado()).isNull();
        assertThat(userBean.getDepartamentoSeleccionado()).isNull();
        assertThat(userBean.getUser().getFechaAlta()).isNotNull();
        assertThat(userBean.getUser().getEstado()).isEqualTo(EstadoEnum.ACTIVO);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#altaUsuario()}.
     */
    @Test
    public void altaUsuario_existeUsuario() {
        User user = User.builder().username("username").build();
        userBean.setUser(user);
        when(userService.exists("username")).thenReturn(Boolean.TRUE);
        userBean.altaUsuario();
        
        // Comprobamos que se muestra el mensaje en pantalla
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq("username"));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#altaUsuario()}.
     */
    @Test
    public void altaUsuario_noExisteUsuario() {
        // Preparación para la llamada al método
        User user = User.builder().username("name").correo("correo").build();
        userBean.setUser(user);
        when(userService.exists("name")).thenReturn(Boolean.FALSE);
        when(passwordEncoderMock.encode("password")).thenReturn("encodedpassword");
        
        userBean.altaUsuario();
        
        verify(userService, times(1)).save(user);
        verify(correoElectronico, times(1)).envioCorreo(eq("correo"), any(String.class), any(String.class));
        
        // Comprobamos que se muestra el mensaje en pantalla
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), eq("Alta"), any(String.class));
        
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.ALTA.name()),
                eq(SeccionesEnum.USUARIOS.name()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#altaUsuario()}.
     */
    @Test
    public void altaUsuario_excepcionBBDD() {
        // Preparación para la llamada al método
        User user = User.builder().username("user_name").correo("correo_destinatario").build();
        userBean.setUser(user);
        when(userService.exists("user_name")).thenReturn(Boolean.FALSE);
        when(passwordEncoderMock.encode("pass")).thenReturn("encodedpass");
        
        when(userService.save(user)).thenThrow(TransientDataAccessResourceException.class);
        
        userBean.altaUsuario();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq("Alta"), any(String.class));
        
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.USUARIOS.name()),
                any(Exception.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#altaUsuario()}.
     */
    @Test
    public void altaUsuario_excepcionCorreo() {
        // Preparación para la llamada al método
        User user = User.builder().username("pepe").correo("correoDestinatario").build();
        userBean.setUser(user);
        when(userService.exists("pepe")).thenReturn(Boolean.FALSE);
        when(passwordEncoderMock.encode("clave")).thenReturn("encodedClave");
        
        when(userService.save(user)).thenThrow(CorreoException.class);
        
        userBean.altaUsuario();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq(Constantes.ERRORMENSAJE),
                any(String.class));
        
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.USUARIOS.name()),
                any(CorreoException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#getFormularioBusquedaUsuarios()}.
     */
    @Test
    public void getFormularioBusquedaUsuarios() {
        UserBusqueda userBusqueda = mock(UserBusqueda.class);
        LazyModelUsuarios model = mock(LazyModelUsuarios.class);
        
        userBean.setUserBusqueda(userBusqueda);
        userBean.setModel(model);
        
        String ruta = userBean.getFormularioBusquedaUsuarios();
        
        assertThat(ruta).isEqualTo("/users/usuarios.xhtml?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#limpiarBusqueda()}.
     */
    @Test
    public void limpiarBusqueda() {
        userBean.setModel(mock(LazyModelUsuarios.class));
        userBean.limpiarBusqueda();
        
        assertThat(userBean.getUserBusqueda()).isNotNull();
        assertThat(userBean.getModel().getRowCount()).isEqualTo(0);
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#getFormularioBusquedaUsuarios()}.
     */
    @Test
    public void buscarUsuario() {
        UserBusqueda userBusqueda = new UserBusqueda();
        LazyModelUsuarios model = new LazyModelUsuarios(userService);
        
        userBean.setUserBusqueda(userBusqueda);
        userBean.setModel(model);
        
        userBean.buscarUsuario();
        
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.AUDITORIA.name()),
                eq(SeccionesEnum.USUARIOS.name()));
        assertThat(userBean.getEstadoUsuario()).isNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#eliminarUsuario(User)}.
     */
    @Test
    public void eliminarUsuario() {
        User usuario = User.builder().username("ezentis").build();
        userBean.eliminarUsuario(usuario);
        
        verify(userService, times(1)).save(usuario);
        assertThat(usuario.getFechaBaja()).isNotNull();
        verify(regActividadService, times(1)).altaRegActividad(any(String.class), eq(TipoRegistroEnum.BAJA.name()),
                eq(SeccionesEnum.USUARIOS.name()));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#eliminarUsuario(User)}.
     */
    @Test
    public void eliminarUsuario_excepcion() {
        User usuario = mock(User.class);
        when(userService.save(usuario)).thenThrow(JpaSystemException.class);
        
        userBean.eliminarUsuario(usuario);
        
        verify(regActividadService, times(1)).altaRegActividadError(eq(SeccionesEnum.USUARIOS.name()),
                any(DataAccessException.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#getFormModificarUsuario(User)}.
     */
    @Test
    public void getFormModificarUsuario_existe() {
        User user = User.builder().estado(EstadoEnum.ACTIVO).build();
        @SuppressWarnings("unchecked")
        List<Empleo> listaEmpleos = mock(List.class);
        when(empleoRepository.findByCuerpoOrderByDescripcionAsc(any(CuerpoEstado.class))).thenReturn(listaEmpleos);
        when(userService.findOne(user.getUsername())).thenReturn(user);
        String formulario = userBean.getFormModificarUsuario(user);
        
        assertThat(formulario).isEqualTo("/users/modificarUsuario?faces-redirect=true");
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#getFormModificarUsuario(User)}.
     */
    @Test
    public void getFormModificarUsuario_noExiste() {
        User user = User.builder().estado(EstadoEnum.ACTIVO).build();
        @SuppressWarnings("unchecked")
        List<Empleo> listaEmpleos = mock(List.class);
        when(empleoRepository.findByCuerpoOrderByDescripcionAsc(any(CuerpoEstado.class))).thenReturn(listaEmpleos);
        when(userService.findOne("test")).thenReturn(null);
        String formulario = userBean.getFormModificarUsuario(user);
        
        assertThat(formulario).isNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#modificarUsuario()}.
     */
    @Test
    public void modificarUsuario() {
        userBean.setEstadoUsuario("ACTIVO");
        User user = User.builder().estado(EstadoEnum.INACTIVO).build();
        userBean.setUser(user);
        
        userBean.modificarUsuario();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
        
        verify(regActividadService, times(1)).altaRegActividad(contains("Modificación del estado"),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.USUARIOS.name()));
        
        verify(regActividadService, times(1)).altaRegActividad(contains("Modificación del usuario"),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.USUARIOS.name()));
        
        assertThat(userBean.getUser().getFechaInactivo()).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#modificarUsuario()}.
     */
    @Test
    public void modificarUsuario_activar() {
        userBean.setEstadoUsuario("INACTIVO");
        User user = User.builder().estado(EstadoEnum.ACTIVO).build();
        userBean.setUser(user);
        
        userBean.modificarUsuario();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class));
        
        verify(regActividadService, times(1)).altaRegActividad(contains("Modificación del estado"),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.USUARIOS.name()));
        
        verify(regActividadService, times(1)).altaRegActividad(contains("Modificación del usuario"),
                eq(TipoRegistroEnum.MODIFICACION.name()), eq(SeccionesEnum.USUARIOS.name()));
        
        assertThat(userBean.getUser().getFechaInactivo()).isNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#restaurarClave()}.
     */
    @Test
    public void restaurarClave() {
        User user = User.builder().correo("correo@correo.es").build();
        userBean.setUser(user);
        when(passwordEncoderMock.encode("password")).thenReturn("encodedpassword");
        
        userBean.restaurarClave();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), eq("Clave"), any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#restaurarClave()}.
     */
    @Test
    public void restaurarClave_correoException() {
        User user = User.builder().correo("correo@correo.es").build();
        userBean.setUser(user);
        when(passwordEncoderMock.encode("password")).thenReturn("encodedpassword");
        doThrow(CorreoException.class).when(correoElectronico).envioCorreo(any(String.class), any(String.class),
                any(String.class));
        
        userBean.restaurarClave();
        
        PowerMockito.verifyStatic(times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_ERROR), eq("Clave"), any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#onToggle(ToggleEvent)}.
     */
    @Test
    public void onToggle() {
        ToggleEvent eventMock = mock(ToggleEvent.class);
        when(eventMock.getData()).thenReturn(0);
        List<Boolean> listaToogle = new ArrayList<>();
        listaToogle.add(Boolean.FALSE);
        userBean.setList(listaToogle);
        
        userBean.onToggle(eventMock);
        
        assertThat(listaToogle.get(0)).isFalse();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#init()}.
     */
    @Test
    public void init() {
        
        userBean.init();
        
        assertThat(userBean.getPuestoTrabajoSeleccionado()).isNull();
        assertThat(userBean.getCuerpoEstadoSeleccionado()).isNull();
        assertThat(userBean.getEmpleoSeleccionado()).isNull();
        assertThat(userBean.getDepartamentoSeleccionado()).isNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.UserBean#buscarEmpleo()}.
     */
    @Test
    public void buscarEmpleo() {
        CuerpoEstado cuerpo = CuerpoEstado.builder().id(1).build();
        userBean.setCuerpoEstadoSeleccionado(CuerpoEstado.builder().id(1).build());
        
        userBean.buscarEmpleo();
        
        verify(empleoRepository, times(1)).findByCuerpoOrderByDescripcionAsc(cuerpo);
    }
    
}
