package es.mira.progesin.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class SendSimpleMail {
	public static void sendSugerencia() throws MessagingException {
		 
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
	        message.setFrom(new InternetAddress("progesinipss@gmail.com"));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse("dragossterpu@gmail.com"));
	        message.setSubject("Testing Subject");
	        message.setText("Dear Mail Crawler,"
	                + "\n\n No spam to my email, please!");

	        Transport.send(message);

	        System.out.println("Done");

	    } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }

	}
}
