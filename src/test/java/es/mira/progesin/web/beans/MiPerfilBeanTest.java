/**
 * 
 */
package es.mira.progesin.web.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import javax.faces.application.FacesMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.util.FacesUtilities;

/**
 * Test para MiPerfilBean.
 * @author EZENTIS
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.security.*")
@PrepareForTest({ FacesUtilities.class, SecurityContextHolder.class })
public class MiPerfilBeanTest {
    
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
     * Mock Servicio de usuarios.
     */
    @Mock
    private IUserService userService;
    
    /**
     * Mock Servicio de usuarios.
     */
    @Captor
    private ArgumentCaptor<User> userCaptor;
    
    /**
     * Simulación de MiPerfilBean.
     */
    @InjectMocks
    private MiPerfilBean miPerfilBeanMock;
    
    /**
     * Configuración inicial del test.
     */
    @Before
    public void setUp() {
        PowerMockito.mockStatic(FacesUtilities.class);
        PowerMockito.mockStatic(SecurityContextHolder.class);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("usuarioLogueado");
    }
    
    /**
     * Comprobación clase existe.
     */
    @Test
    public void type() {
        assertThat(MiPerfilBean.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta.
     */
    @Test
    public void instantiation() {
        MiPerfilBean target = new MiPerfilBean();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.MiPerfilBean#cambiarClave()}.
     */
    @Test
    public final void testCambiarClave() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passNueva = "2D";
        String passActual = "abc";
        String passActualCodificada = passwordEncoder.encode(passActual);
        miPerfilBeanMock.setClaveNueva(passNueva);
        miPerfilBeanMock.setClaveConfirm(passNueva);
        miPerfilBeanMock.setClaveActual(passActual);
        
        User userActual = new User();
        userActual.setPassword(passActualCodificada);
        miPerfilBeanMock.setUser(userActual);
        
        miPerfilBeanMock.cambiarClave();
        
        verify(userService, timeout(1)).save(userCaptor.capture());
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeConfirmacionDialog(eq(FacesMessage.SEVERITY_INFO), any(String.class),
                any(String.class), any(String.class));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.MiPerfilBean#cambiarClave()}.
     */
    @Test
    public final void testCambiarClaveDiferentesClaves() {
        String passNueva = "2D";
        String passConfirm = "1D";
        miPerfilBeanMock.setClaveNueva(passNueva);
        miPerfilBeanMock.setClaveConfirm(passConfirm);
        
        miPerfilBeanMock.cambiarClave();
        
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.MiPerfilBean#cambiarClave()}.
     */
    @Test
    public final void testCambiarClaveActualNovalida() {
        String passNueva = "2D";
        String passActual = "abc";
        String passActualCodificada = "abahsgdgdkd";
        miPerfilBeanMock.setClaveNueva(passNueva);
        miPerfilBeanMock.setClaveConfirm(passNueva);
        miPerfilBeanMock.setClaveActual(passActual);
        
        User userActual = new User();
        userActual.setPassword(passActualCodificada);
        miPerfilBeanMock.setUser(userActual);
        
        miPerfilBeanMock.cambiarClave();
        
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
    }
    
    /**
     * Test method for {@link es.mira.progesin.web.beans.MiPerfilBean#cambiarClave()}.
     */
    @Test
    public final void testCambiarClaveNoCoincideConPatron() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passNueva = "2ddd";
        String passActual = "abc";
        String passActualCodificada = passwordEncoder.encode(passActual);
        miPerfilBeanMock.setClaveNueva(passNueva);
        miPerfilBeanMock.setClaveConfirm(passNueva);
        miPerfilBeanMock.setClaveActual(passActual);
        
        User userActual = new User();
        userActual.setPassword(passActualCodificada);
        miPerfilBeanMock.setUser(userActual);
        
        miPerfilBeanMock.cambiarClave();
        
        verifyStatic(FacesUtilities.class, times(1));
        FacesUtilities.setMensajeInformativo(eq(FacesMessage.SEVERITY_ERROR), any(String.class), any(String.class),
                eq(null));
    }
    
}
