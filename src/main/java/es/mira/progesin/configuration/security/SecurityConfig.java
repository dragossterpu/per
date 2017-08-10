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
import es.mira.progesin.persistence.entities.enums.RoleEnum;
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
    
    /**
     * Número máximo de usuarios que pueden iniciar sessión con el mismo nombre de usuario.
     */
    private static final int MAXCONCURRENTUSERSESSIONS = 1;
    
    /**
     * Implementación de UserDetailsService que necesita Spring Security.
     */
    @Autowired
    private LoginService loginService;
    
    /**
     * Implementación de lo que tiene que hacer Spring Security cuando un usuario se loguee con éxito. Usado para la
     * auditoría de acceso a la aplicación.
     */
    @Autowired
    private AuthenticationSuccessHandlerPersonalizado authenticationSuccessHandlerPersonalizado;
    
    /**
     * Configuramos el UserDetailsService y el PasswordEncoder que vamos a usar.
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
    }
    
    /**
     * Configuración de la codificación de la contraseña usando BCrypt.
     * 
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Configuración de la seguridad para no permitir el acceso a páginas por usuarios que no tengan los permisos
     * adecuados. Defininicón de la redirección de la apliación al iniciar y cerrar sesión en la aplicación.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO falta limitar el acceso por url a todas las rutas a las que no pueden acceder todos los usuarios, por ejemplo, los provisionales
        http.csrf().disable().authorizeRequests().antMatchers("/css/**", "/images/**", "/javax.faces.resource/**")
                .permitAll().antMatchers(Constantes.RUTALOGIN + "/**").anonymous().antMatchers("/acceso/**").anonymous()
                // Acceso a la administración sólo para el role ADMIN
                .antMatchers("/administracion/**", "/users/altaUsuario.xhtml").hasRole(RoleEnum.ROLE_ADMIN.getNombre())
                // Acceso a ciertas partes sólo el ADMIN y JEFE_INSPECCIONES
                .antMatchers("/equipos/altaEquipo.xhtml", "/inspecciones/modeloInspeccion/**")
                .hasAnyRole(RoleEnum.ROLE_ADMIN.getNombre(), RoleEnum.ROLE_JEFE_INSPECCIONES.getNombre())
                // Al resto pueden acceder todos los usuarios autenticados
                .anyRequest().authenticated().and().formLogin().loginPage(Constantes.RUTALOGIN).permitAll()
                .successHandler(authenticationSuccessHandlerPersonalizado).failureUrl(Constantes.RUTALOGIN);
        
        http.logout().logoutUrl(Constantes.RUTALOGOUT).logoutSuccessUrl(Constantes.RUTALOGIN);
        
        // configuración para el manejo de las sessiones de los usuarios
        http.sessionManagement().maximumSessions(MAXCONCURRENTUSERSESSIONS).maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry());
        
    }
    
    /**
     * Usado por spring security para saber los usuarios (Principal) que han iniciado sesión.
     * 
     * @return SessionRegistry
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
    /**
     * Trigger usado cuando un usuario cancela su sesión. Es necesario tenerlo definido para poder validar las sesiones
     * que tiene abiertas un usuario.
     * 
     * @return HttpSessionEventPublisher
     */
    @Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
    
}
