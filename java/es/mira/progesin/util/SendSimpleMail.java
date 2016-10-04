package es.mira.progesin.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import es.mira.progesin.constantes.Constantes;



public class SendSimpleMail {
	static Logger LOG = Logger.getLogger(SendSimpleMail.class);
	
	public static void sendMail(String asunto,String correoEnvio,String nombre,String respuesta) throws MessagingException {
		Date fecha = new Date();
		final String username = "progesinipss@gmail.com";
	    final String password = "ipss2016";
	    	    
	    String certificatesTrustStorePath = "<JAVA HOME>/jre/lib/security/cacerts";
	    System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });

	    try {
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(Constantes.EMAIL_FROM_IPSS));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(correoEnvio));
	        message.setSubject(asunto);
	        message.setContent(respuesta, "text/plain; charset=UTF-8");
	        Transport.send(message);
	    } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }

	}
	
	public static String getMailBody(String respuesta, String nombre, String asunto) {
		   StringBuilder body = new StringBuilder();
		 
		 LOG.info("Cuerpo correo envio:   " );	
		 body.append(asunto );
		 body.append("\r\n");
		 body.append("Estimado/a :" +nombre);
		 body.append("\r\n");
		 body.append(respuesta);
		 body.append("\r\n");
		 body.append("\t" + "Muchas gracias" + "\r\n");
		 return body.toString();
	}
}
