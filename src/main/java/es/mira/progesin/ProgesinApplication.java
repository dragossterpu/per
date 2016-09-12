package es.mira.progesin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import es.mira.progesin.persistence.repositories.IUserRepository;

@SpringBootApplication
@EnableAutoConfiguration
		(exclude = {
		org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class
		,es.mira.progesin.configuration.security.SecurityConfig.class
		//deshabilita la petición de user y password por defecto de login.
		//deshabilita la petición de user y password por defecto de login.
})
@EnableJpaRepositories("es.mira.progesin.persistence.repositories")
public class ProgesinApplication implements CommandLineRunner {
	@Autowired
	IUserRepository repository;

	@Override
	public final void run(String... args) throws Exception {
		System.out.println(repository.findAll());
	}

	public static void main(String[] args) {
		SpringApplication.run(ProgesinApplication.class, args);
	}
}
