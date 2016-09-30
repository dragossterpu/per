package es.mira.progesin.configuration.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import es.mira.progesin.persistence.auditor.UsernameSecurityAuditorAwareImpl;

@Configuration
@EnableJpaAuditing
public class AuditableJpaConfig {

	@Bean
	public AuditorAware<String> auditorProvider() {
	   return new UsernameSecurityAuditorAwareImpl();
	}
}
