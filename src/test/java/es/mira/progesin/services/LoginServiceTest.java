/**
 * 
 */
package es.mira.progesin.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.SeccionesEnum;
import es.mira.progesin.persistence.entities.enums.TipoRegistroEnum;

/**
 * @author Ezentis
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
    
    @Mock
    private IUserService userService;
    
    @Mock
    private IRegistroActividadService registroActividadService;
    
    @InjectMocks
    private LoginService loginService;
    
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
        LoginService target = new LoginService();
        assertThat(target).isNotNull();
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.LoginService#loadUserByUsername(java.lang.String)}.
     */
    @Test
    public void loadUserByUsername_valido() {
        
        User usuario = User.builder().username("ezentis").build();
        when(userService.findByUsernameIgnoreCase("ezentis")).thenReturn(usuario);
        
        UserDetails logueado = loginService.loadUserByUsername("ezentis");
        
        verify(registroActividadService, times(1)).altaRegActividad(any(String.class),
                eq(TipoRegistroEnum.AUDITORIA.name()), eq(SeccionesEnum.LOGIN.name()));
        assertThat(logueado.getUsername()).isEqualTo("ezentis");
        
    }
    
    /**
     * Test method for {@link es.mira.progesin.services.LoginService#loadUserByUsername(java.lang.String)}.
     */
    @Test
    public void loadUserByUsername_novalido() {
        
        when(userService.findByUsernameIgnoreCase("novalido")).thenReturn(null);
        
        assertThatThrownBy(() -> loginService.loadUserByUsername("novalido"))
                .isInstanceOf(UsernameNotFoundException.class);
        
    }
    
}
