package es.mira.progesin.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import es.mira.progesin.constantes.Constantes;
import es.mira.progesin.services.LoginService;

/**
 * Controlador de las operaciones relacionadas con el login del usuario. Login, invalid login, recuperar contraseña,
 * envío de correo, acceso y logout.
 * 
 * @author EZENTIS
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private static final int MAX_CONCURRENT_USER_SESSIONS = 1;
    
    @Autowired
    private LoginService loginService;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
    }
    
    /**
     * Configuración de la codificación de la contraseña usando BCrypt
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.csrf().disable().authorizeRequests().antMatchers("/css/**", "/images/**", "/javax.faces.resource/**")
                .permitAll().antMatchers(Constantes.RUTA_LOGIN + "/**").anonymous().antMatchers("/acceso/**")
                .anonymous()
                // .antMatchers("/user*").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.USER.name())
                .antMatchers("/user*").hasAnyRole().anyRequest().authenticated();
        
        http.formLogin().loginPage(Constantes.RUTA_LOGIN).loginProcessingUrl(Constantes.RUTA_LOGIN)
                .defaultSuccessUrl("/index.xhtml", true).failureUrl(Constantes.RUTA_LOGIN);
        
        http.logout().logoutUrl(Constantes.RUTA_LOGOUT).logoutSuccessUrl(Constantes.RUTA_LOGIN);
        
        // configuración para el manejo de las sessiones de los usuarios
        http.sessionManagement().maximumSessions(MAX_CONCURRENT_USER_SESSIONS).maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry());
        
    }
    
    /**
     * Usado por spring security para saber los usuarios (Principal) que han iniciado sesión
     * 
     * @return SessionRegistry
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
    /**
     * Trigger usado cuando un usuario cancela su sesión. Es necesario tenerlo definido para poder validar las sesiones
     * que tiene abiertas un usuario
     * 
     * @return HttpSessionEventPublisher
     */
    @Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
    
}
