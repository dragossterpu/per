package es.mira.progesin.configuration.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Configuración de la resolución de rutas relativas a la página de login.
 * 
 * @author EZENTIS
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    
    /**
     * Configuración de resolución de ruta de acceso a la vista /acceso/login.xhtml al usar acciones /login.
     * @return resolver
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/acceso/");
        resolver.setSuffix(".xhtml");
        return resolver;
    }
    
}
