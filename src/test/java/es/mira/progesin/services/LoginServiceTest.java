
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import es.mira.progesin.persistence.entities.User;

/**
 * Test del servicio de login.
 * 
 * @author Ezentis
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
    
    /**
     * Username de prueba.
     */
    private final static String EZENTIS = "ezentis";
    
    /**
     * Simulación del servicio de usuarios.
     */
    @Mock
    private IUserService userService;
    
    /**
     * Servicio de login.
     */
    @InjectMocks
    private LoginService loginService;
    
    /**
     * Comprobación de que la clase existe.
     */
    @Test
    public void type() {
        assertThat(LoginService.class).isNotNull();
    }
    
    /**
     * Comprobación de que la clase no es abstracta.
     */
    @Test
    public void instantiation() {
        LoginService target = new LoginService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.LoginService#loadUserByUsername(java.lang.String)}.
     */
    @Test
    public void loadUserByUsername_valido() {
        
        User usuario = User.builder().username(EZENTIS).build();
        when(userService.findByUsernameIgnoreCase(EZENTIS)).thenReturn(usuario);
        
        UserDetails logueado = loginService.loadUserByUsername(EZENTIS);
        
        assertThat(logueado.getUsername()).isEqualTo(EZENTIS);
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.LoginService#loadUserByUsername(java.lang.String)}.
     */
    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_novalido() {
        
        when(userService.findByUsernameIgnoreCase("novalido")).thenReturn(null);
        
        loginService.loadUserByUsername("novalido");
    }
    
}
