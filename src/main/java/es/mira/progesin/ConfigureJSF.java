package es.mira.progesin;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.faces.config.FacesInitializer;

/**
 * Configuración necesaria para la utilización de JSF junto con Spring.
 * 
 * @author EZENTIS
 *
 */
@Configuration
public class ConfigureJSF {
    
    /**
     * @return JsfServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean facesServletRegistration() {
        return new JsfServletRegistrationBean();
    }
    
    /**
     * Clase que hereda de org.springframework.boot.web.servlet.ServletRegistrationBean para poder sobreescribir el
     * método onStartup y definir el FacesInitializer de JSF.
     * 
     * @author EZENTIS
     *
     */
    public class JsfServletRegistrationBean extends ServletRegistrationBean {
        
        /**
         * Constructor.
         */
        public JsfServletRegistrationBean() {
            super();
        }
        
        /**
         * Configuración al arrancar el Servlet.
         */
        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            
            FacesInitializer facesInitializer = new FacesInitializer();
            
            Set<Class<?>> clazz = new HashSet<>();
            clazz.add(ConfigureJSF.class);
            facesInitializer.onStartup(clazz, servletContext);
        }
    }
}