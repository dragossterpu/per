package es.mira.progesin.configuration.security;

import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginService loginService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean());
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsServiceBean());
       // http.headers().frameOptions().sameOrigin();
        http.csrf().disable()
	        .authorizeRequests()
            .antMatchers("/css/**", "/images/**", "/javax.faces.resource/**").permitAll()
            .antMatchers("/login/**").anonymous()
            .antMatchers("/acceso/**").anonymous()
	        .antMatchers("/user*").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.USER.name())
	        .anyRequest().authenticated()
	        .and()
	        .formLogin()
	        .loginPage("/login")
	        .loginProcessingUrl("/login")
	        .defaultSuccessUrl("/index.xhtml")
	        .failureUrl("/login")
	        .and()
	        .logout().logoutUrl("/login/logout").logoutSuccessUrl("/login");

    }

    @Override
    public UserDetailsService userDetailsServiceBean() {
        return loginService;
    }

}

