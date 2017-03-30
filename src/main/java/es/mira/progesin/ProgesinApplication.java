package es.mira.progesin;

import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.primefaces.util.Constants;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;

import es.mira.progesin.jsf.scope.FacesViewScope;

// SpringBootApplication Equivale a @Configuration @EnableAutoConfiguration @ComponentScan
@SpringBootApplication
@EnableScheduling
public class ProgesinApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ProgesinApplication.class, args);
    }
    
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }
    
    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.setScopes(Collections.<String, Object> singletonMap(FacesViewScope.NAME, new FacesViewScope()));
        return configurer;
    }
    
    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return new RegistroPaginasError();
    }
    
    static class RegistroPaginasError implements ErrorPageRegistrar {
        
        @Override
        public void registerErrorPages(ErrorPageRegistry registry) {
            registry.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "redirect:/index.xhtml"),
                    new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.xhtml"),
                    new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/5xx.xhtml"),
                    new ErrorPage(HttpStatus.BAD_GATEWAY, "/error/5xx.xhtml"));
        }
        
    }
    
    @Configuration
    @Profile("dev")
    static class ConfigureJSFContextParameters implements ServletContextInitializer {
        
        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
            servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", "true");
            servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
            servletContext.setInitParameter("facelets.DEVELOPMENT", "true");
            servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
            servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
            servletContext.setInitParameter("primefaces.THEME", "bluesky");
            servletContext.setInitParameter("encoding", "UTF-8");
            servletContext.setInitParameter(Constants.ContextParams.FONT_AWESOME, "true");
        }
    }
    
    @Configuration
    @Profile("production")
    static class ConfigureJSFContextParametersProd implements ServletContextInitializer {
        
        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            
            servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
            servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", "true");
            servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Production");
            servletContext.setInitParameter("facelets.DEVELOPMENT", "false");
            servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "-1");
            servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
            servletContext.setInitParameter("primefaces.THEME", "bluesky");
            servletContext.setInitParameter("encoding", "UTF-8");
            servletContext.setInitParameter(Constants.ContextParams.FONT_AWESOME, "true");
        }
    }
    
}
