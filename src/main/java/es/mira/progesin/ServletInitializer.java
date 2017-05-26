package es.mira.progesin;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Configuración del SerlvetInitializer.
 * 
 * @author EZENTIS
 *
 */
public class ServletInitializer extends SpringBootServletInitializer {
    
    /**
     * Inidicamos la clase que contiene el método main para que arranque Spring Boot.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ProgesinApplication.class);
    }
    
}
