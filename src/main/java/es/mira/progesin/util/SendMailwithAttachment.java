package es.mira.progesin.util;

import java.io.File;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import es.mira.progesin.configuration.web.AppConfig;

public class SendMailwithAttachment {
	public static void sendMailWithAttachment() throws MessagingException {
		  
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	       ctx.register(AppConfig.class);
	       ctx.refresh();
	       JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);
		   MimeMessage mimeMessage = mailSender.createMimeMessage();
		   //Pass true flag for multipart message
      	   MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage, true);
      	   mailMsg.setFrom("progesinipss@gmail.com");
      	   mailMsg.setTo("dragossterpu@gmail.com");
      	   mailMsg.setSubject("Test mail with Attachment");
      	   mailMsg.setText("Please find Attachment.");
      	   //FileSystemResource object for Attachment
      	   FileSystemResource file = new FileSystemResource(new File("C:/Users/Admin/Desktop/adrian/SpringMail/src/main/java/com/concretepage/AppConfig.java"));
      	   mailMsg.addAttachment("mi clase java", file);
	       mailSender.send(mimeMessage);
	       System.out.println("---Done---");
	}
}
