package es.mira.progesin;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.faces.application.ProjectStage;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

import org.apache.catalina.Context;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.primefaces.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.NonEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sun.faces.config.FacesInitializer;

import es.mira.progesin.jsf.scope.FacesViewScope;
import es.mira.progesin.persistence.entities.CuerpoEstado;
import es.mira.progesin.persistence.entities.PuestoTrabajo;
import es.mira.progesin.persistence.entities.User;
import es.mira.progesin.persistence.entities.enums.EstadoEnum;
import es.mira.progesin.persistence.entities.enums.RoleEnum;
import es.mira.progesin.persistence.repositories.IUserRepository;
import es.mira.progesin.services.IUserService;
import es.mira.progesin.services.UserService;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ProgesinApplication extends SpringBootServletInitializer implements CommandLineRunner{
    
	@Autowired
	IUserRepository repository;
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ProgesinApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ProgesinApplication.class);
    }
    
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }
    
	@Bean
	public static CustomScopeConfigurer customScopeConfigurer() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.setScopes(Collections.<String, Object>singletonMap(
                FacesViewScope.NAME, new FacesViewScope()));
		return configurer;
	}
	
	@Bean
	public ServletContextInitializer servletContextCustomizer() {
	    return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext sc) throws ServletException {
                sc.setInitParameter(Constants.ContextParams.THEME, "bootstrap");
                sc.setInitParameter(Constants.ContextParams.FONT_AWESOME, "true");
                sc.setInitParameter(ProjectStage.PROJECT_STAGE_PARAM_NAME, ProjectStage.Development.name());
//                sc.setInitParameter(ProjectStage.PROJECT_STAGE_PARAM_NAME, ProjectStage.Production.name());
            }
	    };
	}
    
	/**
	 * This bean is only needed when running with embedded Tomcat.
	 */
    @Bean
    @ConditionalOnMissingBean(NonEmbeddedServletContainerFactory.class)
    public EmbeddedServletContainerFactory embeddedServletContainerFactory() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        
        tomcat.addContextCustomizers(new TomcatContextCustomizer() {
            @Override
            public void customize(Context context) {
                // register FacesInitializer
                context.addServletContainerInitializer(new FacesInitializer(),
                        getServletContainerInitializerHandlesTypes(FacesInitializer.class));
                
                // add configuration from web.xml
//                context.addWelcomeFile("index.jsf");
                context.addWelcomeFile("index.xhtml");
                
                // register additional mime-types that Spring Boot doesn't register
                context.addMimeMapping("eot", "application/vnd.ms-fontobject");
                context.addMimeMapping("ttf", "application/x-font-ttf");
                context.addMimeMapping("woff", "application/x-font-woff");
                context.addMimeMapping("woff2", "application/fontawesome-webfont.woff2");
            }
        });
        
        return tomcat;
    }
    
    @SuppressWarnings("rawtypes")
    private Set<Class<?>> getServletContainerInitializerHandlesTypes(Class<? extends ServletContainerInitializer> sciClass) {
        HandlesTypes annotation = sciClass.getAnnotation(HandlesTypes.class);
        if (annotation == null) {
            return Collections.emptySet();
        }
        
        Class[] classesArray = annotation.value();
        Set<Class<?>> classesSet = new HashSet<Class<?>>(classesArray.length);
        for (Class clazz: classesArray) {
            classesSet.add(clazz);
        }
        
        return classesSet;
    }

	@Override
	public void run(String... arg0) throws Exception {
//		User user = new User();
//		user.setUsername("pepe");
//		user.setPassword("pepe");
//		user.setEstado(EstadoEnum.ACTIVO);
//		user.setNombre("Nombre");
//		user.setApellido1("apellido1");
//		user.setDocIndentidad("111111111");
//		user.setCorreo("correo@correo.es");
//		user.setRole(RoleEnum.ADMIN);
//		user.setNumIdentificacion("555555555");
//		user.setEnvioNotificacion("SI");
//		CuerpoEstado cuerpoEstado = new CuerpoEstado();
//		cuerpoEstado.setId(1);
//		user.setCuerpoEstado(cuerpoEstado);
//		PuestoTrabajo puestoTrabajo = new PuestoTrabajo();
//		puestoTrabajo.setId(2);
//		user.setPuestoTrabajo(puestoTrabajo);
//		user.setNivel(20);
//		user.setFechaDestinoIPSS(new Date());
//		user.setFechaAlta(new Date());
//		user.setUsernameAlta("userAlta");
//		IUserService userService = new UserService();
//		//userService.save(user);
//		System.out.println(repository.findAll());
//		repository.save(user);
	}
	
}
