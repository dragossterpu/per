package es.mira.progesin.configuration.web;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configurable
public class AppConfig {
	@Bean
	public JavaMailSenderImpl javaMailSenderImpl() {
		String certificatesTrustStorePath = "<JAVA HOME>/jre/lib/security/cacerts";
		System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		// Set gmail email id
		mailSender.setUsername("progesinipss@gmail.com");
		// Set gmail email password
		mailSender.setPassword("ipss2016");
		Properties prop = mailSender.getJavaMailProperties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		prop.put("mail.smtp.starttls.enable", true);
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");

		return mailSender;
	}
}
