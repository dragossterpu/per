package es.mira.progesin;

import com.sun.faces.config.ConfigureListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;


@Configuration
public class ServletInitializer implements ServletContextAware{
	// extends SpringBootServletInitializer {

	@Bean
	public ServletRegistrationBean facesServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new FacesServlet(), "*.xhtml");
		registration.setLoadOnStartup(1);
		return registration;
	}

	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
		return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
	}


	@Override
	public void setServletContext(ServletContext sc) {
		sc.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
		sc.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
		sc.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
		sc.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", Boolean.TRUE.toString());
		sc.setInitParameter("facelets.DEVELOPMENT", Boolean.TRUE.toString());
		sc.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
//		//sc.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
		sc.setInitParameter("primefaces.THEME", "none"); //estilos de los componentes
		
	}

}
