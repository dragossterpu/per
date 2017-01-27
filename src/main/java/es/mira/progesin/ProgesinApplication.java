package es.mira.progesin;

import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import es.mira.progesin.jsf.scope.FacesViewScope;

// SpringBootApplication Equivale a @Configuration @EnableAutoConfiguration @ComponentScan
@SpringBootApplication
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

		}
	}

}
