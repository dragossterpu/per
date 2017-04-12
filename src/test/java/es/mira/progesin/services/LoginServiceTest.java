/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import es.mira.progesin.persistence.entities.User;

/**
 * @author Ezentis
 *
 */
public class LoginServiceTest {
    
    private IUserService userService;
    
    private IRegistroActividadService registroActividadService;
    
    private LoginService loginService;
    
    /**
     * Configurar test
     */
    @Before
    public void setUp() {
        userService = Mockito.mock(IUserService.class);
        registroActividadService = Mockito.mock(IRegistroActividadService.class);
        loginService = new LoginService(userService, registroActividadService);
    }
    
    /**
     * Comprobación clase existe
     */
    @Test
    public void type() {
        assertThat(LoginService.class).isNotNull();
    }
    
    /**
     * Comprobación clase no abstracta
     */
    @Test
    public void instantiation() {
        LoginService target = new LoginService(null, null);
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.LoginService#loadUserByUsername(java.lang.String)}.
     */
    @Test
    public void loadUserByUsername() {
        
        User usuario = User.builder().username("ezentis").build();
        when(userService.findByUsernameIgnoreCase("ezentis")).thenReturn(usuario);
        when(userService.findByUsernameIgnoreCase("novalido")).thenReturn(null);
        
        UserDetails actual = loginService.loadUserByUsername("ezentis");
        verify(registroActividadService, times(1))
                .altaRegActividad("El usuario ezentis ha iniciado sesión en la aplicación", "AUDITORIA", "LOGIN");
        assertThat(actual).isNotNull();
        assertThat(actual.getUsername()).isEqualTo("ezentis");
        
        assertThatThrownBy(() -> loginService.loadUserByUsername("novalido"))
                .isInstanceOf(UsernameNotFoundException.class);
        
    }
    
}
