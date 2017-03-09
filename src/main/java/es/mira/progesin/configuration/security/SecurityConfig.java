package es.mira.progesin.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Autowired
    private LoginService loginService;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean());
        auth.authenticationProvider(authenticationProvider());
        // auth.inMemoryAuthentication()
        // .withUser("user").password("password").roles("USER");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceBean());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.userDetailsService(userDetailsServiceBean());
        // http.headers().frameOptions().sameOrigin();
        http.csrf().disable().authorizeRequests().antMatchers("/css/**", "/images/**", "/javax.faces.resource/**")
                .permitAll().antMatchers("/login/**").anonymous().antMatchers("/acceso/**").anonymous()
                // .antMatchers("/user*").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.USER.name())
                .antMatchers("/user*").hasAnyRole().anyRequest().authenticated().and().formLogin().loginPage("/login")
                .loginProcessingUrl("/login").defaultSuccessUrl("/index.xhtml", true).failureUrl("/login").and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        
    }
    
    @Override
    public UserDetailsService userDetailsServiceBean() {
        return loginService;
    }
    
}
